package graphics.material;

import static org.lwjgl.opengl.GL40.*;

public class PointMaterial extends BasicMaterial
{
	public PointMaterial()
	{
		drawStyle = GL_POINTS;
		addRenderSetting( "pointSize", 16 );
		addRenderSetting( "roundedPoints", true );
	}
}