package graphics.geometry;

import java.util.HashMap;
import graphics.core.Attribute;

import java.util.List;
import java.util.ArrayList;
import graphics.math.Vector;
import graphics.math.Matrix;

public class Geometry
{
	// Store Attribute objects,
	// indexed by name of associated variable in shader.
	// Shader variable associations set up later
	// and stored in vertex array object in Mesh.
	public HashMap<String, Attribute> attributes;
	public int vertexCount;

	public Geometry()
	{
		attributes = new HashMap<String, Attribute>();
		vertexCount = -1;
	}
	
	public void addAttribute(String dataType, String variableName, float[] dataArray)
	{
		attributes.put(variableName, new Attribute(dataType, dataArray));
	}

	// transform vertex position data using a matrix
	public void applyMatrix(Matrix matrix)
	{
		float[] oldPositionData = attributes.get("vertexPosition").dataArray;
		// convert flattened array back into list of vectors
		List<Vector> oldPositionList 
			= Vector.unflattenList(oldPositionData, 3);
		List<Vector> newPositionList = new ArrayList<Vector>();
		for (Vector oldPos : oldPositionList)
		{
			// add homogeneous fourth coordinate
			oldPos.resize(4);
			oldPos.values[3] = 1;
			// multiply by matrix
			Vector newPos = matrix.multiplyVector(oldPos);
			// remove homogeneous coordinate
			newPos.resize(3);
			// add to new data list
			newPositionList.add( newPos );
		}
		float[] newPositionData = Vector.flattenList(newPositionList);
		attributes.get("vertexPosition").dataArray = newPositionData;
		// new data must be uploaded
		attributes.get("vertexPosition").uploadData();
	}

	// merge data from attributes of other geometry into this object;
	//   requires both geometries to have attributes with same names
	public void merge(Geometry other)
	{
		for ( String variableName : attributes.keySet() )
		{
			// merge two arrays
			float[] data1 =  this.attributes.get(variableName).dataArray;
			float[] data2 = other.attributes.get(variableName).dataArray;
			float[] data3 = new float[data1.length + data2.length];
			for (int i = 0; i < data3.length; i++)
				if (i < data1.length)
					data3[i] = data1[i];
				else
					data3[i] = data2[i - data1.length];

			// new data must be set and uploaded
			this.attributes.get(variableName).dataArray = data3;
			this.attributes.get(variableName).uploadData();
		}
		// update the number of vertices
		this.vertexCount = this.vertexCount + other.vertexCount;
	}
}