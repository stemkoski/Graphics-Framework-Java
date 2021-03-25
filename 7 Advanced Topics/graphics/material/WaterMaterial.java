package graphics.material;

import graphics.math.Vector;
import graphics.core.Texture;

public class WaterMaterial extends Material
{
	public WaterMaterial(Texture texture)
	{
		super(
			"graphics/material/WaterMaterial.vert",
			"graphics/material/WaterMaterial.frag"  );
		
		addUniform("vec3", "baseColor", new Vector(0.43, 0.77, 0.91) );
		addUniform("sampler2D", "tex", new Vector(texture.textureRef, 1));
		locateUniforms();

		addRenderSetting( "doubleSide", true );
		addRenderSetting( "wireframe", false );
		addRenderSetting( "lineWidth", 1 );
	}
}

