in vec2 UV;
uniform sampler2D tex;
uniform sampler2D blendTexture;
uniform float originalStrength;
uniform float blendStrength;
out vec4 fragColor;

void main()
{
	vec4 originalColor = texture(tex, UV);
	vec4 blendColor = texture(blendTexture, UV);
	vec4 color = originalStrength * originalColor +
	blendStrength * blendColor;
	fragColor = color;
}