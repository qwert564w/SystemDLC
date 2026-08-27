#version 150

in vec2 vLocalXZ;

uniform vec4  FillColor;
uniform vec4  EdgeColor;
uniform vec3  ViewDir;
uniform float IsDome;
uniform float GlobalAlpha;

out vec4 fragColor;

void main() {
    float xz2 = dot(vLocalXZ, vLocalXZ);
    if (xz2 > 1.0) discard;

    vec3 baseRgb;
    float baseA;

    if (IsDome > 0.5) {
        // 3D hemisphere: reconstruct surface normal from local x/z, do rim alpha.
        float y = sqrt(max(0.0, 1.0 - xz2));
        vec3 n = vec3(vLocalXZ.x, y, vLocalXZ.y);
        float ndv = clamp(dot(normalize(n), normalize(-ViewDir)), 0.0, 1.0);
        float rim = pow(1.0 - ndv, 2.0);
        float topLight = clamp(0.4 + 0.6 * y, 0.0, 1.0);
        baseRgb = mix(FillColor.rgb * topLight, EdgeColor.rgb, rim * 0.6);
        baseA   = mix(FillColor.a * 0.45, EdgeColor.a, rim);
    } else {
        // Flat billboard disc: r in [0,1], soft edge fade.
        float r = sqrt(xz2);
        float aa = max(fwidth(r), 1e-4);
        float fill = 1.0 - smoothstep(1.0 - aa, 1.0, r);
        float edge = smoothstep(0.92 - aa, 0.96, r) * (1.0 - smoothstep(1.0 - aa, 1.0, r));
        baseRgb = mix(FillColor.rgb, EdgeColor.rgb, edge);
        baseA   = max(FillColor.a * fill, edge * EdgeColor.a);
    }

    float outA = baseA * GlobalAlpha;
    if (outA <= 0.0) discard;

    fragColor = vec4(baseRgb, outA);
}
