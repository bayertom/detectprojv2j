// Description: Functor, compute residuals between sets P,P'
// Utilized for the map projection analysis
// Method  M8S (8 determined parameters, scaled, a rotation involved)

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

package detectprojv2j.algorithms.simplexmethod;

import detectprojv2j.structures.matrix.Matrix;

import detectprojv2j.algorithms.nonlinearleastsquares.FRM8;

public class FRM8NM {
        
        final private FRM8 frm8;                                                      //Reference to NLS functor
        
        public	FRM8NM(final FRM8 frm8_) 
        {
                frm8 = frm8_;
        }
        
        
       public void function (Matrix X, Matrix Y, Matrix V, Matrix W)
       {
		//Call NLS function
                frm8.function(X.trans(), Y, V, W);
       }
}
