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

	public void updateViewMatrix()
	{
		viewMatrix = getWorldMatrix().inverse();
	}

}
