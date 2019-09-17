// Description: Pixel implementation

// Copyright (c) 2018 - 2019
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

import java.awt.Color;

public class Pixel {

    private double px;
    private double py;
    private int color;

    public Pixel(double px_, double py_, int color_) {
        px = px_;
        py = py_;
        color = color_;
    }

    public double getPx() { return px; }
    public double getPy() { return py; }
    public int getColor() { return color; }
    
    public void setPx(final double px_) { px = px_; }
    public void setPy(final double py_) { py = py_; }
    public void setColor(final int color_) {color = color_; }
}