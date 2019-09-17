// Description: Sort geographic points by the spherical distance from the point P

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

package detectprojv2j.comparators;

import static java.lang.Math.*;
import java.util.Comparator;

import detectprojv2j.structures.point.Point3DGeographic;

import static detectprojv2j.consts.Consts.*;

import detectprojv2j.algorithms.sphericaldistance.SphericalDistance;

public class SortPointsByDistGeo implements Comparator <Point3DGeographic> {
        
        private final Point3DGeographic p;
        private final double R;
        
        public SortPointsByDistGeo (final Point3DGeographic p_, final double R_)
        {
                p = p_;
                R = R_;
        }
        
        @Override
        public int compare(final Point3DGeographic p1, final Point3DGeographic p2)
        {
                final double lat = p.getLat();
                final double lon = p.getLon();
                final double lat1 = p1.getLat();
                final double lon1 = p1.getLon();
                final double lat2 = p2.getLat();
                final double lon2 = p2.getLon();
                
                //Compute distances on the sphere
                final double arg1 = sin (lat/RO) * sin (lat1/RO) + cos (lat/RO) * cos (lat1/RO) * cos ((lon1 - lon)/RO);
                final double arg2 = sin (lat/RO) * sin (lat2/RO) + cos (lat/RO) * cos (lat2/RO) * cos ((lon2 - lon)/RO);
                final double d1 = SphericalDistance.distance(p, p1, R);
                final double d2 = SphericalDistance.distance(p, p2, R);
            
                //Compare distances
                if (d1 < d2) 
                        return -1;
                
                if (d1 > d2) 
                        return 1;
                
                return 0;
        }
        
}
