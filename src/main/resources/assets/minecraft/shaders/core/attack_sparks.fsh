#version 150

uniform float uTime;
uniform float uProgress;
uniform float uSeed;
uniform float uTemperature;
uniform float uBrightness;
uniform vec3 uTint;

in vec2 texCoord;
out vec4 fragColor;

float hash(vec2 p) {
    p = fract(p * vec2(123.34 + uSeed, 456.21 + uSeed));
    p += dot(p, p + 45.32);
    return fract(p.x * p.y);
}

void main() {
    vec2 uv = texCoord * 2.0 - 1.0;
    float dist = length(uv);
    
    float fadeOut = 1.0 - uProgress;
    float fadeIn = smoothstep(0.0, 0.15, uProgress);
    float fade = fadeOut * fadeIn;
    
    float angle = atan(uv.y, uv.x);
    int sparkCount = int(12.0 + uTemperature * 8.0);
    
    float spark = 0.0;
    for(int i = 0; i < 24; i++) {
        if(i >= sparkCount) break;
        
        float sparkAngle = float(i) * 6.28318 / float(sparkCount);
        float sparkSeed = hash(vec2(float(i), uSeed));
        sparkAngle += sparkSeed * 0.5;
        
        float angleDiff = abs(mod(angle - sparkAngle + 3.14159, 6.28318) - 3.14159);
        float sparkWidth = 0.08 / uTemperature;
        float sparkShape = smoothstep(sparkWidth, 0.0, angleDiff);
        
        float sparkLength = 0.3 + sparkSeed * 0.4 + uProgress * 0.8;
        float distFade = smoothstep(sparkLength, 0.0, dist);
        
        spark = max(spark, sparkShape * distFade);
    }
    
    float core = smoothstep(0.4 * uTemperature, 0.0, dist);
    
    float alpha = (spark + core * 0.8) * fade * uBrightness;
    
    vec3 color = uTint * (1.0 + core * 0.5);
    
    fragColor = vec4(color, alpha);
}
