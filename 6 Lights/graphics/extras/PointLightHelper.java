package graphics.extras;

import graphics.core.Mesh;
import graphics.light.PointLight;
import graphics.geometry.SphereGeometry;
import graphics.material.SurfaceMaterial;
import graphics.math.Vector;

public class PointLightHelper extends Mesh
{
	public PointLightHelper(PointLight light, double size, int lineWidth)
	{
		super( new SphereGeometry(size, 4, 2),
			   new SurfaceMaterial() );

		this.material.uniforms.get("baseColor").data = light.color;
		this.material.renderSettings.get("wireframe").data = true;
		this.material.renderSettings.get("doubleSide").data = true;
		this.material.renderSettings.get("lineWidth").data = lineWidth;
	}

	public PointLightHelper(PointLight light)
	{
		this(light, 0.1, 1);
	}
}
