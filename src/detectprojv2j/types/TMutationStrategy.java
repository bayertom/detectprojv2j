// Description: Mutation strategy used in the differential evolution

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

public enum TMutationStrategy
{
        DERand1Strategy,                //DE/Rand/1 mutation strategy
	DERand2Strategy,		//
	DERandDir1Strategy,		//
	DERandDir2Strategy,		//
	DERandBest1Strategy,		//
        DERandBest2Strategy,		//
	DERandBestDir1Strategy,		//DE/Rand/Best/Dir/1 mutation strategy
	DETargetToBest1Strategy,	//
	SACPStrategy,			//Self-adapting strategy
}
