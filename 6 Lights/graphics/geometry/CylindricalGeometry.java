package graphics.geometry;

import graphics.math.Vector;
import graphics.math.Matrix;

public class CylindricalGeometry extends SurfaceGeometry
{
	public CylindricalGeometry( 
		double radiusTop, double radiusBottom, double height, 
		int radialSegments, int heightSegments,
		boolean closedTop, boolean closedBottom )
	{
		super( (u,v) -> { return new Vector(
				(v*radiusTop + (1-v)*radiusBottom) * Math.sin(u),
				height * (v - 0.5),
				(v*radiusTop + (1-v)*radiusBottom) * Math.cos(u)); },
				0, 2*Math.PI, radialSegments,
				0, 1, heightSegments  );

		if (closedTop)
		{
			Geometry topGeometry = new PolygonGeometry(radialSegments, radiusTop);
			Matrix transform = Matrix.makeTranslation(0, height/2, 0);
			transform.rightMultiply( Matrix.makeRotationY(-Math.PI/2) );
			transform.rightMultiply( Matrix.makeRotationX(-Math.PI/2) );
			topGeometry.applyMatrix( transform );
			this.merge( topGeometry );
		}

		if (closedBottom)
		{
			Geometry bottomGeometry = new PolygonGeometry(radialSegments, radiusBottom);
			Matrix transform = Matrix.makeTranslation(0, -height/2, 0);
			transform.rightMultiply( Matrix.makeRotationY(-Math.PI/2) );
			transform.rightMultiply( Matrix.makeRotationX( Math.PI/2) );
			bottomGeometry.applyMatrix( transform );
			this.merge( bottomGeometry );
		}
	}

	public CylindricalGeometry()
	{
		this(1,1,1, 32, 4, true,true);
	}
}
