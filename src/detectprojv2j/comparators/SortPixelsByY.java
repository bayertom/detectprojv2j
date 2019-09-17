// Description: Sort pixel by py coordinate
// Copyright (c) 2018 - 2019
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

import detectprojv2j.structures.tile.Pixel;


public class SortPixelsByY implements Comparator <Pixel> {
    
   @Override
    public int compare(final Pixel p1, final Pixel p2)
    {
        if (p1.getPy() < p2.getPy()) return -1;
        if (p1.getPy() > p2.getPy()) return 1;
        return 0;
    }
}
