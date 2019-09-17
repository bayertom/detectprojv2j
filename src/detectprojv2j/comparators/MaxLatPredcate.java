// Description: Functor, remove elements abs(lat) > lat_max
// Used for Mercator projection, remove points close to the Poles

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

package detectprojv2j.comparators;

import detectprojv2j.structures.point.Point3DGeographic;
import static java.lang.Math.abs;
import java.util.function.Predicate;

public class MaxLatPredcate implements Predicate{
        
        private final double lat_max;
        
        public MaxLatPredcate( final double lat_max_)
        {
                lat_max = lat_max_;
        }
       
        @Override
        public boolean test(Object o) 
        {
                if (o == null)
                        return false;
            
                //Applied to a different object than Point3DGeographic
                if ( !(o instanceof Point3DGeographic))
                        return false;
                
                //Conversion to Point3DGeographic
                Point3DGeographic p = (Point3DGeographic) o;
               
                return abs(p.getLat()) > lat_max;
        }
        
}
