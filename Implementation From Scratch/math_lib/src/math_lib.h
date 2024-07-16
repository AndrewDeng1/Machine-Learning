#ifndef MATH_LIB_H
#define MATH_LIB_H

// Include all headers in "math_lib" folder
#include "matrix.h"

namespace math_lib {

// Sigmoid function prototype
double sigmoid(double x);

// Optional: Sigmoid function for vectors (element-wise application)
Matrix sigmoid(const Matrix& m);

}


#endif // MATH_LIB_H