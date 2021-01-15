import static org.lwjgl.glfw.GLFW.*;

import graphics.core.*;
import graphics.math.*;
import graphics.geometry.*;
import graphics.material.*;
import graphics.extras.*;
import graphics.light.*;

public class Test_6_1 extends Base
{
    public Renderer renderer;
    public Scene scene;
    public Camera camera;
    public Mesh mesh;
    public MovementRig rig;

    public void initialize()
    {
        renderer = new Renderer();
        renderer.setClearColor(new Vector(0.2,0.2,0.2));

        scene    = new Scene();
        camera   = new Camera();
        camera.setPerspective(60, 800/600f, 0.1, 1000 );
        camera.setPosition( new Vector(0,0,6) );

        Light ambient = new AmbientLight( new Vector(0.1, 0.1, 0.1) ); 
        scene.add( ambient );

        Light directional = new DirectionalLight( 
            new Vector(0.8, 0.8, 0.8), new Vector(-1, -1, -2) );
        scene.add( directional );

        Light point = new PointLight(
            new Vector(0.9, 0, 0), new Vector(1, 1, 0.8) );
        scene.add( point );

        Geometry sphereGeometry = new SphereGeometry();


        Material flatMaterial = new FlatMaterial(null);
        flatMaterial.uniforms.get("baseColor").data = new Vector(0.6, 0.2, 0.2);

        Mesh sphere1 = new Mesh(sphereGeometry, flatMaterial);
        sphere1.setPosition( new Vector(-2.2, 0, 0) );
        scene.add( sphere1 );

        
        Material lambertMaterial = new LambertMaterial( new Texture("images/grid.png") );

        Mesh sphere2 = new Mesh(sphereGeometry, lambertMaterial);
        sphere2.setPosition( new Vector(0, 0, 0) );
        scene.add( sphere2 );

        
        Material phongMaterial = new PhongMaterial(null);
        phongMaterial.uniforms.get("baseColor").data = new Vector(0.5, 0.5, 1);
        
        Mesh sphere3 = new Mesh(sphereGeometry, phongMaterial);
        sphere3.setPosition( new Vector(2.2, 0, 0) );
        scene.add( sphere3 );
        

    }

    public void update()
    {
        // rig.update(input, deltaTime);
        renderer.render(scene, camera);
    }

    // driver method
    public static void main(String[] args)
    {
        new Test_6_1().run(1600,1200);
    }

}

