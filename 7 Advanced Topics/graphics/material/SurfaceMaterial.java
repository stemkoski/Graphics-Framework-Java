package graphics.material;

import static org.lwjgl.opengl.GL40.*;

public class SurfaceMaterial extends BasicMaterial
{
	public SurfaceMaterial()
	{
		drawStyle = GL_TRIANGLES;
		addRenderSetting( "doubleSide", true );
		addRenderSetting( "wireframe", false );
		addRenderSetting( "lineWidth", 1 );
	}
}