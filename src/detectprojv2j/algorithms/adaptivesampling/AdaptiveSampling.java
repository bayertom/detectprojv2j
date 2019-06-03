/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package detectprojv2j.algorithms.adaptivesampling;

import detectprojv2j.structures.point.Point3DGeographic;
import detectprojv2j.types.ICoordFunctionProj;
import detectprojv2j.types.IFunctionX;
import java.awt.geom.Point2D;
import java.util.List;

/**
 *
 * @author Tomáš
 */
public class AdaptiveSampling {
 
        /*
        public static void as(final ICoordFunctionProj F, final ICoordFunctionProj G, final List<Point3DGeographic> points, final double a_lat, final double b_lat, final double a_lon, final double b_lon, final int d, final double fmax, final int dmin, final int dmax, final double eps, final double dalpha)
        {
                //Create polygonal approximation of function using adaptive sampling
                asInit(F, G, points, a_lat, b_lat, a_lon, b_lon, d, fmax, dmin, dmax, eps, dalpha);
        }
        
        private static void asInit(final ICoordFunctionProj F, final ICoordFunctionProj G, final List<Point3DGeographic> points, final double a_lat, final double b_lat, final double a_lon, final double b_lon, final int d, final double fmax, final int dmin, final int dmax, final double eps, final double dalpha)
        {
                //Initialize adaptive sampling procedure for meridian / parallel
                final double xa = F.f(a_lat, a_lon);
                final double ya = G.f(a_lat, a_lon);
                final double xb = F.f(b_lat, b_lon);
                final double yb = G.f(b_lat, b_lon);
                
                //Singular case
                if ( Math.abs(xa) > fmax)
                        throw new MathSingularityException("", "", u); 

                //Singular case
                if ( Math.abs(fv) > fmax)
                        throw new MathSingularityException("", "", v);
                
                //Add first point
                points.add(new Point2D.Double(u, fu));
                        
                //Intermediat points
                adaptiveSamplingInt3(fx, points, u, v, fu, fv, d, fmax, nmax, eps, ff);
                
                //Add last point;
                points.add(new Point2D.Double(v, fv));
                
                //System.out.println(n[0]);
        }
        
         public static void adaptiveSamplingInt3(final IFunction fx, final List<Point2D.Double> points, final double a, final double b, final double ya, final double yb, final int d, final double fmax, final int dmax, final double eps, final double ff)
        {
                //Stop sampling
                if (d >= dmax)
                        return;
                
                final double dmin = 3;
                //System.out.println("Depth= " + d);
                
                Random rand = new Random();
                final double r1 = rand.nextDouble() * 0.1 + 0.45;
                final double r2 = rand.nextDouble() * 0.1 + 0.45;
                final double r3 = rand.nextDouble() * 0.1 + 0.45;
                
                //Compute mid point
                final double x1 = a + r1 * (b-a) / 2.0;
                final double x2 = a + r2 * (b-a);
                final double x3 = a + r3 * (b-a) * 3.0 /2.0;

                final double y1 = fx.f(x1);
                final double y2 = fx.f(x2);
                final double y3 = fx.f(x3);

                //Singular case
                if ( discontLR(fx, x1, 0.5 * eps))
                        throw new MathSingularityException("", "",x1); 
                
                //Singular case
                if ( discontLR(fx, x2, 0.5 * eps))
                        throw new MathSingularityException("", "",x2); 
                
                //Singular case
                if ( discontLR(fx, x3, 0.5 * eps))
                        throw new MathSingularityException("", "",x3); 

                //Test criteria
                double alpha1 = getCriterion(a, ya, x1, y1, x2, y2);
                double alpha2 = getCriterion(x1, y1, x2, y2, x3, y3);
                double alpha3 = getCriterion(x2, y2, x3, y3, b, yb);
                        
                //Not flat, recursion, first part
                if (alpha1 > ff || alpha2 > ff || alpha3 > ff || d < dmin)
                {
                        //First sub interval
                        if (alpha1 > ff || d < dmin)
                                adaptiveSamplingInt3(fx, points, a, x1, ya, y1, d+1, fmax, dmax, eps, ff);
                        
                        //Add new point
                        points.add(new Point2D.Double(x1, y1));
                        
                        //Second sub interval
                        if (alpha1 > ff || alpha2 > ff || d < dmin)
                                adaptiveSamplingInt3(fx, points, x1, x2, y1, y2, d+1, fmax, dmax, eps, ff);
                        
                        //Add new point
                        points.add(new Point2D.Double(x2, y2));
                        
                        //Third sub interval
                        if (alpha2 > ff || alpha3 > ff || d < dmin)
                                adaptiveSamplingInt3(fx, points, x2, x3, y2, y3, d+1, fmax, dmax, eps, ff);
                        
                        //Add new point
                        points.add(new Point2D.Double(x3, y3));
                        
                        //Fourth sub interval
                        if (alpha3 > ff || d < dmin)
                                adaptiveSamplingInt3(fx, points, x3, b, y3, yb, d+1, fmax, dmax, eps, ff);
                }    
        }

        
*/        
        
}
