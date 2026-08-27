#version 150

uniform sampler2D Sampler0;
uniform vec2 AtlasSize;
uniform float DistanceRange;
uniform float Weight;

in vec2 texCoord;
in vec4 textColor;

out vec4 fragColor;

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
    float sd = median(msd) - 0.5 + Weight;
    float px = screenPxRange(texCoord);
    float a = clamp(px * sd + 0.5, 0.0, 1.0);

    float alpha = textColor.a * a;
    if (alpha < 0.002) discard;

    fragColor = vec4(textColor.rgb * alpha, alpha);
}
