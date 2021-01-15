package graphics.light;

import graphics.math.Vector;

public class PointLight extends Light
{
	public PointLight(Vector color, Vector position, Vector attenuation)
	{
		lightType = Light.POINT;
		this.color = color;
		setPosition(position);
		this.attenuation = attenuation;
	}

	public PointLight(Vector color, Vector position)
	{
		this(color, position, new Vector(1, 0, 0.1));
	}

}