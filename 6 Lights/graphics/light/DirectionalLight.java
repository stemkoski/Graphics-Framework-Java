package graphics.light;

public class DirectionalLight extends Light
{
	public DirectionalLight(Vector color, Vector direction)
	{
		lightType = Light.DIRECTIONAL;
		this.color = color;
		setDirection(direction);
	}
}