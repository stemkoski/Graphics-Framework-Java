package graphics.material;

import graphics.math.Vector;
import graphics.core.Texture;

public class BlendMaterial extends Material
{
	public BlendMaterial(Texture blendMap, Texture baseTex, Texture rTex, Texture gTex, Texture bTex)
	{
		super(
			"graphics/material/BlendMaterial.vert",
			"graphics/material/BlendMaterial.frag"  );

		addUniform("vec3", "baseColor", new Vector(1,1,1) );
		addUniform("Light", "light0", null );
		addUniform("Light", "light1", null );
		addUniform("Light", "light2", null );
		addUniform("Light", "light3", null );
		
		addUniform("vec2", "repeatUV", new Vector(1,1) );

		addUniform("sampler2D", "blendMap", new Vector(blendMap.textureRef, 1));
		addUniform("sampler2D", "baseTex", new Vector(baseTex.textureRef, 2));
		addUniform("sampler2D", "rTex", new Vector(rTex.textureRef, 3));
		addUniform("sampler2D", "gTex", new Vector(gTex.textureRef, 4));
		addUniform("sampler2D", "bTex", new Vector(bTex.textureRef, 5));

		addUniform("bool", "useBumpTexture", 0);

		addUniform("bool", "useShadow", 0);

		locateUniforms();

		addRenderSetting( "doubleSide", true );
		addRenderSetting( "wireframe", false );
		addRenderSetting( "lineWidth", 1 );
	}

	public void addBumpData(Texture bumpTexture, float bumpStrength)
	{
		uniforms.get("useBumpTexture").data = 1;
		addUniform("sampler2D", "bumpTexture", new Vector(bumpTexture.textureRef, 6));
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
