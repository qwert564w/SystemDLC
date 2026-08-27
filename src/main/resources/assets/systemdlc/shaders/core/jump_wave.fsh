#version 150

in vec2 uv;

uniform sampler2D SceneSampler;

uniform float uProgress;
uniform float uStrength;
uniform float uFade;
uniform vec3 uCrestColor;

out vec4 fragColor;

const float RING_WIDTH = 0.30;

void main() {
    // Квад в [-1, 1], расстояние от точки прыжка.
    vec2 centered = (uv - 0.5) * 2.0;
    float dist = length(centered);
    if (dist > 1.0) {
        discard;
    }

    // Расходящееся кольцо: мягкая полоса на текущем радиусе прогресса (гребень).
    // Размывается с обеих сторон, чтобы передний фронт искажения не был резким.
    float edge = dist - uProgress;
    float band = smoothstep(RING_WIDTH, 0.0, abs(edge));
    band *= band;

    // Базовая стеклянная заливка по всему кругу, живая весь срок жизни волны — чтобы
    // кольцо под ней преломлялось ВСЕГДА, а не только в момент прохода гребня.
    float inside = smoothstep(1.0, 0.82, dist);

    float coverage = max(inside, band);
    if (coverage <= 0.001) {
        discard;
    }

    vec2 dir = dist > 0.0001 ? centered / dist : vec2(0.0);

    vec2 screenSize = vec2(textureSize(SceneSampler, 0));
    vec2 screenUv = gl_FragCoord.xy / screenSize;

    // Рябь вдоль гребня даёт стеклу живое, текучее ощущение.
    float ripple = sin(dist * 38.0 - uProgress * 26.0) * 0.5 + 0.5;
    float crest = band * mix(0.7, 1.3, ripple);

    // Искажение по всему кругу, с сильным дополнительным толчком прямо на гребне.
    float innerDistort = inside * mix(0.55, 1.0, dist);
    float offsetAmount = uStrength * uFade * 0.05 * (innerDistort + crest * 1.6);

    // Одна выборка: гнём весь семпл равномерно, чтобы читалось как чистое стекло,
    // без поканального расщепления и радужной каймы.
    vec2 baseOffset = dir * offsetAmount;
    vec3 refracted = texture(SceneSampler, clamp(screenUv + baseOffset, vec2(0.0), vec2(1.0))).rgb;

    // Стеклянный бортик на гребне: белёсое ядро плюс подкрашенный цветом отлив.
    float crestHighlight = pow(band, 1.5) * uFade * 0.5;
    float sheen = inside * uFade * 0.06;
    vec3 crestTint = uCrestColor * pow(band, 1.2) * uFade * 0.55;
    vec3 color = refracted + vec3(crestHighlight + sheen) + crestTint;

    // Круг обязан полностью заменить сцену, иначе нарисованное под ним кольцо
    // просвечивает неискажённым.
    float alpha = max(inside, band) * uFade;
    fragColor = vec4(color, alpha);
}
