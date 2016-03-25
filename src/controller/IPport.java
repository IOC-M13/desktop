//Creat per Oscar Membrilla Estorach

package controller;

import javax.swing.JButton;
import javax.swing.JTextField;
import view.JFrameIPport;

/**
 *
 * @author Oscar
 */
public class IPport {

    JFrameIPport jFrame;
    JTextField tfIP;
    JTextField tfPort;
    JButton btnCancel;
    JButton btnSave;
    
    public IPport(JFrameIPport jFrame) {
        this.jFrame = jFrame;
        tfIP = this.jFrame.getTfIP();
        tfPort = this.jFrame.getTfPort();
        btnCancel = this.jFrame.getBtnCancel();
        btnSave = this.jFrame.getBtnSave();
        
        tfIP.setText(Support.IP);
        tfPort.setText(Support.port);
    }
    
    public void cancel() {
        exit();
    }
    
    public void save() {
        Support.IP = jFrame.getTfIP().getText();
        Support.port = jFrame.getTfPort().getText();
        //Guardos los datos en el fichero config.properties (la IP y puerto)
        FileProperties fp = new FileProperties();
        fp.writeProperties();
        
        exit();
    }
    
    public void exit() {
        jFrame.dispose();
    }
    
}
