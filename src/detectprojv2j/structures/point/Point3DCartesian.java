// Description: 3D Cartesian point

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

public class Point3DCartesian implements IPoint3DFeatures {
        
        private static  int points_cart_id_counter = 0;         //Static variable: counter of created points
        private int point_id;                                   //Internal point ID (start from 0)
        private String  point_label;                            //Point label ( or point ID ) loaded from file ( may contain alphanumeric characters )
        private double x;                                       //Point x coordinate
        private double y;                                       //Point y coordinate
        private double z;                                       //Point z coordinate
        
          
        public Point3DCartesian ( final double x_, final double y_, final double z_) 
        {
                //3D point
                point_id = points_cart_id_counter++;
                point_label = "";
                x = x_;
                y = y_;
                z = z_;
        } 
        
         public Point3DCartesian (final String point_label_, final double x_, final double y_, final double z_) 
        {
                //3D point with label
                point_id = points_cart_id_counter++;
                point_label = point_label_;
                x = x_;
                y = y_;
                z = z_;
        } 
         
         
        public Point3DCartesian ( final double x_, final double y_) 
        {
                //2D point
                point_id = points_cart_id_counter++;
                point_label = "";
                x = x_;
                y = y_;
                z = 0;
        } 
        
         public Point3DCartesian (final String point_label_, final double x_, final double y_) 
        {
                //2D point with label
                point_id = points_cart_id_counter++;
                point_label = point_label_;
                x = x_;
                y = y_;
                z = 0;
        } 
        
        //Other functions
        public int getPointID() {return point_id; }
        public String getPointLabel() {return point_label; }
        public double getX() {return x;}
        public double getY() {return y;}
        public double getZ() {return z;}
      
        @Override
        public int getVal1() {return point_id;}
        
        @Override
        public double getVal2() {return x;}
        
        @Override
        public double getVal3() {return y;}
        
        @Override
        public double getVal4() {return z;}


        public void updateID () {point_id = points_cart_id_counter ++;}
	public void setPointLabel(final String point_label_) { point_label = point_label_; }
        public void setX ( final double x_ ) {x = x_;}
        public void setY ( final double y_ ) {y = y_;}
        public void setZ ( final double z_ ) {z = z_;}
}
