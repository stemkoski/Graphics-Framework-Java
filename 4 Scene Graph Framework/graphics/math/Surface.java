package graphics.math;

public class Surface
{
	public interface Function
	{
		Vector apply(float u, float v);
	}

	public Function function;

    public Surface(Function function)
    {
        this.function = function;
    }

    // get a 2D array of points on the surface
    //   represented by this function
    public Vector[][] getPoints(
    	float uStart, float uEnd, int uResolution, 
    	float vStart, float vEnd, int vResolution)
    {

    	Vector[][] points = new Vector[uResolution+1][vResolution+1];
		float deltaU = (uEnd - uStart) / uResolution;
        float deltaV = (vEnd - vStart) / vResolution;

    	for (int uIndex = 0; uIndex < uResolution+1; uIndex++)
    	{
    		for (int vIndex = 0; vIndex < vResolution+1; vIndex++)
	    	{
	    		float u = uStart + uIndex * deltaU;
                float v = vStart + vIndex * deltaV;
                points[uIndex][vIndex] = this.function.apply(u,v);
	    	}
    	}

    	return points;
    }
}
