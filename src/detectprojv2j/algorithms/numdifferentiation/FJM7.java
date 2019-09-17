// Description: Functor, create Jacobian matrix J
// Method  M7 (6 determined parameters)
// Jacobian matrix elements computed numerically using the Stirling method

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

import java.util.List;

import detectprojv2j.types.TTransformedLongitudeDirection;
import static detectprojv2j.types.TDerivativeType.*;
import static detectprojv2j.types.TDerivativeVariable.*;
import detectprojv2j.types.ICoordFunctionProj;

import static detectprojv2j.consts.Consts.*;

import detectprojv2j.structures.point.Point3DCartesian;
import detectprojv2j.structures.point.Point3DGeographic;
import detectprojv2j.structures.matrix.Matrix;

public class FJM7 {
        
        private final List <Point3DCartesian> test_points;				//List of test points
	private final List <Point3DGeographic>  reference_points;			//List of reference points
	private final ICoordFunctionProj  F;                                            //Reference to the coordinate function X = F(lat, lon)
        private final ICoordFunctionProj  G;                                            //Reference to the coordinate function Y = G(lat, lon)
       	private final TTransformedLongitudeDirection trans_lon_dir;			//Transformed longitude direction
        
        public FJM7(final List <Point3DCartesian > test_points_, final List <Point3DGeographic > reference_points_, final ICoordFunctionProj  pF, final ICoordFunctionProj  pG, final TTransformedLongitudeDirection trans_lon_dir_)
        { 
                test_points = test_points_;
                reference_points = reference_points_;
                F = pF; 
                G = pG;
                trans_lon_dir  = trans_lon_dir_;
        }

	public void function (final Matrix X, Matrix J)
	{
		//Evaluate members of the Jacobian matrix, method M7
                //Temporary Jacobian matrix
		Matrix  J_T = new Matrix(J);                          
		
		//Create matrix XT (1, 7) from X (7, 1)
		Matrix  XT = X.trans();          

		//Process all points: compute Jacobian matrix of partial derivatives
		int i = 0, m = test_points.size();
                        
		for (final Point3DGeographic p : reference_points)
		{
			//Get coordinates of the point
			final double lat = p.getLat();
			final double lon = p.getLon();
                        
                        //Create objects
                        FDiffM7  fderx = new FDiffM7(lat, lon, F, trans_lon_dir);
                        FDiffM7  fdery = new FDiffM7(lat, lon, G, trans_lon_dir);

			//Upper part of the Jacobian matrix: R, latp, lonp, lat0, lon0=0 (angular values in radians)
			J_T.items[i][0] = NumDifferentiation.getDerivative(fderx::function, XT, FirstDerivative, VariableX1, NUM_DERIV_STEP, false);
			J_T.items[i][1] = NumDifferentiation.getDerivative(fderx::function, XT, FirstDerivative, VariableX2, NUM_DERIV_STEP, false);
			J_T.items[i][2] = NumDifferentiation.getDerivative(fderx::function, XT, FirstDerivative, VariableX3, NUM_DERIV_STEP, false);
			J_T.items[i][3] = NumDifferentiation.getDerivative(fderx::function, XT, FirstDerivative, VariableX4, NUM_DERIV_STEP, false);
			J_T.items[i][4] = NumDifferentiation.getDerivative(fderx::function, XT, FirstDerivative, VariableX5, NUM_DERIV_STEP, false);
			J_T.items[i][5] = NumDifferentiation.getDerivative(fderx::function, XT, FirstDerivative, VariableX6, NUM_DERIV_STEP, false);
                        J_T.items[i][6] = NumDifferentiation.getDerivative(fderx::function, XT, FirstDerivative, VariableX7, NUM_DERIV_STEP, false);
			
			//Lower part of the Jcobian matrix: R, latp, lonp, lat0, lon0=0 (angular values in radians)
			J_T.items[i + m][0] = NumDifferentiation.getDerivative(fdery::function, XT, FirstDerivative, VariableX1, NUM_DERIV_STEP, false);
			J_T.items[i + m][1] = NumDifferentiation.getDerivative(fdery::function, XT, FirstDerivative, VariableX2, NUM_DERIV_STEP, false);
			J_T.items[i + m][2] = NumDifferentiation.getDerivative(fdery::function, XT, FirstDerivative, VariableX3, NUM_DERIV_STEP, false);
			J_T.items[i + m][3] = NumDifferentiation.getDerivative(fdery::function, XT, FirstDerivative, VariableX4, NUM_DERIV_STEP, false);
			J_T.items[i + m][4] = NumDifferentiation.getDerivative(fdery::function, XT, FirstDerivative, VariableX5, NUM_DERIV_STEP, false);
			J_T.items[i + m][5] = NumDifferentiation.getDerivative(fdery::function, XT, FirstDerivative, VariableX6, NUM_DERIV_STEP, false);
                        J_T.items[i + m][6] = NumDifferentiation.getDerivative(fdery::function, XT, FirstDerivative, VariableX7, NUM_DERIV_STEP, false);

			//Increment index
			i++;
		}

               // J_T.print();
                
		//Compute column ss of the Jacobian matrix
		double s0x = 0.0, s1x = 0.0, s2x = 0.0, s3x = 0.0, s4x = 0.0, s5x = 0.0, s6x = 0.0, s0y = 0.0, s1y = 0.0, s2y = 0.0, s3y = 0.0, s4y = 0.0, s5y = 0.0, s6y = 0.0;

		for (i = 0; i < m; i++)
		{
			//Sums of X derivatives
			s0x += J_T.items[i][0]; 
                        s1x += J_T.items[i][1]; 
                        s2x += J_T.items[i][2]; 
			s3x += J_T.items[i][3];
                        s4x += J_T.items[i][4]; 
                        s5x += J_T.items[i][5];
                        s6x += J_T.items[i][6];

			//Sums of Y derivatives
			s0y += J_T.items[i + m][0]; 
                        s1y += J_T.items[i + m][1]; 
                        s2y += J_T.items[i + m][2]; 
			s3y += J_T.items[i + m][3]; 
                        s4y += J_T.items[i + m][4]; 
                        s5y += J_T.items[i + m][5];
                        s6y += J_T.items[i + m][6];
		}

		//Compute Jacobian matrix
		for (i = 0; i < m; i++)
		{
			//X derivatives
			J.items[i][0] = J_T.items[i][0] - s0x / m;
			J.items[i][1] = J_T.items[i][1] - s1x / m;
			J.items[i][2] = J_T.items[i][2] - s2x / m;
			J.items[i][3] = J_T.items[i][3] - s3x / m;
			J.items[i][4] = J_T.items[i][4] - s4x / m;
			J.items[i][5] = J_T.items[i][5] - s5x / m;
                        J.items[i][6] = J_T.items[i][6] - s6x / m;

			//Y derivatives
			J.items[i + m][0] = J_T.items[i + m][0] - s0y / m;
			J.items[i + m][1] = J_T.items[i + m][1] - s1y / m;
			J.items[i + m][2] = J_T.items[i + m][2] - s2y / m;
			J.items[i + m][3] = J_T.items[i + m][3] - s3y / m;
			J.items[i + m][4] = J_T.items[i + m][4] - s4y / m;
			J.items[i + m][5] = J_T.items[i + m][5] - s5y / m;
                        J.items[i + m][6] = J_T.items[i + m][6] - s6y / m;
		}
	}    
        
}
