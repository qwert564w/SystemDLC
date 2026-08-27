#version 150

uniform float uTime;
uniform float uProgress;
uniform float uSeed;
uniform float uTemperature;
uniform float uBrightness;
uniform vec3 uTint;

in vec2 texCoord;
out vec4 fragColor;

void main() {
    vec2 uv = texCoord * 2.0 - 1.0;
    float dist = length(uv);
    
    float waveRadius = uProgress * 1.2;
    float waveThickness = 0.15 + uTemperature * 0.1;
    
    float wave = abs(dist - waveRadius);
    wave = smoothstep(waveThickness, 0.0, wave);
    
    float fadeOut = 1.0 - smoothstep(0.7, 1.0, uProgress);
    float fadeIn = smoothstep(0.0, 0.1, uProgress);
    
    float distortion = sin(atan(uv.y, uv.x) * 8.0 + uTime * 3.0 + uSeed) * 0.5 + 0.5;
    wave *= 0.7 + distortion * 0.3;
    
    float innerGlow = smoothstep(waveRadius + waveThickness * 0.5, 0.0, dist) * 0.3;
    
    float alpha = (wave + innerGlow) * fadeOut * fadeIn * uBrightness;
    
    vec3 color = uTint;
    
    fragColor = vec4(color, alpha);
}
