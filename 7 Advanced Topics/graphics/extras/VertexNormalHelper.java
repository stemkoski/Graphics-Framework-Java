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
	public VertexNormalHelper(Mesh mesh, double size, Vector color, int lineWidth )
	{

		super( new VertexNormalGeometry(mesh, size, color, lineWidth), 
			   new LineMaterial() );
		this.material.uniforms.get("useVertexColors").data = 1;
		this.material.renderSettings.get("lineWidth").data = lineWidth;
		this.geometry.merge(mesh.geometry);
	}

	public VertexNormalHelper(Mesh mesh)
	{
		this(mesh, 0.2, new Vector(1,0,0), 2);
	}

}