import static org.lwjgl.glfw.GLFW.*;

import graphics.core.*;
import graphics.math.*;
import graphics.geometry.*;
import graphics.material.*;
import graphics.extras.*;
import graphics.effects.*;

public class Test_6_4 extends Base
{
    public Renderer renderer;
    public Scene scene;
    public Camera camera;
    public MovementRig rig;

    public Mesh sphere;
    public Postprocessor glowPass, comboPass;

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

        // glow scene
        Scene glowScene = new Scene();
        Material redMaterial = new SurfaceMaterial();
        redMaterial.uniforms.get("baseColor").data = new Vector(1,0,0);
        Mesh glowSphere = new Mesh(sphereGeometry, redMaterial);
        glowSphere.transform = sphere.transform;
        glowScene.add( glowSphere );

        // glow postprocessing
        Vector size = new Vector(800, 600);
        RenderTarget glowTarget = new RenderTarget( size );
        glowPass = new Postprocessor(renderer, glowScene, camera, glowTarget);

        // empty spots have no initialized values, causing no glow blur...
        glowPass.addEffect( new HorizontalBlurEffect(size, 50) );
        glowPass.addEffect( new VerticalBlurEffect(size, 50) );
        
        // combining results of glow effect with main scene
        comboPass = new Postprocessor(renderer, scene, camera, null);
        comboPass.addEffect( new AdditiveBlendEffect(glowTarget.texture, 1, 3) );


    }

    public void update()
    {
        rig.update(input, deltaTime);
        
        sphere.rotateY( 0.01337, true );

        glowPass.render();
        comboPass.render();
    }

    // driver method
    public static void main(String[] args)
    {
        new Test_6_4().run(800, 600);
    }

}

