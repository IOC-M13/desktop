package controller;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

/**
 * Class for work with DB
 * @author Oscar
 */
public class DBHelper {
    
    Connection con = null;
    Statement st = null;
    ResultSet rs = null;
    
    /**
     * Method that let connect to DB with connector of MySQL JDBC Driver
     * @author Oscar Membrilla Estorach
     */
    public void connectDB() {
        
        try {
            
            Class.forName("com.mysql.jdbc.Driver");
            
            con = DriverManager.getConnection("jdbc:mysql://" + Support.IP + ":" + Support.port + "/dbChronoSchedule", "standard", "1234");
            
        } catch(ClassNotFoundException | SQLException e) {
            JOptionPane.showMessageDialog(null, "La IP y/o el puerto son incorrectos");
        } 
        
    }
    
    /**
     * Method for login into we own app
     * @param user String to user name
     * @param pass String to user password
     * @return int with indicative of state of login
     * @author Oscar Membrilla Estorach
     */
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
    
    /**
     * Method to get shifts of a user on a month (and on a year)
     * @param user String to user name
     * @param year int to year
     * @param month int to month
     * @return ResultSet with shifts of user of param month and param year
     * @author Oscar Membrilla Estorach
     */
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
    
    /**
     * Method to get all user names
     * @return ResultSet with all user names
     * @author Oscar Membrilla Estorach
     */
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
    
    /**
     * Method to get all shift names
     * @return ResultSet with all shift names
     * @author Oscar Membrilla Estorach
     */
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
    
    /**
     * Method to get color and name of all shifts
     * @return ResultSet with color and name of all shifts
     * @author Oscar Membrilla Estorach
     */
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
    
    /**
     * Method to get shifts assigned to user in a date
     * @param date java.sql.Date with a date
     * @param userName String with a user name
     * @param shiftName String with a shift name
     * @author Oscar Membrilla Estorach
     */
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
    
    /**
     * Method to update a shift assigned to a user in a date
     * @param date java.sql.Date with a date
     * @param userName String with a user name
     * @param shiftName String with a shift name
     * @author Oscar Membrilla Estorach
     */
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
    
    /**
     * Method to delete shift assigned to user in a date
     * @param date java.sql.Date with a date
     * @param userName String with a user name
     * @param shiftName String with a shift name
     * @author Oscar Membrilla Estorach
     */
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
            System.out.println("Error: " + ex.getMessage());
        }
            
    }
    
    /**
     * Method to get id shifts of shifts assigned a user in a date
     * @param userName String with a user name
     * @param date java.sql.Date with a date
     * @return ResultSet with id shifts of shifts assigned a user in a date
     * @author Oscar Membrilla Estorach
     */
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
            System.out.println("Error: " + ex.getMessage());
        }
        
        return rs;
    }
    
    /**
     * Method to get shift name from a shift id
     * @param idShift int with a shift id
     * @return String with a shift name
     * @author Oscar Membrilla Estorach
     */
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
    
    /**
     * Method to get personal data of a user name
     * @param userName String with a user name
     * @return ResultSet with a personal data of a user passed as parameter
     * @author Oscar Membrilla Estorach
     */
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

    /**
     * Method to get user id from a user name
     * @param userName String with a user name
     * @return int with a user id from a user name
     * @author Oscar Membrilla Estorach
     */
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
    
    /**
     * Method to get shift id from a shift name
     * @param shiftName String with a shift name
     * @return int with a shift id from a shift name
     * @author Oscar Membrilla Estorach
     */
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
    
    /**
     * Method to get user ids from a user name
     * @param userName String with a user name
     * @return ResultSet with a user ids from a user name passed as parameter
     * @author Oscar Membrilla Estorach
     */
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
    
    /**
     * Method to get user id from a user dni passed as parameter
     * @param userDni String with a user dni
     * @return ResultSet with a user ids from a user dni passed as parameter
     * @author Oscar Membrilla Estorach
     */
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
    
    /**
     * Method to get user id from user dni and not equal to user name passed as parameter
     * @param userDni String with a user dni
     * @param userName String with a user name
     * @return ResultSet with a user dni from user dni and not equal to user name passed as parameter
     * @author Oscar Membrilla Estorach
     */
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
    
    /**
     * Method to insert a full user
     * @param dni String with a user dni
     * @param userName String with a user name
     * @param realName String with a real name
     * @param pass String with a user pass
     * @param isAdmin boolean to know that if a user is admin or not
     * @author Oscar Membrilla Estorach
     */
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
    
    /**
     * Method to get all data user from user name passed as parameter
     * @param userName String with a user name
     * @return ResultSet with all data user from user name passed as parameter
     * @author Oscar Membrilla Estorach
     */
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
    
    /**
     * Method to update all user data
     * @param userName String with a user name
     * @param userDni String with a user dni
     * @param realName String with a real name
     * @param pass String with a user pass
     * @param isAdmin boolean to know that if a user is admin or not
     * @author Oscar Membrilla Estorach
     */
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
    
    /**
     * Method to delete user from user name
     * @param userName String with a user name
     * @author Oscar Membrilla Estorach
     */
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
    
    /**
     * Method to insert shift form all shift data
     * @param name String with a shift name
     * @param startTime String with a start time of a shift, in format "dd-mm-yyyy"
     * @param endTime String with a end time of a shift, in format "dd-mm-yyyy"
     * @param color String with a color of shift, in hexadecimal format "#123456"
     * @throws SQLException SQLException with a possible Exception
     * @author Oscar Membrilla Estorach
     */
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
    
    /**
     * Method to get all data form a shift name passed as parameter
     * @param name String with a shift name
     * @return ResultSet with all data from a shift name passed as parameter
     * @author Oscar Membrilla Estorach
     */
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
    
    /**
     * Method to update all data of a shift name passed as parameter
     * @param name String with a shift name
     * @param startTime String with a start time of a shift, in format "dd-mm-yyyy"
     * @param endTime String with a end time of a shift, in format "dd-mm-yyyy"
     * @param color String with a color of shift, in hexadecimal format "#123456"
     * @throws SQLException SQLException with a possible Exception
     * @author Oscar Membrilla Estorach
     */
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
    
    /**
     * Method to delete shift from shift name passed as parameter
     * @param name String with a shift name
     * @throws SQLException SQLException with a possible Exception
     * @author Oscar Membrilla Estorach
     */
    public void deleteShiftByName(String name) throws SQLException {

        // create the java mysql update preparedstatement
        String query = "DELETE FROM Shifts " + 
                       "WHERE name = ?";

        PreparedStatement preparedStmt = con.prepareStatement(query);
        preparedStmt.setString(1, name);

        // execute the java preparedstatement
        preparedStmt.executeUpdate();
   
    }
    
    /**
     * Method to get shift times from a shift name passed as parameter
     * @param name String with a shift name
     * @return ResultSet with a shift times form a shift name passed as parameter
     * @author Oscar Membrilla Estorach
     */
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
    
    /**
     * Method to update all personal data from user name passed as parameter
     * @param userDni String with a user dni
     * @param userName String with a user name
     * @param realName String with a real name
     * @param pass String with a user password
     * @throws SQLException SQLException with a possible Exception
     * @author Oscar Membrilla Estorach
     */
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
    
    /**
     * Method to convert java.util.Date to java.sql.Date
     * @param day String with a date, in format "dd-mm-yyyy"
     * @return java.sql.Date with a date in format java.sql.Date
     * @author Oscar Membrilla Estorach
     */
    public static java.sql.Date stringDateToSQLdate(String day) {
        
        java.sql.Date sqlDate = null;
        
        try {
            
            //SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyyy");
            java.util.Date date = sdf1.parse(day);
            sqlDate = new java.sql.Date(date.getTime());  

        } catch (ParseException ex) {
            Logger.getLogger(AdminEditDay.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return sqlDate;
    }
    
    /**
     * Method to close the Connection, ResultSet and Statement defined as attributes
     * @author Oscar Membrilla Estorach
     */
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
