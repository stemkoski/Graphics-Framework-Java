struct Light
{
	// 1 = AMBIENT, 2 = DIRECTIONAL, 3 = POINT
	int lightType;
	// used by all lights
	vec3 color;
	// used by directional lights
	vec3 direction;
	// used by point lights
	vec3 position;
	vec3 attenuation;
};

uniform Light light0;
uniform Light light1;
uniform Light light2;
uniform Light light3;

vec3 lightCalc(Light light, vec3 pointPosition, vec3 pointNormal)
{
	float ambient = 0;
	float diffuse = 0;
	float specular = 0;
	float attenuation = 1;
	vec3 lightDirection = vec3(0,0,0);
	if ( light.lightType == 1 ) // ambient light
	{
		ambient = 1;
	}
	else if ( light.lightType == 2 ) // directional light
	{
		lightDirection = normalize(light.direction);
	}
	else if ( light.lightType == 3 ) // point light
	{
		lightDirection = normalize(pointPosition - light.position);
		float distance = length(light.position - pointPosition);
		attenuation = 1.0 / (light.attenuation[0] +
		light.attenuation[1] * distance +
		light.attenuation[2] * distance * distance);
	}
	if ( light.lightType > 1 ) // directional or point light
	{
		pointNormal = normalize(pointNormal);
		diffuse = max( dot(pointNormal, -lightDirection), 0.0 );
		diffuse *= attenuation;
	}
	return light.color * (ambient + diffuse + specular);
}

uniform vec3 baseColor;
uniform bool useTexture;
uniform sampler2D texture;

in vec3 position;
in vec2 UV;
in vec3 normal;

out vec4 fragColor;

void main()
{
	vec4 color = vec4(baseColor, 1.0);

	if ( useTexture )
		color *= texture2D( texture, UV );
	
	// calculate total effect of lights on color
	vec3 total = vec3(0,0,0);
	total += lightCalc( light0, position, normal );
	total += lightCalc( light1, position, normal );
	total += lightCalc( light2, position, normal );
	total += lightCalc( light3, position, normal );
	color *= vec4( total, 1 );
	
	fragColor = color;
}