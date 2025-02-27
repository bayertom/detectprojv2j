// Description: transformation of the geographic coordinates (lat, lon) to (lat, lon)_trans. 

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

package detectprojv2j.algorithms.carttransformation;

import static java.lang.Math.*;
import java.util.ArrayList;
import java.util.List;

import detectprojv2j.types.*;
import static detectprojv2j.types.TTransformedLongitudeDirection.*;

import static detectprojv2j.consts.Consts.*;

import detectprojv2j.structures.point.Point3DGeographic;
import detectprojv2j.structures.projection.Projection;
import detectprojv2j.structures.point.Point3DCartesian;

import detectprojv2j.exceptions.MathInvalidArgumentException;


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
                if (abs(lat_trans - MAX_LAT) < ANGLE_ROUND_ERROR) {
                        return latp;
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
                
                //Reversed direction 2 (Mode M4)
                //lon_trans is measured from the meridian arc(i.e., from its part north of K) of the base system passing the new pole K
                //in the clockwise direction
                double lon_trans = atan2(cos(lat * PI / 180) * sin((lon - lonp) * PI / 180),  sin(lat * PI / 180) * cos(latp * PI / 180) - cos((lon - lonp) * PI / 180) * sin(latp * PI / 180) * cos(lat * PI / 180)) * 180 / PI;

                //Normal direction 2 (Mode M3), DEFAULT
                //lon_trans is measured from the meridian arc(i.e., from its part north of K) of the base system passing the new pole K
                //in the counterclockwise direction.
                if (lon_direction == NormalDirection2) {
                        lon_trans = -lon_trans;
                } 

                //Reversed direction (Mode M2)
                //lon_trans is measured from the “extended” meridian arc(i.e., from its part south of K) of the base system passing the new pole K
                //in the clockwise direction, which represents the new prime meridian.
                else if (lon_direction == ReversedDirection) {
                        if (lon_trans < 0) 
                        {
                                lon_trans = lon_trans + 180;
                        } 
                        else 
                        {
                                lon_trans = lon_trans - 180;
                        }
                }
                
                //Normal direction (Mode M1)
                //lon_trans is measured from the “extended” meridian arc(i.e., from its part south of K) of the base system passing the new pole K
                //in the counterclockwise direction.
                else if (lon_direction == NormalDirection) {
                        if (lon_trans < 0) 
                        {
                                lon_trans = -180 - lon_trans;
                        } 
                        else 
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
                        //lon = lonp + lon_trans
                        final double lon = redLon0(lonp, -lon_trans); 
                        
                        return lon;
                }
                
                //Same coordinates as the cartographic pole: singular point
                //if ((abs(lon_trans - lonp) < ANGLE_ROUND_ERROR) && (abs(lat_trans - latp) < ANGLE_ROUND_ERROR))
                //{
                //
                //}
                
                //Reversed direction 2 (Mode M4)
                //lon_trans is measured from the meridian arc(i.e., from its part north of K) of the base system passing the new pole K
                //in the clockwise direction
                double lon_trans2 = lon_trans;

                //Normal direction 2 (Mode M3), DEFAULT
                //lon_trans is measured from the meridian arc(i.e., from its part north of K) of the base system passing the new pole K
                //in the counterclockwise direction.
                if (lon_direction == NormalDirection2)
                        lon_trans2 = -lon_trans;

                //Reversed direction (Mode M2)
                //lon_trans is measured from the “extended” meridian arc(i.e., from its part south of K) of the base system passing the new pole K
                //in the clockwise direction, which represents the new prime meridian.
                else if (lon_direction == ReversedDirection)
                {
                        if (lon_trans < 0)
                                lon_trans2 = lon_trans - 180;
                        else
                                lon_trans2 = lon_trans + 180;
                }

                //Normal direction (Mode M1)
                //lon_trans is measured from the “extended” meridian arc(i.e., from its part south of K) of the base system passing the new pole K
                //in the counterclockwise direction.
                else if (lon_direction == NormalDirection)
                {
                        if (lon_trans < 0)
                                lon_trans2 = -180 - lon_trans;
                        else
                                lon_trans2 = 180 - lon_trans;
                }

                //Compute dlon
                double dlon = atan2(cos(lat_trans * PI / 180) * sin(lon_trans2 * PI / 180),  sin(lat_trans * PI / 180) * cos(latp * PI / 180) - cos(lon_trans2 * PI / 180) * sin(latp * PI / 180) * cos(lat_trans * PI / 180)) * 180 / PI;

                //lon = lonp + dlon
                final double lon = redLon0(lonp, -dlon); 
                   
                return lon;
        }
        

        public static void latLonToXY(final double lat, final double lon, final Projection proj, final double alpha, double [] lat_trans, double [] lon_trans, double [] X, double [] Y)
        {
                //Convert a geographic point to the Cartesian coordinates using projection equations
                latLonToXY(lat, lon, proj.getR(), proj.getLat1(), proj.getLat2(), proj.getCartPole().getLat(), proj.getCartPole().getLon(), proj.getLonDir(), proj.getLon0(), proj.getDx(), proj.getDy(), proj.getC(), proj.getX(), proj.getY(), alpha, lat_trans, lon_trans, X, Y);
        }
        
        
        public static void latLonToXY(final double lat, final double lon, final double R, final double lat1, final double lat2, final double latp, final double lonp, final TTransformedLongitudeDirection lon_dir, final double lon0, final double dx, final double dy, final double c, final ICoordFunctionProj F, final ICoordFunctionProj G, final double alpha, double [] lat_trans, double [] lon_trans, double [] X, double [] Y)
        {
                //Convert a geographic point to the Cartesian coordinates using projection equations

                //(lat, lon) -> (lat_trans, lon_trans)
                lat_trans[0] = CartTransformation.latToLatTrans(lat, lon, latp, lonp);
                lon_trans[0] = CartTransformation.lonToLonTrans(lat, lon, latp, lonp, lon_dir);
                
                //(lat_trans, lon_trans) -> (X, Y)
                final double Xr = F.f(lat_trans[0], lon_trans[0], R, lat1, lat2, lon0, 0.0, 0.0, c);
                final double Yr = G.f(lat_trans[0], lon_trans[0], R, lat1, lat2, lon0, 0.0, 0.0, c);

                //Compute Helmert transformation coefficients (for the M8 method)
                final double q1 = cos(alpha * PI / 180);
                final double q2 = sin(alpha * PI / 180);

                //Rotate points (for the M8 method)
                X[0] = Xr * q1 - Yr * q2 + dx; 
                Y[0] = Xr * q2 + Yr * q1 + dy; 
        }
        
        
        public static void XYToLatLon(final double X, final double Y, final Projection proj, final double alpha, double [] lat_trans, double [] lon_trans, double [] lat, double [] lon)
        {
                //Convert a point in the Cartesian coordinates to geographic using inverse projection equations
                XYToLatLon(X, Y, proj.getR(), proj.getLat1(), proj.getLat2(), proj.getCartPole().getLat(), proj.getCartPole().getLon(), proj.getLonDir(), proj.getLon0(), proj.getDx(), proj.getDy(), proj.getC(), proj.getLat(), proj.getLon(), alpha, lat_trans, lon_trans, lat, lon);
        }


        public static void XYToLatLon(final double X, final double Y, final double R, final double lat1, final double lat2, final double latp, final double lonp, final TTransformedLongitudeDirection lon_dir, final double lon0, final double dx, final double dy, final double c, final ICoordFunctionProj FI, final ICoordFunctionProj GI, final double alpha, double [] lat_trans, double [] lon_trans, double [] lat, double [] lon )
        {
                //Convert a point in the Cartesian coordinates to geographic using inverse projection equations

                //Compute Helmert transformation coefficients (for the M8 method)
                final double q1 = cos(alpha * PI / 180);
                final double q2 = sin(alpha * PI / 180);

                //Unrotate points (for the M8 method)
                final double Xr = (X - dx) * q1 + ( Y - dy) * q2;
                final double Yr = -(X - dx) * q2 + (Y - dy) * q1;

                //(X, Y)->(lat_trans, lon_trans)
                lat_trans[0] = FI.f(Xr, Yr, R, lat1, lat2, lon0, 0.0, 0.0, c);
                lon_trans[0] = GI.f(Xr, Yr, R, lat1, lat2, lon0, 0.0, 0.0, c);

                //(lat_trans, lon_trans)->(lat, lon)
                lat[0] = CartTransformation.latTransToLat(lat_trans[0], lon_trans[0], latp, lonp, lon_dir);
                lon[0] = CartTransformation.lonTransToLon(lat_trans[0], lon_trans[0], latp, lonp, lon_dir);
        }

        
        public static List <Point3DCartesian> latsLonsToXY (final List <Point3DGeographic> reference_points, final Projection proj, final double alpha)
        {
                //Convert all geographic points to the Cartesian coordinates using the projection equations
                List <Point3DCartesian> projected_points = new ArrayList<>();
                
                for (Point3DGeographic p:reference_points)
                {
                        double [] X = {0.0}, Y = {0.0}, lat_trans = {0.0}, lon_trans = {0.0};
                        
                        try
                        {
                                //Convert a point
                                CartTransformation.latLonToXY(p.getLat(), p.getLon(), proj, alpha,lat_trans, lon_trans, X, Y);
                         
                                //Add to the list of points
                                projected_points.add(new Point3DCartesian(X[0], Y[0], p.getH()));
                        }
                        
                        //Throw exception
                        catch(Exception e)
                        {
                                
                                // System.out.println( p.getLat()+ " " + p.getLon() + " " + X[0] + " " + Y[0]);
                                //e.printStackTrace();     
                        }
                } 
                
                return projected_points;
        }
        
        
        public static List <Point3DGeographic> XYToLatsLons (final List <Point3DCartesian> reference_points, final Projection proj, final double alpha)
        {
                //Convert all points in the Cartesian coordinates to geographic using inverse projection equations
                List <Point3DGeographic> projected_points = new ArrayList<>();
                
                for (Point3DCartesian p : reference_points) 
                {
                        double[] lat = {0.0}, lon = {0.0}, lat_trans = {0.0}, lon_trans = {0.0};

                        try 
                        {
                                //Convert a point
                                CartTransformation.XYToLatLon(p.getX(), p.getY(), proj, alpha, lat_trans, lon_trans, lat, lon);
                                
                                //Add to the list of points
                                projected_points.add(new Point3DGeographic(lat[0], lon[0], p.getZ()));
                        } 
                        
                        //Throw exception
                        catch (Exception e) 
                        {
                                //e.printStackTrace();
                                //System.out.println("     Error:" + p.getX()+ " " + p.getY());
                        }
                }
                
                return projected_points;
        }
                        
}
