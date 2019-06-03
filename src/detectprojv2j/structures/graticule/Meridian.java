// Description: A meridian of the longitude given by the list of points

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


import java.util.List;
import static java.lang.Math.*;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Random;

import detectprojv2j.types.TInterval;

import detectprojv2j.structures.point.Point3DCartesian;
import detectprojv2j.structures.projection.Projection;

import static detectprojv2j.consts.Consts.*;

import detectprojv2j.algorithms.angle3points.Angle3Points;
import detectprojv2j.algorithms.carttransformation.CartTransformation;
import detectprojv2j.algorithms.round.Round;
import detectprojv2j.algorithms.singularitydetection.SingularityDetection;

import detectprojv2j.exceptions.MathException;
import detectprojv2j.exceptions.MathInvalidArgumentException;

public class Meridian {
        
	private final double lon;                                             //Longitude of the meridian
	private final TInterval  lat_interval;                                //Latitude interval of the meridian
	private final double dlat;                                            //Latitude step for uniform sampling
        private final double dff;                                             //Flat factor between sampled segments
	private final double lat_min_shift;                                   //Shift of the initial meridian point: [lat_min + lat_min_shift, lon], default = 0
	private final double lat_max_shift;                                   //Shift of the last meridian point: [lat_max - lat_max_shift, lon], default = 0
	private final List <Double> lats;                                     //Latitude of the meridian points

	public Meridian(final double lon_)
        {
                //Create meridian, uniform sampling with the step dlat = 10
                lon = lon_; 
                lat_interval = new TInterval (MIN_LAT, MAX_LAT);
                dlat = 10.0;
                dff = 0.0;
                lat_min_shift = 0.0;
                lat_max_shift = 0;
                lats = new ArrayList<>();
                this.createMeridian();
        }
	
        
        public Meridian(final double lon_, final TInterval  lat_interval_, final double dlat_, final double lat_min_shift_, final double lat_max_shift_)
        {
                //Create meridian, uniform sampling with the step dlat
                lon = lon_;
                lat_interval = lat_interval_;
                dlat = dlat_;
                dff = 0.0;
                lat_min_shift = lat_min_shift_;
                lat_max_shift = lat_max_shift_;
                lats = new ArrayList<>();
                this.createMeridian();
        }
        
        
        public Meridian(final double lon_, final TInterval  lat_interval_, final double dff_, final double lat_min_shift_, final double lat_max_shift_, final Projection proj, final double alpha, 
                List <Point3DCartesian> mer_proj, final double fmax_, final int dmin_, final int dmax_, final double eps_)
        {
                //Create meridian, adaptive sampling with given angular difference between segments 
                lon = lon_;
                lat_interval = lat_interval_;
                dlat = 0.0;
                dff = dff_;
                lat_min_shift = lat_min_shift_;
                lat_max_shift = lat_max_shift_;
                lats = new ArrayList<>();
                this.createMeridianAS(proj, alpha, mer_proj, fmax_, dmin_, dmax_, eps_);
        }

        
	public double getLon() { return lon; }
	public TInterval getLatInterval() { return lat_interval; }
	public double getDLat() { return dlat; }
	public double getLatMinShift() { return lat_min_shift; }
	public double getLatMaxShift() { return lat_max_shift; }
	public List <Double> getLats() { return lats; }
        
        public void createMeridian()
        {
                //Create meridian: uniform sampling

                //Set start value of the latitude as a multiplier of dlat 
                final double lat_start = Round.roundToMultipleFloor(lat_interval.min_value, dlat) + dlat;
                final double lat_end = Round.roundToMultipleCeil(lat_interval.max_value, dlat) - dlat;

                //Set value lat_min inside [MIN_LAT, MAX_LAT] (shifted lower bound)
                double lat_lb = max(min(lat_interval.min_value + lat_min_shift, MAX_LAT), MIN_LAT);

                //Set value lat_min <= lat_interval.max
                lat_lb = min(lat_lb, lat_interval.max_value);

                //Add first point (shifted lower bound of the interval)
                lats.add(lat_lb);

                //Add intermediate points
                for (double lat_point = lat_start; lat_point <= lat_end; lat_point += dlat)
                {
                        lats.add(lat_point);
                }

                //Set value lat_min inside [MIN_LAT, MAX_LAT] (shifted upper bound)
                double lat_ub = min(max(lat_interval.max_value - lat_max_shift, MIN_LAT), MAX_LAT);

                //Set value lat_max >= lat_interval.min
                lat_ub = max(lat_ub, lat_interval.min_value);

                //Add last point (shifted upper bound of the interval)
                lats.add(lat_ub);
        }
        
        
        public void project(final Projection proj, final double alpha, List <Point3DCartesian> mer)
        {
                //Project meridian
                for (final double lat : lats)
                {
                        double [] X = {0.0}, Y = {0.0}, lat_trans = {0.0}, lon_trans = {0.0};
                        
                        //Project point
                        try
                        {
                                CartTransformation.latLonToXY(lat, lon, proj, alpha, lat_trans, lon_trans, X, Y);
                                
                                //Throw exception: X coordinate
                                if (Double.isNaN(X[0]) || Double.isInfinite(X[0]))
                                        throw new MathInvalidArgumentException("MathException: can not project meridian point, ", "1.0 / r, r = 0:", X[0]);
                                
                                //Throw exception: Y coordinate
                                if (Double.isNaN(Y[0]) || Double.isInfinite(Y[0]))
                                        throw new MathInvalidArgumentException("MathException: can not project meridian point, ", "1.0 / r, r = 0:", Y[0]);

                        }

                        //Throw exception: meridian crosses the singularity
                        catch (MathException error)
                        {
                                //Get singularity direction
                                SingularityDetection.checkProjDiscontinuity(lat, lon, proj, alpha, 1.0e9, GRATICULE_LAT_LON_SHIFT);
                        }

                        //Add projected point to the list
                        Point3DCartesian p_proj = new Point3DCartesian(X[0], Y[0], 0.0);
                        mer.add(p_proj);
                }
        }
        

        public void print(PrintStream output)
        {
                for (final double lat : lats)
                {
                        output.println( "[" + lat + ", " + lon + "]  ");
                }

                output.println('\n' + '\n');
        }

        
        public void createMeridianAS(final Projection proj, final double alpha, List <Point3DCartesian> mer_proj, final double fmax, final int dmin, final int dmax, final double eps )
        {
                //Create polygonal approximation of a meridian using adaptive sampling
                //Polygonal representation refers to a given projection and its parameters
                asInit(proj, alpha, mer_proj, fmax, dmin, dmax, eps);
        }
        
        
        public void asInit(final Projection proj, final double alpha, List <Point3DCartesian> mer_proj, final double fmax, final int dmin, final int dmax, final double eps)
        {
                //Create polygonal approximation of the meridian using adaptive sampling
                //Initial phase, call the recursive function
                int d = 0;
                double [] xa = {0.0}, ya = {0.0}, xb = {0.0}, yb = {0.0}, lat_trans_a = {0.0}, lon_trans_a = {0.0}, lat_trans_b = {0.0}, lon_trans_b = {0.0};
                
                //Get bounds of meridian points
                final double a = lat_interval.min_value;
                final double b = lat_interval.max_value;

                //Check singularity at [a, lon]
                SingularityDetection.checkProjDiscontinuity(a, lon, proj, alpha, fmax, 0.5 * eps);

                //Check singularity at [b, lon]
                SingularityDetection.checkProjDiscontinuity(b, lon, proj, alpha, fmax, 0.5 * eps);

                //Compute image of a point
                CartTransformation.latLonToXY(a, lon, proj, alpha, lat_trans_a, lon_trans_a, xa, ya);
                CartTransformation.latLonToXY(b, lon, proj, alpha, lat_trans_b, lon_trans_b, xb, yb);

                //Add first meridian point a to the list 
                lats.add(a);

                //Add new projected meridian point a
                Point3DCartesian pa = new Point3DCartesian(xa[0], ya[0]);
                mer_proj.add(pa);

                //Apply recursive approach
                asPoints3(proj, alpha, mer_proj, a, b, a, b, xa[0], ya[0], xb[0], yb[0], d, fmax, dmin, dmax, eps);

                //Add last meridian point b to the list
                lats.add(b);

                //Add new projected meridian point b
                Point3DCartesian pb = new Point3DCartesian(xb[0], yb[0]);
                mer_proj.add(pb);
        }
        
        
        public void asPoints3(final Projection proj, final double alpha, List <Point3DCartesian> mer_proj, final double a, final double b, final double ap, final double bn, final double xa, final double ya, final double xb, final double yb, final int d, final double fmax, final int dmin, final int dmax, final double eps)
        {
                //Adaptive sampling of meridian points
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
                final double lat0 = a + r0 * (ap - a) / 2.0;
                final double lat1 = a + r1 * (b - a) / 2.0;
                final double lat2 = a + r2 * (b - a);
                final double lat3 = a + r3 * (b - a) * 3.0 / 2.0;
                final double lat4 = b + r4 * (bn - b) / 2.0;

                //Project these points -> flat criterion needs to be evaluated
                double [] x0 = {0.0}, y0 = {0.0}, x1 = {0.0}, y1 = {0.0}, x2 = {0.0}, y2 = {0.0}, x3 = {0.0}, y3 = {0.0}, x4 = {0.0}, y4 = {0.0};
                double [] lat_trans = {0.0}, lon_trans = {0.0};

                //Point [lat0, lon]
                try
                {
                        CartTransformation.latLonToXY(lat0, lon, proj, alpha, lat_trans, lon_trans, x0, y0);
                        
                        //Throw exception: X coordinate
                        if (Double.isNaN(x0[0]) || Double.isInfinite(x0[0]))
                                throw new MathInvalidArgumentException("MathException: can not project meridian point, ", "1.0 / r, r = 0:", x0[0]);

                        //Throw exception: Y coordinate
                        if (Double.isNaN(y0[0]) || Double.isInfinite(y0[0]))
                                throw new MathInvalidArgumentException("MathException: can not project meridian point, ", "1.0 / r, r = 0:", y0[0]);
                }
                
                //Throw exception
                catch (MathException error)
                {
                        //Check singularity at [lat0, lon]
                        SingularityDetection.checkProjDiscontinuity(lat0, lon, proj, alpha, fmax, 0.5 * eps);
                }
                
                //Point [lat1, lon]
                try
                {
                        CartTransformation.latLonToXY(lat1, lon, proj, alpha, lat_trans, lon_trans, x1, y1);
                        
                        //Throw exception: X coordinate
                        if (Double.isNaN(x1[0]) || Double.isInfinite(x1[0]))
                                throw new MathInvalidArgumentException("MathException: can not project meridian point, ", "1.0 / r, r = 0:", x1[0]);

                        //Throw exception: Y coordinate
                        if (Double.isNaN(y1[0]) || Double.isInfinite(y1[0]))
                                throw new MathInvalidArgumentException("MathException: can not project meridian point, ", "1.0 / r, r = 0:", y1[0]);
                }
                
                //Throw exception
                catch (MathException error)
                {
                        //Check singularity at [lat1, lon]
                        SingularityDetection.checkProjDiscontinuity(lat1, lon, proj, alpha, fmax, 0.5 * eps);
                }
                
                //Point [lat2, lon]
                try
                {
                       CartTransformation.latLonToXY(lat2, lon, proj, alpha, lat_trans, lon_trans, x2, y2);
                       
                       //Throw exception: X coordinate
                       if (Double.isNaN(x2[0]) || Double.isInfinite(x2[0]))
                                throw new MathInvalidArgumentException("MathException: can not project meridian point, ", "1.0 / r, r = 0:", x2[0]);

                       //Throw exception: Y coordinate
                       if (Double.isNaN(y2[0]) || Double.isInfinite(y2[0]))
                                throw new MathInvalidArgumentException("MathException: can not project meridian point, ", "1.0 / r, r = 0:", y2[0]);
                }
                
                //Throw exception
                catch (MathException error)
                {
                        //Check singularity at [lat2, lon]
                        SingularityDetection.checkProjDiscontinuity(lat2, lon, proj, alpha, fmax, 0.5 * eps);
                }
                  
                //Point [lat3, lon]
                try
                {
                        CartTransformation.latLonToXY(lat3, lon, proj, alpha, lat_trans, lon_trans, x3, y3);
                        
                        //Throw exception: X coordinate
                        if (Double.isNaN(x3[0]) || Double.isInfinite(x3[0]))
                                throw new MathInvalidArgumentException("MathException: can not project meridian point, ", "1.0 / r, r = 0:", x3[0]);

                        //Throw exception: Y coordinate
                        if (Double.isNaN(y3[0]) || Double.isInfinite(y3[0]))
                                throw new MathInvalidArgumentException("MathException: can not project meridian point, ", "1.0 / r, r = 0:", y3[0]);
                }
                
                //Throw exception
                catch (MathException error)
                {
                        //Check singularity at [lat3, lon]
                        SingularityDetection.checkProjDiscontinuity(lat3, lon, proj, alpha, fmax, 0.5 * eps);
                }
                 
                //Point [lat4, lon]
                try
                {
                        CartTransformation.latLonToXY(lat4, lon, proj, alpha, lat_trans, lon_trans, x4, y4);
                        
                        //Throw exception: X coordinate
                        if (Double.isNaN(x4[0]) || Double.isInfinite(x4[0]))
                                throw new MathInvalidArgumentException("MathException: can not project meridian point, ", "1.0 / r, r = 0:", x4[0]);

                        //Throw exception: Y coordinate
                        if (Double.isNaN(y4[0]) || Double.isInfinite(y4[0]))
                                throw new MathInvalidArgumentException("MathException: can not project meridian point, ", "1.0 / r, r = 0:", y4[0]);
                }
                
                //Throw exception
                catch (MathException error)
                {
                        //Check singularity at [lat4, lon]
                        SingularityDetection.checkProjDiscontinuity(lat4, lon, proj, alpha, fmax, 0.5 * eps);
                }

                //Test flat criteria values for the recursion
                final double alpha0 = abs(lat0 - a) > eps ? abs(Angle3Points.getAngle3Points(x0[0], y0[0], xa, ya, x1[0], y1[0]) - 180.0) : 0.0;
                final double alpha1 = abs(Angle3Points.getAngle3Points(xa, ya, x1[0], y1[0], x2[0], y2[0]) - 180.0);
                final double alpha2 = abs(Angle3Points.getAngle3Points(x1[0], y1[0], x2[0], y2[0], x3[0], y3[0]) - 180.0);
                final double alpha3 = abs(Angle3Points.getAngle3Points(x2[0], y2[0], x3[0], y3[0], xb, yb) - 180.0);
                final double alpha4 = abs(lat4 - b) > eps ? abs(Angle3Points.getAngle3Points(x3[0], y3[0], xb, yb, x4[0], y4[0]) - 180.0) : 0.0;

                //First sub interval
                if (alpha0 > dff || alpha1 > dff || alpha2 > dff || alpha3 > dff || alpha4 > dff || d < dmin)
                {
                        asPoints3(proj, alpha, mer_proj, a, lat1, lat0, lat2, xa, ya, x1[0], y1[0], d + 1, fmax, dmin, dmax, eps);
                }

                //Add new meridian point p1
                lats.add(lat1);

                //Add new projected meridian point p1
                Point3DCartesian p1 = new Point3DCartesian(x1[0], y1[0]);
                mer_proj.add(p1);

                //Second sub interval
                if (alpha0 > dff || alpha1 > dff || alpha2 > dff || alpha3 > dff || alpha4 > dff || d < dmin)
                {
                        asPoints3(proj, alpha, mer_proj, lat1, lat2, a, lat3, x1[0], y1[0], x2[0], y2[0], d + 1, fmax, dmin, dmax, eps);
                }

                //Add new meridian point p2
                lats.add(lat2);

                //Add new projected meridian point p2
                Point3DCartesian p2 = new Point3DCartesian(x2[0], y2[0]);
                mer_proj.add(p2);

                //Third sub interval
                if (alpha0 > dff || alpha1 > dff || alpha2 > dff || alpha3 > dff || alpha4 > dff || d < dmin)
                {
                        asPoints3(proj, alpha, mer_proj, lat2, lat3, lat1, b, x2[0], y2[0], x3[0], y3[0], d + 1, fmax, dmin, dmax, eps);
                }

                //Add new meridian point p3
                lats.add(lat3);

                //Add new projected meridian point p3
                Point3DCartesian p3 = new Point3DCartesian(x3[0], y3[0]);
                mer_proj.add(p3);

                //Fourth sub interval
                if (alpha0 > dff || alpha1 > dff || alpha2 > dff || alpha3 > dff || alpha4 > dff || d < dmin)
                {
                        asPoints3(proj, alpha, mer_proj, lat3, b, lat2, lat4, x3[0], y3[0], xb, yb, d + 1, fmax, dmin, dmax, eps);
                }
        }
}
