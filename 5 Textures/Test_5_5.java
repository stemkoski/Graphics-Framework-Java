import static org.lwjgl.glfw.GLFW.*;

import graphics.core.*;
import graphics.math.*;
import graphics.geometry.*;
import graphics.material.*;
import graphics.extras.*;

public class Test_5_5 extends Base
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
        
        Texture gridTex  = new Texture("images/grid.png");
        Texture noiseTex = new Texture("images/noise.png");
        Material distortMaterial = new Material("Test_5_3.vert", "Test_5_5.frag");
        distortMaterial.addUniform("sampler2D", "image", new Vector(gridTex.textureRef, 1));
        distortMaterial.addUniform("sampler2D", "noise", new Vector(noiseTex.textureRef, 2));
        distortMaterial.addUniform("float", "time", 0.0);
        distortMaterial.locateUniforms();

        mesh = new Mesh( geometry, distortMaterial );
        scene.add(mesh);
    }

    public void update()
    {
        mesh.material.uniforms.get("time").data = time;
        renderer.render(scene, camera);
    }

    // driver method
    public static void main(String[] args)
    {
        new Test_5_5().run();
    }

}

