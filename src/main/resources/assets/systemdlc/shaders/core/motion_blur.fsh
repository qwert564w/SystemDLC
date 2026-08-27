#version 150

uniform sampler2D MainSampler;
uniform sampler2D MainDepthSampler;

uniform mat4 MvInverse;
uniform mat4 ProjInverse;
uniform mat4 PrevModelView;
uniform mat4 PrevProjection;
uniform vec3 CameraPos;
uniform vec3 PrevCameraPos;
uniform float Strength;
uniform int Samples;
uniform int Centered;
uniform int UseDepth;

in vec2 texCoord;

out vec4 fragColor;

vec3 reproject(vec3 screenPos) {
    vec3 ndc = screenPos * 2.0 - 1.0;
    vec4 viewPos = ProjInverse * vec4(ndc, 1.0);
    vec3 worldPos = (MvInverse * vec4(viewPos.xyz / viewPos.w, 1.0)).xyz + (CameraPos - PrevCameraPos);
    vec4 prevPos = PrevProjection * (PrevModelView * vec4(worldPos, 1.0));
    return (prevPos.xyz / prevPos.w) * 0.5 + 0.5;
}

vec2 clampLength(vec2 velocity) {
    float lenSq = dot(velocity, velocity);
    return lenSq > 0.16 ? velocity * (0.4 * inversesqrt(lenSq)) : velocity;
}

float noise(vec2 pos) {
    return fract(52.9829189 * fract(0.06711056 * pos.x + 0.00583715 * pos.y));
}

void main() {
    vec2 res = vec2(textureSize(MainSampler, 0));
    ivec2 texel = ivec2(gl_FragCoord.xy);
    ivec2 maxTexel = ivec2(res) - 1;

    float depth = 1.0;
    if (UseDepth == 1) {
        for (int x = -1; x <= 1; x++) {
            for (int y = -1; y <= 1; y++) {
                depth = min(depth, texelFetch(MainDepthSampler, clamp(texel + ivec2(x, y), ivec2(0), maxTexel), 0).x);
            }
        }
    }

    vec2 velocity = clampLength(texCoord - reproject(vec3(texCoord, depth)).xy) * Strength;

    int count = clamp(int(ceil(length(velocity * res))), 1, Samples);
    vec2 stepUv = velocity / float(count);
    float centerOffset = Centered == 1 ? -(float(count) * 0.5) : 0.0;

    vec3 sum = vec3(0.0);
    for (int i = 0; i < count; i++) {
        float fi = float(i);
        float jitter = noise(gl_FragCoord.xy + vec2(fi, fi * 1.4));
        vec3 c = texture(MainSampler, texCoord + (fi + centerOffset + jitter) * stepUv).rgb;
        sum += c * c;
    }

    fragColor = vec4(sqrt(sum / float(count)), 1.0);
}
