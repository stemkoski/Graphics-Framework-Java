uniform sampler2D texture;
in vec2 UV;
uniform float time;
out vec4 fragColor;

void main()
{
	vec2 shiftUV = UV + vec2(0, 0.2 * sin(6.0*UV.x + time));
	fragColor = texture2D(texture, shiftUV);
}