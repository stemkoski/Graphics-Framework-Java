import static org.lwjgl.glfw.GLFW.*;

import graphics.core.*;
import graphics.math.*;
import graphics.geometry.*;
import graphics.material.*;
import graphics.extras.*;
import graphics.light.*;
import graphics.font.*;

// cube texture test
public class Test_7_DynamicReflections extends Base
{
    public Renderer renderer;
    public Scene scene;
    public Camera camera;
    public Mesh mesh;
    public MovementRig rig;

    public CubeRenderTarget cubeRenderTarget;
    public CubeCamera cubeCamera;
    public Camera c;

    public void initialize()
    {
        renderer = new Renderer();
        renderer.setClearColor( new Vector(0.5, 0.5, 0.5, 1) );
        scene    = new Scene();
        camera   = new Camera();
        cubeCamera = new CubeCamera();
        camera.setPerspective(60, 4/3f, 0.01, 1000);

        rig = new MovementRig();
        rig.attach( camera );
        rig.setPosition( new Vector(0.5, 1, 4) );
        scene.add( rig );
        
        // add skybox

        String[] fileNames2 = new String[] {
            "images/dawn/xpos.png", "images/dawn/xneg.png", 
            "images/dawn/ypos.png", "images/dawn/yneg.png", 
            "images/dawn/zpos.png", "images/dawn/zneg.png"
        };
        CubeTexture skyTex = new CubeTexture(fileNames2);

        Mesh sky = new Mesh(
            new BoxGeometry(500,500,500),
            new CubeMaterial(skyTex)
        );
        scene.add(sky);

        cubeRenderTarget = new CubeRenderTarget( new Vector(800, 600) );

        // torus to reflect
        ReflectMaterial reflectMat = new ReflectMaterial( cubeRenderTarget.texture, 0.2f );
        reflectMat.uniforms.get("baseColor").data = new Vector(0,0,1);
        mesh = new Mesh(
            new TorusGeometry(),
            reflectMat
        );
        mesh.rotateX(-3.14/2, true);
        scene.add(mesh);

        // helpers

        Mesh grid = new GridHelper();
        grid.rotateX(-3.14/2, true);
        scene.add(grid);

        Mesh axes = new AxesHelper(2, 8);
        axes.translate(0, 0.01, 0, true);
        scene.add( axes );

        cubeCamera = new CubeCamera();
        cubeCamera.setPosition(new Vector(0, 0, 0));

    }

    public void update()
    {
        rig.update(input, deltaTime);
       
        renderer.cubeRenderTarget = cubeRenderTarget;
        renderer.render(scene, cubeCamera);
        //renderer.renderTarget = null;
        //renderer.render(scene, camera);

        mesh.rotateX( 0.011, false );
        mesh.rotateY( 0.017, false );
        
        if (input.isKeyDown(GLFW_KEY_F))
            System.out.println( "FPS: " + Math.floor(1 / deltaTime) );
    }

    // driver method
    public static void main(String[] args)
    {
        new Test_7_DynamicReflections().run(1600, 1200);
    }

}

