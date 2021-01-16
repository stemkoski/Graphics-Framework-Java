import static org.lwjgl.glfw.GLFW.*;

import graphics.core.*;
import graphics.math.*;
import graphics.geometry.*;
import graphics.material.*;
import graphics.extras.*;
import graphics.effects.*;

public class Test_6_3 extends Base
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
        camera.setPerspective(60, 4/3f, 0.1, 1000);
        
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
        // postprocessor.addEffect( new TintEffect(new Vector(0,1,0)) );
        // postprocessor.addEffect( new ColorReduceEffect(5) );
        // postprocessor.addEffect( new PixelateEffect(8, new Vector(800,600)) );

        // new shaders - individual tests
        // postprocessor.addEffect( new BrightFilterEffect(2.4f) );
        // postprocessor.addEffect( new HorizontalBlurEffect( new Vector(1200,800), 50 ) );
        // postprocessor.addEffect( new VerticalBlurEffect( new Vector(1200,800), 50 ) );
        // postprocessor.addEffect( new AdditiveBlendEffect( new Texture("images/grid.png"), 0.5f, 0.5f ) );

        // light bloom effect
        postprocessor.addEffect( new BrightFilterEffect(2.4f) );
        postprocessor.addEffect( new HorizontalBlurEffect( new Vector(800,600), 30 ) );
        postprocessor.addEffect( new VerticalBlurEffect( new Vector(800,600), 30 ) );
        // access results of first render pass (original scene)
        Texture mainScene = postprocessor.renderTargetList.get(0).texture;
        postprocessor.addEffect( new AdditiveBlendEffect( mainScene, 2, 1) );

    }

    public void update()
    {
        rig.update(input, deltaTime);
        
        sphere.rotateY( 0.01337, true );

        postprocessor.render();
    }

    // driver method
    public static void main(String[] args)
    {
        new Test_6_3().run(800, 600);
    }

}

