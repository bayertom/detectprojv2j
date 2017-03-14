// Description: Determined projection and map parameters (1 result)

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

import java.util.List;

import detectprojv2j.structures.graticule.Meridian;
import detectprojv2j.structures.graticule.Parallel;
import detectprojv2j.structures.point.Point3DCartesian;
import detectprojv2j.structures.projection.Projection;

//Result of the cartometric analysis: projection and map parameters
public class TResult {

        public final Projection proj;                                           //Determined projection and its parameters
        public final double map_scale;                                          //Determined map scale
        public final double map_rotation;                                       //Determined map projection
        public final int iterations;                                            //Amount of iterations
        
        public List <Meridian> meridians;                                       //List of projected meridians, first x samples
        public List <Parallel> parallels;                                       //List of projected parallels, first x samples
        public List <Point3DCartesian> points_proj;                             //List of projected reference points, first x samples
        public List <List<Point3DCartesian> > meridians_proj;                   //List of projected meridians, first x samples
        public List <List<Point3DCartesian> > parallels_proj;                   //List of projected parallels, first x samples
        
        
        public TResult (final Projection proj_, final double map_scale_, final double map_rotation_, final int iterations_)
        {
                //Initialize data members
                proj = proj_;
                map_scale = map_scale_;
                map_rotation = map_rotation_;
                iterations = iterations_;
                
                meridians = null;
                parallels = null;
                points_proj = null;
                meridians_proj = null;
                parallels_proj = null;
        }   
}
