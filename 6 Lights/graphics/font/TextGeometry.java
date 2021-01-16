package graphics.font;

import graphics.geometry.Geometry; 

import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import graphics.math.Vector;

public class TextGeometry extends Geometry 
{
	public BitmapFont bitmapFont;
	public int spacingAdjust;

	public TextGeometry(String text, BitmapFont bitmapFont)
	{
		this.bitmapFont = bitmapFont;

		// depends on horizontal padding/spacing used in bitmap font image
		this.spacingAdjust = bitmapFont.padding[1] + bitmapFont.padding[3];

		// initialize vertex buffers
		addAttribute("vec3", "vertexPosition", new float[] {0,0,0} );
		addAttribute("vec2", "vertexUV", new float[] {0,0} );
		generateAttributeData(text);
	}

	// can use for changing text later?
	public void generateAttributeData(String text)
	{
		int xPosition = 0;
		int yPosition = 0;

		List<Vector> positionList = new ArrayList<Vector>();
		List<Vector> uvList = new ArrayList<Vector>();

		for (int i = 0; i < text.length(); i++)
		{
			char c = text.charAt(i);
			int id = (int)c;
			if ( bitmapFont.charData.containsKey(id) )
			{
				BitmapChar bc = bitmapFont.charData.get(id);

				// P2---P3
				// |  /  |
				// P0---P1
				double x = (double)(xPosition + bc.xOffset) / bitmapFont.lineHeight; 
				double y = 1 - (double)(bc.yOffset) / bitmapFont.lineHeight;
				double w = (double)bc.imageWidth / bitmapFont.lineHeight;
				double h = (double)bc.imageHeight / bitmapFont.lineHeight;
				Vector P0 = new Vector(x,   y-h, 0);
                Vector P1 = new Vector(x+w, y-h, 0);
                Vector P2 = new Vector(x,   y,   0);
                Vector P3 = new Vector(x+w, y,   0);
				positionList.addAll( Arrays.asList(P0,P1,P3, P0,P3,P2) );

				xPosition += bc.xAdvance - spacingAdjust;

				// T2---T3
				// |  /  |
				// T0---T1
				// note: BitmapChar data is from upper-left corner in image file 
				double u = (double)bc.imageX / bitmapFont.imageWidth;
				double v = 1 - (double)bc.imageY / bitmapFont.imageHeight;
				w = (double)bc.imageWidth / bitmapFont.imageWidth;
				h = (double)bc.imageHeight / bitmapFont.imageHeight;
				Vector T0 = new Vector(u,   v-h);
                Vector T1 = new Vector(u+w, v-h);
                Vector T2 = new Vector(u,   v);
                Vector T3 = new Vector(u+w, v);
				uvList.addAll( Arrays.asList(T0,T1,T3, T0,T3,T2) );

			}
		}

		float[] positionData = Vector.flattenList(positionList);
		float[] uvData = Vector.flattenList(uvList);

		attributes.get("vertexPosition").dataArray = positionData;
		attributes.get("vertexUV").dataArray = uvData;

		attributes.get("vertexPosition").uploadData();
		attributes.get("vertexUV").uploadData();

		vertexCount = positionList.size();		
	}
}