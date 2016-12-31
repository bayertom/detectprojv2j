// Description: About box of the program

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


public class AboutBox extends javax.swing.JFrame {

        public AboutBox() {
                initComponents();
        }

        @SuppressWarnings("unchecked")
        // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
        private void initComponents() {
                java.awt.GridBagConstraints gridBagConstraints;

                textPanel = new javax.swing.JPanel();
                aboutSoftwarePanel = new javax.swing.JPanel();
                logoPanel = new javax.swing.JPanel();
                jLabel2 = new javax.swing.JLabel();
                descriptionPanel = new javax.swing.JPanel();
                titleLabel = new javax.swing.JLabel();
                descriptionLabel = new javax.swing.JLabel();
                aboutFurtherInformationPanel = new javax.swing.JPanel();
                authorLabel = new javax.swing.JLabel();
                adressLabel = new javax.swing.JLabel();
                supportedMethodsLabel = new javax.swing.JLabel();
                otherInformationLabel = new javax.swing.JLabel();
                jButton1 = new javax.swing.JButton();

                setTitle("About");
                setLocation(new java.awt.Point(800, 300));
                setPreferredSize(new java.awt.Dimension(470, 460));
                setResizable(false);
                setType(java.awt.Window.Type.UTILITY);
                getContentPane().setLayout(new java.awt.GridBagLayout());

                textPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());

                aboutSoftwarePanel.setLayout(new java.awt.GridLayout(1, 0));

                jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/detectprojv2j/resources/projection.png"))); // NOI18N

                javax.swing.GroupLayout logoPanelLayout = new javax.swing.GroupLayout(logoPanel);
                logoPanel.setLayout(logoPanelLayout);
                logoPanelLayout.setHorizontalGroup(
                        logoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(logoPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel2)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                );
                logoPanelLayout.setVerticalGroup(
                        logoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 144, Short.MAX_VALUE)
                );

                aboutSoftwarePanel.add(logoPanel);

                descriptionPanel.setLayout(new javax.swing.BoxLayout(descriptionPanel, javax.swing.BoxLayout.Y_AXIS));

                titleLabel.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
                titleLabel.setText("<html> <br> detectproj v. 1.1 </html>");
                descriptionPanel.add(titleLabel);

                descriptionLabel.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
                descriptionLabel.setText("<html><br> Estimation of the unknown projection and its parameters from a map.<br> <br> License: GNU/GPL v. 2.0 </html>");
                descriptionPanel.add(descriptionLabel);

                aboutSoftwarePanel.add(descriptionPanel);

                authorLabel.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
                authorLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
                authorLabel.setText("C 2016, Tomas Bayer");

                adressLabel.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
                adressLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
                adressLabel.setText("<html> <br> Department of Applied Geoinformatics and Cartography, <br> Faculty of Science, Charles University, Prague.<br><br> E-mail: bayertom@natur.cuni.cz</html>");

                supportedMethodsLabel.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
                supportedMethodsLabel.setText("<html>Supported detection methods: M7, M8 <br> Supported optimizations: NLS, NM, DE </html>");

                otherInformationLabel.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
                otherInformationLabel.setText("<html> Map operations based on jMapViewer library. <br> Compiler: NetBeans 8.x, 64. </html>");

                javax.swing.GroupLayout aboutFurtherInformationPanelLayout = new javax.swing.GroupLayout(aboutFurtherInformationPanel);
                aboutFurtherInformationPanel.setLayout(aboutFurtherInformationPanelLayout);
                aboutFurtherInformationPanelLayout.setHorizontalGroup(
                        aboutFurtherInformationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(aboutFurtherInformationPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(aboutFurtherInformationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(aboutFurtherInformationPanelLayout.createSequentialGroup()
                                                .addGroup(aboutFurtherInformationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(authorLabel)
                                                        .addComponent(otherInformationLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGap(0, 0, Short.MAX_VALUE))
                                        .addGroup(aboutFurtherInformationPanelLayout.createSequentialGroup()
                                                .addGroup(aboutFurtherInformationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(adressLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 414, Short.MAX_VALUE)
                                                        .addComponent(supportedMethodsLabel, javax.swing.GroupLayout.Alignment.TRAILING))
                                                .addContainerGap())))
                );
                aboutFurtherInformationPanelLayout.setVerticalGroup(
                        aboutFurtherInformationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(aboutFurtherInformationPanelLayout.createSequentialGroup()
                                .addComponent(authorLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(adressLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(supportedMethodsLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(otherInformationLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                );

                javax.swing.GroupLayout textPanelLayout = new javax.swing.GroupLayout(textPanel);
                textPanel.setLayout(textPanelLayout);
                textPanelLayout.setHorizontalGroup(
                        textPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(aboutSoftwarePanel, javax.swing.GroupLayout.PREFERRED_SIZE, 434, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(aboutFurtherInformationPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                );
                textPanelLayout.setVerticalGroup(
                        textPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(textPanelLayout.createSequentialGroup()
                                .addComponent(aboutSoftwarePanel, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(aboutFurtherInformationPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
                );

                gridBagConstraints = new java.awt.GridBagConstraints();
                gridBagConstraints.gridx = 0;
                gridBagConstraints.gridy = 0;
                gridBagConstraints.gridwidth = 2;
                gridBagConstraints.ipady = -10;
                gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
                gridBagConstraints.insets = new java.awt.Insets(11, 10, 0, 10);
                getContentPane().add(textPanel, gridBagConstraints);

                jButton1.setText("Close");
                jButton1.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                jButton1ActionPerformed(evt);
                        }
                });
                gridBagConstraints = new java.awt.GridBagConstraints();
                gridBagConstraints.gridx = 0;
                gridBagConstraints.gridy = 1;
                gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
                gridBagConstraints.insets = new java.awt.Insets(11, 196, 11, 0);
                getContentPane().add(jButton1, gridBagConstraints);

                pack();
        }// </editor-fold>//GEN-END:initComponents

        private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
                // Close form
                this.setVisible(false);
        }//GEN-LAST:event_jButton1ActionPerformed

        public static void main(String args[]) {
                /* Set the look and feel */
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
                        java.util.logging.Logger.getLogger(AboutBox.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                } catch (InstantiationException ex) {
                        java.util.logging.Logger.getLogger(AboutBox.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                } catch (IllegalAccessException ex) {
                        java.util.logging.Logger.getLogger(AboutBox.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                } catch (javax.swing.UnsupportedLookAndFeelException ex) {
                        java.util.logging.Logger.getLogger(AboutBox.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                }
                //</editor-fold>

                /* Create and display the form */
                java.awt.EventQueue.invokeLater(new Runnable() {
                        public void run() {
                                new AboutBox().setVisible(true);
                        }
                });
        }

        // Variables declaration - do not modify//GEN-BEGIN:variables
        private javax.swing.JPanel aboutFurtherInformationPanel;
        private javax.swing.JPanel aboutSoftwarePanel;
        private javax.swing.JLabel adressLabel;
        private javax.swing.JLabel authorLabel;
        private javax.swing.JLabel descriptionLabel;
        private javax.swing.JPanel descriptionPanel;
        private javax.swing.JButton jButton1;
        private javax.swing.JLabel jLabel2;
        private javax.swing.JPanel logoPanel;
        private javax.swing.JLabel otherInformationLabel;
        private javax.swing.JLabel supportedMethodsLabel;
        private javax.swing.JPanel textPanel;
        private javax.swing.JLabel titleLabel;
        // End of variables declaration//GEN-END:variables
}
