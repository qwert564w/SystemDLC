#version 150

in vec2 localUv;

uniform vec2 Size;
uniform float Radius;
uniform float GlobalAlpha;

out vec4 fragColor;

float roundedBoxSDF(vec2 p, vec2 halfSize, float r) {
    vec2 q = abs(p) - halfSize + vec2(r);
    return length(max(q, 0.0)) + min(max(q.x, q.y), 0.0) - r;
}

vec3 hueToRGB(float h) {
    float r = abs(h * 6.0 - 3.0) - 1.0;
    float g = 2.0 - abs(h * 6.0 - 2.0);
    float b = 2.0 - abs(h * 6.0 - 4.0);
    return clamp(vec3(r, g, b), 0.0, 1.0);
}

void main() {
    vec2 halfSize = Size * 0.5;
    vec2 p = localUv * Size - halfSize;
    float aa = max(fwidth(length(p)), 1.0);
    float dist = roundedBoxSDF(p, halfSize, Radius);
    float mask = 1.0 - smoothstep(-aa, aa, dist);

    vec3 color = hueToRGB(localUv.x);

    float alpha = mask * GlobalAlpha;
    if (alpha < 0.002) discard;
    fragColor = vec4(color, alpha);
}
