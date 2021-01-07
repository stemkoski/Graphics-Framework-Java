import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL40.*;

import core.*;

// GLFW = Graphics Library FrameWork

public class Test_2_6 extends Base
{
    public int programRef, vaoRef, vertexCount;
    public Uniform translation1, translation2,
                   baseColor1, baseColor2;

    public void initialize()
    {
        // load code, send to GPU, and compile; store program reference
        String vertCode = OpenGLUtils.readFileAsString("Test_2_6.vert");
        String fragCode = OpenGLUtils.readFileAsString("Test_2_6.frag");
        programRef = OpenGLUtils.initializeProgram(vertCode, fragCode);

        // setup vertex array object
        vaoRef = glGenVertexArrays();
        glBindVertexArray(vaoRef);

        float[] positionData = {
                  0.0f,  0.2f, 0.0f,
                  0.2f, -0.2f, 0.0f,
                 -0.2f, -0.2f, 0.0f  };
        vertexCount = positionData.length / 3;
        Attribute positionAttribute = new Attribute( "vec3", positionData );
        positionAttribute.associateVariable( programRef, "position" );

        // set up uniforms
        translation1 = new Uniform("vec3", new float[] {-0.5f, 0.0f, 0.0f} );
        translation1.locateVariable( programRef, "translation" );
        translation2 = new Uniform("vec3", new float[] {0.5f, 0.0f, 0.0f} );
        translation2.locateVariable( programRef, "translation" );
        baseColor1 = new Uniform("vec3", new float[] {1.0f, 0.0f, 0.0f} );
        baseColor1.locateVariable( programRef, "baseColor" );
        baseColor2 = new Uniform("vec3", new float[] {0.0f, 0.0f, 1.0f} );
        baseColor2.locateVariable( programRef, "baseColor" );

    }

    public void update()
    {
        glUseProgram( programRef );
        glBindVertexArray( vaoRef );

        // draw the first triangle
        translation1.uploadData();
        baseColor1.uploadData();
        glDrawArrays( GL_TRIANGLES, 0, vertexCount );

        // draw the second triangle
        translation2.uploadData();
        baseColor2.uploadData();
        glDrawArrays( GL_TRIANGLES, 0, vertexCount );
    }

    // driver method
    public static void main(String[] args)
    {
        new Test_2_6().run();
    }

}
