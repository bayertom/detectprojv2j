// Description: Create projection graticule given by the lat/lon intervals
// Support lon0 shift for the oblique aspect

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


package detectprojv2j.algorithms.graticuleAS;

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
import detectprojv2j.types.TInterval2D;
import java.util.Stack;


public class GraticuleAS {
  
        
        public static void createGraticule ( final Projection proj, final TInterval lat_extent, final TInterval lon_extent, final double lat_step, final double lon_step, final double d_lat, final double d_lon, final double alpha, List <Meridian> meridians, List<List <Point3DCartesian >> meridians_proj, List <Parallel > parallels, List<List <Point3DCartesian >> parallels_proj)
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
                        lat_interval.min_value = max(lat_extent.min_value, MIN_LAT + GRATICULE_LAT_LON_SHIFT);
                        lat_interval.max_value = min(lat_extent.max_value, MAX_LAT - GRATICULE_LAT_LON_SHIFT);

                        //Create lon interval
                        TInterval lon_interval = new TInterval(lon_extent.min_value,lon_extent.max_value);
                        lon_extent.min_value = max(lon_extent.min_value, MIN_LON + GRATICULE_LAT_LON_SHIFT);
                        lon_extent.max_value = min(lon_extent.max_value, MAX_LON - GRATICULE_LAT_LON_SHIFT);

                        //Create 2D interval
                        TInterval2D lat_lon_interval = new TInterval2D(lat_interval, lon_interval);
                        S.push(lat_lon_interval);

                        //Process all meridians and parallels: automatic detection of additional singular points
                        //Process until the stack S is empty (recursive approach)
                        int split_amount = 0;
                       
                        while (!S.empty())
                        {
                                //Get the current lat/lon interval on the top of S
                                TInterval2D interval = S.pop();
			
                                //Create temporary containers for the meridians/parallels
                                List <Meridian> meridians_temp = new ArrayList<>();
                                List <Parallel> parallels_temp = new ArrayList<>();
                                List <List<Point3DCartesian>> meridians_temp_proj = new ArrayList<>();
                                List <List<Point3DCartesian>> parallels_temp_proj = new ArrayList<>();

                                //Try to create meridians and parallels
                                try
                                {                            
                                        //Create meridians and parallels
                                        createMeridians (proj, interval.i1, interval.i2, lon_step, d_lat, alpha, meridians_temp, meridians_temp_proj);
                                        createParallels (proj, interval.i1, interval.i2, lat_step, d_lon, alpha, parallels_temp, parallels_temp_proj);

                                        //Copy temporary meridians and parallels to the output data structure
                                        for (Meridian m:meridians_temp) meridians.add(m);
                                        for (Parallel p:parallels_temp) parallels.add(p);

                                        //Copy temporary projected meridians and parallels to the output data structure
                                        for (List<Point3DCartesian> m:meridians_temp_proj) meridians_proj.add(m);
                                        for (List<Point3DCartesian> p:parallels_temp_proj) parallels_proj.add(p);
                                }

                                //Exception, singularity in the latitude direction
                                catch ( MathLatSingularityException error )
                                {
                                        //Get lagitude singularity
                                        final double [] lat_error = {error.getArg()};
                                        
                                        //Too many splits, projection is suspected, stop graticule construction
                                        if ( split_amount > MAX_GRATICULE_SPLIT_AMOUNT )
                                        {
                                                meridians.clear(); parallels.clear();
                                                meridians_proj.clear(); parallels_proj.clear();
                                                return;
                                        }

                                        //Empty lat interval, delete
                                        else if (abs(interval.i1.max_value - interval.i1.min_value) < 10 * GRATICULE_LAT_LON_SHIFT)
                                        {
                                                continue;
                                        }

                                        //Lat value is lower bound: shift lower bound
                                        else if ((abs(interval.i1.min_value - lat_error[0]) <  2 * GRATICULE_LAT_LON_SHIFT) && 
                                                 (abs(interval.i1.max_value - interval.i1.min_value) > 10 * GRATICULE_LAT_LON_SHIFT))
                                        {
                                                interval.i1.min_value +=  2 *GRATICULE_LAT_LON_SHIFT;
                                        }

                                        //Lat value is upper bound: shift upper bound
                                        else if ((abs(interval.i1.min_value - lat_error[0]) <  2 * GRATICULE_LAT_LON_SHIFT) && 
                                                 (abs(interval.i1.max_value - interval.i1.min_value) > 10 * GRATICULE_LAT_LON_SHIFT))
                                        {
                                                interval.i1.max_value -= 2 * GRATICULE_LAT_LON_SHIFT;
                                        }

                                        //Lat value inside interval: split intervals
                                        else if ((interval.i1.min_value < lat_error[0]) && (interval.i1.max_value > lat_error[0]))
                                        {
                                                //Create second 2D interval and add to the stack S
                                                TInterval2D interval2 = new TInterval2D(interval.i1, interval.i2);
                                                interval2.i1.min_value = lat_error[0] + 2 * GRATICULE_LAT_LON_SHIFT;
                                                S.push(interval2);

                                                //Change upper bound of the first 2D interval
                                                interval.i1.max_value = lat_error[0] - 2 * GRATICULE_LAT_LON_SHIFT;

                                                //Increment split amount
                                                split_amount++;
                                        }

                                        //Add modified interval to the stack S
                                        S.push(interval);
                                }
                                
                                //Exception, singularity in the longitude direction
                                catch ( MathLonSingularityException error )
                                {                      
                                        //Get longitude singularity
                                        final double [] lon_error = {error.getArg()};
                                        
                                        //Too many splits, projection is suspected, stop graticule construction
                                        if ( split_amount > MAX_GRATICULE_SPLIT_AMOUNT )
                                        {
                                                meridians.clear(); parallels.clear();
                                                meridians_proj.clear(); parallels_proj.clear();
                                                return;
                                        }

                                        //Empty lon interval, delete
                                        else if (abs(interval.i2.max_value - interval.i2.min_value) < 10 * GRATICULE_LAT_LON_SHIFT)
                                        {
                                                continue;
                                        }

                                        //Lon value is lower bound: shift lower bound
                                        else if ((abs(interval.i2.min_value - lon_error[0]) <  2 *GRATICULE_LAT_LON_SHIFT) && 
                                                 (abs(interval.i2.max_value - interval.i2.min_value) > 10 * GRATICULE_LAT_LON_SHIFT))
                                        {
                                                interval.i2.min_value += 2 * GRATICULE_LAT_LON_SHIFT;
                                        }

                                        //Lon value is upper bound: shift upper bound
                                        else if ((abs(interval.i2.max_value- lon_error[0]) <  2 * GRATICULE_LAT_LON_SHIFT) && 
                                                 (abs(interval.i2.max_value - interval.i2.min_value) > 10 * GRATICULE_LAT_LON_SHIFT))
                                        {
                                                interval.i2.max_value -= 2 * GRATICULE_LAT_LON_SHIFT;
                                        }

                                        //Lon value inside interval: split intervals
                                        else if ((interval.i2.min_value  < lon_error[0]) && (interval.i2.max_value > lon_error[0]))
                                        {
                                                //Create second 2D interval and add to the stack S
                                                TInterval2D interval2 = new TInterval2D(interval.i1, interval.i2);
                                                interval2.i2.min_value = lon_error[0] + 2 * GRATICULE_LAT_LON_SHIFT;
                                                S.push(interval2);

                                                //Change upper bound of the first 2D interval
                                                interval.i2.max_value = lon_error[0] - 2 * GRATICULE_LAT_LON_SHIFT;

                                                //Increment split amount
                                                split_amount++;
                                        }
                                        
                                        //Add modified interval to the stack S
                                        S.push(interval);
                                }

                                //Other than math error: error in equation or in parser
                                catch ( Exception exception )
                                {
                                        //Clear meridians and parallels
                                        meridians.clear(); parallels.clear();
                                        return;
                                } 
                        }
                }
        }


        public static void createMeridians (final Projection proj, final TInterval lat_interval, final TInterval lon_interval, final double lon_step, 
                final double d_lat, final double alpha, List <Meridian > meridians, List <List<Point3DCartesian>> meridians_proj)
        {
                //Set start value of the longitude as a multiplier of lon_step 
                final double lon_start = Round.roundToMultipleFloor(lon_interval.min_value, lon_step) + lon_step;
                final double lon_end = Round.roundToMultipleCeil(lon_interval.max_value, lon_step) - lon_step;
                
                //Set value lon inside [MIN_LAT, MAX_LAT] (shifted lower bound)
        	double lon_lb = max(min(lon_interval.min_value + GRATICULE_LAT_LON_SHIFT, MAX_LON), MIN_LON);

                //Set value lon <= lon_max
                lon_lb = min(lon_lb, lon_interval.max_value);
        
                //Create first meridian (lower bound of the interval, not lower than MIN_LON)
                //Split the interval, if a meridian is intersected by the prime meridian of the transformed system [lat_trans, lon_trans]
                createMeridianFragment(proj, lon_lb, lat_interval, d_lat, alpha, meridians, meridians_proj);

                //Create intermediate meridians
                //Split the interval, if a meridian is intersected by the prime meridian of the transformed system [lat_trans, lon_trans]
                for (double lon = lon_start; lon <= lon_end; lon += lon_step)
                        createMeridianFragment(proj, lon, lat_interval, d_lat, alpha, meridians, meridians_proj);

                //Set value lat inside [MIN_LAT, MAX_LAT] (shifted upper bound)
                double lon_ub = min(max(lon_interval.max_value - GRATICULE_LAT_LON_SHIFT, MIN_LON), MAX_LON);

                //Set value lon >= lon_interval.min
                lon_ub = max(lon_ub, lon_interval.min_value);

                //Create last meridian (upper bound of the interval, not higher than MAX_LON)
                //Split the interval, if a meridian is intersected by the prime meridian of the transformed system [lat_trans, lon_trans]
                createMeridianFragment(proj, lon_ub, lat_interval, d_lat, alpha, meridians, meridians_proj);

                //Create meridian intersecting the pole of the transformed system [lat_trans, lon_trans]
                double lon = proj.getCartPole().getLon();
                if ((lon > lon_interval.min_value) && (lon < lon_interval.max_value))
                {
                        createMeridianFragment(proj, min(max(lon - GRATICULE_LAT_LON_SHIFT, MIN_LON), MAX_LON), lat_interval, d_lat, alpha, meridians, meridians_proj);
                        createMeridianFragment(proj, max(min(lon + GRATICULE_LAT_LON_SHIFT, MAX_LON), MIN_LON), lat_interval, d_lat, alpha, meridians, meridians_proj);
                }

                //Create meridian opposite the pole of the transformed system [lat_trans, lon_trans]
                lon = (lon > 0 ? lon - 180 : lon + 180);
                if ((lon > lon_interval.min_value) && (lon < lon_interval.max_value))
                {
                        createMeridianFragment(proj, min(max(lon - GRATICULE_LAT_LON_SHIFT, MIN_LON), MAX_LON), lat_interval, d_lat, alpha, meridians, meridians_proj);
                        createMeridianFragment(proj, max(min(lon + GRATICULE_LAT_LON_SHIFT, MAX_LON), MIN_LON), lat_interval, d_lat, alpha, meridians, meridians_proj);
                }      
        }


        public static void createParallels ( final Projection proj, final TInterval lat_interval, final TInterval lon_interval, final double lat_step, 
                final double d_lon, final double alpha, List <Parallel > parallels, List <List<Point3DCartesian>> parallels_proj )
        {
                //Set start value of the longitude as a multiplier of lon_step 
                final double lat_start = Round.roundToMultipleFloor(lat_interval.min_value, lat_step) + lat_step;
                final double lat_end = Round.roundToMultipleCeil(lat_interval.max_value, lat_step) - lat_step;
                
                //Set value lat inside [MIN_LAT, MAX_LAT] (shifted lower bound)
                double lat_lb = max(min(lat_interval.min_value + GRATICULE_LAT_LON_SHIFT, MAX_LAT), MIN_LAT);

                //Set value lat <= lat_max
                lat_lb = min(lat_lb, lat_interval.max_value);
               
                //Create first parallel (lower bound of the interval, nor lower than MIN_LAT)
                //Split the interval, if a parallel is intersected by the prime meridian of the transformed system [lat_trans, lon_trans]
                createParallelFragment(proj, lat_lb, lon_interval, d_lon, alpha, parallels, parallels_proj);

                //Create intermediate parallels
                //Split the interval, if a parallel is intersected by the prime meridian of the transformed system [lat_trans, lon_trans]
                for (double lat = lat_start; lat <= lat_end; lat += lat_step)
                        createParallelFragment(proj, lat, lon_interval, d_lon, alpha, parallels, parallels_proj);

                //Set value lat inside [MIN_LAT, MAX_LAT] (shifted upper bound)
                double lat_ub = min(max(lat_interval.max_value - GRATICULE_LAT_LON_SHIFT, MIN_LAT), MAX_LAT);

                //Set value lat >= lat_interval.min
                lat_ub = max(lat_ub, lat_interval.min_value);

                //Create last parallel (upper bound of the interval, not greater than MAX_LAT)
                //Split the interval, if a parallel is intersected by the prime meridian of the transformed system [lat_trans, lon_trans]
                createParallelFragment(proj, lat_ub, lon_interval, d_lon, alpha, parallels, parallels_proj);
        } 
        
        
        public static void createMeridianFragment(final Projection proj, final double lon, final TInterval lat_interval, final double d_lat, final double alpha, List <Meridian > meridians, List <List<Point3DCartesian>> meridians_proj)
        {
                //Create meridian fragment given by the latitude interval
                //A meridian is interrupted at its intersection with the prime meridian of the transfomed system [lat_trans, lon_trans]
                for (TInterval lat_interval_split : splitLatInterval(lat_interval, lon, proj.getCartPole(), proj.getLonDir(), proj.getLon0()))
                {
                        Meridian  mer = new Meridian(lon, lat_interval_split, d_lat, GRATICULE_LAT_LON_SHIFT, GRATICULE_LAT_LON_SHIFT);
                        List <Point3DCartesian> mer_proj = new ArrayList<>();
                        mer.project(proj, alpha, mer_proj);

                        //Add meridian and projected meridian to the list
                        meridians.add(mer);
                        meridians_proj.add(mer_proj);
                }
        }


        public static void createParallelFragment(final Projection proj, final double lat, final TInterval lon_interval, final double d_lon, final double alpha, List <Parallel > parallels, List <List<Point3DCartesian>> parallels_proj)
        {
                //Create parallel fragment given by the longitude interval [lon_minm lon_max]
                //A parallel is interrupted at its intersection with the prime meridian of the transformed system [lat_trans, lon_trans]
                for (TInterval lon_interval_split : splitLonInterval(lon_interval, lat, proj.getCartPole(), proj.getLonDir(), proj.getLon0()))
                {
                        Parallel  par = new Parallel(lat, lon_interval_split, d_lon, GRATICULE_LAT_LON_SHIFT, GRATICULE_LAT_LON_SHIFT);
                        List <Point3DCartesian> par_proj = new ArrayList<>();
                        par.project(proj, alpha, par_proj);

                        //Add parallel and projected parallel to the list
                        parallels.add(par);
                        parallels_proj.add(par_proj);
                }
        }

        
        public static List<TInterval> splitLatInterval(final TInterval lat_interval, final double lon, final Point3DGeographic pole, final TTransformedLongitudeDirection lon_dir, final double lon0)
        {
                //Split the meridian interval [lat_min, lat_max] at the intersection with the prime meridian of the transformed system [lat_trans, lon_trans] shifted by lon0
                //to two subintervals I1=[lat_min, lat_inters], I2=[lat_inters, lat_max]
                List <TInterval> lats_split = new ArrayList<>();
                
                final Point3DGeographic p1 = new Point3DGeographic(lat_interval.min_value, lon);
                final Point3DGeographic p2 = new Point3DGeographic(0.5 * (lat_interval.min_value + lat_interval.max_value), lon);
                final Point3DGeographic p3 = new Point3DGeographic(lat_interval.max_value, lon);

                //Sample central meridian of the transformed system in the oblique aspect
                //Transform it to the normal aspect
                final double lat4 = CartTransformation.latTransToLat(-80.0, lon0, pole.getLat(), pole.getLon(), lon_dir);
                final double lon4 = CartTransformation.lonTransToLon(-80.0, lon0, pole.getLat(), pole.getLon(), lon_dir);

                //P5: (lat_trans, lon_trans) -> (lat, lon)
                final double lat5 = CartTransformation.latTransToLat(0.0, lon0, pole.getLat(), pole.getLon(), lon_dir);
                final double lon5 = CartTransformation.lonTransToLon(0.0, lon0, pole.getLat(), pole.getLon(), lon_dir);

                //P6: (lat_trans, lon_trans) -> (lat, lon)
                final double lat6 = CartTransformation.latTransToLat(80.0, lon0, pole.getLat(), pole.getLon(), lon_dir);
                final double lon6 = CartTransformation.lonTransToLon(80.0, lon0, pole.getLat(), pole.getLon(), lon_dir);

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
                        if ((lat_inters1 > lat_interval.min_value) && (lat_inters1 < lat_interval.max_value) && (abs(lon - lon_inters1) < ANGLE_ROUND_ERROR))
                        {
                                lats_split.add(new TInterval (lat_interval.min_value, max(lat_inters1 - GRATICULE_LAT_LON_SHIFT, lat_interval.min_value) ));
                                lats_split.add(new TInterval (min(lat_inters1 + GRATICULE_LAT_LON_SHIFT, lat_interval.max_value), lat_interval.max_value ));

                                return lats_split;
                        }

                        //Is an intersection point i1 latitude inside the lat interval? Create 2 intervals.
                        else if ((lat_inters2 > lat_interval.min_value) && (lat_inters2 < lat_interval.max_value) && (abs(lon - lon_inters2) < ANGLE_ROUND_ERROR))
                        {		
                                lats_split.add(new TInterval ( lat_interval.min_value, max(lat_inters2 - GRATICULE_LAT_LON_SHIFT, lat_interval.min_value)));
                                lats_split.add(new TInterval ( min(lat_inters2 + GRATICULE_LAT_LON_SHIFT, lat_interval.max_value), lat_interval.max_value));

                                return lats_split;
                        }
                }

                //No intersection, no split
                lats_split.add(new TInterval (lat_interval.min_value, lat_interval.max_value));

                return lats_split;
        }


        public static List<TInterval> splitLonInterval(final TInterval lon_interval, final double lat, final Point3DGeographic pole, final TTransformedLongitudeDirection lon_dir, final double lon0)
        {
                //Split the parallel interval [lon_min, lon_max] at the intersection with the prime meridian of the transformed system [lat_trans, lon_trans] shifted by lon0
                //to three subintervals I3=[lon_min, lon_inters1], I4=[lon_inters1, lon_inters2], I5=[lon_inters2, lon_max]
                List <TInterval> lons_split = new ArrayList<>();

                final Point3DGeographic p1 = new Point3DGeographic(lat, lon_interval.min_value + 1.0);
                final Point3DGeographic p2 = new Point3DGeographic(lat, 0.5 * (lon_interval.min_value + lon_interval.max_value));
                final Point3DGeographic p3 = new Point3DGeographic(lat, lon_interval.max_value - 1.0);

                //Sample central meridian of the transformed system in the oblique aspect
                //Transform it to the normal aspect
                final double lat4 = CartTransformation.latTransToLat(-80.0, lon0, pole.getLat(), pole.getLon(), lon_dir);
                final double lon4 = CartTransformation.lonTransToLon(-80.0, lon0, pole.getLat(), pole.getLon(), lon_dir);

                //P5: (lat_trans, lon_trans) -> (lat, lon)
                final double lat5 = CartTransformation.latTransToLat(0.0, lon0, pole.getLat(), pole.getLon(), lon_dir);
                final double lon5 = CartTransformation.lonTransToLon(0.0, lon0, pole.getLat(), pole.getLon(), lon_dir);

                //P6: (lat_trans, lon_trans) -> (lat, lon)
                final double lat6 = CartTransformation.latTransToLat(80.0, lon0, pole.getLat(), pole.getLon(), lon_dir);
                final double lon6 = CartTransformation.lonTransToLon(80.0, lon0, pole.getLat(), pole.getLon(), lon_dir);

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
                        if ((lon_inters1 > lon_interval.min_value && lon_inters1 < lon_interval.max_value) && 
                            (lon_inters2 > lon_interval.min_value && lon_inters2 < lon_interval.max_value) && (abs(lon_inters2 - lon_inters1) > ANGLE_ROUND_ERROR))
                        {
                                lons_split.add( new TInterval (lon_interval.min_value, max(lon_inters1 - GRATICULE_LAT_LON_SHIFT, lon_interval.min_value)));
                                lons_split.add( new TInterval (lon_inters1 + GRATICULE_LAT_LON_SHIFT, lon_inters2 - GRATICULE_LAT_LON_SHIFT));
                                lons_split.add( new TInterval (min(lon_inters2 + GRATICULE_LAT_LON_SHIFT, lon_interval.max_value), lon_interval.max_value ));

                                return lons_split;
                        }

                        //Only the first intersection lon1 inside the lon interval. Create 2 intervals.
                        else if ((lon_inters1 > lon_interval.min_value) && (lon_inters1 < lon_interval.max_value))
                        {
                                lons_split.add( new TInterval (lon_interval.min_value, max(lon_inters1 - GRATICULE_LAT_LON_SHIFT, lon_interval.min_value)));
                                lons_split.add( new TInterval (min(lon_inters1 + GRATICULE_LAT_LON_SHIFT, lon_interval.max_value), lon_interval.max_value));

                                return lons_split;
                        }

                        //Only the second intersection lon2 inside the lon interval. Create 2 intervals.
                        else if ((lon_inters2 > lon_interval.min_value) && (lon_inters2 < lon_interval.max_value))
                        {
                                lons_split.add( new TInterval (lon_interval.min_value, max(lon_inters2 - GRATICULE_LAT_LON_SHIFT, lon_interval.min_value)));
                                lons_split.add( new TInterval (min(lon_inters2 + GRATICULE_LAT_LON_SHIFT, lon_interval.max_value), lon_interval.max_value));

                                return lons_split;
                        }
                }

                //No intersection, no split
                lons_split.add(new TInterval (lon_interval.min_value, lon_interval.max_value));

                return lons_split;
        }  
}
