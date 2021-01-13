import static org.lwjgl.opengl.GL40.*;

import graphics.core.*;

public class Test_2_5 extends Base
{
    public int programRef, vaoRef;

    public void initialize()
    {
        // load code, send to GPU, and compile; store program reference
        programRef = OpenGLUtils.initFromFiles(
            "Test_2_5.vert", "Test_2_5.frag" );

        // render settings (optional)

        // set line width
        glPointSize(10);
        glLineWidth(4);

        // setup vertex array object
        vaoRef = glGenVertexArrays();
        glBindVertexArray(vaoRef);

        float[] positionData = {
                  0.8f,  0.0f, 0.0f,
                  0.4f,  0.6f, 0.0f,
                 -0.4f,  0.6f, 0.0f,
                 -0.8f,  0.0f, 0.0f,
                 -0.4f, -0.6f, 0.0f,
                  0.4f, -0.6f, 0.0f  };
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
        glDrawArrays( GL_LINE_LOOP , 0 , 6 );
    }

    // driver method
    public static void main(String[] args)
    {
        new Test_2_5().run();
    }

}
