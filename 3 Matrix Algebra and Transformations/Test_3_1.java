import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL40.*;

import core.*;

/* will use for testing Matrix class */
public class Test_3_1 extends Base
{
    public int programRef, vaoRef, vertexCount;
    public Uniform translation, baseColor;
    public float speed = 0.5f;

    public void initialize()
    {
        String s = String.format("%6.2f", 1.1);
        System.out.println(s);

        Matrix m = new Matrix(3,3);
        System.out.println(m);
        
        Matrix n = new Matrix(2,3);
        n.setValues(1,2,3,4,5,6);
        System.out.println(n);

        Matrix p = new Matrix(2,2);
        p.setValues(1,2,3,4);
        System.out.println(p);

        Matrix q = Matrix.multiply(p, n);
        System.out.println(q);


        /*
        // load code, send to GPU, and compile; store program reference
        String vertCode = OpenGLUtils.readFileAsString("Test_2_6.vert");
        String fragCode = OpenGLUtils.readFileAsString("Test_2_6.frag");
        programRef = OpenGLUtils.initializeProgram(vertCode, fragCode);

        // specify color used when clearing the screen
        glClearColor(0.0f, 0.0f, 0.0f, 1.0f);

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
        translation = new Uniform("vec3", new float[] {-0.5f, 0.0f, 0.0f} );
        translation.locateVariable( programRef, "translation" );
        baseColor = new Uniform("vec3", new float[] {1.0f, 0.0f, 0.0f} );
        baseColor.locateVariable( programRef, "baseColor" );
        */

    }

    public void update()
    {

        /*
        // update data

        float distance = speed * deltaTime;
        if (input.isKeyPressed(GLFW_KEY_LEFT))
            translation.data[0] -= distance;
        if (input.isKeyPressed(GLFW_KEY_RIGHT))
            translation.data[0] += distance;
        if (input.isKeyPressed(GLFW_KEY_DOWN))
            translation.data[1] -= distance;
        if (input.isKeyPressed(GLFW_KEY_UP))
            translation.data[1] += distance;

        // render scene

        // reset color buffer with specified color
        glClear(GL_COLOR_BUFFER_BIT);

        glUseProgram( programRef );
        glBindVertexArray( vaoRef );
        translation.uploadData();
        baseColor.uploadData();
        glDrawArrays( GL_TRIANGLES, 0, vertexCount );
        */

    }

    // driver method
    public static void main(String[] args)
    {
        new Test_3_1().run();
    }

}
