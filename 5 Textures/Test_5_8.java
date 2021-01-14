import static org.lwjgl.glfw.GLFW.*;

import graphics.core.*;
import graphics.math.*;
import graphics.geometry.*;
import graphics.material.*;
import graphics.extras.*;

public class Test_5_8 extends Base
{
    public Renderer renderer;
    public Scene scene;
    public Camera camera;
    public MovementRig rig;
    public Mesh label;

    public void initialize()
    {
        renderer = new Renderer();
        scene    = new Scene();
        camera   = new Camera();
        
        rig = new MovementRig();
        rig.attach( camera );
        rig.setPosition( new Vector(0, 1.5, 5) );
        scene.add( rig );

        Geometry boxGeometry = new BoxGeometry();
        Material boxMaterial = new TextureMaterial( new Texture("images/crate.png") );
        Mesh box = new Mesh( boxGeometry, boxMaterial );
        box.setPosition( new Vector(0,0.5,0) );
        scene.add( box );

        Mesh grid = new GridHelper(10,10, new Vector(1,1,1), new Vector(1,0.5,0.5), 2);
        grid.rotateX(-Math.PI/2, true);
        scene.add( grid );

        Texture labelTexture = new Texture("images/crate-label.png");
        Material labelMaterial = new TextureMaterial(labelTexture);
        Geometry labelGeometry = new RectangleGeometry(1, 0.5);
        labelGeometry.applyMatrix( Matrix.makeRotationY(3.14) );
        label = new Mesh(labelGeometry, labelMaterial);
        label.setPosition( new Vector(0, 1.5, 0) );
        scene.add(label);
    }

    public void update()
    {
        // note: looking at camera is similar to but
        //   not precisely same effect as billboarding
        label.lookAt( camera.getWorldPosition() );
        
        rig.update(input, deltaTime);
        renderer.render(scene, camera);
    }

    // driver method
    public static void main(String[] args)
    {
        new Test_5_8().run();
    }

}

