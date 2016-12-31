// Description: Distance between 2 points on the sphere

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

package detectprojv2j.algorithms.sphericaldistance;

import static java.lang.Math.*;

import detectprojv2j.structures.point.Point3DGeographic;

import static detectprojv2j.consts.Consts.*;

import detectprojv2j.exceptions.MathInvalidArgumentException;



public class SphericalDistance {
        
        public static double distance (final Point3DGeographic p1, final Point3DGeographic p2, final double R)
        {
                final double lat1 = p1.getLat();
                final double lon1 = p1.getLon();
                final double lat2 = p2.getLat();
                final double lon2 = p2.getLon();
                
                //Compute distances on the sphere
                double arg = sin (lat1/RO) * sin (lat2/RO) + cos (lat1/RO) * cos (lat2/RO) * cos ((lon2 - lon1)/RO);
                
                //Throw exception
                if (abs(arg) > 1 + ANGLE_ROUND_ERROR)
                         throw new MathInvalidArgumentException ("MathInvalidArgumentException: ", "can not compute spherical distance, acos(arg), arg = ", arg);

                else if (abs(arg) > 1)
                        arg = signum(arg);
                
                return acos(arg) * R;
        }
        
}
