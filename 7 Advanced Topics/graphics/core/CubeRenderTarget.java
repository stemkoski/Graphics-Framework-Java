package graphics.core;

import static org.lwjgl.opengl.GL40.*;
import graphics.math.Vector;


public class CubeRenderTarget
{
	// store associated texture dimensions
	public int width;
	public int height;

	public Texture[] texture;

	public int framebufferRef;

	public CubeRenderTarget(Vector resolution, int magFilter, int minFilter, int wrap)
	{
		width = (int)resolution.values[0];
		height = (int)resolution.values[1];

		// generate an empty texture // here is where the changes are going to be

		for (int i = 0; i < 6; i++){

		texture[i] = new Texture(width, height, magFilter, minFilter, wrap);

		// create a framebuffer
		framebufferRef = glGenFramebuffers();
		glBindFramebuffer(GL_FRAMEBUFFER, framebufferRef);

		// configure color buffer to use this texture
		glFramebufferTexture(GL_FRAMEBUFFER, GL_COLOR_ATTACHMENT0, texture[i].textureRef, 0);

		
		// generate a buffer to store depth information
		int depthBufferRef = glGenRenderbuffers();
		glBindRenderbuffer(GL_RENDERBUFFER, depthBufferRef);
		
		glRenderbufferStorage(GL_RENDERBUFFER, GL_DEPTH_COMPONENT, width, height);
		glFramebufferRenderbuffer(GL_FRAMEBUFFER, GL_DEPTH_ATTACHMENT, GL_RENDERBUFFER, depthBufferRef);

		// check framebuffer status
		int status = glCheckFramebufferStatus(GL_FRAMEBUFFER);
		if (status != GL_FRAMEBUFFER_COMPLETE)
			System.out.println("Framebuffer status error: " + status);
		}
	}

	public CubeRenderTarget(Vector resolution)
	{
		this(resolution, GL_NEAREST, GL_NEAREST, GL_CLAMP_TO_EDGE);
	}

}