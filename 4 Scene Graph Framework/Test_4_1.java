import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL40.*;

import graphics.core.*;
import graphics.math.*;
import graphics.geometry.*;
import graphics.material.*;

public class Test_4_1 extends Base
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
        camera.setPosition( new Vector(0,0,3) );
        Geometry geometry = new BoxGeometry();
        Material material = new SurfaceMaterial();
        // to change value from default, for example:
        // material.renderSettings.get("pointSize").data = 32;
        material.uniforms.get("useVertexColors").data = 1;
        material.renderSettings.get("wireframe").data = true;
        material.renderSettings.get("lineWidth").data = 8;
        mesh = new Mesh( geometry, material );
        scene.add( mesh );
    }

    public void update()
    {
        mesh.rotateY( 0.0123f, true );
        mesh.rotateX( 0.0237f, true );
        renderer.render(scene, camera);
    }

    // driver method
    public static void main(String[] args)
    {
        new Test_4_1().run();
    }

}
