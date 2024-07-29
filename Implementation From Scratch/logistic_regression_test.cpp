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
    int cnt=0;
    while (getline(file, line)) {
        cnt++;
        vector<double> row;
        stringstream ss(line);
        string value;
        for(int i=0; i<13; i++){
            if(i==0||i==3||i==4||i==9||i==11||i==12){
                getline(ss, value, ',');
                continue;
            }

            getline(ss, value, ',');

            if(i==5){
                if(value=="male"){
                    row.push_back(0.0);
                } else if(value=="female"){
                    row.push_back(1.0);
                } else {
                    cout<<"Invalid gender found in the file: "<<value<<endl;
                    vector<vector<double>> tmp = vector<vector<double>>();
                    return tmp;
                }
                continue;
            } else if(i==6){
                if(value==""){
                    row.push_back(-1.0);
                } else {
                    row.push_back(stod(value));
                }
                continue;
            }

            try {
                row.push_back(stod(value));
            } catch (const invalid_argument& e) {
                throw runtime_error("Invalid value found in the file: " + value);
            }
        }

        data.push_back(row);
    }

    // Average age
    double avg=0;
    double count=0;
    for(int i=0; i<data.size(); i++){
        if(data[i][3]!=-1.0){
            avg+=data[i][3];
            count++;
        }
    }
    avg/=count;

    // Correct all Nan values for age
    for(int i=0; i<data.size(); i++){
        if(data[i][3]==-1.0){
            data[i][3]=avg;
        }
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

void logistic_regression_test(){

    // vector<vector<double>> arr = parseCSV("data/real_estate_linear_regression_data.csv");
    // IDK how to use relative path lmao
    vector<vector<double>> arr = parseCSV("C:\\Users\\andar\\Machine-Learning\\Implementation From Scratch\\data\\titanic_logistic_regression_data.csv");
    
    Matrix temp=Matrix(arr);
    temp=temp.slice(0, temp.getRows(), 1, temp.getCols()).concat(temp.slice(0, temp.getRows(), 0, 1));

    int NUM_TEST_CASE=50;

    Matrix X_train = temp.slice(0, temp.getRows()-NUM_TEST_CASE, 0, temp.getCols()-1);
    Matrix y_train = temp.slice(0, temp.getRows()-NUM_TEST_CASE, temp.getCols()-1, temp.getCols());
    Matrix X_test = temp.slice(temp.getRows()-NUM_TEST_CASE, temp.getRows(), 0, temp.getCols()-1);
    Matrix y_test = temp.slice(temp.getRows()-NUM_TEST_CASE, temp.getRows(), temp.getCols()-1, temp.getCols());

    LogisticRegression log_reg = LogisticRegression();

    // lin_reg.fit_closed_form(X_train, y_train);
    log_reg.fit_gradient_descent(X_train, y_train, 0.1, 1000);

    Matrix y_pred = log_reg.predict(X_test);

    cout<<"Weights:"<<endl;
    log_reg.getW().display();
    cout<<"----------"<<endl;

    y_pred.display();
    cout<<"Average error: "<<average_error(y_test, y_pred)<<endl;

    vector<vector<double>>x = {
        {3.0, 0.0, 22.0, 1.0, 1.0, 10.0},  // 22 yr old male, 10$ fare -> predicts dies
        {1.0, 0.0, 5.0, 1.0, 0.0, 10.0},  // 5 yr old male, 10$ fare -> predicts survives 0.9
        {1.0, 1.0, 5.0, 1.0, 0.0, 50.0},  // 5 yr old female, 50$ fare -> predicts survives 1
        {1.0, 0.0, 5.0, 1.0, 0.0, 50.0},  // 5 yr old male, 50$ fare -> predicts survives 1
    };

    cout<<"Inference:"<<endl;
    for(auto i:x){
        cout<<log_reg.inference(i)<<endl;
    }
}

int main(){

    logistic_regression_test();

    return 0;
}