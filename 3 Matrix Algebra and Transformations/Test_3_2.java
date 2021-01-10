import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL40.*;

import core.*;

/* will use for testing Matrix class */
public class Test_3_2 extends Base
{
    public int programRef, vaoRef, vertexCount;
    public Uniform<Matrix> modelMatrix, projectionMatrix;
    public float moveSpeed, turnSpeed;

    public void initialize()
    {
        // load code, send to GPU, and compile; store program reference
        String vertCode = OpenGLUtils.readFileAsString("Test_3_2.vert");
        String fragCode = OpenGLUtils.readFileAsString("Test_3_2.frag");
        programRef = OpenGLUtils.initializeProgram(vertCode, fragCode);

        // specify color used when clearing the screen
        glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        glEnable(GL_DEPTH_TEST);

        // setup vertex array object
        vaoRef = glGenVertexArrays();
        glBindVertexArray(vaoRef);

        float[] positionData = {
                  0.0f,  0.2f, 0.0f,
                  0.1f, -0.2f, 0.0f,
                 -0.1f, -0.2f, 0.0f  };
        vertexCount = positionData.length / 3;
        Attribute positionAttribute = new Attribute( "vec3", positionData );
        positionAttribute.associateVariable( programRef, "position" );

        // set up uniforms
        Matrix mMatrix = Matrix.makeTranslation(0, 0, -1);
        modelMatrix = new Uniform<Matrix>("mat4", mMatrix);
        modelMatrix.locateVariable( programRef, "modelMatrix" );

        Matrix pMatrix = Matrix.makePerspective();
        projectionMatrix = new Uniform<Matrix>("mat4", pMatrix);
        projectionMatrix.locateVariable( programRef, "projectionMatrix" );

        // movement speed, units per second
        moveSpeed = 0.5f;
        // rotation speed, radians per second
        turnSpeed = (float)Math.toRadians(90);

    }

    public void update()
    {
        // update data
        float moveAmount = moveSpeed * deltaTime;
        float turnAmount = turnSpeed * deltaTime;

        // global translation
        if (input.isKeyPressed(GLFW_KEY_W))
            modelMatrix.data.leftMultiply( 
                Matrix.makeTranslation(0, moveAmount, 0) );

        if (input.isKeyPressed(GLFW_KEY_S))
        {
            Matrix m = Matrix.makeTranslation(0, -moveAmount, 0);
            modelMatrix.data = Matrix.multiply(m, modelMatrix.data);
        }
        if (input.isKeyPressed(GLFW_KEY_A))
        {
            Matrix m = Matrix.makeTranslation(-moveAmount, 0, 0);
            modelMatrix.data = Matrix.multiply(m, modelMatrix.data);
        }
        if (input.isKeyPressed(GLFW_KEY_D))
        {
            Matrix m = Matrix.makeTranslation(moveAmount, 0, 0);
            modelMatrix.data = Matrix.multiply(m, modelMatrix.data);
        }

        if (input.isKeyPressed(GLFW_KEY_Z))
        {
            Matrix m = Matrix.makeTranslation(0, 0, moveAmount);
            modelMatrix.data = Matrix.multiply(m, modelMatrix.data);
        }
        if (input.isKeyPressed(GLFW_KEY_X))
        {
            Matrix m = Matrix.makeTranslation(0, 0, -moveAmount);
            modelMatrix.data = Matrix.multiply(m, modelMatrix.data);
        }

        // global rotation
        if (input.isKeyPressed(GLFW_KEY_Q))
        {
            Matrix m = Matrix.makeRotationZ(turnAmount);
            modelMatrix.data = Matrix.multiply(m, modelMatrix.data);
        }
        if (input.isKeyPressed(GLFW_KEY_E))
        {
            Matrix m = Matrix.makeRotationZ(-turnAmount);
            modelMatrix.data = Matrix.multiply(m, modelMatrix.data);
        }

        // local translation
        if (input.isKeyPressed(GLFW_KEY_I))
            modelMatrix.data.rightMultiply( 
                Matrix.makeTranslation(0, moveAmount, 0) );

        if (input.isKeyPressed(GLFW_KEY_K))
        {
            Matrix m = Matrix.makeTranslation(0, -moveAmount, 0);
            modelMatrix.data = Matrix.multiply(modelMatrix.data, m);
        }
        if (input.isKeyPressed(GLFW_KEY_J))
        {
            Matrix m = Matrix.makeTranslation(-moveAmount, 0, 0);
            modelMatrix.data = Matrix.multiply(modelMatrix.data, m);
        }
        if (input.isKeyPressed(GLFW_KEY_L))
        {
            Matrix m = Matrix.makeTranslation(moveAmount, 0, 0);
            modelMatrix.data = Matrix.multiply(modelMatrix.data, m);
        }

        // local rotation
        if (input.isKeyPressed(GLFW_KEY_U))
        {
            Matrix m = Matrix.makeRotationZ(turnAmount);
            modelMatrix.data = Matrix.multiply(modelMatrix.data, m);
        }
        if (input.isKeyPressed(GLFW_KEY_O))
        {
            Matrix m = Matrix.makeRotationZ(-turnAmount);
            modelMatrix.data = Matrix.multiply(modelMatrix.data, m);
        }

        // render scene

        // reset color buffer with specified color
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

        glUseProgram( programRef );
        glBindVertexArray( vaoRef );
        modelMatrix.uploadData();
        projectionMatrix.uploadData();
        glDrawArrays( GL_TRIANGLES, 0, vertexCount );
    }

    // driver method
    public static void main(String[] args)
    {
        new Test_3_2().run();
    }

}
