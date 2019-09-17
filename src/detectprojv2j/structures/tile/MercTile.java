// Description: Raster tile in Mercator projection
// It will be loaded and displayed over OSM

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


package detectprojv2j.structures.tile;

import java.awt.image.BufferedImage;
import org.openstreetmap.gui.jmapviewer.Coordinate;


public class MercTile {
        
<<<<<<< HEAD
        private final BufferedImage img;                      //Rendered raster (a part of the georeferenced early map)
        private final Coordinate sw;                          //Geographic coordinates of the south-west point of map bounding box
        private final Coordinate ne;                          //Geographic coordinates of the north-east point of map bounding box
        private final float alphat;                           //Transparency ratio
        private final double sx;
        private final double sy;    
        private final double ratio;

        public MercTile(BufferedImage img_, Coordinate sw_, Coordinate ne_, float alphat_, double sx_, double sy_, double ratio_) {
=======
        private final BufferedImage img;                      //Rendered raster (a georeferenced early map)
        private final Coordinate sw;                          //Geographic coordinates of the south-west point of map bounding box
        private final Coordinate ne;                          //Geographic coordinates of the north-east point of map bounding box
        private final float alphat;                           //Transparency ratio

        public MercTile(BufferedImage img_, Coordinate sw_, Coordinate ne_, float alphat_) {
>>>>>>> ea1389cca3027d015eff4476abc3c71495f9e1f5
                img = img_;
                sw = sw_;
                ne = ne_;
                alphat = alphat_;
<<<<<<< HEAD
                sx = sx_;
                sy = sy_;
                ratio = ratio_;
=======
>>>>>>> ea1389cca3027d015eff4476abc3c71495f9e1f5
        }

        public BufferedImage getImgage() {
                return img;
        }

        public Coordinate getSWCorner() {
                return sw;
        }

        public Coordinate getNECorner() {
                return ne;
        }
<<<<<<< HEAD
        
        public double getSx()
        {
                return sx;
        }
        
        public double getSy()
        {
                return sy;
        }
        
        public double getRatio()
        {
                return ratio;
        }
=======
>>>>>>> ea1389cca3027d015eff4476abc3c71495f9e1f5
}