package graphics.core;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL40.*;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import org.lwjgl.BufferUtils;
import static org.lwjgl.stb.STBImage.*;


public class Texture
{
	// reference of available texture from GPU
	public int textureRef;

	public int magFilter;
	public int minFilter;
	public int wrap;

	public int width, height;
	public ByteBuffer pixelData;

	// load texture from file
	public Texture(String fileName)
	{
		magFilter = GL_LINEAR;
		minFilter = GL_LINEAR_MIPMAP_LINEAR;
		wrap = GL_REPEAT;

		IntBuffer widthBuf  = BufferUtils.createIntBuffer(1);
        IntBuffer heightBuf = BufferUtils.createIntBuffer(1);
        IntBuffer compBuf   = BufferUtils.createIntBuffer(1);

        // prevents inverted images
		stbi_set_flip_vertically_on_load(true);
		// last argument as 4 -> generates RGBA formatted buffer
		pixelData = stbi_load(fileName, widthBuf, heightBuf, compBuf, 4);

		width = widthBuf.get();
		height = heightBuf.get();

		uploadData();
	}

	// generate an empty texture - used by RenderTarget class
	public Texture(int width, int height, int magFilter, int minFilter, int wrap)
	{
		this.width = width;
		this.height = height;

		this.magFilter = magFilter;
		this.minFilter = minFilter;
		this.wrap = wrap;

		uploadData();
	}

	public Texture(int width, int height)
	{
		this(width, height, GL_LINEAR, GL_LINEAR, GL_CLAMP_TO_EDGE);
	}

	// upload pixel data to GPU
	public void uploadData()
	{
		textureRef = glGenTextures();

		// specify texture used by the following functions
		glBindTexture(GL_TEXTURE_2D, textureRef);

		// send pixel data (ByteBuffer) to texture buffer
		if (pixelData != null)
			glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA,	width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, pixelData);
		else
			glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, 0);

		// generate mipmap image from uploaded pixel data
		glGenerateMipmap(GL_TEXTURE_2D);
		// specify technique for magnifying/minifying textures
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, magFilter );
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, minFilter );
		// specify what happens to texture coordinates outside range [0, 1]
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, wrap );
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, wrap );
		// set default border color to white; important for rendering shadows
		float[] borderColor = new float[] {1,1,1,1};
		glTexParameterfv(GL_TEXTURE_2D, GL_TEXTURE_BORDER_COLOR, borderColor);
	}

}
