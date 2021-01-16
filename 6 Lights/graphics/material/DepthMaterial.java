package graphics.material;

public class DepthMaterial extends Material
{
	public DepthMaterial()
	{
		super(
			"graphics/material/DepthMaterial.vert",
			"graphics/material/DepthMaterial.frag"  );
		
		locateUniforms();
	}
}