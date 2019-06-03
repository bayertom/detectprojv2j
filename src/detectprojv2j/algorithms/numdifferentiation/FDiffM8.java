// Description: Functor, used in numerical differentiation of the projection equations according to latp, lonp, lat1, lat2, lon0, c (Method M8S, NLSP)

// Copyright (c) 2015 - 2017
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

package detectprojv2j.algorithms.numdifferentiation;

import detectprojv2j.types.TTransformedLongitudeDirection;

import detectprojv2j.structures.matrix.Matrix;

import detectprojv2j.algorithms.carttransformation.CartTransformation;
import detectprojv2j.types.ICoordFunctionProj;

public class FDiffM8 {
        
        private final double  R;						//Earth radius, computed from 2D Helmert transformation
	private final double lat;                                               //Latitude of the point
	private final double lon;                                               //Longitude of the point
	private final ICoordFunctionProj equat;                                     //Coordinate function
	private final TTransformedLongitudeDirection trans_lon_dir;            //Transformed longitude direction
        
        FDiffM8(final double R_, final double lat_, final double lon_, final ICoordFunctionProj equat_, final TTransformedLongitudeDirection trans_lon_dir_) 
        {
               R = R_;
               lat = lat_;
               lon = lon_;
               equat = equat_;
               trans_lon_dir = trans_lon_dir_;
        }
			

	public double function (final Matrix  XT)
        {
                //Convert ( lat, lon ) -> ( lat_trans, lon_trans)_trans
                final double lat_trans = CartTransformation.latToLatTrans(lat, lon, XT.items[0][0], XT.items[0][1]);
                final double lon_trans = CartTransformation.lonToLonTrans(lat, lon, XT.items[0][0], XT.items[0][1], trans_lon_dir);

                //Reduce longitude lon0 (not lon0_trans)
                final double lon_transr = CartTransformation.redLon0(lon_trans, XT.items[0][4]);
                
                //Evaluate map projection equation; used for the partial derivative
                double res = equat.f(lat_trans, lon_transr, R, XT.items[0][2], XT.items[0][3], 0, 0, 0, XT.items[0][5]);

                return res;
        }
}
