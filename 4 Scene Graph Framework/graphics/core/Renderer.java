package graphics.core;

import static org.lwjgl.opengl.GL40.*;
import java.util.List;
import java.util.ArrayList;
import graphics.math.Vector;

public class Renderer
{
	public Renderer()
	{
		glEnable( GL_DEPTH_TEST );
		
		// required for antialiasing
		glEnable( GL_MULTISAMPLE );
	}

	public void setClearColor( Vector color )
	{
		glClearColor(
			(float)color.values[0], (float)color.values[1], (float)color.values[2], 1);
	}

	public void render(Scene scene, Camera camera)
	{
		// clear color and depth buffers
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

		// update camera view (calculate inverse)
		camera.updateViewMatrix();

		// extract list of all Mesh objects in scene
		List<Object3D> descendentList = scene.getDescendentList();
		ArrayList<Mesh> meshList = new ArrayList<Mesh>();

		for (Object3D obj : descendentList)
			if (obj instanceof Mesh)
				meshList.add( (Mesh)obj );


		// meshFilter = lambda x : isinstance(x, Mesh)
		/// meshList = list( filter( meshFilter, descendentList ) )

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