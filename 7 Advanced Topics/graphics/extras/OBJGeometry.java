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

		List<String> pts  				 = new ArrayList<String>();
		List<String> uvs    	 		 = new ArrayList<String>();
		List<String> norms    			 = new ArrayList<String>();

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

        		if (d[0] == "v"){
                	  pts.add(d[1]);
                      pts.add(d[2]);
                      pts.add(d[3]);
                    }
            	if (d[0] == "vt"){
                	  uvs.add(d[1]);
                      uvs.add(d[2]);
                    }
            	if (d[0] == "vn"){
                      norms.add(d[1]);
                      norms.add(d[2]);
                      norms.add(d[3]);
                    }
            	if (d[0] == "f") 
            	{
            		for (int i = 1; i < 4; i++)
            		{
            			String triangles = d[i];
            			String[] faces = triangles.split("/");

            			pts 	= vertexPositionList.get(Integer.parseInt(faces[0])-1);
            			uvs 	= 		vertexUVList.get(Integer.parseInt(faces[1])-1);
            			norms 	=   vertexNormalList.get(Integer.parseInt(faces[2])-1);

            			vertexPositionList.add( new Vector(   Double.parseDouble(pts.get(0)),   
                                                              Double.parseDouble(pts.get(1)),   
                                                              Double.parseDouble(pts.get(2)) ) );

                    		  vertexUVList.add( new Vector(   Double.parseDouble(uvs.get(0)),   
                                                              Double.parseDouble(uvs.get(1)) ) );

                    	  vertexNormalList.add( new Vector(   Double.parseDouble(norms.get(0)), 
                                                              Double.parseDouble(norms.get(1)), 
                                                              Double.parseDouble(norms.get(2)) ) );
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
