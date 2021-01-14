package graphics.math;

public class Matrix
{
    public int rows, cols;
    public double[][] values;
    public float[] flatValues;

    // constructor
    public Matrix(int rows, int cols)
    {  
        this.rows = rows;
        this.cols = cols;
        this.values = new double[rows][cols];
        this.flatValues = new float[rows*cols];
    }

    // required format for GLSL, helpful for copying data to new matrix
    public float[] flatten()
    {
        for (int rowNum = 0; rowNum < rows; rowNum++)
            for (int colNum = 0; colNum < cols; colNum++)
                flatValues[colNum + rowNum*cols] = (float)values[rowNum][colNum];

        return flatValues;
    }

    /** 
        set values in row order
    */
    public void setValues(double... v)
    {
        for (int i = 0; i < v.length; i++)
        {
            int rowNum = i / cols;
            int colNum = i % cols;
            values[rowNum][colNum] = v[i];
        }
    }

    public Vector getRow(int rowNum)
    {
        Vector row = new Vector(this.cols);
        for (int colNum = 0; colNum < cols; colNum++)
            row.values[colNum] = this.values[rowNum][colNum];
        return row;
    }

    public Vector getCol(int colNum)
    {
        Vector col = new Vector(this.rows);
        for (int rowNum = 0; rowNum < rows; rowNum++)
            col.values[rowNum] = this.values[rowNum][colNum];
        return col;
    }

    public void setRow(int rowNum, Vector row)
    {
        int max = Math.min(this.cols, row.values.length);
        for (int colNum = 0; colNum < max; colNum++)
            this.values[rowNum][colNum] = row.values[rowNum];
    }

    public void setCol(int colNum, Vector col)
    {
        int max = Math.min(this.rows, col.values.length);
        for (int rowNum = 0; rowNum < max; rowNum++)
            this.values[rowNum][colNum] = col.values[rowNum];
    }

    public Vector multiplyVector(Vector vec)
    {
        Vector result = new Vector( vec.values.length );
        for (int rowNum = 0; rowNum < rows; rowNum++)
        {
            Vector row = getRow(rowNum);
            result.values[rowNum] = Vector.dot(row, vec);
        }
        return result;
    }

    public static Matrix multiply(Matrix A, Matrix B)
    {
        if (A.cols != B.rows)
            return null;

        Matrix C = new Matrix(A.rows, B.cols);
        for (int rowNum = 0; rowNum < A.rows; rowNum++)
            for (int colNum = 0; colNum < B.cols; colNum++)
                C.values[rowNum][colNum] = Vector.dot( A.getRow(rowNum), B.getCol(colNum) );
        return C;
    }

    // replace this matrix with (M * this)
    public void leftMultiply(Matrix M)
    {
        this.values = Matrix.multiply(M, this).values;
    }

    // replace this matrix with (this * M)
    public void rightMultiply(Matrix M)
    {
        this.values = Matrix.multiply(this, M).values;
    }

    public String toString()
    {
        String s = "";
        for (int rowNum = 0; rowNum < rows; rowNum++)
            s += getRow(rowNum).toString() + "\n";
        return s;
    }

    // inverse related methods; require square matrix
    public double determinant() 
    {
        // if (rows != cols)
        //     throw new Exception();
        if (rows == 1)
            return values[0][0];
        
        if (rows == 2)
            return values[0][0] * values[1][1] - values[0][1] * values[1][0];

        // for larger matrices, calculate determinant 
        //   using cofactor expansion along first row
        double det = 0;
        for (int colNum = 0; colNum < cols; colNum++)
            det += Math.pow(-1, colNum) * values[0][colNum] * minor(0, colNum).determinant();
        return det;
    }

    // generate (rows-1) by (cols-1) submatrix, excluding given row and col.
    public Matrix minor(int excludeRowNum, int excludeColNum)
    {
        Matrix m = new Matrix(this.rows-1, this.cols-1);

        int minorRowNum = 0, minorColNum = 0;
        for (int rowNum = 0; rowNum < rows; rowNum++)
        {
            if (rowNum == excludeRowNum)
                continue;

            minorColNum = 0;
            for (int colNum = 0; colNum < cols; colNum++)
            {
                if (colNum == excludeColNum)
                    continue;

                m.values[minorRowNum][minorColNum] = this.values[rowNum][colNum];
                minorColNum++;
            }
            minorRowNum++;
        }

        return m;
    }

    public void multiplyScalar(double s)
    {
        for (int rowNum = 0; rowNum < rows; rowNum++)
            for (int colNum = 0; colNum < cols; colNum++)
                values[rowNum][colNum] *= s;
    }

    public Matrix transpose()
    {
        Matrix tr = new Matrix(this.cols, this.rows);
        for (int rowNum = 0; rowNum < rows; rowNum++)
            for (int colNum = 0; colNum < cols; colNum++)
                tr.values[colNum][rowNum] = this.values[rowNum][colNum];
        return tr;
    }

    // this is a computationally heavy operation!
    public Matrix inverse()
    {
        Matrix inv = new Matrix(rows, cols);

        for (int rowNum = 0; rowNum < rows; rowNum++)
            for (int colNum = 0; colNum < cols; colNum++)
                inv.values[rowNum][colNum] = Math.pow(-1, rowNum + colNum)
                    * this.minor(rowNum, colNum).determinant();

        double det = determinant();
        inv = inv.transpose();
        inv.multiplyScalar( 1.0f/det );
        return inv;
    }

    // static methods

    public static Matrix makeIdentity()
    {
        Matrix m = new Matrix(4,4);
        m.setValues(1,0,0,0, 0,1,0,0, 0,0,1,0, 0,0,0,1);
        return m;
    }
    
    public static Matrix makeTranslation(double x, double y, double z)
    {
        Matrix m = new Matrix(4,4);
        m.setValues(1,0,0,x, 0,1,0,y, 0,0,1,z, 0,0,0,1);
        return m;
    }

    public static Matrix makeRotationZ(double angle)
    {
        double c = Math.cos(angle);
        double s = Math.sin(angle);
        Matrix m = new Matrix(4,4);
        m.setValues(c,-s,0,0, s,c,0,0, 0,0,1,0, 0,0,0,1);
        return m;
    }

    public static Matrix makeRotationX(double angle)
    {
        double c = Math.cos(angle);
        double s = Math.sin(angle);
        Matrix m = new Matrix(4,4);
        m.setValues(1,0,0,0, 0,c,-s,0, 0,s,c,0, 0,0,0,1);
        return m;
    }

    public static Matrix makeRotationY(double angle)
    {
        double c = Math.cos(angle);
        double s = Math.sin(angle);
        Matrix m = new Matrix(4,4);
        m.setValues(c,0,s,0, 0,1,0,0, -s,0,c,0, 0,0,0,1);
        return m;
    }

    public static Matrix makeScale(double s)
    {
        Matrix m = new Matrix(4,4);
        m.setValues(s,0,0,0, 0,s,0,0, 0,0,s,0, 0,0,0,1);
        return m;
    }

    public static Matrix makePerspective(double angleOfView, double aspectRatio, double near, double far)
    {

        double a = Math.toRadians(angleOfView);
        double d = 1.0 / Math.tan(a/2);
        double r = aspectRatio;
        double b = (far + near) / (near - far);
        double c = 2*far*near / (near - far);
        Matrix m = new Matrix(4,4);
        m.setValues(d/r,0,0,0, 0,d,0,0, 0,0,b,c, 0,0,-1,0);
        return m;
    }

    // default parameters
    public static Matrix makePerspective()
    {
        return makePerspective(60, 1, 0.1, 1000);
    }

    public static Matrix makeLookAt(Vector position, Vector target)
    {
        Vector worldUp = new Vector(0, 1, 0);
        Vector forward = Vector.subtract( target, position );
        Vector right = Vector.cross( forward, worldUp );

        // if forward and worldUp vectors are parallel, right vector is zero;
        // fix by perturbing worldUp vector a bit
        if (right.getLength() < 0.001)
        {
            Vector offset = new Vector(0.001, 0, 0);
            right = Vector.cross( forward, Vector.add(worldUp, offset) );
        }

        Vector up = Vector.cross( right, forward );
        // all vectors should have length 1
        forward.setLength(1);
        right.setLength(1);
        up.setLength(1);
        
        // the vectors are shorter than the matrix dimensions,
        //  and thus the last matrix entry remains as default (0)
        // only needs to be adjusted in last matrix column.
        Matrix m = new Matrix(4,4);
        m.setCol(0, right);
        m.setCol(1, up);
        forward.multiplyScalar(-1);
        m.setCol(2, forward );
        m.setCol(3, position );
        m.values[3][3] = 1;
        return m;

        /*
        [[ right[0], up[0], -forward[0], position[0]],
         [ right[1], up[1], -forward[1], position[1]],
         [ right[2], up[2], -forward[2], position[2]],
         [  0, 0, 0, 1]]
        */
     }
}