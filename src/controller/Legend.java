//Creat per Oscar Membrilla Estorach

package controller;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Oscar
 */
public class Legend  {
    
    private static final String SIZE_COLOR = "     ";
    private JPanel jPanel;

    public Legend(JPanel jPanel) {
        
        this.jPanel = jPanel;
        
    }
    
    public void draw() {
        List<JLabel> jLabel = new ArrayList<>();
        Box col = Box.createVerticalBox();
        Box col2 = Box.createVerticalBox();
        
        DBHelper db = new DBHelper();
        db.connectDB(Support.IP, Support.port);
        
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
                
                
                jLabel.add(new JLabel(rs.getString(2)));
                col2.add(jLabel.get(i + 1));
                
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
        //jPanel.updateUI();
    }
    
}
