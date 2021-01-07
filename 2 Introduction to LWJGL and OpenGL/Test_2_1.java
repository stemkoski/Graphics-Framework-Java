import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL40.*;

import core.*;

// GLFW = Graphics Library FrameWork

public class Test_2_1 extends Base
{
    public int programRef;

    public void initialize()
    {
        // vertex shader code
        String vsCode = String.join("\n",
            "void main()",
            "{",
            "    gl_Position = vec4(0.0, 0.0, 0.0, 1.0);",
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
        new Test_2_1().run();
    }
}
