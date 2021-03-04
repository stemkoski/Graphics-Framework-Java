package graphics.extras;

import graphics.geometry.Geometry;
import graphics.math.Vector;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.io.File;
import java.util.Scanner;


public class OBJGeometry extends Geometry
{
	public OBJGeometry(String fileName)
	{

		List<Vector> points             = new ArrayList<Vector>();
		List<Vector> uvs                = new ArrayList<Vector>(); 		          
		List<Vector> normals            = new ArrayList<Vector>();

    List<Vector> vertexPositionList   = new ArrayList<Vector>();
		List<Vector> vertexUVList    	    = new ArrayList<Vector>();
		List<Vector> vertexNormalList     = new ArrayList<Vector>();

		List<String> dataArray  		      = new ArrayList<String>();

		File file = new File(fileName);
    Scanner scan = null;

    try
    {
      scan = new Scanner(file); 
    }
    catch (Exception ex)
    {
      ex.printStackTrace();
    }

    while (scan.hasNextLine()) 
    {
      dataArray.add(scan.nextLine());
    }
    scan.close();

    for (String line : dataArray)
    {
      if (!line.startsWith("v") || !line.startsWith("vt") ||
        	!line.startsWith("vn") || !line.startsWith("f") )
      continue;
      else
      {
        String[] d = line.split(" ");

       	if (d[0] == "v")

          points.add(  new Vector( Float.parseFloat(d[1]), 
                                   Float.parseFloat(d[2]),
                                   Float.parseFloat(d[3]) ) ); 
        
        if (d[0] == "vt")

          uvs.add(     new Vector( Float.parseFloat(d[1]), 
                                   Float.parseFloat(d[2]) ) ); 

       	if (d[0] == "vn")

          normals.add( new Vector( Float.parseFloat(d[1]), 
                                   Float.parseFloat(d[2]),
                                   Float.parseFloat(d[3]) ) ); 

        if (d[0] == "f") 
        {
          for (int i = 1; i < 4; i++)
          {
            String triangles = d[i];
            String[] indices = triangles.split("/");

            Vector P1 =  points.get( Integer.parseInt(indices[0]) - 1);
            Vector P2 =     uvs.get( Integer.parseInt(indices[1]) - 1);
            Vector P3 = normals.get( Integer.parseInt(indices[2]) - 1);

      			vertexPositionList.add( new Vector(  P1.values[0], P1.values[1], P1.values[2] ) );
            vertexUVList.add(       new Vector(  P2.values[0], P2.values[1]               ) );
            vertexNormalList.add(   new Vector(  P3.values[0], P3.values[1], P3.values[2] ) );
          }
        }
      }
    }

    float[] vertexPositionData 		 = Vector.flattenList(vertexPositionList);
		float[] vertexUVData 			     = Vector.flattenList(vertexUVList);
		float[] vertexNormalData 		   = Vector.flattenList(vertexNormalList);

		addAttribute("vec3", "vertexPosition", vertexPositionData);
    addAttribute("vec2", "vertexUV", vertexUVData);
    addAttribute("vec3", "vertexNormal", vertexNormalData);

		vertexCount = vertexPositionList.size();
	}
}
