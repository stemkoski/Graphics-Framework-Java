package graphics.effects;

import graphics.material.Material;
import graphics.math.Vector;

public class Effect extends Material
{
	public Effect()
	{
		super(
			"graphics/effects/Effect.vert",
			"graphics/effects/Effect.frag"  );
		
		// the actual texture reference is not 0;
		//   will be set from rendertarget by postprocessor class
		addUniform("sampler2D", "tex", new Vector(0, 1));
		locateUniforms();
	}
}
