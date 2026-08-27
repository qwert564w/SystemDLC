#version 150

in vec2 uv;

uniform float uTime;
uniform float uAlpha;
uniform vec3 uTint;

out vec4 fragColor;

const float FREQ_RANGE = 128.0;
const float PI = 3.1415;
const float RADIUS = 0.5;
const float BRIGHTNESS = 0.15;

// Спектр запечён константой: исходник тянул его из текстуры 128x1, отдельный
// ассет ради 128 чисел не нужен.
const float FREQ[128] = float[128](
    0.3098, 0.2941, 0.2627, 0.3137, 0.2627, 0.2706, 0.2392, 0.2941,
    0.3176, 0.2588, 0.3020, 0.2510, 0.2039, 0.1961, 0.1020, 0.1686,
    0.1451, 0.1412, 0.2235, 0.2078, 0.2667, 0.2941, 0.2314, 0.2118,
    0.2353, 0.2353, 0.2549, 0.3333, 0.3529, 0.3020, 0.3255, 0.3176,
    0.2431, 0.1961, 0.1765, 0.1647, 0.2157, 0.2863, 0.2863, 0.1804,
    0.1922, 0.1843, 0.1569, 0.1490, 0.1529, 0.1882, 0.1843, 0.2353,
    0.3020, 0.3020, 0.2549, 0.2824, 0.2549, 0.2314, 0.3216, 0.3176,
    0.3098, 0.3059, 0.3137, 0.3137, 0.2235, 0.2510, 0.2275, 0.1569,
    0.1765, 0.1255, 0.1843, 0.2275, 0.2078, 0.2431, 0.2196, 0.2275,
    0.2431, 0.2000, 0.1922, 0.2471, 0.2863, 0.2824, 0.2745, 0.3216,
    0.2431, 0.2275, 0.2627, 0.1961, 0.2706, 0.2706, 0.2863, 0.2549,
    0.2824, 0.2706, 0.2118, 0.1843, 0.1255, 0.1569, 0.1255, 0.2000,
    0.2549, 0.2039, 0.2353, 0.2588, 0.2431, 0.2431, 0.1961, 0.2392,
    0.2745, 0.3490, 0.3333, 0.3333, 0.3294, 0.2588, 0.2039, 0.2196,
    0.1882, 0.2196, 0.1569, 0.2000, 0.2314, 0.1961, 0.1569, 0.1843,
    0.1686, 0.1333, 0.2471, 0.2392, 0.2471, 0.2941, 0.2627, 0.2824
);

/** Выборка из LUT как из текстуры 128x1: LINEAR + clamp to edge. */
float sampleFreq(float u) {
    float t = clamp(u, 0.0, 1.0) * FREQ_RANGE - 0.5;
    float base = floor(t);
    int a = int(clamp(base, 0.0, FREQ_RANGE - 1.0));
    int b = int(clamp(base + 1.0, 0.0, FREQ_RANGE - 1.0));
    return mix(FREQ[a], FREQ[b], t - base);
}

vec3 hsv2rgb(vec3 color) {
    vec4 konvert = vec4(1.0, 2.0 / 3.0, 1.0 / 3.0, 3.0);
    vec3 calc = abs(fract(color.xxx + konvert.xyz) * 6.0 - konvert.www);
    return color.z * mix(konvert.xxx, clamp(calc - konvert.xxx, 0.0, 1.0), color.y);
}

float luma(vec3 color) {
    return dot(color, vec3(0.299, 0.587, 0.5));
}

float getFrequency(float x) {
    return sampleFreq(floor(x * FREQ_RANGE + 1.0) / FREQ_RANGE) + 0.06;
}

float getFrequency_smooth(float x) {
    float index = floor(x * FREQ_RANGE) / FREQ_RANGE;
    float next = floor(x * FREQ_RANGE + 1.0) / FREQ_RANGE;
    return mix(getFrequency(index), getFrequency(next), smoothstep(0.0, 1.0, fract(x * FREQ_RANGE)));
}

float getFrequency_blend(float x) {
    return mix(getFrequency(x), getFrequency_smooth(x), 0.5);
}

vec3 circleIllumination(vec2 fragment, float radius) {
    float dist = length(fragment);
    float ring = 1.0 / abs(dist - radius - (getFrequency_smooth(0.0) / 4.50));

    vec3 color = vec3(0.0);

    float angle = atan(fragment.x, fragment.y);
    color += hsv2rgb(vec3((angle + uTime * 2.5) / (PI * 2.0), 1.0, 1.0)) * ring * BRIGHTNESS;

    float frequency = max(getFrequency_blend(abs(angle / PI)) - 0.02, 0.0);
    color *= frequency;

    return color;
}

void main() {
    vec2 fragPos = (uv - 0.5) * 2.0;

    vec3 color = circleIllumination(fragPos, RADIUS);
    color += max(luma(color) - 1.0, 0.0);

    color = uTint * max(max(color.r, color.g), color.b);

    float brightness = max(max(color.r, color.g), color.b);
    float visible = smoothstep(0.18, 0.48, brightness);
    if (visible <= 0.01) {
        discard;
    }

    fragColor = vec4(color * visible * uAlpha, visible * uAlpha);
}
