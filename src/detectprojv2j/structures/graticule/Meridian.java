// Description: A meridian of the longitude given by the list of points

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

public class Meridian {
        
	private final double lon;                                             //Longitude of the meridian
	private final TInterval  lat_interval;                                //Latitude interval of the meridian
	private final double dlat;                                            //Latitude step;
	private final double lat_min_shift;                                   //Shift of the initial meridian point: [lat_min + lat_min_shift, lon], default = 0
	private final double lat_max_shift;                                   //Shift of the last meridian point: [lat_max - lat_max_shift, lon], default = 0
	private final List <Double> lats;                                     //Latitude of the meridian points

	public Meridian(final double lon_)
        {
                lon = lon_; 
                lat_interval = new TInterval (MIN_LAT, MAX_LAT);
                dlat = 10.0; 
                lat_min_shift = 0.0;
                lat_max_shift = 0;
                lats = new ArrayList<>();
                this.createMeridian();
        }
	
        public Meridian(final double lon_, final TInterval  lat_interval_, final double dlat_, final double lat_min_shift_, final double lat_max_shift_)
        {
			lon = lon_;
                        lat_interval = lat_interval_;
                        dlat = dlat_;
                        lat_min_shift = lat_min_shift_;
                        lat_max_shift = lat_max_shift_;
                        lats = new ArrayList<>();
                        this.createMeridian();
        }

	public double getLon() { return lon; }
	public TInterval getLatInterval() { return lat_interval; }
	public double getDLat() { return dlat; }
	public double getLatMinShift() { return lat_min_shift; }
	public double getLatMaxShift() { return lat_max_shift; }
	public List <Double> getLats() { return lats; }
        
        public void createMeridian()
        {
                //Create meridian

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
                                CartTransformation.latLontoXY(lat, lon, proj, alpha, lat_trans, lon_trans, X, Y);
                        }

                        //Throw exception: meridian crosses the singularity
                        catch (MathException error)
                        {
                                //Reduce longitude at the suspected point
                                final double lon_trans_r = CartTransformation.redLon0(lon_trans[0], proj.getLon0());

                                //Shift points in 2 orthogonal directions (lat / lon): lat shift
                                final double lat1_trans_shift = lat_trans[0] - 0.5 * GRATICULE_LAT_LON_SHIFT;
                                final double lat2_trans_shift = lat_trans[0] + 0.5 * GRATICULE_LAT_LON_SHIFT;
                                final double lat_trans_shift = (lat1_trans_shift < MAX_LAT) && (lat1_trans_shift > -MAX_LAT) ? lat1_trans_shift : lat2_trans_shift;

                                //Shift points in 2 orthogonal directions (lat / lon): lon shift
                                final double lon1_trans_shift_r = lon_trans_r - 0.5 * GRATICULE_LAT_LON_SHIFT;
                                final double lon2_trans_shift_r = lon_trans_r + 0.5 * GRATICULE_LAT_LON_SHIFT;
                                final double lon_trans_shift_r = (lon1_trans_shift_r < MAX_LON) && (lon1_trans_shift_r > -MAX_LON) ? lon1_trans_shift_r : lon2_trans_shift_r;
                                final double lon_trans_shift = CartTransformation.redLon0(lon_trans_shift_r, -proj.getLon0());

                                //Inverse transformation shifted points [lat_trans_shift, lon_trans], [lat_trans, lon_trans_shift] . [. , lon]
                                final double lat_shift_lon = CartTransformation.lonTransToLon(lat_trans_shift, lon_trans[0], proj.getCartPole().getLat(), proj.getCartPole().getLon(), proj.getLonDir());
                                final double lon_shift_lon = CartTransformation.lonTransToLon(lat_trans[0], lon_trans_shift, proj.getCartPole().getLat(), proj.getCartPole().getLon(), proj.getLonDir());

                                //Projected coordinates of shifted points
                                double [] X_shift = {0.0}, Y_shift = {0.0}, lat_trans_shift_temp = {0.0}, lon_trans_shift_temp = {0.0};

                                //Point [lat_trans, lon_trans] in the latitudinal direction shifted by +-dlat to [lat_trans +- dlat, lon_trans]
                                //Singularity probably remains in the longitudinal (meridian) direction: c = lon_trans
                                //It is aligned with [lat_trans, lon_trans] and [lat_trans, lon_trans +- dlon]
                                try
                                {
                                        CartTransformation.latLontoXY(proj.getR(), proj.getLat1(), proj.getLat2(), lat_trans_shift, lon_trans_r, 90.0, 0.0, proj.getLonDir(), 0.0, proj.getDx(), proj.getDy(), proj.getC(), proj.getX(), proj.getY(), alpha, lat_trans_shift_temp, lon_trans_shift_temp, X, Y);
                                }

                                //Throw error: singularity in the lon/lat coordinate
                                catch (MathException error2)
                                {
                                        //Singularity along the meridian - transformed meridian aligned with the singularity (geographic meridian); meridian needs to be shifted
                                        if (abs(lat_shift_lon - lon) < ANGLE_ROUND_ERROR)
                                                throw new MathLonSingularityException ("MathLonSingularitytException: error in coordinate function.", "Can not compute meridian points, lon = ", lon);

                                        //Singularity intersects the meridian
                                        else
                                                throw new MathLatSingularityException  ("MathLatSingularitytException: error in coordinate function.", "Can not compute meridian points, lat = ", lat);
                                }

                                //Point [lat_trans, lon_trans] shifted in the longitudinal direction by +-dlon to [lat_trans, lon_trans +- dlon]
                                //Singularity probably remains in the latitudinal (parallel) direction: c = lat_trans
                                //It is aligned with [lat_trans, lon_trans] and [lat_trans +- dlat, lon_trans]
                                try
                                {
                                        CartTransformation.latLontoXY(proj.getR(), proj.getLat1(), proj.getLat2(), lat_trans[0], lon_trans_shift_r, 90.0, 0.0, proj.getLonDir(), 0.0, proj.getDx(), proj.getDy(), proj.getC(), proj.getX(), proj.getY(), alpha, lat_trans_shift_temp, lon_trans_shift_temp, X, Y);
                                }

                                //Throw error: singularity in the lon/lat coordinate
                                catch (MathException error2)
                                {
                                        //Singularity along the meridian - transformed parallel aligned with the singularity (geographic meridian); meridian needs to be shifted
                                        if (abs(lon_shift_lon - lon) < ANGLE_ROUND_ERROR)
                                                throw new MathLonSingularityException ("MathLonSingularitytException: error in coordinate function.", "Can not compute meridian points, lon = ", lon);

                                        //Singularity intersects the meridian
                                        else
                                                throw new MathLatSingularityException ("MathLatSingularitytException: error in coordinate function.", "Can not compute meridian points, lat = ", lat);

                                }
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

}
