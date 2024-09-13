#define ll long long
using namespace std;

#include <iostream>
#include <fstream>
#include <sstream>
#include <vector>
#include <string>

#include "models\src\models.h"
#include "math_lib\src\math_lib.h"

vector<vector<double>> parseCSV(const string& filename) {
    vector<vector<double>> data;
    ifstream file(filename);
    
    if (!file.is_open()) {
        throw runtime_error("Could not open file");
    }

    // Remove feature titles
    string line;

    getline(file, line);

    while (getline(file, line)) {
        vector<double> row;
        stringstream ss(line);
        string value;
        getline(ss, value, ',');
        while (getline(ss, value, ',')) {
            try {
                row.push_back(stod(value));
            } catch (const invalid_argument& e) {
                // throw runtime_error("Invalid value found in the file: " + value);
                
                // DIDN'T BOTHER INTERPOLATING DATA, BUT U SHOULD IN PRACTICE
                row.push_back(0.0);
            }
        }

        data.push_back(row);
    }

    file.close();
    return data;
}

double average_error(const Matrix& y_true, const Matrix& y_pred) {

    assert(y_true.getRows() == y_pred.getRows()&&y_true.getCols()==1&&y_pred.getCols()==1&&"y_true and y_pred must both be vector shape (nx1) and have equal number of rows.");

    double error = 0;
    for (size_t i = 0; i < y_true.getRows(); i++) {
        error += abs(y_true[i][0] - y_pred[i][0]);
    }
    return error / y_true.getRows();
}

void knn_classifier_test(){

    // vector<vector<double>> arr = parseCSV("data/real_estate_linear_regression_data.csv");
    // IDK how to use relative path lmao

    vector<vector<double>> arr = parseCSV("C:\\Users\\andar\\Machine-Learning\\Implementation From Scratch\\data\\breast_cancer_knn_classifier_data.csv");
    Matrix temp=Matrix(arr);

    int NUM_TEST_CASE=50;

    Matrix X_train = temp.slice(0, temp.getRows()-NUM_TEST_CASE, 0, temp.getCols()-1);
    Matrix y_train = temp.slice(0, temp.getRows()-NUM_TEST_CASE, temp.getCols()-1, temp.getCols());
    Matrix X_test = temp.slice(temp.getRows()-NUM_TEST_CASE, temp.getRows(), 0, temp.getCols()-1);
    Matrix y_test = temp.slice(temp.getRows()-NUM_TEST_CASE, temp.getRows(), temp.getCols()-1, temp.getCols());

    KNNClassifier knn_classifier = KNNClassifier(X_train, y_train, 5);

    Matrix y_pred = knn_classifier.predict(X_test);

    cout<<"Average error: "<<average_error(y_test, y_pred)<<endl;

    vector<vector<double>>x = {
        {2,1,1,1,2,1,1,1,5}, // 2
        {5,5,5,5,5,5,8,9,5}, // 4
        {2,1,1,1,2,1,2,1,1}, // 2
        {5,5,5,5,3,1,8,9,1} // 4
    };

    cout<<"Inference:"<<endl;
    for(auto i:x){
        cout<<knn_classifier.inference(i)<<endl;
    }
}

int main(){

    knn_classifier_test();

    return 0;
}