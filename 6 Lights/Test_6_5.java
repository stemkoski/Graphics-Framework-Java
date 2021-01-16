import static org.lwjgl.glfw.GLFW.*;

import graphics.core.*;
import graphics.math.*;
import graphics.geometry.*;
import graphics.material.*;
import graphics.extras.*;
import graphics.effects.*;
import graphics.light.*;

public class Test_6_5 extends Base
{
    public Renderer renderer;
    public Scene scene;
    public Camera camera;
    public MovementRig rig;

    public DirectionalLight dirLight;

    public void initialize()
    {
        renderer = new Renderer();
        renderer.setClearColor( new Vector(0.25, 0.25, 0.25, 1) );
        scene    = new Scene();
        camera   = new Camera();
        camera.setPerspective(60, 4/3f, 0.1, 1000);
        
        rig = new MovementRig();
        rig.attach( camera );
        rig.setPosition( new Vector(0, 2, 5) );
        scene.add( rig );

        AmbientLight ambLight = new AmbientLight( new Vector(0.2, 0.2, 0.2) );
        scene.add( ambLight );

        dirLight = new DirectionalLight( new Vector(1,1,1), new Vector(-1,-1,0) );
        dirLight.setPosition( new Vector(2,4,0) );
        scene.add( dirLight );
        Mesh directHelper = new DirectionalLightHelper(dirLight);
        dirLight.add( directHelper );

        Geometry sphereGeometry = new SphereGeometry();
        LambertMaterial phongMaterial = new LambertMaterial( new Texture("images/grid.png") );
        phongMaterial.uniforms.get("useShadow").data = 1;

        Mesh sphere1 = new Mesh(sphereGeometry, phongMaterial);
        sphere1.setPosition( new Vector(-2, 1, 0) );
        scene.add( sphere1 );

        Mesh sphere2 = new Mesh(sphereGeometry, phongMaterial);
        sphere2.setPosition( new Vector(1, 2.2, -0.5) );
        scene.add( sphere2 );

        renderer.enableShadows( dirLight );

        /*
        // optional: render depth texture to mesh in scene. 
        //  note: this object may also cast a shadow in scene.
        Texture depthTexture = renderer.shadowObject.renderTarget.texture;
        Mesh shadowDisplay = new Mesh( 
            new RectangleGeometry(), 
            new TextureMaterial(depthTexture) );
        shadowDisplay.setPosition( new Vector(-1,3,0) );
        scene.add( shadowDisplay );
        */

        Mesh floor = new Mesh( new RectangleGeometry(20, 20), phongMaterial );
        floor.rotateX(-3.14/2, true);
        scene.add(floor);
    }

    public void update()
    {
        // view dynamic shadows -- need to increase shadow camera range
        Vector target = new Vector(-2, 0, 2*Math.sin(time));
        dirLight.lookAt(target);

        rig.update(input, deltaTime);
        renderer.render( scene, camera );

        // render scene from shadow camera
        // Camera shadowCam = renderer.shadowObject.camera;
        // renderer.render( scene, shadowCam );
    }

    // driver method
    public static void main(String[] args)
    {
        new Test_6_5().run(800, 600);
    }

}

