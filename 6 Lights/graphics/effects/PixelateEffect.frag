in vec2 UV;
uniform sampler2D tex;
uniform float pixelSize;
uniform vec2 resolution;
out vec4 fragColor;

void main()
{
	vec2 factor = resolution / pixelSize;
	vec2 newUV = floor( UV * factor ) / factor;
	vec4 color = texture(tex, newUV);
	fragColor = color;
}