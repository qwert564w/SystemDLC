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
    
    float angle = atan(uv.y, uv.x);
    float slashAngle = -0.5 + uSeed * 0.3;
    float angleDiff = abs(angle - slashAngle);
    
    float slashWidth = 0.3 / uTemperature;
    float slash = smoothstep(slashWidth, 0.0, angleDiff);
    
    float dist = length(uv);
    float slashLength = 0.3 + uProgress * 1.2;
    float slashStart = max(0.0, uProgress * 0.8 - 0.3);
    
    float distMask = smoothstep(slashStart, slashStart + 0.1, dist) * smoothstep(slashLength, slashLength - 0.15, dist);
    
    float trail = smoothstep(slashWidth * 2.0, 0.0, angleDiff) * 0.4;
    
    float fadeOut = 1.0 - smoothstep(0.7, 1.0, uProgress);
    
    float alpha = (slash + trail) * distMask * fadeOut * uBrightness;
    
    vec3 color = uTint * (1.0 + slash * 0.3);
    
    fragColor = vec4(color, alpha);
}
