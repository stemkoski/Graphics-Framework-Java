in vec2 UV;
uniform sampler2D tex;
uniform float levels;
out vec4 fragColor;

void main()
{
	vec4 color = texture(tex, UV);
	vec4 reduced = round(color * levels) / levels;
	reduced.a = 1.0;
	fragColor = reduced;
}