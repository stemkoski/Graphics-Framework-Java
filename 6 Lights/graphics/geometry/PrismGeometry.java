package graphics.geometry;

public class PrismGeometry extends CylindricalGeometry
{
	public PrismGeometry( double radius, double height,
		int sides, int heightSegments, boolean closed )
	{
		super( radius, radius, height,
			sides, heightSegments, closed, closed  );
	}

	public PrismGeometry()
	{
		this(1,1, 6,4, true);
	}
}
