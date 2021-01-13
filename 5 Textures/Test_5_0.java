import static org.lwjgl.glfw.GLFW.*;

import graphics.core.*;
import graphics.math.*;
import graphics.geometry.*;
import graphics.material.*;
import graphics.extras.*;

public class Test_5_0 extends Base
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
		rig.setPosition( new Vector(0.5, 1, 4) );
		scene.add( rig );
       	
		Mesh grid = new GridHelper(10,10, new Vector(1,1,0), new Vector(1,1,1), 2);
		grid.rotateX(-Math.PI/2, true);
		scene.add( grid );

    	Mesh axes = new AxesHelper(2, 8);
    	axes.translate(0, 0.01, 0, true);
		scene.add( axes );

        Geometry geometry2 = new RectangleGeometry();
        Material material2 = new TextureMaterial( new Texture("images/circle-border.png") );
        Mesh mesh2 = new Mesh(geometry2, material2);
        mesh2.translate(0,0,1, true);
        scene.add(mesh2);

        Geometry geometry = new RectangleGeometry();
        Material material = new TextureMaterial( new Texture("images/grid.png") );
        Mesh mesh = new Mesh(geometry, material);
        scene.add(mesh);
    }

    public void update()
    {
    	rig.update(input, deltaTime);
        renderer.render(scene, camera);
    }

    // driver method
    public static void main(String[] args)
    {
        new Test_5_0().run();
    }

}

