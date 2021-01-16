uniform mat4 projectionMatrix;
uniform mat4 viewMatrix;
uniform mat4 modelMatrix;
uniform bool billboard;
uniform float tileNumber;
uniform vec2 tileCount;

in vec3 vertexPosition;
in vec2 vertexUV;
out vec2 UV;

void main()
{
	mat4 mvMatrix = viewMatrix * modelMatrix;
	if ( billboard )
	{
		mvMatrix[0][0] = 1;
		mvMatrix[0][1] = 0;
		mvMatrix[0][2] = 0;
		mvMatrix[1][0] = 0;
		mvMatrix[1][1] = 1;
		mvMatrix[1][2] = 0;
		mvMatrix[2][0] = 0;
		mvMatrix[2][1] = 0;
		mvMatrix[2][2] = 1;
	}

	gl_Position = projectionMatrix * mvMatrix * vec4(vertexPosition, 1.0);
	UV = vertexUV;
	if (tileNumber > -1.0)
	{
		vec2 tileSize = 1.0 / tileCount;
		float columnIndex = mod(tileNumber, tileCount[0]);
		float rowIndex = floor(tileNumber / tileCount[0]);
		vec2 tileOffset = vec2( columnIndex/tileCount[0],
								1.0 - (rowIndex + 1.0)/tileCount[1] );
		UV = UV * tileSize + tileOffset;
	}
}