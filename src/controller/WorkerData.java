//Creat per Oscar Membrilla Estorach

package controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 *
 * @author Oscar
 */
public class WorkerData {
    
    public void loadPersonalData(JPasswordField pass, JTextField dni, JTextField realName) {
        
        
        DBHelper db = new DBHelper();
        
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

}
