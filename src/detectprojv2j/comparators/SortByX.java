// Description: Sort Cartesian point by x coordinate

// Copyright (c) 2010 - 2016
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

public class SortByX implements Comparator <Point3DCartesian> {
    
        @Override
        public int compare(final Point3DCartesian p1, final Point3DCartesian p2)
        {
                if (p1.getX() < p2.getX()) return -1;
                if (p1.getX() > p2.getX()) return 1;
                return 0;
        }
}