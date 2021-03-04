import graphics.core.*;
import graphics.extras.AxesHelper;
import graphics.extras.MovementRig;
import graphics.geometry.*;
import graphics.material.Material;
import graphics.material.SurfaceMaterial;
import graphics.material.TextureMaterial;
import graphics.math.Vector;

import javax.management.relation.RoleUnresolved;

public class testingMiniMap extends Base {
    // General initialization
    public Renderer renderer;
    public Scene scene, hudScene;
    public Camera camera, hudCamera;
    public MovementRig rig;

    // Shapes
    public Mesh sphere, box, grass, sky;

    // Minimap initialization
    public RenderTarget renderTarget;
    public Camera skyCamera;

    public void initialize() {
        // Main scene
        renderer = new Renderer();
        scene = new Scene();
        camera = new Camera();
        rig = new MovementRig();

        rig.attach(camera);
        rig.setPosition(new Vector(0, 1.5, 5));

        scene.add(rig);

        // World
        Geometry skySphere = new SphereGeometry(50);
        Material skyMat = new TextureMaterial(new Texture("src/images/sky-earth.jpg"));
        sky = new Mesh(skySphere, skyMat);
        scene.add(sky);

        Geometry grassPlane = new RectangleGeometry(100, 100);
        Material grassMat = new TextureMaterial(new Texture("src/images/grass.jpg"));
        grassMat.uniforms.get("repeatUV").data = new Vector(50,50);
        grass = new Mesh(grassPlane, grassMat);
        grass.rotateX(-3.14/2, true);
        scene.add( grass );

        Geometry sphereGeometry = new SphereGeometry();
        Material sphereMaterial = new TextureMaterial( new Texture("src/images/grid.png") );
        sphere = new Mesh( sphereGeometry, sphereMaterial );
        sphere.setPosition( new Vector(-1.2, 1, 0) );
        scene.add( sphere );

        Geometry boxGeometry = new BoxGeometry();
        Material boxMat = new TextureMaterial(new Texture("src/images/crate.png"));
        box = new Mesh(boxGeometry, boxMat);
        box.setPosition(new Vector(1.2, 1, 0));
        scene.add( box );


        // HUD Scene
        hudScene = new Scene();
        hudCamera = new Camera();
        hudCamera.setOrthographic(0, 512, 0, 512, 1, -1);

        // Minimap
        Geometry rectangleGeometry = new RectangleGeometry(130, 130);
        Material rectangleMat = new SurfaceMaterial();
        rectangleMat.uniforms.get("baseColor").data = new Vector(0.2, 0.2, 0.2);
        Mesh mapHousing = new Mesh(rectangleGeometry, rectangleMat);
        mapHousing.setPosition(new Vector(447, 65, 0));
        hudScene.add(mapHousing);

        renderTarget = new RenderTarget(new Vector(512, 512));

        Geometry screenGeometry = new RectangleGeometry(120, 120);
        Material screenMat = new TextureMaterial(renderTarget.texture);
        Mesh miniMapDisplay = new Mesh(screenGeometry, screenMat);
        miniMapDisplay.setPosition(new Vector(447, 65, 0));
        hudScene.add(miniMapDisplay);

        // Create a cross hair in the minimap
        Mesh positionMarker1 = new AxesHelper(5, 5);
        positionMarker1.rotateX(Math.PI / 2, true);
        positionMarker1.setPosition(new Vector(447, 65, 0));
        hudScene.add(positionMarker1);

        Mesh positionMarker2 = new AxesHelper(5, 5);
        positionMarker2.rotateX(Math.PI / 2, true);
        positionMarker2.rotateY(Math.PI, true);
        positionMarker2.setPosition(new Vector(447, 65, 0));
        hudScene.add(positionMarker2);

        // Minimap camera
        skyCamera = new Camera(50, 1, 1, -1);
        skyCamera.setPosition( new Vector(0, 10, 0.1) );
        scene.add( skyCamera );
        skyCamera.lookAt( new Vector(0, 0, 0) );

        rig.add( skyCamera );

    }//end initialize

    public void update() {
        rig.update(input, deltaTime);

        // Spin sphere
        sphere.rotateY( 0.01337, true );

        // Render minimap
        grass.visible = false;
        sky.visible = false;
        renderer.renderTarget = renderTarget;
        renderer.clearColorBuffer = true;
        renderer.render(scene, skyCamera);

        // Render main scene
        grass.visible = true;
        sky.visible = true;
        renderer.renderTarget = null;
        renderer.clearColorBuffer = true;
        renderer.render(scene, camera);

        // Render hud scene
        renderer.clearColorBuffer = false;
        renderer.render(hudScene, hudCamera);

    }//end update

    public static void main(String[] args) {
        new testingMiniMap().run();
    }
}
