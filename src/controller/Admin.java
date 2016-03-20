//Creat per Oscar Membrilla Estorach

package controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import view.JFrameAdmin;
import view.JFrameAdminShifts;
import view.JFrameAdminUsers;

/**
 *
 * @author Oscar
 */
public class Admin {
    
    private JPanel jPanelYearMonth;
    private JPanel jPanelDays;
    private JPanel jPanelLegend;
    private JComboBox jComboUsers;
    
    private CalendarClass calendar;
    private Legend legend;
    private DBHelper db;
    
    private AdminEditDay controllerAdminEditDay;
    
    public boolean shiftsChanged = false;
    
    public boolean usersLoadedInComboBox = false;
    
    public Admin (JPanel jPanelYearMonth, JPanel jPanelDays, JPanel jPanelLegend, JComboBox jComboUsers) {
        
        this.jPanelYearMonth = jPanelYearMonth;
        this.jPanelDays = jPanelDays;
        this.jPanelLegend = jPanelLegend;
        this.jComboUsers = jComboUsers;
        
        db = new DBHelper();
        calendar = new CalendarClass(this, jPanelYearMonth, jPanelDays);
        legend = new Legend(jPanelLegend);
        
        //loadUsersInCombo();
        
        loadCalendar();
        loadLegend();
        
    }
    
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
    
    public void reloadCalendarToOtherUser() {
        
        if (usersLoadedInComboBox) {
            Support.userName = (String) jComboUsers.getSelectedItem();
            calendar.createCalendarDays();
        }
        
    }
    
    public void loadCalendar() {
        calendar.createCalendar();
    }
    
    public void loadCalendarDays() {
        calendar.createCalendarDays();
    }
    
    public void loadLegend() {
        legend.draw();
        
        //
    }
    
    public void openJFrameAdminUsers() {
        JFrameAdminUsers jFrameAdminUsers = new JFrameAdminUsers(this);
        jFrameAdminUsers.setVisible(true);
        jFrameAdminUsers.setLocationRelativeTo(null);
        jFrameAdminUsers.setLayout(null);
    }
    
    public void openJFrameAdminShifts() {
        
        JFrameAdminShifts jFrameAdminShifts = new JFrameAdminShifts(this);
        jFrameAdminShifts.setVisible(true);
        jFrameAdminShifts.setLocationRelativeTo(null);
        jFrameAdminShifts.setLayout(null);
    }
    
    /**
     * @return the controllerAdminEditDay
     */
    public AdminEditDay getControllerAdminEditDay() {
        return calendar.getControllerAdminEditDay();
    }
    
}
