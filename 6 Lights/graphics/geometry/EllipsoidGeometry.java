package graphics.geometry;

import graphics.math.Vector;

public class EllipsoidGeometry extends SurfaceGeometry
{
	public EllipsoidGeometry( 
		double width, double height, double depth, 
		int radiusSegments, int heightSegments )
	{
		super( (u,v) -> { return new Vector(
			 width/2 * Math.sin(u) * Math.cos(v),
			height/2 * Math.sin(v),
			 depth/2 * Math.cos(u) * Math.cos(v)); },
				0, 2*Math.PI, radiusSegments,
				-Math.PI/2, Math.PI/2, heightSegments  );
	}

	public EllipsoidGeometry()
	{
		this(1,1,1, 32,16);
	}
}
