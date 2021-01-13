import static org.lwjgl.glfw.GLFW.*;

import graphics.core.*;
import graphics.math.*;
import graphics.geometry.*;
import graphics.material.*;
import graphics.extras.*;

public class Test_5_4 extends Base
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
        Texture crateTex = new Texture("images/crate.png");
        Material blendMaterial = new Material("Test_5_3.vert", "Test_5_4.frag");
        blendMaterial.addUniform("sampler2D", "texture1", new Vector(gridTex.textureRef, 1));
        blendMaterial.addUniform("sampler2D", "texture2", new Vector(crateTex.textureRef, 2));
        blendMaterial.addUniform("float", "time", 0.0);
        blendMaterial.locateUniforms();

        mesh = new Mesh( geometry, blendMaterial );
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
        new Test_5_4().run();
    }

}

