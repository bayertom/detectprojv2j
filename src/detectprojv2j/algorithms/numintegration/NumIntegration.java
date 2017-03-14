// Description: Numeric integration
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

package detectprojv2j.algorithms.numintegration;

import static java.lang.Math.PI;
import static java.lang.Math.asin;
import static java.lang.Math.log;
import static java.lang.Math.sin;
import static java.lang.Math.sqrt;
import static java.lang.Math.tan;

public class NumIntegration 
{

        public static double getInEllipticIntegral1(final double k, double phi_radians, final double eps)
        {
                //Incomplete elliptic integral of the first kind: recursive approach based by Landen transformation
                //F = int ((1 - k^2 * sin^2(phi))^(-1/2), 0, phi), threshold eps = 1.0e-14
                //Algorithm by Norbert Rosch, 2011, The derivation of algorithms to compute elliptic integrals of the first and second kind by Landen-transformation
                
                phi_radians = ( asin (k * sin(phi_radians)) + phi_radians) / 2.0;

                //Stop the recursive approach
                if ((1.0 - k) < eps)
                {
                        //Elliptic integral
                        return log( tan( phi_radians / 2.0 + PI / 4.0 ) );
                }

                //Perform recursion
                else
                {
                        //Recursive formula for k
                        return 2.0 / ( 1.0 + k ) * getInEllipticIntegral1((2.0 * sqrt( k )) / (1.0 + k), phi_radians, eps); 
                }
        }
        
        
        public static double getInEllipticIntegral2(final double k, double phi_radians, final double eps)
        {
                //Incomplete elliptic integral of the second kind: recursive approach based by Landen transformation
                //F = int ((1 - k^2 * sin^2(phi))^(1/2), 0, phi, threshold eps = 1.0e-14
                //Algorithm by Norbert Rosch, 2011, The derivation of algorithms to compute elliptic integrals of the first and second kind by Landen-transformation
                
                final double n_phi = (asin( k * sin(phi_radians)) + phi_radians) / 2.0;

                //Stop the recursive approach
                if ((1.0 - k ) < eps)
                {
                        //Elliptic integral
                        return ( 1 + k) * sin( n_phi ) + ( 1 - k ) * 
                                getInEllipticIntegral1((2.0 * sqrt( k )) / (1.0 + k ), n_phi, eps) - k * sin( phi_radians );
                }
                
                //Recursion
                else 
                {
                        //Recursive formulas for k
                        final double n_k = (2.0 * sqrt( k )) / (1.0 + k);

                        return (1.0 + k ) * getInEllipticIntegral2( n_k, n_phi,eps) + (1.0 - k) *
                                getInEllipticIntegral1( n_k, n_phi, eps) - k * sin( phi_radians);
                }
        }
}
