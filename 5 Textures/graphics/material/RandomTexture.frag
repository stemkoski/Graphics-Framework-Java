// Return a random value in [0, 1]
float random(vec2 UV, float time) {
    return fract(235711.0 * sin(14.337*UV.x + 42.418*UV.y) + 0.2*time );
}//end random

float boxRandom(vec2 UV, float scale, float time) {
    vec2 iScaleUV = floor(scale * UV);

    if(iScaleUV.x == (scale - 1))
        iScaleUV.x = 0;

    return random(iScaleUV, time);
}//end boxRandom

float smoothRandom(vec2 UV, float scale, float time) {
    vec2 iScaleUV = floor(scale * UV);
    vec2 fScaleUV = fract(scale * UV);

    float a = random(round(iScaleUV + vec2(0,0)), time);
    float b = random(round(iScaleUV + vec2(1,0)), time);
    float c = random(round(iScaleUV + vec2(0,1)), time);
    float d = random(round(iScaleUV + vec2(1,1)), time);

    if(iScaleUV.x == (scale - 1)) {
        b = random(round(iScaleUV - vec2(scale - 1, 0) ), time);
        d = random(round(iScaleUV - vec2(iScaleUV.x - scale, 1 ) ), time);
    }

    return mix(mix(a, b, fScaleUV.x),
               mix(c, d, fScaleUV.x),
               fScaleUV.y);
}//end smoothRandom

/*
 * Add smooth random values at different scales
 * Weighted (amplitudes) so the sum is approx. 1.0
 */
float fractalRandom(vec2 UV, float scale, float time) {
    float value = 0.0;
    float amplitude = 0.5;

    for(int i = 0; i < 6; i++) {
        value += amplitude * smoothRandom(UV, scale, time);
        scale *= 2.0;
        amplitude *= 0.5;
    }

    return value;
}//end fractalRandom


uniform bool useBox;
uniform bool useSmooth;
uniform bool useFractal;
uniform bool lava;
uniform bool cloud;
uniform bool water;

in vec2 UV;
uniform float time;
out vec4 fragColor;

void main() {

    // Default settings
    float r = random(UV, time);

    if (useBox)
        r = boxRandom(UV, 6, time);

    else if (useSmooth)
        r = smoothRandom(UV, 4, time);

    else if (useFractal)
        r = fractalRandom(UV, 4, time);

    // Send out basic function
    fragColor = vec4(r, r, r, 1);

    // Pre-designed randomized textures are below
    if (lava) {
        r = fractalRandom(UV, 40, time);
        vec4 color1 = vec4(1, 0.8, 0, 1);
        vec4 color2 = vec4(0.8, 0, 0, 1);

        fragColor = mix(color1, color2, r);
    }

    else if (water) {
        r = fractalRandom(UV, 40, time);
        vec4 color1 = vec4(0.0, 0.0, 0.8, 1);   // Dark Blue
        vec4 color2 = vec4(0.3, 0.4, 0.9, 1);   // Lighter blue

        fragColor = mix(color1, color2, r);
    }

    else if (cloud) {
        r = fractalRandom(UV, 25, time);
        vec4 color1 = vec4(0.1, 0.2, 1, 1); // Blue
        vec4 color2 = vec4(1, 1, 1, 1);     // White

        vec4 sky = mix(color1, color2, r);

        vec4 horizon = vec4(0.4, 0.6, 1, 1);// Light blue
        float amount = -2 * UV.y + 2;
        amount = pow(amount, 7);

        fragColor = mix(sky, horizon, amount);
    }

}//end main
