// Description: Warp map represented by a raster from the source to destination projection
// Conversion projection_source -> sphere -> projection_dest

// Copyright (c) 2016 - 2017
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


package detectprojv2j.algorithms.mapwarp;

import java.util.List;
import java.util.ArrayList;
//import java.util.Map;
import java.util.Collections;
import java.awt.image.BufferedImage;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.Map;
import java.util.HashMap;
import static java.lang.Math.ceil;
import static java.lang.Math.min;
import static java.lang.Math.max;
import java.util.concurrent.Callable;
import javax.swing.JLabel;


import org.openstreetmap.gui.jmapviewer.Coordinate;
import org.openstreetmap.gui.jmapviewer.interfaces.MapMarker;

import detectprojv2j.structures.point.Point3DCartesian;
import detectprojv2j.structures.projection.Projection;
import detectprojv2j.structures.point.Point3DGeographic;
import detectprojv2j.structures.tile.MercTile;

import detectprojv2j.comparators.SortByX;
import detectprojv2j.comparators.SortByY;

import detectprojv2j.comparators.MaxLatPredcate;
import detectprojv2j.comparators.SortByLat;
import detectprojv2j.comparators.SortByLon;

import detectprojv2j.algorithms.carttransformation.CartTransformation;

import detectprojv2j.forms.RasterMapMarker;
import detectprojv2j.forms.OSMMap;
import java.awt.image.Raster;
import static java.lang.Math.round;
import javax.swing.JSlider;



public class MapWarp implements Callable <List<MercTile>> {
        
        private final BufferedImage img;
        private final Projection iproj;
        private final Projection proj;
        private final double rotation;
        private final int tile_size;
        private final JSlider slider;
        private final JLabel label;
        private final OSMMap map;
        
        
        public MapWarp(final BufferedImage img_, final Projection iproj_, final Projection proj_, final double rotation_, final int tile_size_, final JSlider slider_, final JLabel label_, final OSMMap map_)
        {
                img = img_;
                iproj = iproj_;
                proj = proj_;
                rotation = rotation_;
                tile_size = tile_size_;
                slider = slider_;
                label = label_;
                map = map_;     
        }
        
        
        @Override
        public List<MercTile> call () 
        {
                return warpEarlyMapToProj();
        }
        
        public List <MercTile> warpEarlyMapToProj()
        {
                //Warp early map from its projection to the destination projection
                //Process tile - by - tile
                //Add tiles to the map marks
                final int w = img.getWidth(), h = img.getHeight();
                final String status_bar_text = "Warping map: " + iproj.getFamily()+ " " + iproj.getName() + " Projection";
                List <MercTile> proj_tiles = new ArrayList<>();

                //Amount of tiles
                final int rows = (int)ceil((double)h / tile_size);
                final int columns = (int)ceil((double)w / tile_size);
                final int tiles_amount = rows * columns;
                
                //Print status
                label.setText(status_bar_text);
                        
                //Remove all map marks representing tiles except control points
                List <MapMarker> map_markers = Collections.synchronizedList(map.getMapMarkerList());
                
                //Process all pixels of the analyzed map: subdivison to tiles
                int index = 0;
                for (int i = 0; i < w; i+= tile_size)
                {
                        for (int j = 0; j < h; j+= tile_size)
                        {
                                //Create list of points for a tile
                                List<Point3DCartesian> tile_points_proj = rasterTileToPointTile (img, i, j, w, h);
                                
                                //Inverse projection of map points to the sphere
                                List <Point3DGeographic> tile_points_sphere = CartTransformation.XYToLatsLons(tile_points_proj, iproj, rotation);        

                                //Remove all points with the latitude > 85 deg (resampled map looks strange here)
                                tile_points_sphere.removeIf(new MaxLatPredcate(85.0));
                                
                                //Remove all unique points

                                //At least 2 points have been converted
                                if (tile_points_sphere.size() > 2)
                                {
                                        //Find vertices of the min-max box: transformed raster will placed inside
                                        final double lat_sw = (Collections.min(tile_points_sphere, new SortByLat())).getLat();
                                        final double lon_sw = (Collections.min(tile_points_sphere, new SortByLon())).getLon();
                                        final double lat_ne = (Collections.max(tile_points_sphere, new SortByLat())).getLat();
                                        final double lon_ne = (Collections.max(tile_points_sphere, new SortByLon())).getLon();
                                                                                
                                        //Initialize shifts as a projection dX, dY values
                                        final double [] sx = {proj.getDx()};
                                        final double [] sy = {proj.getDy()};

                                        //System.out.println(lat_sw + " " + lon_sw + " " + lat_ne + " " + lon_ne);

                                        //Convert geographic coordinates to the destination projection (Web Mercator in JMapViewer)
                                        final List <Point3DCartesian> tile_points_reproj = CartTransformation.latsLonsToXY(tile_points_sphere, proj, rotation);
                                        /*
                                        for(Point3DCartesian point : tile_points_reproj)
                                                System.out.println("X, Y:"+ point.getX() + " " + point.getY());
                                        System.out.println("---");
                                        */
                                        //Convert points to the raster: create reprojected map
                                        double [] ratio = {0};
                                        final BufferedImage img_proj = pointTileToRaster(tile_points_reproj, sx, sy, ratio);

                                        if (img_proj != null)
                                        {
                                                //Create tile
                                                MercTile tile = new MercTile(img_proj, new Coordinate(lat_sw, lon_sw), new Coordinate(lat_ne, lon_ne), 0.01f * slider.getValue());
                                                
                                                //Add tile to the list
                                                proj_tiles.add(tile);
                                                
                                                synchronized (map_markers)
                                                {
                                                        //Add map to the maker list: it will be displayed as a raster map marker
                                                        map_markers.add(new RasterMapMarker(tile.getSWCorner(), tile.getNECorner(), img_proj, 0.01f * slider.getValue(), map));
                                                }
                                        }
                                }
                                
                                //Compute progress and repaint
                                int status = (int)((1.0 * (++index)) / tiles_amount *100);
                                if (status %2 == 0)
                                {
                                        label.setText(status_bar_text + " (" + String.valueOf(status) + "%)");
                                        synchronized(map)
                                        {
                                                map.repaint();
                                        }
                                }
                        }
                }
                
                //Warping has been completed
                label.setText("Completed...");
                
                //Return list of warped raster tiles
                return proj_tiles;   
        }
       
                
        public List<Point3DCartesian> rasterTileToPointTile (final BufferedImage img, final int i, final int j, final int w, final int h)                
        {
                //Convert raster tile to the point tile (list of points)
                final double pix_step = 2.0;
                final int white_threshold = 13824000;
                List<Point3DCartesian> tile_points = new ArrayList<>(); 
               
                //Process tile pixels
                final int k_max = min(i + tile_size, w - 1);
                final int l_max = min(j + tile_size, h - 1);
                
                for (int k = i; k <= k_max; k+=pix_step)
                {
                        for (int l = j; l <= l_max; l+=pix_step)
                        {
                                //Color of the pixel
                                final int color = img.getRGB(k, l);
                                final int blue = color & 0xff;
                                final int green = (color & 0xff00) >> 8;
                                final int red = (color & 0xff0000) >> 16;
                                
                                //Jump white pixels
                                if (red * green * blue < white_threshold)
                                {                                
                                        //Add point to the list
                                        tile_points.add(new Point3DCartesian(k, -l, color));
                                } 
                        }
                }

                //Get color of the south-west pixel
                final int color_sw = img.getRGB(i, l_max - 1);
                final int blue_sw = color_sw & 0xff;
                final int green_sw = (color_sw & 0xff00) >> 8;
                final int red_sw = (color_sw & 0xff0000) >> 16;
                
                //White pixel, add to the list
                if (red_sw * green_sw * blue_sw > white_threshold)
                        tile_points.add(new Point3DCartesian(i, -(l_max - 1), color_sw));
                
                //Get color of the north-east pixel
                final int color_ne = img.getRGB(k_max - 1, j);
                final int blue_ne = color_ne & 0xff;
                final int green_ne = (color_ne & 0xff00) >> 8;
                final int red_ne = (color_ne & 0xff0000) >> 16;
                
                //White pixel, add to the list
                if (red_ne * green_ne * blue_ne > white_threshold)
                        tile_points.add(new Point3DCartesian(k_max - 1, -j, color_ne));
                
                //Add tile to the list of tiles
                return tile_points;   
        }
        

        public BufferedImage pointTileToRaster(final List <Point3DCartesian> points, double [] sx, double [] sy, double [] ratio)
        {
                //Convert point tile (list of points) to the raster tile
                BufferedImage img_proj = null;
                
                //Tile contains at least two points
                if (points.size() > 2)
                {
                        //Find minimum coordinates
                        final double xmin = (Collections.min(points, new SortByX())).getX();
                        final double ymin = (Collections.min(points, new SortByY())).getY();
                        final double xmax = (Collections.max(points, new SortByX())).getX();
                        final double ymax = (Collections.max(points, new SortByY())).getY();

                        //Add raster shifts to the projection shifts
                        sx[0] += - xmin;
                        sy[0] += - ymin;

                        //Compute differences and ratio so as the longest edge of the raster is smaller than MAX_RASTER_SIZE
                        final double dx = xmax - xmin;
                        final double dy = ymax - ymin;
                        final double rx = dx / tile_size;
                        final double ry = dy / tile_size;
                        ratio[0] = min(rx, ry);

                        //Size of the created raster
                        double w = max(dx / ratio[0], 2.0);
                        double h = max(dy / ratio[0], 2.0);

                        //System.out.println(xmin + " " + ymin + " " + xmax + " " + ymax);
                        //Create new raster with the white background
                        img_proj = new BufferedImage((int)round(w), (int)round(h), BufferedImage.TYPE_INT_ARGB);
                        
                        //Set white background
                        Graphics2D g = img_proj.createGraphics();
                        //g.setPaint ( new Color ( 255, 255, 255 ) );
                        //g.fillRect ( 0, 0, img_proj.getWidth(), img_proj.getHeight() );
                        
                        //Set rendering properties of the raster
                        Map<RenderingHints.Key, Object> hm = new HashMap<>();
                        hm.put(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
                        hm.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                        hm.put(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                        RenderingHints renderHints = new RenderingHints(hm);
                        g.setRenderingHints(renderHints);
                        
                        //Write all points of tile to the image as dots
                        for (Point3DCartesian p:points)
                        {
                                //Apply shift and rescaling
                                //Change direction due to the different orientation of Y axis
                                double x_resc = ( p.getX() + sx[0] ) / ratio[0];
                                double y_resc = h - ( p.getY() + sy[0] ) / ratio[0];

                                //Check, if coordinates are not negative
                                x_resc = max (0, x_resc);
                                y_resc = max (0, y_resc);

                                //Check, if coordinates are not bigger than raster size
                                x_resc = min (x_resc, w - 1);
                                y_resc = min (y_resc, h - 1);

                                //Convert (r, g,b) to Color
                                final int col = (int) p.getZ();
                                final int red = (col >> 16) & 0xFF;
                                final int green = (col >> 8) & 0xFF;
                                final int blue = (col >> 0) & 0xFF;

                                //Write pixel to the raster
                                g.setColor(new Color(red, green, blue));
                                
                                double radius = 3.0;
                                g.fillOval((int)(x_resc - 0.5 * radius), (int)(y_resc -0.5 * radius), (int)radius + 1, (int)radius + 1);
                        }
                }
                
                return img_proj;
        }
}