package graphics.geometry;

import java.util.Arrays;
import java.util.ArrayList;
import graphics.math.Vector;

public class PolygonGeometry extends Geometry
{
	public PolygonGeometry()
	{
		this(3, 1);
	}

	public PolygonGeometry(int sides, double radius)
	{
		float A = (float)(2 * Math.PI / sides);
		ArrayList<Vector> positionList = new ArrayList<Vector>();
		ArrayList<Vector> colorList    = new ArrayList<Vector>();
		ArrayList<Vector> uvList       = new ArrayList<Vector>();
		Vector positionCenter = new Vector(0,0,0);
		Vector C1 = new Vector(1,1,1);
		Vector C2 = new Vector(1,0,0);
		Vector C3 = new Vector(0,0,1);
		Vector uvCenter = new Vector(0.5, 0.5);
		
		for (int n = 0; n < sides; n++)
		{
			positionList.add( positionCenter );
			positionList.add( 
				new Vector(radius*Math.cos(n*A), radius*Math.sin(n*A), 0) );
			positionList.add( 
				new Vector(radius*Math.cos((n+1)*A), radius*Math.sin((n+1)*A), 0) );
			colorList.add( C1 );
			colorList.add( C2 );
			colorList.add( C3 );
			uvList.add( uvCenter );
			uvList.add( 
				new Vector( Math.cos(n*A)*0.5 + 0.5, Math.sin(n*A)*0.5 + 0.5 ) );
			uvList.add( 
				new Vector( Math.cos((n+1)*A)*0.5 + 0.5, Math.sin((n+1)*A)*0.5 + 0.5 ) );

		}

		float[] positionData = Vector.flattenList(positionList);
		float[] colorData = Vector.flattenList(colorList);
		float[] uvData = Vector.flattenList(uvList);

		addAttribute("vec3", "vertexPosition", positionData);
        addAttribute("vec3", "vertexColor", colorData);
        addAttribute("vec2", "vertexUV", uvData);

		vertexCount = sides * 3;
	}

}
