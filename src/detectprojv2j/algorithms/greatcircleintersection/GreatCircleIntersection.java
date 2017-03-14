// Description: Compute intersection of the great circle and the plane

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


package detectprojv2j.algorithms.greatcircleintersection;

import static java.lang.Math.*;

import detectprojv2j.types.TTransformedLongitudeDirection;

import detectprojv2j.structures.point.Point3DGeographic;

import static detectprojv2j.consts.Consts.RO;

import detectprojv2j.algorithms.carttransformation.CartTransformation;
import detectprojv2j.algorithms.planeintersection.PlaneIntersection;
import detectprojv2j.algorithms.sphereintersection.SphereIntersection;


public class GreatCircleIntersection {
        
        public static boolean getGreatCirclePlainIntersection(final Point3DGeographic p1, final Point3DGeographic p2, final Point3DGeographic p3, final Point3DGeographic p4,
	final Point3DGeographic p5, final Point3DGeographic p6, Point3DGeographic []i1, Point3DGeographic []i2, final Point3DGeographic pole, final TTransformedLongitudeDirection lon_direction)
        {
                //Compute intersection of the great circle given by the points p1, p2, p3 with a plane (meridian/parallel) given by points p4, p5, p
                final double lat1 = p1.getLat();
                final double lon1 = p1.getLon();
                final double lat2 = p2.getLat();
                final double lon2 = p2.getLon();
                final double lat3 = p3.getLat();
                final double lon3 = p3.getLon();
                final double lat4 = p4.getLat();
                final double lon4 = p4.getLon();
                final double lat5 = p5.getLat();
                final double lon5 = p5.getLon();
                final double lat6 = p6.getLat();
                final double lon6 = p6.getLon();

                //P1: Convert spherical coordinates to the Cartesian
                final double x1 = cos(lat1 / RO) * cos(lon1 / RO);
                final double y1 = cos(lat1 / RO) * sin(lon1 / RO);
                final double z1 = sin(lat1 / RO);

                //P2: Convert spherical coordinates to the Cartesian
                final double x2 = cos(lat2 / RO) * cos(lon2 / RO);
                final double y2 = cos(lat2 / RO) * sin(lon2 / RO);
                final double z2 = sin(lat2 / RO);

                //P3: Convert spherical coordinates to the Cartesian
                final double x3 = cos(lat3 / RO) * cos(lon3 / RO);
                final double y3 = cos(lat3 / RO) * sin(lon3 / RO);
                final double z3 = sin(lat3 / RO);

                //P4: Convert spherical coordinates to the Cartesian
                final double x4 = cos(lat4 / RO) * cos(lon4 / RO);
                final double y4 = cos(lat4 / RO) * sin(lon4 / RO);
                final double z4 = sin(lat4 / RO);

                //P5: Convert spherical coordinates to the Cartesian
                final double x5 = cos(lat5 / RO) * cos(lon5 / RO);
                final double y5 = cos(lat5 / RO) * sin(lon5 / RO);
                final double z5 = sin(lat5 / RO);

                //P6: Convert spherical coordinates to the Cartesian
                final double x6 = cos(lat6 / RO) * cos(lon6 / RO);
                final double y6 = cos(lat6 / RO) * sin(lon6 / RO);
                final double z6 = sin(lat6 / RO);

                //Create initial point p0
                final double x0 = (x1 + x2 + x3) / 3.0;
                final double y0 = (y1 + y2 + y3) / 3.0;
                final double z0 = (z1 + z2 + z3) / 3.0;

                //Get parametric equation of the planes (p1, p2, p3) x (p4, p5, p6) intersection
                double [] xi = {0}, yi = {0}, zi = {0}, ux = {0}, uy = {0}, uz = {0};
                final boolean intersection1_exists = PlaneIntersection.get2PlanesIntersection(x1, y1, z1, x2, y2, z2, x3, y3, z3, x4, y4, z4, x5, y5, z5, x6, y6, z6, x0, y0, z0, xi, yi, zi, ux, uy, uz);

                //Get coordinates of the intersection (sphere x plane intersection) in the horizontal / vertical plane       
                if (intersection1_exists)
                {
                        //Compute intersection of the sphere and both planes intersection
                        final double xc = 0.0, yc = 0.0, zc = 0.0, r = 1.0;
                        double [] xi1 = {0}, yi1 = {0}, zi1 = {0}, xi2 = {0}, yi2 = {0}, zi2 = {0};

                        final boolean intersection2_exists = SphereIntersection.getSphereAndLineIntersection(xc, yc, zc, r, xi[0], yi[0], zi[0], ux[0], uy[0], uz[0], xi1, yi1, zi1, xi2, yi2, zi2);
                        
                        if (intersection2_exists)
                        {
                                //Convert Cartesian coordinates to the spherical
                                final double ri1 = sqrt(xi1[0] * xi1[0] + yi1[0] * yi1[0] + zi1[0] * zi1[0]);
                                final double ri2 = sqrt(xi2[0] * xi2[0] + yi2[0] * yi2[0] + zi2[0] * zi2[0]);

                                //Compute latitude of intersections
                                final double lati1 = asin(zi1[0] / ri1) * RO;
                                final double lati2 = asin(zi2[0] / ri2) * RO;

                                //Compute longitude of intersections
                                final double loni1 = atan2(yi1[0], xi1[0]) * RO;
                                final double loni2 = atan2(yi2[0], xi2[0]) * RO;

                                //Set parameters to points
                                i1[0].setLat(lati1);
                                i1[0].setLon(loni1);
                                i2[0].setLat(lati2);
                                i2[0].setLon(loni2);

                                return true;
                        }
                }

                return false;
        }

        
}
