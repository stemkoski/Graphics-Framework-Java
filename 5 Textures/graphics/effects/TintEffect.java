package graphics.effects;

import graphics.material.Material;
import graphics.math.Vector;

public class TintEffect extends Material
{
	public TintEffect(Vector tintColor)
	{
		super(
			"graphics/effects/Effect.vert",
			"graphics/effects/TintEffect.frag"  );
		
		// the actual texture reference is not -1;
		//   will be set from rendertarget by postprocessor class
		addUniform("sampler2D", "tex", new Vector(-1, 1));
		addUniform("vec3", "tintColor", tintColor);
		locateUniforms();
	}
}
