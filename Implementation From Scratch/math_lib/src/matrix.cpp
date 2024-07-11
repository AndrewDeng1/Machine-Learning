#include "matrix.h"

Matrix::Matrix(size_t rows, size_t cols): m(vector<vector<double>>(rows, vector<double>(cols, 0))){}

Matrix::Matrix(const vector<vector<double>>&arr): m(arr){}

Matrix::Matrix(const vector<double>&arr): m(vector<vector<double>>(arr.size(), vector<double>(1, 0))){
    for(size_t i=0; i<arr.size(); i++){
        m[i][0]=arr[i];
    }
}

// "const" indicates method isn't allowed to modify member variables, and can only access other "const" methods
size_t Matrix::getRows() const {
    return m.size();
}

size_t Matrix::getCols() const {
    if(getRows()==0){
        return 0;
    }
    return m[0].size();
}

vector<double>& Matrix::operator[](size_t row){
    return m[row];
}

const vector<double>& Matrix::operator[](size_t row) const {
    return m[row];
}

Matrix Matrix::operator+(const Matrix& matrix) const {

    assert(getRows()==matrix.getRows()&&"Matrices must have same number of rows.");
    assert(getCols()==matrix.getCols()&&"Matrices must have same number of columns.");

    Matrix temp = Matrix(getRows(), getCols());
    for(int i=0; i<getRows(); i++){
        for(int j=0; j<getCols(); j++){
            temp[i][j]=m[i][j]+matrix[i][j];
        }
    }

    return temp;
}

Matrix Matrix::operator-(const Matrix& matrix) const {

    assert(getRows()==matrix.getRows()&&"Matrices must have same number of rows.");
    assert(getCols()==matrix.getCols()&&"Matrices must have same number of columns.");

    Matrix temp = Matrix(getRows(), getCols());
    for(int i=0; i<getRows(); i++){
        for(int j=0; j<getCols(); j++){
            temp[i][j]=m[i][j]-matrix[i][j];
        }
    }

    return temp;
}

Matrix Matrix::operator*(const Matrix& matrix) const {

    assert(getCols()==matrix.getRows()&&"A must have as many columns as B has rows.");

    Matrix temp = Matrix(getRows(), matrix.getCols());
    for(int i=0; i<getRows(); i++){
        for(int j=0; j<matrix.getCols(); j++){
            for(int x=0; x<getCols(); x++){
                temp[i][j]+=m[i][x]*matrix[x][j];
            }
        }
    }

    return temp;
}

double Matrix::dot(const Matrix& matrix) const {

    assert(getCols()==matrix.getRows()&&getRows()==1&&matrix.getCols()==1&&"Dot product only exists for two vector-shaped matrices of shape 1 x n and n x 1, respectively.");

    Matrix temp = Matrix(getRows(), matrix.getCols());
    for(int i=0; i<getRows(); i++){
        for(int j=0; j<matrix.getCols(); j++){
            for(int x=0; x<getCols(); x++){
                temp[i][j]+=m[i][x]*matrix[x][j];
            }
        }
    }

    return temp[0][0];
}

Matrix Matrix::operator*(double k) const {

    Matrix temp = Matrix(getRows(), getCols());
    for(int i=0; i<getRows(); i++){
        for(int j=0; j<getCols(); j++){
            temp[i][j]=m[i][j]*k;
        }
    }

    return temp;
}

Matrix operator*(double k, const Matrix& m) {

    Matrix temp = Matrix(m.getRows(), m.getCols());
    for(size_t i=0; i<m.getRows(); i++){
        for(size_t j=0; j<m.getCols(); j++){
            temp[i][j]=m[i][j]*k;
        }
    }

    return temp;
}

// vector<double> Matrix::operator*(const vector<double>& vec) const {

//     assert(getCols()==vec.size()&&"Matrix columns must be equal to vector size.");

//     vector<double> temp(getRows(), 0);
//     for(int i=0; i<getRows(); i++){
//         for(int j=0; j<getCols(); j++){
//             temp[i]+=m[i][j]*vec[j];
//         }
//     }

//     return temp;
// }

Matrix Matrix::slice(size_t row_start, size_t row_end, size_t col_start, size_t col_end) const {

    assert(row_start<=row_end&&col_start<=col_end&&row_start>=0&&row_end<=getRows()&&col_start>=0&&col_end<=getCols()&&"Slicing out of bounds.");

    Matrix temp = Matrix(row_end-row_start, col_end-col_start);
    for(int i=row_start; i<row_end; i++){
        for(int j=col_start; j<col_end; j++){
            temp[i-row_start][j-col_start]=m[i][j];
        }
    }

    return temp;
}

Matrix Matrix::T() const {
    
    Matrix temp = Matrix(getCols(), getRows());

    for(int i=0; i<getRows(); i++){
        for(int j=0; j<getCols(); j++){
            temp[j][i]=m[i][j];
        }
    }

    return temp;
}

// Matrix vector::T(){

//     Matrix temp = Matrix(1, size());

//     for(int i=0; i<size(); i++){
//         temp[0][i]=v[i];
//     }

//     return temp;
// }

Matrix Matrix::concat(const Matrix& matrix, int axis) const {

    assert(axis>=0&&axis<=1&&"Axis must be an integer between 0 and 1 inclusive.");

    if(axis==0){
        assert(getRows()==matrix.getRows()&&"Both matrices must have same number of rows to be concatenable along axis 0.");

        Matrix temp = Matrix(getRows(), getCols()+matrix.getCols());
        for(size_t j=0; j<getCols(); j++){
            for(size_t i=0; i<getRows(); i++){
                temp[i][j]=m[i][j];
            }
        }

        for(size_t j=0; j<matrix.getCols(); j++){
            for(size_t i=0; i<matrix.getRows(); i++){
                temp[i][getCols()+j]=matrix[i][j];
            }
        }

        return temp;
    } else {  // axis=1
        // CAREFUL OF 0 COLUMNS CASE
        //  Due to matrix being represented as 2d vector, 
        //  - Possible for matrix to have non-zero rows but 0 columns
        //  - Impossible for matrix to have 0 rows but non-zero columns
        //  Thus we assume if a matrix has 0 columns, it has infinitely many columns
        //  E.g. If matrix has 10 rows and 0 columns, and being concatenated along axis=1
        //  with matrix with 100 rows and 10 columns, resulting matrix is just the ladder 
        //  matrix.
        //      BE CAREFUL IF THIS IS NOT THE DESIRED OUTCOME

        // printf("getCols, m.getCols, %d %d\n", getCols(), matrix.getCols());
        assert((getCols()==0||matrix.getCols()==0||getCols()==matrix.getCols())&&"Both matrices must have same number of columns to be concatenable along axis 1.");

        Matrix temp = Matrix(getRows()+matrix.getRows(), max(getCols(), matrix.getCols()));
        for(size_t i=0; i<getRows(); i++){
            for(size_t j=0; j<getCols(); j++){
                temp[i][j]=m[i][j];
            }
        }

        for(size_t i=0; i<matrix.getRows(); i++){
            for(size_t j=0; j<matrix.getCols(); j++){
                temp[getRows()+i][j]=matrix[i][j];
            }
        }

        // cout<<"CONCATING THE FOLLOWING<"<<endl;
        // printf("dim1: %d %d\n", getRows(), getCols());
        // display();
        // cout<<"--"<<endl;
        // printf("dim2: %d %d\n", matrix.getRows(), matrix.getCols());
        // matrix.display();
        // cout<<"RESULT:"<<endl;
        // temp.display();

        return temp;
    }
    
}

Matrix Matrix::concat(const Matrix& matrix) const{
    // printf("concat successfully overloaded\n");

    return concat(matrix, 0);
}

// double Matrix::det() const {

//     assert(getRows()==getCols()&&"Number of rows must be equal to number of columns for determinant to exist.");

//     return _det();
// }

// Time complexity: O(n!)
// Space complexity: O((n!)^2)
double Matrix::det() const {

    // printf("rows, cols: %d %d\n", getRows(), getCols());
    assert(getRows()==getCols()&&"Number of rows must be equal to number of columns for determinant to exist.");

    // display();

    // printf("calcing determinant of: \n");

    if(getRows()==1&&getCols()==1){
        // printf("determinant of: \n");
        // display();
        // printf("is: %.2f\n", m[0][0]);
        return m[0][0];
    }

    if(getRows()==2&&getCols()==2){
        // printf("determinant of: \n");
        // display();
        // printf("is: %.2f\n", m[0][0]*m[1][1]-m[0][1]*m[1][0]);
        return m[0][0]*m[1][1]-m[0][1]*m[1][0];
    }

    double val=0;

    for(int j=0; j<getCols(); j++){
        Matrix temp=slice(1, getRows(), 0, j).concat(slice(1, getRows(), j+1, getCols()));
        val+=(j%2==1?-1:1)*m[0][j]*temp.det();
    }

    // printf("determinant of: \n");
    // display();
    // printf("is: %.2f\n", val);

    return val;
}

double Matrix::minor(size_t row, size_t col) const {
    Matrix top_left = slice(0, row, 0, col);
    Matrix top_right = slice(0, row, col+1, getCols());
    Matrix bottom_left = slice(row+1, getRows(), 0, col);
    Matrix bottom_right = slice(row+1, getRows(), col+1, getCols());
 
    // printf("minor coords: %d %d\n", row, col);
    // Matrix top_conc = top_left.concat(top_right);
    // Matrix bottom_conc = bottom_left.concat(bottom_right);
    // printf("top_conc dim: %d %d\n", top_conc.getRows(), top_conc.getCols());
    // printf("bottom_conc dim: %d %d\n", bottom_conc.getRows(), bottom_conc.getCols());
    // Matrix temp = top_conc.concat(bottom_conc, 1);
    Matrix temp = top_left.concat(top_right).concat(bottom_left.concat(bottom_right), 1);
    // printf("minor of matrix at %d %d is %d\n", row ,col, temp.det());
    return temp.det();
}

double Matrix::cofactor(size_t row, size_t col) const{
    return pow(-1, row+col)*minor(row, col);
}

Matrix Matrix::adjoint() const{
    Matrix temp = Matrix(getRows(), getCols());

    for(int i=0; i<getRows(); i++){
        for(int j=0; j<getCols(); j++){
            temp[i][j]=cofactor(i, j);
        }
    }

    // cout<<"Adjoint of matrix:"<<endl;
    // temp.T().display();

    return temp.T();
}

Matrix Matrix::inverse() const{
    
    assert(getRows()==getCols()&&"Inverse only exists for square matrices.");


    double determinant = det();
    printf("determinant: %.2f\n", determinant);
    printf("done calcing det for inverse\n");
    assert(determinant!=0.0&&"Determinant of matrix is 0, so inverse doesn't exist.");

    Matrix temp = (1.0/((double)determinant))*adjoint();
    return temp;
}

vector<double> Matrix::toVec() const {

    assert(getCols()==1&&"Matrix must be in vector form (n x 1) to convert to vector.");

    vector<double>ret;
    for(size_t i=0; i<getRows(); i++){
        ret.push_back(m[i][0]);
    }

    return ret;
}

void Matrix::display() const {
    for(int i=0; i<getRows(); i++){
        for(int j=0; j<getCols(); j++){
            cout<<m[i][j]<<" ";
        }
        cout<<endl;
    }
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

//     // vector<vector<double>>arr = {
//     //     {1, -2, 3},
//     //     {2, 0, 3},
//     //     {1, 5, 4},
//     // };
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
//     vector<vector<double>>arr = {
//         {1, 0, 4, 6},
//         {2, 5, 0, 3},
//         {-1, 2, 3, 5},
//         {2, 1, -2, 3}
//     };
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