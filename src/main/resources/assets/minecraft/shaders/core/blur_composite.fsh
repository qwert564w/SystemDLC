#version 150

uniform sampler2D BlurSampler;
uniform sampler2D MainSampler;
uniform float MixAmount;
uniform float DimAmount;
// Rounded-box маска в UV [0..1]. При пустом боксе (min>=max) блюр применяется
// ко всему кадру.
uniform vec4 BoxRect;
uniform vec2 BoxRadius;
// 1.0 — рисуем в оверлей StreamBypass: под нами нет сцены, поэтому отдаём
// только размытие с альфой localMix, чтобы фон проявлялся плавно и не затирал
// уже нарисованный HUD. 0.0 — обычный кадр, поведение прежнее.
uniform float OverlayPass;

in vec2 texCoord;

out vec4 fragColor;

float roundedBoxMask(vec2 uv, vec4 box, vec2 r) {
    vec2 center = (box.xy + box.zw) * 0.5;
    vec2 halfSize = (box.zw - box.xy) * 0.5;
    vec2 p = abs(uv - center) - halfSize + r;
    float dist = length(max(p, vec2(0.0))) + min(max(p.x, p.y), 0.0) - min(r.x, r.y);
    vec2 fw = fwidth(uv);
    float aa = max(fw.x, fw.y);
    return 1.0 - smoothstep(-aa, aa, dist);
}

void main() {
    vec3 sharp = texture(MainSampler, texCoord).rgb;
    vec3 blur  = texture(BlurSampler, texCoord).rgb;

    bool boxed = BoxRect.z > BoxRect.x;
    float mask = boxed ? roundedBoxMask(texCoord, BoxRect, BoxRadius) : 1.0;
    float localMix = MixAmount * mask;

    blur *= 1.0 - DimAmount * localMix;

    // В оверлейном проходе StreamBypass полноэкранный блюр — это фон GUI: под
    // ним нет сцены, поэтому отдаём только размытие с альфой localMix, иначе
    // непрозрачный квад затирал бы уже нарисованный HUD. Локальный блюр (в
    // боксе) размывает то, что под ним уже лежит, — там sharp обязателен.
    if (OverlayPass > 0.5 && !boxed) {
        // Фон GUI в оверлее. Источник уже содержит наши слои, поэтому HUD
        // размыт вместе со сценой и выглядит лежащим под фоном — отдельная
        // полупрозрачность ему не нужна. Альфа = localMix: фон проявляется по
        // анимации и к концу полностью перекрывает кадр MC под оверлеем.
        fragColor = vec4(blur, localMix);
    } else {
        fragColor = vec4(mix(sharp, blur, localMix), 1.0);
    }
}
