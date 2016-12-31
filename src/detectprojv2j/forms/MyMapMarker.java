// Description: redefinition of the map marker circle
// Class is derived from the MapMarkerCircle

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

package detectprojv2j.forms;

import java.awt.Color;
import org.openstreetmap.gui.jmapviewer.Coordinate;
import org.openstreetmap.gui.jmapviewer.MapMarkerCircle;

public class MyMapMarker extends MapMarkerCircle 
{

        //Create own map marker with the user-defined radius
        public MyMapMarker(final double lat, final double lon, final double radius) 
        {
                //Create map marker
                super(null, "", new Coordinate (lat, lon), radius, STYLE.FIXED, getDefaultStyle());
                
                //Set its properties
                this.setBackColor(Color.YELLOW);
                this.setColor(Color.BLACK); 
        }
}