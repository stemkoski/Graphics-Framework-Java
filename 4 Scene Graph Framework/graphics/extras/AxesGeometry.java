package graphics.extras;

import graphics.geometry.Geometry;
import graphics.math.Vector;
import java.util.Arrays;
import java.util.List;

public class AxesGeometry extends Geometry
{
	public AxesGeometry(double axisLength)
	{
		List positionList = Arrays.asList(
			new Vector(0,0,0), new Vector(axisLength,0,0),
			new Vector(0,0,0), new Vector(0,axisLength,0),
			new Vector(0,0,0), new Vector(0,0,axisLength)  );
		float[] positionData = Vector.flattenList(positionList);

		List colorList = Arrays.asList(
			new Vector(0.5,0,0), new Vector(1,0.5,0.5),
			new Vector(0,0.5,0), new Vector(0.5,1,0.5),
			new Vector(0,0,0.5), new Vector(0.5,0.5,1)  );
		float[] colorData = Vector.flattenList(colorList);
		
		addAttribute("vec3", "vertexPosition", positionData);
        addAttribute("vec3", "vertexColor", colorData);
		vertexCount = 6;
	}

	public AxesGeometry()
	{
		this(1);
	}
}
