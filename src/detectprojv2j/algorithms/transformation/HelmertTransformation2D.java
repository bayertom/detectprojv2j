// Description: 2D Helmert transformation

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

package detectprojv2j.algorithms.transformation;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

import detectprojv2j.structures.point.Point3DCartesian;

import detectprojv2j.exceptions.BadDataException;
import detectprojv2j.exceptions.MathZeroDevisionException;

public class HelmertTransformation2D {
        
        private double x_mass_local, y_mass_local;		//Centre of mass: local system
        private double x_mass_global, y_mass_global;		//Centre of mass: global system
        private double c1, c2;                                  //Both transformation coefficients
        private double J;					//(X^T * X)^-1
        private double k;					//Sum of weights
        
        public HelmertTransformation2D()
        {
                x_mass_local = 0;
                y_mass_local = 0;
                x_mass_global = 0;
                y_mass_global = 0;
                c1 = 1;
                c2 = 1;
                J = 0;
                k = 0;
        }

        public final double getXMassLocal() {return x_mass_local;}
        
        public final double getYMassLocal() {return y_mass_local;}
        
        public final double getXMassGlobal() {return x_mass_global;}
        
        public final double getYMassGlobal() {return y_mass_global;}
        
        public final double getC1() {return c1;}
        
        public final double getC2() {return c2;}
        
        public final double getJ() {return J;}
        
        public final double getK() {return k;}
        
        public void transformPoints (final List <Point3DCartesian> global_points, final List <Point3DCartesian> local_points, List <Point3DCartesian> transformed_points)
        {
                //Compute non weighted 2D Helmert transformation
                List <Double> weights = new ArrayList <>( Collections.nCopies(global_points.size(), 1.0) );
                getTransformKey(global_points, local_points, weights);
                transform(global_points, local_points, transformed_points);
        }


        public void getTransformKey (final List <Point3DCartesian> global_points, final List <Point3DCartesian> local_points, final List <Double> weights )
        {
                //Get transformation key for weighted transformation
                final int n_global = global_points.size(), n_local = local_points.size();
                double sumx_local = 0, sumy_local = 0, sumx_global = 0, sumy_global = 0;

                //Not enough points
                if ( ( n_global < 2 ) || ( n_local < 2 ) )
                {
                        throw new BadDataException ( "BadDataException: not enough points. ", "Can not compute Helmert 2D transformation key. \n" );
                }

                //Less local points
                if ( n_global > n_local )
                {
                        throw new BadDataException ( "BadDataException: less local points than global points. ", "Can not compute Helmert 2D transformation key. \n" );
                }

                //Compute sums of coordinates, weights
                double sum_weights = 0;

                for ( int i = 0; i < n_global; i++ )
                {
                        sumx_local += weights.get(i) * local_points.get(i).getX();
                        sumy_local += weights.get(i) * local_points.get(i).getY();
                        sumx_global += weights.get(i) * global_points.get(i).getX();
                        sumy_global += weights.get(i) * global_points.get(i).getY();

                        sum_weights += weights.get(i);
                }

                //Compute center of mass
                x_mass_local = sumx_local / ( sum_weights );
                y_mass_local = sumy_local / ( sum_weights );
                x_mass_global = sumx_global / ( sum_weights );
                y_mass_global = sumy_global / ( sum_weights );

                //Remeber k
                k = sum_weights;

                //Reduction of coordinates to the center of mass
                double x_red_local, y_red_local, xred_global, yred_global, k1 = 0, k2 = 0;

                //Process all points
                J = 0;

                for ( int i = 0; i < n_global; i++ )
                {
                        //Compute reduced coordinates
                        x_red_local = local_points.get(i).getX() - x_mass_local;
                        y_red_local = local_points.get(i).getY() - y_mass_local;
                        xred_global = global_points.get(i).getX() - x_mass_global;
                        yred_global = global_points.get(i).getY() - y_mass_global;

                        //Compute coefficients of transformation
                        J += weights.get(i) * ( x_red_local * x_red_local + y_red_local * y_red_local );
                        k1 += weights.get(i) * ( xred_global * x_red_local + yred_global * y_red_local );
                        k2 += weights.get(i) * ( yred_global * x_red_local - xred_global * y_red_local );
                }

                //Throw exception
                if ( J == 0 )
                {
                        throw new MathZeroDevisionException ( "MathZeroDevisionException: can not compute Helmert 2D transformation key, ", " divider = 0.", J);
                }

                //Transformation coefficients
                c1 = k1 / J;
                c2 = k2 / J;
        }


        public void transform (final List <Point3DCartesian> global_points, final List <Point3DCartesian> local_points, List <Point3DCartesian> transformed_points )
        {
                //Transform all points using 2D Helmert transformation ( weighted / non-weighted )
                final int n = local_points.size();

                if ( transformed_points.size() != 0 )
                {
                        throw new BadDataException ( "BadDataException: list of tranformed points is not empty. ", "Can not compute Helmert 2D transformation." );
                }

                for ( int i = 0; i < n; i++ )
                {
                        //Reduce coordinates
                        final double x_red_local = local_points.get(i).getX() - x_mass_local,
                                                    y_red_local = local_points.get(i).getY() - y_mass_local;

                        //Transform point, add coordinates center of mass
                        final double x_transform = c1 * x_red_local - c2 * y_red_local + x_mass_global,
                                                    y_transform = c2 * x_red_local + c1 * y_red_local + y_mass_global;

                        //Add point to the list
                        transformed_points.add ( new Point3DCartesian ( x_transform, y_transform, 0 ) );
                }
        }
        
}
