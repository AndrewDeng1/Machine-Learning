#ifndef MATRIX_H
#define MATRIX_H

#include <vector>
#include <iostream>
#include <stdexcept>
#include <cmath>
#include <algorithm>
#include <cassert>

using namespace std;

class Matrix {
    
    public:

        // Declare signature of constructor methods
        Matrix();
        Matrix(size_t rows, size_t cols);
        Matrix(const vector<vector<double>> arr);
        Matrix(const vector<double> arr);

        // Gets number of rows/cols of Matrix
        size_t getRows() const;
        size_t getCols() const;

        // Used to index into the Matrix
        vector<double>& operator[](size_t row);
        const vector<double>& operator[](size_t row) const;

        // Addition
        Matrix operator+(const Matrix& matrix) const;

        // Subtraction
        Matrix operator-(const Matrix& matrix) const;

        // Matrix multiplication
        Matrix operator*(const Matrix& matrix) const;

        // Dot product on two vector-shaped matrices, of shape 1 x n and n x 1, respectively
        double dot(const Matrix& matrix) const;

        // Scalar multiply
        Matrix operator*(double n) const;

        // Vector multiply
        vector<double> operator*(const vector<double>& vec) const;

        // Transpose
        Matrix T() const;

        // Concatenates matrices horizontally
        Matrix concat(const Matrix& matrix, int axis) const;
        Matrix concat(const Matrix& matrix) const;

        // Returns a slice of the matrix
        Matrix slice(size_t row_start, size_t row_end, size_t col_start, size_t col_end) const;

        // Samples rows/columns
        Matrix sample(size_t n, size_t axis) const;

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

        // Returns the std::vector equivalent of this Matrix, if it is in vector form (n x 1)
        vector<double> toVec() const;

        // Displays the matrix to console
        void display() const;
    
    private:
        
        // 2D dynamic arrays to represent matrix
        vector<vector<double>>m;
};

Matrix operator*(double k, const Matrix& m);

#endif // MATRIX_H