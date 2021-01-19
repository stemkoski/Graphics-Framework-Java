package graphics.material;

import graphics.math.Vector;
import graphics.core.Texture;

public class LambertMaterial extends Material
{
	public LambertMaterial(Texture texture)
	{
		super(
			"graphics/material/LambertMaterial.vert",
			"graphics/material/LambertMaterial.frag"  );

		addUniform("vec3", "baseColor", new Vector(1,1,1) );
		addUniform("Light", "light0", null );
		addUniform("Light", "light1", null );
		addUniform("Light", "light2", null );
		addUniform("Light", "light3", null );
		
		addUniform("bool", "useTexture", 0);

		if (texture != null)
			addTextureData(texture);

		addUniform("bool", "useBumpTexture", 0);

		addUniform("bool", "useShadow", 0);

		locateUniforms();

		addRenderSetting( "doubleSide", true );
		addRenderSetting( "wireframe", false );
		addRenderSetting( "lineWidth", 1 );
	}

	public void addTextureData(Texture texture)
	{
		uniforms.get("useTexture").data = 1;
		addUniform("sampler2D", "tex", new Vector(texture.textureRef, 1));
		locateUniforms();
	}

	public void addBumpData(Texture bumpTexture, float bumpStrength)
	{
		uniforms.get("useBumpTexture").data = 1;
		addUniform("sampler2D", "bumpTexture", new Vector(bumpTexture.textureRef, 2));
		addUniform("float", "bumpStrength", 1.0f);
		locateUniforms();
	}

	public void enableShadow()
	{
		uniforms.get("useShadow").data = 1;	
		addUniform("Shadow", "shadow0", null);
		locateUniforms();
	}

}