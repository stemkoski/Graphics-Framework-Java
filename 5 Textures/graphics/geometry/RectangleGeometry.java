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

		Vector C0 = new Vector(1 ,1, 1);
		Vector C1 = new Vector(1, 0, 0);
		Vector C2 = new Vector(0, 1, 0);
		Vector C3 = new Vector(0, 0, 1);

		// texture coordinates
		Vector T0 = new Vector(0,0);
		Vector T1 = new Vector(1,0);
		Vector T2 = new Vector(0,1);
		Vector T3 = new Vector(1,1);

		List positionList = Arrays.asList(P0,P1,P3, P0,P3,P2);
		float[] positionData = Vector.flattenList(positionList);

		List colorList = Arrays.asList(C0,C1,C3, C0,C3,C2);
		float[] colorData = Vector.flattenList(colorList);
		
		List uvList = Arrays.asList(T0,T1,T3, T0,T3,T2);
		float[] uvData = Vector.flattenList(uvList);

		addAttribute("vec3", "vertexPosition", positionData);
        addAttribute("vec3", "vertexColor", colorData);
		addAttribute("vec2", "vertexUV", uvData);
		
		vertexCount = 6;
	}

}