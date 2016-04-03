package controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import view.JFrameWorkerData;

/**
 * Controller class of JFrameWorkerData JFrame
 * @author Oscar Membrilla Estorach
 */
public class WorkerData {
    
    private JFrameWorkerData jFrame;
    private JLabel userName;
    private JPasswordField pass;
    private JTextField dni;
    private JTextField realName;
    
    private DBHelper db;
    
    /**
     * Constructor of JFrameWorkerData controller
     * @param jFrame JFrame of JFrameWorkerData
     * @author Oscar Membrilla Estorach
     */
    public WorkerData(JFrameWorkerData jFrame) {
        
        this.jFrame = jFrame;
        this.userName = jFrame.getLblUserName();
        this.pass = jFrame.getPassPass();
        this.dni = jFrame.getTextRealName();
        this.realName = realName;
        
        db = new DBHelper();
        
        userName.setText(Support.userName);
        loadPersonalData();
        
        
    }
    
    /**
     * Load user personal data on swing components of JFrameWorkerData JFrame
     * @author Oscar Membrilla Estorach
     */
    public void loadPersonalData() {
        
        db.connectDB();
        ResultSet rs = db.getUserPersonalData(Support.userName);
        
        try {
            
            rs.first();
            
            pass.setText(rs.getString(1));
            dni.setText(rs.getString(2));
            realName.setText(rs.getString(3));
            
        } catch (SQLException ex) {
            Logger.getLogger(WorkerData.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }
    
    /**
     * Try to persist the all personal user data to DB
     * @author Oscar Membrilla Estorach
     */
    public void save() {
        db.connectDB();
        
        try {
            
            String userName = this.userName.getText();
            String pass = String.valueOf(this.pass.getPassword());
            String userDni = this.dni.getText();
            String realName = this.realName.getText();
            
            db.updateUserPersonalData(userDni, userName, realName, pass);
            
            JOptionPane.showMessageDialog(null, "Your personal data has been edited successfully.");
            
            closeWindow();
            
        } catch (SQLException ex) {
            if (ex.getErrorCode() == 1062) {
                JOptionPane.showMessageDialog(null, "The dni of the user is already used by other user!");
            } else {
                System.out.println("Erro: " + ex.getMessage());
            }
        } finally {
            db.closeDB();
        }
        
    }
    
    /**
     * Close JFrame of JFrameWorker
     * @author Oscar Membrilla Estorach
     */
    public void closeWindow() {
        jFrame.dispose();
    }

}
