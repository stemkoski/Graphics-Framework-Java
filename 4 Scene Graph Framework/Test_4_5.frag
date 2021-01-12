in vec3 color;
uniform float time;
out vec4 fragColor;

void main()
{
	float r = abs(sin(time));
	vec4 c = vec4(r, -0.5*r, -0.5*r, 0.0);
	fragColor = vec4(color, 1.0) + c;
}