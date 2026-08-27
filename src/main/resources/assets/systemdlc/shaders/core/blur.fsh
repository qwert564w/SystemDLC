#version 150

uniform sampler2D InSampler;
uniform vec2 InSize;
uniform vec2 Direction;
uniform float Radius;

in vec2 texCoord;

out vec4 fragColor;

// Separable 1D gaussian, 13 tap (центр + 6 симметричных пар).
// Разложение 2D-гауссианы (35x35 таблично) в два прохода x/y даёт то же поле,
// но 26 семплов вместо 1225. LINEAR-фильтр FBO + downsample добивают гладкость.

void main() {
    // Radius 0 — режим копирования (слияние слоёв в источник блюра). Без этого
    // выхода полноэкранный проход крутит 13 выборок ради одного пикселя.
    // Ветка по uniform когерентна на весь draw, варп не расходится.
    if (Radius <= 0.0) {
        fragColor = texture(InSampler, texCoord);
        return;
    }

    float sigma = max(Radius * 0.333, 0.5);
    float twoSigmaSq = 2.0 * sigma * sigma;
    float stepSize = Radius * 0.16666667;
    vec2 texel = Direction / InSize;

    vec4 sum = texture(InSampler, texCoord);
    float wSum = 1.0;

    for (int i = 1; i <= 6; i++) {
        float d = float(i) * stepSize;
        float w = exp(-(d * d) / twoSigmaSq);
        vec2 off = texel * d;
        sum += (texture(InSampler, texCoord + off) + texture(InSampler, texCoord - off)) * w;
        wSum += w * 2.0;
    }

    fragColor = sum / wSum;
}
