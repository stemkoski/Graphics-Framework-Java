import static org.lwjgl.glfw.GLFW.*;

import graphics.core.*;
import graphics.math.*;
import graphics.geometry.*;
import graphics.material.*;
import graphics.extras.*;

// sprite material -- billboarding and spritesheet animation
public class Test_5_9 extends Base
{
    public Renderer renderer;
    public Scene scene;
    public Camera camera;
    public MovementRig rig;
    public Mesh sprite;

    public void initialize()
    {
        renderer = new Renderer();
        scene    = new Scene();
        camera   = new Camera();
        
        rig = new MovementRig();
        rig.attach( camera );
        rig.setPosition( new Vector(0, 1.5, 5) );
        scene.add( rig );

        Geometry geometry = new RectangleGeometry();
        Material material = new SpriteMaterial( new Texture("images/rolling-ball.png") );
        material.uniforms.get("billboard").data = 1;
        material.uniforms.get("tileCount").data = new Vector(4,4);
        material.uniforms.get("tileNumber").data = 0;
        sprite = new Mesh( geometry, material );
        sprite.setPosition( new Vector(0,0.5,0) );
        scene.add( sprite );

        Mesh grid = new GridHelper(10,10, new Vector(1,1,1), new Vector(1,0.5,0.5), 2);
        grid.rotateX(-Math.PI/2, true);
        scene.add( grid );
    }

    public void update()
    {
        // note: looking at camera is similar to but
        //   not precisely same effect as billboarding
        int tilesPerSecond = 8;
        float tileNumber = (float)Math.floor(time * tilesPerSecond);
        sprite.material.uniforms.get("tileNumber").data = tileNumber;
        
        rig.update(input, deltaTime);
        renderer.render(scene, camera);
    }

    // driver method
    public static void main(String[] args)
    {
        new Test_5_9().run();
    }

}

