package graphics.math;

import java.util.List;
import java.util.ArrayList;

public class Vector
{
	public double[] values;

    public Vector(int size)
    {
        values = new double[size];
    }

    // initialize with contents
    public Vector(double... v)
    {
        values = new double[v.length];
        for (int i = 0; i < v.length; i++)
            values[i] = (double)v[i];
    }

    // resize values array (larger or smaller)
    public void resize(int newSize)
    {
        double[] newValues = new double[newSize];
        int smaller = Math.min(values.length, newValues.length);
        for (int i = 0; i < smaller; i++)
            newValues[i] = values[i];
        values = newValues;
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

    // used by geometry/attribute classes, so must set floats
    public static float[] flattenList(List<Vector> vecList)
    {
        int listSize = vecList.size();
        int vecSize  = vecList.get(0).values.length;
        float[] flattened = new float[listSize * vecSize];
        for (int vecNumber = 0; vecNumber < listSize; vecNumber++)
        {  
            Vector v = vecList.get(vecNumber);
            for (int i = 0; i < vecSize; i++)
                flattened[vecNumber * vecSize + i] = (float)v.values[i];
        }
        return flattened;
    }

    public static List<Vector> unflattenList(float[] flatArray, int vecSize)
    {
        List<Vector> vecList = new ArrayList<Vector>();
        double[] tempData = new double[vecSize];
        for (int i = 0; i < flatArray.length; i += vecSize)
        {
            for (int j = 0; j < vecSize; j++)
                tempData[j] = flatArray[i + j];

            vecList.add( new Vector(tempData) );
        }
        return vecList;
    }
}