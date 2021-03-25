uniform mat4 projectionMatrix;
uniform mat4 viewMatrix;
uniform mat4 modelMatrix;

in vec3 vertexPosition;
in vec2 vertexUV;

out vec2 UV;

void main()
{
	gl_Position = projectionMatrix * viewMatrix * modelMatrix *	vec4(vertexPosition, 1.0);
	UV = vertexUV;
}
