package core;

import static org.lwjgl.opengl.GL40.*;
import java.util.Arrays;

public class Uniform
{
	// type of data:
    // int | bool | float | vec2 | vec3 | vec4
    private String dataType;

    // data to be sent to uniform variable
	public float[] data;

	// store results of generating buffers
    private int[] resultArray = new int[1];

	// reference for variable location in program
	private int variableRef;

	public Uniform(String dataType, float[] data)
	{
		this.dataType = dataType;
        this.data = data;
	}

	// get and store reference for program variable with given name
	public void locateVariable(int programRef, String variableName)
	{
		this.variableRef = glGetUniformLocation(programRef, variableName);
	}

	// store data in uniform variable previously located
	public void uploadData()
	{
		// if the program does not reference the variable, then exit
		if (this.variableRef == -1)
			return;

		if (this.dataType.equals("int"))
			glUniform1i(this.variableRef, (int)this.data[0]);
		else if (this.dataType.equals("bool"))
			glUniform1i(this.variableRef, (int)this.data[0]);
		else if (this.dataType.equals("float"))
			glUniform1f(this.variableRef, this.data[0]);
		else if (this.dataType.equals("vec2"))
			glUniform2f(this.variableRef, this.data[0], this.data[1]);
		else if (this.dataType.equals("vec3"))
			glUniform3f(this.variableRef, this.data[0], this.data[1], this.data[2]);
		else if (this.dataType.equals("vec4"))
			glUniform4f(this.variableRef, this.data[0], this.data[1], this.data[2], this.data[3]);
	}
}