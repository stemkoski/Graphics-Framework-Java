import static org.lwjgl.glfw.GLFW.*;

import graphics.core.*;
import graphics.math.*;
import graphics.geometry.*;
import graphics.material.*;
import graphics.extras.*;

public class Test_5_10 extends Base
{
    public Renderer renderer;
    public Scene scene, hudScene;
    public Camera camera, hudCamera;
    public MovementRig rig;
    public Mesh label;

    public void initialize()
    {
        renderer = new Renderer();
        scene    = new Scene();
        // for non-square windows, adjust aspect ratio size
        camera   = new Camera(60, 800/600f, 0.1, 1000);
        
        rig = new MovementRig();
        rig.attach( camera );
        rig.setPosition( new Vector(0, 1.5, 5) );
        scene.add( rig );

        // main scene
        Geometry boxGeometry = new BoxGeometry();
        Material boxMaterial = new TextureMaterial( new Texture("images/crate.png") );
        Mesh box = new Mesh( boxGeometry, boxMaterial );
        box.setPosition( new Vector(0,0.5,0) );
        scene.add( box );

        Mesh grid = new GridHelper(10,10, new Vector(1,1,1), new Vector(1,0.5,0.5), 2);
        grid.rotateX(-Math.PI/2, true);
        scene.add( grid );

        // HUD scene
        hudScene = new Scene();
        hudCamera = new Camera();
        hudCamera.setOrthographic(0,800, 0,600, 1,-1);
        Geometry labelGeo1 = new RectangleGeometry(600, 80, new Vector(0,600), new Vector(0,1));
        Material labelMat1 = new TextureMaterial( new Texture("images/crate-sim.png"));
        Mesh label1 = new Mesh(labelGeo1, labelMat1);
        hudScene.add( label1 );
        Geometry labelGeo2 = new RectangleGeometry(400, 80, new Vector(800,0), new Vector(1,0));
        Material labelMat2 = new TextureMaterial( new Texture("images/version-1.png"));
        Mesh label2 = new Mesh(labelGeo2, labelMat2);
        hudScene.add( label2 );
    }

    public void update()
    {
        rig.update(input, deltaTime);
        renderer.clearColorBuffer = true;
        renderer.render(scene, camera);
        renderer.clearColorBuffer = false;
        renderer.render(hudScene, hudCamera);
    }

    // driver method
    public static void main(String[] args)
    {
        new Test_5_10().run(800, 600);
    }

}

