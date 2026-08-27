#version 150

uniform sampler2D InSampler;
uniform vec2 InSize;
uniform vec2 BlurDir;
uniform float Radius;

in vec2 texCoord;

out vec4 fragColor;

// Разделяемая гауссиана: два прохода X/Y вместо 2D-ядра.
//
// Шаг выборок берётся от sigma, а не фиксированные 24 на сторону. Гауссиана не несёт
// частот выше своей sigma, поэтому шага в sigma/2 хватает — билинейная фильтрация
// достраивает промежутки. Прежние 24 выборки при sigma = 0.75*Radius давали ~9 выборок
// на sigma, то есть примерно четырёхкратную переработку на каждом проходе.
// Покрытие ±2*Radius сохранено.
const int MAX_TAPS = 24;

void main() {
    float r = max(Radius, 0.001);
    float sigma = max(r * 0.75, 0.5);

    float span = 2.0 * r;
    int taps = int(clamp(ceil(span / (sigma * 0.5)), 1.0, float(MAX_TAPS)));
    float stepSize = span / float(taps);
    float invTwoSigmaSq = -1.0 / (2.0 * sigma * sigma);
    vec2 texel = BlurDir / InSize;

    vec4 sum = texture(InSampler, texCoord);
    float wSum = 1.0;

    // Константная граница цикла + break: динамические границы ломают часть старых драйверов.
    for (int i = 1; i <= MAX_TAPS; i++) {
        if (i > taps) break;
        float d = float(i) * stepSize;
        float w = exp(d * d * invTwoSigmaSq);
        vec2 off = texel * d;
        sum += (texture(InSampler, texCoord + off) + texture(InSampler, texCoord - off)) * w;
        wSum += w * 2.0;
    }

    fragColor = sum / wSum;
}
