#define ll long long
using namespace std;

#include <iostream>
#include <vector>

#include "C:\Users\andar\Machine-Learning\Implementation From Scratch\models\src\models.h"
#include "C:\Users\andar\Machine-Learning\Implementation From Scratch\math_lib\src\matrix.h"
#include "C:\Users\andar\Machine-Learning\Implementation From Scratch\models\src\Linear Regression\Normal Equation\linear_regression.h"


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
    vector<vector<double>>arr = {
        {1.0, -2.0, 3.0},
        {2.0, -1.0, 3.0},
        {1.0, 5.0, -4.0},
    };

    vector<double> y = {2.0, 5.0, 10.0};

    // vector<vector<double>>arr = {
    //     {1.0, 2.0, 3.0},
    //     {4.0, 5.0, 6.0},
    //     {7.0, 8.0, 9.0},
    //     {10.0, 11.0, 12.0},
    // };

    // vector<double> y = {6.0, 15.0, 24.0, 33.0};

    Matrix X = Matrix(arr);

    LinearRegression lr = LinearRegression();
    lr.fit_closed_form(X, y);

    vector<double> x = {13.0, 14.0, 15.0};

    cout << lr.inference(x) << endl;
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