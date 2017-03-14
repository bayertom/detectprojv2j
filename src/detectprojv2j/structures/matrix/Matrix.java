// Description: Matrix and basic operations

// Copyright (c) 2015 - 2016
// Tomas Bayer
// Charles University in Prague, Faculty of Science
// bayertom@natur.cuni.cz

// This library is free software: you can redistribute it and/or modify
// it under the terms of the GNU Lesser General Public License as published
// by the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.
//
// This library is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
// GNU Lesser General Public License for more details.
//
// You should have received a copy of the GNU Lesser General Public License
// along with this library. If not, see <http://www.gnu.org/licenses/>.

package detectprojv2j.structures.matrix;

import java.util.Arrays;
import static java.lang.Math.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static detectprojv2j.consts.Consts.*;

import detectprojv2j.comparators.IndexComparator;

import detectprojv2j.exceptions.MathMatrixDifferentSizeException;
import detectprojv2j.exceptions.BadDataException;
import detectprojv2j.exceptions.MathMatrixNotSquareException;
import detectprojv2j.exceptions.MathOverflowException;

public class Matrix{
       
        public final double[][] items;                      //Data
        private final int rows_count;                       //Number of rows
        private final int columns_count;                    //Number of columns

        public Matrix( final int rows_count_, final int columns_count_)
        {
            //Create empty matrix
            rows_count = rows_count_;
            columns_count = columns_count_;

            items = new double[rows_count][columns_count];
        }

        
        public Matrix( final int rows_count_, final int columns_count_, final double item)
        {
            //Create matrix with items set to the specific value
            rows_count = rows_count_;
            columns_count = columns_count_;

            //Create empty matrix and fill
            items = new double[rows_count][columns_count];
            
            for (double[] row: items)
                 Arrays.fill(row, item);             
        }

        public Matrix( final int rows_count_, final int columns_count_, final double non_diag_val, final double diag_val)
        {
            //Create matrix with a specific value on the main diagonal
            rows_count = rows_count_;
            columns_count = columns_count_;

            //Create empty matrix and fill
            items = new double[rows_count][columns_count];
            
            for (double[] row: items)
                 Arrays.fill(row, non_diag_val);

            //Set diagonal items
            for (int i = 0; i < rows_count; i++)
            {
                items[i][i] = diag_val;
            }
        }
        
        //Ger rows count
        public int rows() {return rows_count;}
        
        //Get columns count
        public int cols() {return columns_count;}

        public Matrix(final Matrix A) 
        {
                //Create new matrix as a copy of a matrix
                this(A.items); 
        }  
        
        public Matrix(final double[][] data) 
        {
                //Create matrix from 2D array
                rows_count = data.length;
                columns_count = data[0].length;
        
                //Create empty matrix and copy data
                items = new double[rows_count][columns_count];
                for (int i = 0; i < rows_count; i++)
                {
                        for (int j = 0; j < columns_count; j++)
                        {
                                items[i][j] = data[i][j];
                        }
                }
        }
       
        public void copy (final Matrix A) 
        {
                //Copy matrix A to the current matrix
                for (int i = 0; i < rows_count; i++) 
                {
                        for (int j = 0; j < columns_count; j++) 
                        {
                                items[i][j] = A.items[i][j];
                        }
                }
        }
        
        public Matrix clone ()
        {
                return new Matrix(items);
        }
        
        
        public Matrix plus(final Matrix B) 
        {
                //Matrix operator +
                
                //Matrix dimemension are invalid, throw exception
                if ( rows_count !=  B.rows() )
                {
                        throw new MathMatrixDifferentSizeException ( "MathMatrixDifferentSizeException: ", " different rows count.  Cannot compute A += B. " , this, B );
                }

                //Matrix dimemension are invalid, throw exception
                if ( columns_count != B.cols() )
                {
                        throw new MathMatrixDifferentSizeException("MathMatrixDifferentSizeException: ", " different columns count.  Cannot compute A += B. ", this, B );
                }
                
                //Create matrices
                Matrix A = this;
                Matrix C = new Matrix(rows_count, columns_count);
        
                for (int i = 0; i < rows_count; i++)
                {
                        for (int j = 0; j < columns_count; j++)
                        {
                                C.items[i][j] = A.items[i][j] + B.items[i][j];
                        }
                }
                
                return C;
        }
        
        public Matrix minus(final Matrix B) 
        {
                //Matrix operator -
                
                //Matrix dimemension are invalid, throw exception
                if ( rows_count !=  B.rows() )
                {
                        throw new MathMatrixDifferentSizeException ( "MathMatrixDifferentSizeException: ", " different rows_count count.  Cannot compute A += B. " , this, B );
                }

                //Matrix dimemension are invalid, throw exception
                if ( columns_count != B.cols() )
                {
                        throw new MathMatrixDifferentSizeException("ErrorMathRange: ", " different columns count.  Cannot compute A += B. ", this, B );
                }
                
                //Create matrices
                Matrix C = new Matrix(rows_count, columns_count);
        
                for (int i = 0; i < rows_count; i++)
                {
                        for (int j = 0; j < columns_count; j++)
                        {
                                C.items[i][j] = items[i][j] - B.items[i][j];
                        }
                }
                
                return C;
        }
        
        public Matrix mult(final Matrix B) 
        {
                //Matrix operator *
                final int m2 = B.rows();
                final int n2 = B.cols();
                
                //Matrix dimemension are invalid, throw exception
                if ( columns_count !=  m2 )
                {
                        throw new MathMatrixDifferentSizeException ( "MathMatrixDifferentSizeException: ", " different rows count.  Cannot compute A += B. " , this, B );
                }

                //Create matrices
                Matrix C = new Matrix(rows_count, n2);
                
                for (int i = 0; i < rows_count; i++)
                {
                        for (int k = 0; k < columns_count; k++)
                        {
                                for (int j = 0; j < n2; j++)
                                {
                                        C.items[i][j] += items[i][k] * B.items[k][j];
                                }
                        }
                }
                
                return C;
        }
        
        
         public Matrix mult(final double val) 
         {
                //Matrix operator *
                Matrix A = new Matrix(rows_count, columns_count);

                for (int i = 0; i < rows_count; i++)
                {
                        for (int j = 0; j < columns_count; j++)
                        {
                                A.items[i][j] = items[i][j] * val;
                        }
                }
                
                return A;
        }
        
        
        //Get row of the matrix
        public Matrix row ( final int r )
        {
                //Matrix dimemension invalid, throw exception
                if ( r > rows_count )
                {
                        throw new IndexOutOfBoundsException ( "IndexOutOfBoundsException: row index exceeds rows_count.  " );
                }

                return this.getMatrix(r, r, 0, columns_count - 1);
        }


        //Get column of the matrix
        public Matrix col ( final int c )
        {
                //Matrix dimemension invalid, throw exception
                if ( c > columns_count )
                {
                        throw new IndexOutOfBoundsException ( "IndexOutOfBoundsException: col index exceeds columns_count.  " );
                }

                return this.getMatrix(0, rows_count - 1, c, c);
        }
        
        
        //Set M as the row r of the matrix (M is row vector)
        public void row ( final Matrix  M, final int r )
        {
                //Matrix dimemension invalid, throw exception
                if ( r > rows_count )
                {
                        throw new IndexOutOfBoundsException ( "IndexOutOfBoundsException:  row index exceeds rows_count.  " );
                }

                //Matrix dimemension invalid, throw exception
                if ( M.cols() != columns_count )
                {
                        throw new MathMatrixDifferentSizeException ( "ErrorMathMatrixDifferentSize: ", " invalid dimension of the matrices (columns_count count).  ", this, M );
                }
                
                //Replace row
                this.replace(M, r, 0);
        }


        //Set M as column c of the matrix (M is column vector)
        public void col ( final Matrix M, final int c )
        {
                //Matrix dimemension invalid, throw exception
                if ( c > columns_count )
                {
                        throw new IndexOutOfBoundsException ( "IndexOutOfBoundsException: col index exceeds columns_count.  " );
                }

                //Matrix dimemension are invalid, throw exception
                if ( M.rows() != rows_count )
                {
                        throw new MathMatrixDifferentSizeException ( "ErrorMathMatrixDifferentSize: ", " invalid dimension of the matrices (rows_count count).  ", this, M );
                }

                //Replace column
                this.replace(M, 0, c);
        }
        
        
        //Replace part of the matrix A with another matrix
        public void replace ( final Matrix M, final int row, final int col )
        {
                final int m = M.rows(), n = M.cols();

                if ( m + row > rows_count )
                {
                         throw new ArrayIndexOutOfBoundsException("ErrorIndexOutOfBound: a submatrix does not fit at the specified row position, can not append a submatrix to the matrix. ");
                }

                if ( n + col > columns_count )
                {
                         throw new ArrayIndexOutOfBoundsException("ErrorIndexOutOfBound: a submatrix does not fit at the specified row position, can not append a submatrix to the matrix. ");
                }

                //Copy submatrix
                for ( int i = 0; i < m; i++ )
                {
                        for ( int j = 0; j < n; j++ )
                        {
                                items [i + row][j + col] = M.items [i] [j];
                        }
                }
        }
        
        
        //Matrix operator (r1, r2, c1, c2): get submatrix of the matrix
        public Matrix getMatrix( final int r1, final int r2, final int c1, final int c2 )
        {
                //Bad row index
                if ( r2 >  rows_count )
                        throw new BadDataException ( "BadDataException: row index r2 must not be greater than A.rows_count. ", " Can not create the submatrix. " );

                //Bad row index
                if ( c2 > columns_count )
                        throw new BadDataException ( "BadDataException: col index c2 must not be greater than A.columns_count. ", " Can not create the submatrix. " );

                //Bad row index interval
                if ( r1 > r2 )
                        throw new BadDataException ( "BadDataException: row index r2 must not be smaller then r1. ", " Can not create the submatrix. " );

                //Bad col index interval
                if ( c1 > c2 )
                        throw new BadDataException ( "BadDataException: col index c2 must not be smaller then c1. ", " Can not create the submatrix. " );

                //Create sub-matrix
                Matrix M = new Matrix( r2 - r1 + 1, c2 - c1 + 1 ) ;

                for ( int i = r1; i <= r2; i++ )
                {
                        for ( int j = c1; j <= c2; j++ )
                        {
                                M.items [i - r1] [j - c1] = items[i][j];
                        }
                }

                return M;
        }
        
        
        public Matrix trans() 
        {
                //Transpose matrix
                Matrix A_trans = new Matrix(columns_count, rows_count);
       
                for (int i = 0; i < rows_count; i++)
                {
                        for (int j = 0; j < columns_count; j++)
                        {
                                A_trans.items[j][i] = items[i][j];
                        }
                }
    
                return A_trans;
        }
        
        
	//Find min value, row and col position
	public double min(int [] pos)
	{
		//Initialize minimum
		double min_value = items[0][0];

		//Process all items
		for (int i = 0; i < rows_count; i++)
		{
			for (int j = 0; j < columns_count; j++)
			{
				if (items[i][j] < min_value)
				{
					min_value = items[i][j];
					pos[0] = i; 
                                        pos[1] = j;
				}
			}
		}

		return min_value;
	}
        
        
        //Find min value
	public double min()
	{
		int [] pos = {0,0};
		return min(pos);
	}


	//Find max value, row and col index
	double max(int [] pos)
	{
		//Initialize maximum
		double max_value = items[0][0];

		//Process all items
		for (int i = 0; i < rows_count; i++)
		{
			for (int j = 0; j < columns_count; j++)
			{
				if (items[i][j] > max_value)
				{
					max_value = items[i][j];
					pos[0] = i; pos[1] = j;
				}
			}
		}

		return max_value;
	}


	//Find max value
	public double max()
	{
		int [] pos = {0,0};
		return max(pos);
	}

         
        //Sum selected row
	public double sumRow(final int row)
	{
		//Matrix row index is invalid, throw exception
		if (row < 0 || row > rows_count)
		{
			throw new IndexOutOfBoundsException("IndexOutOfBoundsException: can not compute row sum, invalid row (row < 0 || row > rows_count)");
		}

		double sum = 0;

		//Sum all items in a row
		for (int j = 0; j < columns_count; j++)
		{
			sum += this.items[row][j];
		}

		return sum;
	}


	//Sum selected col
	public double sumCol(final int col)
	{
		//Matrix  col index is invalid, throw exception
		if (col < 0 || col > columns_count)
		{
			throw new IndexOutOfBoundsException("IndexOutOfBoundsException: can not compute col sum, invalid col (col < 0 || col > columns_count)");
		}

		double sum = 0;

		//Sum all items in a column
		for (int i = 0; i < rows_count; i++)
		{
			sum += this.items[i][col];
		}

		return sum;
	}


	//Sum of rows
	public Matrix sumRows()
	{

		Matrix SR = new Matrix(rows_count, 1);

		//Sum all rows
		for (int i = 0; i < rows_count; i++)
		{
			SR.items[i][0] = this.sumRow(i);
		}

		return SR;
	}


	//Sum of columns
	public Matrix sumCols()
	{
		Matrix SC = new Matrix(1, columns_count);

		//Sum all columns
		for (int i = 0; i < columns_count; i++)
		{
			SC.items[0][i] = this.sumCol(i);
		}

		return SC;
	}
        
        
        //Hadamard product
        public Matrix had ( final Matrix M )
        {
                final int m2 = M.rows(), n2 = M.cols();

                //Matrix dimemension invalid, throw exception
                if ( rows_count !=  m2 )
                {
                        throw new MathMatrixDifferentSizeException ( "MathMatrixDifferentSizeException: ", " different rows_count count. Cannot compute A .* B.", this, M );
                }

                //Matrix dimemension invalid, throw exception
                if ( columns_count != n2 )
                {
                        throw new MathMatrixDifferentSizeException ( "MathMatrixDifferentSizeException: ", " different columns_count count.  Cannot compute A .* B. ", this, M );
                }

                //Create temporary matrix for results
                Matrix C = new Matrix(m2, n2);

                //Hadamard product
                for ( int i = 0; i < rows_count; i++ )
                {
                        for ( int j = 0; j < columns_count; j++ )
                        {
                                C.items[i][j] = items[i][j] * M.items[i][j];
                        }
                }

                return C;
        }

        
        //Sum of items
        public double sum()
	{
		double sum = 0;

		for ( int i = 0; i < rows_count; i++)
		{
			for ( int j = 0; j < columns_count; j++)
			{
				sum += items[i][j];
			}
		}

		return sum;
	}


	//Sum2 of the matrix items
	public double sum2()
	{
		double sum = 0;

		for (int i = 0; i < rows_count; i++)
		{
			for (int j = 0; j < columns_count; j++)
			{
				sum += items[i][j] * items[i][j];
			}
		}

		return sum;
	}
        
        //Norm of the matrix
	public double norm()
	{
                return sqrt(this.sum2());
        }
        
        
        //Sort matrix by columns and return array of indices
	public void sort(Matrix IX, final int c)
	{
		//Sort matrix by a column c, if c = -1 sort all columns
		final int m1 = rows_count, n1 =columns_count;
		final int m2 = IX.rows(), n2 = IX.cols();
                
		//Test number of rows A and IX
		if (m1 != m2)
		{
			throw new MathMatrixDifferentSizeException ("MathMatrixDifferentSizeException: ", " different rows count for A, IX, can not sort a matrix:  ", this, IX);
		}

		//Test number of columns A and IX
		if (n1 != n2)
		{
			throw new MathMatrixDifferentSizeException ("MathMatrixDifferentSizeException: ", " different columns count for A, IX, can not sort a matrix:  ", this, IX);
		}

		//Test, if c is correct
		if ((c > n1 - 1) || (c < -1))
		{
			throw new IndexOutOfBoundsException("ErrorIndexOutOfBound: can not sort matrix by a column c, invalid c (c < -1 || col > columns_count)");
		}

		//Initialize indices: i1 = start, i2 = end
		int i1 = (c >= 0 ? c : 0);
		int i2 = (c >= 0 ? c + 1 : n1);

		//Sort columns
		for (; i1 < i2; i1++)
		{
			//Get a column of a matrix
			Matrix col = (this.getMatrix(0, m1 - 1, i1, i1)).trans();

                        //Convert to 1D ArrayList
                        List <Double> col1D = new ArrayList<>();
                        for (int i = 0; i <rows_count;i++) 
                                col1D.add(col.items[0][i]);
		
			//Create 1D vector of indices
			List <Integer> ix = new ArrayList <> (m1);
			for (int j = 0; j < m1; j++)  ix.add(j);

			//Sort 1D vector
			Collections.sort(ix, new IndexComparator(col1D));
			
			//Add sorted column to A and sorted indices to IX
			for (int j = 0; j < m1; j++)
			{
				this.items[j][i1] = col1D.get(ix.get(j));
				IX.items[j][i1] = ix.get(j);
			}
		}
	}
        
        
        //Sort rows of the matrix by a specific column
	public void sortrows(Matrix IX, final int c)
	{
		//Sort matrix by a column c, if c = -1 sort all columns
		final int m1 = this.rows(), n1 = this.cols();
		final int m2 = IX.rows(), n2 = IX.cols();

		//Test number of rows A and IX
		if (m1 != m2)
		{
			throw new MathMatrixDifferentSizeException ("MathMatrixDifferentSizeException: ", " different rows count for A, IX, can not sort rows of a matrix:  ", this, IX);
		}

		//Test number of columns A and IX
		if (n2 != 1)
		{
			throw new MathMatrixDifferentSizeException ("MathMatrixDifferentSizeException: ", " IX does not have 1 column, can not sort rows of a matrix:  ", this, IX);
		}

		//Test, if c is correct
		if ((c > n1 - 1) || (c < -1))
		{
			throw new IndexOutOfBoundsException("IndexOutOfBoundException: can not sort rows of the matrix by a column c, invalid c (c < -1 || col > columns_count)");
		}

		//Get a column of a matrix
		Matrix col = (this.getMatrix(0, m1 - 1, c, c)).trans();

                //Convert to 1D ArrayList
                List <Double> col1D = new ArrayList<>();
                for (int i = 0; i <rows_count;i++) 
                     col1D.add(col.items[0][i]);
		
		//Create 1D vector of indices
		List <Integer> ix = new ArrayList <> (m1);
		for (int j = 0; j < m1; j++)  ix.add(j);

		//Sort 1D vector
		Collections.sort(ix, new IndexComparator(col1D));
                      
		//Sort rows of the matrix
		Matrix AP = new Matrix(this);
		for (int i = 0; i < m1; i++)
		{
			//Store permutation
			IX.items[i][0] = ix.get(i);

			//Copy row to permutated matrix
			AP.replace((this.getMatrix(ix.get(i), ix.get(i), 0, n1 - 1)), i, 0);
		}

		//Assign permutated matrix
		this.copy(AP);
	}
        
        
        //Determinant of the matrix using LU decomposition
	public double det()
	{
		final int m = rows_count, n = columns_count;

                //Create copy of the matrix
                Matrix A = new Matrix(this);
                
		//Rectangular matrix
		if (m != n)
		{
			throw new MathMatrixNotSquareException("MathMatrixNotSquareException: ", " invalid dimension of the matrix (rectangle matrix), can not compute determinant; (rows_count, columns_count):  ", A);
		}

		//Create LU decomposition
		Matrix L = new Matrix(m, m);
		Matrix U = new Matrix(m, m);
		Matrix P = new Matrix(m, m, 0, 1);
                short [] sign = {1};
		A.lu(L, U, P, sign);
             
		//Compute determinant of the triangle matrix
		//det(A) = det(L) * det(P) * det(U) = 1 * (+-1) * det(U)
		double determ = 1.0;

		for (int i = 0; i < m; i++)
		{
			determ *= U.items[i][i];
		}

		//Return determinant
		return sign[0] * determ;
	}
        
        
        //LU decomposition of A, L = lower triangular matrix, U = upper triangular matrix, P = permutation matrix
        public void lu(Matrix L, Matrix U, Matrix P, short [] sign)
	{
		final int m = rows_count, n = columns_count;
                
                //Set the determinant det(U) sign to 1
		sign[0] = 1;

		//Is A rectangular matrix ?
		if (m != n)
		{
			throw new MathMatrixNotSquareException ("MathMatrixNotSquareException: ", " invalid dimension of the matrix (rectangle matrix), can not perform LU decomposition; (rows_count, columns_count):  ", this);
		}

		//Create row permutation vector
		Matrix PR = new Matrix(1, n);

		//Create scale vector
		Matrix S = new Matrix(1, n);

		//Set diagonal items of L to 1, otherwise to 0
		//Set items of the row permutation matrix to <0; n-1>
		for (int i = 0; i < m; i++)
		{
			L.items[i][i] = 1.0;
			PR.items[0][i] = i;

			for (int j = 0; j < m; j++)
			{
				if (j != i)  
                                        L.items[i][j] = 0;

				P.items[i] [j] = 0;
			}
		}

		//Initialize U = A
		U.copy(this);

		//Find max item in each row to compute the scale vector
		for (int i = 0; i < n; i++)
		{
			double max_val = 0.0;

			for (int j = 0; j < n; j++)
			{
				if (abs(U.items[i][j]) > max_val)
					max_val = abs(U.items[i][j]);
			}

			//Actualize scale vector
			if (max_val > MIN_FLOAT)
				S.items[0][i] = 1.0 / max_val;
		}

		//Start LU decomposition
		for (int j = 0; j < n; j++)
		{
			for (int i = 0; i < j; i++)
			{
				double sum = U.items[i][j];

				//Compute new U ( i, j ) item: multiply ith row and j-th column
				for (int k = 0; k < i; k++) 
                                        sum -= U.items[i][k] * U.items[k][j];

				U.items[i][j] = sum;
			}

			//Initialize max_val and pivot index
			double max_val = 0.0;
			int i_pivot = n;

			//Find row that will be swapped and actualize row index
			for (int i = j; i < n; i++)
			{
				double sum = U.items[i][j];

				//Compute new U ( i, j ) item: multiply ith row and j-th column
				for (int k = 0; k < j; k++) 
                                        sum -= U.items[i][k] * U.items[k][j];

				//Compute new U (i, j)
				U.items[i][j] = sum;

				//Compute index of the pivot
				final double val = S.items[0][i] * abs(sum);

				if (val >= max_val)
				{
					max_val = val;
					i_pivot = i;
				}
			}

			//Perform row swaps in U,PR: j <-> i_pivot
			if ((j != i_pivot) && (i_pivot < n))
			{
				//Perform swap in U matrix
				final Matrix U_temp = U.getMatrix(i_pivot, i_pivot, 0, n - 1);
				U.replace(U.getMatrix(j, j, 0, n - 1), i_pivot, 0);
				U.replace(U_temp, j, 0);

				//Perform swap in the row permutation matrix
				final int perm_temp = (int)PR.items[0][i_pivot];
				PR.items[0][i_pivot] = PR.items[0][j];
				PR.items[0][j] = perm_temp;

				//Actualize also the scale vector
				S.items[0][i_pivot] = S.items[0][j];
                                
                                
				//Actualize sign of the determinant det(U)
				sign[0] *= -1;
			}

			//Change diagonal item U ( j, j ) = 0 to "small" value before the devision
			if (U.items[j][j] == 0.0)
				U.items[j][j] = MIN_FLOAT;

			//Actualize U (i, j) from diagonal items
			if (j != n - 1)
			{
				final double val = 1.0 / U.items[j][j];

				for (int i = j + 1; i < n; i++)
					U.items[i][j] *= val;
			}
		}

		//Process L matrix together with U matrix
		for (int i = 0; i < n; i++)
		{
			for (int j = 0; j < i; j++)
			{
				L.items[i][j] = U.items[i][j];
				U.items[i][j] = 0.0;
			}

			//Actualize permutation matrix from the row permutation matrix
			P.items[i] [(int)(PR.items[0][i])] = 1.0;
		}
	}


	//Inverse matrix calculation using LU decomposition
	public Matrix inv()
	{
		final int m = rows_count, n = columns_count;

                //Create copy of the matrix
                Matrix A = new Matrix(this);
                
		//Rectangular matrix
		if (m != n)
		{
			throw new MathMatrixNotSquareException ("MathMatrixNotSquareException: ", " invalid dimension of the matrix (rectangle matrix), can not compute inverse matrix; (rows_count, columns_count):  ", A);
		}

		//Find maximum
		final double max_val = A.max();

		if (max_val > MAX_FLOAT)
			throw new MathOverflowException ("MathOverflowException: bad scaled matrix, can not compute inverse matrix. ", "Max item > MAX_FLOAT.", max_val);

		//Create LU decomposition
		Matrix L = new Matrix(m, m);
		Matrix U = new Matrix(m, m);
		Matrix P = new Matrix(m, m, 0, 1);
                short [] sign = {1};
		A.lu(L, U, P, sign);
		
                //Compute X = L^-1 (lower triangular matrix)
		Matrix X = new Matrix(m, m);

		for ( int j = 0; j < m; j++)
		{
			X.items[j][j] = 1.0;

			for ( int i = j + 1; i < m; i++)
			{
				double sum = 0;

				for (int k = j; k <= i - 1; k++)
				{
					sum -= L.items[i][k] * X.items[k][j];
				}

				X.items[i][j] = sum;
			}
		}

		//Compute Y = U^-1 (upper triangular matrix)
		Matrix Y = new Matrix(m, m);

		for (int j = 0; j < m; j++)
		{
			Y.items[j] [j] = 1 / U.items[j][j];

			for (int i = j - 1; i >= 0; i--)
			{
				double sum = 0.0;

				for ( int k = i + 1; k <= j; k++)
				{
					sum -= U.items[i][k] * Y.items[k][j] / U.items[i][i];
				}

				Y.items[i][j] = sum;
			}
		}

		//Compute inverse matrix A^-1 = U^-1 * L^-1 = X * Y * P
		return (Y.mult(X)).mult(P);
	}
        
        
        //QR decomposition of the matrix
	//Based on modified Gram-Schmidt with reorthogonalization ( high accuracy ), Gander algorithm
	//Will work with rank deficient matrices
	public void qr(Matrix Q, Matrix R)
	{
		final int m = rows_count, n = columns_count;

		//First iteration : Q = A
		Q.copy(this);

		//Perform QR decomposition
		for (int i = 0; i < n; i++)
		{
			//Compute norm
			double tt = 0, t = (Q.getMatrix(0, m - 1, i, i)).norm();

			boolean orthogonalize = true;

			//Perform reortogonalization
			while (orthogonalize)
			{
				for (int j = 0; j <= i - 1; j++)
				{
					//Compute norm
					final Matrix S = ((Q.getMatrix(0, m - 1, j, j)).trans()).mult(Q.getMatrix(0, m - 1, i, i));

					//Actualize R item
					R.items[j][i] = R.items[j][i] + S.items[0][0];

					Q.replace(Q.getMatrix(0, m - 1, i, i).minus((Q.getMatrix(0, m - 1, j, j)).mult(S.items[0][0])), 0, i);
				}

				//Compute norm
				tt = (Q.getMatrix(0, m - 1, i, i)).norm();

				//Perform reorthogonalization
				if ((tt > 10.0 * MIN_FLOAT * t) && (tt < t / 10.0))
				{
					orthogonalize = true;
					t = tt;
				}

				//Stop orthogonalization process
				else
				{
					orthogonalize = false;

					//If column linear dependent, stop orthogonalization
					if (tt < 10.0 * MIN_FLOAT  * t)
						tt = 0.0;
				}
			}

			//Compute diagonal item in R
			R.items[i][i] = tt;

			if (tt * MIN_FLOAT != 0.0)
				tt = 1.0 / tt;
			else
				tt = 0.0;

			//Update Q matrix
			Q.replace((Q.getMatrix(0, m - 1, i, i)).mult(tt), 0, i);
		}
	}


        //QR decomposition of the matrix, return also a permutation matrix P
	//Based on Businger and Golub algorithm
	//Will not work with rank deficient matrices
	void qr( Matrix  Q, Matrix  R, Matrix P)
	{
		final int m = rows_count, n = columns_count;

		//Bad dimensions of A: rank defficient matrix
		if (m < n)
		{
			throw new BadDataException("BadDataException: invalid dimension of the matrix A, m < n.", "Can not compute QR decomposition with columns pivoting.");
		}

		//Bad dimensions of Q
		if ((Q.rows() != m) || (Q.cols() != m))
		{
			throw new BadDataException("BadDataException: invalid dimension of the matrix Q(m, m).", "Can not compute QR decomposition with columns pivoting.");
		}

		//Bad dimensions of R
		if ((R.rows() != m) || (R.cols() != n))
		{
			throw new BadDataException("BadDataException: invalid dimension of the matrix R (m, n).", "Can not compute QR decomposition with columns pivoting.");
		}

		//Bad dimensions of P
		if ((P.rows() != n) || (P.cols() != n))
		{
			throw new BadDataException("BadDataException: Matrix A is rank deficient (m <n).", "Can not compute QR decomposition with columns pivoting, must be of m >=n.");
		}

		//First iteration: R = A, Q = E, P = E
		R.copy(this);

		for ( int i = 0; i < m; i++) 
			Q.items[i][i] = 1.0;

		for ( int i = 0; i < n; i++)
			P.items[i][i] = 1.0;

		//Compute the norms
		Matrix col_norms = new Matrix(1, n);

		for ( int i = 0; i < n; i++)
		{
			final Matrix col_norm = ((R.getMatrix(0, m - 1, i, i)).trans()).mult(R.getMatrix(0, m - 1, i, i));
			col_norms.items[0][i] = col_norm.items[0][0];
		}

		//Perform QR decomposition with columns pivoting
		for ( int i = 0; i < n - 1; i++)
		{
			//Find max column norm
			double max_col_norm = col_norms.items[0][i];
			 int permut_index = i;

			for ( int j = i + 1; j < n; j++)
			{
				//Remeber new max and permutation index
				if (col_norms.items[0][j] > max_col_norm)
				{
					max_col_norm = col_norms.items[0][j];
					permut_index = j;
				}
			}

			//Stop, can not compute QR decomposition
			if (col_norms.items[0][permut_index] == 0.0)
				break;

			//Performs pivoting
			if (permut_index != i)
			{
				//Swap permutaion matrix
				final Matrix TMP = P.getMatrix(0, n - 1, i, i);
				P.replace(P.getMatrix(0, n - 1, permut_index, permut_index), 0, i);
				P.replace(TMP, 0, permut_index);

				//Swap R matrix
				final Matrix  TMP2 = R.getMatrix(0, m - 1, i, i);
				R.replace(R.getMatrix(0, m - 1, permut_index, permut_index), 0, i);
				R.replace(TMP2, 0, permut_index);

				//Swap column norms matrix
				final double tmp = col_norms.items[0][i];
				col_norms.items[0][i] = col_norms.items[0][permut_index];
				col_norms.items[0][permut_index] = tmp;
			}

			//Compute Householder vector
			Matrix  H = (R.getMatrix(0, m - 1, i, i).hous(i, m - 1));

			//Apply left Householder transformation
			R.copy(R.minus(H.mult(((H.trans()).mult(R)))));

			//Apply right Householder transformation
			Q.copy(Q.minus((Q.mult(H)).mult(H.trans())));

			//Norm downdate using Hadamard product
			final Matrix col_norm = R.getMatrix(i, i, i + 1, n - 1).had(R.getMatrix(i, i, i + 1, n - 1));
			col_norms.replace(col_norms.getMatrix(0, 0, i + 1, n - 1).minus(col_norm), 0, i + 1);
		}
	}
        
        
        //Householder rotation
	public Matrix hous(final int i, final int j)
	{
		final int m = rows_count, n = columns_count;

		//Not a vector, n > 1
		if (n > 1)
		{
			throw new BadDataException("BadDataException: Matrix A is not a column vector. ", "Can not compute the Householder vector.");
		}

		//Bad index condition: i > j
		if (i > j)
		{
			throw new BadDataException("BadDataException: index i > j. ", "Can bot compute the Householder vector.");
		}

		//Bad index number: j > m - 1
		if (j > m - 1)
		{
			throw new BadDataException("BadDataException: index i > n - 1 . ", "Can bot compute the Householder vector.");
		}

		//Initialize Householder vector
		Matrix H = new Matrix(m, n);
		H.replace(this.getMatrix(i, j, 0, 0), i, 0);

		//Compute Householder vector
		H.items[i][0] = H.items[i] [0] + (this.items[i][0] >= 0 ? (this.getMatrix(i, j, 0, 0)).norm() : -(this.getMatrix(i, j, 0, 0)).norm());

		//Normalize Householder vector
		final Matrix  norm2 = (H.trans()).mult(H);
		if (norm2.items[0][0] > 0)
			H = H.mult(sqrt(2.0 / (norm2.items[0][0])));

		return H;
	}
        
        
        //Pseudo-inverse matrix calculation using double QR decomposition
	//More numerically stable than Moore-Penrose algorithm
	//Use Goodall, Golub & Loan algorithm, works for rank deficient matrix
	public Matrix pinv()
	{
		int m = rows_count, n = columns_count;
		boolean transpose = false;
		Matrix  AT = new Matrix(n, m);

		//Rand deficient matrix
		if (m < n)
		{
			//Transpose matrix
			AT = this.trans();

			//Swap m, n
			final int temp = m;
			m = n; n = temp;

			//Set flag as true
			transpose = true;
		}

		//Initialize matrix M
		Matrix AA = (transpose ? AT : this);

		//QR decomposition of A with a permutation matrix P
		Matrix Q = new  Matrix(m, m);
                Matrix R = new Matrix (m, n);
		Matrix P = new Matrix (n, n);
		AA.qr(Q, R, P);

		//Permutation matrix represented by the row matrix
		Matrix PR = new Matrix (1, n);
		Matrix M = new Matrix(1, n);

		for (int i = 0; i < n; i++)
			M.items[0][i] = i;

		//Convert P to row matrix PR
		PR = M.mult(P);

		//Reversed row permutation matrix
		Matrix PR_R = new Matrix(1, n);

		for (int i = 0; i < n; i++)
			PR_R.items[0][(int)PR.items[0][i]] = i;

		//Set tolerance
		double eps = Math.max(m, n) * this.norm() * EPS;

		//Find index of the first diagonal element < eps
		int k = 0;

		for (; k < n; k++)
		{
			if (abs(R.items[k][k]) < eps)
				break;
		}

		//Get R submatrix
		Matrix RS = R.getMatrix(0, k - 1, 0, n - 1);

		//Get transposed Q submatrix
		Matrix QTS = (Q.getMatrix(0, m - 1, 0, k - 1)).trans();

		//Correct matrix (not rank deficient matrix)
		if (k == n)
		{
			//Compute reversed permutation matrix ER (n,n) from reversed row permutation matrix
			Matrix P_R = new Matrix(n, n);

			for (int i = 0; i < n; i++)
			{
				P_R.items[i][(int)PR_R.items[0][i]] = 1.0;
			}

			//Compute inverse matrix
			Matrix RI = (RS.inv()).mult(QTS);

			//Compute inverse matrix using the reversed permutation
			return P_R .mult(RI);
		}

		//Rank deficient matrix
		else
		{
			//Compute second QR factorization
			Matrix Q2 = new Matrix(n, k);
                        Matrix R2 = new Matrix(k, k);
        
			(RS.trans()).qr(Q2, R2);

			//Compute reversed permutation matrix PR (n,n) from reversed row permutation matrix
			Matrix P_R = new Matrix(n, k);

			for (int i = 0; i < n; i++)
			{
				for (int j = 0; j < k; j++)
				{
					P_R.items[i][j] = Q2.items[(int)PR_R.items[0][i]][j];
				}
			}

			//Transpose R2 matrix
			Matrix R2T = R2.trans();

			//Compute inverse: from squared matrices
			Matrix R2I = (R2T.inv()).mult(QTS);

			//Compute pseudoinverse
			Matrix AA_I = P_R .mult(R2I);

			//Return A_I or transposed A_I
			return (transpose ? AA_I.trans() : AA_I);
		}
	}  
        
        
        public void print() 
        {
                //Print matrix
                System.out.println("");
                for (int i = 0; i < rows_count; i++) 
                {
                        for (int j = 0; j < columns_count; j++) 
                        {
                                System.out.print(items[i][j] + " ");
                        }

                        System.out.println("");
                }
        }
}
