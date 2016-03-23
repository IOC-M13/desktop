//Creat per Oscar Membrilla Estorach

package controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import view.JFrameAdmin;

/**
 *
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
    
    public AdminEditDay(Admin controllerAdmin, String dateSelected, JFrame jFrame, JLabel day, JLabel userName, JComboBox shiftName, JLabel startTime, JLabel endTime) {
        db = new DBHelper();
        doUpdate = false;
        this.controllerAdmin = controllerAdmin;
        
        this.dateSelected = dateSelected;
        
        this.jFrame = jFrame;
        
        this.day = day;
        this.userName = userName;
        this.shiftName = shiftName;
        this.startTime = startTime;
        this.endTime = endTime;
        
        day.setText(dateSelected);
        userName.setText(Support.userName);
        
    }
    
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
    
    public void changeTimes() {
        
        if (shiftsLoadedInComboBox && !((String)shiftName.getSelectedItem()).equals(TEXT_NO_SHIFT)) {
        
            //System.out.println((String)shiftName.getSelectedItem());
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
    
    public void save () {
        
        db.connectDB();
        
        java.sql.Date sqlDate = db.stringDateToSQLdate(dateSelected);
        
        if (doUpdate) {
            
            if (((String)shiftName.getSelectedItem()).equals(TEXT_NO_SHIFT)) {
                db.deleteShiftToUser(sqlDate, userName.getText(), shiftFindedInDB);               
            } else {
                //Hacer update
                db.updateShiftToUser(sqlDate, userName.getText(), (String)shiftName.getSelectedItem());
            }
            
            //Recargar el calendario()
            controllerAdmin.loadCalendarDays();
            
            jFrame.dispose();
            
        } else if (!doUpdate && !((String)shiftName.getSelectedItem()).equals(TEXT_NO_SHIFT)) {
            //Hacer insert
            db.assignShiftToUser(sqlDate, userName.getText(), (String)shiftName.getSelectedItem());
            
            //Recargar el calendario()
            controllerAdmin.loadCalendarDays();
            
            jFrame.dispose();
        } else {
            JOptionPane.showMessageDialog(null, "There are no shift selected! ");
        }
        
    }
    
    public void exit() {
        jFrame.dispose();
    }
    
    
    
}
