//Creat per Oscar Membrilla Estorach

package controller;

import java.awt.BorderLayout;
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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

/**
 *
 * @author Oscar
 */
public class CalendarClass {
    public static final String[] MONTH_NAME = {"Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Agosto", "Noviembre", "Diciembre"};
    public static final String[] DAY_NAME = {"Lunes", "Martes", "Mi\u00e9rcoles", "Jueves", "Viernes", "S\u00e1bado", "Domingo"};
    private String dateSelected = "";
    
    private BDHelper bdHelper;

    public CalendarClass(){
        
        bdHelper = new BDHelper();
        
    }
    
    public void createCalendar(JPanel jPanelYearMonth, JPanel jPanelDays) {
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
                int year = Integer.valueOf((String) jComboBoxYear.getSelectedItem());
                int month = jComboBoxMonth.getSelectedIndex();
                createCalendarDays(year, month, jPanelDays);
            }
        });
        jComboBoxMonth.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int year = Integer.valueOf((String) jComboBoxYear.getSelectedItem());
                int month = jComboBoxMonth.getSelectedIndex();
                createCalendarDays(year, month, jPanelDays);
            }
        });
        int year = Integer.valueOf((String) jComboBoxYear.getSelectedItem());
        int month = jComboBoxMonth.getSelectedIndex();
        createCalendarDays(year, month, jPanelDays);
    }

    private void createCalendarDays(int year, int month, JPanel jPanelDays) {
        
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
        int daysOfMonth = mycal.getActualMaximum(Calendar.DAY_OF_MONTH);
        JLabel[] labels = new JLabel[daysOfMonth];
        for (int i = 0; i < labels.length; i++) {
            labels[i] = new JLabel(String.valueOf(i + 1), SwingConstants.CENTER);
            labels[i].setBorder(border);
            boldFont(labels[i]);
            labels[i].addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent me) {}

                @Override
                public void mouseEntered(MouseEvent arg0) {} // El ratón se ha situado sobre la etiqueta

                @Override
                public void mouseExited(MouseEvent arg0) {
                }

                @Override
                public void mousePressed(MouseEvent me) {
                    JOptionPane.showMessageDialog(null, "Has clicado el dia: " + ((JLabel) me.getSource()).getText());
                    dateSelected = String.valueOf(year) + "-" + addZeroLeft(String.valueOf(month + 1)) + "-" + addZeroLeft(((JLabel) me.getSource()).getText());
                    System.out.println(dateSelected);
                }

                @Override
                public void mouseReleased(MouseEvent me) {
                }
            });
            jPanelDays.add(labels[i]);
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
        loadShiftsOnCalendar(Support.userName, year, month, labels);
        
        
        
        // Actualizar el JPanel para que se vean los cambios efectuados en el
        jPanelDays.updateUI();
    }

    private void loadShiftsOnCalendar(String user, int year, int month, JLabel[] labels) {
        
        bdHelper.connectDB(Support.IP, Support.port);
        
        ResultSet rs = bdHelper.shiftsOfUserOnMonth(user, year, month);
        
        try {
            
            while (rs.next()) {
                labels[rs.getInt(1) - 1].setBackground(Color.decode("#" + rs.getString(2)));
                labels[rs.getInt(1) - 1].setOpaque(true);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(CalendarClass.class.getName()).log(Level.SEVERE, null, ex);
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
