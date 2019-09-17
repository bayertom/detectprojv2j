// Description: Solve quartic equation ax^4 + bx^3 + cx^2 + dx + e = 0

// Support lon0 shift for the oblique aspect

// Copyright (c) 2015 - 2018
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


package detectprojv2j.algorithms.quartic;

import static java.lang.Math.*;
import java.util.List;

import static detectprojv2j.consts.Consts.*;

import detectprojv2j.exceptions.MathInvalidArgumentException;


public class Quartic {
  
        
        public static void solveR(final double a, final double b, final double c, final double d, final double e, List <Double> roots)
        {
                //Get real roots of the quartic equation ax^4 + bx^3 + cx^2 + dx + e = 0
                //A universal method of solving quartic equations, International Journal of Pure and Applied Mathematics
                //Volume 71 No. 2 2011, 251 - 259, S. Shmakov, 2011
                //Slightly modified algorithm with the Cardano's formula instead of ys
                final double A = b / a;
                final double B = c / a;
                final double C = d / a;
                final double D = e / a;

                //Convert to the cubic polynomial y^3 + py + q = 0
                final double p = A * C - B * B / 3.0 - 4.0 * D;
                final double q = A * B * C / 3 - A * A * D - 2.0 * B * B * B / 27.0 - C * C + 8.0 * B * D / 3.0;

                //Discriminate of the Cardano's formula
                final double q2 = 0.5 * q;
                final double p3 = p / 3.0;
                final double delta = q2 * q2 + p3 * p3 * p3;

                //Compute y from the Cardano's formula
                //Non-negative discriminate
                double y;
                if (delta >= 0)
                {
                        y = cbrt(-q / 2.0 + sqrt(delta)) + cbrt(-q / 2.0 - sqrt(delta));
                }

                //Negative discriminate
                else
                {
                        final double r = sqrt(-p3 * p3 * p3);
                        final double theta = acos(-q / (2 * r)) / 3.0;
                        y = 2.0 * cbrt(r) * cos(theta);
                }

                //Find roots of the quadratic: g^2 - ag + 2/3 b - y = 0
                double discr1 = A * A - 8.0 * B / 3.0 + 4.0 * y;

                //Correct the numerical inaccuracies
                if ((discr1 < 0) && (discr1 > -ARGUMENT_ROUND_ERROR))
                        discr1 = 0;

                //No real solution exists
                if (discr1 < 0)
                {
                        return;
                }

                //Find g12
                final double g1 = (A + sqrt(discr1)) / 2.0;
                final double g2 = (A - sqrt(discr1)) / 2.0;

                //Find roots of the quadratic: h^2 - (y + b/3)h + d = 0
                final double yB = y + B / 3.0;
                double discr2 = yB * yB - 4.0 * D;

                //Correct the numerical inaccuracies
                if ((discr2 < 0) && (discr2 > -ARGUMENT_ROUND_ERROR))
                        discr2 = 0;

                //No real solution exists
                if (discr2 < 0)
                {
                        return;
                }

                //Find h12
                final double h1 = (y + B / 3.0 + sqrt(discr2)) / 2.0;
                final double h2 = (y + B / 3.0 - sqrt(discr2)) / 2.0;

                //Compute x12
                double discr3 = g1 * g1 - 4.0 * h2;
                double discr4 = g2 * g2 - 4.0 * h1;

                //Correct the numerical inaccuracies
                if ((discr3 < 0) && (discr3 > -ARGUMENT_ROUND_ERROR))
                        discr3 = 0;
                if ((discr4 < 0) && (discr4 > -ARGUMENT_ROUND_ERROR))
                        discr4 = 0;

                //Use only the real roots
                if (discr3 >= 0)
                {
                        final double x1 = (-g1 + sqrt(discr3)) / 2.0;
                        final double x2 = (-g1 - sqrt(discr3)) / 2.0;

                        roots.add(x1);
                        roots.add(x2);
                }

                //Use only the real roots
                if (discr4 >= 0)
                {
                        final double x3 = (-g2 + sqrt(discr4)) / 2.0;
                        final double x4 = (-g2 - sqrt(discr4)) / 2.0;

                        roots.add(x3);
                        roots.add(x4);
                }
        }
}
