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

package detectprojv2j.algorithms.nonlinearleastsquares;

import static java.lang.Math.*;

import static detectprojv2j.consts.Consts.*;

import detectprojv2j.types.IJacobian;
import detectprojv2j.types.IResiduals;

import detectprojv2j.structures.matrix.Matrix;


public class NonLinearLeastSquares {
        
        public static double  BFGSH(IJacobian function_j, IResiduals function_v, Matrix  W, Matrix  X, Matrix  Y, Matrix  V, final Matrix  A, final Matrix  B, int [] iterations,
	final double alpha, final double nu, final double max_error, final int max_iterations, final double max_diff)
        {
                //Solving Non-linear Least Squares using the hybrid BFGS algorithm
                //Combination of the Gauss-Newton and BFGS method, algorithm by L Luksan
                //Default values: nu = 0.0001, alpha = 0.0001;

                //Create matrices
                final int m = W.rows(), n = X.rows();
                Matrix J = new Matrix(m, n);
                Matrix V2 = new Matrix(m, 1); 
                Matrix dX = new Matrix(n, 1); 
                Matrix E = new Matrix(n, n, 0.0, 1.0);
                Matrix H_new = new Matrix(n, n);
                Matrix G_new = new Matrix(n, 1); 

                //Assign matrix
                Matrix  Y2 = Y;

                //Compute initial V matrix (residuals)
                function_v.f(X, Y, V, W);
                //X.print();

                //Compute initial J matrix
                function_j.f(X, J);

                //Compute matrices
                Matrix  H = ((J.trans()).mult(W)).mult(J);
                Matrix  G = ((J.trans()).mult(W)).mult(V);
                H_new.copy(H);
                G_new.copy(G);

                //Compute objective function
                double fx = (((V.trans()).mult(W)).mult(V)).norm();
                double fx_new = fx;

                //Set iterations to 0
                iterations[0] = 0;

                //Perform iterations
                while (iterations[0] < max_iterations)
                {
                        //Increment iterations
                        iterations[0]++;

                        //Compute new dX
                        dX = (H.pinv()).mult(G).mult(-1.0);
                        //if (iterations[0] == 16)
                        //{
                        //        H.print();
                        //        G.print();
                        //        H.pinv().print();
                        //}
                        //Too long step, reduction
                        final double ndX = dX.norm();
                        if (ndX > MAX_NLS_STEP_LENGTH) 
                                dX = dX.mult(100 / ndX);

                        //Compute new trial X
                        Matrix  X2 = X.plus(dX);

                        //Reflection into the search space
                        reflection(X2, A, B);

                        //Compute new trial V matrix (residuals)
                        function_v.f(X2, Y2, V2, W);

                        //Apply back-step using a bisection
                        final double t_min = 1.0e-10;
                        double t = 1.0;

                        while ((V2.sum2() > V.sum2() + (((V.trans()).mult(J)).mult(dX)).sum() * t * alpha * 2.0) && (t > t_min))
                        {
                                //Step t bisection
                                t /= 2;

                                //Compute new X2
                                X2 = X.plus(dX.mult(t));

                                //Reflection into the search space
                                reflection(X2, A, B);

                                //Compute new V matrix: do not change parameters in one iteration step
                                function_v.f(X2, Y2, V2, W);
                        }

                        //Compute new X using back-step method
                        X.copy(X.plus(dX.mult(t)));

                        //Reflection into the search space
                        reflection(X, A, B);

                        //Compute new V matrix and residual matrix V
                        function_v.f(X, Y, V, W);

                        //Compute new J matrix
                        function_j.f(X, J);

                        //std::cout << iterations;
                        //X.print();

                        //Compute new residuals and gradient
                        G_new = ((J.trans()).mult(W)).mult(V);
                        fx_new = (((V.trans()).mult(W)).mult(V)).norm();

                        //Terminal condition
                        if ((G.norm() < max_error) /*|| (fabs(fx_new - fx) < 1.0 * max_diff * std::min(1.0, fx))*/ || (fx < max_error) || ( dX.norm() < 1.0e-10 ) )
                        {
                                break;
                        }

                        //Compute selection criterium
                        final double df = (fx - fx_new) / fx;

                        //Compute Hessian matrix as H=J*W*J (Gauss-Newton)
                        if (df > nu)
                        {
                                H_new = ((J.trans()).mult(W)).mult(J);
                        }

                        //Compute Hessian matrix from BFGS
                        else
                        {
                                //Compute solution difference
                                Matrix  s = dX.mult(t);

                                //Compute gradient difference
                                Matrix  y = G_new.minus(G);

                                //Compute denominators
                                final double ys = ((y.trans()).mult(s)).sum();
                                final double shs = (((s.trans()).mult(H)).mult(s)).sum();

                                //Compute update, if y * s > 0 (symmetric positive definite update)
                                if (ys > 0)
                                {
                                        Matrix dH = new Matrix (n, n);

                                        if (abs(ys) > MIN_FLOAT && abs(shs) > MIN_FLOAT)
                                        {
                                                Matrix dH1 = (y.mult(y.trans())).mult(1.0/ys);
                                                Matrix dH2 = (H.mult(s)).mult(((H.mult(s)).trans()).mult(1.0/shs));
                                                dH = dH.plus(dH1.minus(dH2));
                                        }

                                        //Compute Hessian matrix using quasi-Newton update: H = J*W*J + dH
                                        H_new = (((J.trans()).mult(W)).mult(J)).plus(dH);
                                }

                                //Do not update, if y * s < = 0
                                else
                                        H_new = ((J.trans()).mult(W)).mult(J);
                        }

                        //Assign values
                        H = H_new;
                        G = G_new;
                        fx = fx_new;

                        //X.print(); 
                        //dX.print(output);
                        //dX.print();
                        //RES.print(output);*/
                        //*output << dx << '\n';
                        //*output << RES(0, 0) << '\n';
                }

                //Compute final values in V
                function_v.f(X, Y, V, W);

                //Evaluate minimum
                final double fxmin = (((V.trans()).mult(W)).mult(V)).norm();

                //System.out.print(" [" + iterations[0] + " it., fmin = " + fxmin + "]" + '\n');

                return fxmin;
        }


        public static void reflection(Matrix  X, final Matrix  XMIN, final Matrix  XMAX)
        {
                //Reflect elements of vectors into the search space represented by the n-dimensional cuboid
                final  int n = X.rows();
                final double max_multiplier = 100;

                for ( int i = 0; i < n; i++)
                {
                        //Process each element of the vector
                        while ((X.items[i][0] < XMIN.items[i][0]) || (X.items[i][0] > XMAX.items[i][0]))
                        {
                                //XMIN == XMAX
                                if (XMAX.items[i][0] - XMIN.items[i][0] < EPS)
                                {
                                        X.items[i][0] = XMIN.items[i][0];
                                        break;
                                }

                                //Left form the lower bound
                                else if (X.items[i][0] > XMAX.items[i][0])
                                {
                                        X.items[i][0] = 2 * XMAX.items[i][0] - X.items[i][0];
                                }

                                //Right to the upper bound
                                else if (X.items[i][0] < XMIN.items[i][0])
                                {
                                         X.items[i][0] = 2 * XMIN.items[i][0] - X.items[i][0];
                                }
                        }
                }
        }

        
}
