#include "linear_regression.h"

LinearRegression::LinearRegression(): w(0.0, 0.0){}

// "const" indicates method isn't allowed to modify member variables, and can only access other "const" methods
void LinearRegression::fit_closed_form(const Matrix& X, const vector<double>& y) {
    w=(X.T()*X).inverse()*X.T()*Matrix(y);
}

vector<double> LinearRegression::predict(const Matrix& X) const {
    return (X*w).toVec();
}

double LinearRegression::inference(const vector<double>& x) const {
    return Matrix(x).T().dot(w);
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
//         {1.0, 2.0, 3.0},
//         {4.0, 5.0, 6.0},
//         {7.0, 8.0, 9.0},
//         {10.0, 11.0, 12.0},
//     };

//     vector<double> y = {6.0, 15.0, 24.0, 33.0};

//     Matrix X = Matrix(arr);

//     LinearRegression lr = LinearRegression();
//     lr.fit_closed_form(X, y);

//     vector<double> x = {13.0, 14.0, 15.0};

//     cout << lr.inference(x) << endl;
// }



// int main(){
//     ios::sync_with_stdio(0);
//     cin.tie(0);

//     solution();
// }