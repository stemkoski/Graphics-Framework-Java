uniform vec3 baseColor;
uniform bool useTexture;
uniform sampler2D tex;

in vec2 UV;
in vec3 light;

out vec4 fragColor;

void main()
{
	vec4 color = vec4(baseColor, 1.0);
	
	if ( useTexture )
		color *= texture( tex, UV );
	
	color *= vec4( light, 1 );
	fragColor = color;
}