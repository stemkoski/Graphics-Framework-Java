package graphics.core;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL40.*;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import org.lwjgl.BufferUtils;
import static org.lwjgl.stb.STBImage.*;

import java.util.HashMap;

// imports for TEST 1
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.ByteArrayOutputStream;

public class Texture
{
	// pygame object for storing pixel data;
    // can load from image or manipulate directly
    // surface

	// reference of available texture from GPU
	public int textureRef;

	public HashMap<String, Integer> properties;

	public BufferedImage image;

	public String fileName;

	public Texture(String fileName)
	{
		textureRef = glGenTextures();
		
		properties = new HashMap<String, Integer>();
		// default property values
		properties.put("magFilter", GL_LINEAR);
		properties.put("minFilter", GL_LINEAR_MIPMAP_LINEAR);
		properties.put("wrap", GL_REPEAT);

		this.fileName = fileName;

		if (fileName != null)
		{
			loadImage(fileName);
			uploadData();
		}
	}

	public void loadImage(String fileName)
	{
		/*
		// TEST 1 - works but no longer useful
		try 
		{
    		File imageFile = new File(fileName);
   			image = ImageIO.read(imageFile); 
    		System.out.println(image);
		} 
		catch (Exception ex) 
		{ 
    		ex.printStackTrace(); 
		}
		*/
	}

	// upload pixel data to GPU
	public void uploadData()
	{
		IntBuffer widthBuf  = BufferUtils.createIntBuffer(1);
        IntBuffer heightBuf = BufferUtils.createIntBuffer(1);
        IntBuffer compBuf   = BufferUtils.createIntBuffer(1);

        // prevents inverted images
		stbi_set_flip_vertically_on_load(true);
		// last argument as 4 -> generates RGBA formatted buffer
		ByteBuffer pixelData = stbi_load(fileName, widthBuf, heightBuf, compBuf, 4);

		int width = widthBuf.get();
		int height = heightBuf.get();
		
		/* 
		// TEST 1 - FAIL

		// store image dimensions
		int width = image.getWidth();
		int height = image.getHeight();

		// convert image data to byte buffer
		ByteBuffer pixelData = null;

		try
		{
			ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
	  		ImageIO.write(image, "png", byteStream);
	  		byte[] byteArray = byteStream.toByteArray();
	 		pixelData = ByteBuffer.wrap(byteArray);
	 	}
 		catch (Exception ex)
 		{
 			ex.printStackTrace();
 		}
		*/

		// specify texture used by the following functions
		glBindTexture(GL_TEXTURE_2D, textureRef);

		// send pixel data (ByteBuffer) to texture buffer
		glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA,	width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, pixelData);
		// generate mipmap image from uploaded pixel data
		glGenerateMipmap(GL_TEXTURE_2D);
		// specify technique for magnifying/minifying textures
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, properties.get("magFilter") );
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, properties.get("minFilter") );
		// specify what happens to texture coordinates outside range [0, 1]
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, properties.get("wrap") );
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, properties.get("wrap") );
		// set default border color to white; important for rendering shadows
		float[] borderColor = new float[] {1,1,1,1};
		glTexParameterfv(GL_TEXTURE_2D, GL_TEXTURE_BORDER_COLOR, borderColor);
		
	}

}
