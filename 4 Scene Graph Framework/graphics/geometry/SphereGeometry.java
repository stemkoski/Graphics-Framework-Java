package graphics.geometry;

public class SphereGeometry extends EllipsoidGeometry
{
	public SphereGeometry( double radius, 
		int radiusSegments, int heightSegments )
	{
		super( 2*radius, 2*radius, 2*radius,
			   radiusSegments, heightSegments  );
	}

	public SphereGeometry( double radius )
	{
		this(radius, 32,16);
	}

	public SphereGeometry()
	{
		this(1, 32,16);
	}
}
