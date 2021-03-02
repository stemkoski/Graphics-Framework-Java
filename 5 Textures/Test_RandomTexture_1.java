import graphics.core.*;
import graphics.math.*;
import graphics.geometry.*;
import graphics.material.*;
import graphics.extras.*;
import graphics.light.*;
import graphics.font.*;

public class Test_RandomTexture_1 extends Base {
    public Renderer renderer;
    public Scene scene;
    public Camera camera;
    public Mesh mesh;
    public MovementRig rig;
    public Material waterMat;

    public void initialize() {
        renderer = new Renderer();
        scene    = new Scene();
        camera   = new Camera();
        camera.setPosition( new Vector(0, 0, 4) );

        Geometry rect = new RectangleGeometry(1.5, 1.5);
        waterMat = new RandomTexture();

        waterMat.addUniform("bool", "useSmooth", 1);
        waterMat.locateUniforms();

        mesh = new Mesh(rect, waterMat);
        scene.add(mesh);
    }

    public void update() {
        waterMat.uniforms.get("time").data = time;
        renderer.render(scene, camera);
    }

    // driver method
    public static void main(String[] args) {
        new Test_RandomTexture_1().run();
    }

}

