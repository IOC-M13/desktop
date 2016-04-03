package controller;

import javax.swing.JLabel;
import javax.swing.JPanel;
import view.JFrameWorkerData;

/**
 * Controller class of JFrameWorker JFrame
 * @author Oscar
 */
public class Worker {
    
    private JPanel jPanelYearMonth;
    private JPanel jPanelDays;
    private JPanel jPanelLegend;
    private JLabel jLabelUser;
    
    private CalendarClass calendar;
    private Legend legend;

    /**
     * Constructor of JFrameWorker controller
     * @param jPanelYearMonth JPanel of years and months of a GUI calendar
     * @param jPanelDays JPanel of days of a GUI calendar
     * @param jPanelLegend JPanel of GUI legend
     * @param jLabelUser JLabel of user name
     * @author Oscar Membrilla Estorach
     */
    public Worker(JPanel jPanelYearMonth, JPanel jPanelDays, JPanel jPanelLegend, JLabel jLabelUser) {
        
        this.jPanelYearMonth = jPanelYearMonth;
        this.jPanelDays = jPanelDays;
        this.jPanelLegend = jPanelLegend;
        this.jLabelUser = jLabelUser;
        
        calendar = new CalendarClass(jPanelYearMonth, jPanelDays);
        legend = new Legend(jPanelLegend);
        
        
        
        
        jLabelUser.setText(Support.userName);
        calendar.createCalendar();
        legend.draw();
        
    }
    
    /**
     * Try open JFrame of JFrameWorkerData
     * @author Oscar Membrilla Estorach
     */
    public void openJFrameWorkerData() {
        JFrameWorkerData jFrameWorkerData = JFrameWorkerData.getInstance();
        jFrameWorkerData.setVisible(true);
        jFrameWorkerData.setLocationRelativeTo(null);
        jFrameWorkerData.setLayout(null);
    }
    
    /**
     * Close JFrame of JFrameWorker
     * @author Oscar Membrilla Estorach
     */
    public void exit() {
        System.exit(0);
    }
    
}
