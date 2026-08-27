#version 150

uniform sampler2D SceneSampler;
uniform sampler2D BloomSampler;
uniform sampler2D BloomMSampler;
uniform float BloomBoost;

in vec2 texCoord;

out vec4 fragColor;

// Два уровня блюра: узкий (Bloom, radius 6) + широкий (BloomM, radius 24 поверх узкого).
// Смесь: bloom-сумма → screen для мягкого верха + аддитивный вклад для «жирного» гало.
void main() {
    vec3 scene = texture(SceneSampler, texCoord).rgb;
    vec3 bNarrow = texture(BloomSampler,  texCoord).rgb;
    vec3 bWide   = texture(BloomMSampler, texCoord).rgb;

    vec3 bloom = (bNarrow * 0.55 + bWide * 0.7) * max(BloomBoost, 0.0);
    vec3 screen = 1.0 - (1.0 - scene) * (1.0 - clamp(bloom * 0.7, 0.0, 1.0));

    fragColor = vec4(screen + bloom * 0.6, 1.0);
}
