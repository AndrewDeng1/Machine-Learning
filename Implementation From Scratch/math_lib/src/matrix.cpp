#include "matrix.h"

Matrix::Matrix(size_t rows, size_t cols): m(vector<vector<double>>(rows, vector<double>(cols, 0))){}

Matrix::Matrix(vector<vector<double>>&arr): m(arr){}

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

Matrix Matrix::operator+(Matrix& matrix) const {

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

Matrix Matrix::operator-(Matrix& matrix) const {

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

Matrix Matrix::operator*(Matrix& matrix) const {

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

vector<double> Matrix::operator*(vector<double>& vec){

    assert(getCols()==vec.size()&&"Matrix columns must be equal to vector size.");

    vector<double> temp(getRows(), 0);
    for(int i=0; i<getRows(); i++){
        for(int j=0; j<getCols(); j++){
            temp[i]+=m[i][j]*vec[j];
        }
    }

    return temp;
}

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

Matrix Matrix::T(){
    
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
        assert(getCols()==matrix.getCols()&&"Both matrices must have same number of columns to be concatenable along axis 1.");

        Matrix temp = Matrix(getRows()+matrix.getRows(), getCols());
        for(size_t i=0; i<getRows(); i++){
            for(size_t j=0; j<getCols(); j++){
                temp[i][j]=m[i][j];
            }
        }

        for(size_t i=0; i<matrix.getRows(); i++){
            for(size_t j=0; j<getCols(); j++){
                temp[getRows()+i][j]=matrix[i][j];
            }
        }

        return temp;
    }
    
}

Matrix Matrix::concat(const Matrix& matrix) const{
    return concat(matrix, 0);
}

double Matrix::det() const {

    assert(getRows()==getCols()&&"Number of rows must be equal to number of columns for determinant to exist.");

    return _det(*this);
}

// Time complexity: O(n!)
// Space complexity: O((n!)^2)
double Matrix::_det(const Matrix& m) const {

    assert(m.getRows()==m.getCols()&&"Number of rows must be equal to number of columns for determinant to exist.");

    if(m.getRows()==1&&m.getCols()==1){
        return m[0][0];
    }

    if(m.getRows()==2&&m.getCols()==2){
        return m[0][0]*m[1][1]-m[0][1]*m[1][0];
    }

    double val=0;

    for(int j=0; j<m.getCols(); j++){
        val+=-1*(j%2==1)*m[0][j]*_det(m.slice(1, m.getRows(), 0, j).concat(m.slice(1, m.getRows(), j, m.getCols())));
    }

    return val;
}

double Matrix::minor(size_t row, size_t col) const {
    Matrix top_left = slice(0, row, 0, col);
    Matrix top_right = slice(0, row, col+1, getCols());
    Matrix bottom_left = slice(row+1, getRows(), 0, col);
    Matrix bottom_right = slice(row+1, getRows(), col+1, getCols());
 
    Matrix temp = top_left.concat(top_right).concat(bottom_left.concat(bottom_right), 1);
    return _det(temp);
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

    return temp.T();
}

Matrix Matrix::inverse() const{
    
    assert(getRows()==getCols()&&"Inverse only exists for square matrices.");

    double determinant = det();
    assert(determinant!=0.0&&"Determinant of matrix is 0, so inverse doesn't exist.");

    Matrix temp = (1.0/((double)determinant))*adjoint();
    return temp;
}

void Matrix::display(){
    for(int i=0; i<getRows(); i++){
        for(int j=0; j<getCols(); j++){
            cout<<m[i][j]<<" ";
        }
        cout<<endl;
    }
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

    m.inverse().display();
}



int main(){
    ios::sync_with_stdio(0);
    cin.tie(0);

    solution();
}