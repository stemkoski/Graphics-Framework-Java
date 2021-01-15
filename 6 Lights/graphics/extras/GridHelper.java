package graphics.extras;

import graphics.core.Mesh;
import graphics.math.Vector;
import graphics.material.LineMaterial;

public class GridHelper extends Mesh
{
	public GridHelper(double size, int divisions, 
		Vector gridColor, Vector centerColor, int lineWidth )
	{
		super( new GridGeometry(size, divisions, gridColor, centerColor), 
			   new LineMaterial() );
		this.material.uniforms.get("useVertexColors").data = 1;
		this.material.renderSettings.get("lineWidth").data = lineWidth;
	}

	public GridHelper()
	{
		this(10, 10, 
			new Vector(0.5,0.5,0.5), 
			new Vector(0.8,0.8,0.8), 1);
	}
}