#version 150

uniform float uTime;
uniform vec2 uResolution;
uniform vec3 uColor;
uniform float uAlpha;
uniform float uSpeed;
uniform float uScale;
uniform float uIntensity;
uniform vec3 uCamRight;
uniform vec3 uCamUp;
uniform vec3 uCamForward;
uniform float uFov;
uniform float FogStart;
uniform float FogEnd;
uniform vec4 FogColor;
uniform int FogShape;

in vec2 vScreen;

out vec4 fragColor;

float hash3(vec3 p) {
    p = fract(p * 0.3183099 + vec3(0.1, 0.2, 0.3));
    p *= 17.0;
    return fract(p.x * p.y * p.z * (p.x + p.y + p.z));
}

float vnoise3(vec3 p) {
    vec3 i = floor(p);
    vec3 f = fract(p);
    vec3 u = f * f * (3.0 - 2.0 * f);
    return mix(mix(mix(hash3(i),                    hash3(i + vec3(1,0,0)), u.x),
                   mix(hash3(i + vec3(0,1,0)),      hash3(i + vec3(1,1,0)), u.x), u.y),
               mix(mix(hash3(i + vec3(0,0,1)),      hash3(i + vec3(1,0,1)), u.x),
                   mix(hash3(i + vec3(0,1,1)),      hash3(i + vec3(1,1,1)), u.x), u.y), u.z);
}

float fbm3(vec3 p) {
    float v = 0.5 * vnoise3(p);          p *= 2.03;
    v +=      0.25 * vnoise3(p);         p *= 2.03;
    v +=      0.125 * vnoise3(p);
    return v;
}

vec3 worldDir() {
    float t = tan(radians(uFov) * 0.5);
    float aspect = uResolution.x / uResolution.y;
    return normalize(uCamRight * (vScreen.x * aspect * t)
                   + uCamUp    * (vScreen.y * t)
                   + uCamForward);
}

void main() {
    vec3 dir = worldDir();
    float ts = uTime * uSpeed * 0.25;

    // Плавный фон по всему куполу — без резкой границы у горизонта.
    // Внизу почти чёрный, вверху лёгкий оттенок uColor.
    float skyFade = smoothstep(-0.3, 0.5, dir.y);
    vec3 col = mix(uColor * 0.02, uColor * 0.08, skyFade);

    // Штора рендерится только если dir.y > 0. Ниже — просто плавный фон,
    // так что перехода не видно.
    if (dir.y > -0.05) {
        float h = clamp(sqrt(max(dir.y, 0.0)), 0.0, 1.0);

        vec3 p = vec3(dir.x, 0.0, dir.z) * uScale;
        float warp = fbm3(p * 0.6 + vec3(ts, 0.0, ts * 0.7));

        float band = sin((dir.x * 3.0 + warp * 6.0 + dir.z * 2.0) * uScale * 0.3 + ts);
        band = pow(0.5 + 0.5 * band, 4.0);

        float curtain = band * (1.0 - h * 0.85) * smoothstep(0.0, 0.15, dir.y);
        float threads = vnoise3(vec3(dir.x, dir.y * 3.0, dir.z) * uScale * 2.0 + vec3(ts * 2.0));
        curtain *= 0.5 + 0.6 * threads;

        vec3 topColor = uColor.bgr * 0.7 + vec3(0.4, 0.1, 0.5) * 0.6;
        vec3 gradient = mix(uColor, topColor, h);

        col += gradient * curtain * (1.2 + uIntensity * 40.0);
        col += gradient * curtain * curtain * 0.3;
    }

    // Vanilla-like linear_fog. dir.y=0 (горизонт) → distance=FogEnd (полный fog).
    // dir.y=1 (зенит) → distance=FogStart (нет fog). При FogEnd маленьком — весь sky в fog.
    float skyDist = mix(FogEnd, FogStart, clamp(dir.y, 0.0, 1.0));
    float fogValue = skyDist <= FogStart ? 0.0
                   : (skyDist < FogEnd ? smoothstep(FogStart, FogEnd, skyDist) : 1.0);
    col = mix(col, FogColor.rgb, fogValue * FogColor.a);

    fragColor = vec4(col, uAlpha);
}
