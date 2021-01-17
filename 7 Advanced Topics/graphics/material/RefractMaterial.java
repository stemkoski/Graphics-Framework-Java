package graphics.material;

import graphics.math.Vector;
import graphics.core.Texture;
import graphics.core.CubeTexture;

public class RefractMaterial extends Material
{
	// ratios: air->water = 0.75, air->glass = 0.66, air->diamond = 0.50
	public RefractMaterial(CubeTexture cubeTex, float ratio, float colorPercent)
	{
		super(
			"graphics/material/RefractMaterial.vert",
			"graphics/material/RefractMaterial.frag"  );
		
		addUniform("vec3", "baseColor", new Vector(1,1,1) );
		addUniform("samplerCube", "cubeTex", new Vector(cubeTex.textureRef, 1));
		addUniform("float", "ratio", ratio);
		addUniform("float", "colorPercent", colorPercent);
		addUniform("vec3", "viewPosition", new Vector(0,0,0) );

		locateUniforms();

	}
}

