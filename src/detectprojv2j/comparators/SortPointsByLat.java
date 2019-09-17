// Description: Sort geographic point by Lat coordinate

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

import detectprojv2j.structures.point.Point3DGeographic;


public class SortPointsByLat implements Comparator <Point3DGeographic> {
    
   @Override
    public int compare(final Point3DGeographic p1, final Point3DGeographic p2)
    {
        if (p1.getLat() < p2.getLat()) return -1;
        if (p1.getLat() > p2.getLat()) return 1;
        return 0;
    }
}
