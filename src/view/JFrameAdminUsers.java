/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import controller.Admin;
import controller.AdminUsers;

/**
 *
 * @author Oscar
 */
public class JFrameAdminUsers extends javax.swing.JFrame {

    private static JFrameAdminUsers instance;
    
    private AdminUsers controller;
    
    //Utilizar el patrón Singleton, para obtener una sola instancia de JFrameAdminUsers
    public static JFrameAdminUsers getInstance(Admin controllerAdmin) {
        if (instance == null) {
            instance = new JFrameAdminUsers(controllerAdmin);
        }
        return instance;
    }
    
    /**
     * Creates new form JFrameAdminUsers
     */
    private JFrameAdminUsers(Admin controllerAdmin) {
        
        
        initComponents();
        
        controller = new AdminUsers(controllerAdmin,
                                    this,
                                    textAddName, textAddDni, textAddRealName, passAddPass, checkAddIsAdmin,
                                    comboEditName, textEditDni, textEditRealName, passEditPass, checkEditIsAdmin, btnEditClearAll, btnEditSave, btnEditDel);
        
        //jTabbedPane1.setIconAt(0, new ImageIcon("icons" + File.separator + "edit.png"));
        //jTabbedPane1.setTitleAt(0, "Users");
        
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanelAddUser = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        textAddName = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        textAddRealName = new javax.swing.JTextField();
        passAddPass = new javax.swing.JPasswordField();
        checkAddIsAdmin = new javax.swing.JCheckBox();
        btnAddAddUser = new javax.swing.JButton();
        btnAddClearAll = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        textAddDni = new javax.swing.JTextField();
        jPanelEditUser = new javax.swing.JPanel();
        comboEditName = new javax.swing.JComboBox();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        checkEditIsAdmin = new javax.swing.JCheckBox();
        textEditDni = new javax.swing.JTextField();
        textEditRealName = new javax.swing.JTextField();
        btnEditSave = new javax.swing.JButton();
        btnEditDel = new javax.swing.JButton();
        btnEditClearAll = new javax.swing.JButton();
        passEditPass = new javax.swing.JPasswordField();
        jLabel9 = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Manage Users");
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jTabbedPane1.setDoubleBuffered(true);
        jTabbedPane1.setName(""); // NOI18N

        jPanelAddUser.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jPanelAddUser.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                jPanelAddUserComponentShown(evt);
            }
        });

        jLabel1.setText("User Name:");

        jLabel2.setText("Real Name:");

        jLabel3.setText("Password:");

        checkAddIsAdmin.setText("User admin?");

        btnAddAddUser.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/icons/addUser.png"))); // NOI18N
        btnAddAddUser.setText("Add user");
        btnAddAddUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddAddUserActionPerformed(evt);
            }
        });

        btnAddClearAll.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/icons/clearAll.png"))); // NOI18N
        btnAddClearAll.setText("Clear all");
        btnAddClearAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddClearAllActionPerformed(evt);
            }
        });

        jLabel8.setText("User DNI:");

        javax.swing.GroupLayout jPanelAddUserLayout = new javax.swing.GroupLayout(jPanelAddUser);
        jPanelAddUser.setLayout(jPanelAddUserLayout);
        jPanelAddUserLayout.setHorizontalGroup(
            jPanelAddUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelAddUserLayout.createSequentialGroup()
                .addGroup(jPanelAddUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelAddUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanelAddUserLayout.createSequentialGroup()
                            .addGap(20, 20, 20)
                            .addComponent(btnAddClearAll)
                            .addGap(18, 18, 18)
                            .addComponent(btnAddAddUser))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelAddUserLayout.createSequentialGroup()
                            .addGap(23, 23, 23)
                            .addGroup(jPanelAddUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(jPanelAddUserLayout.createSequentialGroup()
                                    .addComponent(jLabel8)
                                    .addGap(18, 18, 18)
                                    .addComponent(textAddDni, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanelAddUserLayout.createSequentialGroup()
                                    .addGroup(jPanelAddUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addGroup(jPanelAddUserLayout.createSequentialGroup()
                                            .addComponent(jLabel3)
                                            .addGap(19, 19, 19))
                                        .addGroup(jPanelAddUserLayout.createSequentialGroup()
                                            .addComponent(jLabel2)
                                            .addGap(18, 18, 18)))
                                    .addGroup(jPanelAddUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(passAddPass, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(textAddRealName, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGroup(jPanelAddUserLayout.createSequentialGroup()
                                    .addComponent(jLabel1)
                                    .addGap(18, 18, 18)
                                    .addComponent(textAddName, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGap(14, 14, 14)))
                    .addGroup(jPanelAddUserLayout.createSequentialGroup()
                        .addGap(56, 56, 56)
                        .addComponent(checkAddIsAdmin)))
                .addContainerGap(117, Short.MAX_VALUE))
        );
        jPanelAddUserLayout.setVerticalGroup(
            jPanelAddUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelAddUserLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanelAddUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(textAddName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanelAddUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(textAddDni, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanelAddUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(textAddRealName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addGroup(jPanelAddUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(passAddPass, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(checkAddIsAdmin)
                .addGap(18, 18, 18)
                .addGroup(jPanelAddUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAddAddUser)
                    .addComponent(btnAddClearAll))
                .addGap(63, 63, 63))
        );

        jTabbedPane1.addTab("Add User ", new javax.swing.ImageIcon(getClass().getResource("/resources/icons/addUsers.png")), jPanelAddUser); // NOI18N

        jPanelEditUser.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                jPanelEditUserComponentShown(evt);
            }
        });

        comboEditName.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                comboEditNameItemStateChanged(evt);
            }
        });
        comboEditName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboEditNameActionPerformed(evt);
            }
        });

        jLabel4.setText("User:");

        jLabel5.setText("User DNI:");

        jLabel6.setText("Real name:");

        jLabel7.setText("Password:");

        checkEditIsAdmin.setText("User admin?");

        btnEditSave.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/icons/save.png"))); // NOI18N
        btnEditSave.setText("Save");
        btnEditSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditSaveActionPerformed(evt);
            }
        });

        btnEditDel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/icons/delete.png"))); // NOI18N
        btnEditDel.setText("Delete");
        btnEditDel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditDelActionPerformed(evt);
            }
        });

        btnEditClearAll.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/icons/clearAll.png"))); // NOI18N
        btnEditClearAll.setText("Clear all");
        btnEditClearAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditClearAllActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanelEditUserLayout = new javax.swing.GroupLayout(jPanelEditUser);
        jPanelEditUser.setLayout(jPanelEditUserLayout);
        jPanelEditUserLayout.setHorizontalGroup(
            jPanelEditUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelEditUserLayout.createSequentialGroup()
                .addGroup(jPanelEditUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelEditUserLayout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(btnEditClearAll)
                        .addGap(18, 18, 18)
                        .addComponent(btnEditSave))
                    .addGroup(jPanelEditUserLayout.createSequentialGroup()
                        .addGap(65, 65, 65)
                        .addComponent(checkEditIsAdmin))
                    .addGroup(jPanelEditUserLayout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addGroup(jPanelEditUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel6)
                            .addComponent(jLabel5)
                            .addComponent(jLabel4)
                            .addComponent(jLabel7))
                        .addGap(18, 18, 18)
                        .addGroup(jPanelEditUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanelEditUserLayout.createSequentialGroup()
                                .addGroup(jPanelEditUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(comboEditName, 0, 140, Short.MAX_VALUE)
                                    .addComponent(textEditDni))
                                .addGap(18, 18, 18)
                                .addComponent(btnEditDel))
                            .addGroup(jPanelEditUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(passEditPass, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(textEditRealName, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 140, Short.MAX_VALUE)))))
                .addContainerGap(20, Short.MAX_VALUE))
        );
        jPanelEditUserLayout.setVerticalGroup(
            jPanelEditUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelEditUserLayout.createSequentialGroup()
                .addGroup(jPanelEditUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelEditUserLayout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addGroup(jPanelEditUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(comboEditName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4)))
                    .addGroup(jPanelEditUserLayout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addComponent(btnEditDel)))
                .addGap(18, 18, 18)
                .addGroup(jPanelEditUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(textEditDni, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanelEditUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(textEditRealName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanelEditUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(passEditPass, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(checkEditIsAdmin)
                .addGap(18, 18, 18)
                .addGroup(jPanelEditUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnEditSave)
                    .addComponent(btnEditClearAll))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Edit User", new javax.swing.ImageIcon(getClass().getResource("/resources/icons/edit.png")), jPanelEditUser); // NOI18N

        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/icons/jFrameAdminUsers.png"))); // NOI18N

        jMenu1.setText("File");

        jMenuItem1.setText("Close");
        jMenu1.add(jMenuItem1);

        jMenuBar1.add(jMenu1);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(136, 136, 136)
                .addComponent(jLabel9)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 319, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAddClearAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddClearAllActionPerformed
        controller.clearAll();        
    }//GEN-LAST:event_btnAddClearAllActionPerformed

    private void btnAddAddUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddAddUserActionPerformed
        controller.addUser();
    }//GEN-LAST:event_btnAddAddUserActionPerformed

    private void jPanelEditUserComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_jPanelEditUserComponentShown
        controller.loadUsersInComboBox();
    }//GEN-LAST:event_jPanelEditUserComponentShown

    private void comboEditNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboEditNameActionPerformed
        controller.loadUserDataInComponents();
    }//GEN-LAST:event_comboEditNameActionPerformed

    private void comboEditNameItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_comboEditNameItemStateChanged
        // TODO add your handling code here:
        
    }//GEN-LAST:event_comboEditNameItemStateChanged

    private void jPanelAddUserComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_jPanelAddUserComponentShown
        controller.changePanel();
    }//GEN-LAST:event_jPanelAddUserComponentShown

    private void btnEditClearAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditClearAllActionPerformed
        controller.loadUserDataInComponents();
    }//GEN-LAST:event_btnEditClearAllActionPerformed

    private void btnEditSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditSaveActionPerformed
        controller.editUserData();
    }//GEN-LAST:event_btnEditSaveActionPerformed

    private void btnEditDelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditDelActionPerformed
        controller.deleteUser();
    }//GEN-LAST:event_btnEditDelActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        instance = null;
    }//GEN-LAST:event_formWindowClosing

    /**
     * @param args the command line arguments
     */
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
            java.util.logging.Logger.getLogger(JFrameAdminUsers.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JFrameAdminUsers.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JFrameAdminUsers.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JFrameAdminUsers.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                //new JFrameAdminUsers().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddAddUser;
    private javax.swing.JButton btnAddClearAll;
    private javax.swing.JButton btnEditClearAll;
    private javax.swing.JButton btnEditDel;
    private javax.swing.JButton btnEditSave;
    private javax.swing.JCheckBox checkAddIsAdmin;
    private javax.swing.JCheckBox checkEditIsAdmin;
    private javax.swing.JComboBox comboEditName;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JPanel jPanelAddUser;
    private javax.swing.JPanel jPanelEditUser;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JPasswordField passAddPass;
    private javax.swing.JPasswordField passEditPass;
    private javax.swing.JTextField textAddDni;
    private javax.swing.JTextField textAddName;
    private javax.swing.JTextField textAddRealName;
    private javax.swing.JTextField textEditDni;
    private javax.swing.JTextField textEditRealName;
    // End of variables declaration//GEN-END:variables
}
