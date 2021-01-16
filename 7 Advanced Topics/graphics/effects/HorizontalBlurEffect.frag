in vec2 UV;
uniform sampler2D tex;
uniform vec2 textureSize;
uniform int blurRadius;
out vec4 fragColor;

void main()
{
	vec2 pixelToTextureCoords = 1 / textureSize;
	vec4 averageColor = vec4(0,0,0,0);
	for (int offsetX = -blurRadius; offsetX <= blurRadius; offsetX++)
	{
		float weight = blurRadius - abs(offsetX) + 1;
		vec2 offsetUV = vec2(offsetX, 0) * pixelToTextureCoords;
		averageColor += texture(tex, UV + offsetUV) * weight;
	}
	averageColor /= averageColor.a;
	fragColor = averageColor;
}