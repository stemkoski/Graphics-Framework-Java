package graphics.effects;

import graphics.material.Material;
import graphics.math.Vector;

public class VignetteEffect extends Material
{
	public VignetteEffect(float dimStart, float dimEnd, Vector dimColor)
	{
		super(
			"graphics/effects/Effect.vert",
			"graphics/effects/VignetteEffect.frag"  );
		
		// the actual texture reference is not 0;
		//   will be set from rendertarget by postprocessor class
		addUniform("sampler2D", "tex", new Vector(0, 1));
		addUniform("float", "dimStart", dimStart);
		addUniform("float", "dimEnd", dimEnd);
		addUniform("vec3", "dimColor", dimColor);
		locateUniforms();
	}
}
