#version 150

uniform sampler2D InSampler;
uniform vec2 InSize;
uniform vec2 Direction;
uniform float Radius;

in vec2 texCoord;

out vec4 fragColor;

// Separable RGBA gaussian, 15-tap. Сохраняем цвет линий, не только alpha.
void main() {
    float sigma = max(Radius * 0.3333, 0.5);
    float twoSigmaSq = 2.0 * sigma * sigma;
    float stepSize = Radius * 0.14285714;
    vec2 texel = Direction / InSize;

    vec4 sum = texture(InSampler, texCoord);
    float wSum = 1.0;

    for (int i = 1; i <= 7; i++) {
        float d = float(i) * stepSize;
        float w = exp(-(d * d) / twoSigmaSq);
        vec2 off = texel * d;
        sum += (texture(InSampler, texCoord + off) + texture(InSampler, texCoord - off)) * w;
        wSum += w * 2.0;
    }

    fragColor = sum / wSum;
}
