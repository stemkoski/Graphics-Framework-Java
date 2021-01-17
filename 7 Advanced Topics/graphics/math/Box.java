package graphics.math;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

public class Box extends Geometry
{
    public Box(Mesh mesh, Vector min, Vector max, Vector color, double lineWidth)
    {
        Geometry geo = new Geometry();

        double xMin = min[0];
        double yMin = min[1];
        double zMin = min[2];

        double xMax = max[0];
        double yMax = max[1];
        double zMax = max[2];

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

        geo.addAttribute("vec3", "vertexPosition", positionData);
        geo.addAttribute("vec3", "vertexColor", colorData);
        vertexCount = 36;
        
        geo.merge(mesh.geometry);
    }
}