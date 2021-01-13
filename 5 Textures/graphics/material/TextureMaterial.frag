uniform vec3 baseColor;
uniform sampler2D texture;

in vec2 UV;
out vec4 fragColor;

void main()
{
	vec4 color = vec4(baseColor, 1.0) * texture2D(texture, UV);

	if (color.a < 0.10)
		discard;
	
	fragColor = color;
}