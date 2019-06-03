// Description: A parallel of the latitude given by the list of points

// Copyright (c) 2015 - 2018
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

package detectprojv2j.structures.graticule;

import detectprojv2j.algorithms.angle3points.Angle3Points;
import java.util.List;
import static java.lang.Math.*;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Random;

import detectprojv2j.types.TInterval;

import detectprojv2j.structures.point.Point3DGeographic;
import detectprojv2j.structures.point.Point3DCartesian;
import detectprojv2j.structures.projection.Projection;

import static detectprojv2j.consts.Consts.*;

import detectprojv2j.algorithms.carttransformation.CartTransformation;
import detectprojv2j.algorithms.round.Round;
import detectprojv2j.algorithms.singularitydetection.SingularityDetection;

import detectprojv2j.exceptions.MathException;
import detectprojv2j.exceptions.MathInvalidArgumentException;


public class Parallel {
        
        private final double lat;                                             //Latitude of the parallel
	private final TInterval lon_interval;                                //Longitude interval of the parallel
	private final double dlon;                                            //Longitude step
	private final double dff;                                             //Flat factor between sampled segments
        private final double lon_min_shift;                                   //Shift of the initial parallel point: [lat, lon_min + lon_min_shift], default = 0
	private final double lon_max_shift;                                   //Shift of the last parallel point: [lat, lon_max - lon_max_shift], default = 0
	private final List <Double> lons;                                     //Longitude of the parallel points

        
	public Parallel(final double lat_)
        {
                //Create parallel, uniform sampling with the step dlon = 10
                lat = lat_; 
                lon_interval = new TInterval (MIN_LON, MAX_LON);
                dlon = 10.0; 
                dff = 0.0;
                lon_min_shift = 0.0;
                lon_max_shift = 0;
                lons = new ArrayList<>();
                this.createParallel();
        }
	
        
        public Parallel(final double lat_, final TInterval lon_interval_, final double dlon_, final double lon_min_shift_, final double lon_max_shift_)
        {
                //Create parallel, uniform sampling with the step dlon = 10
                lat = lat_;
                lon_interval = lon_interval_;
                dlon = dlon_;
                dff = 0.0;
                lon_min_shift = lon_min_shift_;
                lon_max_shift = lon_max_shift_;
                lons = new ArrayList<>();
                this.createParallel();
        }
        
        
         public Parallel(final double lat_, final TInterval  lon_interval_, final double dff_, final double lon_min_shift_, final double lon_max_shift_, final Projection proj, final double alpha, 
                List <Point3DCartesian> mer_proj, final double fmax_, final int dmin_, final int dmax_, final double eps_)
        {
                //Create meridian, adaptive sampling with given angular difference between segments 
                lat = lat_;
                lon_interval = lon_interval_;
                dlon = 0.0;
                dff = dff_;
                lon_min_shift = lon_min_shift_;
                lon_max_shift = lon_max_shift_;
                lons = new ArrayList<>();
                this.createParallelAS(proj, alpha, mer_proj, fmax_, dmin_, dmax_, eps_);
        }


	public double getLat() { return lat; }
	public TInterval getLonInterval() { return lon_interval; }
	public double getDLon() { return dlon; }
	public double getLonMinShift() { return lon_min_shift; }
	public double getLonMaxShift() { return lon_max_shift; }
	public List <Double> getLons() { return lons; }
        

        public void createParallel()
        {
                //Create parallel

                //Set start value of the latitude as a multiplier of dlat 
                final double lon_start = Round.roundToMultipleFloor(lon_interval.min_value, dlon) + dlon;
                final double lon_end = Round.roundToMultipleCeil(lon_interval.max_value, dlon) - dlon;

                //Set value lon_min inside [MIN_LON, MAX_LON] (shifted lower bound)
                double lon_lb = max(min(lon_interval.min_value + lon_min_shift, MAX_LON), MIN_LON);

                //Set value lon_min <= lon_interval.max
                lon_lb = min(lon_lb, lon_interval.max_value);
	
                //Add first point (shifted lower bound of the interval)
                lons.add(lon_lb);

                //Add intermediate points
                for (double lon_point = lon_start; lon_point <= lon_end; lon_point += dlon)
                {
                        lons.add(lon_point);
                }

                //Set value lon_max inside [MIN_LON, MAX_LON] (shifted upper bound)
                double lon_ub = min(max(lon_interval.max_value - lon_max_shift, MIN_LON), MAX_LON);
	
                //Set value lon_min >= lon_interval.min
                lon_ub = max(lon_ub, lon_interval.min_value);

                //Add last point (shifted upper bound of the interval)
                lons.add(lon_ub);
        }

        
        public void project(final Projection proj, final double alpha, List <Point3DCartesian> par)
        {
                //Project parallel
                Point3DGeographic pole = proj.getCartPole();

                //Process all meridian points
                for (final double lon : lons)
                {
                        double [] X = {0}, Y = {0}, lat_trans = {0}, lon_trans = {0};
                        
                        //Project point
                        try
                        {
                                CartTransformation.latLonToXY(lat, lon, proj, alpha, lat_trans, lon_trans, X, Y);
                                
                                //Throw exception: X coordinate
                                if (Double.isNaN(X[0]) || Double.isInfinite(X[0]))
                                        throw new MathInvalidArgumentException("MathException: can not project parallel point, ", "1.0 / r, r = 0:", X[0]);
                                
                                //Throw exception: Y coordinate
                                if (Double.isNaN(Y[0]) || Double.isInfinite(Y[0]))
                                        throw new MathInvalidArgumentException("MathException: can not project parallel point, ", "1.0 / r, r = 0:", Y[0]);
                        }

                        //Throw exception: meridian crosses the singularity
                        catch (MathException error)
                        {
                                //Get singularity direction
                                SingularityDetection.checkProjDiscontinuity(lat, lon, proj, alpha, 1.0e9, GRATICULE_LAT_LON_SHIFT);
                        }

                        //Add projected point to the list
                        Point3DCartesian p_proj = new Point3DCartesian(X[0], Y[0], 0.0);
                        par.add(p_proj);
                }
        }
        

        public void print(PrintStream output)
        {
                for (final double lon : lons)
                {
                        output.println( "[" + lat + ", " + lon + "]  ");
                }

                output.println('\n' + '\n');
        }

        
        public void createParallelAS(final Projection proj, final double alpha, List <Point3DCartesian> par_proj, final double fmax, final int dmin, final int dmax, final double eps)
        {
                //Create polygonal approximation of a meridian using adaptive sampling
                //Polygonal representation refers to a given projection and its parameters
                asInit(proj, alpha, par_proj, fmax, dmin, dmax, eps);
        }



        public void asInit(final Projection proj, final double alpha, List <Point3DCartesian> par_proj, final double fmax, final int dmin, final int dmax, final double eps)
        {
                //Create polygonal approximation of the meridian using adaptive sampling
                //Initial phase, call the recursive function
                int d = 0;
                double [] xa = {0.0}, xb = {0.0}, ya = {0.0}, yb = {0.0}, lat_trans_a = {0.0}, lat_trans_b = {0.0}, lon_trans_a = {0.0}, lon_trans_b = {0.0};

                //Get bounds of meridian points
                final double a = lon_interval.min_value;
                final double b = lon_interval.max_value;

                //Check singularity at [a, lon]
                SingularityDetection.checkProjDiscontinuity(lat, a, proj, alpha, fmax, 0.5 * eps);

                //Check singularity at [b, lon]
                SingularityDetection.checkProjDiscontinuity(lat, b, proj, alpha, fmax, 0.5 * eps);

                //Compute image
                CartTransformation.latLonToXY(lat, a, proj, alpha, lat_trans_a, lon_trans_a, xa, ya);
                CartTransformation.latLonToXY(lat, b, proj, alpha, lat_trans_b, lon_trans_b, xb, yb);

                //Add first parallel point a to the list 
                lons.add(a);

                //Add new projected parallel point a
                Point3DCartesian pa = new Point3DCartesian(xa[0], ya[0]);
                par_proj.add(pa);

                //Apply recursive approach
                //asPoints(proj, alpha, par_proj, a, b, xa, ya, xb, yb, d, fmax, dmin, dmax, eps);
                asPoints3(proj, alpha, par_proj, a, b, a, b, xa[0], ya[0], xb[0], yb[0], d, fmax, dmin, dmax, eps);

                //Add last parallel point b to the list
                lons.add(b);

                //Add new projected parallel point a
                Point3DCartesian pb = new Point3DCartesian(xb[0], yb[0]);
                par_proj.add(pb);
        }


        public void asPoints3(final Projection proj, final double alpha, List <Point3DCartesian> par_proj, final double a, final double b, final double ap, final double bn, final double xa, final double ya, final double xb, final double yb, final int d, final double fmax, final int dmin, final int dmax, final double eps)
        {
                //Adaptive sampling of parallel points
                final double r_min = 0.45, r_max = 0.55;
                Random r = new Random();

                //Stop sampling: max recursion depth exceeded
                if (d >= dmax)
                	return;

                //Stop sampling: to narrow interval
                if (b - a < eps)
                        return;

                //Create random numbers in interval [0.45, 0.55]
                final double r0 = r_max;
                final double r1 = r_min + r.nextDouble() * (r_max - r_min);
                final double r2 = r_min + r.nextDouble() * (r_max - r_min);
                final double r3 = r_min + r.nextDouble() * (r_max - r_min);
                final double r4 = r_max;

                //Compute coordinates of meridian points
                final double lon0 = a + r0 * (ap - a) / 2.0;
                final double lon1 = a + r1 * (b - a) / 2.0;
                final double lon2 = a + r2 * (b - a);
                final double lon3 = a + r3 * (b - a) * 3.0 / 2.0;
                final double lon4 = b + r4 * (bn - b) / 2.0;

                //Project these points -> flat criterion needs to be evaluated
                double [] x0 = {0.0}, y0 = {0.0}, x1 = {0.0}, y1 = {0.0}, x2 = {0.0}, y2 = {0.0}, x3 = {0.0}, y3 = {0.0}, x4 = {0.0}, y4 = {0.0};
                double [] lat_trans = {0.0}, lon_trans = {0.0};
                
                //Point [lat, lon0]
                try
                {
                        CartTransformation.latLonToXY(lat, lon0, proj, alpha, lat_trans, lon_trans, x0, y0);
                        
                        //Throw exception: X coordinate
                        if (Double.isNaN(x0[0]) || Double.isInfinite(x0[0]))
                                throw new MathInvalidArgumentException("MathException: can not project parallel point, ", "1.0 / r, r = 0:", x0[0]);

                        //Throw exception: Y coordinate
                        if (Double.isNaN(y0[0]) || Double.isInfinite(y0[0]))
                                throw new MathInvalidArgumentException("MathException: can not project parallel point, ", "1.0 / r, r = 0:", y0[0]);
                }
                
                //Throw exception
                catch (MathException error)
                {
                        //Check singularity at [lat0, lon]
                        SingularityDetection.checkProjDiscontinuity(lat, lon0, proj, alpha, fmax, 0.5 * eps);
                }
                
                //Point [lat, lon1]
                try
                {
                        CartTransformation.latLonToXY(lat, lon1, proj, alpha, lat_trans, lon_trans, x1, y1);
                        
                        //Throw exception: X coordinate
                        if (Double.isNaN(x1[0]) || Double.isInfinite(x1[0]))
                                throw new MathInvalidArgumentException("MathException: can not project parallel point, ", "1.0 / r, r = 0:", x1[0]);

                        //Throw exception: Y coordinate
                        if (Double.isNaN(y1[0]) || Double.isInfinite(y1[0]))
                                throw new MathInvalidArgumentException("MathException: can not project parallel point, ", "1.0 / r, r = 0:", y1[0]);
                }

                //Throw exception
                catch (MathException error)
                {
                        //Check singularity at [lat1, lon]
                        SingularityDetection.checkProjDiscontinuity(lat, lon1, proj, alpha, fmax, 0.5 * eps);
                }
                
                //Point [lat, lon2]
                try
                {
                        CartTransformation.latLonToXY(lat, lon2, proj, alpha, lat_trans, lon_trans, x2, y2);
                        
                        //Throw exception: X coordinate
                        if (Double.isNaN(x2[0]) || Double.isInfinite(x2[0]))
                                throw new MathInvalidArgumentException("MathException: can not project parallel point, ", "1.0 / r, r = 0:", x2[0]);

                        //Throw exception: Y coordinate
                        if (Double.isNaN(y2[0]) || Double.isInfinite(y2[0]))
                                throw new MathInvalidArgumentException("MathException: can not project parallel point, ", "1.0 / r, r = 0:", y2[0]);
                }
                
                //Throw exception
                catch (MathException error)
                {
                        //Check singularity at [lat2, lon]
                        SingularityDetection.checkProjDiscontinuity(lat, lon2, proj, alpha, fmax, 0.5 * eps);
                }
                
                //Point [lat, lon3]
                try
                {
                        CartTransformation.latLonToXY(lat, lon3, proj, alpha, lat_trans, lon_trans, x3, y3);
                        
                        //Throw exception: X coordinate
                        if (Double.isNaN(x3[0]) || Double.isInfinite(x3[0]))
                                throw new MathInvalidArgumentException("MathException: can not project parallel point, ", "1.0 / r, r = 0:", x3[0]);

                        //Throw exception: Y coordinate
                        if (Double.isNaN(y3[0]) || Double.isInfinite(y3[0]))
                                throw new MathInvalidArgumentException("MathException: can not project parallel point, ", "1.0 / r, r = 0:", y3[0]);
                }

                //Throw exception
                catch (MathException error)
                {
                        //Check singularity at [lat3, lon]
                        SingularityDetection.checkProjDiscontinuity(lat, lon3, proj, alpha, fmax, 0.5 * eps);
                }
                    
                //Point [lat, lon4]
                try
                {
                        CartTransformation.latLonToXY(lat, lon4, proj, alpha, lat_trans, lon_trans, x4, y4);
                        
                        //Throw exception: X coordinate
                        if (Double.isNaN(x4[0]) || Double.isInfinite(x4[0]))
                                throw new MathInvalidArgumentException("MathException: can not project parallel point, ", "1.0 / r, r = 0:", x4[0]);

                        //Throw exception: Y coordinate
                        if (Double.isNaN(y4[0]) || Double.isInfinite(y4[0]))
                                throw new MathInvalidArgumentException("MathException: can not project parallel point, ", "1.0 / r, r = 0:", y4[0]);
                }
                
                //Throw exception
                catch (MathException error)
                {
                        //Check singularity at [lat4, lon]
                        SingularityDetection.checkProjDiscontinuity(lat, lon4, proj, alpha, fmax, 0.5 * eps);
                }
                
                //Test flat criteria values for the recursion
                final double alpha0 = abs(lon0 - a) > eps ? abs(Angle3Points.getAngle3Points(x0[0], y0[0], xa, ya, x1[0], y1[0]) - 180.0) : 0.0;
                final double alpha1 = abs(Angle3Points.getAngle3Points(xa, ya, x1[0], y1[0], x2[0], y2[0]) - 180.0);
                final double alpha2 = abs(Angle3Points.getAngle3Points(x1[0], y1[0], x2[0], y2[0], x3[0], y3[0]) - 180.0);
                final double alpha3 = abs(Angle3Points.getAngle3Points(x2[0], y2[0], x3[0], y3[0], xb, yb) - 180.0);
                final double alpha4 = abs(lon4 - b) > eps ? abs(Angle3Points.getAngle3Points(x3[0], y3[0], xb, yb, x4[0], y4[0]) - 180.0) : 0.0;

                //First sub interval
                if (alpha0 > dff || alpha1 > dff || alpha2 > dff || alpha3 > dff || alpha4 > dff || d < dmin)
                {
                        asPoints3(proj, alpha, par_proj, a, lon1, lon0, lon2, xa, ya, x1[0], y1[0], d + 1, fmax, dmin, dmax, eps);
                }
                
                //Add new parallel point p1
                lons.add(lon1);

                //Add new projected parallel point p1
                Point3DCartesian p1 = new Point3DCartesian(x1[0], y1[0]);
                par_proj.add(p1);

                //Second sub interval
                if (alpha0 > dff || alpha1 > dff || alpha2 > dff || alpha3 > dff || alpha4 > dff || d < dmin)
                {
                        asPoints3(proj, alpha, par_proj, lon1, lon2, a, lon3, x1[0], y1[0], x2[0], y2[0], d + 1, fmax, dmin, dmax, eps);
                }
                
                //Add new parallel point p2
                lons.add(lon2);

                //Add new projected parallel point p2
                Point3DCartesian p2 = new Point3DCartesian(x2[0], y2[0]);
                par_proj.add(p2);

                //Third sub interval
                if (alpha0 > dff || alpha1 > dff || alpha2 > dff || alpha3 > dff || alpha4 > dff || d < dmin)
                {
                        asPoints3(proj, alpha, par_proj, lon2, lon3, lon1, b, x2[0], y2[0], x3[0], y3[0], d + 1, fmax, dmin, dmax, eps);
                }
                
                //Add new parallel point p3
                lons.add(lon3);

                //Add new projected parallel point p3
                Point3DCartesian p3 = new Point3DCartesian(x3[0], y3[0]);
                par_proj.add(p3);

                //Fourth sub interval
                if (alpha0 > dff || alpha1 > dff || alpha2 > dff || alpha3 > dff || alpha4 > dff || d < dmin)
                {
                        asPoints3(proj, alpha, par_proj, lon3, b, lon2, lon4, x3[0], y3[0], xb, yb, d + 1, fmax, dmin, dmax, eps);
                }
        }
}
