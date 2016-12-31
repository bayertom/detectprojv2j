// Description: List of resulted projections

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

import detectprojv2j.structures.projection.Projection;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.util.TreeMap;
import javax.swing.JFrame;
import javax.swing.table.JTableHeader;
import java.util.Locale;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import detectprojv2j.types.TResult;

public class ResultsForm extends javax.swing.JFrame {

        private final EarlyMap early_map;                                       //Reference to the early map
        private final TreeMap <Double, TResult> results;                        //List of results
        private final JFrame frame;                                             //Reference to the main frame to change the window title
        
        public ResultsForm(final EarlyMap early_map_, final TreeMap <Double, TResult> results_, final int n_rows, final JFrame frame_) 
        {
                //Initialize components
                initComponents();
                
                //Initialize pointer to the early map
                early_map = early_map_;
                
                //Initialize array for x best candidates: meridians, parallels
                results = results_;
                
                //Initialize frame
                frame = frame_;
                
                //Set rows of the table
                DefaultTableModel model = (DefaultTableModel)resultsTable.getModel();
                model.setRowCount(n_rows);
                
                //Initialize table: create header
                initializeTable();
                
                resultsTable.getSelectionModel().addListSelectionListener( new ListSelectionListener() {
                    
                        @Override
                        public void valueChanged(ListSelectionEvent event) 
                        {
                                //Index of the selected row
                                int index = resultsTable.getSelectedRow();
                                
                                //Test, whether amount of results < index
                                if ((index < results.size()) && (index >= 0))
                                {
                                        //Get item
                                        TResult res = results.get(results.keySet().toArray()[index]);

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
        }


        @SuppressWarnings("unchecked")
        // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
        private void initComponents() {

                jScrollPane1 = new javax.swing.JScrollPane();
                resultsTable = new javax.swing.JTable();
                jLabel1 = new javax.swing.JLabel();

                setTitle("List of detected projections and their properties");
                setAlwaysOnTop(true);
                setLocation(new java.awt.Point(50, 500));
                setPreferredSize(new java.awt.Dimension(1800, 400));
                setType(java.awt.Window.Type.UTILITY);

                resultsTable.setModel(new javax.swing.table.DefaultTableModel(
                        new Object [][] {
                                {null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                                {null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                                {null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                                {null, null, null, null, null, null, null, null, null, null, null, null, null, null}
                        },
                        new String [] {
                                "Title 1", "Title 2", "Title 3", "Title 4", "Title 5", "Title 6", "Title 7", "Title 8", "Title 9", "Title 10", "Title 11", "Title 12", "Title 13", "Title 14"
                        }
                ) {
                        boolean[] canEdit = new boolean [] {
                                false, false, false, false, false, false, false, false, false, false, false, false, false, false
                        };

                        public boolean isCellEditable(int rowIndex, int columnIndex) {
                                return canEdit [columnIndex];
                        }
                });
                resultsTable.setToolTipText("List of determined projections sorted according to the residuals.. Click on the row to see the reconstructed meridians/parallels, points.");
                resultsTable.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
                jScrollPane1.setViewportView(resultsTable);

                jLabel1.setText("* Map  scale refers to the image resolution of 300 DPI. ");

                javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
                getContentPane().setLayout(layout);
                layout.setHorizontalGroup(
                        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1800, Short.MAX_VALUE)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 281, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
                );
                layout.setVerticalGroup(
                        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 363, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel1)
                                .addGap(6, 6, 6))
                );

                pack();
        }// </editor-fold>//GEN-END:initComponents

        
        private void initializeTable()
        {
                //Set properties of the table
                String[] col_names = { "#", "Family", "Projection", "Residuals", "R", "latk", "lonk", "lat1", "lat2", "lon0", "k", "Map scale*", "Map rotation", "Iterations"};
                DefaultTableModel model = (DefaultTableModel)resultsTable.getModel();
                model.setColumnIdentifiers(col_names);
                
                //Set center alignment for the header
                JTableHeader header = resultsTable.getTableHeader();
                DefaultTableCellRenderer r_header = (DefaultTableCellRenderer)header.getDefaultRenderer();
                r_header.setHorizontalAlignment(SwingConstants.CENTER);
                
                //Create renderer for the remaining part  of the table
                DefaultTableCellRenderer r_cells = new DefaultTableCellRenderer();
                r_cells.setHorizontalAlignment(SwingConstants.CENTER);
                
                //Set center alignment for each column of the table
                for (int i = 0; i < col_names.length; i++)
                        resultsTable.getColumnModel().getColumn(i).setCellRenderer(r_cells);
        }
        
        
        public void printResult(final int n_results)
        {
                //Print results to the table
                clearTable();
                
                DefaultTableModel model = (DefaultTableModel)resultsTable.getModel();
                
                //Set amount of results
                model.setRowCount(results.size());
                  
                //Print all candidates
                int index = 0;
                final double r300 = 300.0 / 25.4 * 1000;                //Earth radius and map scale refer to the image resolution of 300 DPI
                
                for (final java.util.Map.Entry <Double, TResult> resuls_item : results.entrySet())
                {
                        //Get residuals and projection
                        double fx= resuls_item.getKey();
                        TResult result =  resuls_item.getValue();
                        Projection proj = result.proj;
                        
                        //Format numbers to the table
                        String fx_string = String.format(Locale.ROOT, "%10.3e", fx);
                        String R_string = String.format(Locale.ROOT, "%12.3f", proj.getR());    
                        String latp_string = String.format(Locale.ROOT, "%7.2f", proj.getCartPole().getLat());  
                        String lonp_string = String.format(Locale.ROOT, "%7.2f", proj.getCartPole().getLon());   
                        String lat1_string = String.format(Locale.ROOT, "%7.2f", proj.getLat1());       
                        String lat2_string = String.format(Locale.ROOT, "%7.2f", proj.getLat2());    
                        String lon0_string = String.format(Locale.ROOT, "%7.2f", proj.getLon0());  
                        String c_string = String.format(Locale.ROOT, "%7.2f", proj.getC());   
                        String map_scale_string = String.format(Locale.ROOT, "%15.1f", result.map_scale * r300);     
                        String map_rotation_string = String.format(Locale.ROOT, "%10.2f", result.map_rotation);    
                        String iterations_string = String.format(Locale.ROOT, "%10d", result.iterations);

                        //Print results
                        model.setValueAt(index + 1, index, 0);
                        model.setValueAt(proj.getFamily(), index, 1);
                        model.setValueAt(proj.getName(), index, 2);
                        model.setValueAt(fx_string, index, 3);
                        model.setValueAt(R_string, index, 4);
                        model.setValueAt(latp_string, index, 5);
                        model.setValueAt(lonp_string, index, 6);
                        model.setValueAt(lat1_string, index, 7);
                        model.setValueAt(lat2_string, index, 8);
                        model.setValueAt(lon0_string, index, 9);
                        model.setValueAt(c_string, index, 10);
                        model.setValueAt(map_scale_string, index, 11);
                        model.setValueAt(map_rotation_string, index, 12);
                        model.setValueAt(iterations_string, index, 13);
                        
                        index++;
                }
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
                }
        }

        // Variables declaration - do not modify//GEN-BEGIN:variables
        private javax.swing.JLabel jLabel1;
        private javax.swing.JScrollPane jScrollPane1;
        private javax.swing.JTable resultsTable;
        // End of variables declaration//GEN-END:variables
}
