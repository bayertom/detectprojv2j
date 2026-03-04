// Description: Raster tile in Mercator projection displayed over OSM
// Boundary edges and transparency are stored

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

package detectprojv2j.structures.tile;

import java.awt.image.BufferedImage;

public class MercTile {
        public final BufferedImage image;
        public final double lat_nw, lon_nw;
        public final double lat_se, lon_se;
        public double alpha; 

        public MercTile(final BufferedImage image_, final double lat_nw_, final double lon_nw_, final double lat_se_, final double lon_se_, double alpha_) {
            image = image_;
            lat_nw = lat_nw_;
            lon_nw = lon_nw_;
            lat_se = lat_se_;
            lon_se = lon_se_;
            alpha = alpha_;
        }
    }