package core;

import static org.lwjgl.opengl.GL40.*;

public class Attribute
{
    // type of elements in data array:
    // int | float | vec2 | vec3 | vec4
    private String dataType;

    // *flattened* array of data to be stored in buffer
    public float[] dataArray;

    // store results of generating buffers
    private int[] resultArray = new int[1];

    // reference of available buffer from GPU
    private int bufferRef;

    public Attribute(String dataType, float[] dataArray)
    {
        this.dataType = dataType;
        this.dataArray = dataArray;
        // returns a single buffer reference
        bufferRef = glGenBuffers();
        // upload data immediately
        uploadData();
    }

    // store this data in a GPU buffer
    public void uploadData()
    {
        // select buffer used by the following functions
        glBindBuffer(GL_ARRAY_BUFFER, bufferRef);

        // store data in currently bound buffer
        glBufferData(GL_ARRAY_BUFFER, dataArray, GL_STATIC_DRAW);
    }

    // associate variable in program with this buffer
    public void associateVariable(int programRef, String variableName)
    {
        // get reference for program variable with given name
        int variableRef = glGetAttribLocation(programRef, variableName);

        // if the program does not reference the variable, then exit
        if (variableRef == -1)
            return;

        // select buffer used by the following functions
        glBindBuffer(GL_ARRAY_BUFFER, bufferRef);

        // specify how data will be read
        //   from the currently bound buffer into the specified variable
        if ( dataType.equals("int") )
            glVertexAttribPointer(variableRef, 1, GL_INT, false, 0, 0);
        else if ( dataType.equals("float") )
            glVertexAttribPointer(variableRef, 1, GL_FLOAT, false, 0, 0);
        else if ( dataType.equals("vec2") )
            glVertexAttribPointer(variableRef, 2, GL_FLOAT, false, 0, 0);
        else if ( dataType.equals("vec3") )
            glVertexAttribPointer(variableRef, 3, GL_FLOAT, false, 0, 0);
        else if ( dataType.equals("vec4") )
            glVertexAttribPointer(variableRef, 4, GL_FLOAT, false, 0, 0);
        else
            throw new RuntimeException ("Attribute " + variableName + " has unknown type " + dataType);

        // indicate that data will be streamed to this variable from a buffer
        glEnableVertexAttribArray(variableRef);
    }
}
