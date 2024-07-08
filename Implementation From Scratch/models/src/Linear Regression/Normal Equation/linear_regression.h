#ifndef LINEAR_REGRESSION_H
#define LINEAR_REGRESSION_H

#include <vector>

#include<bits/stdc++.h>

#include <math_lib/matrix.h>

using namespace std;

class LinearRegression {
    
    public:

        // Declare signature of constructor methods
        LinearRegression();

        // Normal Equation method
        void fit_closed_form(Matrix& X, vector<double>& y);
    
        // Predict method
        vector predict(Matrix& X);

        // Inference method
        double inference(vector& x);

    private:
        
        // 2D dynamic arrays to represent matrix
        Matrix w;
};

#endif // LINEAR_REGRESSION_H