package graphics.math;

import java.util.List;

public class Vector
{
	public float[] values;

    // TODO: add to Ch 2 & 3
    // initialize with array of zeroes
    public Vector(int size)
    {
        values = new float[3];
    }

    // initialize with contents
	public Vector(float... v)
    {
    	values = new float[v.length];
        for (int i = 0; i < v.length; i++)
        	values[i] = v[i];
    }

    // initialize with contents
    public Vector(double... v)
    {
        values = new float[v.length];
        for (int i = 0; i < v.length; i++)
            values[i] = (float)v[i];
    }

    public String toString()
    {
    	String s = "[";
    	for (int i = 0; i < values.length; i++)
            s += String.format("%6.2f", values[i]);
        s += "]";
        return s;
    }

    public static float dot(Vector v, Vector w)
    {
		float c = 0;
        for (int i = 0; i < v.values.length; i++)
            c += v.values[i] * w.values[i];
        return c;
    }

    // used by geometry classes
    public static float[] flattenList(List<Vector> vecList)
    {
        int listSize = vecList.size();
        int vecSize  = vecList.get(0).values.length;
        float[] flattened = new float[listSize * vecSize];
        for (int vecNumber = 0; vecNumber < listSize; vecNumber++)
        {  
            Vector v = vecList.get(vecNumber);
            for (int i = 0; i < vecSize; i++)
                flattened[vecNumber * vecSize + i] = v.values[i];
        }
        return flattened;
    }
}