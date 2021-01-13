package graphics.geometry;

public class CylinderGeometry extends CylindricalGeometry
{
	public CylinderGeometry( double radius, double height,
		int radialSegments, int heightSegments, boolean closed )
	{
		super( radius, radius, height,
			radialSegments, heightSegments, closed, closed  );
	}

	public CylinderGeometry()
	{
		this(1,1, 32,4, true);
	}
}
