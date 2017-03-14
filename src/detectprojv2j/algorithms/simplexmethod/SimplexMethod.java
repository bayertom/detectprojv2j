// Description: Downhill simplex optimalization method

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

package detectprojv2j.algorithms.simplexmethod;

import java.io.PrintStream;
import java.util.Random;
import static java.lang.Math.*;

import detectprojv2j.types.IResiduals;

import static detectprojv2j.consts.Consts.*;

import detectprojv2j.structures.matrix.Matrix;

import detectprojv2j.algorithms.nonlinearleastsquares.NonLinearLeastSquares;


public class SimplexMethod {
        
        public static double NelderMead ( IResiduals function, Matrix W, Matrix X, Matrix Y, Matrix V, final Matrix XMIN, final Matrix XMAX, int iterations[], final double max_error, final int max_iterations, final boolean add_x0,PrintStream s )
        {
                //Compute Nelder-Mead method for a function
                //Algorithm based on Lagarias, Reeds, Wright, 1998, SIAM
                final int n = XMIN.cols(), m = n + 1, m1 = W.rows();
                final double Rho = 1.0, Xi = 2.0, Gama = 0.5, Sigma = 0.5;

                //Create matrices for the simplex operations
                Matrix IX = new Matrix( m, 1 );
                Matrix VV2 = new Matrix( m, 1 );
                Matrix VR = new Matrix( m1, 1 ); 
                Matrix VS = new Matrix( m1, 1 ); 
                Matrix VE = new Matrix( m1, 1 ); 
                Matrix VCO = new Matrix( m1, 1 ); 
                Matrix VCI = new Matrix( m1, 1 ); 
                Matrix YR = new Matrix( m1, 1 );
                Matrix YS = new Matrix( m1, 1 );
                Matrix YE = new Matrix(m1, 1);
                Matrix YCO = new Matrix(m1, 1); 
                Matrix YCI = new Matrix(m1, 1); 
                Matrix WR = new Matrix(m1, m1, 0, 1); 
                Matrix WS = new Matrix(m1, m1, 0, 1); 
                Matrix WE = new Matrix(m1, m1, 0, 1); 
                Matrix WCO = new Matrix(m1, m1, 0, 1); 
                Matrix WCI = new Matrix(m1, m1, 0, 1);
                Matrix YBEST = new Matrix(m1, 1);
                Matrix VBEST = new Matrix(1, 1);
                Matrix WBEST = new Matrix(m1, m1, 0, 1);
                Matrix XX = new Matrix(m, n);
                
                //Add the initial solution to the simplex
                if (add_x0)
                        XX.replace(X, 0, 0);
                
                //Create random simplex
                createRandSimplex ( XMIN, XMAX, XX, add_x0 );

                //Set iterations to 0
                iterations[0] = 0;

                //Compute initial function values
                for (int i = 0; i < m; i++)
                {
                        Matrix VVI = new Matrix(m1, 1);
                        Matrix YI = new Matrix(m1, 1);
                        Matrix WI = new Matrix(m1, m1, 0, 1);

                        Matrix XXI = XX.row(i);

                        function.f(XXI, YI, VVI, WI);
                        final double res2 = (((VVI.trans()).mult(W)).mult(VVI)).norm();
                        VV2.items[i][0] = res2;
                }

                //Sort residuals in ascending order
                VV2.sort(IX, -1 );

                //Change order of XX rows: row i to row IX (i)
                Matrix XS = new Matrix( m, n );

                for ( int i = 0; i < m; i++ )
                        XS.row ( XX.row((int)(IX.items[i][0])), i );

                //Assign sorted matrix to XX
                XX.copy(XS);

                //Perform Nelder-Mead algorithm
                do
                {
                        //Compute centroid
                        Matrix XC = (( XX.getMatrix(0, n - 1, 0, n - 1 ) ).sumCols()).mult( 1.0 / n);

                        //Compute reflection point and residuals
                        Matrix XR = (XC.mult( 1.0 + Rho )).minus((XX.getMatrix(n, n, 0, n - 1 )).mult(Rho));
 
                        //Reflection into the search space
                        reflection ( XMIN, XMAX, n, XR );

                        //Compute residuals
                        function.f ( XR, YR, VR, WR );
                        final double  fr = (((VR.trans()).mult(W)).mult(VR)).norm();

                        //A reflection point acceptable
                        if ( ( VV2.items [0][0] <= fr ) && ( fr < VV2.items[n - 1][0] ) )
                        {
                                XX.replace ( XR, n, 0 );
                                VV2.items[n][0] = fr;
                        }

                        //Expansion of the simplex
                        else if ( fr < VV2.items[0][0] )
                        {
                                //Compute expanded point
                                Matrix XE = (XC.mult( 1.0 + Rho * Xi )).minus(((XX.getMatrix(n, n, 0, n - 1 )).mult(Rho)).mult(Xi));
          
                                //Reflection into the search space
                                reflection ( XMIN, XMAX, n, XE );

                                //Compute residuals
                                function.f ( XE, YE, VE, WE );
                                final double  fe = (((VE.trans()).mult(W)).mult(VE)).norm();

                                //An expanded point is acceptable
                                if ( fe < fr )
                                {
                                        XX.replace ( XE, n, 0 );
                                        VV2.items [n][0] = fe;
                                }

                                //An expanded point is not acceptable (use a reflected one)
                                else
                                {
                                        XX.replace ( XR, n, 0 );
                                        VV2.items[n][0] = fr;
                                }
                        }

                        //Outside contraction of the simplex
                        else if ( ( VV2.items[n - 1][0] <= fr ) && ( fr < VV2.items[n][0] ) )
                        {
                                //Compute outside contracted point
                                Matrix XCO = XC.mult( 1.0 + Rho * Gama ).minus((XX.getMatrix(n, n, 0, n - 1 )).mult(Rho * Gama));

                                //Reflection into the search space
                                reflection ( XMIN, XMAX, n, XCO );

                                //Compute residuals
                                function.f( XCO, YCO, VCO, WCO );
                                final double  fco = (((VCO.trans()).mult(W)).mult(VCO)).norm();

                                //An outside contracted point is acceptable
                                if ( fco < VV2.items[n][0] )
                                {
                                        XX.replace ( XCO, n, 0 );
                                        VV2.items[n][0] = fco;
                                }

                                //An outside contracted point is not acceptable: shrink a simplex
                                else
                                {
                                        shrink ( function, W, XX, XMIN, XMAX, Y, VV2, Sigma );
                                        
                                        //Increment iterations
                                        iterations[0] ++;
                                }
                        }

                        //Inside contraction of the simplex
                        else
                        {
                                //Compute outside contracted point
                                Matrix XCI = (XC.mult ( 1.0 - Gama )).plus((XX.getMatrix(n, n, 0, n - 1 )).mult(Gama));

                                //Reflection into the search space
                                reflection ( XMIN, XMAX, n, XCI );

                                //Compute residuals
                                function.f ( XCI, YCI, VCI, WCI );
                                final double  fci = (((VCI.trans()).mult(W)).mult(VCI)).norm();

                                //An inside contracted point is acceptable
                                if ( fci < VV2.items[n][0] )
                                {
                                        XX.replace ( XCI, n, 0 );
                                        VV2.items[n][0] = fci;
                                }

                                //An inside contracted point is not acceptable: shrink a simplex
                                else
                                {
                                        shrink ( function, W, XX, XMIN, XMAX, Y, VV2, Sigma );
                                        //XX.print();
                                        //Increment iterations
                                        iterations[0] ++;
                                }
                        }

                        //Sort residuals in ascending order
                        VV2.sort ( IX, -1 );

                        //Change order of XX rows: row i to row IX (i)
                        for ( int i = 0; i < m; i++ )
                                XS.row ( XX.row ((int)(IX.items[i][0])), i );

                        //Assign sorted matrix to XX
                        XX.copy(XS);

                        //Increment iterations
                        iterations[0] ++;

                        if ( iterations[0] % 100 == 0 )
                        {
                                System.out.print (".");
                        }
                        
                }
                while ( ( iterations[0] < max_iterations ) && ( abs ( VV2.items[0][0] - VV2.items[n][0] ) > max_error ) );

                //Get minimum
                X.copy(XX.getMatrix(0, 0, 0, n - 1 ));

                //Compute residuals for the found solution
                function.f ( X, Y, V, W );
                final double fx_min =(((V.trans()).mult(W)).mult(V)).norm();

                //System.out.print(" [" + iterations[0] + " it., fmin = " + fx_min + "]" + '\n');

                return fx_min;
        }


        public static void createRandSimplex ( final Matrix XMIN, final Matrix XMAX, Matrix XX, final boolean add_x0 )
        {
                //Create random simplex
                final int dim = XMIN.cols();

                //Initialize random number generator
                Random rn = new Random();

                //Create random simplex
                for ( int i = 0; i < dim + 1; i++ )
                {
                        if ((i != 0) && (add_x0) || (!add_x0))
                        {
                                for ( int j = 0; j < dim; j++ )
                                {
                                        double rand_num = XMIN.items[0][j] + (XMAX.items[0][j] - XMIN.items[0][j]) * rn.nextDouble();          
                                        XX.items[i][j] = rand_num;
                                }
                        }
                }
        }


        public static void shrink ( IResiduals function, Matrix W, Matrix XX, final Matrix XMIN, final Matrix XMAX, Matrix Y, Matrix VV2, final double Sigma )
        {
                //Shrink a simplex
                final int m = XX.rows(), n = XX.cols(), m1 = W.rows();
                
                //Create matrices
                Matrix V1 = new Matrix( m1, 1 );
                Matrix VSH = new Matrix ( m1, 1 );
                Matrix Y1 = new Matrix ( m1, 1 );
                Matrix YSH = new Matrix ( m1, 1) ;
                Matrix W1 = new Matrix ( m1, m1, 0, 1 );
                Matrix WSH = new Matrix ( m1, m1, 0, 1 );

                //Get first point of the simplex (best)
                Matrix X1 = XX.getMatrix(0, 0, 0, n - 1 );

                //Compute residuals
                function.f ( X1, Y1, V1, W1 );
                final double  fv1 = (((V1.trans()).mult(W1)).mult(V1)).norm();

                //Actualize VV matrix
                VV2.items[0][0] = fv1;

                //Shrink remaining simplex points
                for ( int i = 1; i < n + 1 ; i++ )
                {
                        //Compute shrink point
                        Matrix XSH = (XX.getMatrix(0, 0, 0, n - 1 )).plus(((XX.getMatrix(i, i, 0, n - 1 )).minus(XX.getMatrix(0, 0, 0, n - 1 ) )).mult(Sigma));

                        reflection ( XMIN, XMAX, n, XSH );

                        //Compute residuals
                        function.f ( XSH, YSH, VSH, WSH );
                        final double  fvs = (((VSH.trans()).mult(WSH)).mult(VSH)).norm();

                        //Set submatrix
                        XX.replace ( XSH, i, 0 );
                        VV2.items[i][0] = fvs;
                }
        }


        public static void reflection ( final Matrix XMIN, final Matrix XMAX, final int dim, Matrix X )
        {
                //Set elements of the simplex into the search space represented by the n-dimensional cuboid
               
                //Transpose matrix
                Matrix XT = X.trans();
	
                //Call reflection
                NonLinearLeastSquares.reflection(XT, XMIN.trans(), XMAX.trans());

                //Assign transposed matrix
                X.copy(XT.trans());
        }
}