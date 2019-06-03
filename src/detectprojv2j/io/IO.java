// Description: Load different text files

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

package detectprojv2j.io;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Arrays;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import detectprojv2j.types.GenericType;
import detectprojv2j.types.IPoint3DFeatures;

import detectprojv2j.exceptions.BadDataException;

public class IO {
        
        
        public static <Point> void load3DPoints(final String file, List <Point> points, final Class <Point> o_class)
        {
                //Load file and split to the words
                final  List<List<String>> file_content = loadFileToWords(file);

                //Convert to 3D points
                convertTo3DPoints(file_content, points, o_class);          
        }
        
        
        public static <Point> void load2DPoints(final String file, List <Point> points, Class<Point> o_class)
        {
                //Load file and split to the words
                final  List<List<String>> file_content = loadFileToWords(file);

                //Convert to 3D points
                convertTo2DPoints(file_content, points, o_class);
        }
        
        
        public static List <List<String>> loadFileToWords(final String file_name)
        {
                //Load items from text file words 
                List<List<String>> list = new ArrayList<>();
                
                //Process file
                try {
                        Scanner s = new Scanner(new File(file_name));
                        
                        //Process file
                        while (s.hasNextLine()) {
                                String [] words = s.nextLine().split("\\s+");
                                
                                //Do not add an empty line
                                if (!words[0].isEmpty())
                                {
                                        ArrayList<String> line = new ArrayList(Arrays.asList(words));
                                        list.add(line);
                                }
                        }
                        //Close the file and return
                        s.close();
                }
                
                //Exception
                catch (Exception e)
                {
                        e.printStackTrace();
                }
                
                return list;
        }
        
        
        public static <Point> void convertTo2DPoints (final List <List<String>> file_content, List<Point> points, final Class<Point> o_class)
        {
                //Convert matrix of strings to the list of points
                points.clear();
                
                //Convert loaded points to the list of 3D points
                for (int i = 0; i < file_content.size(); i++)
                {

                        //Common 2D point with label
                        if (file_content.get(i).size() == 3)
                        {
                                //Create temporary point
                                GenericType<Point> gp = new GenericType<>(o_class);
                                Point point_temp = gp.create(file_content.get(i).get(0), Double.parseDouble(file_content.get(i).get(1)), Double.parseDouble(file_content.get(i).get(2)), 0);
                                
                                //Add point to the list
                                points.add(point_temp);
                        }

                        //Common 3D point without label
                        else if (file_content.get(i).size() == 2)
                        {
                                //Create temporary point
                                GenericType<Point> gp = new GenericType<>(o_class);
                                Point point_temp = gp.create(Double.parseDouble(file_content.get(i).get(0)), Double.parseDouble(file_content.get(i).get(1)), 0);
                                
                                //Add point to the list
                                points.add(point_temp);
                        }

                        //Throw exception
                        else throw new BadDataException("BadDataException: unknown data format. ", "Can't read the file.");
                }
                 
        }
        
        
        public static <Point> void convertTo3DPoints (final List <List<String>> file_content, List <Point> points, final Class<Point> o_class)
        {
                //Convert matrix of strings to the list of points
                points.clear();
                
                //Convert loaded points to the list of 3D points
                for (int i = 0; i < file_content.size(); i++)
                {
                        //Common 3D point with label
                        if (file_content.get(i).size() == 4)
                        {
                                //Create temporary point
                                GenericType<Point> gp = new GenericType<>(o_class);
                                Point point_temp = gp.create(file_content.get(i).get(0), Double.parseDouble(file_content.get(i).get(1)), Double.parseDouble(file_content.get(i).get(2)), Double.parseDouble(file_content.get(i).get(3)));
                                
                                //Add point to the list
                                points.add(point_temp);
                        }

                        //Common 3D point without label
                        else if (file_content.get(i).size() == 3)
                        {
                                //Create temporary point
                                GenericType<Point> gp = new GenericType<>(o_class);
                                Point point_temp = gp.create(Double.parseDouble(file_content.get(i).get(0)), Double.parseDouble(file_content.get(i).get(1)), Double.parseDouble(file_content.get(i).get(2)));
                                
                                //Add point to the list
                                points.add(point_temp);
                        }

                        //Throw exception
                        else throw new BadDataException("BadDataException: unknown data format. ", "Can't read the file.");
                }
                 
        }
        
        
        public static <Point extends IPoint3DFeatures> void save2DPoints(final String file_text, final List <Point> points)
        {
                //Load file and split to the words
                try {
                        //Create output file
                        File file = new File(file_text);

                        //Create new file
                        if (!file.exists())
                                file.createNewFile();

                        //Create stream
                        FileWriter fw = new FileWriter(file.getAbsoluteFile());

                        //Process all points, store point_id, first coordinate, second coordinate
                        for (Point point:points)
                        {
                                fw.write(/*point.getVal1()+ "\t" +*/ point.getVal2() + "\t" + point.getVal3() + "\n");
                        }

                        fw.flush();
                } 
                
                //Throw exception
                catch (IOException e) 
                {
                    e.printStackTrace();
                }
        }
}
