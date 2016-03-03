//Creat per Oscar Membrilla Estorach

package prova;

import java.sql.*;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

/**
 *
 * @author Oscar
 */
public class BDHelper {
    
    Connection con = null;
    Statement statement = null;
    ResultSet rs = null;
    
    public Connection connectDB(String ip, String port, String user, String pass) {
        
        try {
            
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://" + ip + ":" + port + "/dbchronoschedule", "standard", "1234");
            
        } catch(ClassNotFoundException | SQLException e) {
            JOptionPane.showMessageDialog(null, "Error en: " + e);
        }
        
        return con;
    }
    
    public boolean login(String user, String pass) {
        
        String sentenciaSQL = "SELECT * " + 
                              "FROM users " +
                              "WHERE userName = '" + user + 
                                    "' AND pass = '" + pass + "';";
        
        try {
            
            statement = con.createStatement();
            rs = statement.executeQuery(sentenciaSQL);
            
            if (rs.first()) {
                return true;
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(BDHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return false;
    }
    
}
