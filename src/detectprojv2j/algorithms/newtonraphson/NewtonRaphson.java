// Description: Newton-Rapson method, find root of f(x) = 0

// Copyright (c) 2016 - 2017
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

package detectprojv2j.algorithms.newtonraphson;

import static java.lang.Math.abs;

import detectprojv2j.types.IFunctionX;

import static detectprojv2j.consts.Consts.MIN_FLOAT;


public class NewtonRaphson {
        
        public static double findRoot(IFunctionX function_x, IFunctionX function_x_der, final double x0, final int MAX_ITERATIONS, double MAX_DIFF )
        {
               //Solve y = f(x) by the Newton-Raphson method
                //Used in several pseudocylindrical projections
                int iterations = 0;
                double x = x0;

                //Apply Newton-Raphson method
                do {
                        //Compute F(x) and F'(x) 
                        final double fx = function_x.f(x);
                        final double fx_der = function_x_der.f(x);

                        //Newton-Raphson step
                        double xn = x;
                        if (abs(fx_der) > MIN_FLOAT) {
                                 xn = x - fx / fx_der;
                        }

                        //Test the terminal condition
                        if (abs(xn - x) < MAX_DIFF) {
                                break;
                        }

                        //Assignum new x
                        x = xn;

                } while (++iterations <= MAX_ITERATIONS);

                return x;
        }
}
