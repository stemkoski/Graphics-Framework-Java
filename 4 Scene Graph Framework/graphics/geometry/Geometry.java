package graphics.geometry;

import java.util.HashMap;
import graphics.core.Attribute;

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

	/*
	public void countVertices()
	{
		// number of vertices may be calculated from
		//   the length of any Attribute object's array of data -- divided by something...
		//for ()
		//attrib = list( self.attributes.values() )[0]
		//vertexCount = len( attrib.data )
	}
	*/
}