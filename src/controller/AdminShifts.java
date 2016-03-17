//Creat per Oscar Membrilla Estorach

package controller;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
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
        
        loadComboBoxHourAndMin();
        
    }
    
    private void loadComboBoxHourAndMin() {
        
        //Bucle para rellenar los combobox de las horas
        for (int i = 0; i < 24; i++) {
            addStartTimeHour.addItem(i);
            addEndTimeHour.addItem(i);
            editStartTimeHour.addItem(i);
            editEndTimeHour.addItem(i);
        }
        
        //Bucle para rellenar los combobox de los minutos
        for (int i = 0; i < 60; i++) {
            addStartTimeMin.addItem(i);
            addEndTimeMin.addItem(i);
            editStartTimeMin.addItem(i);
            editEndTimeMin.addItem(i);
        }
        
    }

}
