#ifndef LINEAR_REGRESSION_H
#define LINEAR_REGRESSION_H

#include <vector>
#include <iostream>
#include <stdexcept>
#include <cmath>
#include <algorithm>
#include <cassert>

#include "..\..\..\math_lib\src\math_lib.h"
#include "..\..\..\math_lib\src\matrix.h"

using namespace std;

class LinearRegression {
    
    public:

        // Declare signature of constructor methods
        LinearRegression();

        // Returns weights
        Matrix getW() const;

        // Sets weights
        void setW(const Matrix& m);

        // Normal Equation method
        void fit_closed_form(const Matrix& X, const vector<double>& y);
        void fit_closed_form(const Matrix& X, const Matrix& y);
    
        // Predict method
        Matrix predict(const Matrix& X) const;

        // Inference method
        double inference(const vector<double>& x) const;
        double inference(const Matrix& x) const;

    private:
        
        // 2D dynamic arrays to represent matrix
        Matrix w;
};

#endif // LINEAR_REGRESSION_H