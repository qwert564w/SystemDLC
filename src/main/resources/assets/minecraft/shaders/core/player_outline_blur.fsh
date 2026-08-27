#version 150

uniform sampler2D MaskSampler;
uniform vec2 MaskSize;
uniform vec2 Direction;
uniform float Radius;

in vec2 texCoord;

out vec4 fragColor;

// 15-tap 1D gaussian: центр + 7 симметричных пар покрывают ±Radius равномерно.
// LINEAR filter FBO даёт гладкую интерполяцию между тапами → чистое размытие
// даже при большом Radius.

void main() {
    float sigma = max(Radius * 0.3333, 0.5);
    float twoSigmaSq = 2.0 * sigma * sigma;
    float stepSize = Radius * 0.14285714;
    vec2 texel = Direction / MaskSize;

    float sum = texture(MaskSampler, texCoord).a;
    float wSum = 1.0;

    for (int i = 1; i <= 7; i++) {
        float d = float(i) * stepSize;
        float w = exp(-(d * d) / twoSigmaSq);
        vec2 off = texel * d;
        sum += (texture(MaskSampler, texCoord + off).a + texture(MaskSampler, texCoord - off).a) * w;
        wSum += w * 2.0;
    }

    float result = sum / wSum;
    fragColor = vec4(result, result, result, result);
}
