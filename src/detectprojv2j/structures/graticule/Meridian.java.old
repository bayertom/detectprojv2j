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
import detectprojv2j.exceptions.MathInvalidArgumentException;

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

                //Add first point (lower bound of the interval)
                lats.add(max(min(lat_interval.min_value + lat_min_shift, MAX_LAT), MIN_LAT));

                //Add intermediate points
                for (double lat_point = lat_start; lat_point <= lat_end; lat_point += dlat)
                {
                        lats.add(lat_point);
                }

                //Add last point (upper bound of the interval)
                lats.add(max(min(lat_interval.max_value - lat_max_shift, MAX_LAT), MIN_LAT));
        }


        public void project(final Projection proj, final double alpha, List <Point3DCartesian> mer)
        {
                //Project meridian
                for (final double lat : lats)
                {
                        double [] X={0}, Y = {0};
                        
                        //Project point
                        try
                        {
                                CartTransformation.latLontoXY(lat, lon, proj, alpha, X, Y);
                        }

                        //Throw exception
                        catch (MathException error)
                        {
                                //Throw new math error
                                throw new MathInvalidArgumentException ("MathInvalidArgumentException: error in coordinate function.", "Can not compute meridian points, lat = ", lat);
                        }

                        //Add projected point to the list
                        Point3DCartesian p_proj = new Point3DCartesian(X[0], Y[0], 0);
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
