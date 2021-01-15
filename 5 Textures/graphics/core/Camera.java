package graphics.core;

import graphics.math.Matrix;

public class Camera extends Object3D
{
	public Matrix viewMatrix;
	public Matrix projectionMatrix;

	public Camera()
	{
		viewMatrix = Matrix.makeIdentity();
		projectionMatrix = Matrix.makePerspective();
	}

	public Camera(double angleOfView, double aspectRatio, double near, double far)
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

}
