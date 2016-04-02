//Creat per Oscar Membrilla Estorach

package controller;

import javax.swing.JLabel;
import javax.swing.JPanel;
import view.JFrameWorkerData;

/**
 *
 * @author Oscar
 */
public class Worker {
    
    private JPanel jPanelYearMonth;
    private JPanel jPanelDays;
    private JPanel jPanelLegend;
    private JLabel jLabelUser;
    
    private CalendarClass calendar;
    private Legend legend;

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
    
    public void openJFrameWorkerData() {
        JFrameWorkerData jFrameWorkerData = JFrameWorkerData.getInstance();
        jFrameWorkerData.setVisible(true);
        jFrameWorkerData.setLocationRelativeTo(null);
        jFrameWorkerData.setLayout(null);
    }
    
    public void exit() {
        System.exit(0); // Cerrar la aplicación
    }
    
}
