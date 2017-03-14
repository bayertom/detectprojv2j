// Description: Math overflow (value too big), throw exception 

// Copyright (c) 2010 - 2016
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


public class MathOverflowException extends MathException {
        
        private final double arg;
        
        public MathOverflowException(final String exception_text_, final String math_text_, final double arg_)
        {
                super(exception_text_, math_text_);
                arg = arg_;
        }
        
        @Override
        public void printException() {
                super.printStackTrace();
                System.out.println(arg + '\n');
        }
        
        @Override
        public double getArg() {return arg;}
}
