package graphics.effects;

import graphics.material.Material;
import graphics.math.Vector;

public class HorizontalBlurEffect extends Material
{
	public HorizontalBlurEffect(Vector textureSize, int blurRadius)
	{
		super(
			"graphics/effects/Effect.vert",
			"graphics/effects/HorizontalBlurEffect.frag"  );
		
		// the actual texture reference is not 0;
		//   will be set from rendertarget by postprocessor class
		addUniform("sampler2D", "tex", new Vector(0, 1));
		addUniform("vec2", "textureSize", textureSize);
		addUniform("int", "blurRadius", blurRadius);
		locateUniforms();
	}
}
