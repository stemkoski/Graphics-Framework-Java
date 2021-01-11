package core;

public class Matrix
{

    // TODO: inverse? determinant? lookAt? setPosition/getPosition/setDirection/getDirection?

    private int rows, cols;
    private float[][] values;
    private float[] flatValues;

    // constructor
    public Matrix(int rows, int cols)
    {  
        this.rows = rows;
        this.cols = cols;
        this.values = new float[rows][cols];
        this.flatValues = new float[rows*cols];
    }

    // required format for GLSL, helpful for copying data to new matrix
    public float[] flatten()
    {
        for (int i = 0; i < rows; i++)
            for (int j = 0; j < cols; j++)
                flatValues[j + i*cols] = (float)values[i][j];

        return flatValues;
    }

    /** 
        set values in row order
    */
    public void setValues(float... v)
    {
        for (int i = 0; i < v.length; i++)
        {
            int row = i / cols;
            int col = i % cols;
            values[row][col] = v[i];
        }
    }

    public Vector getRow(int i)
    {
        float[] row = new float[cols];
        for (int j = 0; j < cols; j++)
            row[j] = values[i][j];
        return new Vector(row);
    }

    public Vector getCol(int j)
    {
        float[] col = new float[rows];
        for (int i = 0; i < rows; i++)
            col[i] = values[i][j];
        return new Vector(col);
    }

    public static Matrix multiply(Matrix A, Matrix B)
    {
        if (A.cols != B.rows)
            return null;

        Matrix C = new Matrix(A.rows, B.cols);
        for (int i = 0; i < A.rows; i++)
            for (int j = 0; j < B.cols; j++)
                C.values[i][j] = Vector.dot( A.getRow(i), B.getCol(j) );
        return C;
    }

    // replace this matrix with (M * this)
    public void leftMultiply(Matrix M)
    {
        this.setValues( Matrix.multiply(M, this).flatten() );
    }

    // replace this matrix with (this * M)
    public void rightMultiply(Matrix M)
    {
        this.setValues( Matrix.multiply(this, M).flatten() );
    }

    public String toString()
    {
        String s = "";
        for (int i = 0; i < rows; i++)
            s += getRow(i).toString() + "\n";
        return s;
    }

    // inverse related methods; require square matrix
    public float determinant() 
    {
        // if (rows != cols)
        //     throw new Exception();
        if (rows == 1)
            return values[0][0];
        
        if (rows == 2)
            return values[0][0] * values[1][1] - values[0][1] * values[1][0];

        // for larger matrices, calculate determinant 
        //   using cofactor expansion along first row
        float det = 0;
        for (int colNum = 0; colNum < cols; colNum++)
            det += (float)Math.pow(-1, colNum) * values[0][colNum] * minor(0, colNum).determinant();
        return det;
    }


    // TODO: row != rowNum (!!!!)
    /*
        0 1 2 3 4
        exclude 2

        rowIndex

        minorRowIndex


    */
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

    public void multiplyScalar(float s)
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
                inv.values[rowNum][colNum] = (float)Math.pow(-1, rowNum + colNum)
                    * this.minor(rowNum, colNum).determinant();

        float det = determinant();
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
    
    public static Matrix makeTranslation(float x, float y, float z)
    {
        Matrix m = new Matrix(4,4);
        m.setValues(1,0,0,x, 0,1,0,y, 0,0,1,z, 0,0,0,1);
        return m;
    }

    public static Matrix makeRotationZ(double angle)
    {
        float c = (float)(Math.cos(angle));
        float s = (float)(Math.sin(angle));
        Matrix m = new Matrix(4,4);
        m.setValues(c,-s,0,0, s,c,0,0, 0,0,1,0, 0,0,0,1);
        return m;
    }

    public static Matrix makeRotationX(double angle)
    {
        float c = (float)(Math.cos(angle));
        float s = (float)(Math.sin(angle));
        Matrix m = new Matrix(4,4);
        m.setValues(1,0,0,0, 0,c,-s,0, 0,s,c,0, 0,0,0,1);
        return m;
    }

    public static Matrix makeRotationY(double angle)
    {
        float c = (float)(Math.cos(angle));
        float s = (float)(Math.sin(angle));
        Matrix m = new Matrix(4,4);
        m.setValues(c,0,s,0, 0,1,0,0, -s,0,c,0, 0,0,0,1);
        return m;
    }

    public static Matrix makeScale(float s)
    {
        Matrix m = new Matrix(4,4);
        m.setValues(s,0,0,0, 0,s,0,0, 0,0,s,0, 0,0,0,1);
        return m;
    }

    public static Matrix makePerspective(double angleOfView, double aspectRatio, double near, double far)
    {

        float a = (float)(angleOfView * Math.PI/180.0);
        float d = (float)(1.0 / Math.tan(a/2));
        float r = (float)aspectRatio;
        float b = (float)((far + near) / (near - far));
        float c = (float)(2*far*near / (near - far));
        Matrix m = new Matrix(4,4);
        m.setValues(d/r,0,0,0, 0,d,0,0, 0,0,b,c, 0,0,-1,0);
        return m;
    }

    // default parameters
    public static Matrix makePerspective()
    {
        return makePerspective(60, 1, 0.1, 1000);
    }

}