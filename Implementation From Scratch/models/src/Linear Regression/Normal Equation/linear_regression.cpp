#include "linear_regression.h"

LinearRegression::LinearRegression(){}

// "const" indicates method isn't allowed to modify member variables, and can only access other "const" methods
void Matrix::fit_closed_form(Matrix& X, vector<double>& y) {
    w=(X.T()*X).inverse()*X.T()*y;
}

vector Matrix::predict(Matrix& X) {
    return X*w;
}

double Matrix::inference(vector& x) {
    return x.T()*w;
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

    vector<vector<double>>arr = {
        {1, 2, 3},
        {4, 5, 6},
        {7, 8, 9},
    };
    Matrix m = Matrix(arr);

    m.T().display();
}



int main(){
    ios::sync_with_stdio(0);
    cin.tie(0);

    solution();
}