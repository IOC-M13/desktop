//Creat per Oscar Membrilla Estorach

package controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import view.JFrameAdmin;

/**
 *
 * @author Oscar
 */
public class EditDay {
    
    private DBHelper db;
    
    public EditDay() {
        db = new DBHelper();
    }
    
    public JComboBox loadShifts(JComboBox jComboBox, String day, String user) {
        
        jComboBox.addItem("--Select--");
        
        try {
            
            db.connectDB(Support.IP, Support.port);
            ResultSet rs = db.getShifts();
            
            // Iterar por los resultados de los resultSet para ir agregado items al combobox
            while (rs.next()) {
                jComboBox.addItem(rs.getString(1));
            }
            
            java.sql.Date sqlDate = stringDateToSQLdate(day);
            ResultSet rs2 = db.userWithShiftAssigned(user, sqlDate);
            
            if (rs2.first()) {
                //Hacer update
                
            } else {
                
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(JFrameAdmin.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            db.closeDB();
        }
        
        return jComboBox;
        
    }
    
    public void save (String day, String user, String shift) {
        
        db.connectDB(Support.IP, Support.port);
        
        java.sql.Date sqlDate = stringDateToSQLdate(day);
        
        //Asignar un turno a un usuario
        db.assignShiftToUser(sqlDate, user, shift);
        
        
    }
    
    private java.sql.Date stringDateToSQLdate(String day) {
        
        java.sql.Date sqlDate = null;
        
        try {
            
            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date date = sdf1.parse(day);
            sqlDate = new java.sql.Date(date.getTime());  

        } catch (ParseException ex) {
            Logger.getLogger(EditDay.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return sqlDate;
    }
    
}
