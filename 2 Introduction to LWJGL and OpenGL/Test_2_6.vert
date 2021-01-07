in vec3 position;
uniform vec3 translation;
void main()
{
	vec3 pos = position + translation;
	gl_Position = vec4(pos.x, pos.y, pos.z, 1.0);
}