import static org.lwjgl.glfw.GLFW.*;

import graphics.core.*;
import graphics.math.*;
import graphics.geometry.*;
import graphics.material.*;
import graphics.extras.*;
import graphics.light.*;
import graphics.font.*;

public class Test_7_Blend extends Base
{
    public Renderer renderer;
    public Scene scene;
    public Camera camera;
    public Mesh mesh;
    public MovementRig rig;

    public void initialize()
    {
        renderer = new Renderer();
        renderer.setClearColor( new Vector(0.05, 0.05, 0.5, 1) );
        scene    = new Scene();
        camera   = new Camera();

        rig = new MovementRig();
        rig.attach( camera );
        rig.setPosition( new Vector(0.5, 2, 8) );
        scene.add( rig );
        
        Geometry skyGeometry = new SphereGeometry(50);
        Material skyMaterial = new TextureMaterial( new Texture("images/sky-earth.jpg") );
        Mesh sky = new Mesh( skyGeometry, skyMaterial );
        scene.add( sky );

        // adding lights (testing normals)
        scene.add( new AmbientLight( new Vector(0.3,0.3,0.3) ) );
        scene.add( new DirectionalLight( 
            new Vector(1,1,1), new Vector(-1, -1, -2) ) );

        Geometry geometry = new RectangleGeometry(10,10);


        // test materials
        // Material material = new TextureMaterial( new Texture("images/heightmap-128.png") );
        // Material material = new LambertMaterial( new Texture("images/grid.png") );
        Material material = new BlendMaterial( new Texture("images/blendMap.png"),
        new Texture("images/stone-512.jpg"), new Texture("images/lava.jpg"),
        new Texture("images/grass.jpg"), new Texture("images/water.jpg") );
        
        material.uniforms.get("repeatUV").data = new Vector(5, 5);

        Mesh terrain = new Mesh(geometry, material);
       
        scene.add(terrain);
        
        // vertex normal check
        // Mesh helper = new VertexNormalHelper(terrain, 0.1, new Vector(0.5, 0.5, 1), 2);
        // scene.add(helper);

        Mesh axes = new AxesHelper(2, 8);
        axes.translate(0, 0.01, 0, true);
        // scene.add( axes );
    }

    public void update()
    {
        rig.update(input, deltaTime);
        renderer.render(scene, camera);

        if (input.isKeyDown(GLFW_KEY_F))
            System.out.println( "FPS: " + Math.floor(1 / deltaTime) );
    }

    // driver method
    public static void main(String[] args)
    {
        new Test_7_Blend().run();
    }

}

