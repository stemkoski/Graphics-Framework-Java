package graphics.font;

import graphics.core.Mesh; 
import graphics.material.TextureMaterial;

public class TextMesh extends Mesh 
{
	public TextMesh(String text, BitmapFont bitmapFont)
	{
		super( new TextGeometry(text, bitmapFont),
			   new TextureMaterial( bitmapFont.texture ) );
	}

	public void setText(String text)
	{
		((TextGeometry)geometry).generateAttributeData(text);
	}
}