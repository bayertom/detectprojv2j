// Description: Ellipsoidal projection, derived from Projection

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

package detectprojv2j.structures.projection;

import detectprojv2j.types.*;
import static detectprojv2j.types.TTransformedLongitudeDirection.*;

import static detectprojv2j.consts.Consts.*;

import detectprojv2j.structures.point.Point3DGeographic;

public class ProjectionEllipsoidal extends Projection
{

        protected double a, b;                                                  //Ellpsoid semi-axes
        protected double lat1;                                                  //True parallel
             
         public ProjectionEllipsoidal() {
                super();
                lat1 = 0;
                a = 1;
                b = 1;
        }
        
	public ProjectionEllipsoidal( final double R_,  final double a_,  final double b_,  final double lat1_,  final double lon0_,  final double dx_,  final double dy_,  final double c_,  final ICoordFunctionProj pX,  final ICoordFunctionProj pY, final String name_) 
        {
                super(R_, lon0_, dx_, dy_, c_, pX, pY, name_);
                lat1 = lat1_;
                a = a_;
                b = b_;
        }
        
        @Override
        public Point3DGeographic getCartPole() { return new Point3DGeographic(MAX_LAT, 0, 0);}

        @Override
        public double getLat1() {return lat1;}

        @Override
        public double getLat2() {return lat1;}

        @Override
        public double getA() {return a;}       
        
        @Override
        public double getB() {return b;}

        @Override
        public TTransformedLongitudeDirection getLonDir() { return NoDirection;}

        @Override
        public String getFamily() { return "Elips";}

        @Override
        public TInterval getLatPInterval() { return new TInterval (MAX_LAT, MAX_LAT); }

        @Override
        public TInterval getLonPInterval() { return new TInterval (0, 0); }

        @Override
        public TInterval getLat1Interval() { return new TInterval (0, MAX_LAT1); }

        @Override
        public void setCartPole(final Point3DGeographic cart_pole_) {}

        @Override
        public void setLat1(final double lat1_) { lat1 = lat1_; }

        @Override
        public void setLat2(final double lat2_) {}

        @Override
        public void setA(final double a_) { a = a_;}

        @Override
        public void setB(final double b_) { b = b; }

        @Override
        public void setLonDir(final TTransformedLongitudeDirection lon_dir_) {}

}