package graphics.effects;

import graphics.material.Material;
import graphics.math.Vector;
import graphics.core.Texture;

public class AdditiveBlendEffect extends Material
{
	public AdditiveBlendEffect(Texture blendTexture, float originalStrength, float blendStrength)
	{
		super(
			"graphics/effects/Effect.vert",
			"graphics/effects/AdditiveBlendEffect.frag"  );
		
		// the actual texture reference is not 0;
		//   will be set from rendertarget by postprocessor class
		addUniform("sampler2D", "tex", new Vector(0, 1));
		addUniform("sampler2D", "blendTexture", new Vector(blendTexture.textureRef, 2));
		addUniform("float", "originalStrength", originalStrength);
		addUniform("float", "blendStrength", blendStrength);
		locateUniforms();
	}
}
