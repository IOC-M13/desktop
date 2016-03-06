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
    Statement st = null;
    ResultSet rs = null;
    
    public Connection connectDB(String ip, String port) {
        
        try {
            
            Class.forName("com.mysql.jdbc.Driver");
            
            con = DriverManager.getConnection("jdbc:mysql://" + ip + ":" + port + "/dbchronoschedule", "standard", "1234");
            
        } catch(ClassNotFoundException | SQLException e) {
            JOptionPane.showMessageDialog(null, "La IP y/o el puerto son incorrectos");
        } 
        
        return con;
    }
    
    public int login(String user, String pass) {
        
        String query = "SELECT admin " + 
                       "FROM users " +
                       "WHERE userName = '" + user + 
                            "' AND pass = '" + pass + "';";
        
        try {
            
            st = con.createStatement();
            rs = st.executeQuery(query);
            
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
    
    public ResultSet shiftsOfUserOnMonth(String user, int year, int month) {
        
        String query = "SELECT day(date), color " +
                       "FROM shifts as s, users as u, usershifts as us " +
                       "WHERE s.idShift = us.idShift AND us.idUser = u.idUser " +
                            "AND month(date) = " + (month + 1) + " " +
                            "AND year(date) = " + year + " " +
                            "AND userName = '" + user + "';";
        
        try {
            
            st = con.createStatement();
            rs = st.executeQuery(query);
        } catch (SQLException ex) {
            Logger.getLogger(BDHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rs;
    }
    
    public void closeDB() {
         
        try {
            if (rs != null) {
                rs.close();
            }
            if (st != null) {
                st.close();
            }
            if (con != null) {
                con.close();
            }
        } catch (SQLException ex) {
            System.out.println("Error: " + ex);
        }
    }
    
    
}
