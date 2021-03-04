package graphics.material;

import static org.lwjgl.opengl.GL40.*;
import graphics.core.CubeTexture2;
import graphics.math.Vector;

// uses vertex position as texture coordinates
public class CubeMaterial extends Material
{
	public CubeMaterial(CubeTexture2 texture)
	{
		super(
			"graphics/material/CubeMaterial.vert",
			"graphics/material/CubeMaterial.frag"  );
		
		addUniform("vec3", "baseColor", new Vector(1,1,1) );
		addUniform("samplerCube", "cubeTex", new Vector(texture.textureRef, 1));
		locateUniforms();

		addRenderSetting( "doubleSide", true );
		addRenderSetting( "wireframe", false );
		addRenderSetting( "lineWidth", 1 );
	}
}