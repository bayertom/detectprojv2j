// Description: Plane intersections

// Copyright (c) 2015 - 2016
//  doubleomas Bayer
// Charles University in Prague, Faculty of Science
// bayertom@natur.cuni.cz

//  doublehis library is free software: you can redistribute it and/or modify
// it under the terms of the GNU Lesser General Public License as published
// by the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.
//
//  doublehis library is distributed in the hope that it will be useful,
// but WI doubleHOUdouble ANY WARRAN doubleY; without even the implied warranty of
// MERCHAN doubleABILI doubleY or FI doubleNESS FOR A PAR doubleICULAR PURPOSE. See the
// GNU Lesser General Public License for more details.
//
// You should have received a copy of the GNU Lesser General Public License
// along with this library. If not, see <http://www.gnu.org/licenses/>.


package detectprojv2j.algorithms.planeintersection;

import detectprojv2j.structures.matrix.Matrix;

import static detectprojv2j.consts.Consts.EPS;
import static java.lang.Math.sqrt;


public class PlaneIntersection {
        
        public static boolean get2PlanesIntersection(final double x1, final double y1, final double z1, final double x2, final double y2, final double z2, final double x3, final double y3, final double z3,
	final double x4, final double y4, final double z4, final double x5, final double y5, final double z5, final double x6, final double y6, final double z6, final double x0, final double y0, final double z0,
	double [] xi, double [] yi, double [] zi, double [] ux, double [] uy, double [] uz)
        {
                //Find intersection of two planes Rho(P1, P2, P3), Sigma(P4, P5, P6) given by points
                //P0 =[x0, y0, z0] is the arbitrary point
                //Start point of the intersection Pi=[xi, yi, zi] determined as closest to P0
                //Solution by John Krumm, 2016

                //Compute direction vector u1 = p2 - p1  of the first plane
                final double u1x = x2 - x1;
                final double u1y = y2 - y1;
                final double u1z = z2 - z1;

                //Compute direction vector v1 = p3 - p1 of the first plane
                final double v1x = x3 - x1;
                final double v1y = y3 - y1;
                final double v1z = z3 - z1;

                //Cross product n1 = u1 x v1 of the first plane
                double n1x = u1y * v1z - v1y * u1z;
                double n1y = u1z * v1x - v1z * u1x;
                double n1z = u1x * v1y - v1x * u1y;
                final double n1_norm = sqrt(n1x * n1x + n1y * n1y + n1z * n1z);
                
                //Normalize n1
                n1x /= n1_norm;
                n1y /= n1_norm;
                n1z /= n1_norm;

               //Compute direction vector u2 = p5 - p4  of the second plane
                final double u2x = x5 - x4;
                final double u2y = y5 - y4;
                final double u2z = z5 - z4;

                //Compute direction vector v2 = p6 - p4 of the second plane
                final double v2x = x6 - x4;
                final double v2y = y6 - y4;
                final double v2z = z6 - z4;

                //Cross product n1 = u1 x v1 of the second plane
                double n2x = u2y * v2z - v2y * u2z;
                double n2y = u2z * v2x - v2z * u2x;
                double n2z = u2x * v2y - v2x * u2y;
                final double n2_norm = sqrt(n2x * n2x + n2y * n2y + n2z * n2z);

                //Normalize n2
                n2x /= n2_norm;
                n2y /= n2_norm;
                n2z /= n2_norm;

                //Coefficients k0 - k24
                final double k0 = n1x * n1x;
                final double k1 = n1y * n1y;
                final double k2 = n1z * n1z;

                final double k3 = n2x * n2x;
                final double k4 = n2y * n2y;
                final double k5 = n2z * n2z;

                final double k6 = k0 + k1;
                final double k7 = k3 + k4;
                final double k8 = k3 + k5;
                final double k9 = k4 + k5;

                final double k10 = n1x * n2x;
                final double k11 = n1y * n2y;
                final double k12 = n1z * n2z;
                final double k13 = n1x * n2y;
                final double k14 = n1x * n2z;
                final double k15 = n1y * n2x;
                final double k16 = n1y * n2z;
                final double k17 = n1z * n2x;
                final double k18 = n1z * n2y;

                final double k19 = k15 * n2y;
                final double k20 = k17 * n2z;
                final double k21 = k15 * n1x;
                final double k22 = k13 * n1y;

                final double k23 = k0 + k1 + k2;
                final double k24 = k3 + k4 + k5;

                //Determinant of A
                final double detA = 2.0 * (k0 * k9 - 2.0 * k10 * k12 + k2 * k7 - 2.0 * k11 * (k10 + k12) + k1 * k8);

                //No intersection will be found
                if (detA < EPS)
                        return false;

                //Elements of the inverse matrix inv(A)
                final double a11 = (k18 - k16) * (k18 - k16) / detA;
                final double a12 = (k14 - k17) * (k18 - k16) / detA;
                final double a13 = (k13 - k15) * (k16 - k18) / detA;
                final double a14 = 2.0 * (-k19 - k20 + n1x * k9) / detA;
                final double a15 = 2.0 * (k15 * n1y - k22 + n1z * (k17 - k14)) / detA;
                final double a22 = (k17 - k14) * (k17 - k14) / detA;
                final double a23 = (k13 - k15) * (k17 - k14) / detA;
                final double a24 = 2.0 * (n1y * k8 - n2y * (k10 + k12)) / detA;
                final double a25 = 2.0 * (k13 * n1x - k21 + n1z * (k18 - k16)) / detA;
                final double a33 = (k15 - k13) * (k15 - k13) / detA;
                final double a34 = 2.0 * (n1z * k7 - (k10 + k11) * n2z) / detA;
                final double a35 = 2.0 * (k6 * n2z - n1z * (k10 + k11)) / detA;
                final double a44 = -4.0 * k24 / detA;
                final double a45 = 4.0 * (k10 + k11 + k12) / detA;
                final double a55 = -4.0 * k23 / detA;

                //Cross product u = n1 x n2: vector of the intersection
                ux[0] = n1y * n2z - n2y * n1z;
                uy[0] = n1z * n2x - n2z * n1x;
                uz[0] = n1x * n2y - n2x * n1y;

                //Matrix B
                final double b11 = 2.0 * x0;
                final double b21 = 2.0 * y0;
                final double b31 = 2.0 * z0;
                final double b41 = x1 * n1x + y1 * n1y + z1 * n1z;
                final double b51 = x4 * n2x + y4 * n2y + z4 * n2z;

                //Analytic solution x = inv(A) * B
                xi[0] = a11 * b11 + a12 * b21 + a13 * b31 + a14 * b41 + a15 * b51;
                yi[0] = a12 * b11 + a22 * b21 + a23 * b31 + a24 * b41 + a25 * b51;
                zi[0] = a13 * b11 + a23 * b21 + a33 * b31 + a34 * b41 + a35 * b51;

                return true;
        }
}
