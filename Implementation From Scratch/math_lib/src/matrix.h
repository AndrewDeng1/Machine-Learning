#ifndef MATRIX_H
#define MATRIX_H

#include <vector>

#include<bits/stdc++.h>

using namespace std;

class Matrix {
    
    public:

        // Declare signature of constructor methods
        Matrix(size_t rows, size_t cols);
        Matrix(vector<vector<double>>& arr);

        // Gets number of rows/cols of Matrix
        size_t getRows() const;
        size_t getCols() const;

        // Used to index into the Matrix
        vector<double>& operator[](size_t row);
        const vector<double>& operator[](size_t row) const;

        // Addition
        Matrix operator+(Matrix& matrix) const;

        // Subtraction
        Matrix operator-(Matrix& matrix) const;

        // Matrix multiplication
        Matrix operator*(Matrix& matrix) const;

        // Scalar multiply
        Matrix operator*(double n) const;

        // Vector multiply
        vector<double> operator*(vector<double>& vec);

        // Transpose
        Matrix T();

        // Concatenates matrices horizontally
        Matrix concat(const Matrix& matrix, int axis) const;
        Matrix concat(const Matrix& matrix) const;

        // // Stacks matrices vertically
        // Matrix stack(Matrix& matrix);

        // Returns a slice of the matrix
        Matrix slice(size_t row_start, size_t row_end, size_t col_start, size_t col_end) const;

        // Returns the determinant of the matrix
        double det() const;

        // Minor of an element
        double minor(size_t row, size_t col) const;

        // Cofactor of an element
        double cofactor(size_t row, size_t col) const;

        // Adjoint matrix
        Matrix adjoint() const;

        // Inverse of matrix
        Matrix inverse() const;

        // Displays the matrix to console
        void display();
    
    private:
        
        // 2D dynamic arrays to represent matrix
        vector<vector<double>>m;

        double _det(const Matrix& matrix) const;
};

#endif // MATRIX_H