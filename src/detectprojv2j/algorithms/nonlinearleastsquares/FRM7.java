// Description: Functor, compute residuals between sets P,P'
// Utilized for the map projection analysis
// Method  M7 (7 determined parameters, NLS)

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

package detectprojv2j.algorithms.nonlinearleastsquares;

import java.util.List;
import java.util.ArrayList;

import detectprojv2j.types.TTransformedLongitudeDirection;

import detectprojv2j.structures.point.Point3DCartesian;
import detectprojv2j.structures.point.Point3DGeographic;
import  detectprojv2j.structures.matrix.Matrix;

import detectprojv2j.algorithms.carttransformation.CartTransformation;
import detectprojv2j.types.ICoordFunctionProj;


public class FRM7 {
        
        private final List <Point3DCartesian> test_points;                              //List of test points
	private final List <Point3DGeographic> reference_points;                        //List of analyzed points
	private final ICoordFunctionProj F, G;                                          //Refernces to the coordinate functions
	private final TTransformedLongitudeDirection trans_lon_dir;                     //Transformed longitude direction
	private final double [] dx, dy;                                                 //Shifts between analyzed and reference maps

        public	FRM7(final List test_points_, final List reference_points_, final ICoordFunctionProj  pF_, final ICoordFunctionProj pG_, final TTransformedLongitudeDirection trans_lon_dir_, double [] dx_, double [] dy_) 
        {
                test_points = test_points_;
                reference_points = reference_points_;
                F = pF_;
                G = pG_;
                trans_lon_dir = trans_lon_dir_;
                dx = dx_; 
                dy = dy_;
        }
        
       public void function (Matrix X, Matrix Y, Matrix V, Matrix W)
       {
               //Apply projection proj(Q->P'): convert geographic points to the cartesian
                final int m = test_points.size();
                int index = 0;
		double x_mass_test = 0.0, y_mass_test = 0.0, x_mass_reference = 0.0, y_mass_reference = 0.0;
		dx[0] = 0;
                dy[0] = 0;
                
                //Process all points
                List <Point3DCartesian> reference_points_projected = new ArrayList<>();

		for (final Point3DGeographic p : reference_points)
		{
			//(lat, lon) -> (lat_trans, lon_trans)
			Point3DGeographic pole = new Point3DGeographic(X.items[1][0], X.items[2][0],0);
			final double lat_trans = CartTransformation.latToLatTrans(p.getLat(), p.getLon(), pole.getLat(), pole.getLon());
			final double lon_trans = CartTransformation.lonToLonTrans(p.getLat(), p.getLon(), pole.getLat(), pole.getLon(), trans_lon_dir);

                        //Reduce longitude lon0 (not lon0_trans)
			final double lon_transr = CartTransformation.redLon0(lon_trans, X.items[5][0]);
                        
			// (lat_trans, lon_trans) -> (X, Y)
			final double XR = F.f(lat_trans, lon_transr, X.items[0][0], X.items[3][0], X.items[4][0], 0, 0, 0, X.items[6][0]);
			final double YR = G.f(lat_trans, lon_transr, X.items[0][0], X.items[3][0], X.items[4][0], 0, 0, 0, X.items[6][0]);
			
			//Add point to the list
			Point3DCartesian  p_temp = new Point3DCartesian(XR, YR, 0);
			reference_points_projected.add(p_temp);

			//Coordinate sums
			x_mass_test += test_points.get(index).getX();
			y_mass_test += test_points.get(index).getY();
			x_mass_reference += XR;
			y_mass_reference += YR;

			index++;
		}

		//Update weight matrix
		//W = eye(2 * m, 2 * m, 1.0);

		//Compute centers of mass for both systems P, P'
		x_mass_test = x_mass_test / m;
		y_mass_test = y_mass_test / m;
		x_mass_reference = x_mass_reference / m;
		y_mass_reference = y_mass_reference / m;
                
                //Compute coordinate differences (residuals): estimated - input
		for (int i = 0; i < m; i++)
		{
			V.items[i][0] = ((reference_points_projected.get(i).getX() - x_mass_reference) - (test_points.get(i).getX() - x_mass_test));
			V.items[i + m][0] = ((reference_points_projected.get(i).getY() - y_mass_reference) - (test_points.get(i).getY() - y_mass_test));
		}

		//Compute shifts dx, dy
		dx[0] = x_mass_test - x_mass_reference;
		dy[0] = y_mass_test - y_mass_reference;
       }

}
