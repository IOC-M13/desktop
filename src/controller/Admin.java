package controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import view.JFrameAdmin;
import view.JFrameAdminShifts;
import view.JFrameAdminUsers;

/**
 * Class that contain the JFrameAdmin controller
 * @author Oscar Membrilla Estorach
 */
public class Admin {
    
    private JFrame jFrame;
    private JPanel jPanelYearMonth;
    private JPanel jPanelDays;
    private JPanel jPanelLegend;
    private JComboBox jComboUsers;
    
    private CalendarClass calendar;
    private Legend legend;
    private DBHelper db;
    
    private JFrameAdminUsers jFrameAdminUsers;
    private JFrameAdminShifts jFrameAdminShifts;
    
    
    public boolean shiftsChanged = false;
    
    public boolean usersLoadedInComboBox = false;
    
    /**
     * Constructor of JFrameAdmin controller 
     * @param jFrame The JFrame of JFrameAdmin
     * @author Oscar Membrilla Estorach
     */
    public Admin (JFrameAdmin jFrame) {
        
        this.jFrame = jFrame;
        this.jPanelYearMonth = jFrame.getJPanelYearMonth();
        this.jPanelDays = jFrame.getJPanelDays();
        this.jPanelLegend = jFrame.getJPanelLegend();
        this.jComboUsers = jFrame.getComboUsers();
        
        db = new DBHelper();
        calendar = new CalendarClass(this, jPanelYearMonth, jPanelDays);
        legend = new Legend(jPanelLegend);
        
        loadCalendar();
        loadLegend();
        
    }
    
    
    /**
     * Load the users in the JComboBox users
     * @author Oscar Membrilla Estorach
     */
    public void loadUsersInCombo() {
        
        usersLoadedInComboBox = false;
        
        jComboUsers.removeAllItems();

        // Cargar los nombres de usuarios en el combobox, mediante una query al BD
        db.connectDB();
        ResultSet rs = db.getUserNames();
        
        // Iterar por los resultados de los resultSet para ir agregado items al combobox
        try {
            while (rs.next()) {
                jComboUsers.addItem(rs.getString(1));
            }
            
            usersLoadedInComboBox = true;
            
        } catch (SQLException ex) {
            Logger.getLogger(JFrameAdmin.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            db.closeDB();
        }
        
        jComboUsers.setSelectedItem(Support.userName);
        
    }
    
    /**
     * Reload calendar when change the user selected in the users JComboBox
     * @author Oscar Membrilla Estorach
     */
    public void reloadCalendarToOtherUser() {
        
        if (usersLoadedInComboBox) {
            Support.userName = (String) jComboUsers.getSelectedItem();
            calendar.createCalendarDays();
        }
        
    }
    
    /**
     * Load all the calendar, that is load the years, months and days of calendar
     * @author Oscar Membrilla Estorach
     */
    public void loadCalendar() {
        calendar.createCalendar();
    }
    
    /**
     * Only load the days of calendar
     * @author Oscar Membrilla Estorach
     */
    public void loadCalendarDays() {
        calendar.createCalendarDays();
    }
    
    /**
     * Load the legend of shifts that exists into DB
     * @author Oscar Membrilla Estorach
     */
    public void loadLegend() {
        legend.draw();
    }
    
    /**
     * Open the JFrame with name JFrameAdminUsers
     * @author Oscar Membrilla Estorach
     */
    public void openJFrameAdminUsers() {
        jFrameAdminUsers = JFrameAdminUsers.getInstance(this);
        jFrameAdminUsers.setVisible(true);
        jFrameAdminUsers.setLocationRelativeTo(null);
        jFrameAdminUsers.setLayout(null);
    }
    
    /**
     * Open the JFrame with name JFrameAdminShifts
     * @author Oscar Membrilla Estorach
     */
    public void openJFrameAdminShifts() {
        jFrameAdminShifts = JFrameAdminShifts.getInstance(this);
        jFrameAdminShifts.setVisible(true);
        jFrameAdminShifts.setLocationRelativeTo(null);
        jFrameAdminShifts.setLayout(null);
    }
    
    /**
     * Close the application
     * @author Oscar Membrilla Estorach
     */
    public void exit() {
        System.exit(0); // Cerrar la aplicación
    }
    
    /**
     * @return the controllerAdminEditDay
     */
    public AdminEditDay getControllerAdminEditDay() {
        return calendar.getControllerAdminEditDay();
    }
    
}
