in vec3 position;
out vec4 fragColor;
void main()
{
	vec3 color = mod(position, 1.0);
	fragColor = vec4(color, 1.0);
}
