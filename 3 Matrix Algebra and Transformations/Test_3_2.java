import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL40.*;

import graphics.core.*;
import graphics.math.*;

/* will use for testing Matrix class */
public class Test_3_2 extends Base
{
    public int programRef, vaoRef;
    public Uniform<Matrix> modelMatrix, projectionMatrix;
    public float moveSpeed, turnSpeed;

    public void initialize()
    {
        // load code, send to GPU, and compile; store program reference
        programRef = OpenGLUtils.initFromFiles(
            "Test_3_2.vert", "Test_3_2.frag" );

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
            modelMatrix.data.leftMultiply( 
                Matrix.makeTranslation(0, -moveAmount, 0));

        if (input.isKeyPressed(GLFW_KEY_A))
            modelMatrix.data.leftMultiply( 
                Matrix.makeTranslation(-moveAmount, 0, 0));
      
        if (input.isKeyPressed(GLFW_KEY_D))
            modelMatrix.data.leftMultiply( 
                Matrix.makeTranslation(moveAmount, 0, 0));

        if (input.isKeyPressed(GLFW_KEY_Z))
            modelMatrix.data.leftMultiply( 
                Matrix.makeTranslation(0, 0, moveAmount));
        
        if (input.isKeyPressed(GLFW_KEY_X))
            modelMatrix.data.leftMultiply( 
                Matrix.makeTranslation(0, 0, -moveAmount));

        // global rotation
        if (input.isKeyPressed(GLFW_KEY_Q))
            modelMatrix.data.leftMultiply( 
                Matrix.makeRotationZ(turnAmount));

        if (input.isKeyPressed(GLFW_KEY_E))
            modelMatrix.data.leftMultiply( 
                Matrix.makeRotationZ(-turnAmount));

        // local translation
        if (input.isKeyPressed(GLFW_KEY_I))
            modelMatrix.data.rightMultiply( 
                Matrix.makeTranslation(0, moveAmount, 0) );

        if (input.isKeyPressed(GLFW_KEY_K))
            modelMatrix.data.rightMultiply( 
                Matrix.makeTranslation(0, -moveAmount, 0) );

        if (input.isKeyPressed(GLFW_KEY_J))
            modelMatrix.data.rightMultiply( 
                Matrix.makeTranslation(-moveAmount, 0, 0) );

        if (input.isKeyPressed(GLFW_KEY_L))
            modelMatrix.data.rightMultiply( 
                Matrix.makeTranslation(moveAmount, 0, 0) );

        // local rotation
        if (input.isKeyPressed(GLFW_KEY_U))
            modelMatrix.data.rightMultiply( 
                Matrix.makeRotationZ(turnAmount) );

        if (input.isKeyPressed(GLFW_KEY_O))
            modelMatrix.data.rightMultiply( 
                Matrix.makeRotationZ(-turnAmount) );

        // render scene

        // reset color buffer with specified color
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

        glUseProgram( programRef );
        glBindVertexArray( vaoRef );
        modelMatrix.uploadData();
        projectionMatrix.uploadData();
        glDrawArrays( GL_TRIANGLES, 0, 3 );
    }

    // driver method
    public static void main(String[] args)
    {
        new Test_3_2().run();
    }

}
