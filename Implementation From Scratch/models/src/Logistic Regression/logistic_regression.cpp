#include "logistic_regression.h"

using namespace math_lib;

LogisticRegression::LogisticRegression(): w(0, 0){}

Matrix LogisticRegression::getW() const {
    return w;
}

void LogisticRegression::setW(Matrix m) {
    w = m;
}

void LogisticRegression::generate_random_weights(size_t n) {
    setW(Matrix(n, 1));
    for(size_t i=0; i<n; i++) {
        getW()[i][0] = (double)rand()/RAND_MAX;
    }
}

// Calculates gradient of binary cross entropy loss function, for logistic regression
// assuming y^hat = sigmoid(Xw)
// and X is R^(mxn), y is R^m
//  If X is R^(nxm), use 1/m*X(sigmoid(w^TX)^T-y) instead
Matrix LogisticRegression::gradient(const Matrix& X, const Matrix& y) const {
    return 1.0/(double)X.getRows()*X.T()*(sigmoid(X*getW())-y);
}

// "const" indicates method isn't allowed to modify member variables, and can only access other "const" methods
// Assumes X is R^(mxn)
void LogisticRegression::fit_gradient_descent(const Matrix& X, const vector<double>& y, double learning_rate, size_t epochs) {
    fit_gradient_descent(X, Matrix(y), epochs, learning_rate);
}
void LogisticRegression::fit_gradient_descent(const Matrix& X, const Matrix& y, double learning_rate, size_t epochs) {
    generate_random_weights(X.getCols());
    for(size_t iter=1; iter<=epochs; iter++) {
        Matrix grad=gradient(X, y);
        setW(getW() - learning_rate*grad);
    }
}

void LogisticRegression::fit_stochastic_gradient_descent(const Matrix& X, const vector<double>& y, double learning_rate, size_t epochs, size_t mini_batch_size) {
    fit_stochastic_gradient_descent(X, Matrix(y), epochs, learning_rate, mini_batch_size);
}
void LogisticRegression::fit_stochastic_gradient_descent(const Matrix& X, const Matrix& y, double learning_rate, size_t epochs, size_t mini_batch_size) {
    generate_random_weights(X.getCols());
    for(size_t iter=1; iter<=epochs; iter++){
        Matrix sampled_X = X.sample(mini_batch_size, 1);
        Matrix grad = gradient(sampled_X, y);
        setW(getW() - learning_rate*grad);
    }
}

Matrix LogisticRegression::predict(const Matrix& X) const {
    return sigmoid(X*getW());
}

double LogisticRegression::inference(const vector<double>& x) const {
    return sigmoid(Matrix(x).T().dot(getW()));
}