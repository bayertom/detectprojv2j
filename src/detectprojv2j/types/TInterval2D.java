// Description: 2D interval given by min and max values

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

package detectprojv2j.types;


//2D interval given by two intervals
public class TInterval2D 
{
        public TInterval i1;
        public TInterval i2;
        
        public TInterval2D ( final TInterval i1_, final TInterval i2_ )
        {
                i1 = new TInterval(i1_.min, i1_.max);
                i2 = new TInterval(i2_.min, i2_.max);
        }
}