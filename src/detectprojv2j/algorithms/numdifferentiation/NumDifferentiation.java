// Description: Numeric differentiation using Stirling method, the function is defined using the functor
// Copyright (c) 2015 - 2017
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

package detectprojv2j.algorithms.numdifferentiation;

import detectprojv2j.types.INumDerivative;
import detectprojv2j.types.TDerivativeType;
import detectprojv2j.types.TDerivativeVariable;

import detectprojv2j.structures.matrix.Matrix;

import detectprojv2j.exceptions.BadDataException;

       
public class NumDifferentiation {
        
        public static double getDerivative(INumDerivative function, final Matrix  args, final TDerivativeType deriv_type, final TDerivativeVariable deriv_var, final double deriv_step, final boolean print_exceptions)
        {
                //Values and function values
                double [] values = new double [7];
                double [] fvalues = new double [7];

                //Compute function values
                computeFunctionValues ( function, args, deriv_var, deriv_step, values, fvalues, print_exceptions );

                //Compute differences and Stirling formula
                return computeStirlingFormula ( values, fvalues, deriv_type, deriv_step );
        }


        public static void computeFunctionValues(INumDerivative function, final Matrix  args, final TDerivativeVariable deriv_var, final double deriv_step, double [] values, double [] fvalues, final boolean print_exceptions)
        {
                //Compute values and function values of the function having one variable z = f(lat, lon)
                if ( ( int ) (deriv_var.ordinal()) > args.cols() -  1 )
                        throw new BadDataException ( "BadDataException: not enough arguments (deriv_index > arg.size() - 1).", "Can not compute the partial derivative..." );

                for ( short i = 0; i < 7; i++ )
                {
                        //Compute value from argument
                        values[i] = (double) ( args.items [0][deriv_var.ordinal()] + ( i - 2 ) * deriv_step );

                        //Create temporary argument, replace arg[deriv_index] with the value
                        Matrix args_temp = new Matrix(args);
                        args_temp.items[0][deriv_var.ordinal()] = values[i];

                        //Compute the function value
                        fvalues[i] =  function.f ( args_temp );
                }
        }


        public static double computeStirlingFormula(double [] values, double [] fvalues, final TDerivativeType deriv_type, final double deriv_step)
        {
                // First differences
                double [] dif1 = new double[6];

                for ( short i = 0; i < 6; i++ )
                {
                        dif1[i] = fvalues[i + 1] - fvalues[i];
                }

                // Second differences
                double [] dif2 = new double[5];

                for ( short i = 0; i < 5; i++ )
                {
                        dif2[i] = dif1[i + 1] - dif1[i];
                }

                // Third differences
                double [] dif3 = new double[4];

                for ( short i = 0; i < 4; i++ )
                {
                        dif3[i] = dif2[i + 1] - dif2[i];
                }

                // Fourth differences
                double [] dif4 = new double[3];

                for (short i = 0; i < 3; i++)
                {
                        dif4[i] = dif3[i + 1] - dif3[i];
                }

                // Fifth differences
                double [] dif5 = new double[2];

                for (short i = 0; i < 2; i++)
                {
                        dif5[i] = dif4[i + 1] - dif4[i];
                }

                //Stirling formula for numeric derivative
                //First derivative
                if (deriv_type == TDerivativeType.FirstDerivative)
                        return ( (dif1[2] + dif1[3]) / 2 - (dif3[1] + dif3[2]) / 12 +  (dif5[0] + dif5[1]) / 60 ) / deriv_step;
                
                //Second derivative
                else
                        return (dif2[2]  - dif4[1] / 12 + (dif5[1] - dif5[0]) / 90) / ( deriv_step * deriv_step );
        }
}

