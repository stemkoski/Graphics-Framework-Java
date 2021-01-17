uniform vec3 baseColor;
uniform samplerCube cubeTex;

in vec3 position;
out vec4 fragColor;

void main()
{
	vec4 color = vec4(baseColor, 1.0) * texture(cubeTex, position);
	color = vec4(position, 1);
	fragColor = color;
	fragColor = textureCube(cubeTex, normalize(position));
}