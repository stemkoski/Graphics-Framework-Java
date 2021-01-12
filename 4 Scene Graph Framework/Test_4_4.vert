in vec3 vertexPosition;
out vec3 position;
uniform mat4 modelMatrix;
uniform mat4 viewMatrix;
uniform mat4 projectionMatrix;
void main()
{
	vec4 pos = vec4(vertexPosition, 1.0);
	gl_Position = projectionMatrix * viewMatrix * modelMatrix * pos;
	position = vertexPosition;
}
