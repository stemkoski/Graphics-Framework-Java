package graphics.material;

import graphics.math.Vector;
import graphics.core.Texture;
import graphics.core.CubeTexture;

public class ReflectMaterial extends Material
{
	public ReflectMaterial(CubeTexture cubeTex, float colorPercent)
	{
		super(
			"graphics/material/ReflectMaterial.vert",
			"graphics/material/ReflectMaterial.frag"  );
		
		addUniform("vec3", "baseColor", new Vector(1,1,1) );
		addUniform("samplerCube", "cubeTex", new Vector(cubeTex.textureRef, 1));
		addUniform("float", "colorPercent", colorPercent);
		addUniform("vec3", "viewPosition", new Vector(0,0,0) );

		locateUniforms();

	}
}

