// Description: class, compute the inverse coordinate function to at currrent iteration i to determine lon_i
// Utilized for the Newton-Raphson method
// Call operator () 

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


package detectprojv2j.algorithms.newtonraphson;

import detectprojv2j.types.ICoordFunctionProj;


public class FILon {
        private final double X;							//Cartesian coordinate X of the point
        private final double Y;							//Cartesian coordinate Y of the point
        private final double R;							//Earth radius
        private final double lat1;						//True parallel of latitude lat1
        private final double lat2;						//True parallel of latitude lat2
        private final double lon0; 						//Projection central meridian
        private final double dx;						//Aditive constant dx
        private final double dy;						//Aditive constant dy
        private final ICoordFunctionProj pFILon;                                //Term for evaluation of lon in Newton-Raphson method


	public FILon(final double X_, final double Y_, final double R_, final double lat1_, final double lat2_, final double lon0_, final double dx_, final double dy_, final ICoordFunctionProj pFILon_) {
                X = X_;
                Y = Y_;
                R = R_;
                lat1 = lat1_;
                lat2 = lat2_;
                lon0 = lon0_;
                dx = dx_;
                dy = dy_;
                pFILon = pFILon_;
        }

	public double function (final double lon_i)
	{
		return pFILon.f(lon_i, X, Y, R, lat1, lat2, lon0, dx, dy);
	}
}
