uniform vec3 baseColor;
uniform samplerCube cubeTex;

in vec3 cubeTexCoord;
out vec4 fragColor;

void main()
{
	vec4 color = vec4(baseColor, 1.0) * texture(cubeTex, cubeTexCoord);
	fragColor = color;
}