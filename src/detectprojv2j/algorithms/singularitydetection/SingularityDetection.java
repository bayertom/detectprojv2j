// Description: Detection of a function singularity

// Copyright (c) 2016 - 2018
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

package detectprojv2j.algorithms.singularitydetection;


import static java.lang.Math.*;

import detectprojv2j.structures.projection.Projection;

import static detectprojv2j.consts.Consts.*;

import detectprojv2j.algorithms.carttransformation.CartTransformation;

import detectprojv2j.exceptions.MathException;
import detectprojv2j.exceptions.MathLatSingularityException;
import detectprojv2j.exceptions.MathLonSingularityException;


public class SingularityDetection 
{
        public static boolean checkDiscontinuityLR(final double fxi_2, final double fxi_1, final double fxi, final double fxi1, final double fxi2, final double fmax)
        {
                //Check for a jump discontinuity
                //Criterion by Oliveira and Liu (2008)
                final double LR_MAX = 0.9;

                if (Double.isNaN(fxi_2) || Double.isInfinite(fxi_2) ||abs(fxi_2) > fmax)
                {
                        return true;
                }

                if (Double.isNaN(fxi_1) || Double.isInfinite(fxi_1) || abs(fxi_1) > fmax)
                {
                        return true;
                }

                if (Double.isNaN(fxi) || Double.isInfinite(fxi) || abs(fxi) > fmax)
                {
                        return true;
                }

                if (Double.isNaN(fxi1) || Double.isInfinite(fxi1) || abs(fxi1) > fmax)
                {
                        return true;
                }

                if (Double.isNaN(fxi2) || Double.isInfinite(fxi2) || abs(fxi2) > fmax)
                {
                        return true;
                }

                //Compute LR criterion
                final double fr = 3 * fxi - 4 * fxi1 + fxi2;
                final double fl = 3 * fxi - 4 * fxi_1 + fxi_2;

                //Return true / false for a given point and threshold
                final double lr = abs((fr * fr - fl * fl) / (fr * fr + fl * fl + 0.0001));
                return lr > LR_MAX;
        }


        public static void checkProjDiscontinuity(final double lat, final double lon, final Projection proj, final double alpha, final double fmax, final double h)
        {
                //Check discontinuity at a point [lat, lon]
               	//Check discontinuity at a point [lat, lon]
                boolean [] id_lat = { false, false, false, false, false}, id_lon = { false, false, false, false, false};
                double [] lats_trans = { 0.0, 0.0, 0.0, 0.0, 0.0 }, lons_trans = { 0.0, 0.0, 0.0, 0.0, 0.0 }, lats = { 0, 0, 0, 0, 0 }, lons = { 0.0, 0.0, 0.0, 0.0, 0.0 };
                double [] X_lat = { 0.0, 0.0, 0.0, 0.0, 0.0 }, Y_lat = { 0.0, 0.0, 0.0, 0.0, 0.0 }, X_lon = { 0.0, 0.0, 0.0, 0.0, 0.0 }, Y_lon = { 0.0, 0.0, 0.0, 0.0, 0.0 };

                //doubleransform point to a pole K
                double lat_trans = CartTransformation.latToLatTrans(lat, lon, proj.getCartPole().getLat(), proj.getCartPole().getLon());
                double lon_trans = CartTransformation.lonToLonTrans(lat, lon, proj.getCartPole().getLat(), proj.getCartPole().getLon(), proj.getLonDir());
                double lon_trans_r = CartTransformation.redLon0(lon_trans, proj.getLon0());

                //Shift lat if is too close to the bounds
                if ((lat_trans < MAX_LAT) && (MAX_LAT - lat_trans < 2.0 * h))
                        lat_trans = MAX_LAT - 2.0 * h;
                else if ((lat_trans > MIN_LAT) && (lat_trans - MIN_LAT < 2.0 * h))
                        lat_trans = MIN_LAT + 2.0 * h;

                //Shift lon if is too close to the bounds
                if ((lon_trans_r < MAX_LON) && (MAX_LON - lon_trans_r < 2.0 * h))
                        lon_trans_r = MAX_LON - 2.0 * h;
                else if ((lon_trans_r > MIN_LON) && (lon_trans_r - MIN_LON < 2.0 * h))
                        lon_trans_r = MIN_LON + 2.0 * h;

                //Initialize lat_trans, lon_trans as the smallest of 5 elements
                lat_trans -= 2.0 * h; lon_trans_r -= 2.0 * h;

                //Shift point in lat/lon directions
                for (int i = 0; i < 5; i++, lat_trans+=h, lon_trans_r +=h)
                {
                        lats_trans[i] = lat_trans;
                        lons_trans[i] = lon_trans_r;
                }

                //Check infinite discontinuities in lat/lon directions
                for (int i = 0; i < 5; i++)
                {
                        //Lat direction
                        double [] latt = {0.0}, lont = {0.0}, X = {0.0}, Y = {0.0};
                        try
                        {
                                CartTransformation.latLonToXY(lats_trans[i], lons_trans[2], proj.getR(), proj.getLat1(), proj.getLat2(), 90.0, 0.0, proj.getLonDir(), 0.0, proj.getDx(), proj.getDy(), proj.getC(), proj.getX(), proj.getY(), alpha, latt, lont, X, Y);
                                X_lat[i] = X[0]; Y_lat[i] = Y[0];
                        }

                        catch (MathException error)
                        {
                                id_lat[i] = true;
                        }

                        //Lon direction
                        try
                        {
                                CartTransformation.latLonToXY(lats_trans[2], lons_trans[i], proj.getR(), proj.getLat1(), proj.getLat2(), 90.0, 0.0, proj.getLonDir(), 0.0, proj.getDx(), proj.getDy(), proj.getC(), proj.getX(), proj.getY(), alpha, latt, lont, X, Y);
                                X_lon[i] = X[0]; Y_lon[i] = Y[0];
                        }

                        catch (MathException error)
                        {
                                id_lon[i] = true;
                        }

                        //doubleest Nan anf Inf for both coordinates X, Y
                        id_lat[i] = id_lat[i] || Double.isNaN(X_lat[i]) || Double.isInfinite(X_lat[i]) || Double.isNaN(Y_lat[i]) || Double.isInfinite(Y_lat[i]);
                        id_lon[i] = id_lon[i] || Double.isNaN(X_lon[i]) || Double.isInfinite(X_lon[i]) || Double.isNaN(Y_lon[i]) || Double.isInfinite(Y_lon[i]);
                }

                //doubleest, if an infinite discontinuity in the latitude/longitude direction exists
                final boolean lon_direction_inf_sing = id_lat[0] && id_lat[1] && id_lat[2] && id_lat[3] && id_lat[4];
                final boolean lat_direction_inf_sing = id_lon[0] && id_lon[1] && id_lon[2] && id_lon[3] && id_lon[4];

                //Infinite discontinuities only on a subset of points
                final boolean lat_direction_subset_inf_sing = id_lat[0] || id_lat[1] || id_lat[2] || id_lat[3] || id_lat[4];
                final boolean lon_direction_subset_inf_sing = id_lon[0] || id_lon[1] || id_lon[2] || id_lon[3] || id_lon[4];

                //Discontinuity found but it can not be classified, return
                if ((lat_direction_subset_inf_sing) && (lon_direction_subset_inf_sing) && (!lon_direction_inf_sing) && (!lat_direction_inf_sing))
                        return;

                //Check for the jump discontinuity in lat_trans direction
                boolean lat_direction_jump_sing = checkDiscontinuityLR(X_lat[0], X_lat[1], X_lat[2], X_lat[3], X_lat[4], fmax);
                lat_direction_jump_sing = lat_direction_jump_sing || checkDiscontinuityLR(Y_lat[0], Y_lat[1], Y_lat[2], Y_lat[3], Y_lat[4], fmax);

                //Check for the jump discontinuity in lon_trans direction
                boolean lon_direction_jump_sing = checkDiscontinuityLR(X_lon[0], X_lon[1], X_lon[2], X_lon[3], X_lon[4], fmax);
                lon_direction_jump_sing = lon_direction_jump_sing || checkDiscontinuityLR(Y_lon[0], Y_lon[1], Y_lon[2], Y_lon[3], Y_lon[4], fmax);

                //No discontinuity found, return
                if (!(lon_direction_inf_sing || lat_direction_inf_sing || lat_direction_subset_inf_sing || lon_direction_subset_inf_sing ||
                      lat_direction_jump_sing || lon_direction_jump_sing))
                        return;

                //Infinite or jump discontinuity in lat trans direction found
                //Check, whether lat_trans singularity direction corresponds with lat / lon direction
                for (int i = 0; i < 5; i++)
                {
                        final double lat_trans_i = (lat_direction_inf_sing || lat_direction_jump_sing) ? lats_trans[i] : lats_trans[2];
                        final double lon_trans_i = (lat_direction_inf_sing || lat_direction_jump_sing) ? lons_trans[2]: CartTransformation.redLon0(lons_trans[i], -proj.getLon0());

                        lats[i] = CartTransformation.latTransToLat(lat_trans_i, lon_trans_i, proj.getCartPole().getLat(), proj.getCartPole().getLon(), proj.getLonDir());
                        lons[i] = CartTransformation.lonTransToLon(lat_trans_i, lon_trans_i, proj.getCartPole().getLat(), proj.getCartPole().getLon(), proj.getLonDir());
                }

                //Get lat/lon differences of tested points
                double dlat = 0.0, dlon = 0.0;
                for (int i = 0; i < 4; i++)
                {
                        dlat += abs(lats[i + 1] - lats[i]);
                        dlon += abs(lons[i + 1] - lons[i]);
                }

                //Infinite singularity in the lat direction
                if ((dlat < ANGLE_ROUND_ERROR) && (dlon > ANGLE_ROUND_ERROR) && (lat_direction_inf_sing || lon_direction_inf_sing))
                        throw new MathLatSingularityException("MathLatSingularitytException: error in coordinate function.", "Can not compute meridian points, lat = ", lat);

                //Infinite singularity in the lon direction
                else if ((dlon < ANGLE_ROUND_ERROR) && (dlat > ANGLE_ROUND_ERROR) && (lat_direction_inf_sing || lon_direction_inf_sing))
                        throw new MathLonSingularityException("MathLatSingularitytException: error in coordinate function.", "Can not compute parallel points, lon = ", lon);

                //Jump singularity in the lon direction
                if ((dlat < ANGLE_ROUND_ERROR) && (dlon > ANGLE_ROUND_ERROR) && (lat_direction_jump_sing || lon_direction_jump_sing))
                        throw new MathLonSingularityException("MathLatSingularitytException: error in coordinate function.", "Can not compute parallel points, lon = ", lon);

                //Jump singularity in the lat direction
                else if ((dlon < ANGLE_ROUND_ERROR) && (dlat > ANGLE_ROUND_ERROR) && (lat_direction_jump_sing || lon_direction_jump_sing))
                        throw new MathLatSingularityException("MathLatSingularitytException: error in coordinate function.", "Can not compute meridian points, lat = ", lat);
        }
}
        
