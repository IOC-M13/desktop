//Creat per Oscar Membrilla Estorach

package controller;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

/**
 *
 * @author Oscar
 */
public class DBHelper {
    
    Connection con = null;
    Statement st = null;
    ResultSet rs = null;
    
    public void connectDB() {
        
        try {
            
            Class.forName("com.mysql.jdbc.Driver");
            
            con = DriverManager.getConnection("jdbc:mysql://" + Support.IP + ":" + Support.port + "/dbChronoSchedule", "standard", "1234");
            
        } catch(ClassNotFoundException | SQLException e) {
            JOptionPane.showMessageDialog(null, "La IP y/o el puerto son incorrectos");
        } 
        
    }
    
    public int login(String user, String pass) {
        
        String query = "SELECT admin " + 
                       "FROM Users " +
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
            Logger.getLogger(DBHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return 0;
    }
    
    public ResultSet shiftsOfUserOnMonth(String user, int year, int month) {
        
        String query = "SELECT day(date), color " +
                       "FROM Shifts as s, Users as u, UserShifts as us " +
                       "WHERE s.idShift = us.idShift AND us.idUser = u.idUser " +
                            "AND month(date) = " + (month + 1) + " " +
                            "AND year(date) = " + year + " " +
                            "AND userName = '" + user + "';";
        
        try {
            
            st = con.createStatement();
            rs = st.executeQuery(query);
        } catch (SQLException ex) {
            Logger.getLogger(DBHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rs;
    }
    
    public ResultSet getUserNames() {
        
        String query = "SELECT userName " +
                       "FROM Users " +
                       "ORDER BY userName;";
        
        try {
            
            st = con.createStatement();
            rs = st.executeQuery(query);
        } catch (SQLException ex) {
            Logger.getLogger(DBHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return rs;
    }
    
    public ResultSet getShiftsName() {
        
        String query = "SELECT name " +
                       "FROM Shifts;";
        
        try {
            
            st = con.createStatement();
            rs = st.executeQuery(query);
        } catch (SQLException ex) {
            Logger.getLogger(DBHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return rs;
    }
    
    public ResultSet getNameAndColorShifts(){
        
        String query = "SELECT color, name " +
                       "FROM Shifts;";
        
        try {
            
            st = con.createStatement();
            rs = st.executeQuery(query);
        } catch (SQLException ex) {
            Logger.getLogger(DBHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return rs;
    }
    
    public void assignShiftToUser(java.sql.Date date, String userName, String shiftName) {
        
        try {
            
            //Obtener el id del usuario
            int idUser = getIdUser(userName);
            
            //Obtener el id del turno
            int idShift = getIdShift(shiftName);            
            
            // the mysql insert statement
            String query = "INSERT INTO UserShifts (idUser, idShift, date) " + 
                           "VALUES (?, ?, ?)";

            // create the mysql insert preparedstatement
            PreparedStatement preparedStmt = con.prepareStatement(query);
            preparedStmt.setInt(1, idUser);
            preparedStmt.setInt(2, idShift);
            preparedStmt.setDate(3, date);

            // execute the preparedstatement
            preparedStmt.execute();
        } catch (SQLException ex) {
            Logger.getLogger(DBHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public void updateShiftToUser(java.sql.Date date, String userName, String shiftName) {
          
        try {
            
            //Obtener el id del usuario
            int idUser = getIdUser(userName);
            
            //Obtener el id del turno
            int idShift = getIdShift(shiftName);   

            // create the java mysql update preparedstatement
            String query = "UPDATE UserShifts SET idShift = ? " + 
                           "WHERE idUser = ? AND date = ?";

            PreparedStatement preparedStmt = con.prepareStatement(query);
            preparedStmt.setInt(1, idShift);
            preparedStmt.setInt(2, idUser);
            preparedStmt.setDate(3, date);

            // execute the java preparedstatement
            preparedStmt.executeUpdate();
            
        } catch (SQLException ex) {
            Logger.getLogger(DBHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
            
    }
    
    public void deleteShiftToUser(java.sql.Date date, String userName, String shiftName) {
          
        try {
            
            //Obtener el id del usuario
            int idUser = getIdUser(userName);
            
            //Obtener el id del turno
            int idShift = getIdShift(shiftName);   

            // create the java mysql update preparedstatement
            String query = "DELETE FROM UserShifts " + 
                           "WHERE idUser = ? AND idShift = ? AND date = ?";

            PreparedStatement preparedStmt = con.prepareStatement(query);
            preparedStmt.setInt(1, idUser);
            preparedStmt.setInt(2, idShift);
            preparedStmt.setDate(3, date);

            // execute the java preparedstatement
            preparedStmt.executeUpdate();
            
        } catch (SQLException ex) {
            Logger.getLogger(DBHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
            
    }
    
    public ResultSet userWithShiftAssigned(String userName, java.sql.Date date) {
        
        //Obtener el id del usuario
        int idUser = getIdUser(userName);
        
        String query = "SELECT idShift " +
                       "FROM UserShifts " + 
                       "WHERE idUser = '" + idUser + "' AND date = '" + date + "';";
        
        try {
            
            st = con.createStatement();
            rs = st.executeQuery(query);
        } catch (SQLException ex) {
            Logger.getLogger(DBHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return rs;
    }
    
    
    public String getShiftName(int idShift) {
        
        String shiftName = null;
        
        String query = "SELECT name " +
                       "FROM Shifts " + 
                       "WHERE idShift = " + idShift + ";";
        
        try {
            
            st = con.createStatement();
            rs = st.executeQuery(query);
            
            rs.first();
            
            shiftName = rs.getString(1);
            
        } catch (SQLException ex) {
            Logger.getLogger(DBHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return shiftName;
    }
    
    public ResultSet getUserPersonalData(String userName) {
    
        String query = "SELECT pass, userDni, realName " +
                       "FROM Users " +
                       "WHERE userName = '" + userName + "';";
        
        try {
            
            st = con.createStatement();
            rs = st.executeQuery(query);
        } catch (SQLException ex) {
            Logger.getLogger(DBHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return rs;
        
    }

    private int getIdUser(String userName) {
        
        int idUser = 0;
        
        String query = "SELECT idUser " +
                       "FROM Users " +
                       "WHERE userName = '" + userName + "';";
        
        try {
            
            st = con.createStatement();
            rs = st.executeQuery(query);
            
            rs.first();
            
            idUser = rs.getInt(1);
            
        } catch (SQLException ex) {
            Logger.getLogger(DBHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return idUser;
    }
    
    private int getIdShift(String shiftName) {
        
        int idShift = 0;
        
        String query = "SELECT idShift " +
                       "FROM Shifts " +
                       "WHERE name = '" + shiftName + "';";
        
        try {
            
            st = con.createStatement();
            rs = st.executeQuery(query);
            
            rs.first();
            
            idShift = rs.getInt(1);
            
        } catch (SQLException ex) {
            Logger.getLogger(DBHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return idShift;
    }
    
    
    public ResultSet getUserByUserName(String userName) {
        
        String query = "SELECT idUser " +
                       "FROM Users " +
                       "WHERE userName = '" + userName + "';";
        
        try {
            
            st = con.createStatement();
            rs = st.executeQuery(query);
            
        } catch (SQLException ex) {
            Logger.getLogger(DBHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return rs;
    }
    
    public ResultSet getUserByUserDni(String userDni) {
        
        String query = "SELECT idUser " +
                       "FROM Users " +
                       "WHERE userDni = '" + userDni + "';";
        
        try {
            
            st = con.createStatement();
            rs = st.executeQuery(query);
            
        } catch (SQLException ex) {
            Logger.getLogger(DBHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return rs;
    }
    
    public ResultSet getUserByUserDni(String userDni, String userName) {
        
        String query = "SELECT idUser " +
                       "FROM Users " +
                       "WHERE userDni = '" + userDni + "' AND userName != '" + userName + "';";
        
        try {
            
            st = con.createStatement();
            rs = st.executeQuery(query);
            
        } catch (SQLException ex) {
            Logger.getLogger(DBHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return rs;
    }
    
    public void insertUser(String dni, String userName, String realName, String pass, boolean isAdmin) {
        
        try {
            
            // the mysql insert statement
            String query = "INSERT INTO Users (userDni, userName, realName, pass, admin) " + 
                           "VALUES (?, ?, ?, ?, ?)";

            // create the mysql insert preparedstatement
            PreparedStatement preparedStmt = con.prepareStatement(query);
            preparedStmt.setString(1, dni);
            preparedStmt.setString(2, userName);
            preparedStmt.setString(3, realName);
            preparedStmt.setString(4, pass);
            preparedStmt.setBoolean(5, isAdmin);

            // execute the preparedstatement
            preparedStmt.execute();
        } catch (SQLException ex) {
            Logger.getLogger(DBHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public ResultSet getAllDataUser(String userName) {
    
        String query = "SELECT userDni, realName, pass, admin " +
                       "FROM Users " +
                       "WHERE userName = '" + userName + "';";
        
        try {
            
            st = con.createStatement();
            rs = st.executeQuery(query);
        } catch (SQLException ex) {
            Logger.getLogger(DBHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return rs;
        
    }
    
    public void updateUserData(String userName, String userDni, String realName, String pass, boolean isAdmin) {
          
        try {

            // create the java mysql update preparedstatement
            String query = "UPDATE Users SET userDni = ?, realName = ?, pass = ?, admin = ? " + 
                           "WHERE userName = ?";

            PreparedStatement preparedStmt = con.prepareStatement(query);
            preparedStmt.setString(1, userDni);
            preparedStmt.setString(2, realName);
            preparedStmt.setString(3, pass);
            preparedStmt.setBoolean(4, isAdmin);
            preparedStmt.setString(5, userName);

            // execute the java preparedstatement
            preparedStmt.executeUpdate();
            
        } catch (SQLException ex) {
            Logger.getLogger(DBHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
            
    }
    
    public void deleteUserByUserName(String userName) {
          
        try {

            // create the java mysql update preparedstatement
            String query = "DELETE FROM Users " + 
                           "WHERE userName = ?";

            PreparedStatement preparedStmt = con.prepareStatement(query);
            preparedStmt.setString(1, userName);

            // execute the java preparedstatement
            preparedStmt.executeUpdate();
            
        } catch (SQLException ex) {
            Logger.getLogger(DBHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
            
    }
    
    public void insertShift(String name, String startTime, String endTime, String color) throws SQLException {
            
            // the mysql insert statement
            String query = "INSERT INTO Shifts (name, startTime, endTime, color) " + 
                           "VALUES (?, ?, ?, ?)";

            // create the mysql insert preparedstatement
            PreparedStatement preparedStmt = con.prepareStatement(query);
            preparedStmt.setString(1, name);
            preparedStmt.setString(2, startTime);
            preparedStmt.setString(3, endTime);
            preparedStmt.setString(4, color);

            // execute the preparedstatement
            preparedStmt.execute();
        
    }
    
    public ResultSet getAllDataShift(String name) {
    
        String query = "SELECT startTime, endTime, color " +
                       "FROM Shifts " +
                       "WHERE name = '" + name + "';";
        
        try {
            
            st = con.createStatement();
            rs = st.executeQuery(query);
        } catch (SQLException ex) {
            Logger.getLogger(DBHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return rs;
        
    }
    
    public void updateShiftData(String name, String startTime, String endTime, String color) throws SQLException {

        // create the java mysql update preparedstatement
        String query = "UPDATE Shifts SET startTime = ?, endTime = ?, color = ? " + 
                       "WHERE name = ?";

        PreparedStatement preparedStmt = con.prepareStatement(query);
        preparedStmt.setString(1, startTime);
        preparedStmt.setString(2, endTime);
        preparedStmt.setString(3, color);
        preparedStmt.setString(4, name);

        // execute the java preparedstatement
        preparedStmt.executeUpdate();      
            
    }
    
    public void deleteShiftByName(String name) throws SQLException {

        // create the java mysql update preparedstatement
        String query = "DELETE FROM Shifts " + 
                       "WHERE name = ?";

        PreparedStatement preparedStmt = con.prepareStatement(query);
        preparedStmt.setString(1, name);

        // execute the java preparedstatement
        preparedStmt.executeUpdate();
   
    }
    
    public ResultSet getShiftsTimesByName(String name) {
        String query = "SELECT startTime, endTime " +
                       "FROM Shifts " +
                       "WHERE name = '" + name + "';";
        
        try {
            
            st = con.createStatement();
            rs = st.executeQuery(query);
        } catch (SQLException ex) {
            Logger.getLogger(DBHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return rs;
    }
    
    public void updateUserPersonalData(String userDni, String userName, String realName, String pass) throws SQLException {

        // create the java mysql update preparedstatement
        String query = "UPDATE Users SET userDni = ?, realName = ?, pass = ? " + 
                       "WHERE userName = ?";

        PreparedStatement preparedStmt = con.prepareStatement(query);
        preparedStmt.setString(1, userDni);
        preparedStmt.setString(2, realName);
        preparedStmt.setString(3, pass);
        preparedStmt.setString(4, userName);

        // execute the java preparedstatement
        preparedStmt.executeUpdate();
            
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
