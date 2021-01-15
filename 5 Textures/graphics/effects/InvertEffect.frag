in vec2 UV;
uniform sampler2D tex;
out vec4 fragColor;

void main()
{
	vec4 color = texture(tex, UV);
	vec4 invert = vec4(1.0 - color.r, 1.0 - color.g, 1.0 - color.b, 1.0);
	fragColor = invert;
}