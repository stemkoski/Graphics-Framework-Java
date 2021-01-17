import static org.lwjgl.glfw.GLFW.*;

import graphics.core.*;
import graphics.math.*;
import graphics.geometry.*;
import graphics.material.*;
import graphics.extras.*;
import graphics.light.*;
import graphics.font.*;

// vertex bump displacement from texture
public class Test_7_Bump extends Base
{
    public Renderer renderer;
    public Scene scene;
    public Camera camera;
    public Mesh mesh;
    public MovementRig rig;

    public void initialize()
    {
        renderer = new Renderer();
        scene    = new Scene();
        camera   = new Camera();
        // camera.setPosition( new Vector(0.5, 1, 4) );

        rig = new MovementRig();
        rig.attach( camera );
        rig.setPosition( new Vector(1, 3, 8) );
        scene.add( rig );
        
        Geometry geometry = new PlaneGeometry(20,20, 200,200);

        Material material = new Material("Test_7_Bump.vert", "Test_7_Bump.frag");
        Texture sand = new Texture("images/sand.jpg");
        Texture heightmap = new Texture("images/heightmap.png");
        material.addUniform("sampler2D", "heightmap", new Vector(heightmap.textureRef, 1));
        material.addUniform("float", "factor", 4f);
        material.addUniform("sampler2D", "image", new Vector(sand.textureRef, 2));
        material.addUniform("vec3", "baseColor", new Vector(1,1,1));
        material.locateUniforms();
        material.addRenderSetting("wireframe", true);
        material.addRenderSetting("lineWidth", 1);

        Mesh grid = new Mesh(geometry, material);
        grid.rotateX(-3.14/2, true);
        scene.add(grid);

        Mesh axes = new AxesHelper(2, 8);
        axes.translate(0, 0.01, 0, true);
        scene.add( axes );
    }

    public void update()
    {
        rig.update(input, deltaTime);
        renderer.render(scene, camera);

        if (input.isKeyDown(GLFW_KEY_F))
            System.out.println( "FPS: " + Math.floor(1 / deltaTime) );
    }

    // driver method
    public static void main(String[] args)
    {
        new Test_7_Bump().run();
    }

}

