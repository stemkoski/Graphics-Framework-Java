import static org.lwjgl.opengl.GL40.*;

import graphics.core.*;
import graphics.math.Vector;

public class Test_2_8 extends Base
{
    public int programRef, vaoRef;
    public Uniform<Vector> translation, baseColor;

    public void initialize()
    {
        // load code, send to GPU, and compile; store program reference
        programRef = OpenGLUtils.initFromFiles(
            "Test_2_6.vert", "Test_2_6.frag" );

        // specify color used when clearing the screen
        glClearColor(0.0f, 0.0f, 0.0f, 1.0f);

        // setup vertex array object
        vaoRef = glGenVertexArrays();
        glBindVertexArray(vaoRef);

        float[] positionData = {
                  0.0f,  0.2f, 0.0f,
                  0.2f, -0.2f, 0.0f,
                 -0.2f, -0.2f, 0.0f  };
        Attribute positionAttribute = new Attribute( "vec3", positionData );
        positionAttribute.associateVariable( programRef, "position" );

        // set up uniforms
        translation = new Uniform<Vector>("vec3", new Vector(-0.5f, 0.0f, 0.0f) );
        translation.locateVariable( programRef, "translation" );
        baseColor = new Uniform<Vector>("vec3", new Vector(1.0f, 0.0f, 0.0f) );
        baseColor.locateVariable( programRef, "baseColor" );

    }

    public void update()
    {

        // update data
        // move in circular pattern
        translation.data.values[0] = (float)(0.75 * Math.cos(time));
        translation.data.values[1] = (float)(0.75 * Math.sin(time));

        // render scene

        // reset color buffer with specified color
        glClear(GL_COLOR_BUFFER_BIT);

        glUseProgram( programRef );
        glBindVertexArray( vaoRef );
        translation.uploadData();
        baseColor.uploadData();
        glDrawArrays( GL_TRIANGLES, 0, 3 );

    }

    // driver method
    public static void main(String[] args)
    {
        new Test_2_8().run();
    }

}
