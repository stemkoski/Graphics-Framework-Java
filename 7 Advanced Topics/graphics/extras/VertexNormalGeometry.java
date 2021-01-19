package graphics.extras;

import graphics.core.Mesh;
import graphics.math.Vector;
import graphics.material.LineMaterial;
import graphics.geometry.Geometry;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

public class VertexNormalGeometry extends Geometry
{
	public VertexNormalGeometry( Mesh mesh, double lineLength )
	{

		ArrayList<Vector> helperPositionList = new ArrayList<Vector>();
		ArrayList<Vector> helperColorList 	 = new ArrayList<Vector>();

		// dark to light gradient
		Vector C0 = new Vector(0.1, 0.1, 0.1);
		Vector C1 = new Vector(1, 1, 1);

		float[] vertexPositionData = mesh.geometry.attributes.get("vertexPosition").dataArray;
		float[] vertexNormalData   = mesh.geometry.attributes.get("vertexNormal").dataArray;
		
		List<Vector> vertexPositionList = Vector.unflattenList( vertexPositionData, 3 );
		List<Vector> vertexNormalList   = Vector.unflattenList( vertexNormalData, 3 );
		
        for( int i = 0; i < vertexNormalList.size(); i++ )
        {
        	Vector p = vertexPositionList.get(i);
        	Vector n = vertexNormalList.get(i);
        	n.setLength(lineLength);
        	Vector q = Vector.add(p,n);

			helperPositionList.add( p );
            helperPositionList.add( q );
            helperColorList.add( C0 );
            helperColorList.add( C1 );
        }

		float[] positionData = Vector.flattenList(helperPositionList);
		float[] colorData = Vector.flattenList(helperColorList);

		addAttribute("vec3", "vertexPosition", positionData);
        addAttribute("vec3", "vertexColor", colorData);

        vertexCount = helperPositionList.size();

        System.out.println( vertexCount );
	}
}