// Description: Early map representation
// Zoom operations and several calculations

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

package detectprojv2j.forms;

import static java.lang.Math.*;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.text.NumberFormat;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import java.awt.Color;
import java.util.Collections;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import java.awt.Shape;
import java.awt.BasicStroke;

import detectprojv2j.structures.graticule.Meridian;
import detectprojv2j.structures.graticule.Parallel;
import detectprojv2j.structures.point.Point3DCartesian;
import detectprojv2j.structures.projection.Projection;

import detectprojv2j.consts.Consts;

import detectprojv2j.comparators.SortPointsByDistCart;




public class EarlyMap extends JPanel 
{
        private boolean repaint_vector_data;                                    //Indicator, whether vector data need to be repainted
        private double zoom;                                                    //Actual zoom ratio: xoom = [0.05, 10]
        private double dx;                                                      //Cummulated horizontal shifts
        private double dy;                                                      //Cummulated Vertical shifts
        public BufferedImage img;                                              //Image of the early map  
        private BufferedImage canvas;                                           //Image representing a canvas
        private AffineTransform at;                                             //Affine transformation object
        private Point start, end;                                               //Points for the current Canvas shift computation
        
        public List <Point3DCartesian> test_points;                             //List of test points (public, more comfortable access)
        public List <Point3DCartesian> projected_points;                        //List of projected points (public, more comfortable access)
        
        public OSMMap map;                                                        //Open street map representation
        private JPopupMenu pop_up_menu;                                         //Pop-up menu (point deletition)
        private ControlPointsForm control_points_form;                          //Table with results (needs to be updated)
        
        private Projection proj;                                                //Projection assigned to the early map
        private List <Meridian > meridians;                                     //List of meridians of the graticule
        private List <Parallel> parallels;                                      //List of parallels of the graticule
        private List <List<Point3DCartesian> > meridians_proj;                  //List of projected meridians of the graticule
        private List <List<Point3DCartesian> > parallels_proj;                  //List of projected parallels of the graticule
       
        private boolean [] add_test_point;                                      //Control point may be added to the early map
        private boolean [] add_reference_point;                                 //Control point may be added to the reference (OSM) map
        private boolean [] enable_add_control_points;                           //Enable add control points, if a pushbutton is selected
        private boolean [] enable_panning_lm;                                   //Enable panning operation using the left mouse
        private boolean [] enable_zoom_in_lm;                                   //Enable zoom-in operation using the left mouse
        private boolean [] enable_zoom_out_lm;                                  //Enable zoom-out operation using the left mouse
        private boolean [] enable_zoom_fit_all_lm;                              //Enable zoom fit all operation using the left mouse
        private boolean [] computation_in_progress;                             //Test, whether a computation is in progress
        
        private int [] index_nearest;                                           //Index of the point nearest to the cursor position
        private int [] index_nearest_prev;                                      //Index of the previous point nearest to the cursor position
        
        
        public EarlyMap(List <Point3DCartesian> test_points_, OSMMap map_, final ControlPointsForm control_points_form_, boolean [] add_test_point_, boolean [] add_reference_point_, boolean [] enable_add_control_points_,
                boolean [] enable_panning_lm_, boolean [] enable_zoom_in_lm_, boolean [] enable_zoom_out_lm_, boolean [] enable_zoom_fit_all_lm_, boolean [] computation_in_progress_, int [] index_nearest_, int [] index_nearest_prev_) 
        {
                //Initialize repaint
                repaint_vector_data = true;
                
                //Initial zoom and shifts
                zoom = 1;
                dx = 0;
                dy = 0;
               
                //Initialize start + end points for the current Canvas shift computation
                start = new Point(0, 0);
                end = new Point(0, 0);
                
                //Initialize JPG file to null
                img = null;
                canvas = null;

                //Load default image
                try
                {
                        //Get URL of the early map image
                        URL url = this.getClass().getResource("/detectprojv2j/resources/world.jpg");
                        
                        //If the image exists, load
                        if (url!= null)
                        {
                                img = ImageIO.read(url);
                        }    
                } 
                
                //Default image cannot be loaded
                catch (Exception ex) 
                {
                        ex.printStackTrace();
                }
                  
                //Create new affine transformation and set its parameters
                at = new AffineTransform();
                at.translate(0, 0);
                at.scale(1, 1);
                
                //Assign points
                test_points = test_points_;
                
                //Map projection
                proj = null;
                
                //Assign map (OSM)
                map = map_;
                
                //Assign table with results
                control_points_form = control_points_form_;
                
                //Assign indicators
                add_test_point = add_test_point_;                                    
                add_reference_point = add_reference_point_;
                enable_add_control_points = enable_add_control_points_;
                enable_panning_lm = enable_panning_lm_;
                enable_zoom_in_lm = enable_zoom_in_lm_;
                enable_zoom_out_lm = enable_zoom_out_lm_;
                enable_zoom_fit_all_lm = enable_zoom_fit_all_lm_;
                computation_in_progress = computation_in_progress_;
                index_nearest = index_nearest_;
                index_nearest_prev = index_nearest_prev_;
                
                //Meridians and parallels
                meridians = null;
                parallels = null;
                
                //Projected meridians and parallels
                projected_points = null;
                meridians_proj = null;
                parallels_proj = null; 
                
                //Enable tool tip represented by the geographic coordinates
                this.setToolTipText("");
                                               
                //Create  pop up menu
                pop_up_menu = new JPopupMenu();
                JMenuItem deleteItem = new JMenuItem("Delete control point");
                
                //Click on the item
                deleteItem.addActionListener(new ActionListener () 
                {
                        @Override
                        public void actionPerformed(ActionEvent ae) 
                        {
                                //Delete nearest point: analyzed map
                                if (!computation_in_progress[0])
                                {
                                        //Is there any nearest point?
                                        if (index_nearest[0] >= 0)
                                        {
                                                //Delete nearest point: test map
                                                if ((add_reference_point[0]) && (test_points.size() > index_nearest[0]))
                                                        test_points.remove(index_nearest[0]);

                                                //Delete nearest point: reference map
                                                if ((add_test_point[0]) && (map.reference_points.size() > index_nearest[0]))
                                                        map.deleteNearestPoint();
                                                
                                                //Delete nearest point: projected reference point
                                                if ((projected_points != null) && (projected_points.size() > index_nearest[0]))
                                                        projected_points.remove(index_nearest[0]);

                                                //Repaint both maps
                                                repaint();
                                                map.repaint();
                                                
                                                //Enable adding new points
                                                add_test_point[0] = true;
                                                add_reference_point[0] = true;

                                                //Update table with results
                                                control_points_form.clearTable();
                                                control_points_form.printResult();
                                        }
                                }
                        }
                });
                
                //Add item to pop-up menu
                pop_up_menu.add(deleteItem);
                
                //Mouse wheel rotation
                addMouseWheelListener(new MouseAdapter() {
                      
                        @Override
                        public void mouseWheelMoved(MouseWheelEvent e) 
                        {
                                //Zoom in
                                if (e.getPreciseWheelRotation() < 0) 
                                {
                                        zoomIn();
                                } 
                                
                                //Zoom out
                                else {
                                        zoomOut();
                                }     
                        }
                });
                
                //Mouse clicks
                addMouseListener(new MouseAdapter() {

                        @Override
                        public void mouseClicked(MouseEvent e) {

                                //Assign new start point of the shift (end - start)
                                start = e.getPoint();

                                //Add point to the list of test points
                                if ((SwingUtilities.isLeftMouseButton(e)) && (!enable_panning_lm[0])) 
                                {
                                        
                                        //Enable zoom in
                                        if (enable_zoom_in_lm[0])
                                        {
                                                zoomIn();
                                        }
                                        
                                        //Enable zoom out
                                        else if (enable_zoom_out_lm[0])
                                        {
                                                zoomOut();
                                        }
                                        
                                        //Enable zoom out
                                        else if (enable_zoom_fit_all_lm[0])
                                        {
                                                zoomFitAll();
                                        }

                                        //Add test point
                                        else if (add_test_point[0] && enable_add_control_points[0])
                                        { 
                                                //Get transformed cursor position for the actual zoom level
                                                final double xcur = (start.getX() - at.getTranslateX()) / at.getScaleX();
                                                final double ycur = (start.getY() - at.getTranslateY()) / at.getScaleY();
                                                
                                                //Add test point to the list
                                                //Use -y due to the different axis orientation
                                                Point3DCartesian p = new Point3DCartesian(xcur, -ycur, 0);
                                                test_points.add(p);
                                                
                                                //Disable adding the test points (next point will be reference)
                                                //(true, true)->(false, true)
                                                if (add_reference_point[0])
                                                        add_test_point[0] = false;
                                                
                                                //Enable add any of test/reference points
                                                //(false, true) -> (true, true)
                                                else 
                                                        add_reference_point[0] = true;
                                                
                                                //Update table with control points
                                                control_points_form.clearTable();
                                                control_points_form.printResult();
                                                
                                                repaint_vector_data = true;
                                        }
                                } 
                                
                                //Set zero shift (end - start) of the drawing or delete the test/reference points given by the index
                                else
                                {
                                        //No nearest point closer than a given threshold
                                        //Set zero shift, initial point of the shift (start = end)
                                        if (index_nearest[0] == -1)
                                                end = start;
                                        
                                        //Point closer than a given threshold has been found
                                        //Delete test/reference points
                                        else
                                        {
                                                //Show pop-up menu, remove test point, reference point, or a pair
                                                pop_up_menu.show(EarlyMap.this, e.getX(), e.getY());       
                                        }
                                }
                                
                                //Repaint early map
                                repaint();
                        }
                        
                        @Override
                        public void mousePressed(MouseEvent e) {

                                //Get start point of tne shift
                                start = e.getPoint();

                                //Initial coordinats of the shift
                                if (SwingUtilities.isMiddleMouseButton(e) || SwingUtilities.isRightMouseButton(e) || enable_panning_lm[0]) 
                                {
                                       end = start;
                                } 
                                
                                //Repaint early map (to avoid tooltip problems)
                                repaint();          
                        }
                });

                //Mouse shifts
                addMouseMotionListener(new MouseAdapter() {
                        @Override
                        public void mouseDragged(MouseEvent e) {
                                
                                //Move control point (LM button)
                                //Did we find a nerest point closer than a threshold?
                                if (SwingUtilities.isLeftMouseButton(e) && (index_nearest[0] != -1) && (index_nearest[0] < test_points.size())) 
                                {
                                        //Get transformed cursor position for the actual zoom level
                                        Point pcur = e.getPoint();
                                        final double xcur = (pcur.getX()- at.getTranslateX())/at.getScaleX();
                                        final double ycur = (pcur.getY()- at.getTranslateY())/at.getScaleY();

                                        //Change coordinates x,y of the shifteditem
                                        //Use -y due to the different axis orientation
                                        Point3DCartesian p = test_points.get(index_nearest[0]);
                                        p.setX(xcur);
                                        p.setY(-ycur);
                                }

                                //Move entire drawing by the vector (end - start)
                                else if  (SwingUtilities.isRightMouseButton(e) ||enable_panning_lm[0] ) {
                                        //Remember old point
                                        start = end;

                                        //Get new point
                                        end = e.getPoint();

                                        //Compute shifts
                                        dx += end.getX() - start.getX();
                                        dy += end.getY() - start.getY();
                                } 
                                
                                //Repaint early map
                                repaint();
                        }
                        
                        @Override
                        public void mouseMoved(MouseEvent e)
                        {
                                //Find the nearest point and its index
                                if (test_points.size() > 0)
                                {
                                        final double threshold = 10 / zoom;

                                        //Get transformed cursor position for the actual zoom level
                                        Point pcur = e.getPoint();
                                        final double xcur = (pcur.getX()- at.getTranslateX())/at.getScaleX();
                                        final double ycur = (pcur.getY()- at.getTranslateY())/at.getScaleY();

                                        //Create temporary point at the cursor position for sorting
                                        //Use -y due to the different axis orientation
                                        Point3DCartesian p_temp = new Point3DCartesian(xcur, -ycur, 0);

                                        //Get point nearest to the cursor position
                                        int [] index_nearest_point = {0};
                                        double [] dist_nearest_point = {0};
                                        getNearestPointIndex(p_temp, dist_nearest_point, index_nearest_point);

                                        //Point is closer than a threshold
                                        if ((index_nearest_point[0] >= 0) &&(dist_nearest_point[0] < threshold))
                                        {
                                                //Index is different from the current nearest point
                                                if ( index_nearest[0] != index_nearest_point[0])
                                                {
                                                        //Do not assign -1 as the previous nearest
                                                        if (index_nearest[0] != -1)
                                                                index_nearest_prev[0] = index_nearest[0];

                                                        //Assign the nearest point
                                                        index_nearest[0] = index_nearest_point[0];
                                                }
                                        }   

                                        //Cursor is too far from the nearest point
                                        else 
                                        {
                                                //Do not assign -1 as the previous nearest point
                                                if (index_nearest[0] != -1)
                                                        index_nearest_prev[0] = index_nearest[0];

                                                //There is no nearest point closer than a threshold
                                                index_nearest[0] = -1;
                                        }

                                        //Repaint both maps
                                        repaint();
                                        map.repaintMap();
                                }
                        }  
                });
        }
        
        
        public void getNearestPointIndex(final Point3DCartesian point, double [] dist_nearest, int [] index_nearest)
        {
                //Get index of the nearest point and its distance
                if (test_points.isEmpty())
                {
                        //There is no nearest point
                        index_nearest[0] = -1;
                        dist_nearest[0] = -1;
                        
                        return;
                }
                
                //Sort points according to the distance from the given point
                Point3DCartesian p_nearest = Collections.min(test_points, new SortPointsByDistCart(point));
                
                //Get spherical distance to the nerest point
                final double dX = point.getX() - p_nearest.getX();
                final double dY = point.getY() - p_nearest.getY();
                dist_nearest[0] = sqrt(dX * dX + dY * dY);

                //Get index of the nearest point
                index_nearest[0] = test_points.indexOf(p_nearest);
        }
        
        
        public void zoomIn ()
        {
                //Zoom in
                zoom = min(zoom + 0.025, 10);
                
                //Repaint early map
                repaint();
        } 
               
        
        public void zoomOut ()
        {
                //Zoom out
                zoom = max(zoom - 0.025, 0.05);
                
                //Repaint early map
                repaint();
        } 
        
        
        public void zoomFitAll ()
        {
                //Zoom, fit all map
                final Dimension size = this.getVisibleRect().getSize();
                
                //Get true dimensions of the rescaled window
                final double width = size.getWidth();
                final double height = size.getHeight();
                
                final double qx = width /img.getWidth();
                final double qy = height/img.getHeight();
                
                //Compute zoom ratio
                zoom = min(qx, qy);
                
                //Set shifts dx, dy to zero
                dx = 0;
                dy = 0;
                
                //Repaint early map
                repaint();
        } 
        
        
        
        public void setMap (OSMMap map_) {map = map_;}

        public void setControlPointsForm(final ControlPointsForm control_points_form_) {control_points_form = control_points_form_;}

        
        public String format(double value) {
                return NumberFormat.getNumberInstance().format(value);
        }

        
        @Override
        public Dimension getPreferredSize() {
                return new Dimension(800, 600);
        }

        
        @Override
        protected void paintComponent(Graphics g) 
        {
                //Draw the early map image and additional vector data at a current zoom level
                //Use the affine transformation
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g.create();

                //Set rendering quality
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                
                //An early map image has already been uploaded
                if (img != null)
                {    
                        //Compute shifs of the drawing for the zoom level (user shifts dx, dy are involved)
                        final Dimension size = this.getVisibleRect().getSize();
                        final double width = size.getWidth();
                        final double height = size.getHeight();
                        
                        final double zoomWidth = zoom * img.getWidth();
                        final double zoomHeight = zoom * img.getHeight();
                        
                        final double x = (width - zoomWidth) / 2 + dx;
                        final double y = (height - zoomHeight) / 2 + dy;

                        //Set shifts and scale to the affine transformation: concatenate 2 transformations
                        at = AffineTransform.getTranslateInstance(x, y);
                        at.concatenate(AffineTransform.getScaleInstance(zoom, zoom));
                                
                        //Draw early map image to the transformed graphic object
                        g2d.drawImage(img, at, this);
           
                        //Draw  list of test points to the transformed graphic object
                        if (test_points != null)
                                drawTestPoints(g2d );

                        //Draw list of projected points to the transformed graphic object
                        if (projected_points != null)
                                drawProjectedPoints(g2d );

                        //Draw meridians to the transformed graphic object
                        if (meridians_proj != null)
                        {
                                drawGraticuleElements(g2d, meridians_proj);
                        }

                        //Draw parallels to the transformed graphic object
                        if (parallels_proj != null)
                        {
                                drawGraticuleElements(g2d, parallels_proj);
                        }
                        
                        //Release system resources
                        g2d.dispose();
                }  
        }
      

        public void drawTestPoints ( Graphics2D g2 )
        {
                //Draw all test points
                final double marker_radius = Consts.MAP_MARKER_RADIUS / zoom;
                
                //Draw test points: change sign due to a different y-axis orientation
                for (final Point3DCartesian p:test_points)
                {
                        //Initially, draw a filled circle
                        g2.setColor(Color.red);
                        Ellipse2D.Float e = new Ellipse2D.Float((float)(p.getX() - marker_radius), (float)(-p.getY() - marker_radius), (float)(2.0 * marker_radius), (float)(2.0 * marker_radius));
                        g2.fill(at.createTransformedShape((Shape)e));
                        
                        //Subsequently, draw the boundary
                        g2.setColor(Color.black);
                        g2.draw(at.createTransformedShape((Shape)e));
                }
                
                //Draw the highlighted point: change sign due to a different y-axis orientation
                if ( (index_nearest[0] != -1) && (test_points.size() > index_nearest[0] ))
                {
                        //Initially, draw a filled circle
                        g2.setColor(Color.CYAN);
                        Ellipse2D.Float e = new Ellipse2D.Float((float)(test_points.get(index_nearest[0]).getX() - marker_radius), (float)(-test_points.get(index_nearest[0]).getY() - marker_radius), (float)(2.0 * marker_radius), (float)(2 * marker_radius));
                        g2.fill(at.createTransformedShape((Shape)e));
                        
                        //Subsequently, draw the boundary
                        g2.setColor(Color.black);
                        g2.draw(at.createTransformedShape((Shape)e));;
                }      
        }
        
        
        public void drawProjectedPoints(Graphics2D g2)
        {
                //Draw projected points and vectors of residuals
                final double marker_radius = Consts.MAP_MARKER_RADIUS / zoom;
                
                for (int i = 0; i < projected_points.size(); i++)
                {
                        //Get test and projected points
                        Point3DCartesian p_test = test_points.get(i);
                        Point3DCartesian p_projected = projected_points.get(i);
                        
                        //Change sign due to a different y-axis orientation
                        //Initially, draw a filled circle
                        g2.setColor(Color.yellow);
                        Ellipse2D.Float e = new Ellipse2D.Float((float)(p_projected.getX() - marker_radius), (float)(-p_projected.getY() - marker_radius), (float)(2.0 * marker_radius), (float)(2.0 * marker_radius));
                        g2.fill(at.createTransformedShape((Shape)e));
                        
                        //Subsequently, draw the boundary
                        g2.setColor(Color.black);
                        g2.draw(at.createTransformedShape((Shape)e));
                        
                        //Draw residuals, change sign due to a different y-axis orientation
                        g2.setColor(Color.red);
                        Line2D.Float l = new Line2D.Float((float)p_test.getX(), (float)(-p_test.getY()), (float)p_projected.getX(), (float)(-p_projected.getY()));
                        g2.draw(at.createTransformedShape((Shape)l));
                }
        }
        
        
        public void drawGraticuleElements ( Graphics2D g2, final List<List<Point3DCartesian>> grat_elements )
        {
                //Draw all graticule elements as lines
                g2.setStroke(new BasicStroke(2));
                g2.setColor(Color.black);
                       
                for (final List<Point3DCartesian> el:grat_elements)
                {
                        final int n = el.size();
                        
                        //Initial coordinates
                        float x1 = (float) el.get(0).getX();
                        float y1 = (float) el.get(0).getY();
                        
                        for (int i = 1; i < n ; i++)
                        {
                                //Get coordinates
                                final float x2 = (float) el.get(i).getX();
                                final float y2 = (float) el.get(i).getY();
                                
                                //Create line
                                final Line2D.Float line = new Line2D.Float();
                                
                                //Change sign due to a different y-axis orientation
                                line.x1 = x1;
                                line.y1 = -y1;
                                line.x2 = x2;
                                line.y2 = -y2;
                                
                                //Draw line containing more than 1 pixel
                                g2.draw(at.createTransformedShape((Shape)line));
                                
                                //Assign coordinates
                                x1 = x2;
                                y1 = y2;
                        }
                }
        }
        

        @Override
        public String getToolTipText(MouseEvent e) 
        {
                //Overload JPanel method to get info about cursor position/highlighted point
                String point_num_text = "";
                double xcur = 0, ycur = 0;
                
                //A point is highlighted
                if ((test_points.size() > index_nearest[0]) && (index_nearest[0] >= 0))
                {
                        //Get coordinates of the point
                        xcur = (test_points.get(index_nearest[0]).getX() - at.getTranslateX()) / at.getScaleX();
                        ycur = (test_points.get(index_nearest[0]).getY() - at.getTranslateY()) / at.getScaleY();;
                        
                        point_num_text = String.format(Locale.ROOT, "%3d", index_nearest[0] + 1) + " : ";
                }
                
                //Get position of a cursor     
                else
                {
                        //Get cursor position
                        Point pcur = e.getPoint();
                        
                        //Lon must be reduced, OSM longitude in [-2PI, 2PI]
                        xcur = (pcur.getX() - at.getTranslateX()) / at.getScaleX();
                        ycur = (pcur.getY() - at.getTranslateY()) / at.getScaleY();
                }

                //Return string: new tooltip
                return point_num_text + String.format(Locale.ROOT, "%2.2f", xcur) + "  " + String.format(Locale.ROOT, "%3.2f", ycur);      
        }
        
        //Get projection assigned to the early map
        public Projection getProjection(){return proj;}
        
        //Get list of projected poins
        public List<Point3DCartesian> getProjectedPoints() {return projected_points;}
        
        //Get list of meridians
        public List<Meridian> getMeridians() {return meridians;}
        
        //Get list of parallels
        public List<Parallel> getParallels() {return parallels;}
        
        //Get list of projected meridians
        public List<List<Point3DCartesian>> getProjectedMeridians() {return meridians_proj;}
        
        //Get list of projected parallels
        public List<List<Point3DCartesian>> getProjectedParalells() {return parallels_proj;}
        
        
        public void setProjection(final Projection proj_)
        {
                //Set projection
                proj = proj_;
        }
        
        public void setImage(final BufferedImage img_)
        {
                //Set early map image
                img = img_;
        }
        
        
        public void setProjectedPoints (final List<Point3DCartesian> projected_points_) 
        {
                //Set projected points
                projected_points = projected_points_;
                
                //Repaint early map
                repaint();
        }
        
        
        public void setMeridians (final List<Meridian> meridians_) 
        {
                //Set projected meridians
                meridians = meridians_;
        }
        
        
        public void setParallels (final List<Parallel> parallels_) 
        {
                //Set projected parallels
                parallels = parallels_;
        }
        
        public void setProjectedMeridians (final List<List<Point3DCartesian>> meridians_proj_) 
        {
                //Set projected meridians
                meridians_proj = meridians_proj_;
                
                //Repaint early map
                repaint();
        }
        
        
        public void setProjectedParallels (final List<List<Point3DCartesian>> parallels_proj_) 
        {
                //Set projected parallels
                parallels_proj = parallels_proj_;
                
                //Repaint early map
                repaint();
        }
        
        
        public boolean testRaster()
        {
                boolean drawn = false;
                for (int i = 0; i < canvas.getWidth(); i++) {
                        for (int j = 0; j < canvas.getHeight(); j++) {
                                int color = canvas.getRGB(i, j);
                                int blue = color & 0xff;
                                int green = (color & 0xff00) >> 8;
                                int red = (color & 0xff0000) >> 16;

                                if (red != 238) {
                                        drawn = true;

                                        //System.out.println("Move" + red);
                                }
                        }
                }
                
                return drawn;
        }
}
