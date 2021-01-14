uniform sampler2D tex;
in vec2 UV;
uniform float time;
out vec4 fragColor;

void main()
{
	vec2 shiftUV = UV + vec2(0, 0.2 * sin(6.0*UV.x + time));
	fragColor = texture(tex, shiftUV);
}