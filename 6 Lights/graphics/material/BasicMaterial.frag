uniform vec3 baseColor;
uniform bool useVertexColors;

in vec3 color;
out vec4 fragColor;

void main()
{
	vec4 tempColor = vec4(baseColor, 1.0);

	if ( useVertexColors )
		tempColor *= vec4(color, 1.0);
	
	fragColor = tempColor;
}