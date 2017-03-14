// Description: List of supported methods for the projection analysis

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

//Method of the analysis
public enum TAnalysisMethod 
{
        
	NLSM7,                                  //Non linear least squares method without rotation (local optmization)
	NMM7,					//Simplex method without rotation (global optmization)
	DEM7,					//Differential evolution without rotation (global optmization)
	NLSM8,					//Non linear least squares method involving map rotation (local optmization), scaled
	NMM8,					//Simplex method involving map rotation (global optmization)
	DEM8,   				//Differential evolution involving map rotation (global optmization)
}
