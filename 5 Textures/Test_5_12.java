import static org.lwjgl.glfw.GLFW.*;

import graphics.core.*;
import graphics.math.*;
import graphics.geometry.*;
import graphics.material.*;
import graphics.extras.*;
import graphics.effects.*;

public class Test_5_12 extends Base
{
    public Renderer renderer;
    public Scene scene;
    public Camera camera;
    public MovementRig rig;

    public Mesh sphere;
    public Postprocessor postprocessor;

    public void initialize()
    {
        renderer = new Renderer();
        scene    = new Scene();
        camera   = new Camera();
        
        rig = new MovementRig();
        rig.attach( camera );
        rig.setPosition( new Vector(0, 1, 3) );
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

        Geometry sphereGeometry = new SphereGeometry();
        Material sphereMaterial = new TextureMaterial( new Texture("images/grid.png") );
        sphere = new Mesh( sphereGeometry, sphereMaterial );
        sphere.setPosition( new Vector(0,1,0) );
        scene.add( sphere );

        // set up postprocessing
        postprocessor = new Postprocessor(renderer, scene, camera, null);
        // postprocessor.addEffect( new TemplateEffect() );
        // postprocessor.addEffect( new TintEffect( new Vector(1,0,0) ) );
        // postprocessor.addEffect( new InvertEffect() );
        // postprocessor.addEffect( new PixelateEffect(8, new Vector(800,600)) );
        // postprocessor.addEffect( new VignetteEffect(0.4f, 1.0f, new Vector(0,0,0)) );
        // postprocessor.addEffect( new ColorReduceEffect(3) );

        // retro handheld video game system effect
        postprocessor.addEffect( new TintEffect(new Vector(0,1,0)) );
        postprocessor.addEffect( new ColorReduceEffect(5) );
        postprocessor.addEffect( new PixelateEffect(8, new Vector(800,600)) );

    }

    public void update()
    {
        rig.update(input, deltaTime);
        
        sphere.rotateY( 0.01337, true );
        // renderer.render(scene, camera);
        postprocessor.render();
    }

    // driver method
    public static void main(String[] args)
    {
        new Test_5_12().run();
    }

}

