#version 150

uniform sampler2D InSampler;
uniform float Intensity;

in vec2 texCoord;

out vec4 fragColor;

// Additive blit: цвет blurred линии в mask * intensity.
// Итоговый alpha — исходный alpha blurred семпла (для правильного blendFuncSeparate).
void main() {
    vec4 c = texture(InSampler, texCoord);
    fragColor = vec4(c.rgb * Intensity, c.a * Intensity);
}
