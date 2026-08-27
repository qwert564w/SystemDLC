#version 150

uniform sampler2D BlurSampler;
uniform sampler2D MaskSampler;
uniform vec4 OutlineColor;
uniform vec4 OutlineColorTop;
uniform vec4 FillColor;
uniform float Intensity;
uniform float FillAlpha;
uniform float Time;
uniform float FlameStrength;
uniform float GradientSpeed;

in vec2 texCoord;

out vec4 fragColor;

float hash(vec2 p) {
    p = fract(p * vec2(123.34, 456.21));
    p += dot(p, p + 45.32);
    return fract(p.x * p.y);
}

float vnoise(vec2 p) {
    vec2 i = floor(p);
    vec2 f = fract(p);
    float a = hash(i);
    float b = hash(i + vec2(1.0, 0.0));
    float c = hash(i + vec2(0.0, 1.0));
    float d = hash(i + vec2(1.0, 1.0));
    vec2 u = f * f * (3.0 - 2.0 * f);
    return mix(mix(a, b, u.x), mix(c, d, u.x), u.y);
}

float fbm(vec2 p) {
    float v = 0.0;
    float amp = 0.5;
    for (int i = 0; i < 4; i++) {
        v += amp * vnoise(p);
        p *= 2.0;
        amp *= 0.5;
    }
    return v;
}

void main() {
    vec2 sampleCoord = texCoord;
    float flicker = 1.0;

    if (FlameStrength > 0.001) {
        vec2 flameCoord = texCoord * 4.0;
        flameCoord.y -= Time * 0.9;
        float distortion = (fbm(flameCoord) - 0.5) * FlameStrength * 0.02;
        sampleCoord += vec2(distortion * 0.6, distortion * 1.5);
        flicker = 1.0 + FlameStrength * 0.35 * (fbm(vec2(Time * 3.0, 0.0)) - 0.5);
    }

    float blur = texture(BlurSampler, sampleCoord).a * flicker;
    float mask = texture(MaskSampler, texCoord).a;

    float inside = FlameStrength > 0.001
        ? smoothstep(0.55, 0.95, mask)
        : smoothstep(0.35, 0.75, mask);
    float outsideMask = FlameStrength > 0.001 ? (1.0 - step(0.5, mask)) : 1.0;

    float outerGlow = blur * (1.0 - inside) * Intensity * outsideMask;
    float fillGlow = mask * FillAlpha;

    if (outerGlow + fillGlow <= 0.002) discard;

    outerGlow = clamp(outerGlow, 0.0, 1.0);
    fillGlow = clamp(fillGlow, 0.0, 1.0);

    vec4 outlineColor = OutlineColor;
    if (OutlineColorTop.a > 0.001) {
        float offset = 0.0;
        if (GradientSpeed > 0.001) {
            offset = sin(Time * GradientSpeed) * 0.35;
        }
        float wobble = 0.0;
        if (FlameStrength > 0.001) {
            wobble = (fbm(vec2(texCoord.x * 3.0, Time * 0.7)) - 0.5) * FlameStrength * 0.25;
        }
        float t = clamp(texCoord.y + offset + wobble, 0.0, 1.0);
        t = smoothstep(0.0, 1.0, t);
        outlineColor = mix(OutlineColor, OutlineColorTop, t);
    }

    float outlineA = outlineColor.a * outerGlow;
    float fillA = FillColor.a * fillGlow;
    vec3 outlineRgb = vec3(1.0) - exp(-outlineColor.rgb * outlineA * 2.5);
    vec3 fillRgb = vec3(1.0) - exp(-FillColor.rgb * fillA * 2.5);

    float alpha = outlineA + fillA * (1.0 - outlineA);
    vec3 rgb = outlineRgb + fillRgb * (1.0 - outlineA);

    fragColor = vec4(rgb, alpha);
}
