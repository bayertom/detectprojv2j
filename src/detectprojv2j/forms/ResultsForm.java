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
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import java.io.File;
import static java.lang.Math.*;

import java.util.TreeMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.Future;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.JSlider;

import detectprojv2j.types.TResult;
import detectprojv2j.types.TTransformedLongitudeDirection;
import detectprojv2j.types.TMosaicMap;
import detectprojv2j.structures.projection.Projection;
import detectprojv2j.structures.projection.ProjectionCylindrical;
import detectprojv2j.structures.projection.Projections;
import detectprojv2j.structures.graticule.Meridian;
import detectprojv2j.structures.graticule.Parallel;
import detectprojv2j.structures.point.Point3DCartesian;
import detectprojv2j.algorithms.mapreproject.MapReproject;
import detectprojv2j.algorithms.mosaicmapexport.MosaicMapExport;
import static detectprojv2j.consts.Consts.*;
import detectprojv2j.comparators.sortJTableColumnByDouble;
import detectprojv2j.io.IO;
import detectprojv2j.io.DXFExport;



// List of resulteds> sorted candidate projections
public class ResultsForm extends javax.swing.JFrame {

        private final EarlyMap early_map;                                       //Reference to the early map
        private final OSMMap map;                                               //Reference to the Open Street Map
        private final TreeMap <Double, TResult> results;                        //List of results
        private int proj_index;                                                 //Index of the selected projection
        
        boolean [] computation_in_progress;                                     //Reference to the indicator if a computation runs
        private final JFrame frame;                                             //Reference to the main frame to change the window title
        private JSlider slider;                                                 //Reference to the slider
        private JLabel label;                                                   //Label in status bar for printing the progress of reprojection

        
        public ResultsForm(final EarlyMap early_map_, final OSMMap map_, final TreeMap <Double, TResult> results_, boolean [] computation_in_progress_, final int n_rows, final JFrame frame_, final JSlider slider_, JLabel label_) 
        {
                //Initialize components
                initComponents();
                
                //Initialize reference to both maps
                early_map = early_map_;
                map = map_;
 
                //Assign results of x best candidates: meridians, parallels
                results = results_;
                
                //Set selected projection index
                proj_index = -1;
                
                //Set compuation in progress
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
                        public void valueChanged(ListSelectionEvent e) 
                        {
                                //Index of the selected row
                                proj_index  = getSelectedProjIndex();

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
                                        
                                        //Set reprojected tiles
                                        map.setReprojectedTiles(res.tiles);

                                        //Update early map panel
                                        early_map.repaint();

                                        //Update main window title
                                        frame.setTitle("Map projection analysis: " + res.proj.getName() + " projection");      

                                        //Repaint map
                                        map.repaintMap();
                                } 
                        }
                });
                
                //Double click on the table row
                resultsTable.addMouseListener(new MouseAdapter() {
                        
                        @Override
                        public void mousePressed(MouseEvent e) 
                        {
                                //Get index of the row
                                proj_index  = getSelectedProjIndex();

                                //Two clicks
                                if (e.getClickCount() == 2) 
                                {
                                        //Perform reprojection 
                                        performReprojection();  
                                }
                                
                                //Update results
                                printResults(20);
                        }
                });  
        }
        
        
        @SuppressWarnings("unchecked")
        // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
        private void initComponents() {

                jScrollPane1 = new javax.swing.JScrollPane();
                resultsTable = new javax.swing.JTable();
                jLabel1 = new javax.swing.JLabel();
                jLabel3 = new javax.swing.JLabel();
                reprojectionToolbar = new javax.swing.JToolBar();
                inverseReprojectionButton = new javax.swing.JButton();
                saveInverseReprojectionButton = new javax.swing.JButton();
                jSeparator3 = new javax.swing.JToolBar.Separator();
                deleteInverseReprojectionButton = new javax.swing.JButton();
                deleteAllInverseReprojectionsButton = new javax.swing.JButton();
                jLabel4 = new javax.swing.JLabel();
                exportGraticuleToolbar = new javax.swing.JToolBar();
                saveProjectionGraticuleButton = new javax.swing.JButton();

                setTitle("List of detected projections and their properties");
                setLocation(new java.awt.Point(50, 500));
                setType(java.awt.Window.Type.UTILITY);

                resultsTable.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
                resultsTable.setModel(new javax.swing.table.DefaultTableModel(
                        new Object [][] {
                                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null}
                        },
                        new String [] {
                                "Title 1", "Title 2", "Title 3", "Title 4", "Title 5", "Title 6", "Title 7", "Title 8", "Title 9", "Title 10", "Title 11", "Title 12", "Title 13", "Title 14", "Title 15", "Title 16", "Title 17", "Title 18", "Title 19"
                        }
                ) {
                        boolean[] canEdit = new boolean [] {
                                false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false
                        };

                        public boolean isCellEditable(int rowIndex, int columnIndex) {
                                return canEdit [columnIndex];
                        }
                });
                resultsTable.setToolTipText("List of determined projections sorted according to the residuals.. Click on the row to see the reconstructed meridians/parallels, points.");
                resultsTable.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
                jScrollPane1.setViewportView(resultsTable);

                jLabel1.setText("+ Map  scale refers to the determined  image resolution of:");

                jLabel3.setText("* Inverse formulas are supported");

                reprojectionToolbar.setRollover(true);
                reprojectionToolbar.setMaximumSize(new java.awt.Dimension(176, 35));
                reprojectionToolbar.setMinimumSize(new java.awt.Dimension(176, 35));
                reprojectionToolbar.setPreferredSize(new java.awt.Dimension(176, 35));

                inverseReprojectionButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/detectprojv2j/resources/reproject3.png"))); // NOI18N
                inverseReprojectionButton.setToolTipText("Reproject map by tiles to the Web Mercator.");
                inverseReprojectionButton.setFocusable(false);
                inverseReprojectionButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
                inverseReprojectionButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
                inverseReprojectionButton.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                inverseReprojectionButtonActionPerformed(evt);
                        }
                });
                reprojectionToolbar.add(inverseReprojectionButton);

                saveInverseReprojectionButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/detectprojv2j/resources/reproject_save3.png"))); // NOI18N
                saveInverseReprojectionButton.setToolTipText("Save reprojected and georeferenced raster as PNG file.");
                saveInverseReprojectionButton.setFocusable(false);
                saveInverseReprojectionButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
                saveInverseReprojectionButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
                saveInverseReprojectionButton.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                saveInverseReprojectionButtonActionPerformed(evt);
                        }
                });
                reprojectionToolbar.add(saveInverseReprojectionButton);

                jSeparator3.setDoubleBuffered(true);
                reprojectionToolbar.add(jSeparator3);

                deleteInverseReprojectionButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/detectprojv2j/resources/reproject_clear2.png"))); // NOI18N
                deleteInverseReprojectionButton.setToolTipText("Delete selected reprojected raster.");
                deleteInverseReprojectionButton.setFocusable(false);
                deleteInverseReprojectionButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
                deleteInverseReprojectionButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
                deleteInverseReprojectionButton.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                deleteInverseReprojectionButtonActionPerformed(evt);
                        }
                });
                reprojectionToolbar.add(deleteInverseReprojectionButton);

                deleteAllInverseReprojectionsButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/detectprojv2j/resources/reproject_clear_all2.png"))); // NOI18N
                deleteAllInverseReprojectionsButton.setToolTipText("Clear all reproject rasters.");
                deleteAllInverseReprojectionsButton.setFocusable(false);
                deleteAllInverseReprojectionsButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
                deleteAllInverseReprojectionsButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
                deleteAllInverseReprojectionsButton.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                deleteAllInverseReprojectionsButtonActionPerformed(evt);
                        }
                });
                reprojectionToolbar.add(deleteAllInverseReprojectionsButton);

                exportGraticuleToolbar.setRollover(true);
                exportGraticuleToolbar.setMaximumSize(new java.awt.Dimension(35, 35));
                exportGraticuleToolbar.setMinimumSize(new java.awt.Dimension(35, 35));
                exportGraticuleToolbar.setPreferredSize(new java.awt.Dimension(40, 35));

                saveProjectionGraticuleButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/detectprojv2j/resources/reproject_export2.png"))); // NOI18N
                saveProjectionGraticuleButton.setToolTipText("Save reprojected graticule in DXF file");
                saveProjectionGraticuleButton.setFocusable(false);
                saveProjectionGraticuleButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
                saveProjectionGraticuleButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
                saveProjectionGraticuleButton.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                saveProjectionGraticuleButtonActionPerformed(evt);
                        }
                });
                exportGraticuleToolbar.add(saveProjectionGraticuleButton);

                javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
                getContentPane().setLayout(layout);
                layout.setHorizontalGroup(
                        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1800, Short.MAX_VALUE)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(exportGraticuleToolbar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(reprojectionToolbar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                );
                layout.setVerticalGroup(
                        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(reprojectionToolbar, javax.swing.GroupLayout.PREFERRED_SIZE, 31, Short.MAX_VALUE)
                                        .addComponent(exportGraticuleToolbar, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 339, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                .addComponent(jLabel1)
                                                .addComponent(jLabel3)))
                                .addGap(6, 6, 6))
                );

                pack();
        }// </editor-fold>//GEN-END:initComponents

        private void inverseReprojectionButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_inverseReprojectionButtonActionPerformed
                //Perform inverse reprojection
                proj_index  = getSelectedProjIndex();
                
                //Perform reprojection to the Web Mercator
                if (proj_index >= 0)
                        performReprojection();
                
                //Update results
                printResults(20);
        }//GEN-LAST:event_inverseReprojectionButtonActionPerformed

        
        
        public void performReprojection()
        {
                //Perform reprojection to the Web Mercator projection                        
                TResult res = results.get(results.keySet().toArray()[proj_index]);

                //The previous analysis has been finished
                if (!computation_in_progress[0]) {
                        
                        //Create new service
                        ExecutorService es = Executors.newFixedThreadPool(5);
                        
                        //Disable computation
                        computation_in_progress[0] = true;
                      
                        //Reproject map in a selected projection to tiles
                        if (res.tiles == null || res.tiles.size() == 0) {
                                
                                //Web Mercator porperties
                                final double R = 6378137.0, latp = 90.0, lonp = 0.0, lat1 = 0.0, lon0 = 0.0, dX = 0.0, dY = 0.0, c = 1.0;
                                double[] sx = {0}, sy = {0}, ratio = {0}, lat_sw = {0}, lon_sw = {0}, lat_ne = {0}, lon_ne = {0};

                                //Disable computation
                                computation_in_progress[0] = true;

                                //Create output projection for OSM: Mercator
                                ProjectionCylindrical merc = new ProjectionCylindrical(R, latp, lonp, lat1, TTransformedLongitudeDirection.NormalDirection, lon0, dX, dY, c, Projections::F_merc, Projections::G_merc, Projections::FI_merc, Projections::GI_merc, "Mercator", "merc");

                                //Get maximum size of the raster
                                final int r_size_max = max(early_map.img.getWidth(), early_map.img.getHeight());

                                //Get optimal tile size : 0.1 * rsize                                       
                                final int min_tile = 500, max_tile = 1000;
                                final int tile_size = max(min(r_size_max / 10, max_tile), min_tile);

                                //Create new map object for georeference and reproject
                                MapReproject mw = new MapReproject(early_map.img, res.proj, merc, res.map_rotation, tile_size, label, map);

                                //Start reprojection      
                                Future f = es.submit(mw);

                                //After storing results add to the map
                                es.submit(()-> {
                                        
                                        try {
                                                //Enable computation
                                                computation_in_progress[0] = false;
                                                label.setText("");
                                        
                                                //Set result
                                                res.tiles = map.getReprojectedTiles();
                                                
                                                //Wait for finish
                                                f.get(); 
                                                
                                                //Print results in table
                                                printResults(20);
                                        }
                                        
                                        catch(Exception e)
                                        {
                                                e.printStackTrace();
                                        }

                                });
                        } 

                        //Load created tiles in a selected projection
                        else {
                                //Get reprojected tiles and set them as active
                                map.setReprojectedTiles(res.tiles);

                                //Repaint map
                                map.repaintMap();
                        } 
                        
                        es.shutdown();
                        
                        //Enable computation
                        computation_in_progress[0] = false;
                        
                }
        }
        
               
        private void saveProjectionGraticuleButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveProjectionGraticuleButtonActionPerformed
                //Export graticule to DXF file and save
                final double lat_step = 10, lon_step = 10;
                
                //System.out.println(proj_index);
                                
                //Projection was selected and computation finished
                if (proj_index >= 0 && (!computation_in_progress[0]) && results.size() > 0)
                {
                        //Disable computation
                        computation_in_progress[0] = true;
                        
                        //Get results
                        TResult res = results.get(results.keySet().toArray()[proj_index]);
                
                        //Get projected points, meridians/parallels, reconstructed points and reconstructed meridians/parallels of the projection assigned to the analyzed map
                        List <Point3DCartesian> reference_points_proj = res.points_proj;
                        List <Meridian> meridians = res.meridians;
                        List <Parallel> parallels = res.parallels;
                        Projection proj = res.proj;
                        List <List<Point3DCartesian> > meridians_proj = res.meridians_proj;
                        List <List<Point3DCartesian> > parallels_proj = res.parallels_proj;
 
                        //Merdians and parallels were created
                        if ((meridians != null) && (parallels != null) && (proj != null) && (meridians_proj != null) && (parallels_proj != null))
                        {
                                //Set properties of the  dialog
                                JFileChooser fc = new JFileChooser();
                                fc.setAcceptAllFileFilterUsed(false);
                                FileNameExtensionFilter filter = new FileNameExtensionFilter("CAD files", "dxf");
                                fc.setFileFilter(filter);

                                //Create suggested file name
                                String file_graticule_text = "grat_" + proj.getID() + ".dxf";

                                //Set dialog title and working directory
                                fc.setDialogTitle("Export graticule");
                                fc.setCurrentDirectory(new File(System.getProperty("user.home")));
                                fc.setSelectedFile(new File(file_graticule_text));

                                //Get the file
                                if (fc.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) 
                                {
                                        //Export to DXF + save
                                        try
                                        {
                                                //Get selected file
                                                file_graticule_text = fc.getSelectedFile().toString();

                                                final double font_height = 0.05 * proj.getR() * min(lat_step, lon_step) * PI / 180;
                                                DXFExport.exportGraticuleToDXF(file_graticule_text, meridians, meridians_proj, parallels, parallels_proj, early_map.test_points, reference_points_proj, font_height, min(lat_step, lon_step));
                                        }

                                        //Throw I/O exception
                                        catch (Exception exception) 
                                        {
                                                exception.printStackTrace();
                                        }
                                }
                        }
                        
                        //Enable computation
                        computation_in_progress[0] = false;
                }
        }//GEN-LAST:event_saveProjectionGraticuleButtonActionPerformed

        
        private void saveInverseReprojectionButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveInverseReprojectionButtonActionPerformed
                //Save result of the inverse reprojection as PNG file
                proj_index  = getSelectedProjIndex();
                
                //Projection was selected and comoutation finished
                if (proj_index >= 0 && (!computation_in_progress[0]) && results.size() >0)
                {
                        //Disable computation
                        computation_in_progress[0] = true;
                        
                        //Get results
                        TResult res = results.get(results.keySet().toArray()[proj_index]);
                        
                        //Some tiles exist
                        if (res.tiles.size() > 0)
                        {
                                //Construct file name 
                                final String mask = "png";
                                String file_name = "raster_georef_" + res.proj.getID();

                                //Set properties
                                JFileChooser fc = new JFileChooser();

                                //Set properties of the  dialog 
                                fc.setAcceptAllFileFilterUsed(false);
                                FileNameExtensionFilter filter = new FileNameExtensionFilter("Image files", mask);
                                fc.setFileFilter(filter);

                                //Set dialog title and working directory
                                fc.setDialogTitle("Export georeferenced raster file");
                                fc.setCurrentDirectory(new File(System.getProperty("user.home")));
                                fc.setSelectedFile(new File(file_name));

                                //Get the file
                                if (fc.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) 
                                {

                                        //Create new thread
                                        ExecutorService es = Executors.newFixedThreadPool(5);

                                        try
                                        {
                                               //Get selected file
                                               final String file_name_get = fc.getSelectedFile().getPath();

                                               //Export tiles to mosaic map
                                               MosaicMapExport export = new MosaicMapExport(map.getReprojectedTiles(), -1, MAX_MOSAIC_MAP_DIM); 

                                               //Store computed results
                                               Future <TMosaicMap> img_reproj_fut = es.submit(export);

                                               //After storing results add to the map
                                               es.submit(()-> {

                                                        //Create output structure
                                                        TMosaicMap img_reproj_mosaic = null;

                                                        //Assign results                               
                                                        try {
                                                                img_reproj_mosaic = img_reproj_fut.get();
                                                        } 

                                                        //Throw exception
                                                        catch (Exception e) {
                                                                e.printStackTrace();
                                                        }

                                                        //Write PNG file
                                                        final String mask2 = "png";
                                                        IO.saveImage(img_reproj_mosaic.map, file_name_get + "." + mask2, mask2);

                                                        //Write PGW file
                                                        MosaicMapExport.writePGW(img_reproj_mosaic.x_min, img_reproj_mosaic.y_max, img_reproj_mosaic.resolution, file_name_get);

                                                        //Write PRJ file
                                                        MosaicMapExport.writePRJ(file_name_get);
                                                });

                                                es.shutdown(); 

                                        }

                                        //Throw exception
                                        catch (Exception e)
                                        {
                                                e.printStackTrace();
                                        }
                                }
                        }
                        
                        //Enable computation
                        computation_in_progress[0] = false;
                }    
        }//GEN-LAST:event_saveInverseReprojectionButtonActionPerformed

             
        private void deleteInverseReprojectionButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteInverseReprojectionButtonActionPerformed
                //Delete reprojected raster tile
                proj_index  = getSelectedProjIndex();
                
                //Delete raster tiles of the selected projection
                if (results.size() > 0 && (!computation_in_progress[0])) {

                        //Disable computation
                        computation_in_progress[0] = true;

                        //Get index
                        proj_index = getSelectedProjIndex();

                        //Tiles are not empty
                        if (proj_index >= 0) {

                                //Get result
                                TResult res = results.get(results.keySet().toArray()[proj_index]);

                                //Some tiles exist
                                if ((res.tiles != null) && (res.tiles.size() > 0)) {

                                        //Show warning
                                        final Object[] options = {"Clear ", "Cancel"};
                                        final int response = JOptionPane.showOptionDialog(null, "The reprojected tiles will be deleted. Do you want to continue?", "Warning",
                                                JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[1]);

                                        //Delete reprojected tiles
                                        if (response == 0) {

                                                //Clear tiles
                                                res.tiles.clear();

                                                //Print results
                                                printResults(20);
                                        }

                                        //Repaint map
                                        map.repaint();
                                }
                        }

                        //Enable computation
                        computation_in_progress[0] = false;

                }
        }//GEN-LAST:event_deleteInverseReprojectionButtonActionPerformed

        
        private void deleteAllInverseReprojectionsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteAllInverseReprojectionsButtonActionPerformed
                //Clear all reprojected tiles
                if (results.size() > 0 && (!computation_in_progress[0])) {
                        
                        //Show warning
                        final Object[] options = {"Clear all ", "Cancel"};
                        final int response = JOptionPane.showOptionDialog(null, "All reprojected tiles in the list will be deleted. Do you want to continue?", "Warning",
                                JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[1]);

                        //Delete reprojected tiles
                        if (response == 0) {
                                
                                //Disable computation
                                computation_in_progress[0] = true;

                                //Process results one by one
                                for (Map.Entry<Double, TResult> m : results.entrySet()) {

                                        //Get result
                                        TResult res = m.getValue();

                                        //Tiles are not empty
                                        if ((res.tiles != null) && (res.tiles.size() > 0))
                                                
                                                //Clear tiles
                                                res.tiles.clear();
                                }

                                //Print results
                                printResults(20);
                                
                                //Repaint map
                                map.repaint();
                                
                                //Enable computation
                                computation_in_progress[0] = false;
                        }
                 }
        }//GEN-LAST:event_deleteAllInverseReprojectionsButtonActionPerformed

        
        private void initializeTable()
        {
                //Set properties of the table
                String[] col_names = { "#", "Family", "Projection*", "Reproj.", "Residuals", "R", "latk", "lonk", "lat1", "lat2", "lon0", "dX", "dY", "k", "Map scale+", "Map rotation", "q1", "q2", "Iterations"};

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
                
                //Set left alignment for columns 2,3
                resultsTable.getColumnModel().getColumn(1).setCellRenderer(r_cells_left);
                resultsTable.getColumnModel().getColumn(2).setCellRenderer(r_cells_left);
                
                //Get current column width
                final int col_width = resultsTable.getColumnModel().getColumn(1).getPreferredWidth();
                
                //Resize columns 1, 3, 4
                resultsTable.getColumnModel().getColumn(0).setPreferredWidth(col_width / 3);
                resultsTable.getColumnModel().getColumn(3).setPreferredWidth(col_width / 2);
                resultsTable.getColumnModel().getColumn(2).setPreferredWidth((int)(3.2 * col_width));
        }
        
        
        public void printResults(final int n_results)
        {
                //Print results to the table
                clearTable();
                
                //Set amount of results
                DefaultTableModel model = (DefaultTableModel)resultsTable.getModel();
                model.setRowCount(results.size());
                  
                //Compute pixel per mm
                final double pixels_mm = early_map.getDPI() / MM_PER_INCH * 1000;                                             //Earth radius and map scale refer to the image resolution of xxx DPI

                //Display DPI
                jLabel4.setText(Double.toString(early_map.getDPI()) + " DPI");
                
                //Print all candidates
                int index = 0;
                             
                for (final java.util.Map.Entry <Double, TResult> resuls_item : results.entrySet())
                {
                        //Get residuals and projection
                        double fx = resuls_item.getKey();
                        TResult result =  resuls_item.getValue();
                        Projection proj = result.proj;
                        
                        //Format numbers to the table
                        String reproj_string = (result.tiles == null || result.tiles.size() == 0 ? "-" : "x");
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
                        String map_scale_string = String.format(Locale.ROOT, "%15.1f", result.map_scale * pixels_mm);     
                        String map_rotation_string = String.format(Locale.ROOT, "%10.4f", result.map_rotation);   
                        String q1_string = String.format(Locale.ROOT, "%10.8f", result.q1);    
                        String q2_string = String.format(Locale.ROOT, "%10.8f", result.q2);  
                        String iterations_string = String.format(Locale.ROOT, "%10d", result.iterations);

                        //Print results
                        model.setValueAt(index + 1, index, 0);
                        model.setValueAt("  " + proj.getFamily(), index, 1);
                        model.setValueAt("  " + proj.getName(), index, 2);
                        model.setValueAt(reproj_string, index, 3);
                        model.setValueAt(fx_string, index, 4);
                        model.setValueAt(R_string, index, 5);
                        model.setValueAt(latp_string, index, 6);
                        model.setValueAt(lonp_string, index, 7);
                        model.setValueAt(lat1_string, index, 8);
                        model.setValueAt(lat2_string, index, 9);
                        model.setValueAt(lon0_string, index, 10);
                        model.setValueAt(dX_string, index, 11);
                        model.setValueAt(dY_string, index, 12);
                        model.setValueAt(c_string, index, 13);
                        model.setValueAt(map_scale_string, index, 14);
                        model.setValueAt(map_rotation_string, index, 15);
                        model.setValueAt(q1_string, index, 16);
                        model.setValueAt(q2_string, index, 17);
                        model.setValueAt(iterations_string, index, 18);
                        
                        index++;
                }

                //Sort table by column 3: square of residuals
                sortByColumn(4);               
        }
        
        
        public void clearTable()
        {
                //Clear content of the table
                DefaultTableModel model = (DefaultTableModel)resultsTable.getModel();
                final int n = model.getRowCount();
                
                //Change projection index to -1
                proj_index = -1;
                
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
                        model.setValueAt("", index, 18);
                }
        }
        
        
        private void sortByColumn(final int col_index_sort)
        {
                //Sort table according to the specific column
                TableRowSorter <TableModel> trs =(TableRowSorter) resultsTable.getRowSorter();
               
                //Setting propeties
                List<RowSorter.SortKey> data = new ArrayList<>();
                data.add(new RowSorter.SortKey(col_index_sort, SortOrder.ASCENDING));
                trs.setSortKeys(data);
                
                //Perform sorting
                trs.sort();   
        }
        
        
        private int getSelectedProjIndex()
        {
                //Index of the selected projection index 
                final int sel_index = resultsTable.getSelectedRow();
                                
                //Recomputed index according to the sorted elements
                return (sel_index > -1 ? resultsTable.convertRowIndexToModel( sel_index ) : -1);
        }

        // Variables declaration - do not modify//GEN-BEGIN:variables
        private javax.swing.JButton deleteAllInverseReprojectionsButton;
        private javax.swing.JButton deleteInverseReprojectionButton;
        private javax.swing.JToolBar exportGraticuleToolbar;
        private javax.swing.JButton inverseReprojectionButton;
        private javax.swing.JLabel jLabel1;
        private javax.swing.JLabel jLabel3;
        private javax.swing.JLabel jLabel4;
        private javax.swing.JScrollPane jScrollPane1;
        private javax.swing.JToolBar.Separator jSeparator3;
        private javax.swing.JToolBar reprojectionToolbar;
        private javax.swing.JTable resultsTable;
        private javax.swing.JButton saveInverseReprojectionButton;
        private javax.swing.JButton saveProjectionGraticuleButton;
        // End of variables declaration//GEN-END:variables
}
