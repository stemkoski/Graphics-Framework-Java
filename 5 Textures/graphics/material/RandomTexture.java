import graphics.core.Texture;
import graphics.math.Vector;

import static org.lwjgl.opengl.GL40.*;

public class RandomTexture extends Material {
    public RandomTexture() {
        super(
                "graphics/material/RandomTexture.vert",
                "graphics/material/RandomTexture.frag");

        addRenderSetting( "doubleSide", true );
        addRenderSetting( "wireframe", false );
        addRenderSetting( "lineWidth", 1 );

        // Defaults
        addUniform("float", "time", (float)0);

        locateUniforms();
    }
}
