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
import detectprojv2j.exceptions.MathInvalidArgumentException;


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

                //Add first point (lower bound of the interval)
                lons.add(max(min(lon_interval.min_value + lon_min_shift, MAX_LON), MIN_LON));

                //Add intermediate points
                for (double lon_point = lon_start; lon_point <= lon_end; lon_point += dlon)
                {
                        lons.add(lon_point);
                }

                //Add last point (upper bound of the interval)
                lons.add(max(min(lon_interval.max_value - lon_max_shift, MAX_LON), MIN_LON));
        }


        public void project(final Projection proj, final double alpha, List <Point3DCartesian> par)
        {
                //Project parallel
                Point3DGeographic pole = proj.getCartPole();

                //Process all meridian points
                for (final double lon : lons)
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
                                throw new MathInvalidArgumentException ("MathInvalidArgumentException: error in coordinate function.", "Can not compute parallel points, lon = ", lon);
                        }

                        //Add projected point to the list
                        Point3DCartesian p_proj = new Point3DCartesian(X[0], Y[0], 0);
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
