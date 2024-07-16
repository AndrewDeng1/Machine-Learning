#include "math_lib.h"
#include <cmath> // For std::exp

using namespace std;

namespace math_lib {

// Sigmoid function implementation
double sigmoid(double x) {
    return 1.0 / (1.0 + exp(-x));
}

// Optional: Sigmoid function for vectors (element-wise application)
Matrix sigmoid(const Matrix& m) {
    Matrix ret = Matrix(m.getRows(), m.getCols());
    for (size_t i = 0; i < m.getRows(); i++) {
        for (size_t j = 0; j < m.getCols(); j++) {
            ret[i][j] = sigmoid(m[i][j]);
        }
    }
    return ret;
}

double average_error(const Matrix& y_true, const Matrix& y_pred) {
    double error = 0;
    for (size_t i = 0; i < y_true.getRows(); i++) {
        error += abs(y_true[i][0] - y_pred[i][0]);
    }
    return error / y_true.getRows();
}

}
