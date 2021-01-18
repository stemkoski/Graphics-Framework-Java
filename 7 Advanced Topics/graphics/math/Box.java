package graphics.math;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import graphics.geometry.Geometry;
import graphics.core.Mesh;

public class Box extends Geometry
{
    public Box(Vector min, Vector max, Vector color, double lineWidth)
    {
        
        double xMin = min.values[0];
        double yMin = min.values[1];
        double zMin = min.values[2];

        double xMax = max.values[0];
        double yMax = max.values[1];
        double zMax = max.values[2];

        Vector C1 = color;

        Vector P0 = min;
        Vector P1 = new Vector(xMax, yMin, zMin);
        Vector P2 = new Vector(xMin, yMax, zMin);
        Vector P3 = new Vector(xMax, yMax, zMin);
        Vector P4 = new Vector(xMin, yMin, zMax);
        Vector P5 = new Vector(xMax, yMin, zMax);
        Vector P6 = new Vector(xMin, yMax, zMax);
        Vector P7 = max;

        List positionList = Arrays.asList( P0, P2, P2, P3, P3, P1, P1, P0,
                                           P4, P6, P6, P7, P7, P5, P5, P4,
                                           P2, P6, P3, P7, P4, P0, P5, P1 );
        

        List colorList = Arrays.asList( C1,C1,C1,C1,C1,C1, C1,C1,C1,C1,C1,C1,
                                        C1,C1,C1,C1,C1,C1, C1,C1,C1,C1,C1,C1,
                                        C1,C1,C1,C1,C1,C1, C1,C1,C1,C1,C1,C1 );


        float[] positionData = Vector.flattenList(positionList);
        float[] colorData = Vector.flattenList(colorList);

        addAttribute("vec3", "vertexPosition", positionData);
        addAttribute("vec3", "vertexColor", colorData);
        vertexCount = 36;

    }
}