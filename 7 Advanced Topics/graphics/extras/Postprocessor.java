package graphics.extras;

import static org.lwjgl.opengl.GL40.*;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import graphics.math.Vector;
import graphics.core.*;
import graphics.geometry.Geometry;
import graphics.material.Material;

public class Postprocessor
{
	public Renderer renderer;
	public List<Scene> sceneList;
	public List<Camera> cameraList;
	public List<RenderTarget> renderTargetList;
	public RenderTarget finalRenderTarget;

	// reusable elements for added effects
	public Camera orthoCamera;
	public Geometry rectangleGeom;

	public Postprocessor(Renderer renderer, Scene scene, Camera camera, RenderTarget finalRenderTarget)
	{
		this.renderer = renderer;
		sceneList = new ArrayList<Scene>();
		sceneList.add( scene );
		cameraList = new ArrayList<Camera>();
		cameraList.add( camera );
		renderTargetList = new ArrayList<RenderTarget>();
		renderTargetList.add( finalRenderTarget );

		this.finalRenderTarget = finalRenderTarget; // not needed yet
		
		orthoCamera = new Camera();
		orthoCamera.setOrthographic(-1,1, -1,1, 1,-1); // aligned with clip space by default
	
		// generate a rectangle already aligned with clip space;
		// no matrix transformations will be applied
		rectangleGeom = new Geometry();
		Vector P0 = new Vector(-1, -1);
		Vector P1 = new Vector( 1, -1);
		Vector P2 = new Vector(-1,  1);
		Vector P3 = new Vector( 1,  1);

		Vector T0 = new Vector(0, 0);
		Vector T1 = new Vector(1, 0);
		Vector T2 = new Vector(0, 1);
		Vector T3 = new Vector(1, 1);

		List positionList = Arrays.asList(P0,P1,P3, P0,P3,P2);
		float[] positionData = Vector.flattenList(positionList);
		
		List uvList = Arrays.asList(T0,T1,T3, T0,T3,T2);
		float[] uvData = Vector.flattenList(uvList);

		rectangleGeom.addAttribute("vec2", "vertexPosition", positionData);
		rectangleGeom.addAttribute("vec2", "vertexUV", uvData);
		rectangleGeom.vertexCount = 6;
	}

	public void addEffect(Material effect)
	{
		Scene postScene = new Scene();
		Vector resolution = new Vector(Base.windowWidth, Base.windowHeight);
		RenderTarget target = new RenderTarget( resolution );

		// change the previous entry in the render target list
		//   to this newly created render target
		int size = renderTargetList.size();
		renderTargetList.set( size-1, target );
		// the effect in this render pass will use
		//   the texture that was written to in the previous render pass
		effect.uniforms.get("tex").data = new Vector(target.texture.textureRef, 1);
		Mesh mesh = new Mesh( rectangleGeom, effect );
		postScene.add( mesh );

		sceneList.add( postScene );
		cameraList.add( orthoCamera );
		renderTargetList.add( finalRenderTarget );
	}

	public void render()
	{
		int passes = sceneList.size();
		for (int n = 0; n < passes; n++)
		{
			renderer.renderTarget = renderTargetList.get(n);
			renderer.render( sceneList.get(n), cameraList.get(n) );
		}
	}
}