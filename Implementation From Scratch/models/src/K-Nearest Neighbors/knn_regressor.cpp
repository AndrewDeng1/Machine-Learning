#include "knn_regressor.h"

using namespace math_lib;

// Assumes X e R^(m x n) and y e R^n
KNNRegressor::KNNRegressor(const Matrix& X, const Matrix& y, size_t k) {

    assert(X.getRows() == y.getRows() && y.getCols() == 1 && k<=X.getRows() && "X and y must have the same number of rows and y must be a column vector. k <= m");

    setK(k);
    setTrainingSet({});

    for (size_t i = 0; i < X.getRows(); i++) {
        getTrainingSet().push_back(make_pair(X[i], y[i][0]));
    }
}

KNNRegressor::KNNRegressor(const Matrix& X, const vector<double>& y, size_t k) {
    KNNRegressor(X, Matrix(y), k);
}

Matrix KNNRegressor::predict(const Matrix& X) const {
    Matrix y_pred = Matrix(X.getRows(), 1);

    for (size_t i = 0; i < X.getRows(); i++) {
        y_pred[i][0] = inference(X[i]);
    }

    return y_pred;
}

double KNNRegressor::inference(const vector<double>& x) const {
    return inference(Matrix(x));
}

double KNNRegressor::inference(const Matrix& x) const {
    
    priority_queue<pair<double, double>> pq;

    for (size_t i = 0; i < getTrainingSet().size(); i++) {
        if(pq.size()<getK()){
            pq.push(make_pair(squared_euclidean_distance(x, getTrainingSet()[i].first), getTrainingSet()[i].second));
        } else {
            double dist = squared_euclidean_distance(x, getTrainingSet()[i].first);
            if(dist<pq.top().first){
                pq.pop();
                pq.push(make_pair(dist, getTrainingSet()[i].second));
            }
        }
    }

    double sm=0;
    while(pq.size()>0){
        sm += pq.top().second;
        pq.pop();
    }

    return sm / getK();
}

size_t KNNRegressor::getK() const {
    return k;
}

void KNNRegressor::setK(size_t k) {
    this->k = k;
}

vector<pair<Matrix, double>>& KNNRegressor::getTrainingSet() {
    return training_set;
}

const vector<pair<Matrix, double>>& KNNRegressor::getTrainingSet() const {
    return training_set;
}

void KNNRegressor::setTrainingSet(const vector<pair<Matrix, double>>& training_set) {
    this->training_set = training_set;
}
