package graphics.extras;

import graphics.core.Object3D;
import java.util.HashMap;
import static org.lwjgl.glfw.GLFW.*;
import graphics.core.Input;

public class MovementRig extends Object3D
{
	public Object3D lookAttachment;
	public double unitsPerSecond;
	public double degreesPerSecond;
	public HashMap<String, Integer> keyMap;

	public MovementRig(double unitsPerSecond, double degreesPerSecond)
	{
		this.lookAttachment = new Object3D();
		this.add(lookAttachment);

		this.unitsPerSecond = unitsPerSecond;
		this.degreesPerSecond = degreesPerSecond;
		
		this.keyMap = new HashMap<String, Integer>();

		// customizable key mappings
		// defaults: WASDRF (move), QE (turn), TG (look)
		this.keyMap.put("move_forwards",  GLFW_KEY_W);
		this.keyMap.put("move_backwards", GLFW_KEY_S);
		this.keyMap.put("move_left",      GLFW_KEY_A);
		this.keyMap.put("move_right",     GLFW_KEY_D);
		this.keyMap.put("move_up",        GLFW_KEY_R);
		this.keyMap.put("move_down",      GLFW_KEY_F);
		this.keyMap.put("turn_left",      GLFW_KEY_Q);
		this.keyMap.put("turn_right",     GLFW_KEY_E);
		this.keyMap.put("look_up",        GLFW_KEY_T);
		this.keyMap.put("look_down",      GLFW_KEY_G);
	}

	public MovementRig()
	{
		this(1, 60);
	}

	public void attach(Object3D obj)
	{
		lookAttachment.add(obj);
	}

	public void update(Input input, double deltaTime)
	{
		double moveAmount = unitsPerSecond * deltaTime;
		double rotateAmount = Math.toRadians(degreesPerSecond) * deltaTime;
		if (input.isKeyPressed(keyMap.get("move_forwards")))
			translate( 0, 0, -moveAmount, true );
		if (input.isKeyPressed(keyMap.get("move_backwards")))
			translate( 0, 0, moveAmount, true );
		if (input.isKeyPressed(keyMap.get("move_right")))
			translate( moveAmount, 0, 0, true );
		if (input.isKeyPressed(keyMap.get("move_left")))
			translate( -moveAmount, 0, 0, true );
		if (input.isKeyPressed(keyMap.get("move_up")))
			translate( 0, moveAmount, 0, true );
		if (input.isKeyPressed(keyMap.get("move_down")))
			translate( 0, -moveAmount, 0, true );
		if (input.isKeyPressed(keyMap.get("turn_right")))
			rotateY( -rotateAmount, true );
		if (input.isKeyPressed(keyMap.get("turn_left")))
			rotateY( rotateAmount, true );
		if (input.isKeyPressed(keyMap.get("look_up")))
			lookAttachment.rotateX( rotateAmount, true );
		if (input.isKeyPressed(keyMap.get("look_down")))
			lookAttachment.rotateX( -rotateAmount, true );
	}
}
