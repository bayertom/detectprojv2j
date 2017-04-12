// Description: Find global minimum using the differential evolution algorithm

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


package detectprojv2j.algorithms.geneticalgorithms;

import detectprojv2j.algorithms.nonlinearleastsquares.NonLinearLeastSquares;
import static java.lang.Math.*;
import java.io.PrintStream;

import detectprojv2j.types.IResiduals;
import detectprojv2j.types.TAdaptiveControl;
import detectprojv2j.types.TMutationStrategy;

import detectprojv2j.structures.matrix.Matrix;

import static detectprojv2j.consts.Consts.*;

import detectprojv2j.exceptions.BadDataException;
import detectprojv2j.exceptions.MathMatrixDifferentSizeException;

public class DifferentialEvolution {
        
        public static double  diffEvolution ( IResiduals function, final int population_size, final double epsilon, final int max_gener, Matrix  F, double [] CR, final TMutationStrategy mutation_strategy,
                final TAdaptiveControl adaptive_control, Matrix  W, Matrix  X, Matrix  Y, Matrix  RES, Matrix  XMIN, Matrix  XMAX, Matrix  XAVER, double [] aver_res, double [] fx_max,  int [] gener, final boolean add_x0, PrintStream output)
        {

                //Compute global minimum of the function dim <2,m> using the current differential evolution algorithm
                int dim = XMIN.cols(), row_index_min = 0, column_index_min = 0;
                boolean improve = false;

                //Bad matrix size: throw exception
                if (XMIN.cols() != XMAX.cols())
                        throw new MathMatrixDifferentSizeException ("MathMatrixDifferentSizeException: ", " invalid dimension of the matrices, can not perform differential evolution; (rows_count columns_count):  ", XMIN, XMAX);

                //Too small population
                if (population_size < dim + 1)
                        throw new BadDataException("BadDataException: too small population (pop < dim + 1).", "Can not find the global minimum in this interval...");

                //Bad limits: throw exception
                for ( int i = 0; i < dim; i++)
                        if (XMIN.items[0][i] > XMAX.items[0][i]) throw new BadDataException("BadDataException: all limits a(i) > b(i), should be a(i) < b(i).", "Can not find the global minimum in this interval...");

                //Create population matrices: matrix of arguments and matrix of values
                Matrix P_A = new Matrix(population_size, dim);
                Matrix P_V = new Matrix(population_size, 1);

                //Add the initial solution to the population
                if (add_x0) 
                        P_A.replace(X, 0, 0);

                //Create intial populaton
                createInitialPopulation(function, XMIN, XMAX, W, Y, RES, population_size, dim, P_A, P_V, add_x0);
                
                //Initialize min and old min
                double fx_min = P_V.min(), fx_min_old_100 = fx_min, fx_min_old = fx_min;

                //Assign population attributes and values (residuals)
                Matrix  Q_A = new Matrix(P_A);
                Matrix  Q_V = new Matrix(P_V);

                //Set generation to zero
                gener[0] = 0;

                Matrix FM = new Matrix(population_size, 1);
                Matrix CRM = new Matrix(population_size, 1);

                //Run differential evolution
                while (gener[0] < max_gener)
                {
                        //Process each element of the population: apply differential evolution operators to one element, slightly modified version
                        //Current version differential evolution apply each operator to all population

                        //Set the mutation and crossover factors depending on the adaptive control
                        if (adaptive_control == TAdaptiveControl.AdaptiveDecreasing)
                                F.items[0][0] = 0.5 * (max_gener - gener[0]) / max_gener;

                        //Set initial dg
                        double dg = 1.2;

                        for ( int i = 0; i < population_size; i++)
                        {
                                //Set mutation and cross-over factors depending on the adaptive control
                                if (adaptive_control == TAdaptiveControl.AdaptiveRandom)
                                {
                                        F.items[0][0] = 0.5 * (1.0 + random());   //check
                                }

                                else if (adaptive_control == TAdaptiveControl.Jitter)
                                {
                                        //Create vector of random numbers (dim, 1)
                                        for ( int j = 0; j < dim; j++)
                                        {
                                                //Generate the random number (0,1)
                                                final double r = random();

                                                F.items[0][j] = 0.5 * (1 + 0.001 * (r - 0.5));
                                        }
                                }

                                else if ( ( adaptive_control == TAdaptiveControl.MFDE ) && (gener[0] > 0) )
                                {
                                        //Generate the random number (0,1)
                                        final double r = random();

                                        //Modify the mutation factor F: 
                                        //Improvement of the solution
                                        if (fx_min < fx_min_old) //Improvement
                                        {
                                                F.items[0][0] = 1.5 * sqrt(r * r * dg);
                                        }

                                        //No improvement
                                        else
                                        {
                                                F.items[0][0] = sqrt(r * r * dg) - 0.2;
                                        }

                                        //Decrement dg
                                        dg -= 1.0 / population_size;
                                }

                                else if (adaptive_control == TAdaptiveControl.SAM)
                                {
                                        final double TAU1 = 0.1;
                                        final double r1 = random();

                                        //Update mutation factor
                                        if (r1 < TAU1)
                                        {
                                                final double r2 = random();
                                                F.items[0][0] = 0.1 + 0.9  * r2;
                                                FM.items[i][0] = F.items[0][0];
                                        }

                                        //Use the mutation factor from the previous generation
                                        else F.items[0][0] = FM.items[i][0];

                                        final double r3 = random();

                                        //Update cross-over ratio
                                        if (r3 < TAU1)
                                        {
                                                final double r4 = random();
                                                CR[0] = r4;
                                                CRM.items[i][0] = CR[0];
                                        }

                                        else CR[0] = CRM.items[i][0];
                                }

                                //Set mutation strategy, create mutated vector U
                                Matrix  U = new Matrix(1, dim);
                                Matrix AS = new Matrix(3, dim);

                                if (mutation_strategy == TMutationStrategy.DERand1Strategy)
                                        mutationStrategyDERand1(P_A, i, population_size, F, U);
                                else if (mutation_strategy == TMutationStrategy.DERand2Strategy)
                                        mutationStrategyDERand2(P_A, i, population_size, F, U);
                                else if (mutation_strategy == TMutationStrategy.DERandDir1Strategy)
                                        mutationStrategyDERandDir1(P_A, P_V,  i, population_size, F, U);
                                else if (mutation_strategy == TMutationStrategy.DERandDir2Strategy)
                                        mutationStrategyDERandDir2(P_A, P_V, i, population_size, F, U);
                                else if (mutation_strategy == TMutationStrategy.DERandBest1Strategy)
                                        mutationStrategyDERandBest1(P_A, P_V, i, population_size, F, U);
                                else if (mutation_strategy == TMutationStrategy.DERandBest2Strategy)
                                        mutationStrategyDERandBest2(P_A, P_V, i, population_size, F, U);
                                else if (mutation_strategy == TMutationStrategy.DERandBestDir1Strategy)
                                        mutationStrategyDERandBestDir1(P_A, P_V, i, population_size, F, U);
                                else if (mutation_strategy == TMutationStrategy.DETargetToBest1Strategy)
                                        mutationStrategyDETargetToBest1(P_A, P_V, i, population_size, F, U);
                                else if (mutation_strategy == TMutationStrategy.SACPStrategy)
                                        mutationStrategySACP(P_A, P_V, i, population_size, aver_res[0], F, CR, U);

                                //Perform cross-over, create vector V
                                Matrix  V = P_A.row(i); //Assign V = X
                                crossover(U, CR[0], dim, V);

                                //Perform redlection in to the search space
                                reflection(V, XMIN, XMAX);

                                //Compute residuals for perturbated vector V
                                double function_val_y;

                                try
                                {
                                        function.f(V, Y, RES, W);
                                        function_val_y = (((RES.trans()).mult(W)).mult(RES)).norm();
                                }

                                catch (Exception error)
                                {
                                        function_val_y = MAX_FLOAT;
                                }

                                //Replacement rule: new value is better, update Q
                                if (function_val_y <= P_V.items[i][0])
                                {
                                        Q_A.row(V, i);
                                        Q_V.items[i][0] = function_val_y;
                                }
                        }

                        //Replace P with Q: P elements can not be overwritten inside the cycle
                        P_A = Q_A;
                        P_V = Q_V;

                        //Compute average of the population
                        XAVER = (P_A.sumCols()).mult( 1.0 / population_size);

                        //Actualize new maximum, minimum and average of the population
                        int [] position ={0,0};
                        fx_min_old = fx_min;
                        fx_max[0] = P_V.max();
                        fx_min = P_V.min(position);
                        row_index_min = position[0];
                        column_index_min = position[1];
                        aver_res[0] = P_V.sumCol(0) / population_size;

                        //Compute residual difference for population
                        double diff = fx_max[0] - fx_min;

                        //Increment generation
                        gener[0]++;

                        //Terminal condition: population diversity, no improvement during the last 100 generations
                        if ((diff < epsilon * max(1.0, fx_min)) && (fx_min < 1.0e2)|| ((gener[0] % 100 == 0) && (abs(fx_min - fx_min_old_100) < epsilon * max(1.0, fx_min)) && (fx_min < 1.0e2)))
                        {
                                break;
                        }

                        //Remeber minimal value for every 100-th generation
                        if (gener[0] % 100 == 0)
                        {
                                fx_min_old_100 = fx_min;
                        }
                }

                //Actualize minimum argument
                X.copy(P_A.row(row_index_min));

                //Compute residuals
                function.f(X, Y, RES, W );

                //System.out.print(" [" + gener[0] + " it., fmin = " + fx_min + "]" + '\n');

                return fx_min;
        }


        public static void createInitialPopulation(IResiduals function, final Matrix  XMIN, final Matrix  XMAX, Matrix  W,  Matrix  Y, Matrix  RES, final int population_size, final  int dim, Matrix  P_A, Matrix  P_V, final boolean add_x0)
        {
                //Create initial population

                //Create individual and comoute its cost
                for ( int i = 0; i < population_size; i++)
                {
                        //Create random vectors
                        if ( ( i != 0 ) && ( add_x0 ) || ( ! add_x0 ) )
                        {
                                for ( int j = 0; j < dim; j++)
                                        P_A.items[i][j] = XMIN.items[0][j] + (XMAX.items[0][j] - XMIN.items[0][j]) * random();
                        }

                        //Compute function values for random population
                        try
                        {
                                //Get current row
                                Matrix  P_AR = P_A.row(i);

                                //Compute residuals
                                function.f(P_AR, Y, RES, W);

                                //Evaluate objective function
                                P_V.items[i][0] = (((RES.trans()).mult(W)).mult(RES)).items[0][0];

                        }

                        catch (Exception error)
                        {
                                P_V.items[i][0] = 0;
                        }
                }

        }


        public static void mutationStrategyDERand1(final Matrix  P_A, final int i, final int population_size, final Matrix  F, Matrix  U)
        {
                //Create next generation picking 3 random random vectors from the current population
                 int i1 = 0, i2 = 0, i3 = 0, m = F.rows();

                //Get three random indices different from i
                do {i1 = (int)(random() * population_size);} while ( i1 == i );
                do {i2 = (int)(random() * population_size);} while ( ( i2 == i1 ) || ( i2 == i ) );
                do {i3 = (int)(random() * population_size);} while ( ( i3 == i2 ) || ( i3 == i1 ) || ( i3 == i ) );

                //Get vectors corresponding to indices
                Matrix  R1 = P_A.row ( i1 ), R2 = P_A.row ( i2 ), R3 = P_A.row ( i3 );

                //Create new vector u from R1, R2, R3
                if (m == 1)
                        U.copy(R1.plus((R2.minus(R3)).mult(F.items[0][0])));

                //Jitter version: dot product
                else
                        U.copy(R1.plus(F.had(R2.minus(R3))));
        }


        public static void mutationStrategyDERand2(final Matrix P_A, final int i, final int population_size, final Matrix F, Matrix U)
        {
                //Create next generation picking 5 random random vectors from the current population
                 int i1 = 0, i2 = 0, i3 = 0, i4 = 0, i5 = 0, m = F.rows();

                //Get five random indices different from i
                do {i1 = (int)(random() * population_size);} while (i1 == i);
                do {i2 = (int)(random() * population_size);} while ((i2 == i1) || (i2 == i));
                do {i3 = (int)(random() * population_size);} while ((i3 == i2) || (i3 == i1) || (i3 == i));
                do {i4 = (int)(random() * population_size);} while ((i4 == i3) || (i4 == i2) || (i4 == i1) || (i4 == i));
                do {i5 = (int)(random() * population_size);} while ((i5 == i4) || (i5 == i3) || (i5 == i2) || (i5 == i1) || (i5 == i));

                //Get vectors corresponding to indices
                Matrix  R1 = P_A.row(i1), R2 = P_A.row(i2), R3 = P_A.row(i3), R4 = P_A.row(i4), R5 = P_A.row(i5);

                //Create new vector u from R1, R2, R3
                if (m == 1)
                        U.copy(R1.plus((((R2.plus(R4)).minus(R3)).minus(R5)).mult(F.items[0][0]))); 

                //Jitter version: dot product
                else
                        U.copy(R1.plus(F.had(((R2.plus(R4)).minus(R3)).minus(R5)))); 
        }


        public static void mutationStrategyDERandDir1(final Matrix P_A, final Matrix P_V, final int i, final int population_size, final Matrix F, Matrix U)
        {
                //Create next generation picking 2 random random vectors from the current population + aproximate gradient
                int i1 = 0, i2 = 0;
                final  int m = P_A.rows(), n = P_A.cols(), nf = F.cols();

                //Get two random indices different from i
                do {i1 = (int)(random() * population_size);} while (i1 == i);
                do {i2 = (int)(random() * population_size);} while ((i2 == i1) || (i2 == i));

                //Get vectors corresponding to indices  and objective function values
                Matrix  R1 = P_A.row(i1), R2 = P_A.row(i2);
                double function_val_y1 = P_V.items[i1][0], function_val_y2 = P_V.items[i2][0];

                //Create new vector u from R1, R2, approximation of the gradient
                if (function_val_y1 <= function_val_y2)
                {
                        if (nf == 1)
                                U.copy(R1.plus((R1.minus(R2)).mult(F.items[0][0])));

                        //Jitter version: dot product
                        else
                                U.copy(R1.plus(F.had(R1.minus(R2))));
                }

                else
                {
                        if ( nf == 1)
                                U.copy(R2.plus((R2.minus(R1)).mult(F.items[0][0])));

                        //Jitter version: dot product
                        else
                                U.copy(R2.plus(F.had(R2.minus(R1))));
                }
        }


        public static void mutationStrategyDERandDir2(final Matrix P_A, final Matrix P_V, final int i, final int population_size, final Matrix F, Matrix U)
        {
                //Create next generation picking 4 random sorted vectors from the current population + approximate gradient
                int i1 = 0, i2 = 0, i3 = 0, i4 = 0;
                final  int m = P_A.rows(), n = P_A.cols(), nf = F.cols();

                //Get four random indices different from i
                do {i1 = (int)(random() * population_size);} while (i1 == i);
                do {i2 = (int)(random() * population_size);} while ((i2 == i1) || (i2 == i));
                do {i3 = (int)(random() * population_size);} while ((i3 == i2) || (i3 == i1) || (i3 == i));
                do {i4 = (int)(random() * population_size);} while ((i4 == i3) || (i4 == i2) || (i4 == i1) || (i4 == i));

                //Get individuals corresponding to indices and their values
                Matrix  R1 = P_A.row(i1), R2 = P_A.row(i2), R3 = P_A.row(i3), R4 = P_A.row(i4);
                double function_val_y1 = P_V.items[i1][0], function_val_y2 = P_V.items[i2][0], function_val_y3 = P_V.items[i3][0], function_val_y4 = P_V.items[i4][0];

                //Compute mutation
                Matrix  v1 = R1, v2 = R2, v3 = R3, v4 = R4;
                if (function_val_y1 > function_val_y2)
                {
                        v1 = R2;
                        v2 = R1;
                }

                if (function_val_y3 > function_val_y4)
                {
                        v3 = R4;
                        v4 = R3;
                }

                //Create new vector u from v1, v2, v3, v4
                if ( nf == 1)
                        U.copy(v1.plus((((v1.minus(v2)).plus(v3)).minus(v4)).mult(F.items[0][0])));

                //Jitter version: dot product
                else
                        U.copy(v1.plus(F.had((v1.minus(v2)).plus(v3)).minus(v4)));
        }

        
        public static void mutationStrategyDERandBest1(final Matrix P_A, final Matrix P_V, final int i, final int population_size, final Matrix F, Matrix U)
        {
                //Create next generation picking the minimum and 4 random random vectors from the current population
                 int i1 = 0, i2 = 0;

                //Find best argument
                int row_index_min = 0, column_index_min = 0, nf = F.cols();
                int [] position = {0,0};
                final double min_val = P_V.min(position);
                row_index_min = position[0];
                column_index_min = position[1];

                //Get two random indices different from i
                do {i1 = (int)(random() * population_size);} while ((i1 == i) || (i1 == row_index_min));
                do {i2 = (int)(random() * population_size);} while ((i2 == i1) || (i2 == i) || (i2 == row_index_min));

                //Get vectors corresponding to indices
                Matrix  R1 = P_A.row(i1), R2 = P_A.row(i2);

                //Create new vector u from R1, R2
                if ( nf == 1)
                        U.copy((P_A.row(row_index_min)).plus((R1.minus(R2)).mult(F.items[0][0])));

                //Jitter version: dot product
                else
                {
                        U.copy((P_A.row(row_index_min)).plus(F.had(R1.minus(R2))));
                }
        }

        
        public static void mutationStrategyDERandBest2(final Matrix P_A, final Matrix P_V, final int i, final int population_size, final Matrix F, Matrix U)
        {
                //Create next generation picking the minimum and 4 random random vectors from the current population
                 int i1 = 0, i2 = 0, i3 = 0, i4 = 0;

                //Find best argument
                int [] position = {0, 0};
                int row_index_min = 0, column_index_min = 0, nf = F.cols();
                final double min_val = P_V.min(position);
                row_index_min = position[0];
                column_index_min = position[1];

                //Get four random indices different from i
                do { i1 = (int)(random() * population_size );} while ( ( i1 == i ) || ( i1 == row_index_min ) );
                do { i2 = (int)(random() * population_size );} while ( ( i2 == i1 ) || ( i2 == i ) || ( i2 == row_index_min ) );
                do { i3 = (int)(random() * population_size );} while ( ( i3 == i2 ) || ( i3 == i1 ) || ( i3 == i ) || ( i3 == row_index_min ) );
                do { i4 = (int)(random() * population_size );} while ( ( i4 == i3 ) || ( i4 == i2 ) || ( i4 == i1 ) || ( i4 == i ) || ( i4 == row_index_min ) );

                //Get vectors corresponding to indices
                Matrix  R1 = P_A.row ( i1 ), R2 = P_A.row ( i2 ), R3 = P_A.row ( i3 ), R4 = P_A.row ( i4 );

                //Create new vector u from R1, R2, R3, R4, Rbest
                if ( nf == 1)
                        U.copy((P_A.row ( row_index_min )).plus(((R1.minus(R2)).plus(R3).minus(R4)).mult(F.items[0][0])));

                //Jitter version: dot product
                else
                        U.copy((P_A.row ( row_index_min )).plus(F.had(((R1.minus(R2)).plus(R3)).minus(R4))));
        }


        public static void mutationStrategyDERandBestDir1(final Matrix P_A, final Matrix P_V, final  int i, final int population_size, final Matrix F, Matrix U)
        {
                //Create next generation picking the minimum and 2 random random, best and current vectors from the population
                 int i1 = 0, i2 = 0;

                //Find best argument
                int [] position = {0, 0};
                int row_index_min = 0, column_index_min = 0, nf = F.cols();
                final double min_val = P_V.min(position);
                row_index_min = position[0];
                column_index_min = position[1];

                //Get two random indices different from i
                do {i1 = (int)(random() * population_size);} while ((i1 == i) || (i1 == row_index_min));
                do {i2 = (int)(random() * population_size);} while ((i2 == i1) || (i2 == i) || (i2 == row_index_min));

                //Get vectors corresponding to indices
                Matrix  R1 = P_A.row(i1), R2 = P_A.row(i2), Ri = P_A.row(i);

                //Create new vector u from R1, R2, R3
                if ( nf == 1)
                        U.copy((P_A.row(row_index_min)).plus((((P_A.row(row_index_min).plus(R1)).minus(Ri)).minus(R2)).mult(F.items[0][0])));

                //Jitter version: dot product
                else
                        U.copy((P_A.row(row_index_min)).plus(F .had (((P_A.row(row_index_min).plus(R1)).minus(Ri)).minus(R2))));
        }


        public static void mutationStrategyDETargetToBest1(final Matrix P_A, final Matrix P_V, final int i, final int population_size, final Matrix  F, Matrix  U)
        {
                //Create next generation picking the minimum and 2 random random, best and current vectors from the population
                 int i1 = 0, i2 = 0;

                //Find best argument
                int [] position = {0, 0};
                int row_index_min = 0, column_index_min = 0, nf = F.cols();
                final double min_val = P_V.min(position);
                row_index_min = position[0];
                column_index_min = position[1];

                //Get two random indices different from i
                do {i1 = (int)(random()* population_size);} while ((i1 == i) || (i1 == row_index_min));
                do {i2 = (int)(random()* population_size);} while ((i2 == i1) || (i2 == i) || (i2 == row_index_min));

                //Get vectors corresponding to indices
                Matrix  R1 = P_A.row(i1), R2 = P_A.row(i2), Ri = P_A.row(i);

                //Create new vector u from R1, R2, R3
                if (nf == 1)
                        U.copy(Ri.plus((((P_A.row(row_index_min)).minus(Ri)).mult(F.items[0][0])).plus(R1.minus(R2).mult(F.items[0][0]))));

                //Jitter version: dot product
                else
                        U.copy(Ri.plus((F .had((P_A.row(row_index_min)).minus(Ri))).plus(F.had(R1.minus(R2)))));
        }


        public static void mutationStrategySACP(final Matrix P_A, final Matrix P_V, final int i, final int  population_size, final double aver_res, Matrix  F, double [] CR, Matrix  U)
        {
                //Adaptive strategy for SACP method, modifying the mutation and cross-over factors F, CR
                int i1 = 0, i2 = 0, i3 = 0;
                final  int m = P_A.rows(), n = P_A.cols();

                //Get three random indices different from i
                do {i1 = (int)(random() * population_size);} while (i1 == i);
                do {i2 = (int)(random() * population_size);} while ((i2 == i1) || (i2 == i));
                do {i3 = (int)(random() * population_size);} while ((i3 == i2) || (i3 == i1) || (i3 == i));

                //Create matrix of attributes corresponding to indices and their values
                Matrix A = new Matrix(3, n);
                Matrix V = new Matrix(3, 1);
                A.replace(P_A.row(i1), 0, 0);
                A.replace(P_A.row(i2), 1, 0); 
                A.replace(P_A.row(i3), 2, 0);
                
                V.items[0][0] = P_V.items[i1][0];
                V.items[1][0] = P_V.items[i2][0];
                V.items[2][0] = P_V.items[i3][0];

                //Sort residuals in the ascendent order, get the permutation matrix IX
                Matrix IX = new Matrix(3, 1);
                V.sortrows(IX, 0);

                //Sorted attributes: apply permutation matrix IX
                Matrix  AS = new Matrix(3, n);
                AS.replace(A.row((int)IX.items[0][0]), 0, 0);
                AS.replace(A.row((int)IX.items[1][0]), 1, 0);
                AS.replace(A.row((int)IX.items[2][0]), 2, 0);

                //Compute new mutation factor
                F.items[0][0] = 0.1 + 0.8 * (V.items[1][0] - V.items[0][0]) / (V.items[2][0] - V.items[0][0]);

                //Compute new cross-over ratio
                double function_val_yi = P_V.items[i][0];
                if (function_val_yi >= aver_res)
                {
                        CR[0] = 0.1 + 0.5 * ( function_val_yi - V.items[0][0]) / (V.items[2][0] - V.items[0][0]);
                }

                else CR[0] = 0.1;

                //Apply mutation factor
                U.copy((AS.row(0)).plus((AS.row(1).minus(AS.row(2))).mult(F.items[0][0])));
        }


        public static void crossover ( final Matrix U, final double CR, final int dim, Matrix V )
        {
                //Compute cross-over: rewrite U elements
                 short total_swap = 0;

                for (  int j = 0; j < dim; j++ )
                {
                        //Generate random number and compare to C
                        final double r_val = random();

                        //Rewrite elements
                        if ( r_val < CR )
                        {
                                V.items [0][j] = U.items[0][j];
                                total_swap++;
                        }
                }

                //No cross over has been performed: rewrite random element
                if ( total_swap == 0 )
                {
                        final int index = (int)(random() * dim);
                        V.items [0][index] = U.items[0][index];
                }
        }


        public static void reflection(Matrix X, final Matrix XMIN, final Matrix XMAX)
        {
                //Reflect elements of vectors into the search space represented by the n-dimensional cuboid

                //Transpose matrix
                Matrix XT = X.trans();
	
                //Call reflection
                NonLinearLeastSquares.reflection(XT, XMIN.trans(), XMAX.trans());

                //Assign transposed matrix
                X.copy(XT.trans());    
        }
}
