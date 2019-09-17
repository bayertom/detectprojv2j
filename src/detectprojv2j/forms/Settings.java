// Description: Setting parameters of the detectproj

// Copyright (c) 2017
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

import java.util.List;
import java.util.Collections;
import javax.swing.InputVerifier;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import detectprojv2j.types.TInterval;
import detectprojv2j.types.TTransformedLongitudeDirection;

import detectprojv2j.structures.point.Point3DGeographic;
import detectprojv2j.structures.projection.Projection;

import detectprojv2j.comparators.SortPointsByLat;
import detectprojv2j.comparators.SortPointsByLon;


public class Settings extends javax.swing.JDialog {

        private final List<Point3DGeographic> reference_points;                         //List of reference points (public, more comfortable access)
        private final TTransformedLongitudeDirection [] default_lon_dir;                //Default transformed longitude direction
        private final boolean [] analyze_lon0;                                          //Enable/disable lon0_trans analysis 
        
        private final boolean [] create_entire_graticule;                               //Graticule: Generate graticule over the entire analyzed territory
        private final double [] lat1_step;                                              //Graticule: latitude step between parallels, territory extent > 20 deg
        private final double [] lat2_step;                                              //Graticule: latitude step between parallels, territory extent < 20 deg
        private final double [] lat3_step;                                              //Graticule: latitude step between parallels, territory extent < 2 deg
        private final double [] lon1_step;                                              //Graticule: longitude step between parallels, territory extent > 20 deg
        private final double [] lon2_step;                                              //Graticule: longitude step between parallels, territory extent < 20 deg
        private final double [] lon3_step;                                              //Graticule: longitude step between parallels, territory extent < 2 deg
        private final double [] lat_incr;                                               //Graticule: latitude sampling step (increment) for a parallel
        private final double [] lon_incr;                                               //Graticule: longitude sampling step (increment) for a meridian
    
        private TInterval lat_extent;                                                   //Geographic extent of the analyzed territory in the latitudinal direction
        private TInterval lon_extent;                                                   //Geographic extent of the analyzed territory in the longitudinal direction

        private final List <Projection> projections;                                    //List of the analyzed projections
        
        
        public Settings(final List<Point3DGeographic> reference_points_, final TTransformedLongitudeDirection [] default_lon_dir_, final boolean [] analyze_lon0_, final boolean [] create_entire_graticule_, final double [] lat1_step_, final double [] lat2_step_, final double [] lat3_step_, 
                final double [] lon1_step_, final double [] lon2_step_, final double [] lon3_step_, final double [] lat_incr_, final double [] lon_incr_, final TInterval lat_extent_, final TInterval lon_extent_, final List <Projection> projections_) 
        {
                initComponents();
                
                reference_points = reference_points_;
                default_lon_dir = default_lon_dir_;
                analyze_lon0 = analyze_lon0_;
                create_entire_graticule = create_entire_graticule_;
                
                lat1_step = lat1_step_;
                lat2_step = lat2_step_;
                lat3_step = lat3_step_;
                lon1_step = lon1_step_;
                lon2_step = lon2_step_;
                lon3_step = lon3_step_;
                lat_incr = lat_incr_;
                lon_incr = lon_incr_;
                lat_extent = lat_extent_;
                lon_extent = lon_extent_;
                
                projections = projections_;
                
                //Input verifier: set longitude step of meridians
                InputVerifier lon_step_verifier = new InputVerifier() {
                        
                        @Override
                        public boolean verify(JComponent input) {

                                //Get the text
                                String text = ((JTextField)input).getText();

                                try
                                {
                                        //Convert to double
                                        double lon_step_trial = Double.parseDouble(text);

                                        //Too narrow interval
                                        if (((lon_extent.max_value - lon_extent.min_value) / lon_step_trial  > 50.0) && (reference_points.size() > 1))
                                        {
                                                //Show warning box
                                                JOptionPane.showMessageDialog(Settings.this, "Too small step, computation will take a long time");

                                                return false;
                                        }
                                        
                                        return true;
                                }

                                //Throw exception
                                catch (NumberFormatException e) 
                                {
                                        return false;
                                }
                        }
                };
                
                //Input verifier: set latitude step parallels
                InputVerifier lat_step_verifier = new InputVerifier() {
                        
                        @Override
                        public boolean verify(JComponent input) {

                                //Get the text
                                String text = ((JTextField)input).getText();

                                try
                                {
                                        //Convert to double
                                        double lat_step = Double.parseDouble(text);

                                        //Too narrow interval
                                        if (((lat_extent.max_value - lat_extent.min_value) / lat_step  > 50.0) && (reference_points.size() > 1))
                                        {
                                                //Show warning box
                                                JOptionPane.showMessageDialog(Settings.this, "Too small step, computation will take a long time");

                                                return false;
                                        }
                                        
                                        
                                        return true;
                                }

                                //Throw exception
                                catch (NumberFormatException e) 
                                {
                                        return false;
                                }
                        }
                };
                
                
                //Input verifier: set latitude sampling step for the meridian
                InputVerifier lat_sampling_step_verifier = new InputVerifier() {
                        
                        @Override
                        public boolean verify(JComponent input) {

                                //Get the text
                                String text = ((JTextField)input).getText();

                                try
                                {
                                        //Convert to double
                                        double lat_sampling_step = Double.parseDouble(text);

                                        //Too narrow interval
                                        if (((lat_extent.max_value - lat_extent.min_value) / lat_sampling_step  > 20000.0) && (reference_points.size() > 1))
                                        {
                                                //Show warning box
                                                JOptionPane.showMessageDialog(Settings.this, "Too small sampling step, computation will take a long time");

                                                return false;
                                        }
                                        
                                        //Too wide interval
                                        if ((lat_sampling_step  > 0.5) && (reference_points.size() > 1))
                                        {
                                                //Show warning box
                                                JOptionPane.showMessageDialog(Settings.this, "Too large step, inappropriate shape of meridians");

                                                return false;
                                        }
                                        
                                        return true;
                                }

                                //Throw exception
                                catch (NumberFormatException e) 
                                {
                                        return false;
                                }
                        }
                };
                
                
                //Input verifier: set longitude sampling step for the parallel
                InputVerifier lon_sampling_step_verifier = new InputVerifier() {
                        
                        @Override
                        public boolean verify(JComponent input) {

                                //Get the text
                                String text = ((JTextField)input).getText();

                                try
                                {
                                        //Convert to double
                                        double lon_sampling_step = Double.parseDouble(text);

                                        //Too narrow interval
                                        if (((lon_extent.max_value - lon_extent.min_value) / lon_sampling_step  > 40000.0) && (reference_points.size() > 1))
                                        {
                                                //Show warning box
                                                JOptionPane.showMessageDialog(Settings.this, "Too small step, computation will take a long time");

                                                return false;
                                        }
                                        
                                        //Too wide interval
                                        if ((lon_sampling_step  > 0.5) && (reference_points.size() > 1))
                                        {
                                                //Show warning box
                                                JOptionPane.showMessageDialog(Settings.this, "Too large step, inappropriate shape of parallels");

                                                return false;
                                        }
                                        
                                        return true;
                                }

                                //Throw exception
                                catch (NumberFormatException e) 
                                {
                                        return false;
                                }
                        }
                };
                
                //Set input verifiers
                longitudeIntervalTextField.setInputVerifier(lon_step_verifier);
                latitudeIntervalTextField.setInputVerifier(lat_step_verifier);
                meridianSamplingTextField.setInputVerifier(lat_sampling_step_verifier);
                parallelSamplingTextField.setInputVerifier(lon_sampling_step_verifier);             
        }

        @SuppressWarnings("unchecked")
        // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
        private void initComponents() {

                jButton1 = new javax.swing.JButton();
                cartographicParametersPanel = new javax.swing.JPanel();
                longitudeDirectionLabel = new javax.swing.JLabel();
                longitudeDirectionComboBox = new javax.swing.JComboBox<>();
                determineLon0CheckBox = new javax.swing.JCheckBox();
                intervalBetweenParallelsPanel = new javax.swing.JPanel();
                latitudeExtentLabel = new javax.swing.JLabel();
                latitudeOffsetLabel = new javax.swing.JLabel();
                latitudeExtentComboBox = new javax.swing.JComboBox<>();
                degLabel2 = new javax.swing.JLabel();
                latitudeIntervalTextField = new javax.swing.JTextField();
                additionalParametersPanel = new javax.swing.JPanel();
                generateGraticuleCheckBox = new javax.swing.JCheckBox();
                intervalBetweenMeridianasPanel = new javax.swing.JPanel();
                longitudeExtentLabel = new javax.swing.JLabel();
                longitudeExtentComboBox = new javax.swing.JComboBox<>();
                longitudeOffsetLabel = new javax.swing.JLabel();
                degLabel = new javax.swing.JLabel();
                longitudeIntervalTextField = new javax.swing.JTextField();
                SamplingStepsPanel = new javax.swing.JPanel();
                meridianSamplingLabel = new javax.swing.JLabel();
                latitudeIntervalLabel = new javax.swing.JLabel();
                parallelSamplingLabel = new javax.swing.JLabel();
                longitudeIntervalLabel = new javax.swing.JLabel();
                meridianSamplingTextField = new javax.swing.JTextField();
                parallelSamplingTextField = new javax.swing.JTextField();

                setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
                setTitle("Settings");
                setLocation(new java.awt.Point(800, 300));
                setModal(true);
                setResizable(false);
                setType(java.awt.Window.Type.UTILITY);
                addWindowListener(new java.awt.event.WindowAdapter() {
                        public void windowActivated(java.awt.event.WindowEvent evt) {
                                formWindowActivated(evt);
                        }
                });

                jButton1.setText("Close");
                jButton1.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                jButton1ActionPerformed(evt);
                        }
                });

                cartographicParametersPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Cartographic parameters"));

                longitudeDirectionLabel.setText("Transformed longitude direction");

                longitudeDirectionComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Mode M1 (Normal Direction)", "Mode M2 (Reversed Direction)", "Mode M3 (Normal Direction 2)", "Mode M4  (Reversed Direction 2)" }));
                longitudeDirectionComboBox.addItemListener(new java.awt.event.ItemListener() {
                        public void itemStateChanged(java.awt.event.ItemEvent evt) {
                                longitudeDirectionComboBoxItemStateChanged(evt);
                        }
                });

                determineLon0CheckBox.setText("Determine lon0' as the unknown parameter");
                determineLon0CheckBox.addItemListener(new java.awt.event.ItemListener() {
                        public void itemStateChanged(java.awt.event.ItemEvent evt) {
                                determineLon0CheckBoxItemStateChanged(evt);
                        }
                });

                javax.swing.GroupLayout cartographicParametersPanelLayout = new javax.swing.GroupLayout(cartographicParametersPanel);
                cartographicParametersPanel.setLayout(cartographicParametersPanelLayout);
                cartographicParametersPanelLayout.setHorizontalGroup(
                        cartographicParametersPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(cartographicParametersPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(cartographicParametersPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(cartographicParametersPanelLayout.createSequentialGroup()
                                                .addComponent(longitudeDirectionLabel)
                                                .addGap(40, 40, 40)
                                                .addComponent(longitudeDirectionComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addComponent(determineLon0CheckBox))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                );
                cartographicParametersPanelLayout.setVerticalGroup(
                        cartographicParametersPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(cartographicParametersPanelLayout.createSequentialGroup()
                                .addGap(21, 21, 21)
                                .addGroup(cartographicParametersPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(longitudeDirectionLabel)
                                        .addComponent(longitudeDirectionComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addComponent(determineLon0CheckBox)
                                .addContainerGap(13, Short.MAX_VALUE))
                );

                intervalBetweenParallelsPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Interval between parallels"));

                latitudeExtentLabel.setText("Latitude extent of territory");

                latitudeOffsetLabel.setText("Latitude interval");

                latitudeExtentComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { ">20 deg", ">2 deg", "<2 deg" }));
                latitudeExtentComboBox.addItemListener(new java.awt.event.ItemListener() {
                        public void itemStateChanged(java.awt.event.ItemEvent evt) {
                                latitudeExtentComboBoxItemStateChanged(evt);
                        }
                });

                degLabel2.setText("deg");

                latitudeIntervalTextField.setText("10");
                latitudeIntervalTextField.addFocusListener(new java.awt.event.FocusAdapter() {
                        public void focusLost(java.awt.event.FocusEvent evt) {
                                latitudeIntervalTextFieldFocusLost(evt);
                        }
                });

                javax.swing.GroupLayout intervalBetweenParallelsPanelLayout = new javax.swing.GroupLayout(intervalBetweenParallelsPanel);
                intervalBetweenParallelsPanel.setLayout(intervalBetweenParallelsPanelLayout);
                intervalBetweenParallelsPanelLayout.setHorizontalGroup(
                        intervalBetweenParallelsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(intervalBetweenParallelsPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(intervalBetweenParallelsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(latitudeExtentLabel)
                                        .addComponent(latitudeOffsetLabel))
                                .addGap(65, 65, 65)
                                .addGroup(intervalBetweenParallelsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(intervalBetweenParallelsPanelLayout.createSequentialGroup()
                                                .addComponent(latitudeIntervalTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(21, 21, 21)
                                                .addComponent(degLabel2))
                                        .addComponent(latitudeExtentComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                );
                intervalBetweenParallelsPanelLayout.setVerticalGroup(
                        intervalBetweenParallelsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(intervalBetweenParallelsPanelLayout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addGroup(intervalBetweenParallelsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(latitudeExtentLabel)
                                        .addComponent(latitudeExtentComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(intervalBetweenParallelsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(latitudeOffsetLabel)
                                        .addComponent(degLabel2)
                                        .addComponent(latitudeIntervalTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addContainerGap(17, Short.MAX_VALUE))
                );

                additionalParametersPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Additional parameters"));

                generateGraticuleCheckBox.setText("Generate graticule over entire planisphere");
                generateGraticuleCheckBox.addItemListener(new java.awt.event.ItemListener() {
                        public void itemStateChanged(java.awt.event.ItemEvent evt) {
                                generateGraticuleCheckBoxItemStateChanged(evt);
                        }
                });

                javax.swing.GroupLayout additionalParametersPanelLayout = new javax.swing.GroupLayout(additionalParametersPanel);
                additionalParametersPanel.setLayout(additionalParametersPanelLayout);
                additionalParametersPanelLayout.setHorizontalGroup(
                        additionalParametersPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(additionalParametersPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(generateGraticuleCheckBox, javax.swing.GroupLayout.PREFERRED_SIZE, 355, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                );
                additionalParametersPanelLayout.setVerticalGroup(
                        additionalParametersPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(additionalParametersPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(generateGraticuleCheckBox, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(15, Short.MAX_VALUE))
                );

                intervalBetweenMeridianasPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Interval between meridians"));

                longitudeExtentLabel.setText("Longitude extent of territory");

                longitudeExtentComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { ">20 deg", ">2 deg", "<2 deg" }));
                longitudeExtentComboBox.addItemListener(new java.awt.event.ItemListener() {
                        public void itemStateChanged(java.awt.event.ItemEvent evt) {
                                longitudeExtentComboBoxItemStateChanged(evt);
                        }
                });

                longitudeOffsetLabel.setText("Longitude interval");

                degLabel.setText("deg");

                longitudeIntervalTextField.setText("10");
                longitudeIntervalTextField.addFocusListener(new java.awt.event.FocusAdapter() {
                        public void focusLost(java.awt.event.FocusEvent evt) {
                                longitudeIntervalTextFieldFocusLost(evt);
                        }
                });

                javax.swing.GroupLayout intervalBetweenMeridianasPanelLayout = new javax.swing.GroupLayout(intervalBetweenMeridianasPanel);
                intervalBetweenMeridianasPanel.setLayout(intervalBetweenMeridianasPanelLayout);
                intervalBetweenMeridianasPanelLayout.setHorizontalGroup(
                        intervalBetweenMeridianasPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(intervalBetweenMeridianasPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(intervalBetweenMeridianasPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(longitudeExtentLabel)
                                        .addComponent(longitudeOffsetLabel))
                                .addGap(55, 55, 55)
                                .addGroup(intervalBetweenMeridianasPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(intervalBetweenMeridianasPanelLayout.createSequentialGroup()
                                                .addComponent(longitudeIntervalTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(21, 21, 21)
                                                .addComponent(degLabel))
                                        .addComponent(longitudeExtentComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                );
                intervalBetweenMeridianasPanelLayout.setVerticalGroup(
                        intervalBetweenMeridianasPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(intervalBetweenMeridianasPanelLayout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addGroup(intervalBetweenMeridianasPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(longitudeExtentLabel)
                                        .addComponent(longitudeExtentComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(intervalBetweenMeridianasPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(longitudeOffsetLabel)
                                        .addComponent(degLabel)
                                        .addComponent(longitudeIntervalTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addContainerGap(17, Short.MAX_VALUE))
                );

                SamplingStepsPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Meridian/parallel sampling step"));

                meridianSamplingLabel.setText("Meridian points sampling step");

                latitudeIntervalLabel.setText("x  latitude interval");

                parallelSamplingLabel.setText("Parallel points sampling step");

                longitudeIntervalLabel.setText("x  longitude interval");

                meridianSamplingTextField.setText("0.1");
                meridianSamplingTextField.addFocusListener(new java.awt.event.FocusAdapter() {
                        public void focusLost(java.awt.event.FocusEvent evt) {
                                meridianSamplingTextFieldFocusLost(evt);
                        }
                });

                parallelSamplingTextField.setText("0.1");
                parallelSamplingTextField.addFocusListener(new java.awt.event.FocusAdapter() {
                        public void focusLost(java.awt.event.FocusEvent evt) {
                                parallelSamplingTextFieldFocusLost(evt);
                        }
                });

                javax.swing.GroupLayout SamplingStepsPanelLayout = new javax.swing.GroupLayout(SamplingStepsPanel);
                SamplingStepsPanel.setLayout(SamplingStepsPanelLayout);
                SamplingStepsPanelLayout.setHorizontalGroup(
                        SamplingStepsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(SamplingStepsPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(SamplingStepsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(meridianSamplingLabel)
                                        .addComponent(parallelSamplingLabel))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(SamplingStepsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(meridianSamplingTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(parallelSamplingTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(22, 22, 22)
                                .addGroup(SamplingStepsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(longitudeIntervalLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(latitudeIntervalLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(39, 39, 39))
                );
                SamplingStepsPanelLayout.setVerticalGroup(
                        SamplingStepsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(SamplingStepsPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(SamplingStepsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(meridianSamplingLabel)
                                        .addComponent(latitudeIntervalLabel)
                                        .addComponent(meridianSamplingTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(SamplingStepsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(parallelSamplingLabel)
                                        .addComponent(longitudeIntervalLabel)
                                        .addComponent(parallelSamplingTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                );

                javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
                getContentPane().setLayout(layout);
                layout.setHorizontalGroup(
                        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(10, 10, 10)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(cartographicParametersPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(intervalBetweenMeridianasPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(intervalBetweenParallelsPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(SamplingStepsPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                        .addGroup(layout.createSequentialGroup()
                                                .addContainerGap()
                                                .addComponent(additionalParametersPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                .addContainerGap())
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(195, 195, 195))
                );
                layout.setVerticalGroup(
                        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(cartographicParametersPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(intervalBetweenMeridianasPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(intervalBetweenParallelsPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(SamplingStepsPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(additionalParametersPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(18, 18, 18)
                                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(8, 8, 8))
                );

                pack();
        }// </editor-fold>//GEN-END:initComponents

        
        private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
                //Close the setting window
                this.setVisible(false);
        }//GEN-LAST:event_jButton1ActionPerformed

        
        private void longitudeDirectionComboBoxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_longitudeDirectionComboBoxItemStateChanged
                //Set transformed longitude direction
                default_lon_dir[0] = TTransformedLongitudeDirection.values()[longitudeDirectionComboBox.getSelectedIndex() + 1];
        }//GEN-LAST:event_longitudeDirectionComboBoxItemStateChanged

        
        private void determineLon0CheckBoxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_determineLon0CheckBoxItemStateChanged
                //Set lon0 determination
                analyze_lon0[0] = determineLon0CheckBox.isSelected();
        }//GEN-LAST:event_determineLon0CheckBoxItemStateChanged

        
        private void latitudeExtentComboBoxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_latitudeExtentComboBoxItemStateChanged
                //Display latitude step between parallels
                switch (latitudeExtentComboBox.getSelectedIndex())
                {
                        case 0: latitudeIntervalTextField.setText(String.valueOf(lat1_step[0]));break;
                        case 1: latitudeIntervalTextField.setText(String.valueOf(lat2_step[0]));break;
                        case 2: latitudeIntervalTextField.setText(String.valueOf(lat3_step[0]));
                }
        }//GEN-LAST:event_latitudeExtentComboBoxItemStateChanged

        
        private void generateGraticuleCheckBoxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_generateGraticuleCheckBoxItemStateChanged
                //Set graticule contruction over the entire planisphere
                create_entire_graticule[0] = generateGraticuleCheckBox.isSelected();
        }//GEN-LAST:event_generateGraticuleCheckBoxItemStateChanged

        
        private void longitudeExtentComboBoxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_longitudeExtentComboBoxItemStateChanged
                //Display longitude step between meridians
                switch (longitudeExtentComboBox.getSelectedIndex())
                {
                        case 0: longitudeIntervalTextField.setText(String.valueOf(lon1_step[0]));break;
                        case 1: longitudeIntervalTextField.setText(String.valueOf(lon2_step[0]));break;
                        case 2: longitudeIntervalTextField.setText(String.valueOf(lon3_step[0]));
                }
        }//GEN-LAST:event_longitudeExtentComboBoxItemStateChanged

        
        private void longitudeIntervalTextFieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_longitudeIntervalTextFieldFocusLost
                //Set longitude interval between meridians
                final double lon_step_trial = Double.parseDouble(longitudeIntervalTextField.getText());
                
                switch (longitudeExtentComboBox.getSelectedIndex())
                {
                        case 0: lon1_step[0] = lon_step_trial; break;
                        case 1: lon1_step[0] = lon_step_trial; break;
                        case 2: lon1_step[0] = lon_step_trial; break;
                }
        }//GEN-LAST:event_longitudeIntervalTextFieldFocusLost

        
        private void latitudeIntervalTextFieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_latitudeIntervalTextFieldFocusLost
                //Set latitude interval between meridians
                final double lat_step_trial = Double.parseDouble(latitudeIntervalTextField.getText());
                
                //Set the step
                switch (longitudeExtentComboBox.getSelectedIndex())
                {
                        case 0: lat1_step[0] = lat_step_trial; break;
                        case 1: lat2_step[0] = lat_step_trial; break;
                        case 2: lat3_step[0] = lat_step_trial; break;
                }
        }//GEN-LAST:event_latitudeIntervalTextFieldFocusLost

        
        private void meridianSamplingTextFieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_meridianSamplingTextFieldFocusLost
                //Set latitude sampling step for the meridian
                final double lat_step_trial_ratio = Double.parseDouble(meridianSamplingTextField.getText());
                
                switch (latitudeExtentComboBox.getSelectedIndex())
                {
                        case 0: lat_incr[0] = lat_step_trial_ratio * lat1_step[0] ; break;
                        case 1: lat_incr[0] = lat_step_trial_ratio * lat2_step[0]; break;
                        case 2: lat_incr[0] = lat_step_trial_ratio * lat3_step[0]; break;
                }
        }//GEN-LAST:event_meridianSamplingTextFieldFocusLost

        
        private void parallelSamplingTextFieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_parallelSamplingTextFieldFocusLost
                //Set longitude sampling step for the parallel
                final double lon_step_trial_ratio =  Double.parseDouble(parallelSamplingTextField.getText());
                
                //Set the step
                switch (latitudeExtentComboBox.getSelectedIndex())
                {
                        case 0: lon_incr[0] = lon_step_trial_ratio * lon1_step[0] ; break;
                        case 1: lon_incr[0] = lon_step_trial_ratio * lon2_step[0]; break;
                        case 2: lon_incr[0] = lon_step_trial_ratio * lon3_step[0]; break;
                }
        }//GEN-LAST:event_parallelSamplingTextFieldFocusLost

        
        private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated

                //Geographic extent of the analyzed territory   
                if (reference_points.size() > 1)
                {
                        double lat_min = (create_entire_graticule[0] ? -89.0 : (Collections.min(reference_points, new SortPointsByLat())).getLat());
                        double lat_max = (create_entire_graticule[0] ? 89.0 : (Collections.max(reference_points, new SortPointsByLat())).getLat());
                        double lon_min = (create_entire_graticule[0] ? -180.0 : (Collections.min(reference_points, new SortPointsByLon())).getLon());
                        double lon_max = (create_entire_graticule[0] ? 180.0 : (Collections.max(reference_points, new SortPointsByLon())).getLon());

                        //Change lat/lon intervals
                        lat_extent.min_value = lat_min;
                        lat_extent.max_value = lat_max;
                        lon_extent.min_value = lon_min;
                        lon_extent.max_value = lon_max;
                }
        }//GEN-LAST:event_formWindowActivated


        // Variables declaration - do not modify//GEN-BEGIN:variables
        private javax.swing.JPanel SamplingStepsPanel;
        private javax.swing.JPanel additionalParametersPanel;
        private javax.swing.JPanel cartographicParametersPanel;
        private javax.swing.JLabel degLabel;
        private javax.swing.JLabel degLabel2;
        private javax.swing.JCheckBox determineLon0CheckBox;
        private javax.swing.JCheckBox generateGraticuleCheckBox;
        private javax.swing.JPanel intervalBetweenMeridianasPanel;
        private javax.swing.JPanel intervalBetweenParallelsPanel;
        private javax.swing.JButton jButton1;
        private javax.swing.JComboBox<String> latitudeExtentComboBox;
        private javax.swing.JLabel latitudeExtentLabel;
        private javax.swing.JLabel latitudeIntervalLabel;
        private javax.swing.JTextField latitudeIntervalTextField;
        private javax.swing.JLabel latitudeOffsetLabel;
        private javax.swing.JComboBox<String> longitudeDirectionComboBox;
        private javax.swing.JLabel longitudeDirectionLabel;
        private javax.swing.JComboBox<String> longitudeExtentComboBox;
        private javax.swing.JLabel longitudeExtentLabel;
        private javax.swing.JLabel longitudeIntervalLabel;
        private javax.swing.JTextField longitudeIntervalTextField;
        private javax.swing.JLabel longitudeOffsetLabel;
        private javax.swing.JLabel meridianSamplingLabel;
        private javax.swing.JTextField meridianSamplingTextField;
        private javax.swing.JLabel parallelSamplingLabel;
        private javax.swing.JTextField parallelSamplingTextField;
        // End of variables declaration//GEN-END:variables
}
