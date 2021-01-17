uniform mat4 projectionMatrix;
uniform mat4 viewMatrix;
uniform mat4 modelMatrix;

in vec3 vertexPosition;
in vec2 vertexUV;
in vec3 vertexNormal;

uniform sampler2D heightmap;
uniform float factor;
// normalized between 0 and 1
out float displacement;
out vec2 UV;

void main()
{
	// assuming map is grayscale it doesn't matter if you use r, g, or b
	displacement = texture(heightmap, vertexUV).r;
	
	vec3 pos = vertexPosition + vertexNormal * displacement * factor;

	gl_Position = projectionMatrix * viewMatrix * modelMatrix *	vec4(pos, 1);

	UV = vertexUV;
}
