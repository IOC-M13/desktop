//Creat per Oscar Membrilla Estorach

package controller;

import java.awt.Color;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 *
 * @author Oscar
 */
public class AdminShifts {
    
    private DBHelper db;
    
    // Referencia al JFrame
    private JFrame jFrame;
    
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
    
    public AdminShifts (JFrame jFrame,
                        JTextField addName, JComboBox addStartTimeHour, JComboBox addStartTimeMin, JComboBox addEndTimeHour, JComboBox addEndTimeMin, JLabel addColor,
                        JComboBox editName, JComboBox editStartTimeHour, JComboBox editStartTimeMin, JComboBox editEndTimeHour, JComboBox editEndTimeMin, JLabel editColor) {
        
        this.jFrame = jFrame;
        
        this.addName = addName;
        this.addStartTimeHour = addStartTimeHour;
        this.addStartTimeMin = addStartTimeMin;
        this.addEndTimeHour = addEndTimeHour;
        this.addEndTimeMin = addEndTimeMin;
        this.addColor = addColor;
        
        this.editName = editName;
        this.editStartTimeHour = editStartTimeHour;
        this.editStartTimeMin = editStartTimeMin;
        this.editEndTimeHour = editEndTimeHour;
        this.editEndTimeMin = editEndTimeMin;
        this.editColor = editColor;
        
        db = new DBHelper();
        
        loadComboBoxHourAndMin();
        
    }
    
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
    
    public void clearAll() {
        addName.setText("");
        addStartTimeHour.setSelectedItem("00");
        addStartTimeMin.setSelectedItem("00");
        addEndTimeHour.setSelectedItem("00");
        addEndTimeMin.setSelectedItem("00");
        addColor.setBackground(Color.decode("#" + "66CCFF"));
    }
    
    private String zeroLeft(int num) {
        return "0" + String.valueOf(num);
    }
    
    
    public void addShift() {
        
        String name = addName.getText();
        String startTime = (String)addStartTimeHour.getSelectedItem() + ":" + (String)addStartTimeMin.getSelectedItem();
        String endTime = (String)addEndTimeHour.getSelectedItem() + ":" + (String)addEndTimeMin.getSelectedItem();
        Color colorBackground = addColor.getBackground();
        String color = Integer.toHexString(colorBackground.getRGB() & 0x00ffffff);
        
        db.connectDB();
        
        try {
            db.insertShift(name, startTime, endTime, color);
            JOptionPane.showMessageDialog(null, "The shift " + name + " is added with exit.");
        } catch (SQLException ex) {
            
            if (ex.getErrorCode() == 1062) {
                JOptionPane.showMessageDialog(null, "The name of the shift is already used!");
            }
            
        }
       
        
        
    }

}
