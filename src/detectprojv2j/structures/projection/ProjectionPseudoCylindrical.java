// Description: Pseudocylindrical projection, derived from Projection
// Supported equations in the non-closed form
// For evaluation, stored in the postfix notation

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

public class ProjectionPseudoCylindrical extends Projection
{
        
        protected double lat1;                                                  //True parallel
        protected Point3DGeographic cart_pole;					//Meta-pole position
	protected TTransformedLongitudeDirection lon_dir;                       //Measuring londitudes mode
        
         public ProjectionPseudoCylindrical() {
                super();
                lat1 = 0;
                cart_pole = new Point3DGeographic (MAX_LAT, 0.0, 0.0);
                lon_dir = NormalDirection2;
        }
        
	public ProjectionPseudoCylindrical( final double R_,  final double latp_,  final double lonp_,  final double lat1_, final TTransformedLongitudeDirection lon_dir_,  final double lon0_,  final double dx_,  final double dy_,  
                                            final double c_,  final ICoordFunctionProj pX,  final ICoordFunctionProj pY, final String name_, final String id_) 
        {
                super(R_, lon0_, dx_, dy_, c_, pX, pY, name_, id_);
                lat1 = lat1_;
                cart_pole = new Point3DGeographic (latp_, lonp_, 0.0);
                lon_dir = lon_dir_;
        }
        
        @Override
        public Point3DGeographic getCartPole() { return cart_pole;}

        @Override
        public double getLat1() {return lat1;}

        @Override
        public double getLat2() {return lat1;}

        @Override
        public double getA() {return R;}       
        
        @Override
        public double getB() {return R;}

        @Override
        public TTransformedLongitudeDirection getLonDir() { return lon_dir;}

        @Override
        public String getFamily() { return "Pseudocylindrical";}

        @Override
        public TInterval getLatPInterval() { return new TInterval (MIN_LAT, MAX_LAT); }

        @Override
        public TInterval getLonPInterval() { return new TInterval (MIN_LON, MAX_LON); }

        @Override
        public TInterval getLat1Interval() { return new TInterval (0, MAX_LAT1); }

        @Override
        public void setCartPole(final Point3DGeographic cart_pole_) { cart_pole = cart_pole_; }

        @Override
        public void setLat1(final double lat1_) { lat1 = lat1_; }

        @Override
        public void setLat2(final double lat2_) {}

        @Override
        public void setA(final double a_) { }

        @Override
        public void setB(final double b_) { }

        @Override
        public void setLonDir(final TTransformedLongitudeDirection lon_dir_) { lon_dir = lon_dir_; }

}
