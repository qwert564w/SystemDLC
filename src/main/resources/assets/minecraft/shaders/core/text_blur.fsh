#version 150

uniform sampler2D Sampler0;
uniform vec2 AtlasSize;
uniform float DistanceRange;
uniform float Weight;
uniform float Blur;

in vec2 texCoord;
in vec4 textColor;

out vec4 fragColor;

// MSDF-блюр текста без FBO. При Blur=0 идентичен core/text.
// Гасим screenPxRange через sharpness, добавляем Weight-компенсацию,
// чтобы тонкие штрихи не схлопнулись в ноль на предельном блюре.

float median(vec3 v) {
    return max(min(v.r, v.g), min(max(v.r, v.g), v.b));
}

float screenPxRange(vec2 uv) {
    vec2 unitRange = vec2(DistanceRange) / AtlasSize;
    vec2 screenTexSize = vec2(1.0) / fwidth(uv);
    return max(0.5 * dot(unitRange, screenTexSize), 1.0);
}

void main() {
    vec3 msd = texture(Sampler0, texCoord).rgb;

    float b = clamp(Blur, 0.0, 1.0);
    float weight = Weight + b * 0.12;
    float sd = median(msd) - 0.5 + weight;

    float px = screenPxRange(texCoord);
    float sharpness = mix(1.0, 0.08, b);
    float a = clamp(px * sharpness * sd + 0.5, 0.0, 1.0);

    float fade = mix(1.0, 0.55, b);
    float alpha = textColor.a * a * fade;
    if (alpha < 0.002) discard;

    fragColor = vec4(textColor.rgb * alpha, alpha);
}
