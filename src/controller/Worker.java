//Creat per Oscar Membrilla Estorach

package controller;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

/**
 *
 * @author Oscar
 */
public class Worker {

    public Worker(JPanel jPanel) {
        
        List<JLabel> jLabel = new ArrayList<>();
        
        DBHelper db = new DBHelper();
        db.connectDB(Support.IP, Support.port);
        
        ResultSet rs = db.getNameAndColorShifts();
        
        try {
            int i = 0;
            // Iterar por los resultados
            while (rs.next()) {
                jLabel.add(new JLabel());
                jLabel.add(new JLabel(rs.getString(2)));
                
                jLabel.get(i).setBackground(Color.decode("#" + rs.getString(1)));
                jLabel.get(i).setOpaque(true);
                
                jPanel.add(jLabel.get(i));
                jPanel.add(jLabel.get(i + 1));
                
                i += 2;
                
            }
        } catch (SQLException ex) {
            Logger.getLogger(Worker.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        GridLayout layout = new GridLayout(jLabel.size() / 2, 2, 10, 5);
        
        jPanel.setLayout(layout);
        
        jPanel.updateUI();
        
    }
    
}
