// Description: List of collected control points and their residuals

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

import java.util.List;
import java.util.Locale;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableRowSorter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import detectprojv2j.structures.point.Point3DCartesian;

import detectprojv2j.comparators.sortJColumnByDouble;


public class ControlPointsForm extends javax.swing.JFrame {
        
        private final EarlyMap early_map;                                               //Early map prepresentation
        private final OSMMap map;                                                          //Reference map prepresentation (OSM)
        private JPopupMenu pop_up_menu;                                                 //Pop-up menu (point deletition)
        private boolean [] add_test_point;                                              //Control point may be added to the early map
        private boolean [] add_reference_point;                                         //Control point may be added to the reference (OSM) map
        private boolean [] computation_in_progress;                                     //Test, whether a computation is in progress
        private int [] index_nearest;                                                   //Index of the point nearest to the cursor position
        private int [] index_nearest_prev;                                              //Index of the previous point nearest to the cursor position

        
        public ControlPointsForm(EarlyMap early_map_, OSMMap map_, boolean [] add_test_point_, boolean [] add_reference_point_, boolean [] computation_in_progress_, int [] index_nearest_, int [] index_nearest_prev_)
        {
                //Initialize parameters
                initComponents();
                
                //Assign points
                early_map = early_map_;
                map = map_;
                
                //Assign indicators
                add_test_point = add_test_point_;
                add_reference_point = add_reference_point_;
                computation_in_progress = computation_in_progress_;
                index_nearest = index_nearest_;
                index_nearest_prev = index_nearest_prev_;
                
                //Set rows of the table
                DefaultTableModel model = (DefaultTableModel)controlPointsTable.getModel();
                model.setRowCount(20);
                
                //Initialize table: create header
                initializeTable();
                
                //Create  pop up menu
                pop_up_menu = new JPopupMenu();
                JMenuItem deleteItem = new JMenuItem("Delete control point");
                
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
                                                if ((add_test_point[0]) && (map.reference_points.size() > index_nearest[0]))
                                                        map.deleteNearestPoint();
                                                
                                                //Delete nearest point: projected reference point
                                                if ((early_map.projected_points != null) && (early_map.projected_points.size() > index_nearest[0]))
                                                        early_map.projected_points.remove(index_nearest[0]);
                                                
                                                //Repaint both maps
                                                early_map.repaint();
                                                map.repaint();
                                                
                                                //Enable adding new points
                                                add_test_point[0] = true;
                                                add_reference_point[0] = true;

                                                //Update table
                                                clearTable();
                                                printResult();
                                        }
                                }
                        }
                });
                
                pop_up_menu.add(deleteItem);
                
                //Set selection model
                controlPointsTable.getSelectionModel().addListSelectionListener( new ListSelectionListener() 
                {
                        @Override
                        public void valueChanged(ListSelectionEvent event) 
                        {
                                //Index of the selected row
                                int sel_index = controlPointsTable.getSelectedRow();
                                
                                //Recomputed index according to the sorted elements
                                final int point_index  = sel_index > -1 ? controlPointsTable.convertRowIndexToModel( sel_index ) : -1;

                                //Highlight selected control point
                                if ((point_index < Math.max(early_map.test_points.size(), map.reference_points.size())) && (point_index >= 0))
                                {
                                        //Assign index
                                        index_nearest_prev[0] = index_nearest[0];
                                        index_nearest[0] = point_index;

                                        //Repaint maps
                                        early_map.repaint();
                                        map.repaintMap();
                                }
                        }
                });
        }

        @SuppressWarnings("unchecked")
        // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
        private void initComponents() {

                jScrollPane1 = new javax.swing.JScrollPane();
                controlPointsTable = new javax.swing.JTable();

                setTitle("List of control points and residuals");
                setLocation(new java.awt.Point(200, 500));
                setType(java.awt.Window.Type.UTILITY);

                controlPointsTable.setModel(new javax.swing.table.DefaultTableModel(
                        new Object [][] {
                                {null, null, null, null, null, null, null, null},
                                {null, null, null, null, null, null, null, null},
                                {null, null, null, null, null, null, null, null},
                                {null, null, null, null, null, null, null, null},
                                {null, null, null, null, null, null, null, null},
                                {null, null, null, null, null, null, null, null},
                                {null, null, null, null, null, null, null, null},
                                {null, null, null, null, null, null, null, null},
                                {null, null, null, null, null, null, null, null},
                                {null, null, null, null, null, null, null, null},
                                {null, null, null, null, null, null, null, null},
                                {null, null, null, null, null, null, null, null},
                                {null, null, null, null, null, null, null, null},
                                {null, null, null, null, null, null, null, null},
                                {null, null, null, null, null, null, null, null},
                                {null, null, null, null, null, null, null, null},
                                {null, null, null, null, null, null, null, null},
                                {null, null, null, null, null, null, null, null},
                                {null, null, null, null, null, null, null, null},
                                {null, null, null, null, null, null, null, null}
                        },
                        new String [] {
                                "Title 1", "Title 2", "Title 3", "Title 4", "Title 5", "Title 6", "Title 7", "Title 8"
                        }
                ) {
                        boolean[] canEdit = new boolean [] {
                                false, false, false, false, false, false, false, false
                        };

                        public boolean isCellEditable(int rowIndex, int columnIndex) {
                                return canEdit [columnIndex];
                        }
                });
                controlPointsTable.setRequestFocusEnabled(false);
                controlPointsTable.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
                controlPointsTable.addMouseListener(new java.awt.event.MouseAdapter() {
                        public void mouseClicked(java.awt.event.MouseEvent evt) {
                                controlPointsTableMouseClicked(evt);
                        }
                });
                jScrollPane1.setViewportView(controlPointsTable);

                javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
                getContentPane().setLayout(layout);
                layout.setHorizontalGroup(
                        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 862, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                );
                layout.setVerticalGroup(
                        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 368, Short.MAX_VALUE)
                );

                pack();
        }// </editor-fold>//GEN-END:initComponents

        private void controlPointsTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_controlPointsTableMouseClicked
                
                 //Delete control point from the table
                if (!SwingUtilities.isLeftMouseButton(evt)) 
                {
                        //Show pop-up menu, remove test point, reference point, or a pair
                        if (index_nearest[0] != -1)
                                pop_up_menu.show(controlPointsTable, evt.getX(), evt.getY()); 
                }
        }//GEN-LAST:event_controlPointsTableMouseClicked

        
        private void initializeTable()
        {
                //Set properties of the table
                String[] col_names = { "#", "X_test [pix]", "Y_test [pix]", "Lat_ref [deg]", "Lon_ref [deg]", "X_proj [pix]", "Y_proj [pix]", "dX [pix]", "dY [pix]"};
                DefaultTableModel model = (DefaultTableModel)controlPointsTable.getModel();
                model.setColumnIdentifiers(col_names);
                
                 //Enable sorting by columns
                TableRowSorter trs = new TableRowSorter(model);  
                for (int i = 0; i < col_names.length; i++)
                        trs.setComparator(i, new sortJColumnByDouble());

                controlPointsTable.setRowSorter(trs);   
                
                //Set center alignment for the header
                JTableHeader header = controlPointsTable.getTableHeader();
                DefaultTableCellRenderer r_header = (DefaultTableCellRenderer)header.getDefaultRenderer();
                r_header.setHorizontalAlignment(SwingConstants.CENTER);
                
                //Set font size for header and table
                header.setFont( header.getFont().deriveFont(12f) );
                controlPointsTable.setFont(header.getFont().deriveFont(12f));
                
                //Create renderer for the remaining part  of the table
                DefaultTableCellRenderer r_cells = new DefaultTableCellRenderer();
                r_cells.setHorizontalAlignment(SwingConstants.CENTER);
                
                //Set center alignment for each column of the table
                for (int i = 0; i < col_names.length; i++)
                        controlPointsTable.getColumnModel().getColumn(i).setCellRenderer(r_cells); 
        }
        
        
        public void printResult()
        {
                //Print results to the table
                clearTable();
                
                //Get table model
                DefaultTableModel model = (DefaultTableModel)controlPointsTable.getModel();

                //Get amount of points
                final int n_test = early_map.test_points.size();
                final int n_reference = map.reference_points.size();
                
                //Set amount of items
                final int n_rows = Math.max(n_test, n_reference);
                if (n_rows >= 0)
                        model.setRowCount(n_rows);
                
                //Print #
                for (int index = 0; index < n_rows; index++)
                        model.setValueAt(index + 1, index, 0);
                
                //Print all test points
                for (int index = 0; index < n_test; index++)
                {
                        //Format numbers to the table
                        String X_string = String.format(Locale.ROOT, "%10.3f", early_map.test_points.get(index).getX());
                        String Y_string = String.format(Locale.ROOT, "%10.3f", early_map.test_points.get(index).getY());      

                        //Print results
                        model.setValueAt(X_string, index, 1);
                        model.setValueAt(Y_string, index, 2);
                }
                
                //Print all reference points
                for (int index = 0; index < n_reference; index++)
                {
                        //Format numbers to the table  
                        String lat_string = String.format(Locale.ROOT, "%10.3f", map.reference_points.get(index).getLat());  
                        String lon_string = String.format(Locale.ROOT, "%10.3f", map.reference_points.get(index).getLon());  

                        //Print results
                        model.setValueAt(lat_string, index, 3);
                        model.setValueAt(lon_string, index, 4);
                }
                
                //Print all projected reference points
                List <Point3DCartesian> reference_points_projected = early_map.getProjectedPoints();
                if (reference_points_projected != null)
                {
                        final int n_projected = reference_points_projected.size();
                        for (int index = 0; index < n_projected; index++)
                        {
                                //Format numbers to the table
                                String X_proj_string = String.format(Locale.ROOT, "%10.3f", reference_points_projected.get(index).getX());
                                String Y_proj_string = String.format(Locale.ROOT, "%10.3f", reference_points_projected.get(index).getY());      
                                String dX_string = String.format(Locale.ROOT, "%10.3f", (early_map.test_points.get(index).getX() - reference_points_projected.get(index).getX()));
                                String dY_string = String.format(Locale.ROOT, "%10.3f", (early_map.test_points.get(index).getY() - reference_points_projected.get(index).getY()));   

                                //Print results
                                model.setValueAt(X_proj_string, index, 5);
                                model.setValueAt(Y_proj_string, index, 6);
                                model.setValueAt(dX_string, index, 7);
                                model.setValueAt(dY_string, index, 8);
                        }
                }
        }
        
        
        public void clearTable()
        {
                //Clear content of the table
                DefaultTableModel model = (DefaultTableModel)controlPointsTable.getModel();
                
                //Clear all items
                final int n = model.getRowCount();
 
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
                }
        }
                
        
        // Variables declaration - do not modify//GEN-BEGIN:variables
        private javax.swing.JTable controlPointsTable;
        private javax.swing.JScrollPane jScrollPane1;
        // End of variables declaration//GEN-END:variables
}
