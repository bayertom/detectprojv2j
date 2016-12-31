// Description: Functor, compute residuals between sets P,P'
// Utilized for the map projection analysis
// Method  M7 (7 determined parameters, DE

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

package detectprojv2j.algorithms.geneticalgorithms;

import detectprojv2j.structures.matrix.Matrix;

import detectprojv2j.algorithms.nonlinearleastsquares.FRM7;

public class FRM7DE {
        final private FRM7 frm7;                                                //Reference to NLS functor
        
        public	FRM7DE(final FRM7 frm7_) 
        {
                frm7 = frm7_;
        }
        
        
       public void function (Matrix X, Matrix Y, Matrix V, Matrix W)
       {
		//Call NLS function
                frm7.function(X.trans(), Y, V, W);
       }
        
}
