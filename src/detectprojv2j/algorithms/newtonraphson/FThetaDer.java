// Description: Functor, compute the derivative df/dheta of f(theta, c) = 0 at current iteration i
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

import detectprojv2j.types.IThetaFunction;


public class FThetaDer {
        
        private final double c;							//Constant c at current iteration in df/dtheta
        private final IThetaFunction pThetaDer;                                 //Term for evaluation of theta in Newton-Raphson method

	public FThetaDer(final double c_, final IThetaFunction pThetaDer_) 
        {
                c = c_;
                pThetaDer = pThetaDer_;
        }

        
	public double function (final double theta_i)
	{
		return pThetaDer.f(c, theta_i);
	}
        
}
