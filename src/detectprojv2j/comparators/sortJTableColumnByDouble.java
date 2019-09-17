// Description: Comparator, compare elements in the JTable column
// Used to store a permutation of indices of sorted elements

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

import java.util.Comparator;

//Comparator, compare elements in the JTable column
public class sortJTableColumnByDouble implements Comparator {

        @Override
        public int compare(Object o1, Object o2) 
        {
                //Try to convert to double
                try 
                {
                        Double val1 = Double.parseDouble(o1.toString());
                        Double val2 = Double.parseDouble(o2.toString());
                        
                        //Compare both double values
                        return val1.compareTo(val2);
                } 
                
                //Throw exception
                catch (NumberFormatException numberFormatException) {
                        
                        //Try to convert to integer (due to the decimal separator)
                        try 
                        {
                                Integer val1 = Integer.parseInt(o1.toString());
                                Integer val2 = Integer.parseInt(o2.toString());
                                
                                //Compare both integer values
                                return val1.compareTo(val2);
                        } 
                        
                        //Throw exception
                        catch (Exception e) 
                        {
                                //Compare as strings
                                return o1.toString().compareTo(o2.toString());
                        }
                }
        }
}
