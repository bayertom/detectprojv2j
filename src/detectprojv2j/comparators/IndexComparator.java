// Description: Comparator, returns indices of sorted elements
// Used to store a permutation of indices of sorted elements

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
import java.util.List;

public class IndexComparator implements Comparator <Integer> {

    private final List <Double> list;
    
    public IndexComparator (List <Double> list_)
    {
            list = list_;
    }
        
   @Override
    public int compare(final Integer i, final Integer j)
    {
        return Double.compare(list.get(i), list.get(j));
    }
        
}
