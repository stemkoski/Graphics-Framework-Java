package graphics.geometry;

import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import graphics.math.Vector;
import graphics.math.Surface;

public class SurfaceGeometry extends Geometry
{
	public SurfaceGeometry( Surface.Function function,
		double uStart, double uEnd, int uResolution, 
    	double vStart, double vEnd, int vResolution  )
	{
		Surface surface = new Surface(function);

		Vector[][] positions = surface.getPoints(
			uStart, uEnd, uResolution, vStart, vEnd, vResolution);

		Vector[][] uvs = surface.getUVs(
			uStart, uEnd, uResolution, vStart, vEnd, vResolution);

		List<Vector> quadColors = Arrays.asList(
			new Vector(1,0,0), new Vector(0,1,0), new Vector(0,0,1),
			new Vector(0,1,1), new Vector(1,0,1), new Vector(1,1,0) );

		Vector[][] vertexNormals = surface.getNormals(
			uStart, uEnd, uResolution, vStart, vEnd, vResolution);

		ArrayList<Vector> positionList     = new ArrayList<Vector>();
		ArrayList<Vector> colorList        = new ArrayList<Vector>();
		ArrayList<Vector> uvList           = new ArrayList<Vector>();
		ArrayList<Vector> vertexNormalList = new ArrayList<Vector>();
		ArrayList<Vector> faceNormalList   = new ArrayList<Vector>();


		for (int uIndex=0; uIndex<uResolution; uIndex++)
		{
			for (int vIndex=0; vIndex<vResolution; vIndex++)
			{
                // position coordinates
                Vector pA = positions[uIndex+0][vIndex+0];
                Vector pB = positions[uIndex+1][vIndex+0];
                Vector pD = positions[uIndex+0][vIndex+1];
                Vector pC = positions[uIndex+1][vIndex+1];
                positionList.addAll( Arrays.asList(pA,pB,pC, pA,pC,pD) );

                colorList.addAll(quadColors);

                // uv coordinates
				Vector uvA = uvs[uIndex+0][vIndex+0];
				Vector uvB = uvs[uIndex+1][vIndex+0];
				Vector uvD = uvs[uIndex+0][vIndex+1];
				Vector uvC = uvs[uIndex+1][vIndex+1];
				uvList.addAll( Arrays.asList(uvA,uvB,uvC, uvA,uvC,uvD) );

				// vertex normal vectors
				Vector nA = vertexNormals[uIndex+0][vIndex+0];
				Vector nB = vertexNormals[uIndex+1][vIndex+0];
				Vector nD = vertexNormals[uIndex+0][vIndex+1];
				Vector nC = vertexNormals[uIndex+1][vIndex+1];
				vertexNormalList.addAll( Arrays.asList(nA,nB,nC, nA,nC,nD) );

				// face normal vectors
				Vector fn0 = Vector.calcNormal(pA, pB, pC);
				Vector fn1 = Vector.calcNormal(pA, pC, pD);
				faceNormalList.addAll( Arrays.asList(fn0,fn0,fn0, fn1,fn1,fn1) );
            }
		}

		float[] positionData = Vector.flattenList(positionList);
		float[] colorData = Vector.flattenList(colorList);
		float[] uvData = Vector.flattenList(uvList);
		float[] vertexNormalData = Vector.flattenList(vertexNormalList);
		float[] faceNormalData = Vector.flattenList(faceNormalList);
		
		addAttribute("vec3", "vertexPosition", positionData);
        addAttribute("vec3", "vertexColor", colorData);
        addAttribute("vec2", "vertexUV", uvData);
        addAttribute("vec3", "vertexNormal", vertexNormalData);
		addAttribute("vec3", "faceNormal", faceNormalData);

		vertexCount = uResolution * vResolution * 6;
	}
}
