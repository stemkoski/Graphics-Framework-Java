package graphics.effects;

import graphics.material.Material;
import graphics.math.Vector;

public class PixelateEffect extends Material
{
	public PixelateEffect(float pixelSize, Vector resolution)
	{
		super(
			"graphics/effects/Effect.vert",
			"graphics/effects/PixelateEffect.frag"  );
		
		// the actual texture reference is not 0;
		//   will be set from rendertarget by postprocessor class
		addUniform("sampler2D", "tex", new Vector(0, 1));
		addUniform("float", "pixelSize", pixelSize);
		addUniform("vec2", "resolution", resolution);
		locateUniforms();
	}
}
