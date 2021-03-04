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

public class CubeCamera extends Object3D
{

	public CubeCamera()
	{

	ArrayList<Vector> cams = [  new Vector( 1, 0, 0),
								new Vector(-1, 0, 0),
								new Vector( 0, 1, 0),
								new Vector( 0,-1, 0),
								new Vector( 0, 0, 1),
								new Vector( 0, 0,-1)
							];
		

		for(int i = 0; i < 6; i++){

			Camera c = new Camera().setOrthographic(0,800, 0,600, 1,-1);
			c.lookAt(cams.get(i));

			
		}

	}
}