package graphics.extras;

import graphics.geometry.Geometry;
import graphics.math.Vector;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

public class GridGeometry extends Geometry
{
	public GridGeometry(double size, int divisions, 
		Vector gridColor, Vector centerColor )
	{
		double deltaSize = size / divisions;

		List<Double> values = new ArrayList<Double>();
		for (int n = 0; n < divisions+1; n++)
			values.add( -size/2 + n * deltaSize );

		List<Vector> positionList = new ArrayList<Vector>();
		List<Vector> colorList    = new ArrayList<Vector>();

		// add vertical lines
		for (Double x : values)
		{
			positionList.add( new Vector(x, -size/2, 0) );
			positionList.add( new Vector(x,  size/2, 0) );
			if (x == 0)
			{
				colorList.add(centerColor);
				colorList.add(centerColor);
			}
			else
			{
				colorList.add(gridColor);
				colorList.add(gridColor);
			}
		}

		// add horizontal lines
		for (Double y : values)
		{
			positionList.add( new Vector(-size/2, y, 0) );
			positionList.add( new Vector( size/2, y, 0) );
			if (y == 0)
			{
				colorList.add(centerColor);
				colorList.add(centerColor);
			}
			else
			{
				colorList.add(gridColor);
				colorList.add(gridColor);
			}
		}

		float[] positionData = Vector.flattenList(positionList);
		float[] colorData = Vector.flattenList(colorList);
		
		addAttribute("vec3", "vertexPosition", positionData);
        addAttribute("vec3", "vertexColor", colorData);
		vertexCount = positionList.size();
	}

	public GridGeometry()
	{
		this(10,10, new Vector(0.5,0.5,0.5), new Vector(0.8,0.8,0.8));
	}
}
