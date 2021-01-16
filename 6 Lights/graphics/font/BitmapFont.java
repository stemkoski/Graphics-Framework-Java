package graphics.font;

import java.util.HashMap;
import java.io.File;
import java.util.Scanner;

import graphics.core.Texture;
import graphics.math.Vector;

public class BitmapFont
{
	// data to retrieve and display individual characters
	public HashMap<Integer, BitmapChar> charData;

	// data fields from common line
	public int imageWidth;
	public int imageHeight;
	public int lineHeight;
	// padding for each character (top, right, bottom, left)
	public int[] padding;

	// stores image data containing all characters
	public Texture texture;

	// assumes all character images are packed into a single image
	public BitmapFont(String fontFileName, String imageFileName)
	{
		charData = new HashMap<Integer, BitmapChar>();
		texture = new Texture(imageFileName);

		File file = new File(fontFileName);
		Scanner scan = null;
		try
		{
	    	scan = new Scanner(file); 
  		}
  		catch (Exception ex)
  		{
  			ex.printStackTrace();
  		}

  		// parse font information text file
   		while ( scan.hasNextLine() ) 
   		{
   			String line = scan.nextLine();

   			// data that applies to all characters
   			if (line.startsWith("common "))
   			{
   				String[] tokens = line.split("\\s+");

   				// lineHeight=56 base=40 scaleW=512 scaleH=512
   				for (String token : tokens)
   				{
   					if (token.startsWith("scaleW="))
	   					imageWidth = Integer.parseInt( token.replace("scaleW=","") );
   					else if (token.startsWith("scaleH="))
	   					imageHeight = Integer.parseInt( token.replace("scaleH=","") );
   					else if (token.startsWith("lineHeight="))
	   					lineHeight = Integer.parseInt( token.replace("lineHeight=","") );
	   			}
   			}

   			if (line.startsWith("info "))
   			{
   				String[] tokens = line.split("\\s+");

   				// lineHeight=56 base=40 scaleW=512 scaleH=512
   				for (String token : tokens)
   				{
   					if (token.startsWith("padding="))
	   				{
	   					String[] strArray = token.replace("padding=","").split(",");
	   					padding = new int[4];
	   					for (int i=0; i<4; i++)
	   						padding[i] = Integer.parseInt( strArray[i] );
	   				}
	   			}
   			}

   			// individual character data
   			if (line.startsWith("char "))
   			{
   				String[] tokens = line.split("\\s+");
   				BitmapChar bc = new BitmapChar();

   				for (String token : tokens)
   				{
   					if (token.startsWith("id="))
	   					bc.id = Integer.parseInt( token.replace("id=","") );
   					else if (token.startsWith("x="))
	   					bc.imageX = Integer.parseInt( token.replace("x=","") );
   					else if (token.startsWith("y="))
	   					bc.imageY = Integer.parseInt( token.replace("y=","") );
   					else if (token.startsWith("width="))
	   					bc.imageWidth = Integer.parseInt( token.replace("width=","") );
   					else if (token.startsWith("height="))
	   					bc.imageHeight = Integer.parseInt( token.replace("height=","") );
   					else if (token.startsWith("xoffset="))
	   					bc.xOffset = Integer.parseInt( token.replace("xoffset=","") );
   					else if (token.startsWith("yoffset="))
	   					bc.yOffset = Integer.parseInt( token.replace("yoffset=","") );
   					else if (token.startsWith("xadvance="))
	   					bc.xAdvance = Integer.parseInt( token.replace("xadvance=","") );
   				}

      			charData.put( bc.id, bc );
      		}

  		}
  		// done parsing text file
	}
}