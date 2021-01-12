package graphics.core;

import static org.lwjgl.opengl.GL40.*;

public class RenderSetting
{
	// setting name
	public String settingName;

	// default data value
	public Object data;

	public RenderSetting(String settingName, Object data)
	{
		this.settingName = settingName;
		this.data = data;
	}

	public void apply()
	{
		if (settingName.equals("pointSize"))
		{
			glPointSize( (int)data );
		}
		else if (settingName.equals("roundedPoints"))
		{
			if ( (boolean)data )
				glEnable(GL_POINT_SMOOTH);
			else
				glDisable(GL_POINT_SMOOTH);
		}
		else if (settingName.equals("lineWidth"))
		{
			glLineWidth( (int)data );
		}
		else if (settingName.equals("doubleSide"))
		{
			if ( (boolean)data )
				glDisable(GL_CULL_FACE);
			else
				glEnable(GL_CULL_FACE);
		}
		else if (settingName.equals("wireframe"))
		{
			if ( (boolean)data )
				glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);
			else
				glPolygonMode(GL_FRONT_AND_BACK, GL_FILL);
		}
		else 
		{
			System.out.println("Unknown render setting: " + settingName);
		}
	}

}