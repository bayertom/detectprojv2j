// Description: Performs cartometric analysis (i.e. estimation of the map projection)
// Computation runs in a separate thread

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

package detectprojv2j.algorithms.cartanalysis;

import static java.lang.Math.*;

import java.util.List;
import java.util.ArrayList;
import java.util.TreeMap;
import java.util.Collections;
import java.io.PrintStream;
import java.util.Map;
import javax.swing.JToggleButton;
import javax.swing.SwingUtilities;

import detectprojv2j.types.TAnalysisMethod;
import detectprojv2j.types.TResult;
import detectprojv2j.types.TAdaptiveControl;
import detectprojv2j.types.TMutationStrategy;

import detectprojv2j.structures.point.Point3DCartesian;
import detectprojv2j.structures.point.Point3DGeographic;
import detectprojv2j.structures.projection.Projection;
import detectprojv2j.structures.matrix.Matrix;

import static detectprojv2j.consts.Consts.*;

import detectprojv2j.comparators.SortPointsByLat;
import detectprojv2j.comparators.SortPointsByLon;

import detectprojv2j.algorithms.carttransformation.CartTransformation;
import detectprojv2j.algorithms.geneticalgorithms.DifferentialEvolution;
import detectprojv2j.algorithms.geneticalgorithms.FRM7DE;
import detectprojv2j.algorithms.geneticalgorithms.FRM8DE;
import detectprojv2j.algorithms.simplexmethod.FRM7NM;
import detectprojv2j.algorithms.simplexmethod.FRM8NM;
import detectprojv2j.algorithms.nonlinearleastsquares.*;
import detectprojv2j.algorithms.numdifferentiation.FJM7;
import detectprojv2j.algorithms.numdifferentiation.FJM8;
import detectprojv2j.algorithms.transformation.HelmertTransformation2D;
import detectprojv2j.algorithms.simplexmethod.*;


public class CartAnalysisMT implements Runnable
{
        private final List<Point3DCartesian> test_points;
        private final List <Point3DGeographic> reference_points; 
	private final List <Projection> projections;
        private final TreeMap<Double, TResult> results;
        private final TAnalysisMethod method;
        private final boolean analyze_lon0;
        private final PrintStream s;
        private final JToggleButton button;
        private final Runnable f_callback;
        
        public CartAnalysisMT(final List<Point3DCartesian> test_points_, final List <Point3DGeographic> reference_points_, List <Projection> projections_, 
                TreeMap<Double, TResult> results_, final TAnalysisMethod method_, final boolean analyze_lon0_, final PrintStream s_, final JToggleButton button_, final Runnable f_callback_)
        {
                test_points = test_points_;
                reference_points = reference_points_;
                projections = projections_;
                results = results_;
                method = method_;
                analyze_lon0 = analyze_lon0_;
                s = s_;  
                f_callback = f_callback_;
                button = button_;
        }
        
        
        @Override
        public void run ()
        {
                //Run analysis in the separate thread
                analyzeProjection();
        }
        
        
        public void analyzeProjection()
        {
                //Analyze all projections
                final int n_points = test_points.size();
                final int n_proj = projections.size();
                String button_text = button.getText();

                //Geographic extent of the analyzed territory
                final double lat_min = (Collections.min(reference_points, new SortPointsByLat())).getLat();
                final double lat_max = (Collections.max(reference_points, new SortPointsByLat())).getLat();
                final double lon_min = (Collections.min(reference_points, new SortPointsByLon())).getLon();
                final double lon_max = (Collections.max(reference_points, new SortPointsByLon())).getLon();
                final double lat_aver = 0.5 * (lat_min + lat_max), lon_aver = 0.5 * (lon_min + lon_max);

                //Initialize cartographic parameters
                final double lat1 = (lat_aver == 0 ? 10 : lat_aver);
                final double lat2 = lat1 + 10;
                final double latp = min(90.0 - abs(lat_aver), 80.0);
                final double lonp = (lon_aver > 90 ? lon_aver - 270 : (latp == 90 ? 0 : lon_aver + 90));
                final double lon0 =  lon_aver;

                //Process all analyzed projections
                int processed = 1;
                for (final Projection proj : projections)
                {
                        //Set label of the run button
                        int status = (int)((1.0 * (++processed)) / n_proj *100);
                        if (status %2 == 0)
                                button.setText("Analyzing map" + " (" + String.valueOf(status) + "%)");

                        //Set parameters of the projection
                        proj.setCartPole(new Point3DGeographic (latp, lonp,0));
                        proj.setLat1(lat1);
                        proj.setLat2(lat2);
                        proj.setLon0(lon0);
                        proj.setC(1.0);
                        
                        //Print analyzed projection name
                        try
                        {
                                //Get initial scale
                                List <Point3DCartesian> reference_points_projected = new ArrayList<>();

                                //Apply projection proj(Q.P'): convert geographic points to the cartesian 
                                for (final Point3DGeographic p : reference_points)
                                {
                                        //(lat, lon) . (lat_trans, lon_trans)
                                        final double lat_trans = CartTransformation.latToLatTrans(p.getLat(), p.getLon(), proj.getCartPole().getLat(), proj.getCartPole().getLon());
                                        final double lon_trans = CartTransformation.lonToLonTrans(p.getLat(), p.getLon(), proj.getCartPole().getLat(), proj.getCartPole().getLon(), proj.getLonDir());

                                        //Reduce longitude
                                        final double lon_transr = CartTransformation.redLon0(lon_trans, proj.getLon0());
                                        
                                        //(lat_trans, lon_trans) . (X, Y)
                                        final double XR = proj.getX(lat_trans, lon_transr);
                                        final double YR = proj.getY(lat_trans, lon_transr);

                                        //Add point to the list
                                        reference_points_projected.add(new Point3DCartesian (XR, YR, 0));
                                }

                                //Compute 2D Helmert transformation between P, P'                                
                                List <Double>  weights = new ArrayList <> (Collections.nCopies(test_points.size(), 1.0));
                                HelmertTransformation2D  key = new HelmertTransformation2D();
                                key.getTransformKey(test_points, reference_points_projected, weights);
                                double [] R_0 = {sqrt(key.getC1() * key.getC1() + key.getC2() * key.getC2()) * proj.getR()};

                                //Remember old radius
                                final double R = proj.getR();

                                //Set new radius
                                proj.setR(R_0[0]);

                                //Get initial matrices: X, A, B
                                int  m = (method.ordinal() < 3 ? 7 : 6);
                                Matrix  X = (method.ordinal() < 3 ? X0M7 (m, proj, method) : X0M8 (m, proj, method));
                                Matrix  A = (method.ordinal() < 3 ? AM7 (m, proj, R_0[0]) : AM8 (m, proj));
                                Matrix  B = (method.ordinal() < 3 ? BM7 (m, proj, R_0[0]) : BM8 (m, proj));

                                //Transpose matrices (Differential evolution, Nelder-Mead)
                                Matrix XT = X.trans();
                                Matrix AT = A.trans();
                                Matrix BT = B.trans();
                                Matrix  XAVER = new Matrix(XT.rows(), XT.cols());
                                XAVER.copy(XT);

                                //Initialization
                                boolean add_x0 = true;
                                int population = 2 * m * A.rows(), max_iter_nls = 80, max_gen = 100, max_iter_nm = 700;
                                final double alpha = 0.0001, nu = 0.0001, max_error = 1.0e-10, max_diff = 1.0e-12, eps = 1.0e-10, CR [] = {0.8};
                                double min_cost = MAX_FLOAT;
                                double res_aver[] = {0}, res_max [] = {0};
                                
                                int [] iterations = {0};
                                double [] q1 = {key.getC1()};
                                double [] q2 = {key.getC2()};
                                double [] dx = {0};
                                double [] dy = {0};
                                
                                Matrix W = new Matrix(2 * n_points, 2 * n_points, 0.0, 1);
                                Matrix Y = new Matrix(2 * n_points, 1);
                                Matrix V = new Matrix(2 * n_points, 1);
                                Matrix F = new Matrix(1, 1); 
                                F.items[0][0] = 0.5;
                                
                                //Create objects for residual computations
                                FRM8 frm8 = null;
                                FRM7 frm7 = null;

                                if (method.ordinal() < 3)
                                        frm7 = new FRM7(test_points, reference_points, proj.getX(), proj.getY(), proj.getLonDir(), dx, dy);         
                                else
                                        frm8 = new FRM8(test_points, reference_points, proj.getX(), proj.getY(), proj.getLonDir(), R_0, q1, q2, dx, dy);
                                
                                // Method M7, Non-linear least squares
                                if (method == TAnalysisMethod.NLSM7)
                                {
                                        FJM7 fjm7 = new FJM7(test_points, reference_points, proj.getX(), proj.getY(), proj.getLonDir());
                                        min_cost = NonLinearLeastSquares.BFGSH(fjm7::function, frm7::function, W, X, Y, V, A, B, iterations, alpha, nu, max_error, max_iter_nls, max_diff);
                                }

                                // Method M8, Non-linear least squares
                                else if (method == TAnalysisMethod.NLSM8)
                                {
                                        FJM8 fjm8 = new FJM8(test_points, reference_points, proj.getX(), proj.getY(), proj.getLonDir(), R_0, q1, q2);
                                        min_cost = NonLinearLeastSquares.BFGSH( fjm8::function, frm8::function, W, X, Y, V, A, B, iterations, alpha, nu, max_error, max_iter_nls, max_diff);
                                }

                                // Method M7, Differential evolution
                                else if (method == TAnalysisMethod.DEM7)
                                {
                                        //Create object for residual compuation
                                        FRM7DE frm7de = new FRM7DE(frm7);
                                        min_cost = DifferentialEvolution.diffEvolution(frm7de::function, population, eps, max_gen, F, CR, TMutationStrategy.DERand1Strategy, TAdaptiveControl.MFDE, W, XT, Y, V, AT, BT, XAVER, res_aver, res_max, iterations, add_x0, s);
                                        
                                        X = XT.trans();
                                }

                                // Method M8, Differential evolution
                                else if (method == TAnalysisMethod.DEM8)
                                {
                                        //Create object for residual compuation
                                        FRM8DE frm8de = new FRM8DE(frm8);
                                        min_cost = DifferentialEvolution.diffEvolution(frm8de::function, population, eps, max_gen, F, CR, TMutationStrategy.DERand1Strategy, TAdaptiveControl.MFDE, W, XT, Y, V, AT, BT, XAVER, res_aver, res_max, iterations, add_x0, s);
                                        
                                        X = XT.trans();
                                }

                                // Method M7, Nelder-Mead optimization
                                else if (method == TAnalysisMethod.NMM7)
                                {
                                        //Create object for residual compuation
                                        FRM7NM frm7nm = new FRM7NM(frm7);
                                     
                                        min_cost = SimplexMethod.NelderMead(frm7nm::function, W, XT, Y, V, AT, BT, iterations, eps, max_iter_nm, add_x0, System.out);
                                        X = XT.trans();
                                }

                                // Method M8, Nelder-Mead optimization
                                else if (method == TAnalysisMethod.NMM8)
                                {
                                        //Create object for residual compuation
                                        FRM8NM frm8nm = new FRM8NM(frm8);
                                        
                                        min_cost = SimplexMethod.NelderMead(frm8nm::function, W, XT, Y, V, AT, BT, iterations, eps, max_iter_nm, add_x0, System.out);
                                        X = XT.trans();
                                }

                                //Set the determined parameters to the projections
                                if (method.ordinal() < 3) proj.setR(X.items[0][0]); else proj.setR(R_0[0]);
                                if (method.ordinal() < 3) proj.setCartPole(new Point3DGeographic (X.items[1][0], X.items[2][0], 0)); 
                                        else proj.setCartPole(new Point3DGeographic (X.items[0][0], X.items[1][0], 0));
                                if (method.ordinal() < 3) proj.setLat1(X.items[3][0]); else proj.setLat1(X.items[2][0]);
                                if (method.ordinal() < 3) proj.setLat2(X.items[4][0]); else proj.setLat2(X.items[3][0]);
                                if (method.ordinal() < 3) proj.setLon0(X.items[5][0]); else proj.setLon0(X.items[4][0]);
                                if (method.ordinal() < 3) proj.setC(X.items[6][0]); else proj.setC(X.items[5][0]);
                                proj.setDx(dx[0]);
                                proj.setDy(dy[0]);
                                
                                //Set the determined  parameters to the map
                                final double map_scale = R / proj.getR() * 1000 ;
                                //System.out.println("R:" + R + " RR: " + proj.getR() + " S: " + map_scale);
                                final double rotation = (method.ordinal() < 3 ? 0 : atan2(q2[0], q1[0]) * 180.0 / PI);

                                //Add result to the list of results
                                TResult  res = new TResult (proj, map_scale, rotation, q1[0], q2[0], iterations[0]);
                                results.put(min_cost, res);        
                        }

                        catch (Exception e)
                        {
                                e.printStackTrace();
                                //System.out.println(proj.getName());
                        }
                }
                
                //Set the old text caption
                button.setText(button_text);
                
                //Process the callback
                SwingUtilities.invokeLater(f_callback);
        }
                
                
        private Matrix X0M7(final int m, final Projection proj, final TAnalysisMethod method_)
        {
                //Set initial solution, M7 method
                Matrix  X0 = new Matrix(m, 1);
                
                X0.items[0][0] = proj.getR();
                X0.items[1][0] = (method.ordinal() %3 == 0 ? proj.getCartPole().getLat() : 89.0);
                X0.items[2][0] = (method.ordinal() %3 == 0 ? proj.getCartPole().getLon() : 10.0);
                X0.items[3][0] = proj.getLat1();
                X0.items[4][0] = proj.getLat2();
                X0.items[5][0] = proj.getLon0();
                X0.items[6][0] = proj.getC();

                return X0;
        }

        
        private Matrix X0M8(final int m, final Projection proj,final TAnalysisMethod method_ )
        {
                //Set initial solution, M8 method
                Matrix X0 = new Matrix(m, 1);
                
                X0.items[0][0] = (method.ordinal() %3 == 0 ? proj.getCartPole().getLat() : 89.0);
                X0.items[1][0] = (method.ordinal() %3 == 0 ? proj.getCartPole().getLon() : 10.0);
                X0.items[2][0] = proj.getLat1();
                X0.items[3][0] = proj.getLat2();
                X0.items[4][0] = proj.getLon0();
                X0.items[5][0] = proj.getC();

                return X0;
        }


        private Matrix AM7(final int m, final Projection proj, final double scale)
        {
                //Return matrix of the lower bounds, method M7
                Matrix  A = new Matrix(m, 1);

                A.items[0][0] = 0.01 * scale;
                A.items[1][0] = -90.0;
                A.items[2][0] = -180.0;
                A.items[3][0] = proj.getLat1Interval().min_value;
                A.items[4][0] = proj.getLat1Interval().min_value;
                A.items[5][0] = (analyze_lon0 ? -180 : 0.0);
                A.items[6][0] = 0;

                return A;
        }

        
        private Matrix BM7(final int m, final Projection proj, final double scale)
        {
                //Return matrix of the upper bounds, method M7
                Matrix B = new Matrix(m, 1);

                B.items[0][0] = 100.0 * scale;
                B.items[1][0] = 90.0;
                B.items[2][0] = 180.0;
                B.items[3][0] = proj.getLat1Interval().max_value;
                B.items[4][0] = proj.getLat1Interval().max_value;
                B.items[5][0] = (analyze_lon0 ? 180.0 : 0.0);
                B.items[6][0] = 100.0;

                return B;
        }


        
        private Matrix AM8(final int m, final Projection proj)
        {
                //Return matrix of the lower bounds, method M8
                Matrix A = new Matrix(m, 1);

                //Set initial solution
                A.items[0][0] = -90.0;
                A.items[1][0] = -180.0;
                A.items[2][0] = proj.getLat1Interval().min_value;
                A.items[3][0] = proj.getLat1Interval().min_value;
                A.items[4][0] = (analyze_lon0 ? -180.0 : 0.0);
                A.items[5][0] = 0.0;

                return A;
        }

        
        private Matrix BM8(final int m, final Projection proj)
        {
                //Return matrix of the upper bounds, method M8
                Matrix B = new Matrix(m, 1);

                //Set initial solution
                B.items[0][0] = 90.0;
                B.items[1][0] = 180.0;
                B.items[2][0] = proj.getLat1Interval().max_value;
                B.items[3][0] = proj.getLat1Interval().max_value;
                B.items[4][0] = (analyze_lon0 ? 180.0 : 0.0);
                B.items[5][0] = 100.0;
                
                return B;
        }

        
        public void printResults(final Map<Double, TResult> results, PrintStream s)
        {
                //Print results to the table
                s.println();
                
                //Create header
                s.format("%4s %13s %8s %10s %12s %7s %7s %7s %7s %7s %7s %15s %10s %10s", "#", "Family", "Proj.", "Resid.", "R", "latP", 
                        "lonP", "lat1", "lat2", "lon0", "C", "Scale", "Rotation", "Iter.");
                
                //Print all candidates into the table grid
                int index = 1;
                for (final Map.Entry <Double, TResult> resuls_item : results.entrySet())
                {
                        //Get residuals and projection
                        double fx= resuls_item.getKey();
                        TResult result =  resuls_item.getValue();
                        Projection proj = result.proj;

                        //Print results
                        s.format(" %3d %13s %8s %10.3e %12.3f %7.1f %7.1f %7.1f %7.1f %7.1f %7.1f %15.1f %10.2f %10d", index, proj.getFamily(),  proj.getName(), fx, proj.getR(), proj.getCartPole().getLat(), 
                                 proj.getCartPole().getLon(), proj.getLat1(), proj.getLat2(), proj.getLon0(), proj.getC(), result.map_scale, result.map_rotation, result.iterations);
                        s.println("");
                        
                        index ++;
                }
        }
}
