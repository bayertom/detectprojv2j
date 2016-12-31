// Description: transformation of the geographic coordinates (lat, lon) to (lat, lon)_trans. 

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

package detectprojv2j.algorithms.carttransformation;

import static java.lang.Math.*;

import detectprojv2j.types.*;
import static detectprojv2j.types.TTransformedLongitudeDirection.*;

import static detectprojv2j.consts.Consts.*;

import detectprojv2j.structures.point.Point3DGeographic;
import detectprojv2j.structures.projection.Projection;
import detectprojv2j.structures.point.Point3DCartesian;

import detectprojv2j.exceptions.MathInvalidArgumentException;

import java.util.List;

public class CartTransformation
{    
        public static double redLon0(final double lon, final double lon0) 
        { 
                return (lon - lon0 < MIN_LON ? 360.0 + (lon - lon0) : (lon - lon0 > MAX_LON ? (lon - lon0) - 360 : lon - lon0)); 
        }


        public static double latToLatTrans( final Point3DGeographic p, final Point3DGeographic pole)
        {
                //Transform latitude  ( lat, lon ) -> ( lat_tans ) using a cartographic pole (latp, lonp)
                return latToLatTrans(p.getLat(), p.getLon(), pole.getLat(), pole.getLon());
        }
        
        public static double latTransToLat( final Point3DGeographic p, final Point3DGeographic pole, final TTransformedLongitudeDirection lon_direction)
        {
                //Transform latitude  ( lat_trans, lon_trans ) -> ( lat ) using a cartographic pole (latp, lonp)
                return latTransToLat(p.getLat(), p.getLon(), pole.getLat(), pole.getLon(), lon_direction);
        }

        public static double lonToLonTrans( final Point3DGeographic p, final Point3DGeographic pole, final TTransformedLongitudeDirection lon_direction)
        {
                //Transform longitude  ( lat, lon ) -> ( lon_tans ) using a cartographic pole (latp, lonp)
                return lonToLonTrans(p.getLat(), p.getLon(), pole.getLat(), pole.getLon(), lon_direction);
        }
        
        public static double lonTransToLon( final Point3DGeographic p, final Point3DGeographic pole, final TTransformedLongitudeDirection lon_direction)
        {
                //Transform longitude  ( lat_trans, lon_trans ) -> ( lon_tans ) using a cartographic pole (latp, lonp)
                return lonTransToLon(p.getLat(), p.getLon(), pole.getLat(), pole.getLon(), lon_direction);
        }

        public static double latToLatTrans( final double lat, final double lon, final double latp, final double lonp)
        {
                //Transform latitude  ( lat, lon ) -> ( lat_tans ) using a cartographic pole (latp, lonp)

                //Throw exception: bad lat
                if (abs(lat) > MAX_LAT) {
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: ", "can not convert lat to lat_trans, lat > +- Pi/2", lat);
                }

                //Throw exception: bad lon
                if (abs(lon) > MAX_LON) {
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: ", "can not convert lat to lat_trans, lon > +- Pi", lon);
                }

                //Projection in normal aspect
                if (abs(MAX_LAT - latp) < ANGLE_ROUND_ERROR) {
                        return lat;
                }

                //Same coordinates as the cartographic pole, singular point
                if ((abs(lon - lonp) < ANGLE_ROUND_ERROR) && (abs(lat - latp) < ANGLE_ROUND_ERROR)) {
                        return MAX_LAT;
                }

                //Compute latitude
                double lat_trans_asin = sin(lat * PI / 180.0) * sin(latp * PI / 180.0) + cos(lat * PI / 180.0) * cos(latp * PI / 180.0) * cos((lonp - lon) * PI / 180.0);

                //Throw exception
                if ((lat_trans_asin > 1.0 + ARGUMENT_ROUND_ERROR) || (lat_trans_asin < -1.0 - ARGUMENT_ROUND_ERROR)) {
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: ", "can not convert lat to lat_trans, asin(arg), arg = ", lat_trans_asin);
                }

                //Correct latitude
                if (lat_trans_asin > 1.0) {
                        return MAX_LAT;
                }

                //Correct latitude
                if (lat_trans_asin < -1.0) {
                        return MIN_LAT;
                }

                //Compute transformed latitude
                return asin(lat_trans_asin) * 180.0 / PI;
        }

        
        public static double latTransToLat( final double lat_trans, final double lon_trans, final double latp, final double lonp, final TTransformedLongitudeDirection lon_direction)
        {
                //Transform latitude  ( lat_trans, lon_trans ) -> ( lat ) using a cartographic pole (latp, lonp)

                //Throw exception: bad lat
                if (abs(lat_trans) > MAX_LAT) {
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: ", "can not convert lat_trans to lat, lat_trans > +- Pi/2", lat_trans);
                }

                //Throw exception: bad lon
                if (abs(lon_trans) > MAX_LON) {
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: ", "can not convert lat_trans to lat, lon > +- Pi", lon_trans);
                }

                //Projection in normal aspect
                if (abs(MAX_LAT - latp) < ANGLE_ROUND_ERROR) {
                        return lat_trans;
                }

                //Same coordinates as the cartographic pole, singular point
                if ((abs(lon_trans - lonp) < ANGLE_ROUND_ERROR) && (abs(lat_trans - latp) < ANGLE_ROUND_ERROR)) {
                        return MAX_LAT;
                }
                
                //Reversed direction 2
                double lon_trans2 = lon_trans;

                //Normal direction 2
                if (lon_direction == NormalDirection2)
                        lon_trans2 = -lon_trans;

                //Reversed direction (JTSK)
                else if (lon_direction == ReversedDirection)
                {
                        if (lon_trans < 0)
                                lon_trans2 = lon_trans - 180;
                        else
                                lon_trans2 = lon_trans + 180;
                }

                //Normal direction
                else if (lon_direction == NormalDirection)
                {
                        if (lon_trans < 0)
                                lon_trans2 = -180 - lon_trans;
                        else
                                lon_trans2 = 180 - lon_trans;
                }

                //Compute latitude
                double lat_asin = sin(lat_trans * PI / 180.0) * sin(latp * PI / 180.0) + cos(lat_trans * PI / 180.0) * cos(latp * PI / 180.0) * cos(lon_trans2 * PI / 180.0);

                //Throw exception
                if ((lat_asin > 1.0 + ARGUMENT_ROUND_ERROR) || (lat_asin < -1.0 - ARGUMENT_ROUND_ERROR)) {
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: ", "can not convert lat_trans to lat, asin(arg), arg = ", lat_asin);
                }

                //Correct latitude
                if (lat_asin > 1.0) {
                        return MAX_LAT;
                }

                //Correct latitude
                if (lat_asin < -1.0) {
                        return MIN_LAT;
                }

                //Compute transformed latitude
                return asin(lat_asin) * 180.0 / PI;
        }
        
        
        public static double lonToLonTrans( final double lat, final double lon, final double latp, final double lonp, final TTransformedLongitudeDirection lon_direction)
        {
                //Transform longitude  ( lat, lon ) -> ( lon_tans ) using a cartographic pole (latp, lonp)

                //Throw exception: bad lat
                if (abs(lat) > MAX_LAT) {
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: ", " can not convert lon to lon_trans, lat > +- Pi/2", lat);
                }

                //Throw exception: bad lon
                if (abs(lon) > MAX_LON) {
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: ", " can not convert lon to lon_trans, lon > +- Pi", lon);
                }
                
                //Projection in normal position
                if ((abs(MAX_LAT - latp) < ANGLE_ROUND_ERROR) && (abs(lonp) < ANGLE_ROUND_ERROR))
                {
                        return lon;
                }
                
                //Compute lon_trans: Reversed direction 2
                double lon_trans = atan2(cos(lat * PI / 180) * sin((lon - lonp) * PI / 180),  sin(lat * PI / 180) * cos(latp * PI / 180) - cos((lon - lonp) * PI / 180) * sin(latp * PI / 180) * cos(lat * PI / 180)) * 180 / PI;

                //Normal direction 2
                if (lon_direction == NormalDirection2) {
                        lon_trans = -lon_trans;
                } 

                //Reversed direction (JTSK)
                else if (lon_direction == ReversedDirection) {
                        if (lon_trans < 0) {
                                lon_trans = lon_trans + 180;
                        } else {
                                lon_trans = lon_trans - 180;
                        }
                }
                
                //Normal direction
                else if (lon_direction == NormalDirection) {
                        if (lon_trans < 0) {
                                lon_trans = -180 - lon_trans;
                        } else 
                        {
                                lon_trans = 180 - lon_trans;
                        }
                }

                return lon_trans;
        }
        
        
        public static double lonTransToLon( final double lat_trans, final double lon_trans, final double latp, final double lonp, final TTransformedLongitudeDirection lon_direction)
        {
                //Transform longitude  ( lat_trans, lon_trans ) -> ( lon ) using a cartographic pole (latp, lonp)

                //Throw exception: bad lat
                if (abs(lat_trans) > MAX_LAT) {
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: ", " can not convert lon_trans to lon, lat_trans > +- Pi/2", lat_trans);
                }

                //Throw exception: bad lon
                if (abs(lon_trans) > MAX_LON) {
                        throw new MathInvalidArgumentException ("MathInvalidArgumentException: ", " can not convert lon_trans to lon, lon_trans > +- Pi", lon_trans);
                }
                
                //Projection in normal position
                if ((abs(MAX_LAT - latp) < ANGLE_ROUND_ERROR) && (abs(lonp) < ANGLE_ROUND_ERROR))
                {
                        return lonp + lon_trans;
                }
                
                //Reversed direction 2
                double lon_trans2 = lon_trans;

                //Normal direction 2
                if (lon_direction == NormalDirection2)
                        lon_trans2 = -lon_trans;

                //Reversed direction (JTSK)
                else if (lon_direction == ReversedDirection)
                {
                        if (lon_trans < 0)
                                lon_trans2 = lon_trans - 180;
                        else
                                lon_trans2 = lon_trans + 180;
                }

                //Normal direction
                else if (lon_direction == NormalDirection)
                {
                        if (lon_trans < 0)
                                lon_trans2 = -180 - lon_trans;
                        else
                                lon_trans2 = 180 - lon_trans;
                }

                //Compute lon_trans: Reversed direction 2
                double dlon = atan2(cos(lat_trans * PI / 180) * sin(lon_trans2 * PI / 180),  sin(lat_trans * PI / 180) * cos(latp * PI / 180) - cos(lon_trans2 * PI / 180) * sin(latp * PI / 180) * cos(lat_trans * PI / 180)) * 180 / PI;

                return lonp + dlon;
        }
        
        
        
        public static void latLontoXY(final double lat, final double lon, final Projection proj, final double alpha, double [] X, double [] Y)
        {
                latLontoXY(proj.getR(), proj.getLat1(), proj.getLat2(), lat, lon, proj.getCartPole().getLat(), proj.getCartPole().getLon(), proj.getLonDir(), proj.getLon0(), proj.getDx(), proj.getDy(), proj.getC(), proj.getX(), proj.getY(), alpha, X, Y);
        }
        
        
        public static void latLontoXY(final double R, final double lat1, final double lat2, final double lat, final double lon, final double latp, final double lonp, final TTransformedLongitudeDirection lon_dir, final double lon0, final double dx, final double dy, final double c, final ICoordFunctionProj getX, final ICoordFunctionProj getY, final double alpha, double [] X, double [] Y)
        {
                //Convert a geographic point to the Cartesian coordinates

                //(lat, lon) -> (lat_trans, lon_trans)
                final double lat_trans = CartTransformation.latToLatTrans(lat, lon, latp, lonp);
                final double lon_trans = CartTransformation.lonToLonTrans(lat, lon, latp, lonp, lon_dir);
                
                //(lat_trans, lon_trans) -> (X, Y)
                final double Xr = getX.f(R, lat1, lat2, lat_trans, lon_trans, lon0, 0.0, 0.0, c);
                final double Yr = getY.f(R, lat1, lat2, lat_trans, lon_trans, lon0, 0.0, 0.0, c);

                //Compute Helmert transformation coefficients (for the M8 method)
                final double q1 = cos(alpha * PI / 180);
                final double q2 = sin(alpha * PI / 180);

                //Rotate points (for the M8 method)
                X[0] = Xr * q1 - Yr * q2 + dx; 
                Y[0] = Xr * q2 + Yr * q1 + dy; 
        }
        
        
        public static void latsLonstoXY (final List <Point3DGeographic> reference_points, final Projection proj, final double alpha, List <Point3DCartesian> projected_points)
        {
                //Convert all geographic points to the Cartesian coordinates
                try
                {
                        for (Point3DGeographic p:reference_points)
                        {
                                double [] X = {0}, Y = {0};
                                CartTransformation.latLontoXY(p.getLat(), p.getLon(), proj, alpha, X, Y);
                                projected_points.add(new Point3DCartesian(X[0], Y[0], 0));
                        }
                }

                catch(Exception e)
                {
                        e.printStackTrace();
                }
        }
                        
}
