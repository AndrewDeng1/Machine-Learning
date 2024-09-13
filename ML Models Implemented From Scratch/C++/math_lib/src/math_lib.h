#ifndef MATH_LIB_H
#define MATH_LIB_H

// Include all headers in "math_lib" folder
#include "matrix.h"

namespace math_lib {

// Sigmoid function prototype
double sigmoid(double x);

// Optional: Sigmoid function for vectors (element-wise application)
Matrix sigmoid(const Matrix& m);

// Average accuracy of predictions vs ground truth
double average_error(const Matrix& y_true, const Matrix& y_pred);

// Calculates squared euclidean distance between two vectors (or L_2^2 norm)
double squared_euclidean_distance(const vector<double>& v1, const vector<double> v2);
double squared_euclidean_distance(const Matrix& v1, const Matrix& v2);

}


#endif // MATH_LIB_H