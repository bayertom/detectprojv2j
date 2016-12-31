// Description: Different size of matrices A, B in matrix algebra, throw exception

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

package detectprojv2j.exceptions;

import detectprojv2j.structures.matrix.Matrix;


public class MathMatrixDifferentSizeException extends MathMatrixException {
   
        private final Matrix M2;
        
        public MathMatrixDifferentSizeException (final String exception_text_, final String math_text_, final Matrix M1_, final Matrix  M2_)
        {
                super (exception_text_, math_text_, M1_);
                M2 = M2_;
        }
        
        public void printException() {
                super.printStackTrace();
                System.out.println( "Matrix B, rows count: " + M2.rows() + ", cols count: " +  M2.cols() + '\n');
                M2.print();
        }
        
         @Override
         public double getArg() {return 0;}
}
