import static org.lwjgl.glfw.GLFW.*;

import graphics.core.*;
import graphics.math.*;
import graphics.geometry.*;
import graphics.material.*;
import graphics.extras.*;

public class Test_5_6 extends Base
{
    public Renderer renderer;
    public Scene scene;
    public Camera camera;
    public Mesh mesh;

    public void initialize()
    {
        renderer = new Renderer();
        scene    = new Scene();
        camera   = new Camera();
        camera.setPosition( new Vector(0,0,1.5) );

        Geometry geometry = new RectangleGeometry();
        
        Material material = new Material("Test_5_6.vert", "Test_5_6.frag");
        material.locateUniforms();

        mesh = new Mesh( geometry, material );
        scene.add(mesh);
    }

    public void update()
    {
        renderer.render(scene, camera);
    }

    // driver method
    public static void main(String[] args)
    {
        new Test_5_6().run();
    }

}

