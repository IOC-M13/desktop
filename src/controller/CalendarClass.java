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
 * Class that contain all necessary to generate a GUI calendar
 * @author Oscar Membrilla Estorach
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
    
    private Admin controllerAdmin;
    private JFrameAdminEditDay jFrameAdminEditDay;
    private DBHelper db;

    /**
     * Constructor of CalendarClass with an Admin controller parameter
     * @param controllerAdmin The JFrameAdmin controller
     * @param jPanelYearMonth The JPanel of years and months of a GUI calendar
     * @param jPanelDays The JPanel of days of a GUI calendar
     * @author Oscar Membrilla Estorach
     */
    public CalendarClass(Admin controllerAdmin, JPanel jPanelYearMonth, JPanel jPanelDays){
        
        db = new DBHelper();
        
        this.controllerAdmin = controllerAdmin;
        this.jPanelYearMonth = jPanelYearMonth;
        this.jPanelDays = jPanelDays;
        
    }
    
    /**
     * Constructor of CalendarClass
     * @param jPanelYearMonth The JPanel of years and months of a GUI calendar
     * @param jPanelDays The JPanel of days of a GUI calendar
     * @author Oscar Membrilla Estorach
     */
    public CalendarClass(JPanel jPanelYearMonth, JPanel jPanelDays){
        
        db = new DBHelper();
        
        this.jPanelYearMonth = jPanelYearMonth;
        this.jPanelDays = jPanelDays;
        
    }
    
    /**
     * Call the two methods that create a complete GUI calendar
     * @author Oscar Membrilla Estorach
     */
    public void createCalendar() {
        createCalendarYearMonth();
        createCalendarDays();
    }
    
    /**
     * Create two JComboBoxes with years and months of a GUI calendar
     * @author Oscar Membrilla Estorach
     */
    public void createCalendarYearMonth() {
        
        /* 
           Crear el comboBox con los años, concretamente se crean 6 años
           antes del año actual y 6 años después del año actual. 
           Se queda seleccionado, por defecto, el año actual.
        */
        jPanelYearMonth.setLayout(new GridLayout(1, 2));
        JComboBox jComboBoxYear = new JComboBox();
        java.util.Calendar c1 = java.util.Calendar.getInstance();
        int currentYear = c1.get(java.util.Calendar.YEAR);
        for (int i = 0; i <= 12; i++) {
            jComboBoxYear.addItem(String.valueOf(currentYear + i - 6));
        }
        jComboBoxYear.setSelectedItem(String.valueOf(currentYear));
        jPanelYearMonth.add(jComboBoxYear);
        
        /* 
           Crear el comboBox con los meses.
           Se queda, por defecto, seleccionado el mes actual.
        */
        JComboBox jComboBoxMonth = new JComboBox();
        for (int i = 0; i < CalendarClass.MONTH_NAME.length; i++) {
            jComboBoxMonth.addItem(CalendarClass.MONTH_NAME[i]);
        }
        
        //Seleccionar el mes actual
        int currentMonth = c1.get(java.util.Calendar.MONTH);
        jComboBoxMonth.setSelectedItem(MONTH_NAME[currentMonth]);
        jPanelYearMonth.add(jComboBoxMonth);
        
        jComboBoxYear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                year = Integer.valueOf((String) jComboBoxYear.getSelectedItem());
                month = jComboBoxMonth.getSelectedIndex();
                createCalendarDays();
            }
        });
        jComboBoxMonth.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                year = Integer.valueOf((String) jComboBoxYear.getSelectedItem());
                month = jComboBoxMonth.getSelectedIndex();
                createCalendarDays();
            }
        });
        year = Integer.valueOf((String) jComboBoxYear.getSelectedItem());
        month = jComboBoxMonth.getSelectedIndex();
    }

    /**
     * Create a days of a month (and year) selected of a GUI calendar.
     * And load shifts, from DB, of selected user.
     * @author Oscar Membrilla Estorach
     */
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
                    dateSelected = addZeroLeft(((JLabel) me.getSource()).getText()) + "-" + addZeroLeft(String.valueOf(month + 1)) + "-" + String.valueOf(year) ;
                    if (Support.isAdmin) {
                        jFrameAdminEditDay = JFrameAdminEditDay.getInstance(controllerAdmin, dateSelected);
                        
                        jFrameAdminEditDay.getController().changeDate(dateSelected);
                        jFrameAdminEditDay.getController().loadShifts();
                        
                    } else {
                        JFrameWorkerShowDay jFrameWorkerShowDay = JFrameWorkerShowDay.getInstance(dateSelected);
                        
                        jFrameWorkerShowDay.getController().changeDate(dateSelected);
                        jFrameWorkerShowDay.getController().loadData();
                        
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

    /**
     * Load shifts, from DB, of selected user.
     * @author Oscar Membrilla Estorach
     */
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
    
    /**
     * Method that get number of days before month of cal parameter
     * @param cal Instance of GregorianCalendar
     * @return int with number of days before month of cal parameter
     * @author Oscar Membrilla Estorach
     */
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
    
    /**
     * Put text of JLabel in bold
     * @param lab JLabel which is to put the text in bold 
     * @return JLabel with your own text in bold
     * @author Oscar Membrilla Estorach
     */
    private JLabel boldFont(JLabel lab) {
        Font f = lab.getFont();
        lab.setFont(f.deriveFont(f.getStyle() | Font.BOLD));
        return lab;
    }

    /**
     * Add zero to left to a number, if numString parameter is under of 10
     * @param numString The number to add zero to left, if it necessary...
     * @return String with number that zero added to left, in case that numString param is under of 10
     * @author Oscar Membrilla Estorach
     */
    private String addZeroLeft(String numString) {
        int num = Integer.valueOf(numString);
        if (num < 10) {
            return "0" + numString;
        }
        return numString;
    }

    /**
     * @return the controllerAdminEditDay
     * @author Oscar Membrilla Estorach
     */
    public AdminEditDay getControllerAdminEditDay() {
        if (jFrameAdminEditDay != null) {
            return jFrameAdminEditDay.getController();
        }
       
        return null;
        
    }

}
