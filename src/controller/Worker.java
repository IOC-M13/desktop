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
        
        Legend legend = new Legend(jPanel);
        legend.draw();
        
    }
    
}
