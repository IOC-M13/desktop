package controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import view.JFrameAdmin;
import view.JFrameAdminEditDay;

/**
 * Class that contain the JFrameAdminEditDay controller
 * @author Oscar
 */
public class AdminEditDay {
    
    private final String TEXT_NO_SHIFT = "--Select--";
    
    private DBHelper db;
    private boolean doUpdate;
    private Admin controllerAdmin;
    
    private String dateSelected;
    
    private String shiftFindedInDB;
    
    private JFrame jFrame;
    
    private JLabel day;
    private JLabel userName;
    private JComboBox shiftName;
    private JLabel startTime;
    private JLabel endTime;
    
    public boolean shiftsLoadedInComboBox = false;
    
    /**
     * Constructor of JFrameAdminEditDay controller
     * @param controllerAdmin The JFrameAdmin controller
     * @param dateSelected The date selected, in the calendar, by user
     * @param jFrame The JFrame of JFrameAdminEditDay
     * @author Oscar Membrilla Estorach
     */
    public AdminEditDay(Admin controllerAdmin, String dateSelected, JFrameAdminEditDay jFrame) {
        db = new DBHelper();
        doUpdate = false;
        this.controllerAdmin = controllerAdmin;
        
        this.dateSelected = dateSelected;
        
        this.jFrame = jFrame;
        
        this.day = jFrame.getLblDate();
        this.userName = jFrame.getLblUser();
        this.shiftName = jFrame.getComboShift();
        this.startTime = jFrame.getLblStartTime();
        this.endTime = jFrame.getLblEndTime();
        
        day.setText(dateSelected);
        userName.setText(Support.userName);
        
    }
    
    /**
     * Load shifts in JComboBox shifts
     * @author Oscar Membrilla Estorach
     */
    public void loadShifts() {
        
        shiftsLoadedInComboBox = false;
        
        shiftName.removeAllItems();
        
        shiftName.addItem(TEXT_NO_SHIFT);
        
        try {
            
            db.connectDB();
            ResultSet rs = db.getShiftsName();
            
            // Iterar por los resultados de los resultSet para ir agregando los nombres de los turnos existentes
            while (rs.next()) {
                shiftName.addItem(rs.getString(1));
            }
            
            //Buscar si existe un turno asignado a este día y seleccinarlo en el combobox
            java.sql.Date sqlDate = db.stringDateToSQLdate(dateSelected);
            ResultSet rs2 = db.userWithShiftAssigned(userName.getText(), sqlDate);

            if (rs2.first()) {
                shiftName.setSelectedItem(db.getShiftName(rs2.getInt(1)));
                doUpdate = true;
                
            }
            
            //Almacenar el turno encontrado en el dia actual
            shiftFindedInDB = (String)shiftName.getSelectedItem();
            
            //Indicar que ya ha acabado de completarse el listado de turnos existente
            shiftsLoadedInComboBox = true;
            
            //Cargar las horas de empiece y final del turno que aparece en el combobox
            changeTimes();
            
        } catch (SQLException ex) {
            Logger.getLogger(JFrameAdmin.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            db.closeDB();
        }
        
    }
    
    /**
     * Load hours and minutes of start and end to the shift selected
     * @author Oscar Membrilla Estorach
     */
    public void changeTimes() {
        
        if (shiftsLoadedInComboBox && !((String)shiftName.getSelectedItem()).equals(TEXT_NO_SHIFT)) {
        
            try {
            
                db.connectDB();
                ResultSet rs = db.getShiftsTimesByName((String) shiftName.getSelectedItem());

                rs.first();
                startTime.setText(rs.getString(1));
                endTime.setText(rs.getString(2));
            
            } catch (SQLException ex) {
                System.out.println("Error en: " + ex.getClass() + ". Ocurre lo siguiente: " + ex.getMessage());
            } finally {
                db.closeDB();
            }
            
        } else if (shiftsLoadedInComboBox && ((String)shiftName.getSelectedItem()).equals(TEXT_NO_SHIFT)) {
            startTime.setText("-");
            endTime.setText("-");
        }
    }
    
    /**
     * Save changes of JFrameAdminEditDay in DB
     * @author Oscar Membrilla Estorach
     */
    public void save () {
        
        db.connectDB();
        
        java.sql.Date sqlDate = db.stringDateToSQLdate(dateSelected);
        
        if (doUpdate) {
            
            if (((String)shiftName.getSelectedItem()).equals(TEXT_NO_SHIFT)) {
                //Do delete
                db.deleteShiftToUser(sqlDate, userName.getText(), shiftFindedInDB);               
            } else {
                //Do update
                db.updateShiftToUser(sqlDate, userName.getText(), (String)shiftName.getSelectedItem());
            }
            
            //Recargar el calendario()
            controllerAdmin.loadCalendarDays();
            
            jFrame.dispose();
            
        } else if (!doUpdate && !((String)shiftName.getSelectedItem()).equals(TEXT_NO_SHIFT)) {
            //Do insert
            db.assignShiftToUser(sqlDate, userName.getText(), (String)shiftName.getSelectedItem());
            
            //Recargar el calendario()
            controllerAdmin.loadCalendarDays();
            
            jFrame.dispose();
        } else {
            JOptionPane.showMessageDialog(null, "There are no shift selected! ");
        }
        
    }
    
    /**
     * Write date selected in JLabel of date
     * @param dateSelected The date selected, in the calendar, by user
     * @author Oscar Membrilla Estorach
     */
    public void changeDate(String dateSelected) {
        this.dateSelected = dateSelected;
        day.setText(this.dateSelected);
    }
    
    /**
     * Close JFrame of JFrameAdminEditDay
     * @author Oscar Membrilla Estorach
     */
    public void closeWindow() {
        jFrame.dispose();
    }
    
    
    
}
