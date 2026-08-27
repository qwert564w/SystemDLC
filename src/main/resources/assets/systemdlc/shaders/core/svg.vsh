#version 150

in vec3 Position;
in vec2 UV0;

uniform mat4 ModelViewMat;
uniform mat4 ProjMat;
uniform vec4 ViewBox;
uniform float PaddingSvg;

out vec2 svgPos;

void main() {
    gl_Position = ProjMat * ModelViewMat * vec4(Position, 1.0);

    vec2 vbMin = ViewBox.xy - vec2(PaddingSvg);
    vec2 vbSize = ViewBox.zw + vec2(PaddingSvg) * 2.0;
    svgPos = vbMin + UV0 * vbSize;
}
