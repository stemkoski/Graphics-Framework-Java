package graphics.geometry;

public class PyramidGeometry extends CylindricalGeometry
{
	public PyramidGeometry( double radius, double height,
		int sides, int heightSegments, boolean closed )
	{
		super( 0, radius, height,
			sides, heightSegments, false, closed  );
	}

	public PyramidGeometry()
	{
		this(1,1, 4,4, true);
	}
}
