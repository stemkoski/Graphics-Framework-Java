in vec2 UV;
uniform vec3 tintColor;
uniform sampler2D tex;
out vec4 fragColor;

void main()
{
	vec4 color = texture(tex, UV);
	float gray = (color.r + color.g + color.b) / 3.0;
	fragColor = vec4(gray * tintColor, 1.0);
}