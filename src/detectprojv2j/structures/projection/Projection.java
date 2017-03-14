// Description: General cartographic projection with abstract methods
// Supported equations in the non-closed form,

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

import detectprojv2j.types.TInterval;
import detectprojv2j.types.TTransformedLongitudeDirection;
import detectprojv2j.types.ICoordFunctionProj;

import detectprojv2j.structures.point.Point3DGeographic;

public abstract class Projection
{
        
        //Common cartographic projection: abstract class
	protected double R;						//Sphere radius
	protected double lon0;						//Projection central parallel
	protected double dx;						//Aditive finalant dx
	protected double dy;						//Aditive finalant dy
	protected double c;						//Additional finalant of the projection
	protected String name;                                          //Projection name (In accordance with Proj.4)

	ICoordFunctionProj X;                                           //Pointer to the coordinate function X = F(lat, lon)
	ICoordFunctionProj Y;                                           //Pointer to the coordinate function Y = Y(lat, lon)

        public Projection()
        {
                 R = 1.0;
                 lon0 = 0.0;
                 dx = 0;
                 dy = 0;
                 c = 1;
                 X = Projections::X_aea;
                 Y = Projections::Y_def;
                 name = "default";
        }

	Projection(final double R_, final double lon0_, final double dx_, final double dy_, final double c_, final ICoordFunctionProj  pX, final ICoordFunctionProj  pY, final String name_) 
        {
                
		R = R_;
                lon0 = lon0_;
                dx = dx_;
                dy = dy_;
                c = c_;
                name = name_;
                X = pX;  
                Y = pY;
        }

	//Methods common for all derived classes (methods are final)
	public final double getR() {return R;}
	public final double getLon0() {return lon0;}
	public final double getDx() {return dx;}
	public final double getDy() {return dy;}
	public final double getC() {return c;}
	public final double getX(final double lat, final double lon) { return X.f(this.getR(), this.getLat1(), this.getLat2(), lat, lon, this.getLon0(), this.getDx(), this.getDy(), this.getC()); }
	public final double getX(final double R_, final double lat1_, final double lat2_, final double lat_, final double lon_, final double lon0_, final double dx_, final double dy_, final double c_) {return X.f(R_, lat1_, lat2_, lat_, lon_, lon0_, dx_, dy_, c_);}
	public final ICoordFunctionProj  getX() { return X; }
	public final double getY(final double lat, final double lon) { return Y.f(this.getR(), this.getLat1(), this.getLat2(), lat, lon, this.getLon0(), this.getDx(), this.getDy(), this.getC()); }
	public final double getY(final double R_, final double lat1_, final double lat2_, final double lat_, final double lon_, final double lon0_, final double dx_, final double dy_, final double c_) { return Y.f(R_, lat1_, lat2_, lat_, lon_, lon0_, dx_, dy_, c_); }
	public final ICoordFunctionProj  getY() { return Y; }
	public final String getName() {return name;}

	public void setR(final double R_) {R = R_;}
	public void setLon0(final double lon0_) {lon0 = lon0_;}
	public void setDx(final double dx_) {dx = dx_;}
	public void setDy(final double dy_) {dy = dy_;}
	public void setC(final double c_) {c = c_;}
	public void setName(final String name_){name = name_;}

	//Methods different for all derived classes (abstract methods)
	public abstract Point3DGeographic getCartPole();
	public abstract double getLat1();
	public abstract double getLat2();
	public abstract double getA();
	public abstract double getB();
	public abstract  TTransformedLongitudeDirection getLonDir();
	public abstract String getFamily();
	public abstract TInterval  getLatPInterval();
	public abstract TInterval  getLonPInterval();
	public abstract TInterval  getLat1Interval();

	public abstract void setCartPole(final Point3DGeographic  pole);
	public abstract void setLat1(final double lat1);
	public abstract void setLat2(final double lat2);
	public abstract void setA(final double a);
	public abstract void setB(final double b);
	public abstract void setLonDir(final TTransformedLongitudeDirection lon_dir_);
}
