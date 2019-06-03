// Description: Conic projection, derived form Projection

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

package detectprojv2j.structures.projection;

import detectprojv2j.types.*;
import static detectprojv2j.types.TTransformedLongitudeDirection.*;

import static detectprojv2j.consts.Consts.*;

import detectprojv2j.structures.point.Point3DGeographic;

public class ProjectionConic extends Projection
{
        
        protected double lat1, lat2;                                            //True parallels
        protected Point3DGeographic cart_pole;					//Meta-pole position
	protected TTransformedLongitudeDirection lon_dir;                       //Measuring londitudes mode
        
         public ProjectionConic() {
                super();
                lat1 = 45;
                lat2 = 45;
                cart_pole = new Point3DGeographic (MAX_LAT, 0.0, 0.0);
                lon_dir = NormalDirection2;
        }
        
	public ProjectionConic( final double R_,  final double latp_,  final double lonp_,  final double lat1_, final double lat2_, final TTransformedLongitudeDirection lon_dir_,  final double lon0_,  final double dx_,  final double dy_,  
                                final double c_,  final ICoordFunctionProj pF_,  final ICoordFunctionProj pG_, final ICoordFunctionProj pFI_,  final ICoordFunctionProj pGI_, final String name_, final String id_) 
        {
                super(R_, lon0_, dx_, dy_, c_, pF_, pG_, pFI_, pGI_, name_, id_);
                lat1 = lat1_;
                lat2 = lat2_;
                cart_pole = new Point3DGeographic (latp_, lonp_, 0.0);
                lon_dir = lon_dir_;
        }
        
        @Override
        public Point3DGeographic getCartPole() { return cart_pole;}

        @Override
        public double getLat1() {return lat1;}

        @Override
        public double getLat2() {return lat2;}

        @Override
        public double getA() {return R;}       
        
        @Override
        public double getB() {return R;}

        @Override
        public TTransformedLongitudeDirection getLonDir() { return lon_dir;}

        @Override
        public String getFamily() { return "Conic";}

        @Override
        public TInterval getLatPInterval() { return new TInterval (MIN_LAT, MAX_LAT); }

        @Override
        public TInterval getLonPInterval() { return new TInterval (MIN_LON, MAX_LON); }

        @Override
        public TInterval getLat1Interval() { return new TInterval (MIN_LAT1, MAX_LAT1); }

        @Override
        public void setCartPole(final Point3DGeographic cart_pole_) { cart_pole = cart_pole_; }

        @Override
        public void setLat1(final double lat1_) { lat1 = lat1_; }

        @Override
        public void setLat2(final double lat2_) { lat2 = lat2_;}

        @Override
        public void setA(final double a_) { }

        @Override
        public void setB(final double b_) { }

        @Override
        public void setLonDir(final TTransformedLongitudeDirection lon_dir_) { lon_dir = lon_dir_; }
      
}
