// Description: Definiton of the global constants

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

package detectprojv2j.consts;

public final class Consts {

    //Ro definition
    public static final double RO = 180.0 / Math.PI;

    //Initial Earth radius				
    public static final double R0 = 6380.0;

    //Numeric threshold for the difference of two angles
    public static final double MAX_ANGULAR_DIFF = 1.0e-9;
    
    ////Round error of the function argument (i.e. asin(arg))
    public static final double ANGLE_ROUND_ERROR  = 1.0e-8;
    
    //Round error of the function argument (i.e. asin(arg))
    public static final double ARGUMENT_ROUND_ERROR = 1.0e-5;

    //Minimum float value
    public static final double MIN_FLOAT = 1.0e-37;

    //Maximum float value
    public static final double MAX_FLOAT = 1.0e37;
    
    //Maximum floating operation error: numeric threshold
    public static final double EPS = 1.0e-15;

    //Shift of the meridian/parallel point, when graticule constructed
    public static final double GRATICULE_LAT_LON_SHIFT = 1.0e-3;
    
    //Maximum amount of the planisphere subdivision into tiles (when the graticule constructed)
    public static final double MAX_GRATICULE_SPLIT_AMOUNT = 100;

    //Maximum length of the NLS step
    public static final double MAX_NLS_STEP_LENGTH = 1.0e3;

    //Minimum latitude of the  true parallel
    public static final double MIN_LAT1 = -85.0;

    //Minimum latitude of the true parallel
    public static final double MAX_LAT1 = 85.0;

    //Minimum latitude
    public static final double MIN_LAT = -90.0;

    //Maximum latitude
    public static final double MAX_LAT = 90.0;

    //Minimum longitude
    public static final double MIN_LON = -180.0;

    //Maximum longitude
    public static final double MAX_LON = 180.0;

    //Minimum position difference between two 2D points. Points having angle difference < MIN_ANGLE_DIF are unique
    public static final double MIN_POSITION_DIFF = 5.0e-4;
    
    //Maximum Newton-Raphson iterations
    public static final int MAX_NR_ITERATIONS = 20;
    
    //Maximum Newton-Raphson iterations
    public static final double MAX_NR_ERROR = 1.0e-5;
    
    //Set step for numeric derivative using Stirling method
    public static final double NUM_DERIV_STEP = 0.001;
    
    //Set step for numeric derivative using Stirling method
    public static final double MAP_MARKER_RADIUS = 10;

}
