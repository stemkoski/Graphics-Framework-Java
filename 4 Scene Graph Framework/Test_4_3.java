import static org.lwjgl.opengl.GL40.*;

import graphics.core.*;
import graphics.math.*;
import graphics.geometry.*;
import graphics.material.*;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

public class Test_4_3 extends Base
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
        camera.setPosition( new Vector(0,0,7) );

        Geometry geometry = new Geometry();
        ArrayList<Vector> positionList = new ArrayList<Vector>();
        for (double x=-3.2; x <= 3.2; x += 0.3)
            positionList.add( new Vector(x, Math.sin(x), 0) );

        float[] positionData = Vector.flattenList(positionList);
        geometry.addAttribute("vec3", "vertexPosition", positionData);

        geometry.vertexCount = positionList.size();

        Material lineMaterial = new LineMaterial("connected");
        lineMaterial.uniforms.get("baseColor").data = new Vector(1,0,1);
        lineMaterial.renderSettings.get("lineWidth").data = 4;
        Mesh lineMesh = new Mesh( geometry, lineMaterial );
        scene.add( lineMesh );

        Material pointMaterial = new PointMaterial();
        pointMaterial.uniforms.get("baseColor").data = new Vector(1,1,0);
        pointMaterial.renderSettings.get("pointSize").data = 8;
        Mesh pointMesh = new Mesh( geometry, pointMaterial );
        scene.add(pointMesh);
    }

    public void update()
    {
        renderer.render(scene, camera);
    }

    // driver method
    public static void main(String[] args)
    {
        new Test_4_3().run();
    }

}
