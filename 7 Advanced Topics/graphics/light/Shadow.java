package graphics.light;

import static org.lwjgl.opengl.GL40.*;
import graphics.light.DirectionalLight;
import graphics.core.Camera;
import graphics.core.RenderTarget;
import graphics.material.DepthMaterial;
import graphics.math.Vector;

public class Shadow
{
	public DirectionalLight lightSource;

	// camera used to render scene from perspective of light
	public Camera camera;

	// target used during the shadow pass,
	//   contains depth texture
	public RenderTarget renderTarget;

	// render only depth data to target texture
	public DepthMaterial material;

	// controls darkness of shadow
	public float strength;

	// used to avoid visual artifacts
	//   due to rounding/sampling precision issues
	public float bias;

	// constructor
	public Shadow(DirectionalLight lightSource, float strength, Vector resolution, float bias)
	{
		this.lightSource = lightSource;
		this.camera = new Camera();
		setCameraBounds(-5,5, -5,5, 0,20);
		this.lightSource.add( this.camera );

		this.renderTarget = new RenderTarget( resolution, GL_NEAREST, GL_NEAREST, GL_CLAMP_TO_BORDER );
		this.material = new DepthMaterial();
		this.strength = strength;
		this.bias = bias;
	}

	public Shadow(DirectionalLight lightSource)
	{
		this(lightSource, 0.5f, new Vector(512,512), 0.01f);
	}

	public void setCameraBounds(double left, double right, 
		double bottom, double top, double near, double far)
	{
		camera.setOrthographic(left, right, bottom, top, near, far);
	}

	public void updateInternal()
	{
		camera.updateViewMatrix();
		material.uniforms.get("viewMatrix").data = camera.viewMatrix;
		material.uniforms.get("projectionMatrix").data = camera.projectionMatrix;
	}
}