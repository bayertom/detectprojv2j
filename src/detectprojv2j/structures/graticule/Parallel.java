// Description: A parallel of the latitude given by the list of points

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

package detectprojv2j.structures.graticule;

import java.util.List;
import static java.lang.Math.*;
import java.io.PrintStream;
import java.util.ArrayList;

import detectprojv2j.types.TInterval;

import detectprojv2j.structures.point.Point3DGeographic;
import detectprojv2j.structures.point.Point3DCartesian;
import detectprojv2j.structures.projection.Projection;

import static detectprojv2j.consts.Consts.*;

import detectprojv2j.algorithms.carttransformation.CartTransformation;
import detectprojv2j.algorithms.round.Round;

import detectprojv2j.exceptions.MathException;
import detectprojv2j.exceptions.MathLatSingularityException;
import detectprojv2j.exceptions.MathLonSingularityException;

public class Parallel {
        
        private final double lat;                                             //Latitude of the parallel
	private final TInterval  lon_interval;                                //Longitude interval of the parallel
	private final double dlon;                                            //Longitude step
	private final double lon_min_shift;                                   //Shift of the initial parallel point: [lat, lon_min + lon_min_shift], default = 0
	private final double lon_max_shift;                                   //Shift of the last parallel point: [lat, lon_max - lon_max_shift], default = 0
	private final List <Double> lons;                                     //Longitude of the parallel points

	public Parallel(final double lat_)
        {
                lat = lat_; 
                lon_interval = new TInterval (MIN_LON, MAX_LON);
                dlon = 10.0; 
                lon_min_shift = 0.0;
                lon_max_shift = 0;
                lons = new ArrayList<>();
                this.createParallel();
        }
	
        public Parallel(final double lat_, final TInterval lon_interval_, final double dlon_, final double lon_min_shift_, final double lon_max_shift_)
        {
			lat = lat_;
                        lon_interval = lon_interval_;
                        dlon = dlon_;
                        lon_min_shift = lon_min_shift_;
                        lon_max_shift = lon_max_shift_;
                        lons = new ArrayList<>();
                        this.createParallel();
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
                                CartTransformation.latLontoXY(lat, lon, proj, alpha, lat_trans, lon_trans, X, Y);
                        }

                        //Throw exception: parallel crosses the singularity
                        catch (MathException error)
                        {
                                //Reduce longitude at the suspected point
                                final double lon_trans_r = CartTransformation.redLon0(lon_trans[0], proj.getLon0());

                                //Shift points in 2 orthogonal directions (lat / lon): lat shift
                                final double lat1_trans_shift = lat_trans[0] - 0.5 * GRATICULE_LAT_LON_SHIFT;
                                final double lat2_trans_shift = lat_trans[0] + 0.5 * GRATICULE_LAT_LON_SHIFT;
                                final double lat_trans_shift = (lat1_trans_shift < MAX_LAT) && (lat1_trans_shift > -MAX_LAT) ? lat1_trans_shift : lat2_trans_shift ;

                                //Shift points in 2 orthogonal directions (lat / lon): lon shift
                                final double lon1_trans_shift_r = lon_trans_r - 0.5 * GRATICULE_LAT_LON_SHIFT;
                                final double lon2_trans_shift_r = lon_trans_r + 0.5 * GRATICULE_LAT_LON_SHIFT;
                                final double lon_trans_shift_r = (lon1_trans_shift_r < MAX_LON) && (lon1_trans_shift_r > -MAX_LON) ? lon1_trans_shift_r : lon2_trans_shift_r;
                                final double lon_trans_shift = CartTransformation.redLon0(lon_trans_shift_r, -proj.getLon0());

                                //Inverse transformation shifted points [lat_trans_shift, lon_trans], [lat_trans, lon_trans_shift] . [lat, .]
                                final double lat_shift_lat = CartTransformation.latTransToLat(lat_trans_shift, lon_trans[0], proj.getCartPole().getLat(), proj.getCartPole().getLon(), proj.getLonDir());
                                final double lon_shift_lat = CartTransformation.latTransToLat(lat_trans[0], lon_trans_shift, proj.getCartPole().getLat(), proj.getCartPole().getLon(), proj.getLonDir());

                                //Projected coordinates of shifted points
                                double [] X_shift = {0.0}, Y_shift = {0.0}, lat_trans_shift_temp = {0.0}, lon_trans_shift_temp = {0.0};

                                //Point [lat_trans, lon_trans] shifted in the latitudinal direction by +-dlat to [lat_trans +- dlat, lon_trans]
                                //Singularity probably remains in the longitudinal (meridian) direction: c = lon_trans
                                //It is aligned with [lat_trans, lon_trans] and [lat_trans, lon_trans +- dlon]
                                try
                                {
                                        CartTransformation.latLontoXY(proj.getR(), proj.getLat1(), proj.getLat2(), lat_trans_shift, lon_trans_r, 90.0, 0.0, proj.getLonDir(), 0.0, proj.getDx(), proj.getDy(), proj.getC(), proj.getX(), proj.getY(), alpha, lat_trans_shift_temp, lon_trans_shift_temp, X, Y);
                                }

                                //Throw error: singularity in the lat/lon coordinate
                                catch (MathException error2)
                                {
                                        //Singularity along the parallel - transformed meridian aligned with the singularity (geographic parallel); parallel needs to be shifted
                                        if (abs(lat_shift_lat - lat) < ANGLE_ROUND_ERROR)
                                                throw new MathLatSingularityException ("MathLatSingularitytException: error in coordinate function.", "Can not compute parallel points, lat = ", lat);

                                        //Singularity intersects the parallel
                                        else
                                                throw new MathLonSingularityException ("MathLonSingularitytException: error in coordinate function.", "Can not compute parallel points, lon = ", lon);
                                }

                                //Point [lat_trans, lon_trans] shifted in the longitudinal direction by +-dlon to [lat_trans, lon_trans +- dlon]
                                //Singularity probably remains in the latitudinal (parallel) direction: c = lat_trans
                                //Is it aligned with [lat_trans, lon_trans] and [lat_trans +- dlat, lon_trans] (a parallel direction)
                                try
                                {
                                        CartTransformation.latLontoXY(proj.getR(), proj.getLat1(), proj.getLat2(), lat_trans[0], lon_trans_shift_r, 90.0, 0.0, proj.getLonDir(), 0.0, proj.getDx(), proj.getDy(), proj.getC(), proj.getX(), proj.getY(), alpha, lat_trans_shift_temp, lon_trans_shift_temp, X, Y);
                                }

                                //Throw error: singularity in the lat/lon coordinate
                                catch (MathException error2)
                                {
                                        //Singularity along the parallel - transformed parallel aligned with the singularity (geographic parallel); parallel needs to be shifted
                                        if (abs(lon_shift_lat - lat) < ANGLE_ROUND_ERROR)
                                                throw new MathLatSingularityException ("MathLatSingularitytException: error in coordinate function.", "Can not compute parallel points, lat = ", lat);

                                        //Singularity intersects the parallel
                                        else
                                                throw new MathLonSingularityException ("MathLonSingularitytException: error in coordinate function.", "Can not compute parallel points, lon = ", lon);
                                }			
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

}
