package graphics.math;

public class Vector
{
	public double[] values;

    public Vector(int size)
    {
        values = new double[size];
    }
    
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

    public static double dot(Vector v, Vector w)
    {
		double c = 0;
        for (int i = 0; i < v.values.length; i++)
            c += v.values[i] * w.values[i];
        return c;
    }

}