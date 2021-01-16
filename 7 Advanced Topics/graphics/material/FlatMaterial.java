package graphics.material;

import graphics.math.Vector;
import graphics.core.Texture;

public class FlatMaterial extends Material
{
	public FlatMaterial(Texture texture)
	{
		super(
			"graphics/material/FlatMaterial.vert",
			"graphics/material/FlatMaterial.frag"  );

		addUniform("vec3", "baseColor", new Vector(1,1,1) );
		addUniform("Light", "light0", null );
		addUniform("Light", "light1", null );
		addUniform("Light", "light2", null );
		addUniform("Light", "light3", null );
		
		if (texture == null)
			addUniform("bool", "useTexture", 0);
		else
		{
			addUniform("bool", "useTexture", 1);
			addUniform("sampler2D", "tex", new Vector(texture.textureRef, 1));
		}

		locateUniforms();

		addRenderSetting( "doubleSide", true );
		addRenderSetting( "wireframe", false );
		addRenderSetting( "lineWidth", 1 );
	}
}
