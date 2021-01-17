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
	public VertexNormalGeometry(Mesh mesh, double size, Vector color, int lineWidth )
	{

		ArrayList<Vector> positionList  	 = new ArrayList<Vector>();
		ArrayList<Vector> colorList   	   	 = new ArrayList<Vector>();

		float[] vertexNormals = mesh.geometry.attributes.get("vertexNormal").dataArray;
		float[] vertexPositions = mesh.geometry.attributes.get("vertexPosition").dataArray;

		//System.out.println(vertexPositions);
		
        for(int i = 0; i < vertexNormals.length; i++)
        {
        	//System.out.println(vertexPositions[i]);

           positionList.add( new Vector(vertexPositions[i]) );
           positionList.add( new Vector(vertexPositions[i] + vertexNormals[i] * size));
        }

        for(int i = 0; i < vertexNormals.length * 2; i++)
        {
            colorList.add( color );
        }

		float[] positionData = Vector.flattenList(positionList);
		float[] colorData = Vector.flattenList(colorList);

		
		addAttribute("vec3", "vertexPosition", positionData);
        addAttribute("vec3", "vertexColor", colorData);
		 
		this.merge(mesh.geometry);
	}
}