import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL40.*;

import core.*;

// GLFW = Graphics Library FrameWork

public class Test_2_4 extends Base
{
    public int programRef, 
        vaoTri, vertexCountTri,
        vaoSquare, vertexCountSquare;

    public void initialize()
    {
        // load code, send to GPU, and compile; store program reference
        String vertCode = OpenGLUtils.readFileAsString("Test_2_3.vert");
        String fragCode = OpenGLUtils.readFileAsString("Test_2_3.frag");
        programRef = OpenGLUtils.initializeProgram(vertCode, fragCode);

        // render settings (optional)

        // set line width
        glLineWidth(4);

        // setup vertex array object: triangle
        vaoTri = glGenVertexArrays();
        glBindVertexArray(vaoTri);

        float[] positionDataTri = {
                 -0.5f, 0.8f, 0.0f,
                 -0.2f, 0.2f, 0.0f,
                 -0.8f, 0.2f, 0.0f  };
        vertexCountTri = positionDataTri.length / 3;
        Attribute positionAttributeTri = new Attribute( "vec3", positionDataTri );
        positionAttributeTri.associateVariable( programRef, "position" );

        // setup vertex array object: square
        vaoSquare = glGenVertexArrays();
        glBindVertexArray(vaoSquare);

        float[] positionDataSquare = {
                 0.8f, 0.8f, 0.0f,
                 0.8f, 0.2f, 0.0f,
                 0.2f, 0.2f, 0.0f,
                 0.2f, 0.8f, 0.0f  };
        vertexCountSquare = positionDataSquare.length / 3;
        Attribute positionAttributeSquare = new Attribute( "vec3", positionDataSquare );
        positionAttributeSquare.associateVariable( programRef, "position" );
    }

    public void update()
    {
        // using same program to render both shapes
        glUseProgram( programRef );

        // draw the triangle
        glBindVertexArray( vaoTri );
        glDrawArrays( GL_LINE_LOOP , 0 , vertexCountTri );

        // draw the square
        glBindVertexArray( vaoSquare );
        glDrawArrays( GL_LINE_LOOP , 0 , vertexCountSquare );
    }

    // driver method
    public static void main(String[] args)
    {
        new Test_2_4().run();
    }

}
