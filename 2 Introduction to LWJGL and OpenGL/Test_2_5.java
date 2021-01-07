import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL40.*;

import core.*;

// GLFW = Graphics Library FrameWork

public class Test_2_5 extends Base
{
    public int programRef, vaoRef, vertexCount;

    public void initialize()
    {
        // load code, send to GPU, and compile; store program reference
        String vertCode = OpenGLUtils.readFileAsString("Test_2_5.vert");
        String fragCode = OpenGLUtils.readFileAsString("Test_2_5.frag");
        programRef = OpenGLUtils.initializeProgram(vertCode, fragCode);

        // render settings (optional)

        // set line width
        glPointSize(10);
        glLineWidth(4);

        // setup vertex array object: triangle
        vaoRef = glGenVertexArrays();
        glBindVertexArray(vaoRef);

        float[] positionData = {
                  0.8f,  0.0f, 0.0f,
                  0.4f,  0.6f, 0.0f,
                 -0.4f,  0.6f, 0.0f,
                 -0.8f,  0.0f, 0.0f,
                 -0.4f, -0.6f, 0.0f,
                  0.4f, -0.6f, 0.0f  };
        vertexCount = positionData.length / 3;
        Attribute positionAttribute = new Attribute( "vec3", positionData );
        positionAttribute.associateVariable( programRef, "position" );

        float[] colorData = {
                 1.0f, 0.0f, 0.0f,
                 1.0f, 0.5f, 0.0f,
                 1.0f, 1.0f, 0.0f,
                 0.0f, 1.0f, 0.0f,
                 0.0f, 0.0f, 1.0f,
                 0.5f, 0.0f, 1.0f  };
        Attribute colorAttribute = new Attribute( "vec3", colorData );
        colorAttribute.associateVariable( programRef, "vertexColor" );
    }

    public void update()
    {
        glUseProgram( programRef );

        // draw the object
        glBindVertexArray( vaoRef );
        glDrawArrays( GL_LINE_LOOP , 0 , vertexCount );
    }

    // driver method
    public static void main(String[] args)
    {
        new Test_2_5().run();
    }

}
