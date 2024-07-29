#ifndef KNN_CLASSIFIER_H
#define KNN_CLASSIFIER_H

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

class KNNClassifier {
    
    public:

        // Declare signature of constructor methods
        KNNClassifier(const Matrix& X, const vector<double>& y, size_t k);
        KNNClassifier(const Matrix& X, const Matrix& y, size_t k);
    
        // Predict method
        Matrix predict(const Matrix& X) const;

        // Inference method
        double inference(const vector<double>& x) const;
        double inference(const Matrix& x) const;

    private:
        
        size_t k;
        size_t num_classes;
        vector<pair<Matrix, size_t>> training_set;
        map<double, size_t> label_encoder;
        map<size_t, double> label_decoder;
        Matrix normalize_vector;

        size_t getK() const;
        void setK(size_t k);

        vector<pair<Matrix, size_t>>& getTrainingSet();
        const vector<pair<Matrix, size_t>>& getTrainingSet() const;
        void setTrainingSet(const vector<pair<Matrix, size_t>>& training_set);

        map<double, size_t>& getLabelEncoder();
        const map<double, size_t>& getLabelEncoder() const;
        void setLabelEncoder(const map<double, size_t>& label_encoder);

        map<size_t, double>& getLabelDecoder();
        const map<size_t, double>& getLabelDecoder() const;
        void setLabelDecoder(const map<size_t, double>& label_decoder);

        Matrix& getNormalizeVector();
        const Matrix& getNormalizeVector() const;
        void setNormalizeVector(const Matrix& normalize_vector);

        size_t getNumClasses() const;
        void setNumClasses(size_t num_classes);

        // Does initial encoding of classes
        void initLabelEncodings(const Matrix& y);

        // Noramlize the data
        Matrix normalized_training_data(const Matrix& X);
        Matrix normalized_sample(const vector<double>& x) const;
        Matrix normalized_sample(const Matrix& x) const;

};

#endif // KNN_CLASSIFIER_H