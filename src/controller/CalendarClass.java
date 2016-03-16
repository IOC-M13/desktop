//Creat per Oscar Membrilla Estorach

package controller;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import view.JFrameAdminEditDay;
import view.JFrameWorkerShowDay;

/**
 *
 * @author Oscar
 */
public class CalendarClass {
    public static final String[] MONTH_NAME = {"Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Agosto", "Noviembre", "Diciembre"};
    public static final String[] DAY_NAME = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
    private String dateSelected = "";
    private int year;
    private int month;
    private int daysOfMonth;
    private JPanel jPanelYearMonth;
    private JPanel jPanelDays;
    private JLabel[] labelsDaysCurrentMonth;
    
    private DBHelper db;

    public CalendarClass(JPanel jPanelYearMonth, JPanel jPanelDays){
        
        db = new DBHelper();
        
        this.jPanelYearMonth = jPanelYearMonth;
        this.jPanelDays = jPanelDays;
        
    }
    
    public void createCalendar() {
        createCalendarYearMonth();
        createCalendarDays();
    }
    
    public void createCalendarYearMonth() {
        jPanelYearMonth.setLayout(new GridLayout(1, 2));
        JComboBox jComboBoxYear = new JComboBox();
        java.util.Calendar c1 = java.util.Calendar.getInstance();
        int currentYear = c1.get(java.util.Calendar.YEAR);
        for (int i = 0; i < 6; i++) {
            jComboBoxYear.addItem(String.valueOf(currentYear + i));
        }
        jPanelYearMonth.add(jComboBoxYear);
        JComboBox jComboBoxMonth = new JComboBox();
        for (int i = 0; i < CalendarClass.MONTH_NAME.length; i++) {
            jComboBoxMonth.addItem(CalendarClass.MONTH_NAME[i]);
        }
        jPanelYearMonth.add(jComboBoxMonth);
        jComboBoxYear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                year = Integer.valueOf((String) jComboBoxYear.getSelectedItem());
                month = jComboBoxMonth.getSelectedIndex();
                createCalendarDays();
                //loadShiftsOnCalendar();
            }
        });
        jComboBoxMonth.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                year = Integer.valueOf((String) jComboBoxYear.getSelectedItem());
                month = jComboBoxMonth.getSelectedIndex();
                createCalendarDays();
                //loadShiftsOnCalendar();
            }
        });
        year = Integer.valueOf((String) jComboBoxYear.getSelectedItem());
        month = jComboBoxMonth.getSelectedIndex();
        //createCalendarDays();
    }

    public void createCalendarDays() {
        
        // Crear un Layout en forma de cuadrícula de 7 filas x 7 columnas
        jPanelDays.setLayout(new GridLayout(7, 7));
        jPanelDays.removeAll();
        
        // Crear un borde para los JLabel de los días
        Border border = LineBorder.createGrayLineBorder();
        
        
        // Agregar los nombre de los días de la semana
        JLabel[] lab = new JLabel[7];
        for (int i = 0; i < lab.length; i++) {
            lab[i] = new JLabel(CalendarClass.DAY_NAME[i], SwingConstants.CENTER);
            lab[i].setForeground(Color.white);
            lab[i].setBackground(Color.black);
            lab[i].setOpaque(true);
            jPanelDays.add(lab[i]);
        }
        
        
        
        
        // Agregar los días anteriores al mes seleccionado
        Calendar mycal = new GregorianCalendar(year, month, 1);
        int daysBeforeMonth = getDaysBeforeMonth(mycal);
        Calendar c = new GregorianCalendar(year, month - 1, 1);
        int daysMonthAgo = c.getActualMaximum(Calendar.DAY_OF_MONTH);
        JLabel[] jLab = new JLabel[daysBeforeMonth];
        for (int i = 0; i < daysBeforeMonth; i++) {
            jLab[i] = new JLabel(String.valueOf(daysMonthAgo - daysBeforeMonth + i + 1), SwingConstants.CENTER);
            jLab[i].setForeground(Color.gray);
            jLab[i].setBorder(border);
            jPanelDays.add(jLab[i]);
        }
        
        
        
        
        
        // Agregar los días del mes seleccionado
        daysOfMonth = mycal.getActualMaximum(Calendar.DAY_OF_MONTH);
        labelsDaysCurrentMonth = new JLabel[daysOfMonth];
        for (int i = 0; i < labelsDaysCurrentMonth.length; i++) {
            labelsDaysCurrentMonth[i] = new JLabel(String.valueOf(i + 1), SwingConstants.CENTER);
            labelsDaysCurrentMonth[i].setBorder(border);
            boldFont(labelsDaysCurrentMonth[i]);
            labelsDaysCurrentMonth[i].addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent me) {}

                @Override
                public void mouseEntered(MouseEvent arg0) {} // El ratón se ha situado sobre la etiqueta

                @Override
                public void mouseExited(MouseEvent arg0) {
                }

                @Override
                public void mousePressed(MouseEvent me) {
                    //JOptionPane.showMessageDialog(null, "Has clicado el dia: " + ((JLabel) me.getSource()).getText());
                    dateSelected = String.valueOf(year) + "-" + addZeroLeft(String.valueOf(month + 1)) + "-" + addZeroLeft(((JLabel) me.getSource()).getText());
                    //System.out.println(dateSelected);
                    if (Support.isAdmin) {
                        JFrameAdminEditDay jFrameAdminEditDay = new JFrameAdminEditDay(dateSelected);
                        jFrameAdminEditDay.setVisible(true);
                        jFrameAdminEditDay.setLocationRelativeTo(null);
                        jFrameAdminEditDay.setLayout(null);
                    } else {
                        JFrameWorkerShowDay jFrameWorkerShowDay = new JFrameWorkerShowDay(dateSelected);
                        jFrameWorkerShowDay.setVisible(true);
                        jFrameWorkerShowDay.setLocationRelativeTo(null);
                        jFrameWorkerShowDay.setLayout(null);
                    }
                }

                @Override
                public void mouseReleased(MouseEvent me) {
                }
            });
            jPanelDays.add(labelsDaysCurrentMonth[i]);
        }
        
        
        
        
        
        // Agregar los días posteriores al mes seleccionado
        JLabel[] jLab2 = new JLabel[42 - daysOfMonth - daysBeforeMonth];
        for (int i = 0; i < jLab2.length; i++) {
            jLab2[i] = new JLabel(String.valueOf(i + 1), SwingConstants.CENTER);
            jLab2[i].setForeground(Color.gray);
            jLab2[i].setBorder(border);
            jPanelDays.add(jLab2[i]);
        }
        
        
        
        // Cargar los turnos del usuario indicado
        loadShiftsOnCalendar();
        
        // Actualizar el JPanel para que se vean los cambios efectuados en el
        jPanelDays.updateUI();
    }

    private void loadShiftsOnCalendar() {
        
        db.connectDB();
        
        ResultSet rs = db.shiftsOfUserOnMonth(Support.userName, year, month);
        
        try {
            
            while (rs.next()) {
                labelsDaysCurrentMonth[rs.getInt(1) - 1].setBackground(Color.decode("#" + rs.getString(2)));
                labelsDaysCurrentMonth[rs.getInt(1) - 1].setOpaque(true);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(CalendarClass.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            db.closeDB();
        }
        
    }
    
    private int getDaysBeforeMonth(Calendar cal) {
        int initialDayOfMonth = cal.get(java.util.Calendar.DAY_OF_WEEK);
        cal.setMinimalDaysInFirstWeek(7);
        int numberWeekOfMonth = cal.get(java.util.Calendar.WEEK_OF_MONTH);
        if (numberWeekOfMonth == 0) {
            switch (initialDayOfMonth) {
                case 1:
                    return 6;
                default:
                    return initialDayOfMonth - 2;
            }
        } else {
            return 7;
        }
    }
    
    private JLabel boldFont(JLabel lab) {
        Font f = lab.getFont();
        lab.setFont(f.deriveFont(f.getStyle() | Font.BOLD));
        return lab;
    }

    private String addZeroLeft(String numString) {
        int num = Integer.valueOf(numString);
        if (num < 10) {
            return "0" + numString;
        }
        return numString;
    }

    private String getDateSelected() {
        return dateSelected;
    }

    private void setDateSelected(String dateSelected) {
        this.dateSelected = dateSelected;
    }

}
