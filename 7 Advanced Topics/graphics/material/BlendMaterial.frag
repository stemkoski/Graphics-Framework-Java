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

struct Shadow
{
	// direction of light that casts shadow
	vec3 lightDirection;
	// data from camera that produces depth texture
	mat4 projectionMatrix;
	mat4 viewMatrix;
	// texture that stores depth values from shadow camera
	sampler2D depthTexture;
	// regions in shadow multiplied by (1-strength)
	float strength;
	// reduces unwanted visual artifacts
	float bias;
};

uniform bool useShadow;
uniform Shadow shadow0;
in vec3 shadowPosition0;

uniform vec3 baseColor;

uniform sampler2D blendMap;
// only apply repeat factors to textures
uniform vec2 repeatUV;
uniform sampler2D baseTex;
uniform sampler2D rTex;
uniform sampler2D gTex;
uniform sampler2D bTex;

uniform bool useBumpTexture;
uniform sampler2D bumpTexture;
uniform float bumpStrength;

in vec3 position;
in vec2 UV;
in vec3 normal;

out vec4 fragColor;

void main()
{
	vec4 color = vec4(baseColor, 1.0);

	vec4 blend = texture( blendMap, UV );
	float baseAmount = 1.0 - (blend.r + blend.g + blend.b);
	vec4 backColor = texture(baseTex, UV * repeatUV) * baseAmount;
	vec4 rTexColor = texture(rTex, UV * repeatUV) * blend.r;
	vec4 gTexColor = texture(gTex, UV * repeatUV) * blend.g;
	vec4 bTexColor = texture(bTex, UV * repeatUV) * blend.b;
	
	color *= (backColor + rTexColor + gTexColor + bTexColor);
	
	// calculate total effect of lights on color
	vec3 bNormal = normal;
	if (useBumpTexture)
		bNormal += bumpStrength * vec3(texture( bumpTexture, UV ));

	vec3 total = vec3(0,0,0);
	total += lightCalc( light0, position, bNormal );
	total += lightCalc( light1, position, bNormal );
	total += lightCalc( light2, position, bNormal );
	total += lightCalc( light3, position, bNormal );
	color *= vec4( total, 1 );
	
	if (useShadow)
	{
		// determine if surface is facing towards light direction
		float cosAngle = dot( normalize(normal), -normalize(shadow0.lightDirection) );
		bool facingLight = (cosAngle > 0.01);
		
		// convert range [-1, 1] to range [0, 1]
		// for UV coordinate and depth information
		vec3 shadowCoord = ( shadowPosition0.xyz + 1.0 ) / 2.0;
		float closestDistanceToLight = texture2D(
		shadow0.depthTexture, shadowCoord.xy).r;
		float fragmentDistanceToLight = clamp(shadowCoord.z, 0, 1);

		// determine if fragment lies in shadow of another object
		bool inShadow = ( fragmentDistanceToLight >
		closestDistanceToLight + shadow0.bias );
		if (facingLight && inShadow)
		{
			float s = 1.0 - shadow0.strength;
			color *= vec4(s, s, s, 1);
		}
	}
	
	fragColor = color;
}