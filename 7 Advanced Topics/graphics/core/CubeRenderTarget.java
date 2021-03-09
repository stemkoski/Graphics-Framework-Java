package graphics.core;

import static org.lwjgl.opengl.GL40.*;
import graphics.math.Vector;
import graphics.core.CubeCamera;
import graphics.core.*;
import graphics.material.*;

public class CubeRenderTarget
{
	// store associated texture dimensions
	public int width;
	public int height;

	public CubeTexture ct;
	public CubeCamera camera;
	public Texture texture;
	public RenderTarget renderTarget;

	public int framebufferRef;


	public CubeRenderTarget(Vector resolution, int magFilter, int minFilter, int wrap)
	{
		width = (int)resolution.values[0];
		height = (int)resolution.values[1];

		camera = new CubeCamera();
		ct = new CubeTexture(width, height, magFilter, minFilter, wrap);


 		for (int i = 0; i < 6; i++) {

 		texture = new Texture(width, height, magFilter, minFilter, wrap);

 		renderTarget = new RenderTarget( new Vector(512,512) );

 		Material m = new TextureMaterial( renderTarget.texture );
 			
		glFramebufferTexture2D(GL_FRAMEBUFFER, GL_COLOR_ATTACHMENT0, 
			GL_TEXTURE_CUBE_MAP_POSITIVE_X + i, texture.textureRef, 0);

			camera.turnCam(i);
		}
	
	}

	public CubeRenderTarget(Vector resolution)
	{
		this(resolution, GL_NEAREST, GL_NEAREST, GL_CLAMP_TO_EDGE);
	}

}