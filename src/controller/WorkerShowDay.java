//Creat per Oscar Membrilla Estorach

package controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import view.JFrameWorkerShowDay;

/**
 *
 * @author Oscar
 */
public class WorkerShowDay {

    private JFrameWorkerShowDay jFrame;
    private DBHelper db;
    
    private String dateSelected;
    
    private JLabel date;
    private JLabel user;
    private JLabel shift;
    private JLabel startTime;
    private JLabel endTime;
    
    public WorkerShowDay(JFrameWorkerShowDay jFrame, String dateSelected) {
        this.jFrame = jFrame;
        this.dateSelected = dateSelected;
        this.date = this.jFrame.getjLabelDate();
        this.user = this.jFrame.getjLabelUser();
        this.shift = this.jFrame.getjLabelShift();
        this.startTime = this.jFrame.getjLabelStartTime();
        this.endTime = this.jFrame.getjLabelEndTime();
        
        db = new DBHelper();
        
        loadData();
    }
    
    private void loadData(){
        
        //Cargar la fecha
        date.setText(dateSelected);
        
        //Cargar el usuario
        user.setText(Support.userName);
        
        db.connectDB();
        try {
            //Buscar si existe un turno asignado a este díax
            java.sql.Date sqlDate = db.stringDateToSQLdate(dateSelected);
            ResultSet rs = db.userWithShiftAssigned(user.getText(), sqlDate);

            if (rs.first()) {
                shift.setText(db.getShiftName(rs.getInt(1)));
                
                ResultSet rs2 = db.getShiftsTimesByName(shift.getText());
                rs2.first();
                startTime.setText(rs2.getString(1));
                endTime.setText(rs2.getString(2));
                
            } else {
                shift.setText("-");
                startTime.setText("-");
                endTime.setText("-");
            }
            
        } catch (SQLException ex) {
            System.out.println("Error: " + ex.getMessage());
        } 
        
        
        
    }
    
    
    
}
