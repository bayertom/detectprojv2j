// Description: Reproject rater map represented into to destination projection (Mercator)
// Conversion projection_source -> sphere -> projection_dest

// Copyright (c) 2016 - 2019
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

package detectprojv2j.algorithms.mapreproject;

import java.util.List;
import java.util.ArrayList;
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
import static java.lang.Math.round;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

import detectprojv2j.structures.point.Point3DCartesian;
import detectprojv2j.structures.projection.Projection;
import detectprojv2j.structures.point.Point3DGeographic;
import detectprojv2j.structures.tile.MercTile;

import detectprojv2j.comparators.SortPointsByX;
import detectprojv2j.comparators.SortPointsByY;

import detectprojv2j.comparators.MaxLatPredicate;
import detectprojv2j.comparators.SortPointsByLat;
import detectprojv2j.comparators.SortPointsByLon;

import detectprojv2j.algorithms.carttransformation.CartTransformation;

import static detectprojv2j.consts.Consts.MAX_LAT_MERC;

import detectprojv2j.forms.OSMMap;



public class MapReproject implements Runnable {
        
        private final BufferedImage img_map;            //Early map
        private final Projection iproj;                 //Source projection that is inverted
        private final Projection proj;                  //Destination projection: Mercator
        private final double rotation;                  //Raster rotation
        private final int tile_size;                    //Raster tile size in pixels (500-1000
        private final JLabel label;                     //Label text
        private final OSMMap map;                       //Current map in Web Mercator projection

        
        
        public MapReproject(final BufferedImage img_map_, final Projection iproj_, final Projection proj_, final double rotation_, final int tile_size_, final JLabel label_, final OSMMap map_)
        {
                img_map = img_map_;
                iproj = iproj_;
                proj = proj_;
                rotation = rotation_;
                tile_size = tile_size_;
                label = label_;
                map = map_;   
        }
        
        
        @Override
        public void run () 
        {
                //Run map warp
                warpEarlyMapToProj();
        }
        
        
        public void warpEarlyMapToProj()
        {
                //Warp early map from its projection to the destination projection
                //Process tile - by - tile
                final int w = img_map.getWidth(), h = img_map.getHeight();
                
                //Create lit of reprojected tiles
                List<MercTile> tiles_reproj_temp = new ArrayList<>();
                
                //Set as as source data for tiles
                map.setReprojectedTiles(tiles_reproj_temp);
                
                //Actualize status bar info
                final String status_bar_text = "Reprojecting map: " + iproj.getFamily()+ " " + iproj.getName() + " Projection";

                //Expected amount of tiles
                final int rows_expected = (int)ceil((double)h / tile_size);
                final int columns_expected = (int) ceil((double) w / tile_size);
                final int tiles_expected = rows_expected * columns_expected;

                //Print status
                label.setText(status_bar_text);

                //Initialize scale ratio
                double[] ratio = {-1};

                //Process tiles one by one
                int index = 0;
                for (int i = 0; i < w; i += tile_size) // i < w
                {
                        for (int j = 0; j < h; j += tile_size) //i < h
                        {
                                //Create list of points for a tile
                                List<Point3DCartesian> tile_points_proj = rasterTileToPointTile(img_map, i, j, w, h);

                                //Inverse projection of tile points onto the sphere
                                List<Point3DGeographic> tile_points_sphere = CartTransformation.XYToLatsLons(tile_points_proj, iproj, rotation);

                                //Remove all points with the latitude > 85 deg (resampled map looks strange here)
                                tile_points_sphere.removeIf(new MaxLatPredicate(MAX_LAT_MERC));

                                //Remove all unique points
                                //At least 2 points have been converted
                                if (tile_points_sphere.size() > 2) {
                                        
                                        //Find vertices of the min-max box: transformed raster will placed inside
                                        final double lat_nw = (Collections.max(tile_points_sphere, new SortPointsByLat())).getLat();
                                        final double lon_nw = (Collections.min(tile_points_sphere, new SortPointsByLon())).getLon();
                                        final double lat_se = (Collections.min(tile_points_sphere, new SortPointsByLat())).getLat();
                                        final double lon_se = (Collections.max(tile_points_sphere, new SortPointsByLon())).getLon();

                                        //Initialize shifts as a projection dX, dY values
                                        final double[] sx = {proj.getDx()};
                                        final double[] sy = {proj.getDy()};

                                        //Convert geographic coordinates to the destination projection (Web Mercator in JMapViewer)
                                        final List<Point3DCartesian> tile_points_reproj = CartTransformation.latsLonsToXY(tile_points_sphere, proj, rotation);

                                        //Convert points to the raster: create reprojected map
                                        final BufferedImage img_map_proj = pointTileToRaster(tile_points_reproj, sx, sy, ratio);

                                        //The image has been created
                                        if (img_map_proj != null) {

                                                try {
                                                        //Safe painting
                                                        SwingUtilities.invokeLater(() -> { 
                                                                
                                                                //Create new tile
                                                                MercTile tile = new MercTile(img_map_proj, lat_nw, lon_nw, lat_se, lon_se, 0.65);

                                                                //Add reprojected tile to the tile source list
                                                                tiles_reproj_temp.add(tile);

                                                                //Map repaint
                                                                map.repaint();
                                                       });
                                                } 
                                                
                                                //Throw exception
                                                catch (Exception e) {
                                                        e.printStackTrace();
                                                }
                                        }
                                }

                                //Compute % progress and repaint screen ech 2%
                                int status = (int) ((1.0 * (++index)) / tiles_expected * 100);
                                if (status % 2 == 0) {
                                        
                                        //Print progress
                                        label.setText(status_bar_text + " (" + String.valueOf(status) + "%)");
                                        
                                        //Repaint map
                                        synchronized (map) {
                                                map.repaint();
                                        }
                                }
                        }
                }


                //Warping has been completed
                label.setText("Completed...");
        }
       
                
        public List<Point3DCartesian> rasterTileToPointTile (final BufferedImage img_map, final int i_start, final int j_start, final int w, final int h)                
        {
                //Convert raster tile to the point tile (list of points)
                final double pix_step = 1.0;
                final int white_threshold = 13824000;
                
                //Tile converted to points
                List<Point3DCartesian> tile_points = new ArrayList<>(); 
               
                //Maximum pixel limits
                final int k_max = min(i_start + tile_size, w - 1);
                final int l_max = min(j_start + tile_size, h - 1);
                
                //Process tile pixels
                for (int k = i_start; k <= k_max; k++)
                {
                        for (int l = j_start; l <= l_max; l++)
                        {
                                //Color of the pixel
                                final int color = img_map.getRGB(k, l);
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
                final int color_sw = img_map.getRGB(i_start, l_max - 1);
                final int blue_sw = color_sw & 0xff;
                final int green_sw = (color_sw & 0xff00) >> 8;
                final int red_sw = (color_sw & 0xff0000) >> 16;
                
                //White pixel, do not add to the list
                if (red_sw * green_sw * blue_sw > white_threshold)
                        tile_points.add(new Point3DCartesian(i_start, -(l_max - 1), color_sw));
                
                //Get color of the north-east pixel
                final int color_ne = img_map.getRGB(k_max - 1, j_start);
                final int blue_ne = color_ne & 0xff;
                final int green_ne = (color_ne & 0xff00) >> 8;
                final int red_ne = (color_ne & 0xff0000) >> 16;
                
                //White pixel, do not add to the list
                if (red_ne * green_ne * blue_ne > white_threshold)
                        tile_points.add(new Point3DCartesian(k_max - 1, -j_start, color_ne));
                
                //Add tile to the list of tiles
                return tile_points;   
        }
        

        public BufferedImage pointTileToRaster(final List <Point3DCartesian> points, double [] sx, double [] sy, double [] ratio)
        {
                //Convert point tile (list of points) to the raster tile: rasterization
                BufferedImage img_map_proj = null;
                
                //Tile contains at least two points
                if (points.size() > 2)
                {
                        //Find minimum coordinates
                        final double xmin = (Collections.min(points, new SortPointsByX())).getX();
                        final double ymin = (Collections.min(points, new SortPointsByY())).getY();
                        final double xmax = (Collections.max(points, new SortPointsByX())).getX();
                        final double ymax = (Collections.max(points, new SortPointsByY())).getY();

                        //Add raster shifts to the projection shifts
                        sx[0] += - xmin;
                        sy[0] += - ymin;

                        //Compute differences and ratio so as the longest edge of the raster is smaller than MAX_RASTER_SIZE
                        final double dx = xmax - xmin;
                        final double dy = ymax - ymin;
                        final double rx = dx / tile_size;
                        final double ry = dy / tile_size;
                        
                        //Variable ratio for tile downsample (multiply by 3) because of memory reasons
                        ratio[0] = 1.0 * (0.5*(rx + ry));

                        //Size of the created raster: width and height
                        final double w = max(dx / ratio[0], 100.0);
                        final double h = max(dy / ratio[0], 100.0);

                        //Create new raster with the white background
                        img_map_proj = new BufferedImage((int)round(w), (int)round(h), BufferedImage.TYPE_INT_ARGB);
                        
                        //Set white background
                        Graphics2D g = img_map_proj.createGraphics();
                        
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
                                //Apply shift and rescaling to the pixel coordinates [x_pix, y_pix]
                                //Change direction due to the different orientation of Y axis
                                double x_pix = ( p.getX() + sx[0] ) / ratio[0];
                                double y_pix = h - ( p.getY() + sy[0] ) / ratio[0];

                                //Check, if coordinates are inside the raster
                                x_pix = min(max (0, x_pix), w - 1);
                                y_pix = min(max (0, y_pix), h - 1);

                                //Convert (r, g,b) components to JColor
                                final int col = (int) p.getZ();
                                final int red = (col >> 16) & 0xFF;
                                final int green = (col >> 8) & 0xFF;
                                final int blue = (col >> 0) & 0xFF;
                                
                                //Set color
                                g.setColor(new Color(red, green, blue));
                                
                                //Write pixel to the raster: draw oversized to avoid interpolation
                                final double pixel_radius = 3.0;                                                               
                                g.fillOval((int)(x_pix - 0.5 * pixel_radius), (int)(y_pix -0.5 * pixel_radius), (int)pixel_radius + 1, (int)pixel_radius + 1);
                        }
                }
                
                return img_map_proj;
        }
        
}