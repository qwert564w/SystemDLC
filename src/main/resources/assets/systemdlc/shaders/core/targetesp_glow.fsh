#version 150

in vec2 vUv;
in vec4 vColor;

out vec4 fragColor;

void main() {
    vec2 p = vUv * 2.0 - 1.0;
    float d = dot(p, p);
    if (d > 1.0) discard;
    // Процедурный glow-шарик вместо png: яркое ядро + мягкий хвост.
    float core = exp(-d * 6.0);
    float halo = exp(-d * 2.2) * 0.55;
    float a = clamp(core + halo, 0.0, 1.0);
    fragColor = vec4(vColor.rgb, vColor.a * a);
}
