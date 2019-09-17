// Description: Compute angle given by 3 points

// Copyright (c) 2017 - 2018
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

package detectprojv2j.algorithms.angle3points;

import static java.lang.Math.*;

import static detectprojv2j.consts.Consts.*;

import detectprojv2j.structures.point.Point3DCartesian;

import detectprojv2j.exceptions.BadDataException;


//Compute angle given by 3 points
public class Angle3Points {
        
        public static double getAngle3Points ( final Point3DCartesian p1, final Point3DCartesian p2, final Point3DCartesian p3 )
        {
                //Compute angle between ordered set of three point p_left, p_mid, p_right, angle in interval <0, 360>
                return getAngle3Points(p1.getX(), p1.getY(), p2.getX(), p2.getY(), p3.getX(), p3.getY());
        }
        
        public static double getAngle3Points(final double x1, final double y1, final double x2, final double y2, final double x3, final double y3)
        {
                //Compute angle between ordered set of three point p_left, p_mid, p_right, angle in interval <0, 360>
                final double x21 = x2 - x1;
                final double y21 = y2 - y1;
                final double x13 = x1 - x3;
                final double y13 = y1 - y3;
                final double x23 = x2 - x3;
                final double y23 = y2 - y3;

                if (((abs(x21) < MIN_FLOAT) && (abs(y21) < MIN_FLOAT)) || ((abs(x13) < MIN_FLOAT) && (abs(y13) < MIN_FLOAT)) ||
                    ((abs(x23) < MIN_FLOAT) && (abs(y23) < MIN_FLOAT)))
                {
                        throw new BadDataException("BadDataException: can not compute angle between 3 points, ", " at least two points are indentical.");
                }

                //Angle
                final double angle = (atan2(y23, x23) - atan2(y21, x21)) * 180 / PI;

                return angle < 0 ? angle + 360 : angle;
        }        
}
