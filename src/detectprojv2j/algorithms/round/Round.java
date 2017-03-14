// Description: Number rounding

// Copyright (c) 2010 - 2013
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

package detectprojv2j.algorithms.round;

import static java.lang.Math.*;

public class Round {
        
        public static double roundToMultipleCeil( final double value, final double multiple )
        {
                if (multiple == 0) return value;
                return (ceil((value) / multiple) * (multiple));
        }


        public static double roundToMultipleFloor( final double value, final double multiple )
        {
                if (multiple == 0) return value;
                return (floor((value) / multiple) * (multiple));
        }
}
