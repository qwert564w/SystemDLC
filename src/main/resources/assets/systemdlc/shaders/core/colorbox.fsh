#version 150

in vec2 localUv;

uniform vec2 Size;
uniform float Radius;
uniform vec3 HueColor;
uniform float GlobalAlpha;

out vec4 fragColor;

float roundedBoxSDF(vec2 p, vec2 halfSize, float r) {
    vec2 q = abs(p) - halfSize + vec2(r);
    return length(max(q, 0.0)) + min(max(q.x, q.y), 0.0) - r;
}

void main() {
    vec2 halfSize = Size * 0.5;
    vec2 p = localUv * Size - halfSize;
    float aa = max(fwidth(length(p)), 1.0);
    float dist = roundedBoxSDF(p, halfSize, Radius);
    float mask = 1.0 - smoothstep(-aa, aa, dist);

    vec3 white = vec3(1.0);
    vec3 topColor = mix(white, HueColor, localUv.x);
    vec3 color = topColor * (1.0 - localUv.y);

    float alpha = mask * GlobalAlpha;
    if (alpha < 0.002) discard;
    fragColor = vec4(color, alpha);
}
