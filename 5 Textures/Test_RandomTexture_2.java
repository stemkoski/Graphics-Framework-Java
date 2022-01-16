import graphics.core.*;
import graphics.math.*;
import graphics.geometry.*;
import graphics.material.*;
import graphics.extras.*;
import graphics.light.*;
import graphics.font.*;

import java.awt.*;

public class Test_RandomTexture_2 extends Base
{
    public Renderer renderer;
    public Scene scene;
    public Camera camera;
    public Mesh skyBox;
    public MovementRig rig;
    public Material liquidMat, skyMat;

    public void initialize()
    {
        renderer = new Renderer();
        scene    = new Scene();
        camera   = new Camera();

        rig = new MovementRig();
        rig.attach( camera );
        rig.setPosition( new Vector(0.5, 2, 4) );
        scene.add( rig );

        // SkyBox
        Geometry skySphere = new SphereGeometry(50);
        skyMat = new RandomTexture();

        skyMat.addUniform("bool", "cloud", 1);
        skyMat.locateUniforms();

        skyBox = new Mesh(skySphere, skyMat);
        scene.add(skyBox);

        // Water
        Geometry liquidSurface = new EllipsoidGeometry(110, 0.1, 110, 128, 128);
        liquidMat = new RandomTexture();

        // Make liquid water
        liquidMat.addUniform("bool", "water", 1);
        liquidMat.addUniform("bool", "waves", 1);
        liquidMat.locateUniforms();

        Mesh liquid = new Mesh(liquidSurface, liquidMat);
        liquid.setPosition(new Vector(0, 0.4 , 0));
        scene.add(liquid);

        // Island
        Geometry islandSphere = new EllipsoidGeometry(20, 2, 17, 32, 32);
        Material islandMat = new TextureMaterial(new Texture("images/grass.jpg"));
        Mesh island = new Mesh(islandSphere, islandMat);
        island.setPosition(new Vector(0, 0.25, 0));
        scene.add(island);
    }

    public void update()
    {
        // Rotate skyBox
        //skyBox.rotateY(0.000337, true);

        // Animate liquid and sky
        skyMat.uniforms.get("time").data = 0.1f * time;
        liquidMat.uniforms.get("time").data = 1.4f * time;

        rig.update(input, deltaTime);
        renderer.render(scene, camera);
    }

    // driver method
    public static void main(String[] args)
    {
        new Test_RandomTexture_2().run();
    }

}

