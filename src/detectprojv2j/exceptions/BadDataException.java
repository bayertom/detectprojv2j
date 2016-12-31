// Description: Bad input data, throw exception

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

package detectprojv2j.exceptions;

public class BadDataException extends RuntimeException {

        private final String data_text;

        public BadDataException(final String exception_text_, final String data_text_) {
                super(exception_text_);
                data_text = data_text_;
        }

        public void printException() {
                super.printStackTrace();
                System.out.println(data_text + '\n');
        }
}
