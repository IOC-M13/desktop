package controller;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Class that contain all necessary to generate a GUI Legend
 * @author Oscar Membrilla Estorach
 */
public class Legend  {
    
    private static final String SIZE_COLOR = "     ";
    private JPanel jPanel;

    /**
     * Constructor of Legend with a JPanel parameter
     * @param jPanel JPanel that draw the gui legend
     * @author Oscar Membrilla Estorach
     */
    public Legend(JPanel jPanel) {
        
        this.jPanel = jPanel;
        
    }
    
    /**
     * Draw gui legend on the jPanel attribute
     * @author Oscar Membrilla Estorach
     */
    public void draw() {
        
        jPanel.removeAll();
        
        List<JLabel> jLabel = new ArrayList<>();
        Box col = Box.createVerticalBox();
        Box col2 = Box.createVerticalBox();
        
        DBHelper db = new DBHelper();
        db.connectDB();
        
        ResultSet rs = db.getNameAndColorShifts();
        
        try {
            int i = 0;
            // Iterar por los resultados
            while (rs.next()) {
                
                if (rs.isFirst()) {
                    col.add(Box.createRigidArea(new Dimension(10,0)));
                }
                
                jLabel.add(new JLabel(SIZE_COLOR));
                jLabel.get(i).setBackground(Color.decode("#" + rs.getString(1)));
                jLabel.get(i).setOpaque(true);
                col.add(jLabel.get(i));
                
                //jPanel.updateUI();
                
                jLabel.add(new JLabel(rs.getString(2)));
                col2.add(jLabel.get(i + 1));
                
                //jPanel.updateUI();
                
                if (!rs.isLast()) {
                    col.add(Box.createRigidArea(new Dimension(0,5)));
                    col2.add(Box.createRigidArea(new Dimension(0,5)));
                }
                
                i += 2;
                
            }
        } catch (SQLException ex) {
            Logger.getLogger(Worker.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        Box row = Box.createHorizontalBox();
        row.add(col);
        row.add(Box.createRigidArea(new Dimension(5,0)));
        row.add(col2);
        
        /*
        //Poner en el centro
        jPanel.setLayout(new BoxLayout(jPanel, BoxLayout.Y_AXIS));
        jPanel.add(Box.createVerticalGlue());
        jPanel.add(row);
        jPanel.add(Box.createVerticalGlue());
        */
        
        /*
        //Lo mismo que hay ahora
        //jPanel1.setLayout(new BorderLayout());
        //jPanel1.add(row, BorderLayout.CENTER);
        */
        
        /*
        //Para centrar en el medio del JPanel, igual que el primer ejemplo
        //jPanel1.setLayout(new GridBagLayout());
        //jPanel1.add(row);
        */
        
        jPanel.setLayout(new BorderLayout());
        jPanel.add(row);
        
        jPanel.updateUI();
    }
    
}
