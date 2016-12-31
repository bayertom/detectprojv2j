// Description: Plane intersections

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
// but WITHOUdouble ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
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
                double n2_norm = sqrt(n2x * n2x + n2y * n2y + n2z * n2z);
                
                //Normalize n2
                n2x /= n2_norm;
                n2y /= n2_norm;
                n2z /= n2_norm;

                //Create matrix M
                Matrix M = new Matrix(5, 5);
                M.items[0][0] = 2.0;
                M.items[0][3] = n1x;
                M.items[0][4] = n2x;

                M.items[1][1] = 2.0;
                M.items[1][3] = n1y;
                M.items[1][4] = n2y;

                M.items[2][2] = 2.0;
                M.items[2][3] = n1z;
                M.items[2][4] = n2z;

                M.items[3][0] = n1x;
                M.items[3][1] = n1y;
                M.items[3][2] = n1z;

                M.items[4][0] = n2x;
                M.items[4][1] = n2y;
                M.items[4][2] = n2z;

                //Test, if planes are colinear
                final double detM = M.det();

                //No intersection found
                if (detM < EPS)
                        return false;

                //Create matrix B
                Matrix b = new Matrix(5, 1);
                b.items[0][0] = 2 * x0;
                b.items[1][0] = 2 * y0;
                b.items[2][0] = 2 * z0;
                b.items[3][0] = x1 * n1x + y1 * n1y + z1 * n1z;
                b.items[4][0] = x4 * n2x + y4 * n2y + z4 * n2z;

                //Find solution Mx = b;
                Matrix x = (M.inv()).mult(b);

                //Cross product t = n1 x n2: vector of the intersection
                ux[0] = n1y * n2z - n2y * n1z;
                uy[0] = n1z * n2x - n2z * n1x;
                uz[0] = n1x * n2y - n2x * n1y;

                //Start point of the intersection
                xi[0] = x.items[0][0];
                yi[0] = x.items[1][0];
                zi[0] = x.items[2][0];
                
                return true;
        }
}
