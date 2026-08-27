#version 150

in vec2 localUv;

uniform vec2  Size;
uniform float Thickness;
uniform float Progress;
uniform vec4  RingColor;
uniform float GlobalAlpha;

out vec4 fragColor;

const float TAU = 6.28318530718;

void main() {
    // Координаты от центра в единицах Size; радиус кольца — по меньшей стороне.
    vec2 p = (localUv - 0.5) * Size;
    float outerR = min(Size.x, Size.y) * 0.5;

    float r = length(p);

    // Сглаживание ровно в один экранный пиксель: fwidth(r) даёт, насколько меняется
    // радиус между соседними фрагментами, то есть длину пикселя в тех же единицах.
    // Без этой привязки при толщине ~1 обод размывался целиком и читался как пиксельный.
    float px = max(fwidth(r), 1e-4);
    float halfW = max(Thickness * 0.5, px);
    float midR = outerR - halfW;

    // Кольцо: 1 внутри обода, плавно спадает на границах в пределах пикселя.
    float ring = smoothstep(halfW + px, halfW - px, abs(r - midR));
    if (ring <= 0.0) discard;

    if (Progress < 0.9995) {
        // Угол от 12 часов по часовой: 0 сверху, растёт вправо.
        float ang = atan(p.x, -p.y);
        if (ang < 0.0) ang += TAU;

        // Срез сглаживается в те же полпикселя: на радиусе r пиксель занимает px/r радиан.
        float angPx = px / max(r, px);
        float edge = Progress * TAU;
        ring *= smoothstep(edge + angPx, edge - angPx, ang);
        if (ring <= 0.0) discard;
    }

    float alpha = RingColor.a * ring * GlobalAlpha;
    if (alpha < 0.002) discard;

    fragColor = vec4(RingColor.rgb * alpha, alpha);
}
