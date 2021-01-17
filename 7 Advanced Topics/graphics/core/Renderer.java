package graphics.core;

import static org.lwjgl.opengl.GL40.*;
import java.util.List;
import java.util.ArrayList;
import graphics.math.Vector;
import graphics.light.*;

public class Renderer
{
	public Vector clearColor;
	public boolean clearColorBuffer;
	public boolean clearDepthBuffer;
	public RenderTarget renderTarget;

	public boolean shadowsEnabled;
	public Shadow shadowObject;

	public Renderer()
	{
		// default clear color black
		glClearColor(0, 0, 0, 1);

		// support depth testing
		glEnable( GL_DEPTH_TEST );

		// support transparency
		glEnable( GL_BLEND );
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

		// required for antialiasing
		glEnable( GL_MULTISAMPLE );

		clearColorBuffer = true;
		clearDepthBuffer = true;
		renderTarget = null; // default to window

		shadowsEnabled = false;
		shadowObject = null;
	}

	public void setClearColor( Vector clearColor )
	{
		this.clearColor = clearColor;

		glClearColor((float)clearColor.values[0], (float)clearColor.values[1], 
				     (float)clearColor.values[2], (float)clearColor.values[3]);
	}

	public void enableShadows(DirectionalLight shadowLight, float strength, Vector resolution, float bias)
	{
		shadowsEnabled = true;
		shadowObject = new Shadow(shadowLight, strength, resolution, bias);
	}

	public void enableShadows(DirectionalLight shadowLight)
	{
		enableShadows(shadowLight, 0.5f, new Vector(512,512), 0.01f);
	}

	public void render(Scene scene, Camera camera)
	{
		// extract list of all Mesh objects in scene
		List<Object3D> descendentList = scene.getDescendentList();
		
		// extract list of all Mesh objects in scene
		ArrayList<Mesh> meshList = new ArrayList<Mesh>();
		for (Object3D obj : descendentList)
			if (obj instanceof Mesh)
				meshList.add( (Mesh)obj );

		// shadow pass start ----------------------------

		if (shadowsEnabled)
		{
			// set render target properties
			glBindFramebuffer(GL_FRAMEBUFFER, shadowObject.renderTarget.framebufferRef);
			glViewport(0,0, shadowObject.renderTarget.width, shadowObject.renderTarget.height);

			// set default color to white,
			//   used when no objects present to cast shadows
			glClearColor(1,1,1,1);

			glClear(GL_COLOR_BUFFER_BIT);
			glClear(GL_DEPTH_BUFFER_BIT);

			// reset original clear color
			glClearColor((float)clearColor.values[0], (float)clearColor.values[1], 
					     (float)clearColor.values[2], (float)clearColor.values[3]);

			// everything in the scene gets rendered with depthMaterial
			//   so only need to call glUseProgram & set matrices once
			glUseProgram( shadowObject.material.programRef );
			
			shadowObject.updateInternal();
			
			for (Mesh mesh : meshList)
			{
				// skip invisible meshes
				if (!mesh.visible)
					continue;
				
				// only triangle-based meshes cast shadows
				if (mesh.material.drawStyle != GL_TRIANGLES)
					continue;

				// bind VAO
				glBindVertexArray( mesh.vaoRef );
				
				// update transform data
				shadowObject.material.uniforms.get("modelMatrix").data = mesh.getWorldMatrix();
				
				// update uniforms (matrix data) stored in shadow material
				for (Uniform uniform : shadowObject.material.uniforms.values())
					uniform.uploadData();

				// no render settings to update

				glDrawArrays( GL_TRIANGLES, 0, mesh.geometry.vertexCount );
			}
		}

		// shadow pass end ----------------------------


		// activate render target
		if (renderTarget == null)
		{
			// set render target to window
			glBindFramebuffer(GL_FRAMEBUFFER, 0);
			glViewport(0,0, Base.windowWidth, Base.windowHeight); // TODO: set correctly...
		}
		else
		{
			// set render target properties
			glBindFramebuffer(GL_FRAMEBUFFER, renderTarget.framebufferRef);
			glViewport(0,0, renderTarget.width, renderTarget.height);
		}



		// clear color and/or depth buffers
		if (clearColorBuffer)
			glClear(GL_COLOR_BUFFER_BIT);
		if (clearDepthBuffer)
			glClear(GL_DEPTH_BUFFER_BIT);

		// update camera view (calculate inverse)
		camera.updateViewMatrix();

		// extract list of all Light objects in scene
		ArrayList<Light> lightList = new ArrayList<Light>();
		for (Object3D obj : descendentList)
			if (obj instanceof Light)
				lightList.add( (Light)obj );
		// scenes support 4 lights; precisely 4 must be present
		while ( lightList.size() < 4 )
			lightList.add( new Light() );

		for (Mesh mesh : meshList)
		{
			// if this object is not visible,
			//   continue to next object in list
			if (!mesh.visible)
				continue;

			glUseProgram( mesh.material.programRef );

			// bind VAO
			glBindVertexArray( mesh.vaoRef );
			
			// update uniform values stored outside of material
			mesh.material.uniforms.get("modelMatrix").data = mesh.getWorldMatrix();
			mesh.material.uniforms.get("viewMatrix").data = camera.viewMatrix;
			mesh.material.uniforms.get("projectionMatrix").data = camera.projectionMatrix;

			// if material uses light data, add lights from list
			if ( mesh.material.uniforms.containsKey("light0") )
			{
				for (int lightNumber = 0; lightNumber < 4; lightNumber++)
				{
					String lightName = "light" + lightNumber;
					Light lightObject = lightList.get(lightNumber);
					mesh.material.uniforms.get(lightName).data = lightObject;
				}
			}
			// add camera position if needed (specular lighting)
			if ( mesh.material.uniforms.containsKey("viewPosition") )
				mesh.material.uniforms.get("viewPosition").data = camera.getWorldPosition();
			
			// add shadow data if enabled and used by shader
			if ( shadowsEnabled &&  mesh.material.uniforms.containsKey("shadow0") )
				mesh.material.uniforms.get("shadow0").data = shadowObject;

			// update uniforms stored in material
			for (Uniform uniform : mesh.material.uniforms.values())
				uniform.uploadData();

			// update render settings
			for (RenderSetting setting : mesh.material.renderSettings.values()) 
				setting.apply();
			
			glDrawArrays( mesh.material.drawStyle, 0, mesh.geometry.vertexCount );
		}
	}

}