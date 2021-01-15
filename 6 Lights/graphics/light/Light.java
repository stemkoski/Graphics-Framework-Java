package graphics.light;

import graphics.core.Object3D;
import graphics.math.Vector;

public class Light extends Object3D
{
	public static int AMBIENT = 1;
	public static int DIRECTIONAL = 2;
	public static int POINT = 3;

	public int lightType;
	public Vector color;
	public Vector attenuation;

	public Light()
	{
		lightType = 0;
		color = new Vector(1, 1, 1);
		attenuation = new Vector(1, 0, 0);
	}
}