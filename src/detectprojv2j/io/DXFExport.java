// Description: Export graticule to the DXF file

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

package detectprojv2j.io;


import java.io.FileOutputStream;
import java.io.PrintStream;
import static java.lang.Math.*;
import java.util.List;
import java.util.Locale;

import detectprojv2j.structures.point.Point3DCartesian;
import detectprojv2j.structures.graticule.Meridian;
import detectprojv2j.structures.graticule.Parallel;

import static detectprojv2j.consts.Consts.*;
import detectprojv2j.types.IPoint3DFeatures;


public class DXFExport {
        

        public static void exportGraticuleToDXF(final String file_name, final List <Meridian> meridians, final List<List<Point3DCartesian> > meridians_proj, final List <Parallel> parallels, final List <List<Point3DCartesian> > parallels_proj, final List <Point3DCartesian> test_points, final List <Point3DCartesian> reference_points_proj, final double font_height, final double step)
        {
                //Export the graticule formed by meridians/parallels into DXF file
                //Export test and reference points
                //Export generated meridians and parallels to DXF
                final int color_graticule = 8, color_test_points = 5, color_reference_points = 1;
               
               	final String level_meridians = "Meridians", level_parallels = "Parallels", level_meridian_labels = "Meridian_labels",
		level_parallel_labels = "Parallel_labels", level_test_points = "Test_points", level_test_point_labels = "Test_point_labels",
		level_proj_reference_points = "Reference_points_proj", level_proj_reference_point_labels = "Reference_points_proj_labels";
                
                try
                {
                        PrintStream file = new PrintStream(new FileOutputStream(file_name));

                        //Create header section
                        createHeaderSection(file);

                        //Create table section
                        createTableSection(file);

                        //Create layer for meridians
                        createLayerSection(file, level_meridians, color_graticule);

                        //Create layer for parallels
                        createLayerSection(file, level_parallels, color_graticule);

                        //Create layer for meridian labels
                        createLayerSection(file, level_meridian_labels, color_graticule);

                        //Create layer for parallel labels
                        createLayerSection(file, level_parallel_labels, color_graticule);
                        
                        //Create layer for test points
			createLayerSection(file, level_test_points, color_test_points);

			//Create layer for test points
			createLayerSection(file, level_test_point_labels, color_test_points);

			//Create layer for projected reference points
			createLayerSection(file, level_proj_reference_points, color_reference_points);

			//Create layer for projected reference points
			createLayerSection(file, level_proj_reference_point_labels, color_reference_points);

                        //End table header
                        endTableSection(file);

                        //Create entity section
                        createEntitySection(file);

                        //Process all meridians
                        final int nm = meridians_proj.size();
                        for (int i = 0; i < nm; i++)
                        {
                                processGraticuleElements(file, meridians_proj.get(i), meridians.get(i).getLon(), level_meridians, level_meridian_labels, font_height, step, color_graticule);
                        }

                        //Process all parallels
                        final int np = parallels_proj.size();
                        for (int i = 0; i < np; i++)
                        {
                                processGraticuleElements(file, parallels_proj.get(i), parallels.get(i).getLat(), level_parallels, level_parallel_labels, font_height, step, color_graticule);
                        }
                        
                        //Process all test points
			int index_test = 0;
			for (final Point3DCartesian point : test_points)
			{
				//Create test point
				createPoint(file, level_test_points, point.getX(), point.getY(), point.getZ(), color_test_points);

				//Create test point label
				String point_label = String.valueOf(++index_test);
				createText(file, level_test_point_labels, point_label, point.getX() + 0.5 * font_height, point.getY() - 0.5 * font_height, point.getZ(), 0.0, font_height, color_test_points);
			}

			//Process all projected reference points
			int index_reference = 0;
			for (final Point3DCartesian point : reference_points_proj)
			{
				//Create reference point
				createPoint(file, level_proj_reference_points, point.getX(), point.getY(), point.getZ(), color_reference_points);

				//Create reference point label
				String point_label = String.valueOf( ++index_reference);
				createText(file, level_proj_reference_point_labels, point_label, point.getX() + 0.5 * font_height, point.getY() - 0.5 * font_height, point.getZ(), 0.0, font_height, color_reference_points);
			}

                        //End header section
                        endHeaderSection(file);

                        //Close file
                        file.close();
                }

                //Any error has appeared
                catch (Exception e)
                {
                        e.printStackTrace();
                }
        }
        
        
        public static void exportGraticuleToDXF(final String file_name, final List <Meridian> meridians, final List<List<Point3DCartesian> > meridians_proj, final List <Parallel> parallels, final List <List<Point3DCartesian> > parallels_proj, final double font_height, final double step)
        {
                //Export the graticule formed by meridians/parallels into DXF file
                //Export generated meridians and parallels to DXF
                final int color_graticule = 8, color_test_points = 5, color_reference_points = 1;
               
               	final String level_meridians = "Meridians", level_parallels = "Parallels", level_meridian_labels = "Meridian_labels",
		level_parallel_labels = "Parallel_labels";
                
                try
                {
                        PrintStream file = new PrintStream(new FileOutputStream(file_name));

                        //Create header section
                        createHeaderSection(file);

                        //Create table section
                        createTableSection(file);

                        //Create layer for meridians
                        createLayerSection(file, level_meridians, color_graticule);

                        //Create layer for parallels
                        createLayerSection(file, level_parallels, color_graticule);

                        //Create layer for meridian labels
                        createLayerSection(file, level_meridian_labels, color_graticule);

                        //Create layer for parallel labels
                        createLayerSection(file, level_parallel_labels, color_graticule);

                        //End table header
                        endTableSection(file);

                        //Create entity section
                        createEntitySection(file);

                        //Process all meridians
                        final int nm = meridians_proj.size();
                        for (int i = 0; i < nm; i++)
                        {
                                processGraticuleElements(file, meridians_proj.get(i), meridians.get(i).getLon(), level_meridians, level_meridian_labels, font_height, step, color_graticule);
                        }

                        //Process all parallels
                        final int np = parallels_proj.size();
                        for (int i = 0; i < np; i++)
                        {
                                processGraticuleElements(file, parallels_proj.get(i), parallels.get(i).getLat(), level_parallels, level_parallel_labels, font_height, step, color_graticule);
                        }
                        
                        //End header section
                        endHeaderSection(file);

                        //Close file
                        file.close();
                }

                //Any error has appeared
                catch (Exception e)
                {
                        e.printStackTrace();
                }
        }  


        public static <Point extends IPoint3DFeatures> void exportPointsToDXF(final String file_name, final List <Point> points, final double font_height, final  int color)
        {
                //Export list of points to DXF file
                String level_points = "Points", level_point_labels = "Point_labels";
                try
                {
                        PrintStream file = new PrintStream(new FileOutputStream(file_name));
                                
                        //Create header section
                        createHeaderSection(file);

                        //Create table section
                        createTableSection(file);

                        //Create layer for points
                        createLayerSection(file, level_points, color);

                        //Create layer for point labels
                        createLayerSection(file, level_point_labels, color);

                        //End table header
                        endTableSection(file);

                        //Create entity section
                        createEntitySection(file);

                        //Process all points
                        for (final Point point:points)
                        {
                                //Create point
                                createPoint(file, level_points, point.getVal1(), point.getVal2(), point.getVal3(), color);

                                //Create point label
                                String point_label = String.valueOf(point.getVal1());
                                createText(file, level_point_labels, point_label, point.getVal1() + 0.5 * font_height, point.getVal2() - 0.5 * font_height, point.getVal3(), 0, font_height, color);
                        }

                        //End header section
                        endHeaderSection(file);

                        //Close file
                        file.close();
                }

                //Any error has appeared
                catch (Exception e)
                {
                        e.printStackTrace();
                }
        }

        
        
        public static void createHeaderSection ( PrintStream file )
        {
                //Create header section
                final String object_type_id = "0";
                final String object_type = "SECTION";
                final String header_id = "2";
                final String header_name = "HEADER";
                final String variable_name = "9";
                final String acad_version = "$ACADVER";
                final String acad_version_id =  "1";
                final String acad_version_val = "AC1006";
                final String section_terminator = "ENDSEC";

                /* Add to file */
                file.println(object_type_id);
                file.println(object_type);
                file.println(header_id);
                file.println(header_name);
                file.println(variable_name);
                file.println(acad_version);
                file.println(acad_version_id);
                file.println(acad_version_val);
                file.println(object_type_id);
                file.println(section_terminator);
        }


        public static void endHeaderSection (PrintStream file )
        {
                //Create end of the header section
                final String entity_id = "0";
                final String section_terminator ="ENDSEC";
                final String file_terminator = "EOF";

                /* Add to file */
               file.println(entity_id);
               file.println(section_terminator);
               file.println(entity_id);
               file.println(file_terminator);
        }


        public static void createTableSection (PrintStream file )
        {
                //Create table section
                final String object_type_id = "0";
                final String object_name = "SECTION";
                final String table_id = "2";
                final String table = "TABLES";
                final String table_name = "TABLE";
                final String layer_name = "LAYER";
                final String max_number_of_entries_id = "70";
                final String max_number_of_entries = "0";

               file.println(object_type_id);
               file.println(object_name);
               file.println(table_id);
               file.println(table);
               file.println(object_type_id);
               file.println(table_name);
               file.println(table_id);
               file.println(layer_name);
               file.println(max_number_of_entries_id);
               file.println(max_number_of_entries);
        }


        public static void endTableSection ( PrintStream file )
        {
                //Write end of the table section
                final String object_type_id = "0";
                final String table_end_name = "ENDTAB";
                final String section_end_name = "ENDSEC";

                /* Add to file */
                file.println(object_type_id);
                file.println(table_end_name);
                file.println(object_type_id);
                file.println(section_end_name);
        }


        public static void createLayerSection (PrintStream file, final String layer_name, final int color )
        {
                //Add section for one layer
                final String object_type_id = "0";
                final String object_name = "LAYER";
                final String layer_name_id = "2";
                final String layer_flag_id = "70";
                final String layer_flag = "0";
                final String color_number_id = "62";
                final String line_type_id = "6";
                final String line_type_name = "CONTINUOUS";

                /* Add to file */
                file.println(object_type_id);
                file.println(object_name);
                file.println(layer_name_id);
                file.println(layer_name);
                file.println(layer_flag_id);
                file.println(layer_flag);
                file.println(color_number_id);
                file.println(color);
                file.println(line_type_id);
                file.println(line_type_name);
        }


        public static void createEntitySection (PrintStream file)
        {
                //Create section for entities
                final String object_type_id = "0";
                final String object_name = "SECTION";
                final String entity_name_id = "2";
                final String entity_name = "ENTITIES";

                /* Add to file */
                file.println(object_type_id);
                file.println(object_name);
                file.println(entity_name_id);
                file.println(entity_name);
        }


        public static void createLine (PrintStream file, final String layer_name, final double x1, final double y1, final double z1, final double x2, final double y2, final double z2 )
        {
                //Write line to DXF file
                final String entity_id = "0";
                final String entity_name = "LINE";
                final String level_id = "8";
                final String xi_id = "10";
                final String yi_id = "20";
                final String zi_id = "30";
                final String xii_id = "11";
                final String yii_id = "21";
                final String zii_id = "31";

                /* Add to file */
                file.println(entity_id);
                file.println(entity_name);
                file.println(level_id);
                file.println(layer_name);
                file.println(xi_id);
                file.printf(Locale.ROOT, "%.5f%n", x1);
                file.println(yi_id);
                file.printf(Locale.ROOT, "%.5f%n", y1);
                file.println(zi_id);
                file.printf(Locale.ROOT, "%.5f%n", z1);
                file.println(xii_id);
                file.printf(Locale.ROOT, "%.5f%n", x2);
                file.println(yii_id);
                file.printf(Locale.ROOT, "%.5f%n", y2);
                file.println(zii_id);
                file.printf(Locale.ROOT, "%.5f%n", z2);
        }

        
        public static void createPoint (PrintStream file, final String layer_name, final double x, final double y, final double z, final int color )
        {
                //Write point to DXF file
                final String entity_id = "0";
                final String entity_name = "POINT";
                final String level_id = "8";
                final String color_id = "62";
                final String xi_id = "10";
                final String yi_id = "20";
                final String zi_id = "30";

                /* Add to file */
                file.println(entity_id);
                file.println(entity_name);
                file.println(level_id);
                file.println(layer_name);
                file.println(color_id);
                file.printf(Locale.ROOT, "%d%n", color);
                file.println(xi_id);
                file.printf(Locale.ROOT, "%.5f%n", x);
                file.println(yi_id);
                file.printf(Locale.ROOT, "%.5f%n", y);
                file.println(zi_id);
                file.printf(Locale.ROOT, "%.5f%n", z);
        }


        public static void createText (PrintStream file, final String layer_name, final String text, final double x, final double y, final double z, final double rotation, final double height, final int color )
        {
                //Create text
                final String entity_id = "0";
                final String entity_name = "TEXT";
                final String style_id = "7";
                final String text_style = "PNTNUM";
                final String rotation_id = "50";
                final String level_id = "8";
                final String color_id = "62";
                final String xi_id = "10";
                final String yi_id = "20";
                final String zi_id = "30";
                final String height_id = "40";
                final String text_id = "1";

                /* Add to file */
                file.println(entity_id);
                file.println(entity_name);
                file.println(style_id);
                file.println(text_style);
                file.println(rotation_id);
                file.printf(Locale.ROOT, "%.5f%n", rotation);
                file.println(level_id);
                file.println(layer_name);
                file.println(color_id);
                file.printf(Locale.ROOT, "%d%n", color);
                file.println(xi_id);
                file.printf(Locale.ROOT, "%.5f%n", x);
                file.println(yi_id);
                file.printf(Locale.ROOT, "%.5f%n", y);
                file.println(zi_id);
                file.printf(Locale.ROOT, "%.5f%n", z);
                file.println(height_id);
                file.printf(Locale.ROOT, "%.5f%n", height);
                file.println(text_id);
                file.println(text);
        }


        public static void processGraticuleElements ( PrintStream file, List<Point3DCartesian> part, final double val, final String layer_graticule_name, final String layer_labels_name, final double font_height, final double step, final int color)
        {
                //Export meridian or parallel (defined as a template parameter GraticulePart)
                final int n = part.size();

                //Process all points
                for (int i = 1; i < n; i++)
                {
                        //Get actual point
                        Point3DCartesian p = part.get(i);

                        //Get previous point
                        Point3DCartesian p_previous = part.get(i - 1);

                        //Create a line> part of the meridian or parallel
                        createLine(file, layer_graticule_name, p_previous.getX(), p_previous.getY(), p_previous.getZ(), p.getX(), p.getY(), p.getZ());
                }

                //Create label
                String point_id_text;

                if (n > 1)
                {
                        //Set accuracy depending on a step
                        
                        if (step > 1.0) point_id_text = String.format("%.1f", val);
                        else if (step > 0.1) point_id_text = String.format("%.2f", val);
                        else point_id_text = String.format("%.3f", val);

                        //Compute bearing
                        Point3DCartesian p1 = part.get( n / 2 - 1);
                        Point3DCartesian p2 = part.get(n / 2);
                        final double bearing = atan2(p2.getY() - p1.getY(), p2.getX() - p1.getX()) * RO;

                        //Create label for meridian/parallel
                        createText(file, layer_labels_name, point_id_text, part.get(n/2 - 1).getX() + 0.5 * font_height * cos(bearing * PI / 180), 
                                part.get(n/2 - 1).getY() + 0.5 * font_height * sin(bearing * PI / 180), part.get(0).getZ(), bearing, font_height, color);
                }
        }  
}
