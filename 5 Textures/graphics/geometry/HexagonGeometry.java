package graphics.geometry;

public class HexagonGeometry extends PolygonGeometry
{
	public HexagonGeometry()
	{
		super(6, 1);
	}

	public HexagonGeometry(float radius)
	{
		super(6, radius);
	}
}