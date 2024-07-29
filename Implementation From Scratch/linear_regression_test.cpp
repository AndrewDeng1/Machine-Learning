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
                throw runtime_error("Invalid value found in the file: " + value);
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

void linear_regression_test(){

    // vector<vector<double>> arr = parseCSV("data/real_estate_linear_regression_data.csv");
    // IDK how to use relative path lmao
    vector<vector<double>> arr = parseCSV("C:\\Users\\andar\\Machine-Learning\\Implementation From Scratch\\data\\real_estate_linear_regression_data.csv");
    Matrix temp=Matrix(arr);

    int NUM_TEST_CASE=50;

    Matrix X_train = temp.slice(0, temp.getRows()-NUM_TEST_CASE, 0, temp.getCols()-1);
    Matrix y_train = temp.slice(0, temp.getRows()-NUM_TEST_CASE, temp.getCols()-1, temp.getCols());
    Matrix X_test = temp.slice(temp.getRows()-NUM_TEST_CASE, temp.getRows(), 0, temp.getCols()-1);
    Matrix y_test = temp.slice(temp.getRows()-NUM_TEST_CASE, temp.getRows(), temp.getCols()-1, temp.getCols());

    LinearRegression lin_reg = LinearRegression();

    lin_reg.fit_closed_form(X_train, y_train);

    Matrix y_pred = lin_reg.predict(X_test);

    cout<<"Weights:"<<endl;
    lin_reg.getW().display();
    cout<<"----------"<<endl;

    // sigmoid(y_pred).display();
    cout<<"Average error: "<<average_error(y_test, y_pred)<<endl;

    vector<vector<double>>x = {
        {13.3,4082.015,8,25.00705868353470,121.56169421770800},
        {35.5,274.0144,2,25.012148257650200,121.54698984457100},
        {1.1,1978.671,10,25.003849862836500,121.52833647288200}
    };

    cout<<"Inference:"<<endl;
    for(auto i:x){
        cout<<lin_reg.inference(i)<<endl;
    }
}

int main(){

    linear_regression_test();

    return 0;
}