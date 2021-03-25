import static org.lwjgl.glfw.GLFW.*;

import graphics.core.*;
import graphics.math.*;
import graphics.geometry.*;
import graphics.material.*;
import graphics.extras.*;
import graphics.light.*;
import graphics.font.*;


public class Test_7_Water extends Base
{
    public Renderer renderer;
    public Scene scene;
    public Camera camera;
    public Mesh mesh;
    public MovementRig rig;
    public RenderTarget rt;
    public Camera waterCamera;
    public Mesh water;

    public void initialize()
    {
        renderer = new Renderer();
        scene    = new Scene();
        camera   = new Camera();

        rig = new MovementRig();
        rig.attach( camera );
        rig.setPosition( new Vector(0.5, 1, 4) );
        scene.add( rig );

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
        
        Mesh grid = new GridHelper(10,10, new Vector(1,1,0), new Vector(1,1,1), 2);
        grid.rotateX(-Math.PI/2, true);
        //scene.add( grid );

        Mesh axes = new AxesHelper(2, 8);
        axes.translate(0, 0.01, 0, true);
        //scene.add( axes );

        Mesh tree = new Mesh(
            new OBJGeometry("models/tree/tree.obj"),
            new TextureMaterial( new Texture("models/tree/tree.png"))
        );
        tree.setPosition(new Vector(0, 0, -4));
        scene.add(tree);

        Mesh box = new Mesh(
            new BoxGeometry(.5,.5,.5),
            new TextureMaterial( new Texture("images/crate.png"))
        );
        box.setPosition(new Vector(0, -2, -0.5));
        box.rotateX(0.2337, true);
        box.rotateY(0.4337, true);
        scene.add(box);

        Mesh sphere = new Mesh(
            new SphereGeometry(.5),
            new TextureMaterial( new Texture("images/grid.png"))
        );
        sphere.setPosition(new Vector(-1, -1, -1));
        scene.add(sphere);

        rt = new RenderTarget( new Vector(512,512) );

        Geometry waterGeo = new RectangleGeometry(5,5);
        Material waterMat = new WaterMaterial( rt.texture );

        water = new Mesh( waterGeo, waterMat );
        water.rotateX(-Math.PI/2, true);
        scene.add(water);

        waterCamera = new Camera();
        scene.add( waterCamera );
        

    }

    public void update()
    {
        waterCamera.setPosition( new Vector( rig.getPosition().values[0], 
                                             2 * -rig.getPosition().values[1] - water.getPosition().values[1],
                                             rig.getPosition().values[2] ) );
        waterCamera.lookAt( water.getPosition() );
        rig.update(input, deltaTime);
        renderer.renderTarget = rt;
        water.visible = false;
        renderer.render(scene, waterCamera);
        renderer.renderTarget = null;
        water.visible = true;
        renderer.render(scene, camera);
    }

    // driver method
    public static void main(String[] args)
    {
        new Test_7_Water().run();
    }

}

