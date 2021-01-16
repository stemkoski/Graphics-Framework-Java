package graphics.effects;

import graphics.material.Material;
import graphics.math.Vector;

public class BrightFilterEffect extends Material
{
	public BrightFilterEffect(float threshold)
	{
		super(
			"graphics/effects/Effect.vert",
			"graphics/effects/BrightFilterEffect.frag"  );
		
		// the actual texture reference is not 0;
		//   will be set from rendertarget by postprocessor class
		addUniform("sampler2D", "tex", new Vector(0, 1));
		addUniform("float", "threshold", threshold);
		locateUniforms();
	}
}
