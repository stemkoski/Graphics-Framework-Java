package graphics.core;

import graphics.math.*;

public class CubeCamera extends Camera
{
    public Matrix viewMatrix;
    public Matrix projectionMatrix;

    public Vector[] faces = {
        new Vector( 1,  0,  0),
        new Vector(-1,  0,  0),
        new Vector( 0,  1,  0),
        new Vector( 0, -1,  0),
        new Vector( 0,  0,  1),
        new Vector( 0,  0, -1)
    };

    public CubeCamera()
    {
        viewMatrix = Matrix.makeIdentity();
        projectionMatrix = Matrix.makePerspective();
    }

    public CubeCamera(double angleOfView, double aspectRatio, double near, double far)
    {
        viewMatrix = Matrix.makeIdentity();
        projectionMatrix = Matrix.makePerspective(angleOfView, aspectRatio, near, far);
    }

    public void setPerspective(double angleOfView, double aspectRatio, double near, double far)
    {
        projectionMatrix = Matrix.makePerspective(angleOfView, aspectRatio, near, far);
    }

    public void setPerspective()
    {
        projectionMatrix = Matrix.makePerspective();
    }

    public void setOrthographic(double left, double right,
        double bottom, double top, double near, double far)
    {
        projectionMatrix = Matrix.makeOrthographic(left, right, bottom, top, near, far);
    }

    public void setOrthographic()
    {
        projectionMatrix = Matrix.makeOrthographic();
    }

    public void updateViewMatrix()
    {
        viewMatrix = getWorldMatrix().inverse();
    }

    public void turnCam(int index)
    {
        this.lookAt(faces[index]);
        updateViewMatrix();
    }

}
