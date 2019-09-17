// Description: Sort triplet by the first value

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

import detectprojv2j.types.Triplet;

//public class SortByFirst implements Comparator <Triplet> {
public class SortByFirst<T extends Comparable<T>, U extends Comparable<U>, V extends Comparable<V>> implements Comparator<Triplet<T, U, V>> {

        @Override
        public int compare(final Triplet t1, final Triplet t2) {
                return t1.getFirst().compareTo(t2.getFirst());
        }
}
