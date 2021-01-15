in vec2 UV;
uniform sampler2D tex;
out vec4 fragColor;

void main()
{
	vec4 color = texture(tex, UV);
	fragColor = color;
	fragColor = vec4(1,0,0,1);
}