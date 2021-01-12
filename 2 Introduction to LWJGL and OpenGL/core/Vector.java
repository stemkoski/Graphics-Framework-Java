package core;

public class Vector
{
	public float[] values;

	public Vector(float... v)
    {
    	values = new float[v.length];
        for (int i = 0; i < v.length; i++)
        	values[i] = v[i];
    }

    public String toString()
    {
    	String s = "[";
    	for (int i = 0; i < values.length; i++)
            s += String.format("%6.2f", values[i]);
        s += "]";
        return s;
    }
}