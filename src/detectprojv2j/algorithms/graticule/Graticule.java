// Description: Create projection graticule given by the lat/lon intervals

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


package detectprojv2j.algorithms.graticule;

import static java.lang.Math.*;
import java.util.ArrayList;
import java.util.List;

import detectprojv2j.types.TInterval;
import detectprojv2j.types.TTransformedLongitudeDirection;

import detectprojv2j.structures.graticule.Meridian;
import detectprojv2j.structures.graticule.Parallel;
import detectprojv2j.structures.point.Point3DCartesian;
import detectprojv2j.structures.point.Point3DGeographic;
import detectprojv2j.structures.projection.Projection;

import static detectprojv2j.consts.Consts.*;

import detectprojv2j.exceptions.MathLatSingularityException;
import detectprojv2j.exceptions.MathLonSingularityException;

import detectprojv2j.algorithms.carttransformation.CartTransformation;
import detectprojv2j.algorithms.greatcircleintersection.GreatCircleIntersection;
import detectprojv2j.algorithms.round.Round;
import detectprojv2j.exceptions.MathSingularityException;
import detectprojv2j.types.TGraticuleSampling;
import detectprojv2j.types.TInterval2D;
import java.util.Stack;


public class Graticule {
  
        private final double dlat;				//Uniform sampling, sampling step
	private final double dlon;				//Uniform sampling, sampling step
	private final double dff;				//Adaptive sampling, flat factor
	private final int dmin;                                 //Minimum recursion depth, after the uniform sampling transforms into adaptive
	private final int dmax;                                 //Maximum recursion depth, adaptive sampling

	
        public Graticule(final double dlat_, final double dlon_, final double dff_, final int dmin_, final int dmax_)
        {
                dlat = dlat_;
                dlon = dlon_;
                dff = dff_;
                dmin = dmin_;
                dmax = dmax_;
        }

        
        public void createGraticule ( final Projection proj, final TInterval lat_extent, final TInterval lon_extent, final double lat_step, final double lon_step, final double alpha, List <Meridian> meridians, List<List <Point3DCartesian >> meridians_proj, List <Parallel > parallels, List<List <Point3DCartesian >> parallels_proj, final TGraticuleSampling sampling_method, final int max_splits, final double fmax, final double eps)
        {
                //Compute graticule given by meridians and parallels
                Stack <TInterval2D> S = new Stack<>();
                
                final double latp = proj.getCartPole().getLat();
                final double lonp = proj.getCartPole().getLon();
                final double lon0 = proj.getLon0();

                //Get size of the interval
                final double lat_interval_width = lat_extent.max_value - lat_extent.min_value;
                final double lon_interval_width = lon_extent.max_value - lon_extent.min_value;

                //Generate meridians and parallels
                if ( lat_step <= lat_interval_width || lon_step <= lon_interval_width )
                {
                        //Create lat interval
                        TInterval lat_interval = new TInterval(lat_extent.min_value,lat_extent.max_value);
                        lat_interval.min_value = max(lat_extent.min_value, MIN_LAT + eps);
                        lat_interval.max_value = min(lat_extent.max_value, MAX_LAT - eps);

                        //Create lon interval
                        TInterval lon_interval = new TInterval(lon_extent.min_value,lon_extent.max_value);
                        lon_interval.min_value = max(lon_extent.min_value, MIN_LON + eps);
                        lon_interval.max_value = min(lon_extent.max_value, MAX_LON - eps);

                        //Create 2D interval
                        TInterval2D lat_lon_interval = new TInterval2D(lat_interval, lon_interval);
                        S.push(lat_lon_interval);

                        //Process all meridians and parallels: automatic detection of additional singular points
                        //Process until the stack S is empty (recursive approach)
                        int [] split_amount = {0};
                       
                        while (!S.empty())
                        {
                                //Get the current lat/lon interval on the top of S
                                TInterval2D interval = S.pop();
			
                                //System.out.println("[" + interval.i1.min_value + "," + interval.i1.max_value + "]");
                                //System.out.println("[" + interval.i2.min_value + "," + interval.i2.max_value + "]\n"); 
                          
                                //Create temporary containers for the meridians/parallels
                                List <Meridian> meridians_temp = new ArrayList<>();
                                List <Parallel> parallels_temp = new ArrayList<>();
                                List <List<Point3DCartesian>> meridians_temp_proj = new ArrayList<>();
                                List <List<Point3DCartesian>> parallels_temp_proj = new ArrayList<>();

                                //Create intervals to treat the singularity
                                short [] k = {0};
                                TInterval2D int1 = new TInterval2D(interval.i1, interval.i2);
                                TInterval2D int2 = new TInterval2D(interval.i1, interval.i2);
                                
                                //Try to create meridians and parallels
                                try
                                {        
                                        try
                                        {
                                                //Create meridians and parallels
                                                createMeridians (proj, interval.i1, interval.i2, lon_step, alpha, meridians_temp, meridians_temp_proj, sampling_method, fmax, eps);
                                                createParallels (proj, interval.i1, interval.i2, lat_step, alpha, parallels_temp, parallels_temp_proj, sampling_method, fmax, eps);

                                                //Copy temporary meridians and parallels to the output data structure
                                                for (Meridian m:meridians_temp) meridians.add(m);
                                                for (Parallel p:parallels_temp) parallels.add(p);

                                                //Copy temporary projected meridians and parallels to the output data structure
                                                for (List<Point3DCartesian> m:meridians_temp_proj) meridians_proj.add(m);
                                                for (List<Point3DCartesian> p:parallels_temp_proj) parallels_proj.add(p);
                                        }

                                        //Exception: singularity in the latitude direction
                                        catch (MathLatSingularityException error)
                                        {
                                                //Get latitude singularity
                                                final double lat_error = error.getArg();

                                                //Too many splits, projection is suspected, stop graticule construction
                                                if (split_amount[0] < max_splits)
                                                        processInterval(interval.i1, lat_error, int1.i1, int2.i1, k, split_amount, eps);
                                                
                                                throw error;
                                        }

                                        //Exception, singularity in the longitude direction
                                        catch (MathLonSingularityException error)
                                        {
                                                //Get longitude singularity
                                                final double lon_error = error.getArg();

                                                //Process lon interval
                                                if (split_amount[0] < max_splits)
                                                        processInterval(interval.i2, lon_error, int1.i2, int2.i2, k, split_amount, eps);
                                                
                                                throw error;
                                        }
                     
                                        //Other than math error: error in equation or in parser
                                        catch (Exception error)
                                        {
                                                //Clear meridians and parallels
                                                meridians.clear(); parallels.clear();
                                                return;
                                        }
                                }
                                
                                //Math singularity exception
                                catch (MathSingularityException error)
                                {
                                        //Too many splits, projection is suspected, stop graticule construction
                                        if (split_amount[0] > max_splits)
                                        {
                                                meridians.clear(); parallels.clear();
                                                meridians_proj.clear(); parallels_proj.clear();
                                                return;
                                        }

                                        //Add intervals
                                        else
                                        {
                                                //Add first interval
                                                if (k[0] > 0)
                                                        S.add(int1);

                                                //Add second interval
                                                if (k[0] > 1)
                                                        S.add(int2);
                                        }
                                }
                        }
                }
        }

        
        public void processInterval(final TInterval i, final double c, TInterval i1, TInterval i2, short []k, int [] split_amount, final double eps)
        {
                //Assign values: amount of destination intervals
                k[0] = 0;
                i1.min_value = i.min_value;
                i1.max_value = i.max_value;
                i2.min_value = i.min_value;
                i2.max_value = i.max_value;

                //Resize or split a given interval
                //Empty interval, delete
                if (abs(i.max_value - i.min_value) < eps)
                {
                        return;
                }

                //Incorrect interval, continue
                else if (i.min_value > i.max_value)
                {
                        return;
                }

                //Singularity is lower bound: shift lower bound
                else if ((i.min_value <= c) && (abs(c - i.min_value) <= eps))
                {
                        i1.min_value += eps;
                        k[0]++;
                }

                //Singularity is upper bound: shift upper bound
                else if ((i.max_value >= c) && (abs(c - i.max_value) <= eps))
                {
                        i1.max_value -= eps;
                        k[0]++;
                }

                //Singularity inside the interval: split intervals
                else if ((i.min_value < c) && (i.max_value > c) &&
                        (abs(c - i.min_value) > eps) && (abs(c - i.max_value) > eps))
                {
                        //Process first interval
                        i1.max_value = c - eps;

                        //Process second interval
                        i2.min_value = c + eps;

                        //Increment split amount
                        split_amount[0]++;
                        k[0] += 2;
                }
        }
        
        
        public void createMeridians (final Projection proj, final TInterval lat_interval, final TInterval lon_interval, final double lon_step, 
                final double alpha, List <Meridian > meridians, List <List<Point3DCartesian>> meridians_proj, final TGraticuleSampling sampling_method, final double fmax, final double eps)
        {
                //Set start value of the longitude as a multiplier of lon_step 
                final double lon_start = Round.roundToMultipleFloor(lon_interval.min_value, lon_step) + lon_step;
                final double lon_end = Round.roundToMultipleCeil(lon_interval.max_value, lon_step) - lon_step;
                
                //Set value lon inside [MIN_LAT, MAX_LAT] (shifted lower bound)
        	double lon_lb = max(min(lon_interval.min_value, MAX_LON), MIN_LON);

                //Set value lon <= lon_max
                lon_lb = min(lon_lb, lon_interval.max_value);
        
                //Create first meridian (lower bound of the interval, not lower than MIN_LON)
                //Split the interval, if a meridian is intersected by the prime meridian of the transformed system [lat_trans, lon_trans]
                createMeridianFragment(proj, lon_lb, lat_interval, alpha, meridians, meridians_proj, sampling_method, fmax, eps);

                //Create intermediate meridians
                //Split the interval, if a meridian is intersected by the prime meridian of the transformed system [lat_trans, lon_trans]
                for (double lon = lon_start; lon <= lon_end; lon += lon_step)
                        createMeridianFragment(proj, lon, lat_interval, alpha, meridians, meridians_proj, sampling_method, fmax, eps);

                //Set value lat inside [MIN_LAT, MAX_LAT] (shifted upper bound)
                double lon_ub = min(max(lon_interval.max_value - eps, MIN_LON), MAX_LON);

                //Set value lon >= lon_interval.min
                lon_ub = max(lon_ub, lon_interval.min_value);

                //Create last meridian (upper bound of the interval, not higher than MAX_LON)
                //Split the interval, if a meridian is intersected by the prime meridian of the transformed system [lat_trans, lon_trans]
                createMeridianFragment(proj, lon_ub, lat_interval, alpha, meridians, meridians_proj, sampling_method, fmax, eps);

                //Create meridian intersecting the pole of the transformed system [lat_trans, lon_trans]
                double lon = proj.getCartPole().getLon();
                if ((lon > lon_interval.min_value) && (lon < lon_interval.max_value))
                {
                        createMeridianFragment(proj, min(max(lon - eps, MIN_LON), MAX_LON), lat_interval, alpha, meridians, meridians_proj, sampling_method, fmax, eps);
                        createMeridianFragment(proj, max(min(lon + eps, MAX_LON), MIN_LON), lat_interval, alpha, meridians, meridians_proj, sampling_method, fmax, eps);
                }

                //Create meridian opposite the pole of the transformed system [lat_trans, lon_trans]
                lon = (lon > 0 ? lon - 180 : lon + 180);
                if ((lon > lon_interval.min_value) && (lon < lon_interval.max_value))
                {
                        createMeridianFragment(proj, min(max(lon - GRATICULE_LAT_LON_SHIFT, MIN_LON), MAX_LON), lat_interval, alpha, meridians, meridians_proj, sampling_method, fmax, eps);
                        createMeridianFragment(proj, max(min(lon + GRATICULE_LAT_LON_SHIFT, MAX_LON), MIN_LON), lat_interval, alpha, meridians, meridians_proj, sampling_method, fmax, eps);
                }      
        }


        public void createParallels ( final Projection proj, final TInterval lat_interval, final TInterval lon_interval, final double lat_step, final double alpha, 
                List <Parallel > parallels, List <List<Point3DCartesian>> parallels_proj, final TGraticuleSampling sampling_method, final double fmax, final double eps)
        {
                //Set start value of the longitude as a multiplier of lon_step 
                final double lat_start = Round.roundToMultipleFloor(lat_interval.min_value, lat_step) + lat_step;
                final double lat_end = Round.roundToMultipleCeil(lat_interval.max_value, lat_step) - lat_step;
                
                //Set value lat inside [MIN_LAT, MAX_LAT] (shifted lower bound)
                double lat_lb = max(min(lat_interval.min_value, MAX_LAT), MIN_LAT);

                //Set value lat <= lat_max
                lat_lb = min(lat_lb, lat_interval.max_value);
               
                //Create first parallel (lower bound of the interval, nor lower than MIN_LAT)
                //Split the interval, if a parallel is intersected by the prime meridian of the transformed system [lat_trans, lon_trans]
                createParallelFragment(proj, lat_lb, lon_interval, alpha, parallels, parallels_proj, sampling_method, fmax, eps);

                //Create intermediate parallels
                //Split the interval, if a parallel is intersected by the prime meridian of the transformed system [lat_trans, lon_trans]
                for (double lat = lat_start; lat <= lat_end; lat += lat_step)
                        createParallelFragment(proj, lat, lon_interval, alpha, parallels, parallels_proj, sampling_method, fmax, eps);

                //Set value lat inside [MIN_LAT, MAX_LAT] (shifted upper bound)
                double lat_ub = min(max(lat_interval.max_value, MIN_LAT), MAX_LAT);

                //Set value lat >= lat_interval.min
                lat_ub = max(lat_ub, lat_interval.min_value);

                //Create last parallel (upper bound of the interval, not greater than MAX_LAT)
                //Split the interval, if a parallel is intersected by the prime meridian of the transformed system [lat_trans, lon_trans]
                createParallelFragment(proj, lat_ub, lon_interval, alpha, parallels, parallels_proj, sampling_method, fmax, eps);
        } 
        
        
        public void createMeridianFragment(final Projection proj, final double lon, final TInterval lat_interval, final double alpha, List <Meridian > meridians, List <List<Point3DCartesian>> meridians_proj, final TGraticuleSampling sampling_method, final double fmax, final double eps)
        {
                //Create meridian fragment given by the latitude interval
                //A meridian is interrupted at its intersection with the prime meridian of the transfomed system [lat_trans, lon_trans]
                for (TInterval lat_interval_split : splitLatInterval(lat_interval, lon, proj.getCartPole(), proj.getLonDir(), proj.getLon0(), eps))
                {
                        Meridian mer = null;
                        List <Point3DCartesian> mer_proj = new ArrayList<>();
                        
                        //Uniform sampling
                        if (sampling_method == TGraticuleSampling.UniformSampling)
                        {
                                Meridian mer_u = new Meridian(lon, lat_interval_split, dlat, 0.0, 0.0);
                                mer_u.project(proj, alpha, mer_proj);
                                mer = mer_u;
                        }

                        //Adaptive sampling
                        else
                        {
                                Meridian mer_a = new Meridian(lon, lat_interval_split, dff, 0.0, 0.0, proj, alpha, mer_proj, fmax, dmin, dmax, eps);
                                mer = mer_a;
                        }

                        //Add meridian and projected meridian to the list
                        meridians.add(mer);
                        meridians_proj.add(mer_proj);
                }
        }


        public void createParallelFragment(final Projection proj, final double lat, final TInterval lon_interval, final double alpha, List <Parallel > parallels, List <List<Point3DCartesian>> parallels_proj, final TGraticuleSampling sampling_method, final double fmax, final double eps)
        {
                //Create parallel fragment given by the longitude interval [lon_minm lon_max]
                //A parallel is interrupted at its intersection with the prime meridian of the transformed system [lat_trans, lon_trans]
                for (TInterval lon_interval_split : splitLonInterval(lon_interval, lat, proj.getCartPole(), proj.getLonDir(), proj.getLon0(), eps))
                {
                        Parallel par = null;
                        List <Point3DCartesian> par_proj = new ArrayList<>();
                        
                        //Uniform sampling
                        if (sampling_method == TGraticuleSampling.UniformSampling)
                        {
                                Parallel par_u = new Parallel(lat, lon_interval_split, dlon, 0.0, 0.0);
                                par_u.project(proj, alpha, par_proj);
                                par = par_u;
                        }

                        //Adaptive sampling
                        else
                        {
                                Parallel par_a = new Parallel(lat, lon_interval_split, dff, 0.0, 0.0, proj, alpha, par_proj, fmax, dmin, dmax, eps);
                                par = par_a;
                        }

                        //Add parallel and projected parallel to the list
                        parallels.add(par);
                        parallels_proj.add(par_proj);
                }
        }

        
        public List<TInterval> splitLatInterval(final TInterval lat_interval, final double lon, final Point3DGeographic pole, final TTransformedLongitudeDirection lon_dir, final double lon0, final double eps)
        {
                //Split the meridian interval [lat_min, lat_max] at the intersection with the prime meridian of the transformed system [lat_trans, lon_trans] shifted by lon0
                //to two subintervals I1=[lat_min, lat_inters], I2=[lat_inters, lat_max]
                List <TInterval> lats_split = new ArrayList<>();
                
                final Point3DGeographic p1 = new Point3DGeographic(MIN_LAT + 1.0, lon);
                final Point3DGeographic p2 = new Point3DGeographic(1.0, lon);
                final Point3DGeographic p3 = new Point3DGeographic(MAX_LAT - 1.0, lon);

                //Sample central meridian of the transformed system in the oblique aspect
                //Transform it to the normal aspect
                final double lat4 = CartTransformation.latTransToLat(MIN_LAT + 1.0, lon0, pole.getLat(), pole.getLon(), lon_dir);
                final double lon4 = CartTransformation.lonTransToLon(MIN_LAT + 1.0, lon0, pole.getLat(), pole.getLon(), lon_dir);

                //P5: (lat_trans, lon_trans) -> (lat, lon)
                final double lat5 = CartTransformation.latTransToLat(1.0, lon0, pole.getLat(), pole.getLon(), lon_dir);
                final double lon5 = CartTransformation.lonTransToLon(1.0, lon0, pole.getLat(), pole.getLon(), lon_dir);

                //P6: (lat_trans, lon_trans) -> (lat, lon)
                final double lat6 = CartTransformation.latTransToLat(MAX_LAT - 1.0, lon0, pole.getLat(), pole.getLon(), lon_dir);
                final double lon6 = CartTransformation.lonTransToLon(MAX_LAT - 1.0, lon0, pole.getLat(), pole.getLon(), lon_dir);

                //Create sampled points
                final Point3DGeographic p4 = new Point3DGeographic(lat4, lon4);
                final Point3DGeographic p5 = new Point3DGeographic(lat5, lon5);
                final Point3DGeographic p6 = new Point3DGeographic(lat6, lon6);

                //Intersection of both great circles
                Point3DGeographic [] i1 = {new Point3DGeographic(0,0)}, i2 = {new Point3DGeographic(0,0)};
                boolean intersection_exists = GreatCircleIntersection.getGreatCirclePlainIntersection(p1, p2, p3, p4, p5, p6, i1, i2, pole, lon_dir);

                //Is there any intersection ?
                if (intersection_exists)
                {
                        //Get both intersections
                        final double lat_inters1 = i1[0].getLat();
                        final double lat_inters2 = i2[0].getLat();
                        final double lon_inters1 = i1[0].getLon();
                        final double lon_inters2 = i2[0].getLon();

                        //Is an intersection point i1 latitude inside the lat interval? Create 2 intervals.
                        if ((lat_inters1 > lat_interval.min_value) && (lat_inters1 < lat_interval.max_value) && (abs(lon_inters1 - lon) < ANGLE_ROUND_ERROR))
                        {
                                lats_split.add(new TInterval (lat_interval.min_value, lat_inters1 - eps));
                                lats_split.add(new TInterval (lat_inters1 + eps, lat_interval.max_value));

                                return lats_split;
                        }

                        //Is an intersection point i1 latitude inside the lat interval? Create 2 intervals.
                        else if ((lat_inters2 > lat_interval.min_value) && (lat_inters2 < lat_interval.max_value) && (abs(lon - lon_inters2) < ANGLE_ROUND_ERROR))
                        {		
                                lats_split.add(new TInterval ( lat_interval.min_value, lat_inters2 - eps));
                                lats_split.add(new TInterval ( lat_inters2 + eps, lat_interval.max_value));

                                return lats_split;
                        }
                }

                //No intersection, no split
                lats_split.add(new TInterval (lat_interval.min_value, lat_interval.max_value));

                return lats_split;
        }


        public List<TInterval> splitLonInterval(final TInterval lon_interval, final double lat, final Point3DGeographic pole, final TTransformedLongitudeDirection lon_dir, final double lon0, final double eps)
        {
                //Split the parallel interval [lon_min, lon_max] at the intersection with the prime meridian of the transformed system [lat_trans, lon_trans] shifted by lon0
                //to three subintervals I3=[lon_min, lon_inters1], I4=[lon_inters1, lon_inters2], I5=[lon_inters2, lon_max]
                List <TInterval> lons_split = new ArrayList<>();

                final Point3DGeographic p1 = new Point3DGeographic(lat, MIN_LON + 1.0);
                final Point3DGeographic p2 = new Point3DGeographic(lat, 1.0);
                final Point3DGeographic p3 = new Point3DGeographic(lat, MAX_LON - 1.0);

                //Sample central meridian of the transformed system in the oblique aspect
                //Transform it to the normal aspect
                final double lat4 = CartTransformation.latTransToLat(MIN_LAT + 1.0, lon0, pole.getLat(), pole.getLon(), lon_dir);
                final double lon4 = CartTransformation.lonTransToLon(MIN_LAT + 1.0, lon0, pole.getLat(), pole.getLon(), lon_dir);

                //P5: (lat_trans, lon_trans) -> (lat, lon)
                final double lat5 = CartTransformation.latTransToLat(1.0, lon0, pole.getLat(), pole.getLon(), lon_dir);
                final double lon5 = CartTransformation.lonTransToLon(1.0, lon0, pole.getLat(), pole.getLon(), lon_dir);

                //P6: (lat_trans, lon_trans) -> (lat, lon)
                final double lat6 = CartTransformation.latTransToLat(MAX_LAT - 1.0, lon0, pole.getLat(), pole.getLon(), lon_dir);
                final double lon6 = CartTransformation.lonTransToLon(MAX_LAT - 1.0, lon0, pole.getLat(), pole.getLon(), lon_dir);

                //Create sampled points
                final Point3DGeographic p4 = new Point3DGeographic(lat4, lon4);
                final Point3DGeographic p5 = new Point3DGeographic(lat5, lon5);
                final Point3DGeographic p6 = new Point3DGeographic(lat6, lon6);

                //Intersection of both great circles
                Point3DGeographic [] i1 = {new Point3DGeographic(0,0)}, i2 = {new Point3DGeographic(0,0)};
                boolean intersection_exists = GreatCircleIntersection.getGreatCirclePlainIntersection(p1, p2, p3, p4, p5, p6, i1, i2, pole, lon_dir);

                //Is there any intersection ?
                if (intersection_exists)
                {
                        //Sort both intersections according to lon
                        final double lon_inters1 = min(i1[0].getLon(), i2[0].getLon());
                        final double lon_inters2 = max(i1[0].getLon(), i2[0].getLon());

                        //Both intersections inside the lon interval? Create 3 intervals.
                        if (((lon_inters1 > lon_interval.min_value) && (lon_inters1 < lon_interval.max_value)) &&
			((lon_inters2 > lon_interval.min_value) && (lon_inters2 < lon_interval.max_value)) && (abs(lon_inters2 - lon_inters1) > ANGLE_ROUND_ERROR))
                        {
                                final double lon_inters12_min = min(lon_inters1, lon_inters2);
                                final double lon_inters12_max = max(lon_inters1, lon_inters2);
                                
                                lons_split.add( new TInterval (lon_interval.min_value, lon_inters12_min - eps ));
                                lons_split.add( new TInterval (lon_inters12_min + eps, lon_inters12_max - eps ));
                                lons_split.add( new TInterval (lon_inters12_max + eps, lon_interval.max_value ));

                                return lons_split;
                        }

                        //Only the first intersection lon1 inside the lon interval. Create 2 intervals.
                        else if ((lon_inters1 > lon_interval.min_value) && (lon_inters1 < lon_interval.max_value))
                        {
                                lons_split.add( new TInterval (lon_interval.min_value, lon_inters1 - eps ));
                                lons_split.add( new TInterval (lon_inters1 + eps, lon_interval.max_value));

                                return lons_split;
                        }

                        //Only the second intersection lon2 inside the lon interval. Create 2 intervals.
                        else if ((lon_inters2 > lon_interval.min_value) && (lon_inters2 < lon_interval.max_value))
                        {
                                lons_split.add( new TInterval (lon_interval.min_value, lon_inters2 - eps));
                                lons_split.add( new TInterval (lon_inters2 + eps, lon_interval.max_value));

                                return lons_split;
                        }
                }

                //No intersection, no split
                lons_split.add(new TInterval (lon_interval.min_value, lon_interval.max_value));

                return lons_split;
        }  
}
