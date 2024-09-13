/*
 * Class: Matrix
 * Description: Template class used to represent a matrix. Only 
 * compatible for 2D matrices. Also contains all necessary 
 * operations to do matrix operations, such as transposition, 
 * adding, subtracting, dot product, multiplying, etc.
 */

// Package and imports
package Model;

import java.util.*;
import com.fasterxml.jackson.annotation.JsonProperty;

// Matrix class
// Used to represent a matrix in math. Compatible for only doubles.
public class Matrix {

    // For when instantiating this class from the serialized JSON version
    @JsonProperty("matrix")

    // 2D arraylist that represents matrix
    ArrayList<ArrayList<Double>> matrix;

    // Set to an already made matrix
    public Matrix(ArrayList<ArrayList<Double>> matrix) {
        setMatrix(matrix);
    }

    // Initialize matrix with random doubles
    public Matrix(int n, int m) {

        // Initialize matrix
        setMatrix(new ArrayList<ArrayList<Double>>());

        // Iterate through rows and columns
        for (int i = 0; i < n; i++) {
            ArrayList<Double> row = new ArrayList<>();

            for (int j = 0; j < m; j++) {

                // Initializes each element as a double between [-0.5, 0.5]
                row.add(Math.random() - 0.5);
            }

            getMatrix().add(row);
        }
    }

    // Initialize matrix with default value
    public Matrix(int n, int m, double d) {

        // Initialize matrix
        setMatrix(new ArrayList<ArrayList<Double>>());

        // Iterate through rows and columns
        for (int i = 0; i < n; i++) {
            ArrayList<Double> row = new ArrayList<>();

            for (int j = 0; j < m; j++) {

                // Initializes each element as a double between [-0.5, 0.5]
                row.add(d);
            }

            getMatrix().add(row);
        }
    }

    // Invalid matrix default constructor
    public Matrix() {
        setMatrix(new ArrayList<ArrayList<Double>>());
    }

    // Dot product with another matrix
    // https://www.youtube.com/watch?v=2spTnAiQg4M
    public Matrix dot_product(Matrix m) {

        // Number of columns of left matrix must be equal to number of rows of right
        // matrix for dot product to be possible
        if (numCols() != m.numRows()) {
            System.out.println(String.format("[%d x %d] . [%d x %d], %d != %d !!", numRows(), numCols(), m.numRows(),
                    m.numCols(), numCols(), m.numRows()));
            return new Matrix();
        }

        // Create new matrix to return
        Matrix ret = new Matrix(numRows(), m.numCols());

        // Iterates through rows of left matrix
        for (int i = 0; i < numRows(); i++) {

            // Iterates through columns of right matrix
            for (int j = 0; j < m.numCols(); j++) {

                double sum = 0;

                // Multiply xth element of left-matrix-row to xth element of
                // right-matrix-column, sum it all up, set that value to the ith row and jth
                // column of resulting matrix
                // numCols() == m.numRows()
                // for(int x=0; x<Math.min(numRows(), m.numCols()); x++){
                for (int x = 0; x < Math.min(numCols(), m.numRows()); x++) {
                    sum += get(i).get(x) * m.get(x).get(j);
                }

                ret.get(i).set(j, sum);
            }
        }

        // Return modified matrix
        return ret;
    }

    // Returns this matrix except all elements added by certain value
    public Matrix add(double n) {

        // Create new matrix to return
        Matrix ret = new Matrix(numRows(), numCols());

        // Iterates through rows and columns and adds the value passed in
        for (int i = 0; i < numRows(); i++) {
            for (int j = 0; j < numCols(); j++) {
                ret.get(i).set(j, get(i).get(j) + n);
            }
        }

        // Return modified matrix
        return ret;
    }

    // Addition with another matrix
    public Matrix add(Matrix m) {

        // Both matrices must have same number of columns
        if (numRows() != m.numRows()) {
            System.out.println(String.format("[%d x %d] + [%d x %d], %d != %d!!", numRows(), numCols(), m.numRows(),
                    m.numCols(), numRows(), m.numRows()));
            return new Matrix();
        }

        // Both matrices either have to have same number of columns, or one of them must have only 1 column
        if (numCols() != m.numCols() && numCols() != 1 && m.numCols() != 1) {
            System.out.println(String.format("[%d x %d] + [%d x %d], %d != 1 || %d != %d!!", numRows(), numCols(),
                    m.numRows(), m.numCols(), m.numCols(), numCols(), m.numCols()));
            return new Matrix();
        }

        // Create new matrix to return
        Matrix ret = new Matrix(numRows(), Math.max(numCols(), m.numCols()));

        // Iterate through rows and columns
        for (int i = 0; i < numRows(); i++) {

            // Math.max for case where numCols() or m.numCols() == 1, but the other isn't,
            // so iterate over the number of columns for the one with more columns
            for (int j = 0; j < Math.max(numCols(), m.numCols()); j++) {

                // Math.min to generalize for the case such that that matrix has 1 column
                // (iterating over number of columns of the matrix for more columns, so will
                // index into first column for matrix with 1 column)
                ret.get(i).set(j, get(i).get(Math.min(numCols() - 1, j)) + m.get(i).get(Math.min(m.numCols() - 1, j)));
            }
        }

        // Return modified matrix
        return ret;
    }

    // Returns this matrix except all elements subtracted by certain value
    public Matrix subtract(double n) {

        // Create new matrix to return
        Matrix ret = new Matrix(numRows(), numCols());

        // Iterate through rows and columns and subtract value
        for (int i = 0; i < numRows(); i++) {
            for (int j = 0; j < numCols(); j++) {
                ret.get(i).set(j, get(i).get(j) - n);
            }
        }

        // Return modified matrix
        return ret;
    }

    // Subtraction with another matrix
    public Matrix subtract(Matrix m) {

        // Both matrices must have same number of rows
        if (numRows() != m.numRows()) {
            System.out.println(String.format("[%d x %d] - [%d x %d], %d != %d!!", numRows(), numCols(), m.numRows(),
                    m.numCols(), numRows(), m.numRows()));
            return new Matrix();
        }

        // Both matrices either have to have same number of columns or one has 1 column
        if (numCols() != m.numCols() && numCols() != 1 && m.numCols() != 1) {
            System.out.println(String.format("[%d x %d] - [%d x %d], %d != 1 || %d != %d!!", numRows(), numCols(),
                    m.numRows(), m.numCols(), m.numCols(), numCols(), m.numCols()));
            return new Matrix();
        }

        // Create new matrix to return
        Matrix ret = new Matrix(numRows(), Math.max(numCols(), m.numCols()));

        // Iterate through rows and columns and subtract
        for (int i = 0; i < numRows(); i++) {

            // Math.max for case where numCols() or m.numCols() == 1, but the other isn't,
            // so iterate over the number of columns for the one with more columns
            for (int j = 0; j < Math.max(numCols(), m.numCols()); j++) {

                // Math.min to generalize for the case such that that matrix has 1 column
                // (iterating over number of columns of the matrix for more columns, so will
                // index into first column for matrix with 1 column)
                ret.get(i).set(j, get(i).get(Math.min(numCols() - 1, j)) - m.get(i).get(Math.min(m.numCols() - 1, j)));
            }
        }

        // Return modified matrix
        return ret;
    }

    // Transpose a matrix
    public Matrix transpose() {

        // Create new matrix to return
        Matrix ret = new Matrix(numCols(), numRows());

        // Iterates through rows and colums and transposes matrix
        for (int i = 0; i < numRows(); i++) {
            for (int j = 0; j < numCols(); j++) {
                ret.get(j).set(i, get(i).get(j));
            }
        }

        // Return modified matrix
        return ret;
    }

    // Sums all values in matrix
    public double sum() {

        double ret = 0.0;

        // Iterate through rows and columns and sums up all values
        for (int i = 0; i < numRows(); i++) {
            for (int j = 0; j < numCols(); j++) {
                ret += get(i).get(j);
            }
        }

        // Return modified matrix
        return ret;
    }

    // Multiplies a double to all values in matrix
    public Matrix multiply(double n) {

        // Create new matrix to return
        Matrix ret = new Matrix(numRows(), numCols());

        // Iterate through rows and columns and multiply each value by n
        for (int i = 0; i < numRows(); i++) {
            for (int j = 0; j < numCols(); j++) {
                ret.get(i).set(j, n * get(i).get(j));
            }
        }

        // Return modified matrix
        return ret;
    }

    // Multiplies a double to all values in matrix
    public Matrix multiply(Matrix m) {

        // Both matrices must have same number of rows and columns
        if (numRows() != m.numRows() || numCols() != m.numCols()) {
            System.out.printf(
                    "\n\nATTEMPTED MATRIX MULTIPLICATION WITH ANOTHER MATRIX BUT DIMENSIONS AREN'T THE SAME: %d x %d * %d x %d\n\n",
                    numRows(), numCols(), m.numRows(), m.numCols());
            return new Matrix();
        }

        // Create new matrix to return
        Matrix ret = new Matrix(Math.min(numRows(), m.numRows()), Math.min(numCols(), m.numCols()));

        // Iterate through rows and columns and multiply corresponding values
        for (int i = 0; i < numRows(); i++) {
            for (int j = 0; j < numCols(); j++) {
                ret.get(i).set(j, get(i).get(j) * m.get(i).get(j));
            }
        }

        // Return modified matrix
        return ret;
    }

    // Returns average of all values in matrix
    public double average() {

        // Gets the sum of all values in matrix
        double ret = sum();

        // // Iterate through rows and columns and sum it all up
        // for (int i = 0; i < numRows(); i++) {
        //     for (int j = 0; j < numCols(); j++) {
        //         ret += get(i).get(j);
        //     }
        // }

        // Returns sum divided by total number of cells
        return (double) ret / (numRows() * numCols());
    }

    // Sets entire matrix to 0.0s
    public void setAll(double n) {

        // Iterate through rows and columns and set all to a value
        for (int i = 0; i < numRows(); i++) {
            for (int j = 0; j < numCols(); j++) {
                get(i).set(j, n);
            }
        }
    }

    // Returns a "slice" of rows of this matrix
    // l and r are inclusive
    public Matrix slice(int l, int r) {

        // Index l must be less than or equal to r
        if (l > r) {
            System.out.printf("\n\nATTEMPTING TO GET SLICE OF MATRIX FROM L = %d TO R = %d, but %d > %d\n\n", l, r, r,
                    l);
            return new Matrix();
        } 
        
        // l and r must be within the bounds of number of rows
        else if (l < 0 || l > numRows() || r < 0 || r > numRows()) {
            System.out.printf(
                    "\n\nATTEMPTING TO GET SLICE OF MATRIX FROM L = %d TO R = %d, but %d or %d is out of bounds for matrix with shape %d x %d\n\n",
                    l, r, l, r, numRows(), numCols());
            return new Matrix();
        }
    
        // Create new matrix to return
        Matrix ret = new Matrix();

        // Iterates through rows of matrix and adds to new matrix
        for (int i = l; i <= r; i++) {
            ret.add(get(i));
        }

        // Return modified matrix
        return ret;
    }

    // ACTIVATION FUNCTION IMPLEMENTATIONS
    // ReLU activation function
    public Matrix ReLU() {
        // Create new matrix to return
        Matrix ret = new Matrix(numRows(), numCols());

        // Iterate through rows and columns and apply ReLU activation function
        for (int i = 0; i < numRows(); i++) {
            for (int j = 0; j < numCols(); j++) {
                ret.get(i).set(j, Math.max(0, get(i).get(j)));
            }
        }

        // Return modified matrix
        return ret;
    }

    // Derivative of ReLU activation function
    public Matrix differentiated_ReLU() {
        // Create new matrix to return
        Matrix ret = new Matrix(numRows(), numCols());

        // Iterates through rows and columns and applies differentiated ReLU activation function
        for (int i = 0; i < numRows(); i++) {
            for (int j = 0; j < numCols(); j++) {
                ret.get(i).set(j, get(i).get(j) > 0.0 ? 1.0 : 0.0);
            }
        }

        // Return modified matrix
        return ret;
    }

    // Softmax activation function
    public Matrix softmax() {

        // Applied to unactivated, Z2, 10 x m

        // m x 10
        // Create new matrix to return
        Matrix ret = transpose();


        // Iterates through rows
        for (int i = 0; i < ret.numRows(); i++) {

            // Sum of the e^x where x is each cell value
            double exp_sum = 0.0;

            // Iterate through columns and sum up e^x
            for (int j = 0; j < ret.numCols(); j++) {
                exp_sum += Math.exp(ret.get(i).get(j));
            }

            // Iterate through columns and set to e^x/sum(e^x)
            for (int j = 0; j < ret.numCols(); j++) {
                ret.get(i).set(j, (double) Math.exp(ret.get(i).get(j)) / exp_sum);
            }
        }

        // Returns transposed matrix so 10 x m so easier to find predicted number for each sample
        // Return modified matrix
        return ret.transpose();
    }

    // Displays first sample in matrix assuming either the number of rows or columns
    // is equal to 784
    public void printAsImage() {

        // The string to print
        String str = "";

        // If the rows represent the pixels, then there should be 784 rows
        if (numRows() == Model.SAMPLE_HEIGHT * Model.SAMPLE_WIDTH) {

            // Iterate through each row, add the value to the string, and at every 28th, add a new line
            for (int i = 0; i < numRows(); i++) {
                str += Double.toString(get(i).get(0));
                if ((i + 1) % Model.SAMPLE_WIDTH == 0) {
                    str += "\n";
                }
            }
        } 
        
        // If the columns represent the pixels, then there should be 784 columns
        else if (numCols() == Model.SAMPLE_HEIGHT * Model.SAMPLE_WIDTH) {

            // Iterate through each column, add the value to the string, and every 28th, add a new line
            for (int i = 0; i < numCols(); i++) {
                str += Double.toString(get(0).get(i));
                if ((i + 1) % Model.SAMPLE_WIDTH == 0) {
                    str += "\n";
                }
            }
        } 
        
        // Neither the rows nor columns represent the pixels, thus not a valid image
        else {
            System.out.printf(
                    "ATTEMPTING TO DISPLAY MATRIX AS IMAGE. DIMENSION MUST BE 784 x 1 OR 1 x 784. DIMENSION OF MATRIX: %d x %d\n",
                    numRows(), numCols());
        }

        // Display the string
        System.out.println(str);
    }

    // Getters and setters
    public ArrayList<Double> get(int i) {
        return getMatrix().get(i);
    }

    public void add(ArrayList<Double> arr) {
        getMatrix().add(arr);
    }

    public int size() {
        return getMatrix().size();
    }

    public ArrayList<ArrayList<Double>> getMatrix() {
        return matrix;
    }

    public void setMatrix(ArrayList<ArrayList<Double>> matrix) {
        this.matrix = matrix;
    }

    public int numRows() {
        return getMatrix().size();
    }

    public int numCols() {
        return getMatrix().get(0).size();
    }

    @Override
    public String toString() {

        // Create return string
        String ret = "";

        // Iterate through each row
        for (int i = 0; i < numRows(); i++) {

            // Add value from each row to string
            String row = "";
            for (int j = 0; j < numCols(); j++) {
                row += get(i).get(j) + " ";
            }

            // Add new line, then add to return string
            row += "\n";
            ret += row;
        }

        // Return modified matrix
        return ret;
    }
}
