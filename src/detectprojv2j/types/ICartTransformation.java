// Description: Interface to the cartographic transformation (lat, lon) <-> (x,y)
// Replacement of the function pointer in C++

// Copyright (c) 2016 - 2017
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

import detectprojv2j.structures.projection.Projection;

public interface ICartTransformation {
        
        double f (final double X, final double Y, final Projection proj, final double alpha, double [] lat_trans, double [] lon_trans, double [] lat, double [] lon);
}
