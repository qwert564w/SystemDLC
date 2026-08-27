#version 150

uniform sampler2D InSampler;
uniform float Saturation;

in vec2 texCoord;
out vec4 fragColor;

void main() {
    vec4 color = texture(InSampler, texCoord);
    
    float gray = dot(color.rgb, vec3(0.299, 0.587, 0.114));
    
    vec3 result = mix(vec3(gray), color.rgb, Saturation);
    
    fragColor = vec4(result, color.a);
}
