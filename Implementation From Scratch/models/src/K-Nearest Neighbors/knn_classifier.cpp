#include "knn_classifier.h"

using namespace math_lib;

KNNClassifier::KNNClassifier(const Matrix& X, const vector<double>& y, size_t k) {
    KNNClassifier(X, Matrix(y), k);
}

// Assumes X e R^(m x n) and y e R^n

KNNClassifier::KNNClassifier(const Matrix& X, const Matrix& y, size_t k) {

    assert(X.getRows() == y.getRows() && y.getCols() == 1 && k<=X.getRows() && "X and y must have the same number of rows and y must be a column vector. k <= m");

    setK(k);
    setTrainingSet({});

    initLabelEncodings(y);
    Matrix normalized_X = normalized_training_data(X);
    // getNormalizeVector().display();
    // normalized_X.display();

    for (size_t i = 0; i < normalized_X.getRows(); i++) {
        getTrainingSet().push_back(make_pair(normalized_X[i], getLabelEncoder()[y[i][0]]));
    }

}

void KNNClassifier::initLabelEncodings(const Matrix& y) {
    
    setLabelEncoder({});
    setLabelDecoder({});

    setNumClasses(0);
    
    for(size_t i=0; i<y.getRows(); i++){

        if(getLabelEncoder().find(y[i][0])==getLabelEncoder().end()){
            getLabelEncoder()[y[i][0]] = getNumClasses();
            getLabelDecoder()[getNumClasses()] = y[i][0];
            setNumClasses(getNumClasses()+1);
        }
    }
}

Matrix KNNClassifier::predict(const Matrix& X) const {
    Matrix y_pred = Matrix(X.getRows(), 1);

    for (size_t i = 0; i < X.getRows(); i++) {
        y_pred[i][0] = inference(X[i]);
    }

    return y_pred;
}

double KNNClassifier::inference(const vector<double>& x) const {
    return inference(Matrix(x));
}

double KNNClassifier::inference(const Matrix& x) const {
    
    Matrix normalized_x = normalized_sample(x);

    priority_queue<pair<double, size_t>> pq;

    for (size_t i = 0; i < getTrainingSet().size(); i++) {
        if(pq.size()<getK()){
            pq.push(make_pair(squared_euclidean_distance(normalized_x, getTrainingSet()[i].first), getTrainingSet()[i].second));
        } else {
            double dist = squared_euclidean_distance(normalized_x, getTrainingSet()[i].first);
            if(dist<pq.top().first){
                pq.pop();
                pq.push(make_pair(dist, getTrainingSet()[i].second));
            }
        }
    }

    Matrix prob_distro = Matrix(getNumClasses(), 1);

    while(pq.size()>0){
        prob_distro[pq.top().second][0] += 1;
        pq.pop();        
    }

    for(size_t i=0; i<prob_distro.getRows(); i++){
        prob_distro[i][0] /= getK();
    }

    size_t mx_class=0;
    double mx=-1;
    for(size_t i=0; i<prob_distro.getRows(); i++){
        if(prob_distro[i][0]>mx){
            mx = prob_distro[i][0];
            mx_class = i;
        }
    }

    return getLabelDecoder().at(mx_class);
    // return 1.0;
}

size_t KNNClassifier::getK() const {
    return k;
}

void KNNClassifier::setK(size_t k) {
    this->k = k;
}

vector<pair<Matrix, size_t>>& KNNClassifier::getTrainingSet() {
    return training_set;
}

const vector<pair<Matrix, size_t>>& KNNClassifier::getTrainingSet() const {
    return training_set;
}

void KNNClassifier::setTrainingSet(const vector<pair<Matrix, size_t>>& training_set) {
    this->training_set = training_set;
}

map<double, size_t>& KNNClassifier::getLabelEncoder() {
    return label_encoder;
}

const map<double, size_t>& KNNClassifier::getLabelEncoder() const {
    return label_encoder;
}

void KNNClassifier::setLabelEncoder(const map<double, size_t>& label_encoder) {
    this->label_encoder = label_encoder;
}

map<size_t, double>& KNNClassifier::getLabelDecoder() {
    return label_decoder;
}

const map<size_t, double>& KNNClassifier::getLabelDecoder() const {
    return label_decoder;
}

void KNNClassifier::setLabelDecoder(const map<size_t, double>& label_decoder) {
    this->label_decoder = label_decoder;
}

Matrix& KNNClassifier::getNormalizeVector() {
    return normalize_vector;
}

const Matrix& KNNClassifier::getNormalizeVector() const {
    return normalize_vector;
}

void KNNClassifier::setNormalizeVector(const Matrix& normalize_vector) {
    this->normalize_vector = normalize_vector;
}

size_t KNNClassifier::getNumClasses() const {
    return num_classes;
}

void KNNClassifier::setNumClasses(size_t num_classes) {
    this->num_classes = num_classes;
}

Matrix KNNClassifier::normalized_training_data(const Matrix& X) {
    
    setNormalizeVector(Matrix(X.getCols(), 1));

    for(size_t i=0; i<X.getCols(); i++){
        double mx = -1;
        for(size_t j=0; j<X.getRows(); j++){
            mx = max(mx, X[j][i]);
        }
        getNormalizeVector()[i][0] = mx;
    }

    Matrix temp = Matrix(X.getRows(), X.getCols());

    for(size_t i=0; i<X.getCols(); i++){
        for(size_t j=0; j<X.getRows(); j++){
            temp[j][i] = X[j][i]/getNormalizeVector()[i][0];
        }
    }

    return temp;
}

Matrix KNNClassifier::normalized_sample(const vector<double>& x) const {
    return normalized_sample(Matrix(x));
}

Matrix KNNClassifier::normalized_sample(const Matrix& x) const {

    Matrix temp = Matrix(x.getRows(), x.getCols());
    for(size_t i=0; i<x.getCols(); i++){
        temp[0][i] = x[0][i]/getNormalizeVector()[i][0];
    }

    return temp;
}