package core;

import static org.lwjgl.opengl.GL40.*;
import java.util.Arrays;

public class Uniform<T>
{
	// GLSL types
    // int | bool | float | vec2 | vec3 | vec4 | mat4
    // Java types
    // Integer | Float | Vector | Matrix
    private String dataType;

    // data to be sent to uniform variable
	public T data;

	// store results of generating buffers
    private int[] resultArray = new int[1];

	// reference for variable location in program
	private int variableRef;

	public Uniform(String dataType, T data)
	{
		this.dataType = dataType;
        this.data = data;
	}

	// get and store reference for program variable with given name
	public void locateVariable(int programRef, String variableName)
	{
		variableRef = glGetUniformLocation(programRef, variableName);
	}

	// store data in uniform variable previously located
	public void uploadData()
	{
		// if the program does not reference the variable, then exit
		if (variableRef == -1)
			return;

		if (dataType.equals("int"))
			glUniform1i(variableRef, (Integer)data);
		else if (dataType.equals("bool"))
			glUniform1i(variableRef, (Integer)data);
		else if (dataType.equals("float"))
			glUniform1f(variableRef, (Float)data);
		else if (dataType.equals("vec2"))
		{
			Vector v = (Vector)data;
			glUniform2f(variableRef, v.values[0], v.values[1]);
		}
		else if (dataType.equals("vec3"))
		{
			Vector v = (Vector)data;
			glUniform3f(variableRef, v.values[0], v.values[1], v.values[2]);
		}
		else if (dataType.equals("vec4"))
		{
			Vector v = (Vector)data;
			glUniform4f(variableRef, v.values[0], v.values[1], v.values[2], v.values[3]);
		}
		else if (dataType.equals("mat4"))
		{
			Matrix m = (Matrix)data;
			glUniformMatrix4fv(variableRef, true, m.flatten());
		}

	}
}