package graphics.geometry;

import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

import java.io.File;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

import graphics.math.Vector;

public class HeightGeometry extends Geometry
{	
	// this could be useful later
	public double[][] heightData;

	// the resolution of the geometry is equal
	//   to the resolution of the heightmap
	public HeightGeometry(String imageFileName)
	{
		this(imageFileName, -1, 1, 0, 1, -1, 1);
	}

	public HeightGeometry(String imageFileName,
		double xMin, double xMax, 
		double yMin, double yMax, 
		double zMin, double zMax )
	{
		// load image
		File file = new File(imageFileName);
		BufferedImage image = null;
		try
		{ image = ImageIO.read(file); }
    	catch (Exception ex)
    	{ ex.printStackTrace(); }

    	// fill 2D array with image data
    	int imageWidth = image.getWidth();
    	int imageHeight = image.getHeight();
		double[][] heightData = new double[imageWidth][imageHeight];

		// generate position and UV data
		Vector[][] positions = new Vector[imageWidth][imageHeight];
		Vector[][] 		 uvs = new Vector[imageWidth][imageHeight];
		Vector[][] vertexNormals = new Vector[imageWidth][imageHeight];

		double deltaX = (xMax - xMin) / (imageWidth - 1);
        double deltaZ = (zMax - zMin) / (imageHeight - 1);

		for (int i = 0; i < imageWidth; i++)
		{
			for (int j = 0; j < imageHeight; j++)
			{
				int rgb = image.getRGB(i, j);
				// assuming image is grayscale
				//   can use any color component (all equal)
				int red = (rgb >> 16) & 0x000000FF;
				// convert range [0, 255] to [0.0, 1.0]
				double h = red / 255.0;

				heightData[i][j] = h;

				positions[i][j] = new Vector(
					xMin + i*deltaX,
					yMin + h*(yMax-yMin),
					zMin + j*deltaZ );

				uvs[i][j] = new Vector( i/(imageWidth-1.0), 
					                    1 - j/(imageHeight-1.0) );

				vertexNormals[i][j] = new Vector(1,0,0);
			}
		}
		
		// once all position vectors are generated,
		//   generate interior normals in a second pass
		for (int i = 1; i < imageWidth-1; i++)
		{
			for (int j = 1; j < imageHeight-1; j++)
			{
				Vector v = Vector.subtract(positions[i][j-1],  positions[i][j+1]);
				Vector w = Vector.subtract(positions[i-1][j],  positions[i+1][j]);
				Vector n = Vector.cross(v,w);
				n.setLength(1);
				vertexNormals[i][j] = n;
			}
		}

		// group data into triangles
		ArrayList<Vector> positionList     = new ArrayList<Vector>();
		ArrayList<Vector> colorList        = new ArrayList<Vector>();
		ArrayList<Vector> uvList           = new ArrayList<Vector>();
		ArrayList<Vector> vertexNormalList = new ArrayList<Vector>();
		ArrayList<Vector> faceNormalList   = new ArrayList<Vector>();

		for (int i = 0; i < imageWidth-1; i++)
		{
			for (int j = 0; j < imageHeight-1; j++)
			{
                // position coordinates
                Vector pA = positions[i+0][j+0];
                Vector pB = positions[i+1][j+0];
                Vector pD = positions[i+0][j+1];
                Vector pC = positions[i+1][j+1];
                positionList.addAll( Arrays.asList(pA,pB,pC, pA,pC,pD) );

                // uv coordinates
				Vector uvA = uvs[i+0][j+0];
				Vector uvB = uvs[i+1][j+0];
				Vector uvD = uvs[i+0][j+1];
				Vector uvC = uvs[i+1][j+1];
				uvList.addAll( Arrays.asList(uvA,uvB,uvC, uvA,uvC,uvD) );

				
				// vertex normal vectors
				Vector nA = vertexNormals[i+0][j+0];
				Vector nB = vertexNormals[i+1][j+0];
				Vector nD = vertexNormals[i+0][j+1];
				Vector nC = vertexNormals[i+1][j+1];
				vertexNormalList.addAll( Arrays.asList(nA,nB,nC, nA,nC,nD) );
            }
		}

		float[] positionData = Vector.flattenList(positionList);
		float[] uvData = Vector.flattenList(uvList);
		float[] vertexNormalData = Vector.flattenList(vertexNormalList);
		
		addAttribute("vec3", "vertexPosition", positionData);
        addAttribute("vec2", "vertexUV", uvData);
        addAttribute("vec3", "vertexNormal", vertexNormalData);
		
		vertexCount = (imageWidth-1) * (imageHeight-1) * 6;
	}
}
