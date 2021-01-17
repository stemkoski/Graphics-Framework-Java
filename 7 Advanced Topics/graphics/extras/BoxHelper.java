package graphics.extras;

import graphics.core.Mesh;
import graphics.math.Vector;
import graphics.math.Box;
import graphics.material.LineMaterial;
import graphics.geometry.Geometry;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

public class BoxHelper extends Mesh
{
    public BoxHelper(Mesh mesh, Vector min, Vector max, Vector color, double lineWidth )
    {

        super( new Box(mesh, min, max, color, lineWidth), 
               new LineMaterial() );

        this.material.uniforms.get("useVertexColors").data = 1;
        this.material.renderSettings.get("lineWidth").data = lineWidth;
    }

    public BoxHelper(Mesh mesh)
    {
        this(mesh, new Vector(-1, -1, -1), new Vector(1, 1, 1), new Vector(0, 0, 0.5), 1 );
    }
