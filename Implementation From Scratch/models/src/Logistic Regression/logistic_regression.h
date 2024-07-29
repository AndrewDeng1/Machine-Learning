#ifndef LOGISTIC_REGRESSION_H
#define LOGISTIC_REGRESSION_H

#include <vector>
#include <iostream>
#include <stdexcept>
#include <cmath>
#include <algorithm>
#include <cassert>

#include "..\..\..\math_lib\src\math_lib.h"
#include "..\..\..\math_lib\src\matrix.h"

using namespace std;

class LogisticRegression {
    
    public:

        // Declare signature of constructor methods
        LogisticRegression();

        // Returns weight matrix
        Matrix getW() const;

        // Sets weights matrix
        void setW(Matrix m);

        // Gradient descent
        void fit_gradient_descent(const Matrix& X, const Matrix& y, double learning_rate, size_t epochs);
        void fit_gradient_descent(const Matrix& X, const vector<double>& y, double learning_rate, size_t epochs);
    
        // Stochastic gradient descent
        void fit_stochastic_gradient_descent(const Matrix& X, const Matrix& y, double learning_rate, size_t epochs, size_t mini_batch_size);
        void fit_stochastic_gradient_descent(const Matrix& X, const vector<double>& y, double learning_rate, size_t epochs, size_t mini_batch_size);

        // Predict method
        Matrix predict(const Matrix& X) const;

        // Inference method
        double inference(const Matrix& x) const;
        double inference(const vector<double>& x) const;

    private:
        
        // 2D dynamic arrays to represent matrix
        Matrix w;

        // Generates random weights
        void generate_random_weights(size_t n);

        // Calculates gradient of binary cross entropy loss function, for logistic regression
        // assuming y^hat = sigmoid(Xw)
        Matrix gradient(const Matrix& X, const Matrix& y) const;
};

#endif // LOGISTIC_REGRESSION_H