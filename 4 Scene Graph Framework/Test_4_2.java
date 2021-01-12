import static org.lwjgl.opengl.GL40.*;

import graphics.core.*;
import graphics.math.*;
import graphics.geometry.*;
import graphics.material.*;
import java.util.Arrays;
import java.util.List;

public class Test_4_2 extends Base
{
    public Renderer renderer;
    public Scene scene;
    public Camera camera;
    public Mesh mesh;

    public void initialize()
    {
        renderer = new Renderer();
        scene    = new Scene();
        camera   = new Camera();
        camera.setPosition( new Vector(0,0,1) );
        
        Geometry geometry = new Geometry();

        Vector P0 = new Vector(-0.1, 0.1, 0.0);
        Vector P1 = new Vector( 0.0, 0.0, 0.0);
        Vector P2 = new Vector( 0.1, 0.1, 0.0);
        Vector P3 = new Vector(-0.2, -0.2, 0.0);
        Vector P4 = new Vector( 0.2, -0.2, 0.0);

        List positionList = 
            Arrays.asList(P0,P3,P1, P1,P3,P4, P1,P4,P2);
        float[] positionData = Vector.flattenList(positionList);
        
        Vector R = new Vector(1, 0, 0);
        Vector Y = new Vector(1, 1, 0);
        Vector G = new Vector(0, 0.25, 0);

        List colorList = Arrays.asList(R,G,Y, Y,G,G, Y,G,R);
        float[] colorData = Vector.flattenList(colorList);

        geometry.addAttribute("vec3", "vertexPosition", positionData);
        geometry.addAttribute("vec3", "vertexColor", colorData);
        geometry.vertexCount = 9;

        Material material = new SurfaceMaterial();
        
        mesh = new Mesh( geometry, material );
        scene.add( mesh );
    }

    public void update()
    {
        renderer.render(scene, camera);
    }

    // driver method
    public static void main(String[] args)
    {
        new Test_4_2().run();
    }

}

