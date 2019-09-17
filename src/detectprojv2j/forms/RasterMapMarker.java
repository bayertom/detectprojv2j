// Description: Display raster map marker
// Raster loaded as a map marker and fit inside the bounding box

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

package detectprojv2j.forms;


import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import static java.lang.Math.PI;
import static java.lang.Math.abs;
import static java.lang.Math.log;
import static java.lang.Math.round;
import static java.lang.Math.sqrt;
import static java.lang.Math.tan;

import org.openstreetmap.gui.jmapviewer.Coordinate;
import org.openstreetmap.gui.jmapviewer.JMapViewer;
import org.openstreetmap.gui.jmapviewer.MapMarkerCircle;
import org.openstreetmap.gui.jmapviewer.interfaces.ICoordinate;
import org.openstreetmap.gui.jmapviewer.interfaces.MapMarker;

public class RasterMapMarker extends MapMarkerCircle implements MapMarker {

        private Coordinate sw;                          //Geographic coordinates of the south-west point of map bounding box
        private Coordinate ne;                          //Geographic coordinates of the north-east point of map bounding box
        private BufferedImage img;                      //Rendered raster (a georeferenced early map)
        private float alphat;                           //Transparency ratio
        private JMapViewer map;                         //Reference to a JmapViewer object
        
        public RasterMapMarker(final Coordinate sw_, final Coordinate ne_, final BufferedImage img_, final float alphat_, final JMapViewer map_) {
                this(sw_, ne_, 0.0, img_, alphat_, map_);
        }

        public RasterMapMarker(final Coordinate sw_, final Coordinate ne_, final double radius, final BufferedImage img_, final float alphat_, final JMapViewer map_) {
                super(sw_, radius);
                sw = sw_;
                ne = ne_;
                img = img_;
                alphat = alphat_;
                map = map_;
        }
        
        public Coordinate getSWCorner() 
        {
                return sw;
        }

        public void setSWCorner(final Coordinate sw_) {
                sw = sw_;
        }

        public Coordinate getNECorner() {
                return ne;
        }

        public void setNECorner(final Coordinate ne_) {
                ne = ne_;
        }

        public BufferedImage getTile() {
                return img;
        }

        public void setTile(final BufferedImage img_) {
                img = img_;
        }

        public float getTransparency() {
                return alphat;
        }

        public void setTransparency(final float alphat_) {
                alphat = alphat_;
        }

        @Override
        public void paint(Graphics g, Point position, int sc) {
                
                //Repaint screen
                super.paint(g, position, sc);
                
                //Repaint raster
                if ((img != null) && (map != null))
                {
                        //Get visible rectangle and its coordinates
                        Rectangle r = map.getVisibleRect();
                        final double x1r = r.x;
                        final double y1r = r.y +  0.5 * r.height;
                        final double x2r = r.x + r.width;
                        final double y2r = y1r;

                        //Get geographic coordinates of rectangle points (a middle edge)
                        final ICoordinate v1r = map.getPosition((int)x1r, (int)y1r);
                        final ICoordinate v2r = map.getPosition((int)x2r, (int)y2r);
                        final double lon1r = v1r.getLon();
                        final double lon2r = v2r.getLon();

                        //Compute map scale: s = dx / dX as a fraction of the horizontal
                        //edge of the visible rectangle and its image in Mercator projection
                        final double R_km = 6378.137;
                        final double dXr = x2r - x1r;
                        final double dxr = (lon2r - lon1r) * R_km * PI / 180;
                        final double scale = dxr / dXr;

                        //Get coordinates of the vertices of the raster bounding box
                        //given by the south-west and north-east points
                        final double lat_sw = sw.getLat();
                        final double lon_sw = sw.getLon();
                        final double lat_ne = ne.getLat();
                        final double lon_ne = ne.getLon();

                        //Compute their Cartesian coordinates in the Mercator projection
                        final double x_sw = R_km * lon_sw * PI / 180;
                        final double y_sw = R_km * log(tan(0.5 * lat_sw * PI / 180 + PI / 4));
                        final double x_ne = R_km * lon_ne * PI / 180;
                        final double y_ne = R_km * log(tan(0.5 * lat_ne * PI / 180 + PI / 4));

                        //Compute the distance in the Mercator projection
                        final double dx = x_ne - x_sw;
                        final double dy = y_ne - y_sw;
                        final double dM = sqrt(dx * dx + dy * dy);

                        //Compute distance of these points in the raster (its diagonal)
                        final double w = img.getWidth();
                        final double h = img.getHeight();
                        final double dr = sqrt(w * w + h * h);

                        //Compute multiplication ratio mr = dM / ( dr * scale )
                        final double mr = dM / (dr * scale);

                        //Compute new dimensions of the raster (resize it)
                        int wres = (int) round(w * mr);
                        int hres = (int) round(h * mr);
                        
                        //Compute pixel size
                        final int px = (int)(wres/w);
                        
                        /*
                        //Enlarge raster in x direction by 1 pixel: compute shifts
                        final int sx1 = px;
                        final int sy1 = (int) (px * h / w);
                        
                        //Enlarge raster in y direction by 1 pixel: compute shifts
                        final int sy2 = px;
                        final int sx2 = (int) (px * w / h);
                        
                        //Select better fit rectangle: a smaller shift
                        final int sx = (sy1 < sx2) ? sx1 : sx2;
                        final int sy = (sy1 < sx2) ? sy1 : sy2;
                        */
                        
                        //Set transparency for a raster   
                        Graphics2D g2d = (Graphics2D) g;
                        AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alphat);
                        g2d.setComposite(ac);
                        
                        //Draw raster over the OSM map: moved by half of a pixel and enlarged by a pixel
                        final int xw = position.x;
                        final int yw = position.y - hres;
                        
                        //Shift by a half of pixel
                        g2d.drawImage(img, xw - px/2, yw - px/2, wres + px, hres + px, null); 
                        
                        //Shift by pixel*
                        //g2d.drawImage(img, xw - sx, yw - sy, wres + 2 * sx, hres + 2 * sy, null);
                       
                        //g2d.setColor(Color.red);
                        //g2d.setStroke(new BasicStroke(2));
                        //g2d.drawRect(position.x, position.y - hres, wres, hres);
                }
        }
}