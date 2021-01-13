package graphics.math;

public class Vector
{
	public double[] values;

	// initialize zero vector with given size
    public Vector(int size)
    {
        values = new double[size];
    }

    // initialize with contents
    public Vector(double... v)
    {
        values = new double[v.length];
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