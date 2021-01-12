package graphics.material;

import graphics.core.OpenGLUtils;
import graphics.math.Vector;

public class BasicMaterial extends Material
{
	public BasicMaterial()
	{
		super(
			OpenGLUtils.readFileAsString("graphics/material/BasicMaterial.vert"),
			OpenGLUtils.readFileAsString("graphics/material/BasicMaterial.frag")  );
		
		addUniform("vec3", "baseColor", new Vector(1,1,1) );
		addUniform("bool", "useVertexColors", 0);
		locateUniforms();
	}
}