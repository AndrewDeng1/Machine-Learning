#define ll long long
using namespace std;

#include <iostream>
#include <fstream>
#include <sstream>
#include <vector>
#include <string>

#include "models\src\models.h"
#include "math_lib\src\math_lib.h"
// #include "math_lib\src\matrix.h"
// #include "models\src\Linear Regression\linear_regression.h"
// #include "models\src\Logistic Regression\logistic_regression.h"
// #include "models\src\models.h"
// #include "C:\Users\andar\Machine-Learning\Implementation From Scratch\models\src\models.h"
// #include "C:\Users\andar\Machine-Learning\Implementation From Scratch\math_lib\src\matrix.h"
// #include "C:\Users\andar\Machine-Learning\Implementation From Scratch\math_lib\src\math_lib.h"
// #include "C:\Users\andar\Machine-Learning\Implementation From Scratch\models\src\Linear Regression\linear_regression.h"
// #include "C:\Users\andar\Machine-Learning\Implementation From Scratch\models\src\Logistic Regression\logistic_regression.h"

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

void solution(){
    // Matrix m = Matrix(2, 2);
    // vector<vector<double>>arr1 = {{1, 2}, {3, 4}};
    // vector<vector<double>>arr2 = {{1, 2}, {3, 5}};

    // Matrix m2 = Matrix(arr1);
    // Matrix m3 = Matrix(arr2);

    // m.display();
    // m2.display();
    // m3.display();

    // (m+m2).display();
    // (m2+m3).display();
    // (m-m2).display();
    // (m2-m3).display();
    // (m*m2).display();
    // (m2*m3).display();

    // vector<vector<double>>arr = {
    //     {1.0, -2.0, 3.0},
    //     {2.0, 0.0, 3.0},
    //     {1.0, 5.0, 4.0},
    // };

    // NICE TEST FOR LINEAR REGRESSION
    // vector<vector<double>>arr = {
    //     {1.0, -2.0, 3.0},
    //     {2.0, -1.0, 3.0},
    //     {1.0, 5.0, -4.0},
    // };

    // vector<double> y = {1.0, 5.0, 10.0};
    // NICE TEST FOR LINEAR REGRESSION

    //     {1.0, -2.0, -10.0},
    //     {2.0, -5.0, 5.0},
    //     {1.0, 5.0, 4.0},
    // };

    // vector<double> y = {0.0, 1.0, 1.0};
    // vector<double> x = {-13.0, -14.0, -15.0};
    // NICE TEST FOR LOGISTIC REGRESSION

    // vector<vector<double>>arr = {

    // vector<vector<double>>arr = {
    //     {1.0, 2.0, 3.0},
    //     {4.0, 5.0, 6.0},
    //     {7.0, 8.0, 9.0},
    //     {10.0, 11.0, 12.0},
    // };

    // vector<double> y = {6.0, 15.0, 24.0, 33.0};


    // test_linear_regression();


    vector<vector<double>> arr = parseCSV("C:\\Users\\andar\\Machine-Learning\\Implementation From Scratch\\data\\real_estate_linear_regression_data.csv");
    Matrix temp=Matrix(arr);

    // temp.display();

    int NUM_TEST_CASE=50;

    Matrix X_train = temp.slice(0, temp.getRows()-NUM_TEST_CASE, 0, temp.getCols()-1);
    Matrix y_train = temp.slice(0, temp.getRows()-NUM_TEST_CASE, temp.getCols()-1, temp.getCols());
    Matrix X_test = temp.slice(temp.getRows()-NUM_TEST_CASE, temp.getRows(), 0, temp.getCols()-1);
    Matrix y_test = temp.slice(temp.getRows()-NUM_TEST_CASE, temp.getRows(), temp.getCols()-1, temp.getCols());

    LinearRegression lin_reg = LinearRegression();
    lin_reg.fit_closed_form(X_train, y_train);
    // LogisticRegression lr = LogisticRegression();
    // lr.fit_gradient_descent(X_train, y_train, 100, 0.01);
    // cout<<"finished gd"<<endl;
    Matrix y_pred = lin_reg.predict(X_test);

    cout<<"weights"<<endl;
    lin_reg.getW().display();

    cout<<"average error"<<endl;
    cout<<average_error(y_test, y_pred)<<endl;

    vector<vector<double>>x = {
        {13.3,4082.015,8,25.00705868353470,121.56169421770800},
        {35.5,274.0144,2,25.012148257650200,121.54698984457100},
        {1.1,1978.671,10,25.003849862836500,121.52833647288200}
    };
    for(auto i:x){
        cout<<lin_reg.inference(i)<<endl;
    }

    cout<<"boo"<<endl;
}

int main(){
    ios::sync_with_stdio(0);
    cin.tie(0);

    solution();
}


// void solution(){
//     // Matrix m = Matrix(2, 2);
//     // vector<vector<double>>arr1 = {{1, 2}, {3, 4}};
//     // vector<vector<double>>arr2 = {{1, 2}, {3, 5}};

//     // Matrix m2 = Matrix(arr1);
//     // Matrix m3 = Matrix(arr2);

//     // m.display();
//     // m2.display();
//     // m3.display();

//     // (m+m2).display();
//     // (m2+m3).display();
//     // (m-m2).display();
//     // (m2-m3).display();
//     // (m*m2).display();
//     // (m2*m3).display();

//     vector<vector<double>>arr = {
//         {1, -2, 3},
//         {2, 0, 3},
//         {1, 5, 4},
//     };
//     // vector<vector<double>>arr = {
//     //     {1, 2, 0},
//     //     {3, -1, 2},
//     //     {-2, 3, -2},
//     // };
//     // vector<vector<double>>arr = {
//     //     {5, 6, 6, 8},
//     //     {2, 2, 2, 8},
//     //     {6, 6, 2, 8},
//     //     {2, 3, 6, 7}
//     // };
//     // vector<vector<double>>arr = {
//     //     {1, 0, 4, 6},
//     //     {2, 5, 0, 3},
//     //     {-1, 2, 3, 5},
//     //     {2, 1, -2, 3}
//     // };
//     Matrix m = Matrix(arr);

//     m.T().display();

//     Matrix temp=m.inverse();
//     printf("inverse of m is: \n");
//     temp.display();
//     printf("following should be identity matrix: \n");
//     (m*temp).display();


// }



// int main(){
//     ios::sync_with_stdio(0);
//     cin.tie(0);

//     solution();
// }