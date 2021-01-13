import static org.lwjgl.opengl.GL40.*;

import graphics.core.*;

public class Test_2_2 extends Base
{
    public int programRef;

    public void initialize()
    {       
        // load code, send to GPU, and compile; store program reference
        programRef = OpenGLUtils.initFromFiles(
            "Test_2_2.vert", "Test_2_2.frag" );

        // set up vertex array object
        int vaoRef = glGenVertexArrays();
        glBindVertexArray(vaoRef);

        // render settings (optional)

        // set point width and height
        glPointSize(10);
    }

    public void update()
    {
        // select program to use when rendering
        glUseProgram( programRef );

        // render geometric objects using selected program
        glDrawArrays(GL_POINTS, 0, 1);
    }

    // driver method
    public static void main(String[] args)
    {
        new Test_2_2().run();
    }
}
