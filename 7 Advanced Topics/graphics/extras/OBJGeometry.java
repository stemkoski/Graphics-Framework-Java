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
		super();

		List<Vector> pts  				 = new ArrayList<Vector>();
		List<Vector> uvs    	 		 = new ArrayList<Vector>();
		List<Vector> norms    			 = new ArrayList<Vector>();

		List<Vector> vertexPositionList  = new ArrayList<Vector>();
		List<Vector> vertexUVList    	 = new ArrayList<Vector>();
		List<Vector> vertexNormalList    = new ArrayList<Vector>();

		List<String> dataArray  		 = new ArrayList<String>();

		File file = new File(fileName);

        Scanner sc = new Scanner(file);

        while (sc.hasNextLine()) {
           dataArray.add(sc.nextLine());
        }
        sc.close();

        for (String line : dataArray)
        {
        	if (!line.startsWith("v") || !line.startsWith("vt") ||
        	 	!line.startsWith("vn") || !line.startsWith("f") )
        		continue;
        	else
        	{
        		String[] d = line.split(" ");

        		if (d[0] == "v")
                	  pts.addAll(Arrays.asList(d[1], d[2], d[3]));
            	if (d[0] == "vt")
                	  uvs.addAll(Arrays.asList(d[1], d[2]));
            	if (d[0] == "vn")
                	norms.addAll(Arrays.asList(d[1], d[2], d[3]));
            	if (d[0] == "f") 
            	{
            		for (int i = 1; i < 4; i++)
            		{
            			String[] triangles = d[i];
            			String[] faces = triangles.split("/");

            			pts 	= vertexPositionList.get(faces[0]-1);
            			uvs 	= 		vertexUVList.get(faces[1]-1);
            			norms 	=   vertexNormalList.get(faces[2]-1);

            			vertexPositionList.add( new Vector(   pts[0],   pts[1],   pts[2] ) );
                    		  vertexUVList.add( new Vector(   uvs[0],   uvs[1]           ) );
                    	  vertexNormalData.add( new Vector( norms[0], norms[1], norms[2] ) );
                    }
            	}
        	}
        }

        float[] vertexPositionData 		 = Vector.flattenList(vertexPositionList);
		float[] vertexUVData 			 = Vector.flattenList(vertexUVList);
		float[] vertexNormalData 		 = Vector.flattenList(vertexNormalList);

		addAttribute("vec3", "vertexPosition", vertexPositionData);
        addAttribute("vec2", "vertexUV", vertexUVData);
        addAttribute("vec3", "vertexNormal", vertexNormalData);
		vertexCount = vertexPositionList.size();
	}
}
