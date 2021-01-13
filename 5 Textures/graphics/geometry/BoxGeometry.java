package graphics.geometry;

import java.util.Arrays;
import java.util.List;
import graphics.math.Vector;

public class BoxGeometry extends Geometry
{
	public BoxGeometry()
	{
		this(1,1,1);
	}

	public BoxGeometry(double width, double height, double depth)
	{
		// corners of a cube
		Vector P0 = new Vector(-width/2, -height/2, -depth/2);
		Vector P1 = new Vector( width/2, -height/2, -depth/2);
		Vector P2 = new Vector(-width/2,  height/2, -depth/2);
		Vector P3 = new Vector( width/2,  height/2, -depth/2);
		Vector P4 = new Vector(-width/2, -height/2,  depth/2);
		Vector P5 = new Vector( width/2, -height/2,  depth/2);
		Vector P6 = new Vector(-width/2,  height/2,  depth/2);
		Vector P7 = new Vector( width/2,  height/2,  depth/2);

		// colors for faces in order: x+, x-, y+, y-, z+, z-
		Vector C1 = new Vector(1.0f, 0.5f, 0.5f);
		Vector C2 = new Vector(0.5f, 0.0f, 0.0f);
		Vector C3 = new Vector(0.5f, 1.0f, 0.5f);
		Vector C4 = new Vector(0.0f, 0.5f, 0.0f);
		Vector C5 = new Vector(0.5f, 0.5f, 1.0f);
		Vector C6 = new Vector(0.0f, 0.0f, 0.5f);

		// texture coordinates
		Vector T0 = new Vector(0,0);
		Vector T1 = new Vector(1,0);
		Vector T2 = new Vector(0,1);
		Vector T3 = new Vector(1,1);

		List positionList = Arrays.asList(
			P5,P1,P3,P5,P3,P7, P0,P4,P6,P0,P6,P2,
			P6,P7,P3,P6,P3,P2, P0,P1,P5,P0,P5,P4,
			P4,P5,P7,P4,P7,P6, P1,P0,P2,P1,P2,P3  );
		float[] positionData = Vector.flattenList(positionList);

		List colorList = Arrays.asList(
			C1,C1,C1,C1,C1,C1, C2,C2,C2,C2,C2,C2,
			C3,C3,C3,C3,C3,C3, C4,C4,C4,C4,C4,C4,
			C5,C5,C5,C5,C5,C5, C6,C6,C6,C6,C6,C6  );
		float[] colorData = Vector.flattenList(colorList);
		
		List uvList = Arrays.asList(
			T0,T1,T3,T0,T3,T2, T0,T1,T3,T0,T3,T2,
			T0,T1,T3,T0,T3,T2, T0,T1,T3,T0,T3,T2,
			T0,T1,T3,T0,T3,T2, T0,T1,T3,T0,T3,T2  );
		float[] uvData = Vector.flattenList(uvList);

		addAttribute("vec3", "vertexPosition", positionData);
        addAttribute("vec3", "vertexColor", colorData);
        addAttribute("vec2", "vertexUV", uvData);
		vertexCount = 36;
	}

}