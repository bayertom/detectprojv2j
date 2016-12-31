// Description: Create projection graticule given by the lat/lon intervals

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


package detectprojv2j.algorithms.graticule;

import java.util.List;
import java.util.ArrayList;
import static java.lang.Math.*;

import detectprojv2j.types.TInterval;

import detectprojv2j.structures.graticule.Meridian;
import detectprojv2j.structures.graticule.Parallel;
import detectprojv2j.structures.point.Point3DCartesian;
import detectprojv2j.structures.projection.Projection;

import static detectprojv2j.consts.Consts.*;

import detectprojv2j.exceptions.MathException;

import detectprojv2j.algorithms.carttransformation.CartTransformation;
import detectprojv2j.algorithms.round.Round;


public class Graticule {
  
        
        public static void createGraticule ( final Projection proj, final TInterval lat_extent, final TInterval lon_extent, final double lat_step, final double lon_step, final double d_lat, final double d_lon, final double alpha, List <Meridian> meridians, List<List <Point3DCartesian >> meridians_proj, List <Parallel > parallels, List<List <Point3DCartesian >> parallels_proj )
        {
                //Compute graticule given by meridians and parallels
                TInterval  lat_interval_part, lon_interval_part;

                //Parameters of the projection
                final double latp = proj.getCartPole().getLat();
                final double lonp = proj.getCartPole().getLon();
                final double lon0 = proj.getLon0();

                //Get size of the interval
                final double lat_interval_width = lat_extent.max - lat_extent.min;
                final double lon_interval_width = lon_extent.max - lon_extent.min;

                //Generate meridians and parallels
                if ( lat_step <= lat_interval_width || lon_step <= lon_interval_width )
                {
                        //Create lat intervals to avoid basic singular points
                        List <TInterval>  lat_intervals = new ArrayList<>();
                        createLatIntervals ( lat_extent, latp, lat_intervals );

                        //Create lon intervals to avoid basic singular points
                        List <TInterval>  lon_intervals = new ArrayList<>();
                        final double lon_break = CartTransformation.redLon0(lonp, -lon0);
                        createLonIntervals(lon_extent, lon_break, lon_intervals);

                        //Process all meridians and parallels: automatic detection of additional singular points
                        int split_amount = 0;
                        int [] i = {0}, j = {0};

                        //Process all lat intervals
                        for ( i[0] = 0; i[0] <  lat_intervals.size(); i[0]++)
                        {
                                //cout << "lat: <" << i_lat_intervals.min << ", " << i_lat_intervals.max << "> \n";
                                //Process all lon intervals
                                for ( j[0] = 0; j[0] <  lon_intervals.size();  )
                                {
                                        //cout << "   lon: <" << i_lon_intervals.min << ", " << i_lon_intervals.max << "> \n";
                                        //Create temporary meridians and parallels
                                        List <Meridian> meridians_temp = new ArrayList<>();
                                        List <Parallel> parallels_temp = new ArrayList<>();
                                        List <List<Point3DCartesian>> meridians_temp_proj = new ArrayList<>();
                                        List <List<Point3DCartesian>> parallels_temp_proj = new ArrayList<>();

                                        //Compute meridians and parallels
                                        double [] lat_error = {0.0}, lon_error = {0.0};

                                        try
                                        {                            
                                                //Compute meridians and parallels
                                                createMeridians (proj, lat_intervals.get(i[0]), lon_intervals.get(j[0]), lon_step, d_lat, alpha, meridians_temp, meridians_temp_proj, lat_error, lon_error );
                                                createParallels (proj, lat_intervals.get(i[0]), lon_intervals.get(j[0]), lat_step, d_lon, alpha, parallels_temp, parallels_temp_proj, lat_error, lon_error);

                                                //Copy temporary meridians and parallels to the output data structure
                                                for (Meridian m:meridians_temp) meridians.add(m);
                                                for (Parallel p:parallels_temp) parallels.add(p);

                                                //Copy temporary projected meridians and parallels to the output data structure
                                                for (List<Point3DCartesian> m:meridians_temp_proj) meridians_proj.add(m);
                                                for (List<Point3DCartesian> p:parallels_temp_proj) parallels_proj.add(p);

                                                //Increment lon intervals only if comuptation was successful
                                                j[0]++;
                                        }

                                        //Exception
                                        catch ( MathException error )
                                        {
                                                //Too many splits, projection is suspected, stop computations
                                                if ( split_amount > 100 )
                                                {
                                                        meridians.clear(); parallels.clear();
                                                        meridians_proj.clear(); parallels_proj.clear();
                                                        return;
                                                }

                                                //Empty lat interval, delete
                                                if (abs((lat_intervals.get(i[0])).max - (lat_intervals.get(i[0])).min) < 10 * GRATICULE_LAT_LON_SHIFT)
                                                {
                                                        lat_intervals.remove(i[0]);
       
                                                }

                                                //Lat value is lower bound: shift lower bound
                                                else if ((abs((lat_intervals.get(i[0])).min - lat_error[0]) <  2 * GRATICULE_LAT_LON_SHIFT) && 
                                                         (abs((lat_intervals.get(i[0])).max - (lat_intervals.get(i[0])).min) > 10 * GRATICULE_LAT_LON_SHIFT))
                                                {
                                                        lat_intervals.get(i[0]).min +=  2 *GRATICULE_LAT_LON_SHIFT;
                                                }

                                                //Lat value is upper bound: shift upper bound
                                                else if ((abs((lat_intervals.get(i[0])).max - lat_error[0]) <  2 * GRATICULE_LAT_LON_SHIFT) && 
                                                         (abs((lat_intervals.get(i[0])).max - (lat_intervals.get(i[0])).min) > 10 * GRATICULE_LAT_LON_SHIFT))
                                                {
                                                        lat_intervals.get(i[0]).max -= 2 * GRATICULE_LAT_LON_SHIFT;
                                                }

                                                //Lat value inside interval: split intervals
                                                else if ((((lat_intervals.get(i[0])).min) < lat_error[0]) && ((lat_intervals.get(i[0])).max > lat_error[0]))
                                                {
                                                        splitIntervals(lat_intervals, lat_intervals.get(i[0]), i, lat_error[0]);
                                                        
                                                        //Increment split amount
                                                        split_amount++;
                                                }

                                                //Empty lon interval, delete
                                                if (abs((lon_intervals.get(j[0])).max - (lon_intervals.get(j[0])).min) < 10 * GRATICULE_LAT_LON_SHIFT)
                                                {
                                                        lon_intervals.remove(j[0]);
                                                }

                                                //Lon value is lower bound : shift lower bound
                                                else if ((abs((lon_intervals.get(j[0])).min - lon_error[0]) <  2 *GRATICULE_LAT_LON_SHIFT) && 
                                                         (abs((lon_intervals.get(j[0])).max - (lon_intervals.get(j[0])).min) > 10 * GRATICULE_LAT_LON_SHIFT))
                                                {
                                                        (lon_intervals.get(j[0])).min += 2 * GRATICULE_LAT_LON_SHIFT;
                                                }

                                                //Lon value is upper bound: shift upper bound
                                                else if ((abs((lon_intervals.get(j[0])).max - lon_error[0]) <  2 * GRATICULE_LAT_LON_SHIFT) && 
                                                         (abs((lon_intervals.get(j[0])).max - (lon_intervals.get(j[0])).min) > 10 * GRATICULE_LAT_LON_SHIFT))
                                                {
                                                        (lon_intervals.get(j[0])).max -= 2 * GRATICULE_LAT_LON_SHIFT;
                                                }

                                                //Lon value inside interval: split intervals
                                                else if ((lon_intervals.get(j[0]).min  < lon_error[0] ) && (lon_intervals.get(j[0]).max > lon_error[0] ))
                                                {
                                                        splitIntervals ( lon_intervals, lon_intervals.get(j[0]), j, lon_error[0] );

                                                        //Increment split amount
                                                        split_amount++;
                                                }

                                                //cout << (*i_lat_intervals).min << "   " << (*i_lat_intervals).max << '\n';
                                                //cout << (*i_lon_intervals).min << "   " << (*i_lon_intervals).max << '\n';
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
        }


        public static void  createLatIntervals ( final TInterval lat_extent, final double latp, List <TInterval> lat_intervals )
        {
                //Split the geographic extent into several sub-intervals
                TInterval  lat_interval_part1 = new TInterval(-90, 90);
                TInterval  lat_interval_part2 = new TInterval(-90, 90);

                //First lat interval ( -90, latp )
                lat_interval_part1.min = max ( MIN_LAT, lat_extent.min ); 
                lat_interval_part1.max = min ( latp, lat_extent.max );

                //Test interval and add to the list
                if ( lat_interval_part1.max > lat_interval_part1.min )
                        lat_intervals.add ( lat_interval_part1 );

                //Second lat interval ( latp, 90 )
                lat_interval_part2.min = max ( latp, lat_extent.min );
                lat_interval_part2.max = min ( lat_extent.max, MAX_LAT );

                //Test interval and add to the list
                if ( lat_interval_part2.max > lat_interval_part2.min )
                        lat_intervals.add ( lat_interval_part2 );
        }


        public static void createLonIntervals ( final TInterval lon_extent, final double lonp, List <TInterval> lon_intervals )
        {
                //Split the geographic extent into several sub-intervals
                TInterval  lon_interval_part1 = new TInterval(-180, 180);
                TInterval  lon_interval_part2 = new TInterval(-180, 180);
                TInterval  lon_interval_part3 = new TInterval(-180, 180);

                //Split given interval into sub intervals: lonp >=0
                if ( lonp >= 0 )
                {
                        //First interval <-180, lonp - 180)
                        lon_interval_part1.min = max ( MIN_LON, lon_extent.min ); 
                        lon_interval_part1.max =  min ( lonp - 180.0, lon_extent.max );

                        //Test first interval and add to the list
                        if ( lon_interval_part1.max > lon_interval_part1.min )
                                lon_intervals.add ( lon_interval_part1 );

                        //Second interval (lonp - 180, lonp>
                        lon_interval_part2.min = max ( lonp - 180.0, lon_extent.min ); 
                        lon_interval_part2.max = min ( lonp, lon_extent.max );

                        //Test second interval and add to the list
                        if ( lon_interval_part2.max > lon_interval_part2.min )
                                lon_intervals.add ( lon_interval_part2 );

                        //Third interval (lonp, MAX_LON>
                        lon_interval_part3.min = max ( lonp, lon_extent.min ); 
                        lon_interval_part3.max = min ( MAX_LON, lon_extent.max );

                        //Test third interval and add to the list
                        if ( lon_interval_part3.max > lon_interval_part3.min )
                                lon_intervals.add ( lon_interval_part3 );
                }

                //Split given interval into sub intervals: lonp < 0
                else
                {
                        //First interval <-180, lonp >
                        lon_interval_part1.min = max ( MIN_LON, lon_extent.min ); 
                        lon_interval_part1.max = min ( lonp, lon_extent.max );

                        //Test first interval and add to the list
                        if ( lon_interval_part1.max > lon_interval_part1.min )
                                lon_intervals.add ( lon_interval_part1 );

                        //Second interval <lonp, lonp + 180>
                        lon_interval_part2.min = max ( lonp, lon_extent.min ); 
                        lon_interval_part2.max = min ( lonp + 180.0, lon_extent.max );

                        //Test second interval and add to the list
                        if ( lon_interval_part2.max > lon_interval_part2.min )
                                lon_intervals.add ( lon_interval_part2 );

                        //Third interval (lonp + 180, 180>
                        lon_interval_part3.min = max ( lonp + 180.0, lon_extent.min ); 
                        lon_interval_part3.max = min ( MAX_LON, lon_extent.max );

                        //Test third interval and add to the list
                        if ( lon_interval_part3.max > lon_interval_part3.min )
                                lon_intervals.add ( lon_interval_part3 );
                }
        }


        public static void splitIntervals ( List<TInterval> intervals, TInterval interval, int [] index, final double error )
        {
                //Split one interval in 2
                final double max_val = interval.max;

                //Resize old interval <min, max> to <min, error)
                interval.max = error;

                //Create new interval (error, max>
                TInterval  interval_temp  = new TInterval(error, max_val);

                //Add the new interval to the list
                intervals.add (index[0], interval_temp );

                //Move iterator to the previous item
                index[0] --;
        }


        public static void createMeridians (final Projection proj, final TInterval lat_interval, final TInterval lon_interval, final double lon_step, 
                final double d_lat, final double alpha, List <Meridian > meridians, List <List<Point3DCartesian>> meridians_proj, double [] lat_error, double [] lon_error )
        {
                //Set start value of the longitude as a multiplier of lon_step 
                final double lon_start = Round.roundToMultipleFloor(lon_interval.min, lon_step) + lon_step;
                final double lon_end = Round.roundToMultipleCeil(lon_interval.max, lon_step) - lon_step;
                double lon = max(min(lon_interval.min + GRATICULE_LAT_LON_SHIFT, MAX_LON), MIN_LON);

                try
                {
                        //Create first meridian (lower bound of the interval, not lower than MIN_LON)
                        Meridian  m1 = new Meridian(lon, lat_interval, d_lat, GRATICULE_LAT_LON_SHIFT, GRATICULE_LAT_LON_SHIFT);
                        List <Point3DCartesian> mer1 = new ArrayList<>();
                        m1.project(proj, alpha, mer1);

                        //Add meridian and projected meridian to the list
                        meridians.add(m1);
                        meridians_proj.add(mer1);

                        //Create intermediate meridians
                        for (lon = lon_start; lon <= lon_end; lon += lon_step)
                        {
                                Meridian  m2 = new Meridian(lon, lat_interval, d_lat, GRATICULE_LAT_LON_SHIFT, GRATICULE_LAT_LON_SHIFT);
                                List <Point3DCartesian> mer2 = new ArrayList<>();
                                m2.project(proj, alpha, mer2);

                                //Add meridian and projected meridian to the list
                                meridians.add(m2);
                                meridians_proj.add(mer2);
                        }

                        //Create last meridian (upper bound of the interval, not higher than MAX_LON)
                        lon = max(min(lon_interval.max - GRATICULE_LAT_LON_SHIFT, MAX_LON), MIN_LON);

                        Meridian  m3 = new Meridian(lon, lat_interval, d_lat, GRATICULE_LAT_LON_SHIFT, GRATICULE_LAT_LON_SHIFT);
                        List <Point3DCartesian> mer3 = new ArrayList<>();
                        m3.project(proj, alpha, mer3);

                        //Add meridian and projected meridian to the list
                        meridians.add(m3);
                        meridians_proj.add(mer3);
                }

                //Throw math exception: get error values
                catch (MathException error)
                {
                        //Get posible error values
                        lat_error[0] = error.getArg();
                        lon_error[0] = lon;

                        //Throw exception
                        throw error;
                }

                //Throw other exception: error in parser or incorrect equation
                catch (Exception error)
                {
                        throw error;
                }
        }


        public static void createParallels ( final Projection proj, final TInterval lat_interval, final TInterval lon_interval, final double lat_step, 
                final double d_lon, final double alpha, List <Parallel > parallels, List <List<Point3DCartesian>> parallels_proj, double [] lat_error, double [] lon_error )
        {
                //Set start value of the longitude as a multiplier of lon_step 
                final double lat_start = Round.roundToMultipleFloor(lat_interval.min, lat_step) + lat_step;
                final double lat_end = Round.roundToMultipleCeil(lat_interval.max, lat_step) - lat_step;
                double lat = max(min(lat_interval.min + GRATICULE_LAT_LON_SHIFT, MAX_LAT), MIN_LAT);

                try
                {
                        //Create first parallel (lower bound of the interval, nor lower than MIN_LAT)
                        Parallel  p1 = new Parallel(lat, lon_interval, d_lon, GRATICULE_LAT_LON_SHIFT, GRATICULE_LAT_LON_SHIFT);
                        List <Point3DCartesian> par1 = new ArrayList<>();
                        p1.project(proj, alpha, par1);

                        //Add parallel and projected parallel to the list
                        parallels.add(p1);
                        parallels_proj.add(par1);

                        //Create intermediate parallels
                        for (lat = lat_start; lat <= lat_end; lat += lat_step)
                        {
                                Parallel  p2 = new Parallel(lat, lon_interval, d_lon, GRATICULE_LAT_LON_SHIFT, GRATICULE_LAT_LON_SHIFT);
                                List <Point3DCartesian> par2 = new ArrayList<>();
                                p2.project(proj, alpha, par2);

                                //Add parallel and projected parallel to the list
                                parallels.add(p2);
                                parallels_proj.add(par2);
                        }

                        //Create last parallel (upper bound of the interval, not hreater than MAX_LAT)
                        lat = max(min(lat_interval.max - GRATICULE_LAT_LON_SHIFT, MAX_LAT), MIN_LAT);
                        Parallel  p3 = new Parallel(lat, lon_interval, d_lon, GRATICULE_LAT_LON_SHIFT, GRATICULE_LAT_LON_SHIFT);
                        List <Point3DCartesian> par3 = new ArrayList<>();
                        p3.project(proj, alpha, par3);

                        //Add parallel and projected parallel to the list
                        parallels.add(p3);
                        parallels_proj.add(par3);
                }

                //Throw math exception: get error values
                catch (MathException error)
                {
                        //Get posible error values
                        lat_error[0] = lat;
                        lon_error[0] = error.getArg();

                        //Throw exception
                        throw error;
                }

                //Throw other exception: error in parser or incorrect equation
                catch (Exception error)
                {
                        throw error;
                }
        } 
}
