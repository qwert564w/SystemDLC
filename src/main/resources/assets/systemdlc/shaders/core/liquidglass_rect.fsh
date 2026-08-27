#version 150

uniform sampler2D Sampler0;

uniform vec2  Resolution;
uniform vec2  Size;
uniform vec2  QuadSize;
uniform vec2  ContentOffset;
uniform vec4  Radius;
uniform vec4  FillColor;
uniform vec4  BorderColor;
uniform float BorderWidth;
uniform vec4  GlassTint;
uniform float GlassTintStrength;
uniform float GlassRefraction;
uniform float GlassChromatic;
uniform float GlassBlur;
uniform float GlassIntensity;
uniform float GlassFrosted;
uniform float GlassPosterize;
uniform float GlassPixelate;
uniform float GlassWave;
uniform float GlassWaveSpeed;
uniform float GlassWaveFreq;
uniform float GlassInnerGlow;
uniform float GlassInnerGlowSize;
uniform vec4  GlassInnerGlowColor;
uniform float Time;
uniform float GlobalAlpha;

uniform int  BoxCount;
uniform vec4 BoxCenterA;
uniform vec4 BoxHalfSizeA;
uniform vec4 BoxRadii0;
uniform vec4 BoxRadii1;
uniform vec2 FilletRadius;

in  vec2 texCoord0;
out vec4 fragColor;

float sdRoundBox(vec2 p, vec2 b, vec4 r) {
    float rTL = r.x, rTR = r.y, rBR = r.z, rBL = r.w;
    float rRight = (p.y < 0.0) ? rTR : rBR;
    float rLeft  = (p.y < 0.0) ? rTL : rBL;
    float rad    = (p.x > 0.0) ? rRight : rLeft;
    vec2 q = abs(p) - b + vec2(rad);
    return min(max(q.x, q.y), 0.0) + length(max(q, 0.0)) - rad;
}

float shapeSDF(vec2 p, vec2 halfSize) {
    if (BoxCount > 0) {
        vec2 c0 = BoxCenterA.xy - halfSize;
        float dHeader = sdRoundBox(p - c0, BoxHalfSizeA.xy, BoxRadii0);
        if (BoxCount <= 1) return dHeader;

        vec2 c1 = BoxCenterA.zw - halfSize;
        float dContent = sdRoundBox(p - c1, BoxHalfSizeA.zw, BoxRadii1);

        float k = (p.x < c0.x) ? FilletRadius.x : FilletRadius.y;
        if (k > 0.001) {
            float h = clamp(0.5 + 0.5 * (dContent - dHeader) / k, 0.0, 1.0);
            return mix(dContent, dHeader, h) - k * h * (1.0 - h);
        }
        return min(dHeader, dContent);
    }
    return sdRoundBox(p, halfSize, Radius);
}

float hash21(vec2 p) {
    p = fract(p * vec2(123.34, 456.21));
    p += dot(p, p + 45.32);
    return fract(p.x * p.y);
}

void main() {
    vec2 pixel  = texCoord0 * QuadSize - ContentOffset;
    vec2 center = Size * 0.5;
    vec2 p      = pixel - center;

    float d  = shapeSDF(p, center);
    float aa = max(fwidth(d), 0.5);
    float fillMask = 1.0 - smoothstep(-aa, aa, d);
    if (fillMask <= 0.001) discard;

    float borderMask = 0.0;
    if (BorderWidth > 0.0) {
        float dInner = d + BorderWidth;
        float innerFill = 1.0 - smoothstep(-aa, aa, dInner);
        borderMask = clamp(fillMask - innerFill, 0.0, 1.0);
    }

    float k = clamp(GlassIntensity, 0.0, 1.0);

    vec2 screenUV = gl_FragCoord.xy / Resolution;

    vec2 m2     = p / max(Size.x, Size.y);
    float depth = clamp(1.0 - abs(d) / max(min(Size.x, Size.y) * 0.5, 1.0), 0.0, 1.0);

    vec2 refractedUV = screenUV - m2 * (GlassRefraction * k * 0.045 * depth);

    if (GlassWave > 0.0001) {
        float wt = Time * GlassWaveSpeed;
        float freq = mix(2.0, 12.0, clamp(GlassWaveFreq, 0.0, 1.0));
        vec2 norm = p / max(min(Size.x, Size.y), 1.0);

        float wx = sin(norm.y * freq + wt) * 0.6
                 + sin(norm.y * freq * 2.2 - wt * 1.4 + norm.x * 1.7) * 0.3
                 + sin(norm.x * freq * 0.7 + wt * 0.5) * 0.2;
        float wy = cos(norm.x * freq * 1.1 + wt * 1.2) * 0.6
                 + cos(norm.x * freq * 2.5 - wt * 0.9 + norm.y * 1.7) * 0.3
                 + cos(norm.y * freq * 0.7 - wt * 0.5) * 0.2;

        refractedUV += vec2(wx, wy) * GlassWave * 0.012;
    }

    vec2 baseR, baseG, baseB;
    baseG = refractedUV;
    if (GlassChromatic > 0.0001) {
        vec2 caOffset = m2 * (GlassChromatic * k * 0.0035);
        baseR = refractedUV + caOffset;
        baseB = refractedUV - caOffset;
    } else {
        baseR = refractedUV;
        baseB = refractedUV;
    }

    bool chromatic = GlassChromatic > 0.0001;

    if (GlassPixelate > 0.0001) {
        float pixSize = mix(2.0, 32.0, clamp(GlassPixelate, 0.0, 1.0));
        vec2 grid = pixSize / Resolution;
        baseG = (floor(baseG / grid) + 0.5) * grid;
        if (chromatic) {
            baseR = (floor(baseR / grid) + 0.5) * grid;
            baseB = (floor(baseB / grid) + 0.5) * grid;
        } else {
            baseR = baseG;
            baseB = baseG;
        }
    }

    if (GlassFrosted > 0.0001) {
        float h1 = hash21(gl_FragCoord.xy + 0.5);
        float h2 = hash21(gl_FragCoord.xy + vec2(17.31, 7.71));
        vec2 jitter = (vec2(h1, h2) - 0.5) * GlassFrosted * 6.0 / Resolution;
        baseG += jitter;
        if (chromatic) {
            baseR += jitter;
            baseB += jitter;
        } else {
            baseR = baseG;
            baseB = baseG;
        }
    }

    // Блюр — trilinear-тап по мип-цепочке бэкдропа (Java-сторона строит её при GlassBlur > 0).
    // Окно мипа 2^lod px эквивалентно прежней сетке 3x3 с шагом GlassBlur*k*8, но без её
    // разреженного шиммера. Кламп 4.0 = MAX_MIP_LEVEL цепочки.
    vec3 col;
    float blurPx = GlassBlur * k * 8.0;
    float lod = (blurPx > 0.0001) ? clamp(log2(blurPx * 2.0), 0.0, 4.0) : 0.0;

    if (chromatic) {
        col.r = textureLod(Sampler0, baseR, lod).r;
        col.g = textureLod(Sampler0, baseG, lod).g;
        col.b = textureLod(Sampler0, baseB, lod).b;
    } else {
        col = textureLod(Sampler0, baseG, lod).rgb;
    }

    if (GlassPosterize > 0.0001) {
        float steps = mix(16.0, 3.0, clamp(GlassPosterize, 0.0, 1.0));
        col = floor(col * steps + 0.5) / steps;
    }

    col = mix(col, FillColor.rgb, FillColor.a * 0.5);
    col = mix(col, GlassTint.rgb, GlassTint.a * GlassTintStrength * k);

    if (GlassInnerGlow > 0.0001) {
        float maxDepth = mix(4.0, min(Size.x, Size.y) * 0.5, clamp(GlassInnerGlowSize, 0.0, 1.0));
        float depthFromEdge = max(-d, 0.0);
        float gt = clamp(depthFromEdge / max(maxDepth, 0.5), 0.0, 1.0);
        float glow = pow(1.0 - gt, 2.5);
        vec3 glowRgb = GlassInnerGlowColor.rgb * (glow * GlassInnerGlow * GlassInnerGlowColor.a);
        col = 1.0 - (1.0 - col) * (1.0 - clamp(glowRgb, 0.0, 1.0));
    }

    if (borderMask > 0.0) {
        col = mix(col, BorderColor.rgb, BorderColor.a * borderMask);
    }

    fragColor = vec4(col, fillMask * GlobalAlpha);
}
