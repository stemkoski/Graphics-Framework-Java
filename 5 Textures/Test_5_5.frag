uniform sampler2D image;
uniform sampler2D noise;
in vec2 UV;
uniform float time;
out vec4 fragColor;

void main()
{
	vec2 uvShift = UV + vec2( -0.033, 0.07 ) * time;
	vec4 noiseValues = texture( noise, uvShift );
	vec2 uvNoise = UV + 0.4 * noiseValues.rg;
	fragColor = texture( image, uvNoise );
}