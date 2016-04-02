//Creat per Oscar Membrilla Estorach

package controller;

import java.awt.Color;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import view.JFrameAdminShifts;

/**
 * Class that contain the JFrameAdminShifts controller
 * @author Oscar
 */
public class AdminShifts {
    
    private DBHelper db;
    
    //Referencia al controller del JFrameAdmin
    private Admin controllerAdmin;
    
    //Referencia al controller del JFrameAdminEditDay
    private AdminEditDay controllerAdminEditDay;
    
    // Referencia al JFrame
    private JFrameAdminShifts jFrame;
    
    // Referencias a componentes de la pestaña de Add Shifts
    private JTextField addName;
    private JComboBox addStartTimeHour;
    private JComboBox addStartTimeMin;
    private JComboBox addEndTimeHour;
    private JComboBox addEndTimeMin;
    private JLabel addColor;
    
    // Referencias a componentes de la pestaña de Edit Shifts
    private JComboBox editName;
    private JComboBox editStartTimeHour;
    private JComboBox editStartTimeMin;
    private JComboBox editEndTimeHour;
    private JComboBox editEndTimeMin;
    private JLabel editColor;
    private JButton editClearAll;
    private JButton editSave;
    private JButton editDel;
    
    public boolean shiftsLoadedInComboBox = false;
    
    /**
     * Constructor of JFrameAdminShifts controller
     * @param controllerAdmin The JFrameAdmin controller
     * @param jFrame The jFrame of JFrameAdminShifts
     * @author Oscar Membrilla Estorach
     */
    public AdminShifts (Admin controllerAdmin, JFrameAdminShifts jFrame) {
        
        this.controllerAdmin = controllerAdmin;
        
        this.jFrame = jFrame;
        
        this.addName = jFrame.getTextAddShiftName();
        this.addStartTimeHour = jFrame.getComboAddStartTimeHour();
        this.addStartTimeMin = jFrame.getComboAddStartTimeMin();
        this.addEndTimeHour = jFrame.getComboAddEndTimeHour();
        this.addEndTimeMin = jFrame.getComboAddEndTimeMin();
        this.addColor = jFrame.getLblAddColor();
        
        this.editName = jFrame.getComboEditName();
        this.editStartTimeHour = jFrame.getComboEditStartTimeHour();
        this.editStartTimeMin = jFrame.getComboEditStartTimeMin();
        this.editEndTimeHour = jFrame.getComboEditEndTimeHour();
        this.editEndTimeMin = jFrame.getComboEditEndTimeMin();
        this.editColor = jFrame.getLblEditColor();
        this.editClearAll = jFrame.getBtnEditClearAll();
        this.editSave = jFrame.getBtnEditSave();
        this.editDel = jFrame.getBtnEditDel();
        
        db = new DBHelper();
        
        loadComboBoxHourAndMin();
        
    }
    
    /**
     * Fill time JComboBoxes with hours and mins that have a day
     * @author Oscar Membrilla Estorach
     */
    private void loadComboBoxHourAndMin() {
        
        //Bucle para rellenar los combobox de las horas
        for (int i = 0; i < 24; i++) {
            if (i < 10) {
                addStartTimeHour.addItem(zeroLeft(i));
                addEndTimeHour.addItem(zeroLeft(i));
                editStartTimeHour.addItem(zeroLeft(i));
                editEndTimeHour.addItem(zeroLeft(i));
            } else {
                addStartTimeHour.addItem(String.valueOf(i));
                addEndTimeHour.addItem(String.valueOf(i));
                editStartTimeHour.addItem(String.valueOf(i));
                editEndTimeHour.addItem(String.valueOf(i));
            }
        }
        
        //Bucle para rellenar los combobox de los minutos
        for (int i = 0; i < 60; i++) {
            if (i < 10) {
                addStartTimeMin.addItem(zeroLeft(i));
                addEndTimeMin.addItem(zeroLeft(i));
                editStartTimeMin.addItem(zeroLeft(i));
                editEndTimeMin.addItem(zeroLeft(i));
            } else {
                addStartTimeMin.addItem(String.valueOf(i));
                addEndTimeMin.addItem(String.valueOf(i));
                editStartTimeMin.addItem(String.valueOf(i));
                editEndTimeMin.addItem(String.valueOf(i));
            }
        }
        
    }
    
    /**
     * Clear all the swing components in JPanel of "Add Shifts"
     * @author Oscar Membrilla Estorach
     */
    public void clearAll() {
        addName.setText("");
        addStartTimeHour.setSelectedItem("00");
        addStartTimeMin.setSelectedItem("00");
        addEndTimeHour.setSelectedItem("00");
        addEndTimeMin.setSelectedItem("00");
        addColor.setBackground(Color.decode("#" + "66CCFF"));
    }
    
    /**
     * Add zero to left to a number
     * @param num The number to add zero to left
     * @return String with number that zero added to left
     * @author Oscar Membrilla Estorach
     */
    private String zeroLeft(int num) {
        return "0" + String.valueOf(num);
    }
    
    /**
     * Add a new shift in DB
     * @author Oscar Membrilla Estorach
     */
    public void addShift() {
        
        String name = addName.getText();
        String startTime = (String)addStartTimeHour.getSelectedItem() + ":" + (String)addStartTimeMin.getSelectedItem();
        String endTime = (String)addEndTimeHour.getSelectedItem() + ":" + (String)addEndTimeMin.getSelectedItem();
        String color = Integer.toHexString(addColor.getBackground().getRGB() & 0x00ffffff);
        
        db.connectDB();
        
        try {
            db.insertShift(name, startTime, endTime, color);
            JOptionPane.showMessageDialog(null, "The shift " + name + " has been added successfully.");
            
            //Clear all
            clearAll();
            
            //Cargar de nuevo la leyenda del JFrameAdmin
            controllerAdmin.loadLegend();
            
            //Actualizar los turnos de la ventana JFrameEditDay, en el caso de que exista esta ventana
            controllerAdminEditDay = controllerAdmin.getControllerAdminEditDay();
            if (controllerAdminEditDay != null)  {
                controllerAdminEditDay.loadShifts();
            }
            
        } catch (SQLException ex) {
            
            if (ex.getErrorCode() == 1062) {
                JOptionPane.showMessageDialog(null, "The name of the shift is already used!");
            }
            
        } finally {
            db.closeDB();
        }       
        
    }
    
    /**
     * Load shift names of JComboBox shifts
     * @author Oscar Membrilla Estorach
     */
    public void loadShiftsInComboBox() {
        
        editName.removeAllItems();
        
        db.connectDB();
        ResultSet rs = db.getShiftsName();
        
        // Iterar por los resultados de los resultSet para ir agregado items al combobox
        try {
            while (rs.next()) {
                
                editName.addItem(rs.getString(1));
                
                if (rs.isFirst()) {
                    editName.setSelectedItem(rs.getString(1));
                }     
                
            }
            
            shiftsLoadedInComboBox = true;
            
        } catch (SQLException ex) {
            System.out.println("Error en: " + ex);
        } finally {
            db.closeDB();
        }
        
        loadShiftDataInComponents();
         
    }
    
    /**
     * Load shift data in swing components of JPanel of "Edit Shifts"
     * @author Oscar Membrilla Estorach
     */
    public void  loadShiftDataInComponents() {
        
        if (shiftsLoadedInComboBox) {
        
            String shiftOfCombo = (String) editName.getSelectedItem();

            db.connectDB();
            ResultSet rs = db.getAllDataShift(shiftOfCombo);

            // Iterar por los resultados de los resultSet
            try {
                if (rs.first()) {
                    editStartTimeHour.setSelectedItem(rs.getString(1).substring(0, 2));
                    editStartTimeMin.setSelectedItem(rs.getString(1).substring(3, 5));

                    editEndTimeHour.setSelectedItem(rs.getString(2).substring(0, 2));
                    editEndTimeMin.setSelectedItem(rs.getString(2).substring(3, 5));

                    editColor.setBackground(Color.decode("#" + rs.getString(3))); 
                }

                if (shiftOfCombo.equals("Free")) {
                    editStartTimeHour.setEnabled(false);
                    editStartTimeMin.setEnabled(false);
                    editEndTimeHour.setEnabled(false);
                    editEndTimeMin.setEnabled(false);
                    editDel.setEnabled(false);
                } else if (!editDel.isEnabled()) {
                    editStartTimeHour.setEnabled(true);
                    editStartTimeMin.setEnabled(true);
                    editEndTimeHour.setEnabled(true);
                    editEndTimeMin.setEnabled(true);
                    editDel.setEnabled(true);                
                }

            } catch (SQLException ex) {
                System.out.println("Error en: " + ex);
            } finally {
                db.closeDB();
            }
         }
        
    }
    
    /**
     * Put red semaphore "shiftLoadedInComboBox", else this semaphore will always be in green
     * @author Oscar Membrilla Estorach
     */
    public void changePanel() {
        shiftsLoadedInComboBox = false;
    }
    
    /**
     * Edit shift data in DB
     * @author Oscar Membrilla Estorach
     */
    public void editShiftData() {
    
        String name = (String) editName.getSelectedItem();
        String startTime = (String)editStartTimeHour.getSelectedItem() + ":" + (String)editStartTimeMin.getSelectedItem();
        String endTime = (String)editEndTimeHour.getSelectedItem() + ":" + (String)editEndTimeMin.getSelectedItem();
        String color = Integer.toHexString(editColor.getBackground().getRGB() & 0x00ffffff);
        
        db.connectDB();
        
        try {
                
            //Hacer update
            db.updateShiftData(name, startTime, endTime, color);
            JOptionPane.showMessageDialog(null, "The shift has been edited successfully.");
            
            //Cargar de nuevo la leyenda del JFrameAdmin
            controllerAdmin.loadLegend();

            //Cargar de nuevo el calendario de días, porque a lo mejor se ha eliminado un turno
            //el cual estaba asignado a varios días.
            controllerAdmin.loadCalendarDays();
            
        } catch (SQLException ex) {
            System.out.println("Error en: " + ex);
        } finally {
            db.closeDB();
        }
        
    }
    
    /**
     * Delete shift in DB
     * @author Oscar Membrilla Estorach
     */
    public void deleteShift() {
        
        String comboBox = (String) editName.getSelectedItem();
           
        db.connectDB();

        try {
            db.deleteShiftByName(comboBox);
        } catch (SQLException ex) {
            System.out.println("Error en: " + ex);
        } finally {
            db.closeDB();
        }

        JOptionPane.showMessageDialog(null, "The shift " + comboBox + " has been deleted successfully.");
        
        //Cargar de nuevo la leyenda del JFrameAdmin
        controllerAdmin.loadLegend();
        
        //Cargar de nuevo el calendario de días, porque a lo mejor se ha eliminado un turno
        //el cual estaba asignado a varios días.
        controllerAdmin.loadCalendarDays();
        
        
        //Actualizar los turnos de la ventana JFrameEditDay, en el caso de que exista esta ventana
        controllerAdminEditDay = controllerAdmin.getControllerAdminEditDay();
        if (controllerAdminEditDay != null)  {
            controllerAdminEditDay.loadShifts();
        }

        //Recargar el comboBox de nombres de turnos
        shiftsLoadedInComboBox = false;
        loadShiftsInComboBox();
    
    }
    
    /**
     * Close JFrame of JFrameAdminShifts
     * @author Oscar Membrilla Estorach
     */
    public void closeWindow() {
        jFrame.dispose();
    }

}
