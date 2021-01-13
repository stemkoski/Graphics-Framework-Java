import static org.lwjgl.glfw.GLFW.*;

import graphics.core.*;
import graphics.math.*;
import graphics.geometry.*;
import graphics.material.*;
import graphics.extras.*;

public class Test_5_2 extends Base
{
    public Renderer renderer;
    public Scene scene;
    public Camera camera;
    public MovementRig rig;

    public void initialize()
    {
        renderer = new Renderer();
        scene    = new Scene();
        camera   = new Camera();
        
        rig = new MovementRig();
        rig.attach( camera );
        rig.setPosition( new Vector(0, 1, 4) );
        scene.add( rig );

        Geometry skyGeometry = new SphereGeometry(50);
        Material skyMaterial = new TextureMaterial( new Texture("images/sky-earth.jpg") );
        Mesh sky = new Mesh( skyGeometry, skyMaterial );
        scene.add( sky );

        Geometry grassGeometry = new RectangleGeometry(100, 100);
        Material grassMaterial = new TextureMaterial( new Texture("images/grass.jpg") );
        grassMaterial.uniforms.get("repeatUV").data = new Vector(50,50);
        Mesh grass = new Mesh( grassGeometry, grassMaterial );
        grass.rotateX(-3.14/2, true);
        scene.add( grass );
    }

    public void update()
    {
        rig.update(input, deltaTime);
        renderer.render(scene, camera);
    }

    // driver method
    public static void main(String[] args)
    {
        new Test_5_2().run();
    }

}

