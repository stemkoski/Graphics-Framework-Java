package graphics.core;

public class Camera extends Object3D
{
	public Matrix viewMatrix;
	public Matrix projectionMatrix;

	public Camera()
	{
		viewMatrix = Matrix.makeIdentity();
		projectionMatrix = Matrix.makePerspective();
	}

	public Camera(float angleOfView, float aspectRatio, float near, float far)
	{
		viewMatrix = Matrix.makeIdentity();
		projectionMatrix = Matrix.makePerspective(angleOfView, aspectRatio, near, far);
	}

	public void updateViewMatrix()
	{
		viewMatrix = getWorldMatrix().inverse();
	}

}
