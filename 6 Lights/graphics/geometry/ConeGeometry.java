package graphics.geometry;

public class ConeGeometry extends CylindricalGeometry
{
	public ConeGeometry( double radius, double height,
		int radialSegments, int heightSegments, boolean closed )
	{
		super( 0, radius, height,
			radialSegments, heightSegments, false, closed  );
	}

	public ConeGeometry()
	{
		this(1,1, 32,4, true);
	}
}
