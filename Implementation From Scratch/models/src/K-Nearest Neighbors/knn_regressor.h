#ifndef KNN_REGRESSOR_H
#define KNN_REGRESSOR_H

#include <vector>
#include <iostream>
#include <stdexcept>
#include <cmath>
#include <algorithm>
#include <cassert>
#include <queue>
#include <map>

#include "..\..\..\math_lib\src\math_lib.h"
#include "..\..\..\math_lib\src\matrix.h"

using namespace std;

class KNNRegressor {
    
    public:

        // Declare signature of constructor methods
        KNNRegressor(const Matrix& X, const vector<double>& y, size_t k);
        KNNRegressor(const Matrix& X, const Matrix& y, size_t k);
    
        // Predict method
        Matrix predict(const Matrix& X) const;

        // Inference method
        double inference(const vector<double>& x) const;
        double inference(const Matrix& x) const;

    private:
        
        size_t k;
        vector<pair<Matrix, double>> training_set;

        size_t getK() const;
        void setK(size_t k);

        vector<pair<Matrix, double>>& getTrainingSet();
        const vector<pair<Matrix, double>>& getTrainingSet() const;
        void setTrainingSet(const vector<pair<Matrix, double>>& training_set);
};

#endif // KNN_REGRESSOR_H