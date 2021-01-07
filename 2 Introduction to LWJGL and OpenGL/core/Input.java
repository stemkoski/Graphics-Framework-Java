package core;

import java.util.ArrayList;

import static org.lwjgl.glfw.GLFW.*;

public class Input
{
    public boolean quit;

    public ArrayList<Integer> keyPressQueue;
    public ArrayList<Integer> keyReleaseQueue;

    public ArrayList<Integer> keyDownList;
    public ArrayList<Integer> keyPressedList;
    public ArrayList<Integer> keyUpList;

    // requires listening window reference
    public Input(long window)
    {
        quit = false;

        keyPressQueue   = new ArrayList<Integer>();
        keyReleaseQueue = new ArrayList<Integer>();

        keyDownList    = new ArrayList<Integer>();
        keyPressedList = new ArrayList<Integer>();
        keyUpList      = new ArrayList<Integer>();

        // Setup a key callback. Maybe be asynchronous with update.
        // It will be called every time a key is pressed, repeated or released.
        glfwSetKeyCallback(window,
            (window_, key, scancode, action, mods) ->
            {
                if ( action == GLFW_PRESS )
                    keyPressQueue.add(key);

                if ( action == GLFW_RELEASE )
                    keyReleaseQueue.add(key);
            }
        );
    }

    public void update()
    {
        // reset discrete key states
        keyDownList.clear();
        keyUpList.clear();

        // process queued press/release events;
        // add to or remove from corresponding lists
        for (Integer key : keyPressQueue)
        {
            keyDownList.add(key);
            keyPressedList.add(key);
        }
        for (Integer key : keyReleaseQueue)
        {
            keyUpList.add(key);
            keyPressedList.remove(key);
        }

        // finished processing queues; clear contents
        keyPressQueue.clear();
        keyReleaseQueue.clear();
    }

    public boolean isKeyDown(Integer key)
    {
        return keyDownList.contains(key);
    }

    public boolean isKeyPressed(Integer key)
    {
        return keyPressedList.contains(key);
    }

    public boolean isKeyUp(Integer key)
    {
        return keyUpList.contains(key);
    }

}
