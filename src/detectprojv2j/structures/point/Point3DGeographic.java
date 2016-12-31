// Description: 3D geographic point

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
 
package detectprojv2j.structures.point;

import detectprojv2j.types.IPoint3DFeatures;

public class Point3DGeographic implements IPoint3DFeatures {
        
        private static int points_geo_id_counter = 0;           //Static variable: counter of created points
        private int point_id;                                   //Internal point ID (start from 0)
        private String  point_label;                            //Point label ( or point ID ) loaded from file ( may contain alphanumeric characters )
        private double lat;                                     //Point latitude
        private double lon;                                     //Point longitude
        private double H;                                       //Point height
        
        public Point3DGeographic ( final double lat_, final double lon_, final double H_) 
        {
                //3D point
                point_id = points_geo_id_counter++;
                point_label = "";
                lat = lat_;
                lon = lon_;
                H = H_;
        }
        
        public Point3DGeographic ( final String point_label_, final double lat_, final double lon_, final double H_) 
        {
                //3D point with label
                point_id = points_geo_id_counter++;
                point_label = point_label_;
                lat = lat_;
                lon = lon_;
                H = H_;
        }
        
        public Point3DGeographic ( final double lat_, final double lon_) 
        {
                //2D point
                point_id = points_geo_id_counter++;
                point_label = "";
                lat = lat_;
                lon = lon_;
                H = 0;
        }
        
        public Point3DGeographic ( final String point_label_, final double lat_, final double lon_) 
        {
                //2D point with label
                point_id = points_geo_id_counter++;
                point_label = point_label_;
                lat = lat_;
                lon = lon_;
                H = 0;
        }
        
        //Other methods
        public int getPointID() {return point_id;}
        public String getPointLabel() {return point_label; }
        public double getLat() {return lat;}
        public double getLon() {return lon;}
        public double getH() {return H;}
       
         @Override
        public int getVal1() {return point_id;}
        
        @Override
        public double getVal2() {return lat;}
        
        @Override
        public double getVal3() {return lon;}
        
        @Override
        public double getVal4() {return H;}

        public void updateID () {point_id = points_geo_id_counter ++;}
        public void setPointID ( final int point_id_ )  {point_id = point_id_;}
        public void setPointLabel (final String point_label_) { point_label = point_label_; }
        public void setLat ( final double lat_ ) {lat = lat_;}
        public void setLon ( final double lon_ ) {lon = lon_;}
        public void setH ( final double H_ ) {H = H_;}
}
