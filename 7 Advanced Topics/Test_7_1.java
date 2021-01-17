import static org.lwjgl.glfw.GLFW.*;

import graphics.core.*;
import graphics.math.*;
import graphics.geometry.*;
import graphics.material.*;
import graphics.extras.*;
import graphics.light.*;
import graphics.font.*;

// this and the font directory will eventually move to Chapter 7
public class Test_7_1 extends Base
{
    public Renderer renderer;
    public Scene scene;
    public Camera camera;
    public Mesh mesh;
    public MovementRig rig;
    public TextMesh textMesh;

    public void initialize()
    {
        renderer = new Renderer();
        renderer.setClearColor( new Vector(0.5,0.5,0.5,1) );
        scene    = new Scene();
        camera   = new Camera();
        // camera.setPosition( new Vector(0.5, 1, 4) );

        rig = new MovementRig();
        rig.attach( camera );
        rig.setPosition( new Vector(0.5, 1, 4) );
        scene.add( rig );
        
        Mesh grid = new GridHelper(10,10, new Vector(1,1,0), new Vector(1,1,1), 2);
        grid.rotateX(-Math.PI/2, true);
        scene.add( grid );

        Mesh axes = new AxesHelper(2, 8);
        axes.translate(0, 0.01, 0, true);
        scene.add( axes );

        BitmapFont bmfont = new BitmapFont("assets/arial.fnt", "assets/arial.png");
        textMesh = new TextMesh("Hello!", bmfont);
        textMesh.scale(0.5, true);
        scene.add( textMesh );
    }

    public void update()
    {
        rig.update(input, deltaTime);
        renderer.render(scene, camera);

        if (input.isKeyDown(GLFW_KEY_1))
            textMesh.setText("Hello!");
        if (input.isKeyDown(GLFW_KEY_2))
            textMesh.setText("Goodbye?");
           
    }

    // driver method
    public static void main(String[] args)
    {
        new Test_7_1().run();
    }

}

