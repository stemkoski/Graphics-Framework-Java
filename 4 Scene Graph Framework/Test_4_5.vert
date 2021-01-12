uniform mat4 modelMatrix;
uniform mat4 viewMatrix;
uniform mat4 projectionMatrix;

in vec3 vertexPosition;
in vec3 vertexColor;

out vec3 color;
uniform float time;

void main()
{
	float offset = 0.2 * sin(8.0 * vertexPosition.x + time);
	vec3 pos = vertexPosition + vec3(0.0, offset, 0.0);
	gl_Position = projectionMatrix * viewMatrix * modelMatrix * vec4(pos, 1);
	color = vertexColor;
}