// Description: OSM map representation
// Zoom operations and several calculations

// Copyright (c) 2015 - 2019
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
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the+
// GNU Lesser General Public License for more details.
//
// You should have received a copy of the GNU Lesser General Public License
// along with this library. If not, see <http://www.gnu.org/licenses/>.

package detectprojv2j.forms;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Collections;
import javax.swing.SwingUtilities;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import java.awt.image.BufferedImage;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import javax.swing.JLabel;

import org.openstreetmap.gui.jmapviewer.Coordinate;
import org.openstreetmap.gui.jmapviewer.DefaultMapController;
import org.openstreetmap.gui.jmapviewer.JMapViewer;
import org.openstreetmap.gui.jmapviewer.MapPolygonImpl;
import org.openstreetmap.gui.jmapviewer.interfaces.MapMarker;
import org.openstreetmap.gui.jmapviewer.MapMarkerCircle;

import detectprojv2j.types.TInterval;
import static detectprojv2j.types.TTransformedLongitudeDirection.NormalDirection2;
import detectprojv2j.types.TTransformedLongitudeDirection;

import detectprojv2j.structures.graticule.Meridian;
import detectprojv2j.structures.graticule.Parallel;
import detectprojv2j.structures.point.Point3DCartesian;
import detectprojv2j.structures.point.Point3DGeographic;
import detectprojv2j.structures.projection.ProjectionCylindrical;
import detectprojv2j.structures.projection.Projections;
import detectprojv2j.structures.projection.Projection;
import detectprojv2j.structures.tile.MercTile;

import static detectprojv2j.consts.Consts.*;

import detectprojv2j.comparators.SortPointsByDistGeo;

import detectprojv2j.algorithms.graticule.Graticule;
import detectprojv2j.algorithms.sphericaldistance.SphericalDistance;
import detectprojv2j.algorithms.carttransformation.CartTransformation;
import detectprojv2j.algorithms.graticuleAS.GraticuleAS;
import detectprojv2j.algorithms.mapwarp.MapWarp;

import javax.swing.JSlider;


public class OSMMap extends JMapViewer 
{
        public List<Point3DGeographic> reference_points;                        //List of reference points (public, more comfortable access)
        
        private EarlyMap early_map;                                             //Early map representation
        private JPopupMenu pop_up_menu;                                         //Pop-up menu (point deletition)
        private ControlPointsForm control_points_form;                          //Table with results (needs to be updated)
        
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
         
        public OSMMap(List<Point3DGeographic> reference_points_, EarlyMap early_map_, final ControlPointsForm control_points_form_,  boolean [] add_test_point_, boolean [] add_reference_point_, boolean [] enable_add_control_points_,
                boolean [] enable_panning_lm_, boolean [] enable_zoom_in_lm_, boolean [] enable_zoom_out_lm_, boolean [] enable_zoom_fit_all_lm_, boolean [] computation_in_progress_, int [] index_nearest_, int [] index_nearest_prev_) 
        {
                //Assign points
                reference_points = reference_points_;
                
                //Assign early map
                early_map = early_map_;
                
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
                
                //Enable tool tip represented by the geographic coordinates
                this.setToolTipText("");
                
                //Set map to be uninterrupted (no wrap at +- 180 deg, limitless)
                this.setScrollWrapEnabled(false);
                
                //Create  pop up menu
                pop_up_menu = new JPopupMenu();
                JMenuItem deleteItem = new JMenuItem("Delete control point.");
                                
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
                                                if ((add_reference_point[0]) && (early_map.test_points.size() > index_nearest[0]))
                                                        early_map.test_points.remove(index_nearest[0]);

                                                //Delete nearest point: reference map
                                                if ((add_test_point[0]) && (reference_points.size() > index_nearest[0]))
                                                        deleteNearestPoint();
                                                
                                                //Delete nearest point: projected reference point
                                                if ((early_map.projected_points != null) && (early_map.projected_points.size() > index_nearest[0]))
                                                        early_map.projected_points.remove(index_nearest[0]);

                                                //Repaint both maps
                                                early_map.repaint();
                                                repaint();
                                                
                                                //Enable adding new points
                                                add_test_point[0] = true;
                                                add_reference_point[0] = true;

                                                //Update table with control points
                                                control_points_form.clearTable();
                                                control_points_form.printResult();
                                        }
                                }
                        }
                });
                
                pop_up_menu.add(deleteItem);
                
                //Add listener to get the coordinates
                new DefaultMapController(this) {
                        
                        @Override
                        public void mousePressed(MouseEvent e) 
                        {
                                //If panning enabled assign panning also to the left mouse button, 
                                if (enable_panning_lm[0])
                                        this.setMovementMouseButton(MouseEvent.BUTTON1);
                                
                                //If panning disable assign panning to the right mouse button, 
                                else
                                        this.setMovementMouseButton(MouseEvent.BUTTON3);
                                
                                //Call parent method
                                super.mousePressed(e);
                        }
                        
              
                        @Override
                        public void mouseClicked(MouseEvent e) 
                        {          
                                if (SwingUtilities.isLeftMouseButton(e)) {
                                       
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
                                        
                                         //Enable zoom fit all
                                        else if (enable_zoom_fit_all_lm[0])
                                        {
                                                setZoom(1);
                                        }
                                        
                                        //Add reference point
                                        else if (add_reference_point[0] && enable_add_control_points[0])
                                        {
                                                //Get geographic position of the cursor
                                                //Lon must be reduced, OSM longitude not inside [-PI, PI]
                                                Point pcur = e.getPoint();
                                                final double lat = map.getPosition(pcur).getLat();
                                                final double lon = CartTransformation.redLon0(map.getPosition(pcur).getLon()%360.0, 0);

                                                //Add reference point to the list
                                                Point3DGeographic pg = new Point3DGeographic(lat, lon, 0);
                                                reference_points.add(pg);

                                                //Add user-defined map marker to the OSM
                                                MyMapMarker m = new MyMapMarker(lat, lon, MAP_MARKER_RADIUS);
                                                map.addMapMarker(m);
                                                
                                                //Disable adding the reference points (next point will be test)
                                                //(true, true)->(true, false)
                                                if (add_test_point[0])
                                                        add_reference_point[0] = false;
                                                
                                                //Enable add any of test/reference points
                                                //(false, true) -> (true, true)
                                                else
                                                        add_test_point[0] = true;
                                                
                                                //Repaint OSM
                                                repaintMap();
                                                
                                                //Update table with results
                                                control_points_form.clearTable();
                                                control_points_form.printResult();
                                        }
                                }
                                
                                //Delete test/reference point given by an index
                                else
                                {
                                        //Show pop-up menu, remove test point, reference point, or a pair
                                        if (index_nearest[0] != -1)
                                                pop_up_menu.show(map, e.getX(), e.getY());   
                                }
                                
                                //Repaint OSM
                                repaintMap();
                                
                        }  
                        
                        @Override
                        public void mouseMoved(MouseEvent e)
                        {
                                //Find nearest point and its index
                                if (reference_points.size() > 0)
                                {
                                        final double threshold = 0.02 * map.getMeterPerPixel();

                                        //Get geographic position of the cursor
                                        //Lon must be reduced, OSM longitude not inside [-PI, PI]
                                        Point pcur = e.getPoint();
                                        final double lat = map.getPosition(pcur).getLat();
                                        final double lon = CartTransformation.redLon0(map.getPosition(pcur).getLon()%360.0, 0);
                                        
                                        //Create temporary point at the cursor position
                                        Point3DGeographic p_temp = new Point3DGeographic(lat, lon, 0);

                                        //Get point nearest to the cursor position
                                        int [] index_nearest_point = {0};
                                        double [] dist_nearest_point = {0};
                                        getNearestPointIndex(p_temp, 6380, dist_nearest_point, index_nearest_point);

                                        //Point is closer than a threshold
                                        if ( (index_nearest_point[0] >= 0) && (dist_nearest_point[0] < threshold))
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
                                        repaintMap();
                                        early_map.repaint();                    
                                }
                        }
                        
                        
                        @Override
                        public void mouseDragged(MouseEvent e){
                                
                                //If left mouse button is not mapped for panning
                                super.mouseDragged(e);
                                       
                                //Move identical point
                                if (SwingUtilities.isLeftMouseButton(e))
                                {
                                        //Did we find a nerest point closer than a threshold?
                                        if ((index_nearest[0] != -1) && (index_nearest[0] < reference_points.size()))
                                        {
                                                //Change panning button to the right mouse button, left button is now used for moving a point
                                                if (enable_panning_lm[0])
                                                        this.setMovementMouseButton(MouseEvent.BUTTON3);

                                                //Get point and its geographic position
                                                //Lon must be reduced, OSM longitude not inside [-PI, PI]
                                                Point pcur = e.getPoint();
                                                final double lat = map.getPosition(pcur).getLat();
                                                final double lon = CartTransformation.redLon0(map.getPosition(pcur).getLon()%360.0, 0);

                                                //Change coordinates lat, lon of the shifted item
                                                Point3DGeographic p = reference_points.get(index_nearest[0]);
                                                p.setLat(lat);
                                                p.setLon(lon);

                                                //Change the corresponding marker coordinates lat, lon
                                                List <MapMarker> markers = Collections.synchronizedList(map.getMapMarkerList());
                                                
                                                synchronized (markers)
                                                {
                                                        markers.get(index_nearest[0]).setLat(lat);
                                                        markers.get(index_nearest[0]).setLon(lon);
                                                }
                                        }
                                }

                                //Repaint OSM
                                repaintMap();
                        }
                        
                };
                
                //Create projection graticule
                List <MapPolygonImpl> graticule = new ArrayList<>();
                createOSMGraticule(graticule);
                
                //Add graticule items to the map
                for (final MapPolygonImpl pol: graticule)
                {
                        this.addMapPolygon(pol);
                }  
        }
        
                
        public void setEarlyMap (EarlyMap early_map_) {early_map = early_map_;}
        
        
        public void setControlPointsForm(final ControlPointsForm control_points_form_) {control_points_form = control_points_form_;}
        
        
        public void getNearestPointIndex(final Point3DGeographic point, final double R, double [] dist_nearest, int [] index_nearest)
        {
                //Get index of the nearest point and its distance
                if (reference_points.isEmpty())
                {
                        index_nearest[0] = -1;
                        dist_nearest[0] = -1;
                        
                        return;
                }
                
                //Sort points according to the distance from the given point
                Point3DGeographic p_nearest = Collections.min(reference_points, new SortPointsByDistGeo(point, R));

                //Get spherical distance to the nerest point
                dist_nearest[0] = SphericalDistance.distance(point, p_nearest, R);                    

                //Get index of the nearest point
                index_nearest[0] = reference_points.indexOf(p_nearest);
        }
        
        
        public void deleteNearestPoint()
        {
                //Delete nearest point
                if ((index_nearest[0] >= 0) && (reference_points.size() > index_nearest[0]))
                {
                        //Remove the nearest reference point
                        deletePoint(index_nearest[0]);
                }
        }
        
        
        public void deletePoint(final int index)
        {
                //Delete reference point and its map marker
                if ((index < reference_points.size()) && (index >= 0))
                {
                        //Remove reference point
                        reference_points.remove(index); 

                        //Remove its map marker
                        List <MapMarker> markers = Collections.synchronizedList(getMapMarkerList());
                        synchronized(markers)
                        {
                                markers.remove(index);
                                
                                //Repaint OSM map
                                repaintMap();
                        }
                }
        }

        
        @Override
        public String getToolTipText(MouseEvent e) 
        {
                //Overload JPanel method to get info about cursor position/highlighted point
                String point_num_text = "";
                double lat = 0, lon = 0;
                
                //A point is highlighted
                if ((reference_points.size() > index_nearest[0]) && (index_nearest[0] >= 0))
                {
                        //Get list of map markers
                        MapMarkerCircle m =null;
                        List <MapMarker> markers = Collections.synchronizedList(this.getMapMarkerList()); 
                
                        synchronized(markers)
                        {
                                //Get the nearest mark
                                m = (MapMarkerCircle)markers.get(index_nearest[0]);

                                //Get coordinates of the point
                                lat = m.getLat();
                                lon = m.getLon();

                                point_num_text = String.format(Locale.ROOT, "%3d", index_nearest[0] + 1) + " : ";
                                }
                }
                
                //Get position of a cursor     
                else
                {
                        //Get cursor position
                        Point pcur = e.getPoint();
                        
                        //Lon must be reduced, OSM longitude not inside [-PI, PI]
                        lat = this.getPosition(pcur).getLat();
                        lon = CartTransformation.redLon0(this.getPosition(pcur).getLon()%360.0, 0);
                }
                
                //Return string: new tooltip
                return point_num_text + String.format(Locale.ROOT, "%2.2f", lat) + "  " + String.format(Locale.ROOT, "%3.2f", lon); 
       }
        
        
        public void repaintMap()
        {
                //Set properties of the map markers and repaint
                MapMarkerCircle m =null;
                List <MapMarker> markers = Collections.synchronizedList(this.getMapMarkerList());                

                synchronized(markers)
                {
                        //Set unhighlighted map marker
                        if ((reference_points.size() > index_nearest_prev[0]) && (index_nearest_prev[0] >= 0))
                        {
                                //Get the previous nearest mark
                                m = (MapMarkerCircle)markers.get(index_nearest_prev[0]);

                                //Set color
                                m.setBackColor(Color.YELLOW);
                                m.setColor(Color.BLACK); 
                        }  

                        //Set highlighted map marker
                        if ((reference_points.size() > index_nearest[0]) && (index_nearest[0] >= 0))
                        {
                                //Get the nearest map mark
                                m = (MapMarkerCircle)markers.get(index_nearest[0]);

                                //Set color
                                m.setBackColor(Color.MAGENTA);
                                m.setColor(Color.MAGENTA);
                        }

                        //Repaint map
                        repaint();
                }
        }
       

        public void createOSMGraticule (List <MapPolygonImpl> graticule)
        {
                //Create OSM graticule in Mercator projection
                List <Meridian> meridians = new ArrayList<>();
                List <Parallel> parallels = new ArrayList<>();
                List <List<Point3DCartesian> > meridians_proj = new ArrayList<>();
                List <List<Point3DCartesian> > parallels_proj = new ArrayList<>();
                
                //Define equidistant cylindrical projection, normal aspect, R = 1
                ProjectionCylindrical eqc = new ProjectionCylindrical (RO, MAX_LAT, 0.0, 0.0, NormalDirection2, 0.0, 0.0, 0.0, 1.0, Projections::F_eqc, Projections::G_eqc, Projections::F_eqc, Projections::G_eqc, "Equidistant","eqc");
                //eqc.setLon0(17.666666);
                
                //Create graticule intervals
                TInterval lat_interval = new TInterval (-80, 80);
                TInterval lon_interval = new TInterval ( -180, 180);
                
                //Graticule properties
                final double alpha = 0.0;
                final double lat_step = 10.0;
                final double lon_step = 10.0;
                
                //Create graticule in the equidistant cylindrical projection, X = lon, Y = lat
                GraticuleAS.createGraticule(eqc, lat_interval, lon_interval, lat_step, lon_step, 0.1 * lat_step, 0.1 * lon_step, alpha, meridians, meridians_proj, parallels, parallels_proj);
                
                //Convert all meridians to OSM elements
                graticuleToOSMGraticule(meridians_proj, graticule);
                
                //Convert all meridians to OSM elements
                graticuleToOSMGraticule(parallels_proj, graticule);  
        }
        
        
        public void graticuleToOSMGraticule(final List <List<Point3DCartesian> > grat_elements, List <MapPolygonImpl> graticule)
        {
                //Convert generated Mercator projection graticule to OSM representation
                for ( List<Point3DCartesian > grat_element:grat_elements)
                {
                        final int n = grat_element.size();
                        
                        //Convert to Coordinate type
                        Coordinate [] points = new Coordinate[n];
                        for (int i = 0; i < n; i++)
                                points[i] = new Coordinate(grat_element.get(i).getY(), grat_element.get(i).getX());
                        
                        //Add as a new polygon (meridians and parallels are straight)
                        List<Coordinate> grat_element_osm = new ArrayList<>(Arrays.asList(points));
                        MapPolygonImpl grat_element_osm_pol = new MapPolygonImpl(grat_element_osm);
                        grat_element_osm_pol.setColor(Color.black);
                        graticule.add(grat_element_osm_pol);
                }
        }
        
        
        public List <MercTile> warpAndDisplayEarlyMapToOSM(final Projection iproj, final double rotation, final float alpha, final JSlider slider, JLabel label)
        {
                //Reproject early map to OSM map
                final double R = 6378137.0, latp = 90.0, lonp = 0.0, lat1 = 0.0, lon0 = 0.0, dX = 0.0, dY = 0.0, c = 1.0;
                double [] sx ={0}, sy={0}, ratio = {0}, lat_sw = {0}, lon_sw = {0}, lat_ne={0}, lon_ne = {0};
                
                //The previous analysis has been finished
                if (!computation_in_progress[0]) {

                        //Disable computation
                        computation_in_progress[0] = true;

                        //Create output projection for OSM: Mercator
                        ProjectionCylindrical merc = new ProjectionCylindrical(R, latp, lonp, lat1, TTransformedLongitudeDirection.NormalDirection, lon0, dX, dY, c, Projections::F_merc, Projections::G_merc, Projections::F_merc, Projections::G_merc, "Mercator", "merc");

                        //Analyze map projection in new thread: create new service and run in two threads
                        ExecutorService es = Executors.newFixedThreadPool(5);

                        //Create new map object for georeference and warping
                        MapWarp mw = new MapWarp(early_map.img, iproj, merc, rotation, 300, slider, label, this);
                        
                        //Store computed rresults       
                        Future <List<MercTile>> results = es.submit(mw);
                      
                        //After storing results add to the map
                        es.submit(()->
                        {          
                                List <MercTile> tiles = null;
                                
                                //Assign results                               
                                try
                                {
                                        tiles = results.get();
                                }

                                //Throw exception
                                catch (Exception e)
                                {
                                        e.printStackTrace();
                                }
                                label.setText("");
                                //Enable computation
                                computation_in_progress[0] = false;   
                                System.out.println("OKOKOK");
                        
                                return tiles;
                        });                     
                }
                  
                return null;
        }
}