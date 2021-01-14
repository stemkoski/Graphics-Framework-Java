package graphics.material;

import graphics.math.Vector;
import graphics.core.Texture;

public class SpriteMaterial extends Material
{
	public SpriteMaterial(Texture texture)
	{
		super(
			"graphics/material/SpriteMaterial.vert",
			"graphics/material/SpriteMaterial.frag"  );
		
		addUniform("vec3", "baseColor", new Vector(1,1,1) );
		addUniform("sampler2D", "tex", new Vector(texture.textureRef, 1));
		addUniform("bool", "billboard", 0);
		addUniform("float", "tileNumber", -1);
		addUniform("vec2", "tileCount", new Vector(1, 1));
		locateUniforms();

		addRenderSetting( "doubleSide", true );
	}
}
