// Description: Main window of the detectproj application
// Design in NetBeans

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
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.TreeMap;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.util.List;
import javax.swing.UIManager;
import javax.swing.JOptionPane;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.awt.Desktop;
import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;

import detectprojv2j.types.TAnalysisMethod;
import detectprojv2j.types.TInterval;
import detectprojv2j.types.TResult;
import detectprojv2j.types.IPoint3DFeatures;
import detectprojv2j.types.TTransformedLongitudeDirection;

import detectprojv2j.structures.point.Point3DCartesian;
import detectprojv2j.structures.point.Point3DGeographic;
import detectprojv2j.structures.projection.Projection;
import detectprojv2j.structures.projection.Projections;
import detectprojv2j.structures.graticule.Meridian;
import detectprojv2j.structures.graticule.Parallel;

import static detectprojv2j.consts.Consts.MAX_LON;
import static detectprojv2j.consts.Consts.MIN_LON;
import detectprojv2j.consts.Consts;
import static detectprojv2j.consts.Consts.MAX_LAT;
import static detectprojv2j.consts.Consts.MIN_LAT;

import detectprojv2j.comparators.SortByLat;
import detectprojv2j.comparators.SortByLon;

import detectprojv2j.algorithms.cartanalysis.CartAnalysisMT;
import detectprojv2j.algorithms.carttransformation.CartTransformation;
import detectprojv2j.algorithms.graticule2.Graticule2;

import detectprojv2j.io.DXFExport;
import detectprojv2j.io.IO;
import java.awt.event.MouseEvent;
import org.openstreetmap.gui.jmapviewer.DefaultMapController;


public class MainApplication extends javax.swing.JFrame  {

        private final EarlyMap early_map;                                                     //Early map prepresentation
        private final Map map;                                                                //Reference map prepresentation (OSM)
        private final List <Point3DCartesian> test_points;                                    //List of test points
        private final List <Point3DGeographic> reference_points;                              //List of reference points
        private final List <Projection> projections;                                          //List of the analyzed projections
        private BufferedImage img;                                                            //Uploaded early map image
        private int index_method;                                                             //Index of the method from combo box
        private int index_optimization;                                                       //Index of the optimization from combo box
        private final short n_results;                                                        //Displayed amount of candidate projections (results)
        
        private final TTransformedLongitudeDirection [] default_lon_dir;                      //Default transformed longitude direction mode (modes M1-M4)
        private final boolean [] analyze_lon0;                                                //Enable/disable lon0_trans analysis 
                
        private final boolean [] add_test_point;                                              //Control point may be added to the early map
        private final boolean [] add_reference_point;                                         //Control point may be added to the reference (OSM) map
        private final boolean [] enable_add_control_points;                                   //Enable add control points, if a pushbutton is selected
        private final boolean [] enable_panning_lm;                                           //Enable panning operation using the left mouse
        private final boolean [] enable_zoom_in_lm;                                           //Enable zoom-in operation using the left mouse
        private final boolean [] enable_zoom_out_lm;                                          //Enable zoom-out operation using the left mouse
        private final boolean [] enable_zoom_fit_all_lm;                                      //Enable zoom fit all operation using the left mouse
        private final boolean [] computation_in_progress;                                     //Test, whether a computation is in progress
                
        private final int [] index_nearest;                                                   //Index of the point nearest to the cursor position
        private final int [] index_nearest_prev;                                              //Index of the previous point nearest to the cursor position
        
        private final boolean [] create_entire_graticule;                                     //Graticule: Generate graticule over the entire analyzed territory
        private final double [] lat1_step;                                                    //Graticule: latitude step between parallels, territory extent > 20 deg
        private final double [] lat2_step;                                                    //Graticule: latitude step between parallels, territory extent < 20 deg
        private final double [] lat3_step;                                                    //Graticule: latitude step between parallels, territory extent < 2 deg
        private final double [] lon1_step;                                                    //Graticule: longitude step between parallels, territory extent > 20 deg
        private final double [] lon2_step;                                                    //Graticule: longitude step between parallels, territory extent < 20 deg
        private final double [] lon3_step;                                                    //Graticule: longitude step between parallels, territory extent < 2 deg
        private final double [] lat_incr;                                                     //Graticule: latitude sampling step (increment) for a parallel
        private final double [] lon_incr;                                                     //Graticule: longitude sampling step (increment) for a meridian
        
        private final TInterval lat_interval;                                                 //Geographic extent of the analyzed territory in the latitudinal direction
        private final TInterval lon_interval;                                                 //Geographic extent of the analyzed territory in the longitudinal direction
        
        private TAnalysisMethod method;                                                       //Method of the projection analysis
        private final TreeMap <Double, TResult> results;                                      //Dynmamic structure (tree map) storing the results [fx, x]
        
        private final ControlPointsForm control_points_form;                                  //Form displaying control points on the analyzed/reference maps
        private final ResultsForm results_form;                                               //Form displaying results, the determined projections
        private final AboutBox about_form;                                                    //Form, about box
        private final Settings settings_form;                                                 //Form, settings parameters of detectproj
        

        public MainApplication() {
                
                //Change look and feel
                try
                {
                        UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");   
                }
                
                //Catch exception
                catch (Exception e)
                {
                        e.printStackTrace();                    
                }
                
                //Init components
                initComponents();
               
                //Maximize window
                splitPanels.setResizeWeight(.5d);
                setExtendedState(java.awt.Frame.MAXIMIZED_BOTH);
                
                //Create lists
                test_points = new ArrayList<>();
                reference_points = new ArrayList<>();
                projections = new ArrayList<>();
                
                //Parameters of the detection
                default_lon_dir = new TTransformedLongitudeDirection [] {TTransformedLongitudeDirection.NormalDirection}; 
                analyze_lon0 = new boolean[]{false};
 
                //Boolean indicators
                add_test_point = new boolean[]{true};  
                add_reference_point = new boolean[]{true};
                enable_add_control_points = new boolean[]{false};
                enable_panning_lm = new boolean[]{false};
                enable_zoom_in_lm = new boolean[]{false};
                enable_zoom_out_lm = new boolean[]{false};
                enable_zoom_fit_all_lm = new boolean[]{false}; 
                computation_in_progress = new boolean[]{false};

                //Parameters of the graticule
                create_entire_graticule = new boolean[]{false};
                lat1_step = new double [] {10.0};
                lat2_step = new double [] {1.0};
                lat3_step = new double [] {0.1};
                lon1_step = new double [] {10.0};
                lon2_step = new double [] {1.0};
                lon3_step = new double [] {0.1};
                lat_incr = new double [] {0.1 * lat1_step[0]};
                lon_incr = new double [] {0.1 * lon1_step[0]};

                //Set index of the nearest and previous point
                index_nearest = new int[]{-1};
                index_nearest_prev = new int[]{-1}; 
                
                //Create intervals representing the latitude/longitude extent
                lat_interval = new TInterval(MIN_LAT, MAX_LAT);
                lon_interval = new TInterval(MIN_LON, MAX_LON);
                
                //Create early and OSM maps
                early_map = new EarlyMap(test_points, null, null, add_test_point, add_reference_point, enable_add_control_points, enable_panning_lm,
                        enable_zoom_in_lm, enable_zoom_out_lm, enable_zoom_fit_all_lm, computation_in_progress, index_nearest, index_nearest_prev);
                map = new Map(reference_points, early_map, null, add_test_point, add_reference_point, enable_add_control_points, enable_panning_lm,
                        enable_zoom_in_lm, enable_zoom_out_lm, enable_zoom_fit_all_lm, computation_in_progress, index_nearest, index_nearest_prev);
                
                early_map.setMap(map);
    
                //Add maps to the jPanels
                earlyMapPanel.add(early_map);
                osmMapPanel.add(map);
                
                //Set default method (M7, Nelder-Mead)
                index_method = 1;
                index_optimization = 20;
                
                //Amount of printed results
                n_results = 20;              

                //Initialize ComboBox items
                selectDetectionMethodComboBox.setSelectedIndex(index_method - 1);
                selectOptimizationTechniqueComboBox.setSelectedIndex(index_optimization / 10 - 1);
                method = TAnalysisMethod.NMM8;
                
                //List of results
                results = new TreeMap<> ();
                
                //Create forms with points, results and about box
                control_points_form = new ControlPointsForm(early_map, map, add_test_point, add_reference_point, computation_in_progress, index_nearest, index_nearest_prev);
                results_form = new ResultsForm(early_map, results, n_results, this);
                about_form = new AboutBox();
                settings_form = new Settings(reference_points, default_lon_dir, analyze_lon0, create_entire_graticule, lat1_step, lat2_step, lat3_step, lon1_step, lon2_step, lon3_step, lat_incr, lon_incr, lat_interval, lon_interval, projections);
                
                //Set form properties
                early_map.setControlPointsForm(control_points_form);
                map.setControlPointsForm(control_points_form);
                
                //Enable drag and drop operation for both maps
                DropTarget target_early_map = new DropTarget(early_map, new DragAndDrop()); 
                DropTarget target_map = new DropTarget(map, new DragAndDrop()); 
        }

        
        //Inner class containing drag and drop support
        private class DragAndDrop implements DropTargetListener 
        {
                @Override
                public void dragEnter(DropTargetDragEvent dtde) {}

                @Override
                public void dragOver(DropTargetDragEvent dtde) {}

                @Override
                public void dropActionChanged(DropTargetDragEvent dtde) {}

                @Override
                public void dragExit(DropTargetEvent dte) {}

                @Override
                public void drop(DropTargetDropEvent dtde) 
                {
                        try 
                        {
                                //Accept the drop operation
                                dtde.acceptDrop(DnDConstants.ACTION_COPY_OR_MOVE);

                                //Get the list of dropped files
                                final List <File> dragged_files = (List) dtde.getTransferable().getTransferData(DataFlavor.javaFileListFlavor);

                                //Use only the first file
                                final File dragged_file = dragged_files.get(0);
                                final String dragged_file_name = dragged_file.toString().toLowerCase();
                                
                                //Get drag target 
                                final DropTarget target = (DropTarget)dtde.getSource();
                                
                                //Drag target is the early map ?
                                if (target.getComponent() == early_map)
                                {
                                        //Graphic file (JPG, PNG, TIFF), load the map
                                        if (dragged_file_name.endsWith(".jpg") || dragged_file_name.endsWith(".gif") || dragged_file_name.endsWith(".png"))
                                        {
                                                importEarlyMap (dragged_file);
                                        }
                                        
                                        //Text file (TXT), load the points                                        //Graphic file (JPG, PNG, TIFF), load the map
                                        else if (dragged_file_name.endsWith(".txt"))
                                        {
                                                //Enable/disable buttons
                                                zoomGroup.clearSelection();
                                                addControlPointsToggleButton.setSelected(false);
                                                                                                
                                                //Load test points
                                                importPoints(test_points, dragged_file, Point3DCartesian.class);
                                                
                                                //Update early map
                                                early_map.repaint();
                                                
                                                //Update table with control points
                                                control_points_form.clearTable();
                                                control_points_form.printResult();
                                        }
                                }
                                
                                //Drag target is the Open-Street-Map ?
                                else if (target.getComponent() == map)
                                {
                                        //Text file (TXT), load the points                                        //Graphic file (JPG, PNG, TIFF), load the map
                                        if (dragged_file_name.endsWith(".txt"))
                                        {
                                                //Enable/disable buttons
                                                zoomGroup.clearSelection();
                                                addControlPointsToggleButton.setSelected(false);
                                                
                                                 //Load reference points
                                                importPoints(reference_points, dragged_file, Point3DGeographic.class);
                                                
                                                //Update OSM map
                                                map.repaint();
                                                
                                                //Update table with control points
                                                control_points_form.clearTable();
                                                control_points_form.printResult();
                                        }
                                }
                        } 

                        //Throw exception
                        catch (Exception e) 
                        {
                                e.printStackTrace();
                        }
                }
        }
        
        @SuppressWarnings("unchecked")
        // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
        private void initComponents() {

                fileGroup = new javax.swing.ButtonGroup();
                zoomGroup = new javax.swing.ButtonGroup();
                resultsGroup = new javax.swing.ButtonGroup();
                detectionMethodGroup = new javax.swing.ButtonGroup();
                optimizationTechniqueGroup = new javax.swing.ButtonGroup();
                mainMenuPanel = new javax.swing.JPanel();
                selectOptimizationPanel = new javax.swing.JPanel();
                selectDetectionMethodComboBox = new javax.swing.JComboBox<>();
                selectOptimizationTechniqueComboBox = new javax.swing.JComboBox<>();
                fileToolBar = new javax.swing.JToolBar();
                importMapButton = new javax.swing.JButton();
                exportGraticuleButton = new javax.swing.JButton();
                zoomToolBar = new javax.swing.JToolBar();
                panningToggleButton = new javax.swing.JToggleButton();
                zoomInToggleButton = new javax.swing.JToggleButton();
                zoomOutToggleButton = new javax.swing.JToggleButton();
                viewAllToggleButton = new javax.swing.JToggleButton();
                controlPointsToolBar = new javax.swing.JToolBar();
                addControlPointsToggleButton = new javax.swing.JToggleButton();
                jSeparator2 = new javax.swing.JToolBar.Separator();
                importTestPointsButton = new javax.swing.JButton();
                jSeparator11 = new javax.swing.JToolBar.Separator();
                importReferencePointsButton = new javax.swing.JButton();
                jSeparator8 = new javax.swing.JToolBar.Separator();
                showControlPointsButton = new javax.swing.JButton();
                jSeparator14 = new javax.swing.JToolBar.Separator();
                exportTestPointsButton = new javax.swing.JButton();
                jSeparator12 = new javax.swing.JToolBar.Separator();
                exportReferencePointsButton = new javax.swing.JButton();
                analyzeToolBar = new javax.swing.JToolBar();
                showResultsButton = new javax.swing.JButton();
                clearResultsButton = new javax.swing.JButton();
                jSeparator9 = new javax.swing.JToolBar.Separator();
                clearAllButton = new javax.swing.JButton();
                jSeparator6 = new javax.swing.JToolBar.Separator();
                settingsButton = new javax.swing.JButton();
                jSeparator16 = new javax.swing.JToolBar.Separator();
                analyzeButton = new javax.swing.JToggleButton();
                splitPanels = new javax.swing.JSplitPane();
                earlyMapPanel = new javax.swing.JPanel();
                osmMapPanel = new javax.swing.JPanel();
                menuBar = new javax.swing.JMenuBar();
                mapMenu = new javax.swing.JMenu();
                openMenuItem = new javax.swing.JMenuItem();
                saveMenuItem = new javax.swing.JMenuItem();
                jSeparator4 = new javax.swing.JPopupMenu.Separator();
                exitMenuItem = new javax.swing.JMenuItem();
                viewMenu = new javax.swing.JMenu();
                panningMenuItem = new javax.swing.JMenuItem();
                zoomInMenuItem = new javax.swing.JMenuItem();
                zoomOutMenuItem = new javax.swing.JMenuItem();
                viewAllMenuItem = new javax.swing.JMenuItem();
                pointsMenu = new javax.swing.JMenu();
                addControPointsMenuItem = new javax.swing.JMenuItem();
                jSeparator7 = new javax.swing.JPopupMenu.Separator();
                importTestPointsMenuItem = new javax.swing.JMenuItem();
                importReferencePointsMenuItem = new javax.swing.JMenuItem();
                jSeparator1 = new javax.swing.JPopupMenu.Separator();
                jMenuItem2 = new javax.swing.JMenuItem();
                jSeparator13 = new javax.swing.JPopupMenu.Separator();
                exportTestPointsMenuItem = new javax.swing.JMenuItem();
                exportReferencePointsMenuItem = new javax.swing.JMenuItem();
                analysisMenu = new javax.swing.JMenu();
                detectionMethodMenu = new javax.swing.JMenu();
                m7CheckBoxMenuItem = new javax.swing.JCheckBoxMenuItem();
                m8CheckBoxMenuItem = new javax.swing.JCheckBoxMenuItem();
                optimizationTechniqueMenu = new javax.swing.JMenu();
                nonLinearLeastSquaresCheckBoxMenuItem = new javax.swing.JCheckBoxMenuItem();
                nelderMeadCheckBoxMenuItem = new javax.swing.JCheckBoxMenuItem();
                differentialEvolutionCheckBoxMenuItem = new javax.swing.JCheckBoxMenuItem();
                jSeparator5 = new javax.swing.JPopupMenu.Separator();
                analyzeMapMenuItem = new javax.swing.JMenuItem();
                jSeparator3 = new javax.swing.JPopupMenu.Separator();
                showResultsMenuItem = new javax.swing.JMenuItem();
                clearResultsMenuItem = new javax.swing.JMenuItem();
                jSeparator10 = new javax.swing.JPopupMenu.Separator();
                clearAllMenuItem = new javax.swing.JMenuItem();
                jSeparator15 = new javax.swing.JPopupMenu.Separator();
                settingsMenuItem = new javax.swing.JMenuItem();
                helpMenu = new javax.swing.JMenu();
                jMenuItem1 = new javax.swing.JMenuItem();
                aboutMenuItem = new javax.swing.JMenuItem();

                setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
                setTitle("Map projection analysis: detectproj");
                setSize(new java.awt.Dimension(20, 24));
                addWindowListener(new java.awt.event.WindowAdapter() {
                        public void windowClosing(java.awt.event.WindowEvent evt) {
                                formWindowClosing(evt);
                        }
                        public void windowOpened(java.awt.event.WindowEvent evt) {
                                formWindowOpened(evt);
                        }
                });

                mainMenuPanel.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

                selectOptimizationPanel.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 10, 5));

                selectDetectionMethodComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Method M7", "Method M8" }));
                selectDetectionMethodComboBox.setToolTipText("Select detection method");
                selectDetectionMethodComboBox.addItemListener(new java.awt.event.ItemListener() {
                        public void itemStateChanged(java.awt.event.ItemEvent evt) {
                                selectDetectionMethodComboBoxItemStateChanged(evt);
                        }
                });
                selectOptimizationPanel.add(selectDetectionMethodComboBox);

                selectOptimizationTechniqueComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Non-linear least squares", "Nelder-Mead", "Differential evolution" }));
                selectOptimizationTechniqueComboBox.setSelectedIndex(1);
                selectOptimizationTechniqueComboBox.setToolTipText("Select optimization technique");
                selectOptimizationTechniqueComboBox.addItemListener(new java.awt.event.ItemListener() {
                        public void itemStateChanged(java.awt.event.ItemEvent evt) {
                                selectOptimizationTechniqueComboBoxItemStateChanged(evt);
                        }
                });
                selectOptimizationPanel.add(selectOptimizationTechniqueComboBox);

                mainMenuPanel.add(selectOptimizationPanel);

                fileToolBar.setRollover(true);
                fileToolBar.setToolTipText("");
                fileToolBar.setMaximumSize(new java.awt.Dimension(60, 30));
                fileToolBar.setMinimumSize(new java.awt.Dimension(60, 30));
                fileToolBar.setPreferredSize(new java.awt.Dimension(60, 30));
                fileToolBar.setRequestFocusEnabled(false);

                importMapButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/detectprojv2j/resources/open.png"))); // NOI18N
                importMapButton.setToolTipText("Import early map from image.");
                importMapButton.setBorderPainted(false);
                importMapButton.setFocusable(false);
                importMapButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
                importMapButton.setMaximumSize(new java.awt.Dimension(24, 24));
                importMapButton.setMinimumSize(new java.awt.Dimension(24, 24));
                importMapButton.setPreferredSize(new java.awt.Dimension(24, 24));
                importMapButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
                importMapButton.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                importMapButtonActionPerformed(evt);
                        }
                });
                fileToolBar.add(importMapButton);

                exportGraticuleButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/detectprojv2j/resources/save.png"))); // NOI18N
                exportGraticuleButton.setToolTipText("Export reconstructed graticule to DXF file.");
                exportGraticuleButton.setFocusable(false);
                exportGraticuleButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
                exportGraticuleButton.setMaximumSize(new java.awt.Dimension(24, 24));
                exportGraticuleButton.setMinimumSize(new java.awt.Dimension(24, 24));
                exportGraticuleButton.setPreferredSize(new java.awt.Dimension(24, 24));
                exportGraticuleButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
                exportGraticuleButton.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                exportGraticuleButtonActionPerformed(evt);
                        }
                });
                fileToolBar.add(exportGraticuleButton);

                mainMenuPanel.add(fileToolBar);

                zoomToolBar.setRollover(true);
                zoomToolBar.setMaximumSize(new java.awt.Dimension(90, 30));
                zoomToolBar.setMinimumSize(new java.awt.Dimension(90, 30));
                zoomToolBar.setPreferredSize(new java.awt.Dimension(110, 30));

                zoomGroup.add(panningToggleButton);
                panningToggleButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/detectprojv2j/resources/panning_icon.png"))); // NOI18N
                panningToggleButton.setToolTipText("Panning.");
                panningToggleButton.setFocusable(false);
                panningToggleButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
                panningToggleButton.setMaximumSize(new java.awt.Dimension(24, 24));
                panningToggleButton.setMinimumSize(new java.awt.Dimension(24, 24));
                panningToggleButton.setPreferredSize(new java.awt.Dimension(24, 24));
                panningToggleButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
                panningToggleButton.addChangeListener(new javax.swing.event.ChangeListener() {
                        public void stateChanged(javax.swing.event.ChangeEvent evt) {
                                panningToggleButtonStateChanged(evt);
                        }
                });
                zoomToolBar.add(panningToggleButton);

                zoomGroup.add(zoomInToggleButton);
                zoomInToggleButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/detectprojv2j/resources/zoom_in.png"))); // NOI18N
                zoomInToggleButton.setToolTipText("Zoom in.");
                zoomInToggleButton.setDoubleBuffered(true);
                zoomInToggleButton.setFocusable(false);
                zoomInToggleButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
                zoomInToggleButton.setMaximumSize(new java.awt.Dimension(24, 24));
                zoomInToggleButton.setMinimumSize(new java.awt.Dimension(24, 24));
                zoomInToggleButton.setPreferredSize(new java.awt.Dimension(24, 24));
                zoomInToggleButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
                zoomInToggleButton.addChangeListener(new javax.swing.event.ChangeListener() {
                        public void stateChanged(javax.swing.event.ChangeEvent evt) {
                                zoomInToggleButtonStateChanged(evt);
                        }
                });
                zoomToolBar.add(zoomInToggleButton);

                zoomGroup.add(zoomOutToggleButton);
                zoomOutToggleButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/detectprojv2j/resources/zoom_out.png"))); // NOI18N
                zoomOutToggleButton.setToolTipText("Zoom out.");
                zoomOutToggleButton.setFocusable(false);
                zoomOutToggleButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
                zoomOutToggleButton.setMaximumSize(new java.awt.Dimension(24, 24));
                zoomOutToggleButton.setMinimumSize(new java.awt.Dimension(24, 24));
                zoomOutToggleButton.setPreferredSize(new java.awt.Dimension(24, 24));
                zoomOutToggleButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
                zoomOutToggleButton.addChangeListener(new javax.swing.event.ChangeListener() {
                        public void stateChanged(javax.swing.event.ChangeEvent evt) {
                                zoomOutToggleButtonStateChanged(evt);
                        }
                });
                zoomToolBar.add(zoomOutToggleButton);

                zoomGroup.add(viewAllToggleButton);
                viewAllToggleButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/detectprojv2j/resources/fit_all.png"))); // NOI18N
                viewAllToggleButton.setToolTipText("View all.");
                viewAllToggleButton.setFocusable(false);
                viewAllToggleButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
                viewAllToggleButton.setMaximumSize(new java.awt.Dimension(24, 24));
                viewAllToggleButton.setMinimumSize(new java.awt.Dimension(24, 24));
                viewAllToggleButton.setPreferredSize(new java.awt.Dimension(24, 24));
                viewAllToggleButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
                viewAllToggleButton.addChangeListener(new javax.swing.event.ChangeListener() {
                        public void stateChanged(javax.swing.event.ChangeEvent evt) {
                                viewAllToggleButtonStateChanged(evt);
                        }
                });
                zoomToolBar.add(viewAllToggleButton);

                mainMenuPanel.add(zoomToolBar);

                controlPointsToolBar.setRollover(true);
                controlPointsToolBar.setMaximumSize(new java.awt.Dimension(190, 28));
                controlPointsToolBar.setMinimumSize(new java.awt.Dimension(190, 28));
                controlPointsToolBar.setPreferredSize(new java.awt.Dimension(190, 28));

                addControlPointsToggleButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/detectprojv2j/resources/points.png"))); // NOI18N
                addControlPointsToggleButton.setToolTipText("Enable/disable adding of control points");
                addControlPointsToggleButton.setFocusable(false);
                addControlPointsToggleButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
                addControlPointsToggleButton.setMaximumSize(new java.awt.Dimension(24, 24));
                addControlPointsToggleButton.setMinimumSize(new java.awt.Dimension(24, 24));
                addControlPointsToggleButton.setPreferredSize(new java.awt.Dimension(24, 24));
                addControlPointsToggleButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
                addControlPointsToggleButton.addChangeListener(new javax.swing.event.ChangeListener() {
                        public void stateChanged(javax.swing.event.ChangeEvent evt) {
                                addControlPointsToggleButtonStateChanged(evt);
                        }
                });
                controlPointsToolBar.add(addControlPointsToggleButton);

                jSeparator2.setInheritsPopupMenu(true);
                jSeparator2.setMaximumSize(new java.awt.Dimension(8, 32767));
                jSeparator2.setMinimumSize(new java.awt.Dimension(8, 0));
                jSeparator2.setPreferredSize(new java.awt.Dimension(8, 0));
                controlPointsToolBar.add(jSeparator2);

                importTestPointsButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/detectprojv2j/resources/input_test.png"))); // NOI18N
                importTestPointsButton.setToolTipText("Import control points to the analyzed map.");
                importTestPointsButton.setFocusable(false);
                importTestPointsButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
                importTestPointsButton.setMaximumSize(new java.awt.Dimension(24, 24));
                importTestPointsButton.setMinimumSize(new java.awt.Dimension(26, 26));
                importTestPointsButton.setPreferredSize(new java.awt.Dimension(26, 26));
                importTestPointsButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
                importTestPointsButton.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                importTestPointsButtonActionPerformed(evt);
                        }
                });
                controlPointsToolBar.add(importTestPointsButton);

                jSeparator11.setMaximumSize(new java.awt.Dimension(5, 0));
                jSeparator11.setMinimumSize(new java.awt.Dimension(5, 0));
                jSeparator11.setName(""); // NOI18N
                jSeparator11.setPreferredSize(new java.awt.Dimension(5, 0));
                controlPointsToolBar.add(jSeparator11);

                importReferencePointsButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/detectprojv2j/resources/input_reference.png"))); // NOI18N
                importReferencePointsButton.setToolTipText("Import control points to the reference map.");
                importReferencePointsButton.setFocusable(false);
                importReferencePointsButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
                importReferencePointsButton.setMaximumSize(new java.awt.Dimension(24, 24));
                importReferencePointsButton.setMinimumSize(new java.awt.Dimension(24, 24));
                importReferencePointsButton.setPreferredSize(new java.awt.Dimension(26, 26));
                importReferencePointsButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
                importReferencePointsButton.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                importReferencePointsButtonActionPerformed(evt);
                        }
                });
                controlPointsToolBar.add(importReferencePointsButton);

                jSeparator8.setMaximumSize(new java.awt.Dimension(8, 32767));
                jSeparator8.setMinimumSize(new java.awt.Dimension(8, 0));
                jSeparator8.setPreferredSize(new java.awt.Dimension(8, 0));
                controlPointsToolBar.add(jSeparator8);

                showControlPointsButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/detectprojv2j/resources/show_control_points.png"))); // NOI18N
                showControlPointsButton.setToolTipText("Show list of control points on analyzed/reference maps.");
                showControlPointsButton.setFocusable(false);
                showControlPointsButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
                showControlPointsButton.setMaximumSize(new java.awt.Dimension(26, 26));
                showControlPointsButton.setMinimumSize(new java.awt.Dimension(26, 26));
                showControlPointsButton.setPreferredSize(new java.awt.Dimension(26, 26));
                showControlPointsButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
                showControlPointsButton.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                showControlPointsButtonActionPerformed(evt);
                        }
                });
                controlPointsToolBar.add(showControlPointsButton);

                jSeparator14.setMinimumSize(new java.awt.Dimension(8, 0));
                jSeparator14.setPreferredSize(new java.awt.Dimension(8, 0));
                controlPointsToolBar.add(jSeparator14);

                exportTestPointsButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/detectprojv2j/resources/output_test.png"))); // NOI18N
                exportTestPointsButton.setToolTipText("Export control points from the analyzed map.");
                exportTestPointsButton.setFocusable(false);
                exportTestPointsButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
                exportTestPointsButton.setMaximumSize(new java.awt.Dimension(24, 24));
                exportTestPointsButton.setMinimumSize(new java.awt.Dimension(24, 24));
                exportTestPointsButton.setPreferredSize(new java.awt.Dimension(26, 26));
                exportTestPointsButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
                exportTestPointsButton.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                exportTestPointsButtonActionPerformed(evt);
                        }
                });
                controlPointsToolBar.add(exportTestPointsButton);

                jSeparator12.setMaximumSize(new java.awt.Dimension(5, 0));
                jSeparator12.setMinimumSize(new java.awt.Dimension(5, 0));
                jSeparator12.setPreferredSize(new java.awt.Dimension(5, 0));
                controlPointsToolBar.add(jSeparator12);

                exportReferencePointsButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/detectprojv2j/resources/output_reference.png"))); // NOI18N
                exportReferencePointsButton.setToolTipText("Export control points from the reference map.");
                exportReferencePointsButton.setFocusable(false);
                exportReferencePointsButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
                exportReferencePointsButton.setMaximumSize(new java.awt.Dimension(24, 24));
                exportReferencePointsButton.setMinimumSize(new java.awt.Dimension(24, 24));
                exportReferencePointsButton.setPreferredSize(new java.awt.Dimension(26, 24));
                exportReferencePointsButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
                exportReferencePointsButton.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                exportReferencePointsButtonActionPerformed(evt);
                        }
                });
                controlPointsToolBar.add(exportReferencePointsButton);

                mainMenuPanel.add(controlPointsToolBar);

                analyzeToolBar.setRollover(true);

                showResultsButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/detectprojv2j/resources/table.png"))); // NOI18N
                showResultsButton.setToolTipText("Show results: list of the detected projections.");
                showResultsButton.setFocusable(false);
                showResultsButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
                showResultsButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
                showResultsButton.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                showResultsButtonActionPerformed(evt);
                        }
                });
                analyzeToolBar.add(showResultsButton);

                clearResultsButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/detectprojv2j/resources/table_clear.png"))); // NOI18N
                clearResultsButton.setToolTipText("Clear results: list of detected projections.");
                clearResultsButton.setFocusable(false);
                clearResultsButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
                clearResultsButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
                clearResultsButton.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                clearResultsButtonActionPerformed(evt);
                        }
                });
                analyzeToolBar.add(clearResultsButton);

                jSeparator9.setSeparatorSize(new java.awt.Dimension(20, 24));
                analyzeToolBar.add(jSeparator9);

                clearAllButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/detectprojv2j/resources/clear_all.png"))); // NOI18N
                clearAllButton.setToolTipText("Clear all points, results and reconstructed graticules.");
                clearAllButton.setFocusable(false);
                clearAllButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
                clearAllButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
                clearAllButton.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                clearAllButtonActionPerformed(evt);
                        }
                });
                analyzeToolBar.add(clearAllButton);

                jSeparator6.setSeparatorSize(new java.awt.Dimension(20, 24));
                analyzeToolBar.add(jSeparator6);

                settingsButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/detectprojv2j/resources/settings.png"))); // NOI18N
                settingsButton.setToolTipText("Setting parameters of detectproj.");
                settingsButton.setFocusable(false);
                settingsButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
                settingsButton.setMaximumSize(new java.awt.Dimension(24, 24));
                settingsButton.setMinimumSize(new java.awt.Dimension(24, 24));
                settingsButton.setPreferredSize(new java.awt.Dimension(24, 24));
                settingsButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
                settingsButton.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                settingsButtonActionPerformed(evt);
                        }
                });
                analyzeToolBar.add(settingsButton);

                jSeparator16.setMaximumSize(new java.awt.Dimension(20, 24));
                jSeparator16.setMinimumSize(new java.awt.Dimension(20, 24));
                jSeparator16.setPreferredSize(new java.awt.Dimension(20, 24));
                jSeparator16.setRequestFocusEnabled(false);
                analyzeToolBar.add(jSeparator16);

                analyzeButton.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
                analyzeButton.setText("Analyze map");
                analyzeButton.setToolTipText("Start analysis.");
                analyzeButton.setBorder(javax.swing.BorderFactory.createEtchedBorder());
                analyzeButton.setFocusable(false);
                analyzeButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
                analyzeButton.setMaximumSize(new java.awt.Dimension(140, 26));
                analyzeButton.setMinimumSize(new java.awt.Dimension(140, 26));
                analyzeButton.setPreferredSize(new java.awt.Dimension(140, 26));
                analyzeButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
                analyzeButton.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                analyzeButtonActionPerformed(evt);
                        }
                });
                analyzeToolBar.add(analyzeButton);

                mainMenuPanel.add(analyzeToolBar);

                splitPanels.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
                splitPanels.setDividerLocation(940);
                splitPanels.setResizeWeight(0.5);

                earlyMapPanel.setBackground(new java.awt.Color(255, 255, 255));
                earlyMapPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());
                earlyMapPanel.setToolTipText("Drag the analyzed map and control points here.");
                earlyMapPanel.setLayout(new java.awt.BorderLayout());
                splitPanels.setLeftComponent(earlyMapPanel);

                osmMapPanel.setBackground(new java.awt.Color(255, 255, 255));
                osmMapPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());
                osmMapPanel.setToolTipText("Drag the control points here.");
                osmMapPanel.setLayout(new java.awt.BorderLayout());
                splitPanels.setRightComponent(osmMapPanel);

                mapMenu.setMnemonic('m');
                mapMenu.setText("Map");

                openMenuItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/detectprojv2j/resources/open.png"))); // NOI18N
                openMenuItem.setMnemonic('i');
                openMenuItem.setText("Import map...");
                openMenuItem.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                importMapButtonActionPerformed(evt);
                        }
                });
                mapMenu.add(openMenuItem);

                saveMenuItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/detectprojv2j/resources/save.png"))); // NOI18N
                saveMenuItem.setMnemonic('e');
                saveMenuItem.setText("Export graticule...");
                saveMenuItem.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                exportGraticuleButtonActionPerformed(evt);
                        }
                });
                mapMenu.add(saveMenuItem);
                mapMenu.add(jSeparator4);

                exitMenuItem.setMnemonic('x');
                exitMenuItem.setText("Exit");
                exitMenuItem.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                exitMenuItemActionPerformed(evt);
                        }
                });
                mapMenu.add(exitMenuItem);

                menuBar.add(mapMenu);

                viewMenu.setText("View");

                panningMenuItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/detectprojv2j/resources/panning_icon.png"))); // NOI18N
                panningMenuItem.setText("Panning");
                panningMenuItem.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                panningMenuItemActionPerformed(evt);
                        }
                });
                viewMenu.add(panningMenuItem);

                zoomInMenuItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/detectprojv2j/resources/zoom_in.png"))); // NOI18N
                zoomInMenuItem.setText("Zoom In");
                zoomInMenuItem.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                zoomInMenuItemActionPerformed(evt);
                        }
                });
                viewMenu.add(zoomInMenuItem);

                zoomOutMenuItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/detectprojv2j/resources/zoom_out.png"))); // NOI18N
                zoomOutMenuItem.setText("Zoom out");
                zoomOutMenuItem.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                zoomOutMenuItemActionPerformed(evt);
                        }
                });
                viewMenu.add(zoomOutMenuItem);

                viewAllMenuItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/detectprojv2j/resources/fit_all.png"))); // NOI18N
                viewAllMenuItem.setText("View all");
                viewAllMenuItem.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                viewAllMenuItemActionPerformed(evt);
                        }
                });
                viewMenu.add(viewAllMenuItem);

                menuBar.add(viewMenu);

                pointsMenu.setMnemonic('p');
                pointsMenu.setText("Points");

                addControPointsMenuItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/detectprojv2j/resources/points.png"))); // NOI18N
                addControPointsMenuItem.setText("Add control points");
                addControPointsMenuItem.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                addControlPointButtonActionPerformed(evt);
                        }
                });
                pointsMenu.add(addControPointsMenuItem);
                pointsMenu.add(jSeparator7);

                importTestPointsMenuItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/detectprojv2j/resources/input_test.png"))); // NOI18N
                importTestPointsMenuItem.setMnemonic('t');
                importTestPointsMenuItem.setText("Import test points...");
                importTestPointsMenuItem.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                importTestPointsButtonActionPerformed(evt);
                        }
                });
                pointsMenu.add(importTestPointsMenuItem);

                importReferencePointsMenuItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/detectprojv2j/resources/input_reference.png"))); // NOI18N
                importReferencePointsMenuItem.setMnemonic('y');
                importReferencePointsMenuItem.setText("Import reference points...");
                importReferencePointsMenuItem.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                importReferencePointsButtonActionPerformed(evt);
                        }
                });
                pointsMenu.add(importReferencePointsMenuItem);
                pointsMenu.add(jSeparator1);

                jMenuItem2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/detectprojv2j/resources/show_control_points.png"))); // NOI18N
                jMenuItem2.setText("Show control points");
                jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                showControlPointsButtonActionPerformed(evt);
                        }
                });
                pointsMenu.add(jMenuItem2);
                pointsMenu.add(jSeparator13);

                exportTestPointsMenuItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/detectprojv2j/resources/output_test.png"))); // NOI18N
                exportTestPointsMenuItem.setMnemonic('p');
                exportTestPointsMenuItem.setText("Export test points...");
                exportTestPointsMenuItem.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                exportTestPointsButtonActionPerformed(evt);
                        }
                });
                pointsMenu.add(exportTestPointsMenuItem);

                exportReferencePointsMenuItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/detectprojv2j/resources/output_reference.png"))); // NOI18N
                exportReferencePointsMenuItem.setMnemonic('d');
                exportReferencePointsMenuItem.setText("Export reference points...");
                exportReferencePointsMenuItem.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                exportReferencePointsButtonActionPerformed(evt);
                        }
                });
                pointsMenu.add(exportReferencePointsMenuItem);

                menuBar.add(pointsMenu);

                analysisMenu.setMnemonic('a');
                analysisMenu.setText("Analysis");

                detectionMethodMenu.setText("Detection method");

                detectionMethodGroup.add(m7CheckBoxMenuItem);
                m7CheckBoxMenuItem.setSelected(true);
                m7CheckBoxMenuItem.setText("M7 (7 determined parameters)");
                m7CheckBoxMenuItem.addChangeListener(new javax.swing.event.ChangeListener() {
                        public void stateChanged(javax.swing.event.ChangeEvent evt) {
                                m7CheckBoxMenuItemStateChanged(evt);
                        }
                });
                detectionMethodMenu.add(m7CheckBoxMenuItem);

                detectionMethodGroup.add(m8CheckBoxMenuItem);
                m8CheckBoxMenuItem.setText("M8 (8 determined parameters)");
                m8CheckBoxMenuItem.addChangeListener(new javax.swing.event.ChangeListener() {
                        public void stateChanged(javax.swing.event.ChangeEvent evt) {
                                m8CheckBoxMenuItemStateChanged(evt);
                        }
                });
                detectionMethodMenu.add(m8CheckBoxMenuItem);

                analysisMenu.add(detectionMethodMenu);

                optimizationTechniqueMenu.setText("Optimization technique");

                optimizationTechniqueGroup.add(nonLinearLeastSquaresCheckBoxMenuItem);
                nonLinearLeastSquaresCheckBoxMenuItem.setText("Non-linear Least Squares");
                nonLinearLeastSquaresCheckBoxMenuItem.addChangeListener(new javax.swing.event.ChangeListener() {
                        public void stateChanged(javax.swing.event.ChangeEvent evt) {
                                nonLinearLeastSquaresCheckBoxMenuItemStateChanged(evt);
                        }
                });
                optimizationTechniqueMenu.add(nonLinearLeastSquaresCheckBoxMenuItem);

                optimizationTechniqueGroup.add(nelderMeadCheckBoxMenuItem);
                nelderMeadCheckBoxMenuItem.setSelected(true);
                nelderMeadCheckBoxMenuItem.setText("Nelder-Mead method");
                nelderMeadCheckBoxMenuItem.addChangeListener(new javax.swing.event.ChangeListener() {
                        public void stateChanged(javax.swing.event.ChangeEvent evt) {
                                nelderMeadCheckBoxMenuItemStateChanged(evt);
                        }
                });
                optimizationTechniqueMenu.add(nelderMeadCheckBoxMenuItem);

                optimizationTechniqueGroup.add(differentialEvolutionCheckBoxMenuItem);
                differentialEvolutionCheckBoxMenuItem.setText("Differential evolution");
                differentialEvolutionCheckBoxMenuItem.addChangeListener(new javax.swing.event.ChangeListener() {
                        public void stateChanged(javax.swing.event.ChangeEvent evt) {
                                differentialEvolutionCheckBoxMenuItemStateChanged(evt);
                        }
                });
                optimizationTechniqueMenu.add(differentialEvolutionCheckBoxMenuItem);

                analysisMenu.add(optimizationTechniqueMenu);
                analysisMenu.add(jSeparator5);

                analyzeMapMenuItem.setText("Analyze map");
                analyzeMapMenuItem.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                analyzeButtonActionPerformed(evt);
                        }
                });
                analysisMenu.add(analyzeMapMenuItem);
                analysisMenu.add(jSeparator3);

                showResultsMenuItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/detectprojv2j/resources/table.png"))); // NOI18N
                showResultsMenuItem.setText("Show results...");
                showResultsMenuItem.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
                showResultsMenuItem.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                showControlPointsButtonActionPerformed(evt);
                        }
                });
                analysisMenu.add(showResultsMenuItem);

                clearResultsMenuItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/detectprojv2j/resources/table_clear.png"))); // NOI18N
                clearResultsMenuItem.setText("Clear results");
                clearResultsMenuItem.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                clearResultsButtonActionPerformed(evt);
                        }
                });
                analysisMenu.add(clearResultsMenuItem);
                analysisMenu.add(jSeparator10);

                clearAllMenuItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/detectprojv2j/resources/clear_all.png"))); // NOI18N
                clearAllMenuItem.setText("Clear all");
                clearAllMenuItem.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                clearAllButtonActionPerformed(evt);
                        }
                });
                analysisMenu.add(clearAllMenuItem);
                analysisMenu.add(jSeparator15);

                settingsMenuItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/detectprojv2j/resources/settings.png"))); // NOI18N
                settingsMenuItem.setText("Settings...");
                settingsMenuItem.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                settingsMenuItemActionPerformed(evt);
                        }
                });
                analysisMenu.add(settingsMenuItem);

                menuBar.add(analysisMenu);

                helpMenu.setMnemonic('h');
                helpMenu.setText("Help");

                jMenuItem1.setText("Help...");
                jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                jMenuItem1ActionPerformed(evt);
                        }
                });
                helpMenu.add(jMenuItem1);

                aboutMenuItem.setMnemonic('a');
                aboutMenuItem.setText("About...");
                aboutMenuItem.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                aboutMenuItemActionPerformed(evt);
                        }
                });
                helpMenu.add(aboutMenuItem);

                menuBar.add(helpMenu);

                setJMenuBar(menuBar);

                javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
                getContentPane().setLayout(layout);
                layout.setHorizontalGroup(
                        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(splitPanels)
                        .addComponent(mainMenuPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 1900, Short.MAX_VALUE)
                );
                layout.setVerticalGroup(
                        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(mainMenuPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(splitPanels, javax.swing.GroupLayout.DEFAULT_SIZE, 766, Short.MAX_VALUE))
                );

                pack();
        }// </editor-fold>//GEN-END:initComponents

        
    private void exitMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitMenuItemActionPerformed
            //Close the application
            closeApplication();
    }//GEN-LAST:event_exitMenuItemActionPerformed

    
        private void analyzeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_analyzeButtonActionPerformed
                // Analyze early map
                if (!computation_in_progress[0])
                {
                        analyzeMapMenuItem.setEnabled(false);

                        //Perform analysis
                        analyzeEarlyMap();

                        //Push the button up and enable menu item
                        analyzeButton.setSelected(false);
                        analyzeMapMenuItem.setEnabled(true);
                }
        }//GEN-LAST:event_analyzeButtonActionPerformed

        
        private void selectDetectionMethodComboBoxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_selectDetectionMethodComboBoxItemStateChanged
                // Set detection method type (M7, M8)
                zoomGroup.clearSelection();
                addControlPointsToggleButton.setSelected(false);

                index_method  = selectDetectionMethodComboBox.getSelectedIndex() + 1;

                //Change menu items check
                if (index_method == 1) m7CheckBoxMenuItem.setSelected(true);
                else m8CheckBoxMenuItem.setSelected(true);
        }//GEN-LAST:event_selectDetectionMethodComboBoxItemStateChanged

        private void selectOptimizationTechniqueComboBoxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_selectOptimizationTechniqueComboBoxItemStateChanged
                // Set optimization technique (NLS, NM, DE)
                zoomGroup.clearSelection();
                addControlPointsToggleButton.setSelected(false);

                index_optimization = 10 * (selectOptimizationTechniqueComboBox.getSelectedIndex() + 1);

                //Change menu items check
                if (index_optimization == 10) nonLinearLeastSquaresCheckBoxMenuItem.setSelected(true);
                else if (index_optimization ==20) nelderMeadCheckBoxMenuItem.setSelected(true);
                else differentialEvolutionCheckBoxMenuItem.setSelected(true);
        }//GEN-LAST:event_selectOptimizationTechniqueComboBoxItemStateChanged

        
        private void importMapButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_importMapButtonActionPerformed
                //Import early map
                zoomGroup.clearSelection();
                addControlPointsToggleButton.setSelected(false);
                
                importEarlyMap();
        }//GEN-LAST:event_importMapButtonActionPerformed

        
        private void clearAllButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearAllButtonActionPerformed
                //Clear all constrol points and results
                if (!computation_in_progress[0])
                {
                        zoomGroup.clearSelection();
                        addControlPointsToggleButton.setSelected(false);

                        //Are there any points?
                        if ((test_points.size() > 0) || (reference_points.size() > 0))
                        {
                                //Show warning
                                final Object[] options = {"Clear all ", "Cancel"};
                                final int response = JOptionPane.showOptionDialog(null, "All results and control points will be deleted. Do you want to continue?", "Warning",
                                        JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[1]);

                                 //Clear all
                                if (response == 0)
                                {
                                        clearAll();

                                        //Update maps
                                        early_map.repaint();
                                        map.repaint();

                                        //Enable add points
                                        add_test_point[0] = true;
                                        add_reference_point[0] = true;

                                        //There are no nearest points
                                        index_nearest[0] = -1;                        
                                        index_nearest_prev[0] = -1; 
                               }
                        }
                }
        }//GEN-LAST:event_clearAllButtonActionPerformed

        
        private void showResultsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_showResultsButtonActionPerformed
                // Show results
                zoomGroup.clearSelection();
                addControlPointsToggleButton.setSelected(false);
                zoomGroup.clearSelection();
                
                results_form.printResult(n_results);
                
                results_form.setVisible(true);       
        }//GEN-LAST:event_showResultsButtonActionPerformed

        
        private void clearResultsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearResultsButtonActionPerformed
                //Clear all results
                if (!computation_in_progress[0])
                {
                        zoomGroup.clearSelection();
                        addControlPointsToggleButton.setSelected(false);
                        
                        if (results.size() > 0)
                        {
                                //Show warning
                                final Object[] options = {"Clear all ", "Cancel"};
                                final int response = JOptionPane.showOptionDialog(null, "All results will be deleted. Do you want to continue?", "Warning",
                                        JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[1]);

                                 //Clear all
                                if (response == 0)
                                {
                                        clearResults();

                                        //Update early map
                                        early_map.repaint();
                                }
                        }
                }
        }//GEN-LAST:event_clearResultsButtonActionPerformed

        
        private void zoomInToggleButtonStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_zoomInToggleButtonStateChanged
                // Enable, disable left mouse zoom-in
                if (zoomInToggleButton.isSelected())
                {
                        enable_zoom_in_lm[0] = true;
                        addControlPointsToggleButton.setSelected(false);
                }      
                
                //Disable zoom in
                else
                {
                        enable_zoom_in_lm[0] = false;
                }
                        
        }//GEN-LAST:event_zoomInToggleButtonStateChanged

        
        private void zoomOutToggleButtonStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_zoomOutToggleButtonStateChanged
                 // Enable, disable left mouse zoom-out
                if (zoomOutToggleButton.isSelected())
                {
                        enable_zoom_out_lm[0] = true;
                        addControlPointsToggleButton.setSelected(false);
                }      
                
                //Disable zoom out
                else
                {
                        enable_zoom_out_lm[0] = false;
                }
        }//GEN-LAST:event_zoomOutToggleButtonStateChanged

        
        private void viewAllToggleButtonStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_viewAllToggleButtonStateChanged
                // Enable, disable left mouse zoom-in
                if (viewAllToggleButton.isSelected())
                {
                        enable_zoom_fit_all_lm[0] = true;
                        addControlPointsToggleButton.setSelected(false);
                }      
                
                //Disable zoom fit all
                else
                {
                        enable_zoom_fit_all_lm[0] = false;
                }
        }//GEN-LAST:event_viewAllToggleButtonStateChanged

        
        private void exportGraticuleButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exportGraticuleButtonActionPerformed
                //Export graticule to DXF file
                zoomGroup.clearSelection();
                addControlPointsToggleButton.setSelected(false);
                
                exportGraticule();
        }//GEN-LAST:event_exportGraticuleButtonActionPerformed

        
        private void addControlPointButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addControlPointButtonActionPerformed
                // Enable/disable control points collection
                if (!computation_in_progress[0])
                {
                        enable_add_control_points[0] = !enable_add_control_points[0];

                        //Set button status
                        addControlPointsToggleButton.setSelected(enable_add_control_points[0]); 

                        zoomGroup.clearSelection();
                }
        }//GEN-LAST:event_addControlPointButtonActionPerformed
        
        
        private void importTestPointsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_importTestPointsButtonActionPerformed
                // Import test points
                if (!computation_in_progress[0])
                {
                        zoomGroup.clearSelection();
                        addControlPointsToggleButton.setSelected(false);
                        
                        importPoints(test_points, "Import test points", Point3DCartesian.class );

                        //Update early map
                        early_map.repaint();
                        
                        //Update table with control points
                        control_points_form.clearTable();
                        control_points_form.printResult();
                }
        }//GEN-LAST:event_importTestPointsButtonActionPerformed

        
        private void importReferencePointsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_importReferencePointsButtonActionPerformed
                // Import reference points
                if (!computation_in_progress[0])
                {
                        zoomGroup.clearSelection();
                        addControlPointsToggleButton.setSelected(false);
                        
                        importPoints(reference_points, "Import reference points", Point3DGeographic.class );

                        //Update OSM map
                        map.repaint();   
                        
                        //Update table with control points
                        control_points_form.clearTable();
                        control_points_form.printResult();
                }
        }//GEN-LAST:event_importReferencePointsButtonActionPerformed

        
        private void exportTestPointsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exportTestPointsButtonActionPerformed
                // Export test points
                if (test_points.size() > 0)
                        exportPoints(test_points, "Export test points", "test_points.txt" );
        }//GEN-LAST:event_exportTestPointsButtonActionPerformed

        
        private void exportReferencePointsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exportReferencePointsButtonActionPerformed
                // Export reference points
                if (reference_points.size()> 0)
                        exportPoints(reference_points, "Export reference points", "reference_points.txt" );
        }//GEN-LAST:event_exportReferencePointsButtonActionPerformed

        private void zoomInMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_zoomInMenuItemActionPerformed
                // Zoom in
                enable_panning_lm[0] = false;
                enable_zoom_in_lm[0] = true;
                enable_zoom_out_lm[0] = false;
                enable_zoom_fit_all_lm[0] = false;
                addControlPointsToggleButton.setSelected(false);
        }//GEN-LAST:event_zoomInMenuItemActionPerformed

        
        private void viewAllMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_viewAllMenuItemActionPerformed
                // View all
                enable_panning_lm[0] = false;
                enable_zoom_fit_all_lm[0] = true;
                enable_zoom_in_lm[0] = false;
                enable_zoom_out_lm[0] = false;
                addControlPointsToggleButton.setSelected(false);
        }//GEN-LAST:event_viewAllMenuItemActionPerformed

        
        private void zoomOutMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_zoomOutMenuItemActionPerformed
                // Zoom out
                enable_panning_lm[0] = false;
                enable_zoom_out_lm[0] = true;
                enable_zoom_in_lm[0] = false;
                enable_zoom_fit_all_lm[0] = false;
                addControlPointsToggleButton.setSelected(false);
        }//GEN-LAST:event_zoomOutMenuItemActionPerformed

        
        private void m7CheckBoxMenuItemStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_m7CheckBoxMenuItemStateChanged
                // Set detection method: M7
                zoomGroup.clearSelection();
                addControlPointsToggleButton.setSelected(false);

                if (m7CheckBoxMenuItem.isSelected())
                {
                        //Set detection method
                        index_method = 1;

                        //Change ComboBox item
                        selectDetectionMethodComboBox.setSelectedIndex(index_method - 1);
                }
        }//GEN-LAST:event_m7CheckBoxMenuItemStateChanged

        
        private void m8CheckBoxMenuItemStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_m8CheckBoxMenuItemStateChanged
                // Set detection method: M8
                zoomGroup.clearSelection();
                addControlPointsToggleButton.setSelected(false);

                if (m8CheckBoxMenuItem.isSelected())
                {
                        //Set detection method
                        index_method = 2;

                        //Change ComboBox item
                        selectDetectionMethodComboBox.setSelectedIndex(index_method - 1);
                }
        }//GEN-LAST:event_m8CheckBoxMenuItemStateChanged

        
        private void nonLinearLeastSquaresCheckBoxMenuItemStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_nonLinearLeastSquaresCheckBoxMenuItemStateChanged
                // Set optimization technique: NLS
                zoomGroup.clearSelection();
                addControlPointsToggleButton.setSelected(false);

                if (nonLinearLeastSquaresCheckBoxMenuItem.isSelected())
                {
                        //Set optimization technique
                        index_optimization = 10;

                        //Change ComboBox item
                        selectOptimizationTechniqueComboBox.setSelectedIndex(index_optimization / 10 - 1);
                }
        }//GEN-LAST:event_nonLinearLeastSquaresCheckBoxMenuItemStateChanged

        
        private void nelderMeadCheckBoxMenuItemStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_nelderMeadCheckBoxMenuItemStateChanged
                // Set optimization technique: NM
                zoomGroup.clearSelection();
                addControlPointsToggleButton.setSelected(false);

                if (nelderMeadCheckBoxMenuItem.isSelected())
                {
                        //Set optimization technique
                        index_optimization = 20;

                        //Change ComboBox item
                        selectOptimizationTechniqueComboBox.setSelectedIndex(index_optimization / 10 - 1);
                }
        }//GEN-LAST:event_nelderMeadCheckBoxMenuItemStateChanged

        
        private void differentialEvolutionCheckBoxMenuItemStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_differentialEvolutionCheckBoxMenuItemStateChanged
                // Set optimization technique: DE
                zoomGroup.clearSelection();
                addControlPointsToggleButton.setSelected(false);

                //Set optimization technique
                index_optimization = 30;

                //Change ComboBox item
                selectOptimizationTechniqueComboBox.setSelectedIndex(index_optimization / 10 - 1);
        }//GEN-LAST:event_differentialEvolutionCheckBoxMenuItemStateChanged

        
        private void aboutMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_aboutMenuItemActionPerformed
                // Show about box
                about_form.setVisible(true);
        }//GEN-LAST:event_aboutMenuItemActionPerformed

        
        private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
                // Close detectproj
                closeApplication();
        }//GEN-LAST:event_formWindowClosing

        
        private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
                //Set zoom level to view the entire map
                early_map.zoomFitAll();
        }//GEN-LAST:event_formWindowOpened

        
        private void showControlPointsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_showControlPointsButtonActionPerformed
                //Print points
                zoomGroup.clearSelection();
                
                //Update control points
                control_points_form.printResult();
                control_points_form.setVisible(true);
        }//GEN-LAST:event_showControlPointsButtonActionPerformed

        
        private void addControlPointsToggleButtonStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_addControlPointsToggleButtonStateChanged
                // Enable adding control point
                if (!computation_in_progress[0])
                {
                        if (addControlPointsToggleButton.isSelected())
                        {
                                enable_add_control_points[0] = true;
                                zoomGroup.clearSelection();
                        }
                        
                        else
                        {
                                enable_add_control_points[0] = false;
                        }
                }   
        }//GEN-LAST:event_addControlPointsToggleButtonStateChanged

        private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
                // Display help in chm format
                try 
                {
                        Path path = Files.createTempFile(null, ".chm");
                        InputStream res = getClass().getResourceAsStream("/detectprojv2j/resources/detectproj.chm");
                        Files.copy(res, path,  StandardCopyOption.REPLACE_EXISTING);
                        Desktop.getDesktop().open(path.toFile());
                } 
                
                //Throw exception
                catch (Exception e) 
                {
                        e.printStackTrace();
                }
        }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void settingsMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_settingsMenuItemActionPerformed
            //Show settings dialog
            settings_form.setVisible(true);
    }//GEN-LAST:event_settingsMenuItemActionPerformed

    
        private void settingsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_settingsButtonActionPerformed
                //Show settings dialog
                settings_form.setVisible(true);
        }//GEN-LAST:event_settingsButtonActionPerformed

        private void panningToggleButtonStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_panningToggleButtonStateChanged
                // Enable, disable left mouse panning
                if (panningToggleButton.isSelected())
                {
                        enable_panning_lm[0] = true;
                        addControlPointsToggleButton.setSelected(false);
                }      
                
                //Disable zoom in
                else
                {
                        enable_panning_lm[0] = false;
                }
        }//GEN-LAST:event_panningToggleButtonStateChanged

        private void panningMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_panningMenuItemActionPerformed
                // Panning
                enable_panning_lm[0] = true;
                enable_zoom_in_lm[0] = false;
                enable_zoom_out_lm[0] = false;
                enable_zoom_fit_all_lm[0] = false;
                addControlPointsToggleButton.setSelected(false);
        }//GEN-LAST:event_panningMenuItemActionPerformed

        
        public void closeApplication()
        {
                //Show prompt before closing the application
                final Object[] options = {"Exit detectproj", "Cancel"};
                final int response = JOptionPane.showOptionDialog(null, "Detectproj will be closed. Are you sure you want to exit?", "Warning",
                                JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[1]);

                //Clear main window
                if (response == 0)
                {
                      System.exit(0);
                }
        }

        
        public void analyzeEarlyMap ()
        {
                //Analyze all projections
                final int n_test = test_points.size(), n_reference = reference_points.size();
                
                //The previous analysis has been finished
                if (!computation_in_progress[0])
                {
                        //Clear results
                        clearResults();
                        
                        //Create list of projections
                        Projections.init(projections, default_lon_dir[0]);

                        //Set detection and optimization methods
                        switch (index_method + index_optimization)
                        {
                                        case 11: method = TAnalysisMethod.NLSM7;
                                                         break;
                                        case 12: method = TAnalysisMethod.NLSM8;
                                                         break;
                                        case 21: method = TAnalysisMethod.NMM7;
                                                         break;
                                        case 22: method = TAnalysisMethod.NMM8;
                                                         break;
                                        case 31: method = TAnalysisMethod.DEM7;
                                                         break;
                                        case 32: method = TAnalysisMethod.DEM8;
                                                         break;         
                        }

                        //Different amount of points (1 omitted point)
                        if (abs(n_test - n_reference) == 1)
                        {
                                        //Show warning
                                        final Object[] options = {"Delete point", "Cancel"};
                                        final int response = JOptionPane.showOptionDialog(null, "Different amount of control points (1 point is missing). The analysis cannot be computed. Delete LAST collected point?", "Warning",
                                                        JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[1]);

                                        //Delete last control point
                                        if (response == 0)
                                        {
                                                        //Delete a control point on the test map
                                                        if (n_test > n_reference)
                                                                        test_points.remove(n_test - 1);

                                                        //Delete a control point on the reference map
                                                        else
                                                                        map.deletePoint(n_reference - 1);

                                                        //Repaint maps
                                                        early_map.repaint();
                                                        map.repaintMap();

                                                        //Enable adding points
                                                        add_test_point[0] = true;
                                                        add_reference_point[0] = true;
                                        }
                        }

                        //Different amount of points (more than 1 omitted point, wrong data!)
                        else if (abs(n_test - n_reference) > 1)
                        {
                                        //Show warning
                                        final Object[] options = {"Delete points", "Cancel"};
                                        final int response = JOptionPane.showOptionDialog(null, "Wrong data, " + abs(n_test - n_reference) + " poins are missing! " + "The analysis cannot be computed. Delete ALL collected points?", "Warning",
                                                        JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[1]);

                                        //Clear all control points
                                        if (response == 0)
                                        {
                                                        clearAll();
                                        }

                                        return;
                        }

                        //Not enough points
                        if (n_test < 5)
                                        return;

                        //Analyze map projection in new thread
                        CartAnalysisMT ca = new CartAnalysisMT(test_points, reference_points, projections, results, method, analyze_lon0[0], System.out, analyzeButton, new Runnable() 
                        {
                                        @Override
                                        public void run() 
                                        {
                                                        //Print results: all operation performed after the thread has been finished
                                                        results_form.printResult(n_results);

                                                        //Geographic extent of the analyzed territory   
                                                        double lat_min = (create_entire_graticule[0] ? -89.0 : (Collections.min(reference_points, new SortByLat())).getLat());
                                                        double lat_max = (create_entire_graticule[0] ? 89.0 : (Collections.max(reference_points, new SortByLat())).getLat());
                                                        double lon_min = (create_entire_graticule[0] ? -180.0 : (Collections.min(reference_points, new SortByLon())).getLon());
                                                        double lon_max = (create_entire_graticule[0] ? 180.0 : (Collections.max(reference_points, new SortByLon())).getLon());

                                                        double lat_aver = 0.5 * (lat_min + lat_max), lon_aver = 0.5 * (lon_min + lon_max);

                                                        //Get limits; stretch over the whole planishere, if necessarry
                                                        if (((lon_min < MIN_LON + 40) || (lon_max > MAX_LON - 40)) && (lon_max - lon_min > 200))
                                                        {
                                                                lon_min = MIN_LON;
                                                                lon_max = MAX_LON;
                                                        }

                                                        //Change lat/lon intervals depending on the analyzed territory size
                                                        lat_interval.min_value = lat_min;
                                                        lat_interval.max_value = lat_max;
                                                        lon_interval.min_value = lon_min;
                                                        lon_interval.max_value = lon_max;
                                                        
                                                        //Correct steps to avoid slow graticule construction
                                                        final double dlat = lat_max - lat_min;
                                                        final double dlon = lon_max - lon_min;

                                                        final double lat_step = ( dlat < 20.0 ? (dlat < 2.0 ? (lat3_step[0] = max(lat3_step[0], dlat/50)) : (lat2_step[0] = max(lat2_step[0], dlat/50))) : (lat1_step[0] = max(lat1_step[0], dlat/50)));
                                                        final double lon_step = ( dlon < 20.0 ? (dlon < 2.0 ? (lon3_step[0] = max(lon3_step[0], dlon/50)) : (lon2_step[0] = max(lon2_step[0], dlon/50))) : (lon1_step[0] = max(lon1_step[0], dlon/50)));
                                                        
                                                        lat_incr[0] = max(lat_incr[0], dlat / 20000.0 * lat_step);
                                                        lon_incr[0] = max(lon_incr[0], dlon / 40000.0 * lon_step);
                                                        
                                                        //Create and store graticules
                                                        int index = 0;
                                                        for (java.util.Map.Entry<Double, TResult> entry : results.entrySet())
                                                        {
                                                                //Create lists of meridians/parallels
                                                                List <Meridian> meridians = new ArrayList<>();
                                                                List <Parallel> parallels = new ArrayList<>();
                                                                List <List<Point3DCartesian> > meridians_proj = new ArrayList<>();
                                                                List <List<Point3DCartesian> > parallels_proj = new ArrayList<>();

                                                                //Set font height      
                                                                Double key = entry.getKey();
                                                                TResult value = entry.getValue();

                                                                 //Get map rotation
                                                                final double alpha = value.map_rotation;

                                                                //Compute projected points
                                                                List <Point3DCartesian> points_proj = new ArrayList<>();
                                                                CartTransformation.latsLonstoXY (reference_points, value.proj, alpha, points_proj);

                                                                //Create graticule 
                                                                //System.out.println(value.proj.getName());
                                                                Graticule2.createGraticule(value.proj, lat_interval, lon_interval, lat_step, lon_step, lat_incr[0], lon_incr[0], alpha, meridians, meridians_proj, parallels, parallels_proj);

                                                                //Store meridians/parallels, reconstructed points, meridians/paralleles 
                                                                value.meridians = meridians;
                                                                value.parallels = parallels;
                                                                value.points_proj = points_proj;
                                                                value.meridians_proj= meridians_proj;
                                                                value.parallels_proj = parallels_proj;

                                                                //Increment index
                                                                index ++;
                                                        }

                                                        //Display results of the best fit projection: set meridians/paralells
                                                        TResult result_first = results.firstEntry().getValue();
                                                        early_map.setMeridians(result_first.meridians);
                                                        early_map.setParallels(result_first.parallels);

                                                        //Display results of the best fit projection: set projected points, projected meridians/parallels
                                                        early_map.setProjection(result_first.proj);
                                                        early_map.setProjectedMeridians(result_first.meridians_proj);
                                                        early_map.setProjectedParallels(result_first.parallels_proj);
                                                        early_map.setProjectedPoints(result_first.points_proj);

                                                        //Update main window title
                                                        MainApplication.this.setTitle("Map projection analysis: " + result_first.proj.getName() + " projection");

                                                        //Update early map
                                                        early_map.repaint();

                                                        //Show window with results
                                                        results_form.setVisible(true);

                                                        //Enable change buttons
                                                        computation_in_progress[0] = false;
                                        }
                        });

                        //Disable change buttons
                        computation_in_progress[0] = true;

                        //Create new thread and run
                        Thread t = new Thread(ca);
                        t.start(); 
                }
        }
       
        
        public void importEarlyMap ()
        {
                //Import early map from raster file
                JFileChooser fc = new JFileChooser();
                
                //Disable all file filters and set new
                fc.setAcceptAllFileFilterUsed(false);
                FileNameExtensionFilter filter = new FileNameExtensionFilter("Images", "jpg", "png", "gif", "tiff");
                fc.setFileFilter(filter);
                
                //Set dialog title and working directory
                fc.setDialogTitle("Upload early map");
                fc.setCurrentDirectory(new File(System.getProperty("user.home")));
                int result = fc.showOpenDialog(this);
                
                //Get the file
                if (result == JFileChooser.APPROVE_OPTION) 
                {
                        //Import early map
                        importEarlyMap(fc.getSelectedFile());                   
                }
        }
        
        
        private void importEarlyMap (final File file)
        {
                //Load early map
                try
                {
                        img = ImageIO.read(file);
                        early_map.setImage(img);

                        //Set zoom to fit all
                        early_map.zoomFitAll();   
                }

                //File not found, throw exception
                catch (FileNotFoundException exception) 
                {
                        exception.printStackTrace();
                } 

                //Throw I/O exception
                catch (IOException exception) 
                {
                        exception.printStackTrace();
                }
        }
        
        
        public <Point> void importPoints(List<Point> points, String window_title, final Class<Point> o_class)
        {
                //Import test or reference points        
                JFileChooser fc = new JFileChooser();
                
                //Disable all file filters and set new
                fc.setAcceptAllFileFilterUsed(false);
                FileNameExtensionFilter filter = new FileNameExtensionFilter("Text files", "txt");
                fc.setFileFilter(filter);
                
                //Set dialog title and working directory
                fc.setDialogTitle(window_title);
                fc.setCurrentDirectory(new File(System.getProperty("user.home")));
                int result = fc.showOpenDialog(this);
                       
                //Get the file name
                if (result == JFileChooser.APPROVE_OPTION) 
                {
                        //Import points
                        importPoints(points, fc.getSelectedFile(), o_class);   
                }
        }
        
        
        private <Point> void importPoints(List<Point> points, final File file, final Class<Point> o_class)
        {
                //Load test/reference points and create map markers
                try
                {
                        //Clear old points
                        if ((test_points.size() > 0) && (reference_points.size() > 0))
                        {       
                                //Display warning box
                                final Object[] options = {"Continue", "Cancel"};
                                final int response = JOptionPane.showOptionDialog(null, "All results and control points will be deleted. Do you want to continue?", "Warning",
                                         JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[1]);

                                //Clear all
                                if (response == 0)
                                        clearAll();
                        }

                        //Load points
                        String points_file = file.toString();
                        IO.load2DPoints(points_file, points, o_class);                   
                }

                //Throw exception
                catch (Exception exception) 
                {
                        exception.printStackTrace();
                }
                
                //Add user-defined map markers forloaded points to OSM map
                if (o_class == Point3DGeographic.class)
                { 
                        //Process all loaded points
                        for (final Point3DGeographic point:reference_points)
                        {
                                MyMapMarker m = new MyMapMarker(point.getLat(),point.getLon(), Consts.MAP_MARKER_RADIUS);
                                map.addMapMarker(m);
                        }
                }
        }
             
        
        public <Point extends IPoint3DFeatures> void exportPoints( final List <Point> points, String window_title, String file_test_points )
        {
                //Export collected test/reference points
                JFileChooser fc = new JFileChooser();
                fc.setAcceptAllFileFilterUsed(false);
                FileNameExtensionFilter filter = new FileNameExtensionFilter("Text files", "txt");
                fc.setFileFilter(filter);

                //Set dialog title and working directory
                fc.setDialogTitle(window_title);
                fc.setCurrentDirectory(new File(System.getProperty("user.home")));
                fc.setSelectedFile(new File(file_test_points));
                        
                //Get the file
                int result = fc.showSaveDialog(this);
                if (result == JFileChooser.APPROVE_OPTION) 
                {
                        //Export to TXT + save
                        try
                        {
                                //Get selected file
                                file_test_points = fc.getSelectedFile().toString();

                                //Export graticule to DXF
                                IO.save2DPoints(file_test_points, points);
                        }
                        
                        //Throw exception
                        catch (Exception exception) 
                        {
                                exception.printStackTrace();
                        }
                }
        }
      
        
        public void exportGraticule()
        {
                //Export graticule to DXF file
                final double lat_step = 10, lon_step = 10;
                
                //Get projected points, meridians/parallels, reconstructed points and reconstructed meridians/parallels of the projection assigned to the analyzed map
                List <Point3DCartesian> reference_points_proj = early_map.getProjectedPoints();
                List <Meridian> meridians = early_map.getMeridians();
                List <Parallel> parallels = early_map.getParallels();
                Projection proj = early_map.getProjection();
                List <List<Point3DCartesian> > meridians_proj = early_map.getProjectedMeridians();
                List <List<Point3DCartesian> > parallels_proj = early_map.getProjectedParalells();

                if ((meridians != null) && (parallels != null) && (proj != null) && (meridians_proj != null) && (parallels_proj != null))
                {
                        //Set properties of the  dialog
                        JFileChooser fc = new JFileChooser();
                        fc.setAcceptAllFileFilterUsed(false);
                        FileNameExtensionFilter filter = new FileNameExtensionFilter("CAD files", "dxf");
                        fc.setFileFilter(filter);

                        //Create suggested file name
                        String file_graticule_text = "grat_" + proj.getName() + ".dxf";

                        //Set dialog title and working directory
                        fc.setDialogTitle("Export graticule");
                        fc.setCurrentDirectory(new File(System.getProperty("user.home")));
                        fc.setSelectedFile(new File(file_graticule_text));

                        //Get the file
                        int result = fc.showSaveDialog(this);
                        if (result == JFileChooser.APPROVE_OPTION) 
                        {
                                //Export to DXF + save
                                try
                                {
                                        //Get selected file
                                        file_graticule_text = fc.getSelectedFile().toString();

                                        final double font_height = 0.05 * proj.getR() * min(lat_step, lon_step) * PI / 180;
                                        DXFExport.exportGraticuleToDXF(file_graticule_text, meridians, meridians_proj, parallels, parallels_proj, test_points, reference_points_proj, font_height, min(lat_step, lon_step));
                                }

                                //Throw I/O exception
                                catch (Exception exception) 
                                {
                                        exception.printStackTrace();
                                }
                        }
                }
        }
        
        
        public void setChangeButtonStatus(final boolean status)
        {
                //Disable all buttons changing control points
                addControlPointsToggleButton.setEnabled(status);
                importTestPointsButton.setEnabled(status);
                importReferencePointsButton.setEnabled(status);
                clearAllButton.setEnabled(status);
        }
        
    
        private void clearAll()
        {
                //Delete all test/reference points, map markers nad results
                test_points.clear();
                reference_points.clear();
                
                //Delete projected points, meridians and parallels
                clearResults();
                
                //Clear all mp marker
                map.removeAllMapMarkers();
        }
        
        
        private void clearResults()
        {
                // Clear computed results
                results.clear();
                
                //Clear list of projections
                projections.clear();
                
                //Reset pasrameters
                early_map.setMeridians(null);
                early_map.setParallels(null);
                early_map.setProjectedMeridians(null);
                early_map.setProjectedParallels(null);
                early_map.setProjectedPoints(null);

                //Clear table
                results_form.clearTable();
                
                //Remove the projection name
                this.setTitle("Map projection analysis: detectproj");
        }
        

        public static void main(String args[]) {
                /* Set the Nimbus look and feel */
                //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
                /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
                 */
                try {
                        for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                                if ("Nimbus".equals(info.getName())) {
                                        javax.swing.UIManager.setLookAndFeel(info.getClassName());
                                        break;
                                }
                        }
                } catch (ClassNotFoundException ex) {
                        java.util.logging.Logger.getLogger(MainApplication.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                } catch (InstantiationException ex) {
                        java.util.logging.Logger.getLogger(MainApplication.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                } catch (IllegalAccessException ex) {
                        java.util.logging.Logger.getLogger(MainApplication.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                } catch (javax.swing.UnsupportedLookAndFeelException ex) {
                        java.util.logging.Logger.getLogger(MainApplication.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                }
                //</editor-fold>

                /* Create and display the form */
                java.awt.EventQueue.invokeLater(new Runnable() {
                        public void run() {
                                new MainApplication().setVisible(true);
                        }
                });
        }

        // Variables declaration - do not modify//GEN-BEGIN:variables
        private javax.swing.JMenuItem aboutMenuItem;
        private javax.swing.JMenuItem addControPointsMenuItem;
        private javax.swing.JToggleButton addControlPointsToggleButton;
        private javax.swing.JMenu analysisMenu;
        private javax.swing.JToggleButton analyzeButton;
        private javax.swing.JMenuItem analyzeMapMenuItem;
        private javax.swing.JToolBar analyzeToolBar;
        private javax.swing.JButton clearAllButton;
        private javax.swing.JMenuItem clearAllMenuItem;
        private javax.swing.JButton clearResultsButton;
        private javax.swing.JMenuItem clearResultsMenuItem;
        private javax.swing.JToolBar controlPointsToolBar;
        private javax.swing.ButtonGroup detectionMethodGroup;
        private javax.swing.JMenu detectionMethodMenu;
        private javax.swing.JCheckBoxMenuItem differentialEvolutionCheckBoxMenuItem;
        private javax.swing.JPanel earlyMapPanel;
        private javax.swing.JMenuItem exitMenuItem;
        private javax.swing.JButton exportGraticuleButton;
        private javax.swing.JButton exportReferencePointsButton;
        private javax.swing.JMenuItem exportReferencePointsMenuItem;
        private javax.swing.JButton exportTestPointsButton;
        private javax.swing.JMenuItem exportTestPointsMenuItem;
        private javax.swing.ButtonGroup fileGroup;
        private javax.swing.JToolBar fileToolBar;
        private javax.swing.JMenu helpMenu;
        private javax.swing.JButton importMapButton;
        private javax.swing.JButton importReferencePointsButton;
        private javax.swing.JMenuItem importReferencePointsMenuItem;
        private javax.swing.JButton importTestPointsButton;
        private javax.swing.JMenuItem importTestPointsMenuItem;
        private javax.swing.JMenuItem jMenuItem1;
        private javax.swing.JMenuItem jMenuItem2;
        private javax.swing.JPopupMenu.Separator jSeparator1;
        private javax.swing.JPopupMenu.Separator jSeparator10;
        private javax.swing.JToolBar.Separator jSeparator11;
        private javax.swing.JToolBar.Separator jSeparator12;
        private javax.swing.JPopupMenu.Separator jSeparator13;
        private javax.swing.JToolBar.Separator jSeparator14;
        private javax.swing.JPopupMenu.Separator jSeparator15;
        private javax.swing.JToolBar.Separator jSeparator16;
        private javax.swing.JToolBar.Separator jSeparator2;
        private javax.swing.JPopupMenu.Separator jSeparator3;
        private javax.swing.JPopupMenu.Separator jSeparator4;
        private javax.swing.JPopupMenu.Separator jSeparator5;
        private javax.swing.JToolBar.Separator jSeparator6;
        private javax.swing.JPopupMenu.Separator jSeparator7;
        private javax.swing.JToolBar.Separator jSeparator8;
        private javax.swing.JToolBar.Separator jSeparator9;
        private javax.swing.JCheckBoxMenuItem m7CheckBoxMenuItem;
        private javax.swing.JCheckBoxMenuItem m8CheckBoxMenuItem;
        private javax.swing.JPanel mainMenuPanel;
        private javax.swing.JMenu mapMenu;
        private javax.swing.JMenuBar menuBar;
        private javax.swing.JCheckBoxMenuItem nelderMeadCheckBoxMenuItem;
        private javax.swing.JCheckBoxMenuItem nonLinearLeastSquaresCheckBoxMenuItem;
        private javax.swing.JMenuItem openMenuItem;
        private javax.swing.ButtonGroup optimizationTechniqueGroup;
        private javax.swing.JMenu optimizationTechniqueMenu;
        private javax.swing.JPanel osmMapPanel;
        private javax.swing.JMenuItem panningMenuItem;
        private javax.swing.JToggleButton panningToggleButton;
        private javax.swing.JMenu pointsMenu;
        private javax.swing.ButtonGroup resultsGroup;
        private javax.swing.JMenuItem saveMenuItem;
        private javax.swing.JComboBox<String> selectDetectionMethodComboBox;
        private javax.swing.JPanel selectOptimizationPanel;
        private javax.swing.JComboBox<String> selectOptimizationTechniqueComboBox;
        private javax.swing.JButton settingsButton;
        private javax.swing.JMenuItem settingsMenuItem;
        private javax.swing.JButton showControlPointsButton;
        private javax.swing.JButton showResultsButton;
        private javax.swing.JMenuItem showResultsMenuItem;
        private javax.swing.JSplitPane splitPanels;
        private javax.swing.JMenuItem viewAllMenuItem;
        private javax.swing.JToggleButton viewAllToggleButton;
        private javax.swing.JMenu viewMenu;
        private javax.swing.ButtonGroup zoomGroup;
        private javax.swing.JMenuItem zoomInMenuItem;
        private javax.swing.JToggleButton zoomInToggleButton;
        private javax.swing.JMenuItem zoomOutMenuItem;
        private javax.swing.JToggleButton zoomOutToggleButton;
        private javax.swing.JToolBar zoomToolBar;
        // End of variables declaration//GEN-END:variables

}
