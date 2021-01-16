in vec2 UV;
uniform sampler2D tex;
uniform float dimStart;
uniform float dimEnd;
uniform vec3 dimColor;
out vec4 fragColor;

void main()
{
	vec4 color = texture(tex, UV);
	// calculate position in clip space from UV coordinates
	vec2 position = 2 * UV - vec2(1,1);
	// calculate distance (d) from center, which affects brightness
	float d = length(position);
	// calculate brightness (b) factor: d=dimStart, b=1; d=dimEnd, b=0.
	float b = (d - dimEnd)/(dimStart - dimEnd);
	// prevent oversaturation
	b = clamp(b, 0, 1);
	// mix the texture color and dim color
	fragColor = vec4( b * color.rgb + (1-b) * dimColor, 1 );
}
