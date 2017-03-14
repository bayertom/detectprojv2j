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


package detectprojv2j.algorithms.sphereintersection;

import static java.lang.Math.*;

import static detectprojv2j.consts.Consts.EPS;

public class SphereIntersection 
{

        public static boolean getSphereAndLineIntersection(final double xc, final double yc, final double zc, final double r, final double xa, final double ya, final double za,
                final double ux, final double uy, final double uz, double [] xi1, double [] yi1, double [] zi1, double [] xi2, double [] yi2, double [] zi2)
        {
                //Compute intersection of the line and sphere
                //Sphere S(C, r) given by the center and radius, line L(x, y, z) = (xa, ya, ya) + t(ux, uy, uz)
                final double A = ux * ux + uy * uy + uz * uz;
                final double B = 2.0 * (xa * ux + ya * uy + za * uz - ux * xc - uy * yc - uz * zc);
                final double C = xa * xa - 2.0 * xa * xc + xc * xc + ya * ya - 2 * ya * yc + yc * yc + za * za - 2.0 * za * zc + zc * zc - r * r;

                //Compute discriminant
                final double D = B * B - 4 * A * C;

                //Intersection of the line and sphere does not exist
                if (D < 0)
                        return false;

                //Find first intersection
                double t1 = (-B - sqrt(D)) / (2.0 * A);

                //Compute first intersection point
                xi1[0] = xa + t1 * ux;
                yi1[0] = ya + t1 * uy;
                zi1[0] = za + t1 * uz;

                //Line is a tangent, only 1 intersecion
                if (abs(D) < EPS)
                {
                        xi2 = xi1;
                        yi2 = yi1;
                        zi2 = zi1;

                        return true;
                }

                //Find second intersection
                double t2 = (-B + sqrt(D)) / (2.0 * A);

                //Compute second intersection point
                xi2[0] = xa + t2 * ux;
                yi2[0] = ya + t2 * uy;
                zi2[0] = za + t2 * uz;

                //Both intersections have a different direction according to the line: switch points
                if (abs(t1 - 0.5) >= abs(t2 - 0.5))
                {
                        //Create temporary variables
                        final double x_temp = xi1[0];
                        final double y_temp = yi1[0];
                        final double z_temp = zi1[0];

                        //Switch p1 <-> p2
                        xi1[0] = xi2[0];
                        yi1[0] = yi2[0];
                        zi1[0] = zi2[0];

                        xi2[0] = x_temp;
                        yi2[0] = y_temp;
                        zi2[0] = z_temp;
                }

                return true;
        }
        
}
