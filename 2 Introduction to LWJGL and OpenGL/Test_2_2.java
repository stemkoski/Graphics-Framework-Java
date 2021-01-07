import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL40.*;

import core.*;

// GLFW = Graphics Library FrameWork

public class Test_2_2 extends Base
{
    public int programRef;

    public void initialize()
    {
        // vertex shader code
        String vsCode = String.join("\n",
            "in vec3 position;",
            "void main()",
            "{",
            "    gl_Position = vec4(position.x, position.y, position.z, 1.0);",
            "}"
        );

        // fragment shader code
        String fsCode = String.join("\n",
            "void main()",
            "{",
            "    gl_FragColor = vec4(1.0, 1.0, 0.0, 1.0);",
            "}"
        );

        // send code to GPU and compile; store program reference
        programRef = OpenGLUtils.initializeProgram(vsCode, fsCode);

        // render settings (optional)

        // set line width
        glLineWidth(4);

        int vaoRef = glGenVertexArrays();
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

    }

    public void update()
    {
        // select program to use when rendering
        glUseProgram( programRef );

        // render geometric objects using selected program
        glDrawArrays(GL_LINE_LOOP, 0, 6);

    }

    // driver method
    public static void main(String[] args)
    {
        new Test_2_2().run();
    }

}
