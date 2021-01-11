package core;

import org.lwjgl.*;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL40.*;

// LWJGL = Lightweight Java Gaming Library
// GLFW = Graphics Library FrameWork

public abstract class Base
{
    // the window handle
    private long window;
    // is the main loop currently active?
    private boolean running;
    // handle user input events
    public Input input;

    // number of seconds application has been running
    public float time;
    // seconds since last iteration of run loop
    public float deltaTime;
    // store timestamp from last iteration of run loop
    private long previousTime;
    private long currentTime;

    // constructor
    public Base()
    {

    }

    public void startup()
    {
        System.out.println("Starting program...");

        // print error messages in System.err
        GLFWErrorCallback.createPrint(System.err).set();

        // initialize GLFW
        boolean initSuccess = glfwInit();
        if ( !initSuccess )
            throw new RuntimeException("Unable to initialize GLFW");

        // create window and associated OpenGL context (context stores framebuffer and other state information)
        glfwWindowHint(GLFW_RESIZABLE, GL_FALSE);
        window = glfwCreateWindow(1024, 1024, "Graphics Window", 0, 0);
        if ( window == 0 )
            throw new RuntimeException("Failed to create the GLFW window");

        running = true;
        input = new Input(window);

        time = 0;
        deltaTime = 1/60f;
        currentTime = System.currentTimeMillis();
        previousTime = System.currentTimeMillis();


        // make the OpenGL context current: all function calls will apply to this context instance
        glfwMakeContextCurrent(window);
        // specify number of screen updates to wait before swapping buffers (via glfwSwapBuffers)
        //   setting to 1 enables vertical sync - synchronizes application frame rate to display refresh rate
        //   and prevents visual "screen tearing" artifacts
        glfwSwapInterval(1);
        // detects the context that is current in the current thread,
        // creates the GLCapabilities instance and makes the OpenGL
        // bindings available for use.
        GL.createCapabilities();


        OpenGLUtils.checkVersion();
        System.out.println("LWJGL version: " + Version.getVersion() );

    }

    public abstract void initialize();

    public abstract void update();

    public void run()
    {
        // startup ----------------------
        startup();
        // application-specific startup code
        initialize();

        // main loop ----------------------
        while (running)
        {
            // process input ----------------------
            // poll for window events; activates callbacks in Input class methods
            glfwPollEvents();

            // recalculate time variables
            currentTime = System.currentTimeMillis();
            deltaTime = (currentTime - previousTime) / 1000f;
            time += deltaTime;
            previousTime = currentTime;
            
            // process input
            input.update();
            // press Escape key or click on close icon to quit application,
            if ( input.isKeyDown(GLFW_KEY_ESCAPE) || glfwWindowShouldClose(window) )
                running = false;

            // update ----------------------
            update();

            // render ----------------------
            // swap the color buffers
            glfwSwapBuffers(window);
        }

        // shutdown ----------------------
        shutdown();
    }

    public void shutdown()
    {
        // stop window monitoring for user input
        glfwFreeCallbacks(window);
        // close/destroy the window
        glfwDestroyWindow(window);
        // stop GLFW
        glfwTerminate();
        // stop error callback
        glfwSetErrorCallback(null).free();
    }
}