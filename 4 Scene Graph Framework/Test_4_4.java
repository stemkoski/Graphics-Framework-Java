import static org.lwjgl.opengl.GL40.*;

import graphics.core.*;
import graphics.math.*;
import graphics.geometry.*;
import graphics.material.*;

public class Test_4_4 extends Base
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
        camera.setPosition( new Vector(0,0,7) );

        Geometry geometry = new SphereGeometry(3);

        Material material = new Material(
            OpenGLUtils.readFileAsString("Test_4_4.vert"),
            OpenGLUtils.readFileAsString("Test_4_4.frag") );
        material.locateUniforms();

        mesh = new Mesh( geometry, material );
        scene.add( mesh );
    }

    public void update()
    {
        renderer.render(scene, camera);
    }

    // driver method
    public static void main(String[] args)
    {
        new Test_4_4().run();
    }

}
