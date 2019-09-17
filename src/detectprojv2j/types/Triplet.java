// Description: Triplet implementation

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

package detectprojv2j.types;

public class Triplet <T extends Comparable<T>, U extends Comparable<U>,  V extends Comparable<V>> {

    private final T first;
    private final U second;
    private final V third;

    public Triplet(T first_, U second_, V third_) {
        first = first_;
        second = second_;
        third = third_;
    }

    public T getFirst() { return first; }

    public U getSecond() { return second; }

    public V getThird() { return third; }
}