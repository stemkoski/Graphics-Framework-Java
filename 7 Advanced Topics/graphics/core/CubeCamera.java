package graphics.core;

/*In theory it should be six orthogonal cameras,
rendering to the six textures of a cube texture --
we might need to make a cube version of the render target
class as well? (You may notice that in the examples,
	the reflection/refraction only takes into account the
	skybox and not other objects in the scene --
	it's not really "true" reflection/refraction, but a CubeCamera would fix this.) */

import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import graphics.math.Vector;
import graphics.core.*;
import graphics.math.*;
import graphics.geometry.*;
import graphics.material.*;
import graphics.extras.*;

public class CubeCamera extends Camera
{
    public Camera camera;

    public Matrix viewMatrix;
    public Matrix projectionMatrix;

    public Vector[] faces = { new Vector( 1,  0,  0),
            new Vector(-1,  0,  0),
            new Vector( 0,  1,  0),
            new Vector( 0, -1,  0),
            new Vector( 0,  0,  1),
            new Vector( 0,  0, -1),
    };

    public CubeCamera()
    {
        camera = new Camera();
        camera.setPosition(new Vector(0, 0, 0));
        viewMatrix = Matrix.makeIdentity();
        projectionMatrix = Matrix.makePerspective(90, 1, 0.1, 1000);
    }

    public void updateViewMatrix()
    {
        viewMatrix = getWorldMatrix().inverse();
    }

    public void turnCam(int index){

        camera.lookAt(faces[index]);

    }
}