package graphics.geometry;

import graphics.math.Vector;

public class TorusGeometry extends SurfaceGeometry
{
	public TorusGeometry( double centralRadius, double tubeRadius,
		int tubularSegments, int radialSegments, double scale)
	{
		super( (u,v) -> { return new Vector(
			(centralRadius + tubeRadius*Math.cos(v)) * Math.cos(u) * scale,
            (centralRadius + tubeRadius*Math.cos(v)) * Math.sin(u) * scale,
            tubeRadius * Math.sin(v) * scale); },
				0, 2*Math.PI, tubularSegments,
				0, 2*Math.PI, radialSegments  );
	}

	public TorusGeometry()
	{
		this(0.60, 0.40, 48, 24, 1);
	}
}
