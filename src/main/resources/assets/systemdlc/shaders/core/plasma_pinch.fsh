#version 150

in vec2 localUv;

uniform float uTime;
uniform float uProgress;
uniform float uSeed;
uniform float uTemperature;
uniform float uBrightness;
uniform vec3 uTint;

out vec4 fragColor;

float sq(float x) {
    return x * x;
}

float hash21(vec2 p) {
    p = fract(p * vec2(127.1, 311.7));
    p += dot(p, p + 34.56);
    return fract(p.x * p.y);
}

float vnoise(vec2 p) {
    vec2 i = floor(p);
    vec2 f = fract(p);
    vec2 u = f * f * (3.0 - 2.0 * f);
    float a = hash21(i);
    float b = hash21(i + vec2(1.0, 0.0));
    float c = hash21(i + vec2(0.0, 1.0));
    float d = hash21(i + vec2(1.0, 1.0));
    return mix(mix(a, b, u.x), mix(c, d, u.x), u.y);
}

float fbm2(vec2 p) {
    float s = 0.6 * vnoise(p);
    s += 0.4 * vnoise(p * 2.07 + vec2(9.2, 4.7));
    return s;
}

void main() {
    // r = 1.0 — номинальный радиус эффекта, хвост кольца живёт до 1.4.
    vec2 d = localUv * 1.4;
    float r = length(d);
    if (r > 1.4) discard;

    float t = uProgress;
    float ang = atan(d.y, d.x);

    float eIn = smoothstep(0.0, 0.22, t);
    float eOut = 1.0 - smoothstep(0.35, 1.0, t);
    float life = eIn * eOut;

    float te = 1.0 - pow(1.0 - t, 2.4);
    float ringR = te * 1.02;

    float n = fbm2(vec2(ang * (2.0 + uTemperature * 2.0) + uSeed * 3.0 + uTime * 0.35, ringR * 1.6 + uSeed));
    float ringRad = ringR + (n - 0.5) * 0.06;

    float ringW = mix(0.34, 0.15, te);
    float ringG = (r - ringRad) / ringW;
    float ring = exp(-sq(ringG)) * (0.72 + 0.56 * n);

    float coreBloom = exp(-sq(r / 0.42));
    float coreE = (0.55 + 0.45 * eOut) * exp(-t * 3.0) * eIn;

    float haze = exp(-sq(r / 0.95));

    vec3 ringCol = uTint;
    vec3 coreCol = mix(uTint, vec3(1.0), 0.38);
    vec3 hazeCol = mix(uTint, vec3(0.55, 0.16, 0.62), 0.55);

    float ringE = ring * 0.40 * life;
    float coreEn = coreBloom * coreE * 0.46;
    float hazeE = haze * life * 0.13;

    vec3 glow = (ringCol * ringE + coreCol * coreEn + hazeCol * hazeE) * uBrightness;

    // Отсечка по силе свечения, а не по цвету: у чёрного тинта цвет нулевой всегда.
    float energy = (ringE + coreEn + hazeE) * uBrightness;
    if (energy <= 0.002) discard;

    // Тонемап мягче, а пересвет гасим делением на пиковый канал: яркость режется,
    // оттенок остаётся — иначе на верхах всё выцветает в белый.
    glow = glow / (1.0 + glow * 0.35);
    float peak = max(max(glow.r, glow.g), glow.b);
    if (peak > 1.0) glow /= peak;

    // Альфа = сила свечения: нужна, когда цвет тёмный и клиент рисует обычным блендингом.
    fragColor = vec4(glow, clamp(energy, 0.0, 1.0));
}
