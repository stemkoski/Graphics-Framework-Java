import static org.lwjgl.glfw.GLFW.*;

import graphics.core.*;
import graphics.math.*;
import graphics.geometry.*;
import graphics.material.*;
import graphics.extras.*;
import graphics.light.*;
import graphics.font.*;

public class Test_7_OBJ extends Base
{
    public Renderer renderer;
    public Scene scene;
    public Camera camera;
    public Mesh mesh;
    public MovementRig rig;

    public void initialize()
    {
        renderer = new Renderer();
        renderer.setClearColor( new Vector(0.25, 0.25, 0.25, 1) );
        scene    = new Scene();
        camera   = new Camera();
        camera.setPerspective(60, 4/3f, 0.01, 1000);

        rig = new MovementRig();
        rig.attach( camera );
        rig.setPosition( new Vector(0, 1, 4) );
        scene.add( rig );
        
        mesh = new Mesh(
            new OBJGeometry("models/sonic/sonic.obj"),
            new TextureMaterial( new Texture("models/sonic/sonic.png"))
        );
        scene.add(mesh);

        Mesh grid = new GridHelper();
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

        mesh.rotateX(0.01337, false);
        mesh.rotateY(0.02357, false);

        if (input.isKeyDown(GLFW_KEY_F))
            System.out.println( "FPS: " + Math.floor(1 / deltaTime) );
    }

    // driver method
    public static void main(String[] args)
    {
        new Test_7_OBJ().run(1600, 1200);
    }

}

