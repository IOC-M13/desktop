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
import java.util.Calendar;
import java.util.GregorianCalendar;
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
public class Support {
    public static final String DAY_NAME[] = {"Lunes", "Martes", "Miércoles",
                "Jueves", "Viernes", "Sábado", "Domingo"};
    public static final String MONTH_NAME[] = {"Enero", "Febrero", "Marzo",
                "Abril", "Mayo", "Junio" , "Julio", "Agosto", "Septiembre", 
                "Agosto", "Noviembre", "Diciembre"};
    
    public static String IP = "localhost";
    public static String port = "3306";
    public static String userName = "";
    
    private String dateSelected = "";
    
    public void createCalendar (JPanel jPanelYearMonth, JPanel jPanelDays) {
        
        jPanelYearMonth.setLayout(new GridLayout(1,2));
        JComboBox jComboBoxYear = new JComboBox();
        Calendar c1 = Calendar.getInstance();
        int currentYear = c1.get(Calendar.YEAR);
        for (int i = 0; i < 6; i++) {
            jComboBoxYear.addItem(String.valueOf(currentYear + i));
        }
        jPanelYearMonth.add(jComboBoxYear);
        
        
        JComboBox jComboBoxMonth = new JComboBox();
        for (int i = 0; i < Support.MONTH_NAME.length; i++) {
            jComboBoxMonth.addItem(Support.MONTH_NAME[i]);
        }
        jPanelYearMonth.add(jComboBoxMonth);
        
        
        
        
        
        jComboBoxYear.addActionListener (new ActionListener () {
            @Override
            public void actionPerformed(ActionEvent e) {
                int year = Integer.valueOf((String)jComboBoxYear.getSelectedItem());
                int month = jComboBoxMonth.getSelectedIndex();
                createCalendarDays(year, month, jPanelDays);
            }
        });
        
        jComboBoxMonth.addActionListener (new ActionListener () {
            @Override
            public void actionPerformed(ActionEvent e) {
                int year = Integer.valueOf((String)jComboBoxYear.getSelectedItem());
                int month = jComboBoxMonth.getSelectedIndex();
                createCalendarDays(year, month, jPanelDays);
            }
        });
        
        int year = Integer.valueOf((String)jComboBoxYear.getSelectedItem());
        int month = jComboBoxMonth.getSelectedIndex();
        createCalendarDays(year, month, jPanelDays);
        
    }
        
    private void createCalendarDays (int year, int month, JPanel jPanelDays) {   
        
        jPanelDays.setLayout(new GridLayout(7,7)); //(filas, columnas)
        jPanelDays.removeAll();
        
        Border border = LineBorder.createGrayLineBorder();
        
        
        //Mostrar los nombres de los días de la semana
        JLabel[] lab = new JLabel[7];
        for (int i = 0; i < lab.length; i++) {
            lab[i] = new JLabel(Support.DAY_NAME[i], SwingConstants.CENTER);
            lab[i].setForeground(Color.white);
            JPanel titlePanel = new JPanel(new BorderLayout());
            titlePanel.setBackground(Color.black);
            titlePanel.add(lab[i]);  // adds to center of panel's default BorderLayout.
            jPanelDays.add(titlePanel);
        }
        
        
        
        
        
        // Create a calendar object and set year and month
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
        
        
        
        
        
        // Get the number of days in that month
        int daysOfMonth = mycal.getActualMaximum(Calendar.DAY_OF_MONTH);
        JLabel[] labels = new JLabel[daysOfMonth];
        for (int i = 0; i < labels.length; i++) { 
            labels[i] = new JLabel(String.valueOf(i + 1), SwingConstants.CENTER);
            labels[i].setBorder(border);
            boldFont(labels[i]);
            labels[i].addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent me) {
                    
                 
                }

                @Override
                public void mouseEntered(MouseEvent arg0) {
                 // El ratón se ha situado sobre la etiqueta
                }

                @Override
                public void mouseExited(MouseEvent arg0) {}

                @Override
                public void mousePressed(MouseEvent me) {
                    // La etiqueta ha sido clickada
                    JOptionPane.showMessageDialog(null, "Has clicado el dia: " + ((JLabel) me.getSource()).getText());
                    
                    dateSelected = String.valueOf(year) + "-" + addZeroLeft(String.valueOf(month + 1)) + "-"  + addZeroLeft(((JLabel )me.getSource()).getText());
                    System.out.println(dateSelected);
                }

                @Override
                public void mouseReleased(MouseEvent me) {}
               });
            jPanelDays.add(labels[i]);
        }
        
        
        
        
        
        JLabel[] jLab2 = new JLabel[42 - daysOfMonth - daysBeforeMonth];
        for (int i = 0; i < jLab2.length; i++) {
            jLab2[i] = new JLabel(String.valueOf(i + 1), SwingConstants.CENTER);
            jLab2[i].setForeground(Color.gray);
            jLab2[i].setBorder(border);
            jPanelDays.add(jLab2[i]);
        }
        
        
        jPanelDays.updateUI();
        
        
    }
    
    private int getDaysBeforeMonth (Calendar cal) {
	
        int initialDayOfMonth = cal.get(Calendar.DAY_OF_WEEK);
        
        cal.setMinimalDaysInFirstWeek(7);
        int numberWeekOfMonth = cal.get(Calendar.WEEK_OF_MONTH);
        
        if (numberWeekOfMonth == 0) {
            
            switch (initialDayOfMonth) {
                case 1: return 6;
                default: return initialDayOfMonth - 2;
            }
        
        } else {
            return 7;
        }
      
    } 
    
    private JLabel boldFont (JLabel lab) {
        Font f = lab.getFont();
        lab.setFont(f.deriveFont(f.getStyle() | Font.BOLD));
        return lab;
    }
    
    private String addZeroLeft (String numString) {
        
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
