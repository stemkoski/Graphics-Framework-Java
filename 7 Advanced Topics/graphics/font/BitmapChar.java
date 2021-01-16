package graphics.font;

public class BitmapChar
{
	// character code
	public int id;

	// data for extracting subimage from image file
	public int imageX;
	public int imageY;
	public int imageWidth;
	public int imageHeight;

	// data used when assembling image
	public int xOffset;
	public int yOffset;
	public int xAdvance;

	public BitmapChar()
	{}

	public BitmapChar(int id, int x, int y, int w, int h, int xOff, int yOff, int xAdv)
	{
		this.id = id;
		this.imageX = x;
		this.imageY = y;
		this.imageWidth = w;
		this.imageHeight = h;
		this.xOffset = xOff;
		this.yOffset = yOff;
		this.xAdvance = xAdv;
	}

	public String toString()
	{
		return "id:" + id + " x:" + imageX + " y:" + imageY +
			   " width:" + imageWidth + " height:" + imageHeight +
			   " xoffset:" + xOffset + " yoffset:" + yOffset + " xadvance:" + xAdvance;
	}
}