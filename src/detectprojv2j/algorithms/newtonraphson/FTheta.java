// Description: Functor, compute the f(var,c) = 0, c = const
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


public class FTheta {
        
        private final double c;							//Constant c at current iteration of f(var, c) = 0
        private final IThetaFunction pTheta;                                    //Term for evaluation of theta in Newton-Raphson method

        
	public FTheta(final double c_, final IThetaFunction pTheta_)
        {
                c = c_;
                pTheta = pTheta_;
        }

	public double function (final double theta_i)
	{
		return pTheta.f(c, theta_i);
	}
}
