// Description: Export mosaic map by stitching all raster tiles + additional information

// Copyright (c) 2015 - 2025
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

package detectprojv2j.types;

import java.awt.image.BufferedImage;

public class TMosaicMap {
        
       public final BufferedImage map;                          //Merged tiles into a map
       public final double x_min;                               //Minimum x coordinate
       public final double y_max;                               //Maximum y coordinate
       public final double resolution;                          //Rater resolution

       public TMosaicMap(BufferedImage map_, final double x_min_, final double y_max_, final double resolution_)
       {
                map = map_;
                x_min = x_min_;
                y_max = y_max_;
                resolution = resolution_;
       }
          
    
}
