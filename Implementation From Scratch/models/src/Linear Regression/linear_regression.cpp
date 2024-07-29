#include "linear_regression.h"

LinearRegression::LinearRegression(): w(0, 0){}

Matrix LinearRegression::getW() const {
    return w;
}

void LinearRegression::setW(const Matrix& m) {
    w = m;
}

// "const" indicates method isn't allowed to modify member variables, and can only access other "const" methods
void LinearRegression::fit_closed_form(const Matrix& X, const vector<double>& y) {
    fit_closed_form(X, Matrix(y));
}
void LinearRegression::fit_closed_form(const Matrix& X, const Matrix& y) {
    setW((X.T()*X).inverse()*X.T()*Matrix(y));
}

Matrix LinearRegression::predict(const Matrix& X) const {
    return X*getW();
}

double LinearRegression::inference(const vector<double>& x) const {
    return inference(Matrix(x));
}
double LinearRegression::inference(const Matrix& x) const {
    return x.T().dot(getW());
}