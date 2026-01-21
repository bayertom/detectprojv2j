// Description: Estimation of the unknown cartographic projection from the map
// Version 2.0

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

package detectprojv2j;

import java.io.FileNotFoundException;

import detectprojv2j.forms.MainApplication;

import java.awt.Dimension;




public class Detectproj {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException {
        // TODO code application logic here
        /*
        double latp = 50.0, lonp = 15.0;

	double lat1 = 51, lon1 = 16, lat2 = 51, lon2 = 14, lat3 = 49, lon3 = 14, lat4 = 49, lon4 = 16;

	TTransformedLongitudeDirection mode = NormalDirection2;

	double latt1 = CartTransformation.latToLatTrans(lat1, lon1, latp, lonp);
	double latt2 = CartTransformation.latToLatTrans(lat2, lon2, latp, lonp);
	double latt3 = CartTransformation.latToLatTrans(lat3, lon3, latp, lonp);
	double latt4 = CartTransformation.latToLatTrans(lat4, lon4, latp, lonp);

	double lont1 = CartTransformation.lonToLonTrans(lat1, lon1, latp, lonp, mode);
	double lont2 = CartTransformation.lonToLonTrans(lat2, lon2, latp, lonp, mode);
	double lont3 = CartTransformation.lonToLonTrans(lat3, lon3, latp, lonp, mode);
	double lont4 = CartTransformation.lonToLonTrans(lat4, lon4, latp, lonp, mode);

	double lat21 = CartTransformation.latTransToLat(latt1, lont1, latp, lonp, mode);
	double lat22 = CartTransformation.latTransToLat(latt2, lont2, latp, lonp, mode);
	double lat23 = CartTransformation.latTransToLat(latt3, lont3, latp, lonp, mode);
	double lat24 = CartTransformation.latTransToLat(latt4, lont4, latp, lonp, mode);

	double lon21 = CartTransformation.lonTransToLon(latt1, lont1, latp, lonp, mode);
	double lon22 = CartTransformation.lonTransToLon(latt2, lont2, latp, lonp, mode);
	double lon23 = CartTransformation.lonTransToLon(latt3, lont3, latp, lonp, mode);
	double lon24 = CartTransformation.lonTransToLon(latt4, lont4, latp, lonp, mode);
        */
        /*
        TTransformedLongitudeDirection mode1 = NormalDirection2;
        //ProjectionMiscellaneous proj = new ProjectionMiscellaneous (R0, 90.0, 0.0, 10.0, mode1, 0.0, 0.0, 0.0, 1.0, Projections::F_armad, Projections::G_armad, Projections::FI_armad, Projections::GI_armad, "Armadillo", "armad");
        //ProjectionMiscellaneous proj = new ProjectionMiscellaneous (R0, 90.0, 0.0, 0.0, mode1, 0.0, 0.0, 0.0, 1.0, Projections::F_litt, Projections::G_litt, Projections::FI_litt, Projections::GI_litt, "Armadillo", "armad");
        ProjectionPseudoCylindrical proj = new ProjectionPseudoCylindrical (R0, 90.0, 0.0, 0.0, mode1, 0.0, 0.0, 0.0, 1.0, Projections::F_nell_h, Projections::G_nell_h, Projections::FI_nell_h, Projections::GI_nell_h, "Armadillo", "armad");

        double lat = 10, lon = 100;
        double []  lat_trans = {0}, lon_trans ={0}, X={0}, Y = {0};
 
        CartTransformation.latLonToXY(lat, lon, proj, 0.0, lat_trans, lon_trans, X, Y);
        double [] lat2 ={0}, lon2  = {0};
        CartTransformation.XYToLatLon(X[0], Y[0], proj, 0.0, lat_trans, lon_trans, lat2, lon2);
        */
        
        /*
        TTransformedLongitudeDirection mode = NormalDirection2;
        //ProjectionPseudoCylindrical proj = new ProjectionPseudoCylindrical (R0, 90.0, 0.0, 10.0, mode, 0.0, 0.0, 0.0, 1.0, Projections::F_mbt_s3, Projections::G_mbt_s3, Projections::F_mbt_s3, Projections::G_mbt_s3, "McBryde-Thomas, flat-pole sine III.", "mbt_s3");
        //ProjectionMiscellaneous proj = new ProjectionMiscellaneous (R0, 90.0, 0.0, 10.0, mode, 0.0, 0.0, 0.0, 1.0, Projections::F_nicol, Projections::G_nicol, Projections::FI_nicol, Projections::GI_nicol, "Nicolosi, globular", "nicol");
        ProjectionPseudoCylindrical proj = new ProjectionPseudoCylindrical (R0, 90.0, 0.0, 10.0, mode, 0.0, 0.0, 0.0, 1.0, Projections::F_goode, Projections::G_goode, Projections::F_goode, Projections::G_goode, "Goode, homolosine", "goode");
                       
        //proj.setCartPole(new Point3DGeographic(-90, 115.2, 0));
        //proj.setCartPole(new Point3DGeographic(89.5, -174.48));
        proj.setCartPole(new Point3DGeographic(90, -0.04));
        proj.setLat1(42.56);
        proj.setLon0(0);
	//proj.setLon0(-122.71);
        //final double alpha = 7.1;
        final double alpha = 0;
        final double lat_min = -90.0;
        final double lat_max = 90;
        final double lon_min = -180;//-140;
        final double lon_max = 180;//-120;
        final double lat_step = 10;
        final double lon_step = 10;
        final double font_height = 0.05 * proj.getR() * min(lat_step, lon_step) * PI / 180;
        
        TInterval lat_interval = new TInterval (lat_min, lat_max);
        TInterval lon_interval = new TInterval ( lon_min, lon_max);
        List <Meridian> meridians = new ArrayList<>();
        List <Parallel> parallels = new ArrayList<>();
        List <List<Point3DCartesian> > meridians_proj = new ArrayList<>();
        List <List<Point3DCartesian> > parallels_proj = new ArrayList<>();        
       
        //Create graticule
        Graticule gr = new Graticule(1.0, 1.0, 5, 0, 100);
        gr.createGraticule(proj, lat_interval, lon_interval, lat_step, lon_step, alpha, meridians, meridians_proj, parallels, parallels_proj, TGraticuleSampling.AdaptiveSampling, 1000, 1.0e9, 0.001);
	
        //GraticuleAS.createGraticule(proj, lat_interval, lon_interval, lat_step, lon_step, 0.1 * lat_step, 0.1 * lon_step, alpha, meridians, meridians_proj, parallels, parallels_proj);
              
        //Export graticule to DXF
        DXFExport.exportGraticuleToDXF("graticule.dxf", meridians, meridians_proj, parallels, parallels_proj, font_height, min(lat_step, lon_step));
        
        
        /*
        List <TInterval> intervals = new ArrayList<>();
        intervals.add(new TInterval (-90,90));
        intervals.add(new TInterval (-180,180));
        intervals.add(new TInterval (0,90));
        
        for ( Iterator <TInterval> i_lat_intervals = intervals.iterator(); i_lat_intervals.hasNext();  )
        {
                 TInterval lat_interval = i_lat_intervals.next();
                 
                 
                 lat_interval.min += 10;
        }
        
        
        for (int i = 0; i < intervals.size(); i++)
        {
                if (i == 0) intervals.remove(0);
        }
        
        /*
        List <Projection> projections = new ArrayList<Projection> ();
        Projections.init(projections);
        
        Projection proj = projections.get(0);
        double X = proj.getX(60, 15);
        double Y = proj.getY(50, 15);
        
        //double[][] d = { { 1, 2, 3 }, { 4, 5, 6 }, { 0, 0, 0} };

        double [] [] d ={ 
        /*{1.3757475400518885E-5, -2.847157448783027E-6, 0.0, 0.0, -2.8471620691817876E-6, 0.0 },
        {-2.847157448783027E-6, 3.0588500226423854E-5, 0.0, 0.0, 3.058852823111269E-5, 0.0 },
        {0.0, 0.0, 0.0, 0.0, 0.0, 0.0 },
        {0.0, 0.0, 0.0, 0.0, 0.0, 0.0 },
        {-2.8471620691817876E-6, 3.058852823111269E-5, 0.0, 0.0, 3.058855623584992E-5, 0.0 },
        {0.0, 0.0, 0.0, 0.0, 0.0, 0.0 }};
        
        
        {0.000013757486,   -0.000002847231,    0.000000000000,    0.000000000000,   -0.000002847227,    0.000000000000},
    {-0.000002847231,    0.000030588981,    0.000000000000,    0.000000000000,    0.000030588961,    0.000000000000},
   {0.000000000000,    0.000000000000,    0.000000000000,    0.000000000000,    0.000000000000,    0.000000000000},
     {0.000000000000,    0.000000000000,    0.000000000000,    0.000000000000,    0.000000000000,    0.000000000000},
     {-0.000002847227,    0.000030588961,    0.000000000000,    0.000000000000,    0.000030588942,    0.000000000000},
    {0.000000000000,    0.000000000000,    0.000000000000,    0.000000000000,    0.000000000000,    0.000000000000}};
        Matrix A = new Matrix(d);
        //Matrix B= new Matrix(3,3);
        // A.test(B);
       //B.print();
        Matrix B = A.pinv();
        B.print();
        */
       
        
        MainApplication ma = new MainApplication();
        ma.setSize(new Dimension(1920, 1080));
        ma.setVisible(true);
        
        
//        //Map projection analysis
//        final double lat_step = 10,  lon_step = 10;
//        List <Projection> projections = new ArrayList<Projection> ();
//        Projections.init(projections);
//        TAnalysisMethod method =  TAnalysisMethod.NMM8;
//        
//        //Input files
//        String test_file = "E:\\Tomas\\Cpp\\detectproj\\detectproj\\tests\\maps\\Habilitation\\WorldMaps\\Tectonico\\v2\\M8\\NLS\\test.txt";
//	String reference_file = "E:\\Tomas\\Cpp\\detectproj\\detectproj\\tests\\maps\\Habilitation\\WorldMaps\\Tectonico\\v2\\M8\\NLS\\reference.txt";
//
//        //Load list of test points
//	List <Point3DCartesian> test_points = IO.load3DCartPoints(test_file);
//
//	//Load list of reference points
//        List <Point3DGeographic> reference_points = IO.load3DGeoPoints(reference_file);
//        
//        //Different amount of points
//        if (test_points.size() != reference_points.size())
//                throw new BadDataException ("BadDataException: different amount of points in the test/reference files. ", "Analysis canceled.");
//        
//        //Not enough points
//        if (test_points.size() < 4)
//                throw new BadDataException ("BadDataException: not enough points (n < 4). ", "Analysis canceled.");
//
//        //Set output file
//        String output_file_text = "output.log";
//        PrintStream output = new PrintStream(new FileOutputStream(output_file_text));
//        //System.setOut(output);
//        
//        System.out.println ("\nMap projection analysis: method = " + method.ordinal() + "\n\n");
//	output.println("\nMap projection analysis: method = " + method.ordinal() + "\n\n");
//        
//        //Analyze peojwction
//	TreeMap <Double, TResult> results = new TreeMap<> ();
//	CartAnalysis.analyzeProjection(test_points, reference_points, projections, results, method, System.out);
//
//	//Print results
//	CartAnalysis.printResults(results, System.out);
//	CartAnalysis.printResults(results, output);
//        
//        //Close the file
//        output.close();
//        
//        // Geographic extent of the analyzed territory   
//        double lat_min = (Collections.min(reference_points, new SortPointsByLat())).getLat();
//        double lat_max = (Collections.max(reference_points, new SortPointsByLat())).getLat();
//        double lon_min = (Collections.min(reference_points, new SortPointsByLon())).getLon();
//        double lon_max = (Collections.max(reference_points, new SortPointsByLon())).getLon();
//        double lat_aver = 0.5 * (lat_min + lat_max), lon_aver = 0.5 * (lon_min + lon_max);
//
//        //Get limits; stretch over the whole planishere, if necessarry
//        if (((lon_min < MIN_LON + 20) || (lon_max > MAX_LON - 20)) && (lon_max - lon_min > 200))
//        {
//                lon_min = MIN_LON;
//                lon_max = MAX_LON;
//        }
//        TInterval lat_interval = new TInterval (-90, 90);
//        TInterval lon_interval = new TInterval ( -180, 180);
//        
//        System.out.println("Exporting points, graticules: ");
//        
//        //Create graticules
//        int index = 0;
//        for (Map.Entry<Double, TResult> entry : results.entrySet())
//        {
//                List <Meridian> meridians = new ArrayList<>();
//                List <Parallel> parallels = new ArrayList<>();
//                List <List<Point3DCartesian> > meridians_proj = new ArrayList<>();
//                List <List<Point3DCartesian> > parallels_proj = new ArrayList<>();
//                
//                //Set font height      
//                Double key = entry.getKey();
//                TResult value = entry.getValue();
//                
//                final double font_height = 0.05 * value.proj.getR() * min(lat_step, lon_step) * PI / 180;
//
//                //Get map rotation
//                final double alpha = value.map_rotation;
//                //std::cout << res.second.proj->getName() << '\n';
//
//                //Create graticule
//                Graticule gr = new Graticule(10.0, 10.0, 5, 0, 100);
//                gr.createGraticule(value.proj, lat_interval, lon_interval, lat_step, lon_step, alpha, meridians, meridians_proj, parallels, parallels_proj, TGraticuleSampling.UniformSampling, 1000, 1.0e9, 0.001);
//	
//                //Graticule.createGraticule(value.proj, lat_interval, lon_interval, lat_step, lon_step, 0.1 * lat_step, 0.1 * lon_step, alpha, meridians, meridians_proj, parallels, parallels_proj);
//              
//                //Create file names
//                String output_file_graticule = test_file;
//                String output_file_points_test = output_file_graticule;  //Copy string without ID and projection name
//                output_file_graticule += "_" + String.valueOf(index) + "_" + value.proj.getName();
//                String output_file_points_ref = output_file_graticule;
//                //std::string output_file_proj4 = output_file_graticule;
//                output_file_graticule += "_grat.dxf";
//                output_file_points_ref += "_points_ref.dxf";
//                output_file_points_test += "_points_test.dxf";
//                //output_file_proj4 += "_proj4.bat";
//                 System.out.println(output_file_graticule);
//                 
//                //Export graticule to DXF
//                DXFExport.exportGraticuleToDXF(output_file_graticule, meridians, meridians_proj, parallels, parallels_proj, font_height, min(lat_step, lon_step));
//                System.out.print(".");
//
//                //Increment index
//                index++;
//        }
    }
    
}
