#version 150

uniform sampler2D InSampler;
uniform vec2 OutSize;
uniform float Threshold;
uniform float Intensity;

in vec2 texCoord;

out vec4 fragColor;

// Soft-knee bright pass с квадратичным подъёмом и «highlight-lift»:
// яркие пиксели вносят непропорционально больший вклад, что после многослойного
// blur даёт заметные пересветы вокруг источников.
//
// Сцена читается box'ом из 4 билинейных выборок: это префильтр. Блюр ниже ходит
// разрежённым шагом, и без сглаживания здесь тонкие яркие источники мерцали бы
// при движении камеры, попадая то в выборку, то мимо.
void main() {
    vec2 o = 0.75 / OutSize;
    vec3 color = (texture(InSampler, texCoord + vec2(-o.x, -o.y)).rgb
                + texture(InSampler, texCoord + vec2( o.x, -o.y)).rgb
                + texture(InSampler, texCoord + vec2(-o.x,  o.y)).rgb
                + texture(InSampler, texCoord + vec2( o.x,  o.y)).rgb) * 0.25;

    float lum = max(color.r, max(color.g, color.b));

    float knee = 0.2;
    float soft = clamp(lum - Threshold + knee, 0.0, 2.0 * knee);
    soft = soft * soft / (4.0 * knee + 0.00001);
    float contribution = max(soft, lum - Threshold);
    contribution /= max(lum, 0.00001);

    vec3 boosted = color * contribution;
    boosted *= 1.0 + boosted * 1.2;

    fragColor = vec4(boosted * Intensity, 1.0);
}
