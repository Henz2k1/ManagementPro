/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package microfinance;

import java.awt.Color;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import net.proteanit.sql.DbUtils;

/**
 *
 * @author DC
 */
public class Compte extends javax.swing.JPanel {

    /**
     * Creates new form Compte
     */
    public Compte() {
        initComponents();
        initComponents();
        identifiant.setEditable(false);
        refClient.setEditable(false);
        refTypeCompte.setEditable(false);
        idTypeCompte.setEditable(false);
        idRefClient.setEditable(false);
        Afficher();
        ChargerComboBox1();
        ChargerComboBox2();
        getID1();
        getID2();
        
         listeComptes.setRowHeight(25);
        listeComptes.setShowGrid(true);
        listeComptes.setGridColor(new Color(102,102,102));
        listeComptes.setSelectionBackground(new Color(0, 108, 155));
    }

    
    void ChargerComboBox1() {
        try {

           Connection con=DriverManager.getConnection(Connect.getConnexion());

            String sql = "select noms from Client ";
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                refClient.addItem(rs.getString("noms"));
            }
            con.close();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e);
        }
    }

    void ChargerComboBox2() {
        try {

           Connection con=DriverManager.getConnection(Connect.getConnexion());

            String sql = "select designation from Type_compte ";
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                refTypeCompte.addItem(rs.getString("designation"));
            }
            con.close();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e);
        }
    }
        
    void getID1() {
        try {
            Connection con=DriverManager.getConnection(Connect.getConnexion());

            String sql = "select id from Client where noms = '" + refClient.getSelectedItem() + "'";
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                String id = rs.getString("id");
                idRefClient.setText(id);
            }
            con.close();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e);
        }
    }
    
    void getID2() {
        try {           
            Connection con=DriverManager.getConnection(Connect.getConnexion());    
           
            String sql = "select id from Type_compte where designation = '" + refTypeCompte.getSelectedItem() + "'";
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                String id = rs.getString("id");               
                idTypeCompte.setText(id);
            } 
            con.close();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e);
        }
    }
    
    
    
    
    void Inserer()
    {
         try
        {
         Connection con=DriverManager.getConnection(Connect.getConnexion());
            String sql="insert into Compte (refClient,refTypeCompte) values (?,?)";
            PreparedStatement pst=con.prepareStatement(sql);
            pst.setInt(1, Integer.parseInt(idRefClient.getText().toString()));
            pst.setInt(2, Integer.parseInt(idTypeCompte.getText().toString()));
            pst.executeUpdate();
            con.close();
            JOptionPane.showMessageDialog(null, "Enregistrement reussi");
            Afficher();
        }        
        catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Echec d'enregistrement"+ex.toString());
        }
    }
    
    void Modifier()
    {
        try
        {
            Connection con=DriverManager.getConnection(Connect.getConnexion());
            String sql="update Compte set refClient=?,refTypeCompte=? where id=?";
            PreparedStatement pst=con.prepareStatement(sql);
            pst.setInt(1, Integer.parseInt(idRefClient.getText().toString()));
            pst.setInt(2, Integer.parseInt(idTypeCompte.getText().toString()));
            pst.setInt(3, Integer.parseInt(identifiant.getText()));
            pst.executeUpdate();
            con.close();
            JOptionPane.showMessageDialog(null, "Modification reussi");
            Afficher();
        }        
        catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Echec de modification"+ex.toString());
        }
    }
    
    void Supprimer()
    {
        try
        {
           Connection con=DriverManager.getConnection(Connect.getConnexion());
            String sql="delete from Compte where id=?";
            PreparedStatement pst=con.prepareStatement(sql);
            pst.setInt(1, Integer.parseInt(identifiant.getText()));
            pst.executeUpdate();
            con.close();
            JOptionPane.showMessageDialog(null, "Suppression reussi");
            Afficher();
        }        
        catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Echec de suppression"+ex.toString());
        }
    }
    
    
    void Afficher() {
        try {
             Connection con=DriverManager.getConnection(Connect.getConnexion());
            String sql = "select * from show_compte ";
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            listeComptes.setModel(DbUtils.resultSetToTableModel(rs));

            con.close();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Echec de chargement" + ex.toString());
        }
    }
    
    
    void selection_modifier() {
        try {

            int row = listeComptes.getSelectedRow();
            String Table_click = (listeComptes.getModel().getValueAt(row, 0).toString());
            Connection con=DriverManager.getConnection(Connect.getConnexion());
            
            String sql = "select * from show_compte where id = '" + Table_click + "'";
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                 String id = rs.getString("id");
                 String refClients=rs.getString("refClient");
                  String refType=rs.getString("refTypeCompte");
               identifiant.setText(id);
               refClient.setSelectedItem(refClients);
               idRefClient.setText(refClients);
               refTypeCompte.setSelectedItem(refType);
               idTypeCompte.setText(refType);
            } 
            con.close();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e);
        }
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        acceuil = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        identifiant = new javax.swing.JTextField();
        jLabel42 = new javax.swing.JLabel();
        jLabel57 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        TypeCompte = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        refTypeCompte = new javax.swing.JComboBox();
        refClient = new javax.swing.JComboBox();
        idRefClient = new javax.swing.JTextField();
        idTypeCompte = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        sc = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        listeComptes = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();

        acceuil.setBackground(new java.awt.Color(255, 255, 255));
        acceuil.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(204, 204, 204));

        identifiant.setEditable(false);
        identifiant.setEnabled(false);
        identifiant.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                identifiantjTextField2ActionPerformed(evt);
            }
        });

        jLabel42.setFont(new java.awt.Font("Avian", 0, 12)); // NOI18N
        jLabel42.setText("ID");

        jLabel57.setFont(new java.awt.Font("Avian", 0, 12)); // NOI18N
        jLabel57.setText("RefClient");

        jLabel12.setFont(new java.awt.Font("Century Gothic", 1, 11)); // NOI18N
        jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/add_32.png"))); // NOI18N
        jLabel12.setText("Ajouter");
        jLabel12.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel12MouseClicked(evt);
            }
        });

        jLabel13.setFont(new java.awt.Font("Century Gothic", 1, 11)); // NOI18N
        jLabel13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/modify_32.png"))); // NOI18N
        jLabel13.setText("Modifier");
        jLabel13.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel13MouseClicked(evt);
            }
        });

        TypeCompte.setFont(new java.awt.Font("Avian", 0, 12)); // NOI18N
        TypeCompte.setText("RefTypeCompte");

        jLabel14.setFont(new java.awt.Font("Century Gothic", 1, 11)); // NOI18N
        jLabel14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/delete_32.png"))); // NOI18N
        jLabel14.setText("Supprimer");
        jLabel14.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel14MouseClicked(evt);
            }
        });

        jLabel15.setFont(new java.awt.Font("Century Gothic", 1, 11)); // NOI18N
        jLabel15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/refresh_32.png"))); // NOI18N
        jLabel15.setText("Actualiser");
        jLabel15.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel15MouseClicked(evt);
            }
        });

        refTypeCompte.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                refTypeCompteItemStateChanged(evt);
            }
        });

        refClient.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                refClientItemStateChanged(evt);
            }
        });

        idRefClient.setEditable(false);
        idRefClient.setEnabled(false);
        idRefClient.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                idRefClientMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                idRefClientMouseEntered(evt);
            }
        });
        idRefClient.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                idRefClientjTextField2ActionPerformed(evt);
            }
        });
        idRefClient.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                idRefClientKeyReleased(evt);
            }
        });

        idTypeCompte.setEditable(false);
        idTypeCompte.setEnabled(false);
        idTypeCompte.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                idTypeCompteMouseClicked(evt);
            }
        });
        idTypeCompte.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                idTypeComptejTextField2ActionPerformed(evt);
            }
        });
        idTypeCompte.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                idTypeCompteKeyReleased(evt);
            }
        });

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/search1_32.png"))); // NOI18N
        jLabel4.setText("Search");

        sc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                scjTextField2ActionPerformed(evt);
            }
        });
        sc.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                scKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel12)
                        .addGap(25, 25, 25)
                        .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(25, 25, 25)
                        .addComponent(jLabel15)
                        .addGap(25, 25, 25)
                        .addComponent(jLabel14)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 134, Short.MAX_VALUE)
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(sc, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel42, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel57))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(identifiant, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(105, 105, 105)
                                .addComponent(TypeCompte, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(refTypeCompte, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(idTypeCompte, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(refClient, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(idRefClient, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap())))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel42, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(TypeCompte, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(identifiant, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(refTypeCompte, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(idTypeCompte, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel57)
                    .addComponent(refClient, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(idRefClient, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 33, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(25, 25, 25))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(sc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        acceuil.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 810, 190));

        listeComptes.setBackground(new java.awt.Color(204, 204, 204));
        listeComptes.setFont(new java.awt.Font("Aeroportal", 0, 12)); // NOI18N
        listeComptes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        listeComptes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                listeComptesMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(listeComptes);

        acceuil.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 250, 810, 290));

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/close_32.png"))); // NOI18N
        jLabel2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel2MouseClicked(evt);
            }
        });
        acceuil.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(780, 10, 40, 30));

        jLabel3.setFont(new java.awt.Font("Century Gothic", 1, 24)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("COMPTE");
        acceuil.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 110, 30));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/back2.jpg"))); // NOI18N
        jLabel1.setToolTipText("");
        acceuil.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 830, 550));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 830, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(acceuil, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 550, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(acceuil, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void identifiantjTextField2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_identifiantjTextField2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_identifiantjTextField2ActionPerformed

    private void jLabel12MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel12MouseClicked
        Inserer();        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel12MouseClicked

    private void jLabel13MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel13MouseClicked
        Modifier();        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel13MouseClicked

    private void jLabel15MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel15MouseClicked
        identifiant.setText("");
        idRefClient.setText("");
        idTypeCompte.setText("");
    }//GEN-LAST:event_jLabel15MouseClicked

    private void idRefClientjTextField2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_idRefClientjTextField2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_idRefClientjTextField2ActionPerformed

    private void idTypeComptejTextField2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_idTypeComptejTextField2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_idTypeComptejTextField2ActionPerformed

    private void listeComptesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_listeComptesMouseClicked
selection_modifier();        // TODO add your handling code here:
    }//GEN-LAST:event_listeComptesMouseClicked

    private void idTypeCompteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_idTypeCompteMouseClicked
getID2();        // TODO add your handling code here:
    }//GEN-LAST:event_idTypeCompteMouseClicked

    private void idRefClientMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_idRefClientMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_idRefClientMouseEntered

    private void idRefClientMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_idRefClientMouseClicked
getID1();        // TODO add your handling code here:
    }//GEN-LAST:event_idRefClientMouseClicked

    private void jLabel2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel2MouseClicked
System.exit(0);        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel2MouseClicked

    private void scjTextField2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_scjTextField2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_scjTextField2ActionPerformed

    private void scKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_scKeyReleased
        DefaultTableModel table = (DefaultTableModel)listeComptes.getModel();
        String search = sc.getText().toLowerCase();
        TableRowSorter<DefaultTableModel> tr = new TableRowSorter<DefaultTableModel>(table);
        listeComptes.setRowSorter(tr);
        tr.setRowFilter(RowFilter.regexFilter(search));
    }//GEN-LAST:event_scKeyReleased

    private void jLabel14MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel14MouseClicked
Supprimer();          // TODO add your handling code here:
    }//GEN-LAST:event_jLabel14MouseClicked

    private void idTypeCompteKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_idTypeCompteKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_idTypeCompteKeyReleased

    private void idRefClientKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_idRefClientKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_idRefClientKeyReleased

    private void refClientItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_refClientItemStateChanged
getID1();        // TODO add your handling code here:
    }//GEN-LAST:event_refClientItemStateChanged

    private void refTypeCompteItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_refTypeCompteItemStateChanged
getID2();        // TODO add your handling code here:
    }//GEN-LAST:event_refTypeCompteItemStateChanged


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel TypeCompte;
    private javax.swing.JPanel acceuil;
    private javax.swing.JTextField idRefClient;
    private javax.swing.JTextField idTypeCompte;
    private javax.swing.JTextField identifiant;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel57;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable listeComptes;
    private javax.swing.JComboBox refClient;
    private javax.swing.JComboBox refTypeCompte;
    private javax.swing.JTextField sc;
    // End of variables declaration//GEN-END:variables
}
