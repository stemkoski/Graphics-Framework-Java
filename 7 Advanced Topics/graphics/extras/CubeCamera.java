/*In theory it should be six orthogonal cameras, 
rendering to the six textures of a cube texture -- 
we might need to make a cube version of the render target 
class as well? (You may notice that in the examples, 
	the reflection/refraction only takes into account the 
	skybox and not other objects in the scene -- 
	it's not really "true" reflection/refraction, but a CubeCamera would fix this.) */


package graphics.extras;

import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import graphics.math.Vector;
import graphics.core.*;
import graphics.math.*;
import graphics.geometry.*;
import graphics.material.*;
import graphics.extras.*;

public class CubeCamera
{

	/*
	RT 1 = X POS CAMERA 1
	RT 2 = X NEG CAMERA 2
	RT 3 = Y POS CAMERA 3
	RT 4 = X NEG CAMERA 4
	RT 5 = Z POS CAMERA 5
	RT 6 = X NEG CAMERA 6
	*/
	
	List<RenderTarget> renderTargets = new ArrayList<RenderTarget>();
	List<Camera> cameras = new ArrayList<Camera>();

	public CubeCamera()
	{
		for(int i = 0; i < 6; i++){

			Camera c = new Camera().setOrthographic(0,800, 0,600, 1,-1);
			c.lookAt(new Vector(0, 0, 0));
			cameras.add( c );
			RenderTarget r = new RenderTarget( new Vector(800, 600) );
			renderTargets.add( r ); 

		}

	}
}