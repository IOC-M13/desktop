package controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import view.JFrameAdminUsers;

/**
 * Class that contain the JFrameAdminUsers controller
 * @author Oscar Membrilla Estorach
 */
public class AdminUsers {
    
    private DBHelper db;
    
    //Referencia al controller del JFrameAdmin
    private Admin controllerAdmin;
    
    // Referencia al JFrame
    private JFrameAdminUsers jFrame;
    
    // Referencias a componentes de la pestaña de Add Users
    private JTextField addUserName;
    private JTextField addDni;
    private JTextField addRealName;
    private JPasswordField addPass;
    private JCheckBox addIsAdmin;
    
    // Referencias a componentes de la pestaña de Edit Users
    private JComboBox editUserName;
    private JTextField editDni;
    private JTextField editRealName;
    private JPasswordField editPass;
    private JCheckBox editIsAdmin;
    private JButton editClearAll;
    private JButton editSave;
    private JButton editDel;
    
    public boolean usersLoadedInComboBox = false;
    
    /**
     * Constructor of JFrameAdminUsers controller
     * @param controllerAdmin The JFrameAdmin controller
     * @param jFrame The jFrame of JFrameAdminUsers
     * @author Oscar Membrilla Estorach
     */
    public AdminUsers(Admin controllerAdmin, JFrameAdminUsers jFrame) {
        
        this.controllerAdmin = controllerAdmin;
        
        this.jFrame = jFrame;
        
        this.addUserName = jFrame.getTextAddName();
        this.addDni = jFrame.getTextAddDni();
        this.addRealName = jFrame.getTextAddRealName();
        this.addPass = jFrame.getPassAddPass();
        this.addIsAdmin = jFrame.getCheckAddIsAdmin();
        
        this.editUserName = jFrame.getComboEditName();
        this.editDni = jFrame.getTextEditDni();
        this.editRealName = jFrame.getTextEditRealName();
        this.editPass = jFrame.getPassEditPass();
        this.editIsAdmin = jFrame.getCheckEditIsAdmin();
        this.editClearAll = jFrame.getBtnEditClearAll();
        this.editSave = jFrame.getBtnEditSave();
        this.editDel = jFrame.getBtnEditDel();
        
        db = new DBHelper();
    }
    
    /**
     * Clear all the swing components in JPanel of "Add Users"
     * @author Oscar Membrilla Estorach
     */
    public void clearAll() {
        addUserName.setText("");
        addDni.setText("");
        addRealName.setText("");
        addPass.setText("");
        addIsAdmin.setSelected(false);
    }
    
    /**
     * Add a new user in DB
     * @author Oscar Membrilla Estorach
     */
    public void addUser() {
        
        db.connectDB();
        
        try {
            
            // Comprobar que no haya ningún usuario con ese mismo userName
            ResultSet rs = db.getUserByUserName(addUserName.getText());
            if (rs.first() == false) {

                // Comprobar que no haya ningún usuario con este mismo DNI
                ResultSet rs2 = db.getUserByUserDni(addUserName.getText());
                if (rs2.first() == false) {
                    
                    // Insertar el usuario en la DB
                    String dni = addDni.getText();
                    String userName = addUserName.getText();
                    String realName = addRealName.getText();
                    String pass = String.valueOf(addPass.getPassword());
                    boolean isAdmin = addIsAdmin.isSelected();
                    
                    db.insertUser(dni, userName, realName, pass, isAdmin);
                    
                    JOptionPane.showMessageDialog(null, "El usuario se ha agregado correctamente.");
                    
                    //Limpiar todos los componentes
                    clearAll();
                    
                    //Recargar los usuarios del comboBox del JFrameAdmin
                    controllerAdmin.loadUsersInCombo();
                    
                    //jFrame.dispose(); // Cerrar la ventana
                    
                } else {
                    JOptionPane.showMessageDialog(null, "Ya existe un usuario con este DNI!");
                }
                
                
            } else {
                JOptionPane.showMessageDialog(null, "El nombre de usuario ya existe. Debe escoger otro");
            }
        } catch (SQLException ex) {
            System.out.println("Error en: " + ex);
        }   
          
    }
    
    /**
     * Load users names in JComboBox users
     * @author Oscar Membrilla Estorach
     */
    public void loadUsersInComboBox() {
       
        editUserName.removeAllItems();
        
        // Cargar los nombres de usuarios en el combobox, mediante una query a la DB
        db.connectDB();
        ResultSet rs = db.getUserNames();
        
        // Iterar por los resultados de los resultSet para ir agregado items al combobox
        try {
            while (rs.next()) {
                
                editUserName.addItem(rs.getString(1));
                
                if (rs.isFirst()) {
                    editUserName.setSelectedItem(rs.getString(1));
                }     
                
            }
            
            usersLoadedInComboBox = true;
            
        } catch (SQLException ex) {
            System.out.println("Error en: " + ex);
        } finally {
            db.closeDB();
        }
        
        loadUserDataInComponents();
        
    }
    
    /**
     * Load users data in swing components of JPanel of "Edit Users"
     * @author Oscar Membrilla Estorach
     */
    public void loadUserDataInComponents() {
        
        if (usersLoadedInComboBox) {
        
            String userOfCombo = (String) editUserName.getSelectedItem();

            db.connectDB();
            ResultSet rs = db.getAllDataUser(userOfCombo);

            // Iterar por los resultados de los resultSet
            try {
                if (rs.first()) {
                    editDni.setText(rs.getString(1));
                    editRealName.setText(rs.getString(2));
                    editPass.setText(rs.getString(3));
                    editIsAdmin.setSelected(rs.getBoolean(4));   

                }


                if (userOfCombo.equals(Support.userNameLogged)) {
                    editIsAdmin.setEnabled(false);
                    editDel.setEnabled(false);
                    if (!editSave.isEnabled()) {
                        editSave.setEnabled(true);
                    }
                } else if (userOfCombo.equals("admin")) {
                    editIsAdmin.setEnabled(false);
                    editSave.setEnabled(false);
                    editDel.setEnabled(false);
                } else if (!editSave.isEnabled()) {
                    editIsAdmin.setEnabled(true);
                    editSave.setEnabled(true);
                    editDel.setEnabled(true);
                } else {
                    editIsAdmin.setEnabled(true);
                    editDel.setEnabled(true);
                }

            } catch (SQLException ex) {
                System.out.println("Error en: " + ex);
            } finally {
                db.closeDB();
            }
        }
    }
    
    /**
     * Put red semaphore "usersLoadedInComboBox", else this semaphore will always be in green
     * @author Oscar Membrilla Estorach
     */
    public void changePanel() {
        usersLoadedInComboBox = false;
    }
    
    /**
     * Edit user data in DB
     * @author Oscar Membrilla Estorach
     */
    public void editUserData() {
        
        String userName = (String) editUserName.getSelectedItem();
        String userDni = editDni.getText();
        String realName = editRealName.getText();
        String pass = String.valueOf(editPass.getPassword());
        boolean isAdmin = editIsAdmin.isSelected();
        
        
        db.connectDB();
        
        try {
            // Comprobar que no haya ningún otro usuario con este mismo DNI
            ResultSet rs2 = db.getUserByUserDni(userDni, userName);
            if (rs2.first() == false) {
                
                //Hacer update
                db.updateUserData(userName, userDni, realName, pass, isAdmin);
                JOptionPane.showMessageDialog(null, "El usuario se ha editado correctamente.");
                

            } else {
                JOptionPane.showMessageDialog(null, "Ya existe un usuario con este DNI!");
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(AdminUsers.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            db.closeDB();
        }
        
    }
    
    /**
     * Delete user in DB
     * @author Oscar Membrilla Estorach
     */
    public void deleteUser() {
        
        String comboBox = (String) editUserName.getSelectedItem();
           
        db.connectDB();

        db.deleteUserByUserName(comboBox);

        JOptionPane.showMessageDialog(null, "El usuario " + comboBox + " ha sido eliminado correctamente.");
 
        //Recargar los usuarios del comboBox del JFrameAdmin
        controllerAdmin.loadUsersInCombo();
        
        controllerAdmin.reloadCalendarToOtherUser();
        

        //Recargar el comboBox de nombres de usuarios
        usersLoadedInComboBox = false;
        loadUsersInComboBox();
    
    }
    
    /**
     * Close JFrame of JFrameAdminShifts
     * @author Oscar Membrilla Estorach
     */
    public void closeWindow() {
        jFrame.dispose();
    }

}
