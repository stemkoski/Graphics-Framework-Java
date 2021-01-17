uniform vec3 baseColor;
uniform sampler2D image;

in float displacement;
in vec2 UV;
out vec4 fragColor;

void main()
{
	vec4 color = vec4(baseColor * (displacement+0.1), 1) * texture(image, 10*UV);

	if (color.a < 0.10)
		discard;
	
	fragColor = color;
}