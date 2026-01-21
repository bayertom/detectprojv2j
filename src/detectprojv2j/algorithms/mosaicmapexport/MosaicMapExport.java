// Description: Export georeferenced tiles to a single raster file

// Copyright (c) 2024 - 2025
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

package detectprojv2j.algorithms.mosaicmapexport;

import detectprojv2j.structures.tile.MercTile;

import static detectprojv2j.consts.Consts.*;

import detectprojv2j.io.IO;
import detectprojv2j.types.TMosaicMap;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Locale;

import static java.lang.Math.*;
import java.util.concurrent.Callable;



//Export raster map in mosaic to georeferenced file
public class MosaicMapExport implements Callable <TMosaicMap> {
        
        private final List<MercTile> tiles_reproj;            //List of reprojected tiles
        private final double target_res;                      //Resolution of the target map
        private final int max_size;                           //Maximum size of the raster
        
        
        @Override
        public TMosaicMap call(){
                //Start export
                return exportTiles();
        }
              
        
        public MosaicMapExport ( List<MercTile> tiles_reproj_, double target_res_, int max_size_) {
                tiles_reproj = tiles_reproj_;
                target_res = target_res_;
                max_size =  max_size_;
        }   
 
        
        public TMosaicMap exportTiles()
        {
                //Export reprojected tiles in Mercator projection to georeferenced PNG file
                //Specify target resolution or max amount of pixels
                final int n = tiles_reproj.size();
               
                //Edges of the tiles: left, right, top, bottom
                double[] L = new double[n], R = new double[n], B = new double[n], T = new double[n];
                
                //Initialize bounds
                double x_min = MAX_FLOAT, y_min = MAX_FLOAT;
                double x_max = -MAX_FLOAT, y_max = -MAX_FLOAT;
                
                //Initialize resolution: meters per pixel
                double res_min = MAX_FLOAT;
              
                //Process projected tiles one by one
                for (int i = 0; i < tiles_reproj.size(); i++) {
                        
                        //No valid raster, skip
                        if (tiles_reproj.get(i) == null || tiles_reproj.get(i).image == null) 
                                continue;
                     
                        //Bound tile latitude to avoid infinities
                        final double lat_nw_b = max(min(tiles_reproj.get(i).lat_nw, MAX_LAT_MERC), MIN_LAT_MERC); 
                        final double lat_se_b = max(min(tiles_reproj.get(i).lat_se, MAX_LAT_MERC), MIN_LAT_MERC); 
                        
                        //Apply Mercator projection equations:north-west and south-east corners
                        final double R_E = 6378137.0;       
                        final double x1 = R_E * tiles_reproj.get(i).lon_nw/ RO;
                        final double y1 = R_E * log(tan(lat_nw_b/RO/2 + PI / 4.0));
                        final double x2 = R_E * tiles_reproj.get(i).lon_se/ RO;
                        final double y2 = R_E * log(tan(lat_se_b/RO/2 + PI / 4.0));
                        
                        //Bounding box of the raster
                        double left = min(x1, x2), right = max(x1, x2);
                        double top = max(y1, y2), bottom = min(y1, y2);

                        //Store bonding box properties
                        L[i] = left; R[i] = right; B[i] = bottom; T[i] = top;

                        //Native meters-per-pixel resolution
                        final double res_x = (right - left) / tiles_reproj.get(i).image.getWidth();
                        final double res_y = (top - bottom) / tiles_reproj.get(i).image.getHeight();
                        double res_xy = max(res_x, res_y); // avoid upscaling in either axis
                        res_min = Math.min(res_min, res_xy);

                        //Actualize values
                        x_min = min(x_min, left);
                        y_min = min(y_min, bottom);
                        x_max = max(x_max, right);
                        y_max = max(y_max, top);
                }

                //No visible raster was exported
                if (res_min == MAX_FLOAT)
                        return null;

                //Set output resolution defined by the user
                double res = ( target_res > 0 ? target_res : res_min);

                //Compute raster width and height
                int w = (int) ceil((x_max - x_min) / res);
                int h = (int) ceil((y_max - y_min) / res);
                
                //Resize raster if requires
                if (max_size > 0 && (w > max_size || h > max_size)) {
                        
                        //Compute scale
                        final double scale = Math.max((double) w / max_size, (double) h / max_size);
                        
                        //Change resolution: multiply with the scale
                        res *= scale;
                        
                        //Compute new width an height
                        w = (int) ceil((x_max - x_min) / res);
                        h = (int) ceil((y_max - y_min) / res);
                }

                //Output raster is empty
                if (w <= 0 || h <= 0) 
                        return null;

                //Create composite: merge tiles to a single ARGB image at chosen resolution
                BufferedImage img_out = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
                Graphics2D g = img_out.createGraphics();
                g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
                
                //Proces tiles one by one
                for (int i = 0; i < tiles_reproj.size(); i++) {
                       
                        //Destination pixel rectangle
                        final int dx1 = (int)floor((L[i] - x_min) / res);
                        final int dx2 = (int)ceil ((R[i] - x_min) / res);
                        final int dy1 = (int)floor((y_max - T[i]) / res); 
                        final int dy2 = (int)ceil ((y_max - B[i]) / res);

                        //No valid rectangle
                        if (dx2 <= 0 || dy2 <= 0 || dx1 >= w || dy1 >= h) 
                                continue;

                        //Draw image
                        g.drawImage(tiles_reproj.get(i).image, dx1, dy1, dx2, dy2, 0, 0, tiles_reproj.get(i).image.getWidth(), tiles_reproj.get(i).image.getHeight(), null);
                }
                
                g.dispose();
                
                //Create new mosaic map
                return new TMosaicMap(img_out, x_min, y_max, res);
        }
        
        
        public static void writePGW(final double x_min, final double y_max, final double res, final String file_name)
        {
                //Create PGW file to PNG file
                final String mask = "pgw";
                
                //Create output string
                String wf = String.format(Locale.US, "%.12f%n0.0%n0.0%n%.12f%n%.12f%n%.12f%n",
                        res, -res, x_min + res * 0.5, y_max - res * 0.5);
                
                 //Write worldfile (.pgw)
                IO.saveTextFile(wf, file_name + "." + mask);
        }
        
        
        public static void writePRJ(final String file_name)
        {
                //Create PRJ file to PNG file
                final String mask = "prj";
                
                //Create projection definition string
                String prj = "PROJCS[\"WGS 84 / Pseudo-Mercator\","
                        + "GEOGCS[\"WGS 84\",DATUM[\"WGS_1984\",SPHEROID[\"WGS 84\",6378137,298.257223563]],"
                        + "PRIMEM[\"Greenwich\",0],UNIT[\"degree\",0.0174532925199433]],"
                        + "PROJECTION[\"Mercator_1SP\"],"
                        + "PARAMETER[\"central_meridian\",0],"
                        + "PARAMETER[\"scale_factor\",1],"
                        + "PARAMETER[\"false_easting\",0],"
                        + "PARAMETER[\"false_northing\",0],"
                        + "UNIT[\"metre\",1],AUTHORITY[\"EPSG\",\"3857\"]]";
                
                //Write file with the projection definition string
                IO.saveTextFile(prj, file_name + "." + mask);
        }
}