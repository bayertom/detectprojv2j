// Description: Create an instance of a generic type using the reflection

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

public class GenericType <T> 
{
        private final Class<T> UserType;          //Create private inner class stoeing user-defined type

        public GenericType(Class<T> gt) 
        {
                //Assign generic type
                UserType = gt;
        }

        public T create() 
        {
                //Get instance of the templatized type 
                try 
                {
                        return UserType.newInstance();
                } 
                
                //Throw an exception
                catch (Exception e) 
                {
                        e.printStackTrace();
                        return null;
                }
        }
        
        public T create(double val1, double val2) 
        {
                //Get instance of the templatized type (2D Point) 
                try 
                {
                        //Array of 3 types of construction parameters
                        Class[] constrArg = new Class[2];
                        constrArg[0] = double.class;
                        constrArg[1] = double.class;
                      
                        return UserType.getDeclaredConstructor(constrArg).newInstance(val1, val2);
                } 
                
                //Throw an exception
                catch (Exception e) 
                {
                        e.printStackTrace();
                        return null;
                }
        }
        
        public T create(String val1, double val2, double val3) 
        {
                //Get instance of the templatized type (2D Point with label) 
                try 
                {
                        //Array of 3 types of construction parameters
                        Class[] constrArg = new Class[3];
                        constrArg[0] = String.class;
                        constrArg[1] = double.class;
                        constrArg[2] = double.class;
                      
                        return UserType.getDeclaredConstructor(constrArg).newInstance(val1, val2, val3);
                } 
                
                //Throw an exception
                catch (Exception e) 
                {
                        e.printStackTrace();
                        return null;
                }
        }
        
        
        public T create(double val1, double val2, double val3) 
        {
                //Get instance of the templatized type (3D Point) 
                try 
                {
                        //Array of 3 types of construction parameters
                        Class[] constrArg = new Class[3];
                        constrArg[0] = double.class;
                        constrArg[1] = double.class;
                        constrArg[2] = double.class;
                        
                        return UserType.getDeclaredConstructor(constrArg).newInstance(val1, val2, val3);
                } 
                
                //Throw an exception
                catch (Exception e) 
                {
                        e.printStackTrace();
                        return null;
                }
        }
        
        
        public T create(String val1, double val2, double val3, double val4) 
        {
                //Get instance of the templatized type (3D Point with label) 
                try 
                {
                        //Array of 3 types of construction parameters
                        Class[] constrArg = new Class[4];
                        constrArg[0] = String.class;
                        constrArg[1] = double.class;
                        constrArg[2] = double.class;
                        constrArg[3] = double.class;
                        
                        return UserType.getDeclaredConstructor(constrArg).newInstance(val1, val2, val3, val4);
                } 
                
                //Throw an exception
                catch (Exception e) 
                {
                        e.printStackTrace();
                        return null;
                }
        }
}