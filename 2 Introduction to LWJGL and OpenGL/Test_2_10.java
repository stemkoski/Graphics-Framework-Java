import static org.lwjgl.glfw.GLFW.*;

import graphics.core.*;

public class Test_2_10 extends Base
{
    public int programRef, vaoRef, vertexCount;
    public Uniform translation, baseColor;

    public void initialize()
    {
        System.out.println("Initializing...");
    }

    public void update()
    {
        /*
        // debug printing
        if (input.keyDownList.size() > 0)
            System.out.println( "Keys down:" + input.keyDownList.toString() );
        if (input.keyPressedList.size() > 0)
            System.out.println( "Keys pressed:" + input.keyPressedList.toString() );
        if (input.keyUpList.size() > 0)
            System.out.println( "Keys up:" + input.keyUpList.toString() );
        */

        // typical usage
        if (input.isKeyDown(GLFW_KEY_SPACE))
            System.out.println( "The 'space' key was just pressed down.");
        if (input.isKeyPressed(GLFW_KEY_RIGHT))
            System.out.println( "The 'right arrow' key is currently pressed.");
        if (input.isKeyUp(GLFW_KEY_A))
            System.out.println( "The 'A' key was just released.");
    
    }

    // driver method
    public static void main(String[] args)
    {
        new Test_2_10().run();
    }

}