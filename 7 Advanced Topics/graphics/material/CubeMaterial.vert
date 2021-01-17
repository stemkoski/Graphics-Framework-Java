uniform mat4 projectionMatrix;
uniform mat4 viewMatrix;
uniform mat4 modelMatrix;

in vec3 vertexPosition;

out vec3 cubeTexCoord;

void main()
{
	gl_Position = projectionMatrix * viewMatrix * modelMatrix *	vec4(vertexPosition, 1.0);
	cubeTexCoord = vertexPosition;
}
