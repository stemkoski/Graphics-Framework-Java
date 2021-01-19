package graphics.extras;

import graphics.core.Mesh;
import graphics.math.Vector;
import graphics.material.LineMaterial;
import graphics.geometry.Geometry;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

public class VertexNormalHelper extends Mesh
{
	public VertexNormalHelper(Mesh mesh, double lineLength, Vector baseColor, int lineWidth )
	{

		super( new VertexNormalGeometry(mesh, lineLength), 
			   new LineMaterial() );

		this.material.uniforms.get("useVertexColors").data = 1;
		this.material.uniforms.get("baseColor").data = baseColor;
		this.material.renderSettings.get("lineWidth").data = lineWidth;
	}

	public VertexNormalHelper(Mesh mesh)
	{
		this(mesh, 0.2, new Vector(0.5, 0.5, 1), 2);
	}

}