// Description: Interface to the elements of the Jacobian matrix J(2,2) = (aj bj; cj dj) of f(lat, lon) = and g(lat, lon) = 0
// Used to find a solution of the inverse projection equations using NLS
// Replacement of the function pointer in C++

// Copyright (c) 2017 - 2017
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

@FunctionalInterface
public interface ICoordFunctionProjJI {
        
        void f( final double lat, final double lon, final double X, final double Y, final double R, final double lat1, final double lat2, final double lon0, final double dx, final double dy, final double c, double [] aj, double [] bj, double [] cj, double [] dj);
}