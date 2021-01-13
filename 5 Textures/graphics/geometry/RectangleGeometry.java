package graphics.geometry;

import java.util.Arrays;
import java.util.List;
import graphics.math.Vector;

public class RectangleGeometry extends Geometry
{
	public RectangleGeometry()
	{
		this(1,1);
	}

	public RectangleGeometry(double width, double height)
	{
		Vector P0 = new Vector(-width/2, -height/2, 0);
		Vector P1 = new Vector( width/2, -height/2, 0);
		Vector P2 = new Vector(-width/2,  height/2, 0);
		Vector P3 = new Vector( width/2,  height/2, 0);

		Vector C0 = new Vector(1f,1f,1f);
		Vector C1 = new Vector(1f,0f,0f);
		Vector C2 = new Vector(0f,1f,0f);
		Vector C3 = new Vector(0f,0f,1f);

		List positionList = Arrays.asList(P0,P1,P3, P0,P3,P2);
		float[] positionData = Vector.flattenList(positionList);

		List colorList = Arrays.asList(C0,C1,C3, C0,C3,C2);
		float[] colorData = Vector.flattenList(colorList);
		
		addAttribute("vec3", "vertexPosition", positionData);
        addAttribute("vec3", "vertexColor", colorData);
		vertexCount = 6;
	}

}