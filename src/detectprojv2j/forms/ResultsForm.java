// Description: List of resulteds> sorted candidate projections

// Copyright (c) 2015 - 2017
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


import static java.lang.Math.min;
import static java.lang.Math.max;

import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.table.JTableHeader;
import javax.swing.JFrame;
import javax.swing.SwingConstants;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import java.util.TreeMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Collections;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import javax.swing.JSlider;

import org.openstreetmap.gui.jmapviewer.interfaces.MapMarker;

import detectprojv2j.types.TResult;
import detectprojv2j.types.TTransformedLongitudeDirection;

import detectprojv2j.structures.projection.Projection;
import detectprojv2j.structures.projection.ProjectionCylindrical;
import detectprojv2j.structures.projection.Projections;
import detectprojv2j.structures.tile.MercTile;

import detectprojv2j.comparators.sortJTableColumnByDouble;

import detectprojv2j.algorithms.mapwarp.MapWarp;

// List of resulteds> sorted candidate projections
public class ResultsForm extends javax.swing.JFrame {

        private final EarlyMap early_map;                                       //Reference to the early map
        private final OSMMap map;                                               //Reference to the Open Street Map
        private final TreeMap <Double, TResult> results;                        //List of results
        boolean [] computation_in_progress;                                     //Reference to the indicator if a computation runs
        private final JFrame frame;                                             //Reference to the main frame to change the window title
        private JSlider slider;
        private JLabel label;                                                   //Label in status bar for printing the progress of warping
        
        public ResultsForm(final EarlyMap early_map_, final OSMMap map_, final TreeMap <Double, TResult> results_, boolean [] computation_in_progress_, final int n_rows, final JFrame frame_, final JSlider slider_, JLabel label_) 
        {
                //Initialize components
                initComponents();
                
                //Initialize reference to both maps
                early_map = early_map_;
                map = map_;
 
                //Initialize array for x best candidates: meridians, parallels
                results = results_;
                
                computation_in_progress = computation_in_progress_; 
                
                //Initialize frame
                frame = frame_;
                
                //Initlialize slider
                slider = slider_;
                
                //Initialize JLabel
                label = label_;
                
                //Set rows of the table
                DefaultTableModel model = (DefaultTableModel)resultsTable.getModel();
                model.setRowCount(n_rows);
                
                //Initialize table: create header
                initializeTable();
               
                //Selected table row
                resultsTable.getSelectionModel().addListSelectionListener( new ListSelectionListener() {
                    
                        @Override
                        public void valueChanged(ListSelectionEvent event) 
                        {
                                //Index of the selected row
                                final int sel_index = resultsTable.getSelectedRow();
                                
                                //Recomputed index according to the sorted elements
                                final int proj_index  = sel_index > -1 ? resultsTable.convertRowIndexToModel( sel_index ) : -1;

                                //Test, whether amount of results < index
                                if ((proj_index < results.size()) && (proj_index >= 0))
                                {
                                        //Get selected item
                                        TResult res = results.get(results.keySet().toArray()[proj_index]);

                                        //Set projection, projected points, meridians and parallels of the i-th result
                                        early_map.setProjection(res.proj);
                                        early_map.setMeridians(res.meridians);
                                        early_map.setParallels(res.parallels);
                                        early_map.setProjectedMeridians(res.meridians_proj);
                                        early_map.setProjectedParallels(res.parallels_proj);
                                        early_map.setProjectedPoints(res.points_proj);

                                        //Update early map panel
                                        early_map.repaint();

                                        //Update main window title
                                        frame.setTitle("Map projection analysis: " + res.proj.getName() + " projection");                                          
                                } 
                        }
                });
                
                //Double click on the table row
                resultsTable.addMouseListener(new MouseAdapter() {
                        
                        @Override
                        public void mousePressed(MouseEvent e) 
                        {
                                //Get index of the row
                                JTable table =(JTable) e.getSource();
                                Point p = e.getPoint();
                                final int sel_index = table.rowAtPoint(p);
                                
                                //Recomputed index according to the sorted elements
                                final int proj_index  = sel_index > -1 ? resultsTable.convertRowIndexToModel( sel_index ) : -1;

                                //Two clicks
                                if (e.getClickCount() == 2) 
                                {
                                        //Get selected row
                                        TResult res = results.get(results.keySet().toArray()[proj_index]);
                                        
                                        //Remove all map markers except the points
                                        List <MapMarker> map_markers = Collections.synchronizedList(map.getMapMarkerList());
                                        final int np = map.reference_points.size();

                                        synchronized (map_markers)
                                        {              
                                                //More map markers than points
                                                if (map_markers.size() > np) 
                                                        for( int i = map_markers.size() - 1; i >= np; i--)
                                                                map_markers.remove(i);
                                        }

                                        //The previous analysis has been finished
                                        if (!computation_in_progress[0]) 
                                        {
                                                //Create new service
                                                ExecutorService es = Executors.newFixedThreadPool(5);
                                                
                                                //Reproject map in a selected projection to tiles
                                                if (res.tiles == null)
                                                {
                                                        final double R = 6378137.0, latp = 90.0, lonp = 0.0, lat1 = 0.0, lon0 = 0.0, dX = 0.0, dY = 0.0, c = 1.0;
                                                        double[] sx = {0}, sy = {0}, ratio = {0}, lat_sw = {0}, lon_sw = {0}, lat_ne = {0}, lon_ne = {0};

                                                        //Disable computation
                                                        computation_in_progress[0] = true;

                                                        //Create output projection for OSM: Mercator
                                                        ProjectionCylindrical merc = new ProjectionCylindrical(R, latp, lonp, lat1, TTransformedLongitudeDirection.NormalDirection, lon0, dX, dY, c, Projections::F_merc, Projections::G_merc, Projections::FI_merc, Projections::GI_merc, "Mercator", "merc");
                                                        
                                                        //Get size of the raster
                                                        final int rwidth = early_map.img.getWidth();
                                                        final int rheight = early_map.img.getHeight();                                                      
                                                        final int rsize = max(rwidth, rheight);

                                                        //Get optimal tile size : 0.1 * rsize                                       
                                                        final int min_tile = 300, max_tile = 800;
                                                        
                                                        int tile_size = min(rsize / 10, max_tile);
                                                        tile_size = max (tile_size, min_tile);
                                                        
                                                        //Create new map object for georeference and warping
                                                        MapWarp mw = new MapWarp(early_map.img, res.proj, merc, res.map_rotation, tile_size, slider, label, map);
                                                        
                                                        //Store computed results       
                                                        Future <List<MercTile>> results = es.submit(mw);

                                                        //After storing results add to the map
                                                        es.submit(() -> 
                                                        {
                                                                List<MercTile> tiles = null;

                                                                //Assign results                               
                                                                try {
                                                                        tiles = results.get();
                                                                } 

                                                                //Throw exception
                                                                catch (Exception evt) {
                                                                        evt.printStackTrace();
                                                                }
                                                               
                                                                //Enable computation
                                                                computation_in_progress[0] = false;
                                                                label.setText("");

                                                                //Store tiles into the result structure
                                                                res.tiles = tiles;
                                                        });
                                                } 
                                                
                                                //Load created tiles in specified projection
                                                else {
                                                        synchronized (map_markers) { 
                                                                for (MercTile tile : res.tiles) 
                                                                        map_markers.add(new RasterMapMarker(tile.getSWCorner(), tile.getNECorner(), tile.getImgage(), 0.01f * slider.getValue(), map));

                                                                map.repaintMap();
                                                        }
                                                }
                                                
                                        }
                                }
                        }
                });
        }
        

        @SuppressWarnings("unchecked")
        // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
        private void initComponents() {

                jLabel2 = new javax.swing.JLabel();
                jScrollPane1 = new javax.swing.JScrollPane();
                resultsTable = new javax.swing.JTable();
                jLabel1 = new javax.swing.JLabel();
                jLabel3 = new javax.swing.JLabel();

                jLabel2.setText("jLabel2");

                setTitle("List of detected projections and their properties");
                setLocation(new java.awt.Point(50, 500));
                setType(java.awt.Window.Type.UTILITY);

                resultsTable.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
                resultsTable.setModel(new javax.swing.table.DefaultTableModel(
                        new Object [][] {
                                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null}
                        },
                        new String [] {
                                "Title 1", "Title 2", "Title 3", "Title 4", "Title 5", "Title 6", "Title 7", "Title 8", "Title 9", "Title 10", "Title 11", "Title 12", "Title 13", "Title 14", "Title 15", "Title 16"
                        }
                ) {
                        boolean[] canEdit = new boolean [] {
                                false, false, false, false, false, false, false, false, false, false, false, false, false, false, true, true
                        };

                        public boolean isCellEditable(int rowIndex, int columnIndex) {
                                return canEdit [columnIndex];
                        }
                });
                resultsTable.setToolTipText("List of determined projections sorted according to the residuals.. Click on the row to see the reconstructed meridians/parallels, points.");
                resultsTable.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
                jScrollPane1.setViewportView(resultsTable);

                jLabel1.setText("+ Map  scale refers to the image resolution of 300 DPI. ");

                jLabel3.setText("* Inverse formulas are supported");

                javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
                getContentPane().setLayout(layout);
                layout.setHorizontalGroup(
                        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1800, Short.MAX_VALUE)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 281, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
                );
                layout.setVerticalGroup(
                        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 363, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel1)
                                        .addComponent(jLabel3))
                                .addGap(6, 6, 6))
                );

                pack();
        }// </editor-fold>//GEN-END:initComponents

        
        private void initializeTable()
        {
                //Set properties of the table
                String[] col_names = { "#", "Family", "Projection*", "Residuals", "R", "latk", "lonk", "lat1", "lat2", "lon0", "dX", "dY", "k", "Map scale+", "Map rotation", "q1", "q2", "Iterations"};

                DefaultTableModel model = (DefaultTableModel)resultsTable.getModel();
                model.setColumnIdentifiers(col_names);
                
                //Enable user-defined sorting by columns
                TableRowSorter <TableModel> trs = new TableRowSorter<>(model); 
                resultsTable.setRowSorter(trs);
                for (int i = 0; i < col_names.length; i++)
                        trs.setComparator(i, new sortJTableColumnByDouble());  
                
                //Set center alignment for the header
                JTableHeader header = resultsTable.getTableHeader();
                DefaultTableCellRenderer r_header_center = (DefaultTableCellRenderer)header.getDefaultRenderer();
                r_header_center.setHorizontalAlignment(SwingConstants.CENTER);
                
                //Set font size for header and table
                header.setFont( header.getFont().deriveFont(12f) );
                resultsTable.setFont(header.getFont().deriveFont(12f));
                
                //Create renderer for the remaining part  of the table
                DefaultTableCellRenderer r_cells_center = new DefaultTableCellRenderer();
                DefaultTableCellRenderer r_cells_left = new DefaultTableCellRenderer();
                r_cells_center.setHorizontalAlignment(SwingConstants.CENTER);
                r_cells_left.setHorizontalAlignment(SwingConstants.LEFT);
                
                //Set center alignment for each column of the table
                for (int i = 0; i < col_names.length; i++)
                        resultsTable.getColumnModel().getColumn(i).setCellRenderer(r_cells_center);
                
                //Set left alignment for columns 1,3
                resultsTable.getColumnModel().getColumn(1).setCellRenderer(r_cells_left);
                resultsTable.getColumnModel().getColumn(2).setCellRenderer(r_cells_left);
                
                //Get current column width
                final int col_width = resultsTable.getColumnModel().getColumn(1).getPreferredWidth();
                
                //Resize columns 1, 3
                resultsTable.getColumnModel().getColumn(0).setPreferredWidth(col_width / 3);
                resultsTable.getColumnModel().getColumn(2).setPreferredWidth((int)(3.2 * col_width));
        }
        
        
        public void printResult(final int n_results)
        {
                //Print results to the table
                clearTable();
                
                //Set amount of results
                DefaultTableModel model = (DefaultTableModel)resultsTable.getModel();
                model.setRowCount(results.size());
                  
                //Print all candidates
                int index = 0;
                final double r300 = 300.0 / 25.4 * 1000;                //Earth radius and map scale refer to the image resolution of 300 DPI
                
                for (final java.util.Map.Entry <Double, TResult> resuls_item : results.entrySet())
                {
                        //Get residuals and projection
                        double fx = resuls_item.getKey();
                        TResult result =  resuls_item.getValue();
                        Projection proj = result.proj;
                        
                        //Format numbers to the table
                        String fx_string = String.format(Locale.ROOT, "%10.3e", fx);
                        String R_string = String.format(Locale.ROOT, "%12.3f", proj.getR());    
                        String latp_string = String.format(Locale.ROOT, "%7.4f", proj.getCartPole().getLat());  
                        String lonp_string = String.format(Locale.ROOT, "%7.4f", proj.getCartPole().getLon());   
                        String lat1_string = String.format(Locale.ROOT, "%7.4f", proj.getLat1());       
                        String lat2_string = String.format(Locale.ROOT, "%7.4f", proj.getLat2());    
                        String lon0_string = String.format(Locale.ROOT, "%7.4f", proj.getLon0());  
                        String dX_string = String.format(Locale.ROOT, "%7.3f", proj.getDx());  
                        String dY_string = String.format(Locale.ROOT, "%7.3f", proj.getDy());  
                        String c_string = String.format(Locale.ROOT, "%7.3f", proj.getC());   
                        String map_scale_string = String.format(Locale.ROOT, "%15.1f", result.map_scale * r300);     
                        String map_rotation_string = String.format(Locale.ROOT, "%10.4f", result.map_rotation);   
                        String q1_string = String.format(Locale.ROOT, "%10.8f", result.q1);    
                        String q2_string = String.format(Locale.ROOT, "%10.8f", result.q2);  
                        String iterations_string = String.format(Locale.ROOT, "%10d", result.iterations);

                        //Print results
                        model.setValueAt(index + 1, index, 0);
                        model.setValueAt("  " + proj.getFamily(), index, 1);
                        model.setValueAt("  " + proj.getName(), index, 2);
                        model.setValueAt(fx_string, index, 3);
                        model.setValueAt(R_string, index, 4);
                        model.setValueAt(latp_string, index, 5);
                        model.setValueAt(lonp_string, index, 6);
                        model.setValueAt(lat1_string, index, 7);
                        model.setValueAt(lat2_string, index, 8);
                        model.setValueAt(lon0_string, index, 9);
                        model.setValueAt(dX_string, index, 10);
                        model.setValueAt(dY_string, index, 11);
                        model.setValueAt(c_string, index, 12);
                        model.setValueAt(map_scale_string, index, 13);
                        model.setValueAt(map_rotation_string, index, 14);
                        model.setValueAt(q1_string, index, 15);
                        model.setValueAt(q2_string, index, 16);
                        model.setValueAt(iterations_string, index, 17);
                        
                        index++;
                }

                //Sort table by column 3: square of residuals
                sortByColumn(3);          
                
        }
        
        
        public void clearTable()
        {
                //Clear content of the table
                DefaultTableModel model = (DefaultTableModel)resultsTable.getModel();
                final int n = model.getRowCount();
                
                //Print all candidates
                for (int index = 0; index < n; index++)
                {
                        //Print results
                        model.setValueAt("", index, 0);
                        model.setValueAt("", index, 1);
                        model.setValueAt("", index, 2);
                        model.setValueAt("", index, 3);
                        model.setValueAt("", index, 4);
                        model.setValueAt("", index, 5);
                        model.setValueAt("", index, 6);
                        model.setValueAt("", index, 7);
                        model.setValueAt("", index, 8);
                        model.setValueAt("", index, 9);
                        model.setValueAt("", index, 10);
                        model.setValueAt("", index, 11);
                        model.setValueAt("", index, 12);
                        model.setValueAt("", index, 13);
                        model.setValueAt("", index, 14);
                        model.setValueAt("", index, 15);
                        model.setValueAt("", index, 16);
                        model.setValueAt("", index, 17);
                }
        }
        
        
        private void sortByColumn(final int col_index_sort)
        {
                //Sort table according to the specific column
                TableRowSorter <TableModel> trs =(TableRowSorter) resultsTable.getRowSorter();
               
                List<RowSorter.SortKey> data = new ArrayList<>();
                data.add(new RowSorter.SortKey(col_index_sort, SortOrder.ASCENDING));
                trs.setSortKeys(data);
                
                trs.sort();   
        }
        

        // Variables declaration - do not modify//GEN-BEGIN:variables
        private javax.swing.JLabel jLabel1;
        private javax.swing.JLabel jLabel2;
        private javax.swing.JLabel jLabel3;
        private javax.swing.JScrollPane jScrollPane1;
        private javax.swing.JTable resultsTable;
        // End of variables declaration//GEN-END:variables
}
