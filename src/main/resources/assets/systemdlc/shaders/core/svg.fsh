#version 150

in vec2 svgPos;

uniform float StrokeWidth;
uniform vec4 Color;
uniform float GlobalAlpha;
uniform float Filled;
uniform float SegmentCount;
uniform vec4 Segments[256];

out vec4 fragColor;

float distToSeg(vec2 p, vec2 a, vec2 b) {
    vec2 ba = b - a;
    vec2 pa = p - a;
    float h = clamp(dot(pa, ba) / max(dot(ba, ba), 1e-8), 0.0, 1.0);
    return length(pa - ba * h);
}

bool insideAt(vec2 p, int count) {
    float w = 0.0;
    for (int i = 0; i < count; i++) {
        vec4 s = Segments[i];
        vec2 a = s.xy;
        vec2 b = s.zw;
        if (a.y <= p.y) {
            if (b.y > p.y) {
                float t = (p.y - a.y) / (b.y - a.y);
                if (a.x + t * (b.x - a.x) > p.x) w += 1.0;
            }
        } else if (b.y <= p.y) {
            float t = (p.y - a.y) / (b.y - a.y);
            if (a.x + t * (b.x - a.x) > p.x) w -= 1.0;
        }
    }
    return abs(w) > 0.5;
}

void main() {
    int count = int(SegmentCount + 0.5);
    if (count <= 0) discard;

    float minDist = 1e9;
    for (int i = 0; i < count; i++) {
        vec4 s = Segments[i];
        minDist = min(minDist, distToSeg(svgPos, s.xy, s.zw));
    }

    vec2 fw = fwidth(svgPos);
    float aa = max(length(fw) * 0.70710678, 1e-5);

    float alpha;
    if (Filled > 0.5) {
        float sd = insideAt(svgPos, count) ? -minDist : minDist;
        alpha = 1.0 - smoothstep(-aa, aa, sd);
    } else {
        float halfStroke = StrokeWidth * 0.5;
        alpha = 1.0 - smoothstep(halfStroke - aa, halfStroke + aa, minDist);
    }

    alpha *= Color.a * GlobalAlpha;
    if (alpha < 0.002) discard;
    fragColor = vec4(Color.rgb, alpha);
}
