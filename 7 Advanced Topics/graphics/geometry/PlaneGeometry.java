package graphics.geometry;

import graphics.math.Vector;

public class PlaneGeometry extends SurfaceGeometry
{
	public PlaneGeometry( double width, double height, 
		int widthSegments, int heightSegments )
	{
		super( (u,v) -> { return new Vector(u,v,0); },
				 -width/2,  width/2, widthSegments,
				-height/2, height/2, heightSegments  );
	}

	public PlaneGeometry()
	{
		this(1,1, 8,8);
	}
}