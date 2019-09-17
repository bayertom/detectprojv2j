// Description: Sort cartesian points by dthe distance from the point P

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

import java.util.Comparator;

import detectprojv2j.structures.point.Point3DCartesian;

public class SortPointsByDistCart implements Comparator <Point3DCartesian>  {
    
        private final Point3DCartesian p;
        
        public SortPointsByDistCart (Point3DCartesian p_) {p = p_;}
        
        @Override
        public int compare(final Point3DCartesian p1, final Point3DCartesian p2)
        {
                final double x = p.getX();
                final double y = p.getY();
                final double x1 = p1.getX();
                final double y1 = p1.getY();
                final double x2 = p2.getX();
                final double y2 = p2.getY();
                
                //Compute distances
                final double d1_sqr = (x1 - x) * (x1 - x) + (y1 - y) * (y1 - y);
                final double d2_sqr = (x2 - x) * (x2 - x) + (y2 - y) * (y2 - y);
            
                //Comparedistances
                if (d1_sqr < d2_sqr) 
                        return -1;
                
                if (d1_sqr > d2_sqr) 
                        return 1;
                
                return 0;
        }
        
}
