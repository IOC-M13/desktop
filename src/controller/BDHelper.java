//Creat per Oscar Membrilla Estorach

package controller;

import com.mysql.jdbc.CommunicationsException;
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
            JOptionPane.showMessageDialog(null, "La IP y/o el puerto son incorrectos");
        } 
        
        return con;
    }
    
    public int login(String user, String pass) {
        
        String sentenciaSQL = "SELECT admin " + 
                              "FROM users " +
                              "WHERE userName = '" + user + 
                                    "' AND pass = '" + pass + "';";
        
        try {
            
            statement = con.createStatement();
            rs = statement.executeQuery(sentenciaSQL);
            
            if (rs.first()) {
                if (rs.getBoolean("admin") == false){
                    return 1;
                } else {
                    return 2;
                }
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(BDHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return 0;
    }
    
    public ResultSet shiftsOfUser(String user) {
        
        
        String sentenciaSQL = "SELECT * " + 
                              "FROM users " +
                              "WHERE userName = '" + user + 
                                    "' AND pass = ';";
        
        try {
            
            statement = con.createStatement();
            rs = statement.executeQuery(sentenciaSQL);
        } catch (SQLException ex) {
            return null;
        }
        return rs;
    }
    
    
}
