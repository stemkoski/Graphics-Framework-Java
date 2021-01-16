in vec2 UV;
uniform sampler2D tex;
uniform float threshold;
out vec4 fragColor;

void main()
{
	vec4 color = texture(tex, UV);
	if (color.r + color.g + color.b < threshold)
		fragColor = vec4(0,0,0,1);
	else
		fragColor = color;
}