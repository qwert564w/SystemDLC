#version 150

in vec2 localUv;

uniform vec2 Size;
uniform vec2 QuadSize;
uniform vec2 ContentOffset;
uniform vec4 Radius;
uniform vec4 FillColor;
uniform vec4 BorderColor;
uniform float BorderWidth;
uniform vec4 ShadowColor;
uniform vec2 ShadowOffset;
uniform float ShadowBlur;
uniform float GlobalAlpha;

uniform int SegmentCount;
uniform vec4 SegCentersA;
uniform vec4 SegCentersB;
uniform vec4 SegHalfWidthsA;
uniform vec4 SegHalfWidthsB;
uniform float SegmentRadius;

uniform int BoxCount;
uniform vec4 BoxCenterA;
uniform vec4 BoxHalfSizeA;
uniform vec4 BoxRadii0;
uniform vec4 BoxRadii1;
uniform vec2 FilletRadius;

out vec4 fragColor;

float roundedBoxSDF(vec2 p, vec2 halfSize, float r) {
    vec2 q = abs(p) - halfSize + vec2(r);
    return length(max(q, 0.0)) + min(max(q.x, q.y), 0.0) - r;
}

float roundedBoxSDF4(vec2 p, vec2 halfSize, vec4 r) {
    r.xy = (p.x > 0.0) ? r.yz : r.xw;
    float rr = (p.y < 0.0) ? r.x : r.y;
    vec2 q = abs(p) - halfSize + vec2(rr);
    return length(max(q, 0.0)) + min(max(q.x, q.y), 0.0) - rr;
}

float selectRadius(vec2 p) {
    if (p.x < 0.0) return p.y < 0.0 ? Radius.x : Radius.w;
    return p.y < 0.0 ? Radius.y : Radius.z;
}

float getSegCenter(int i) {
    return i < 4 ? SegCentersA[i] : SegCentersB[i - 4];
}

float getSegHalfWidth(int i) {
    return i < 4 ? SegHalfWidthsA[i] : SegHalfWidthsB[i - 4];
}

float shapeSDF(vec2 p, vec2 halfSize) {
    if (BoxCount > 0) {
        vec2 c0 = vec2(BoxCenterA.x - halfSize.x, BoxCenterA.y - halfSize.y);
        vec2 hs0 = vec2(BoxHalfSizeA.x, BoxHalfSizeA.y);
        float dHeader = roundedBoxSDF4(p - c0, hs0, BoxRadii0);
        if (BoxCount <= 1) return dHeader;

        vec2 c1 = vec2(BoxCenterA.z - halfSize.x, BoxCenterA.w - halfSize.y);
        vec2 hs1 = vec2(BoxHalfSizeA.z, BoxHalfSizeA.w);
        float dContent = roundedBoxSDF4(p - c1, hs1, BoxRadii1);

        float k = (p.x < c0.x) ? FilletRadius.x : FilletRadius.y;
        if (k > 0.001) {
            float h = clamp(0.5 + 0.5 * (dContent - dHeader) / k, 0.0, 1.0);
            return mix(dContent, dHeader, h) - k * h * (1.0 - h);
        }
        return min(dHeader, dContent);
    }

    if (SegmentCount <= 1) {
        return roundedBoxSDF(p, halfSize, selectRadius(p));
    }

    float rSeg = min(SegmentRadius, halfSize.y);
    float d = 1e6;
    for (int i = 0; i < 8; i++) {
        if (i >= SegmentCount) break;
        float cx = getSegCenter(i) - halfSize.x;
        float hw = getSegHalfWidth(i);
        vec2 sp = vec2(p.x - cx, p.y);
        d = min(d, roundedBoxSDF(sp, vec2(hw, halfSize.y), min(rSeg, hw)));
    }
    float dOuter = roundedBoxSDF(p, halfSize, selectRadius(p));
    return max(d, dOuter);
}

void main() {
    vec2 halfSize = Size * 0.5;
    vec2 p = localUv * QuadSize - ContentOffset - halfSize;

    bool hasShadow = ShadowColor.a > 0.0 && (ShadowBlur > 0.0 || dot(ShadowOffset, ShadowOffset) > 0.0);
    bool hasBorder = BorderWidth > 0.0;

    float reachX = hasShadow ? abs(ShadowOffset.x) + ShadowBlur + 1.0 : 1.0;
    float reachY = hasShadow ? abs(ShadowOffset.y) + ShadowBlur + 1.0 : 1.0;
    if (abs(p.x) > halfSize.x + reachX || abs(p.y) > halfSize.y + reachY) discard;

    float aa = 0.5;
    float dist = shapeSDF(p, halfSize);
    float rectAlpha = 1.0 - smoothstep(-aa, aa, dist);

    float shadowAlpha = 0.0;
    if (hasShadow) {
        float shadowDist = shapeSDF(p - ShadowOffset, halfSize);
        shadowAlpha = (1.0 - smoothstep(-aa, ShadowBlur + aa, shadowDist)) * ShadowColor.a * (1.0 - rectAlpha);
    }

    float fillMask = rectAlpha;
    float borderMask = 0.0;
    if (hasBorder) {
        float innerAlpha = 1.0 - smoothstep(-aa, aa, dist + BorderWidth);
        borderMask = rectAlpha - innerAlpha;
        fillMask = innerAlpha;
    }

    vec3 color = ShadowColor.rgb;
    float alpha = shadowAlpha;

    float fillA = fillMask * FillColor.a;
    if (fillA > 0.0) {
        color = mix(color, FillColor.rgb, fillA / max(alpha + fillA, 0.001));
        alpha = alpha * (1.0 - fillA) + fillA;
    }

    if (hasBorder) {
        float borderA = borderMask * BorderColor.a;
        if (borderA > 0.0) {
            color = mix(color, BorderColor.rgb, borderA / max(alpha + borderA, 0.001));
            alpha = alpha * (1.0 - borderA) + borderA;
        }
    }

    alpha *= GlobalAlpha;
    if (alpha < 0.002) discard;

    fragColor = vec4(color, alpha);
}
