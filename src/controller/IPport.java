package controller;

import javax.swing.JButton;
import javax.swing.JTextField;
import view.JFrameIPport;

/**
 * Class that contain the JFrameIPport controller
 * @author Oscar Membrilla Estorach
 */
public class IPport {

    JFrameIPport jFrame;
    JTextField tfIP;
    JTextField tfPort;
    JButton btnCancel;
    JButton btnSave;
    
    /**
     * Constructor of JFrameIPport controller
     * @param jFrame JFrame of JFrameIPport
     * @author Oscar Membrilla Estorach
     */
    public IPport(JFrameIPport jFrame) {
        this.jFrame = jFrame;
        tfIP = jFrame.getTfIP();
        tfPort = jFrame.getTfPort();
        btnCancel = jFrame.getBtnCancel();
        btnSave = jFrame.getBtnSave();
        
        tfIP.setText(Support.IP);
        tfPort.setText(Support.port);
    }
    
    /**
     * Method that execute when user press the cancel button
     * @author Oscar Membrilla Estorach
     */
    public void cancel() {
        exit();
    }
    
    /**
     * Save the properties into config.properties file
     * @author Oscar Membrilla Estorach
     */
    public void save() {
        Support.IP = jFrame.getTfIP().getText();
        Support.port = jFrame.getTfPort().getText();
        //Guardar los datos en el fichero config.properties (la IP y puerto)
        FileProperties fp = new FileProperties();
        fp.writeProperties();
        
        exit();
    }
    
    /**
     * Close JFrame of JFrameIPport
     * @author Oscar Membrilla Estorach
     */
    public void exit() {
        jFrame.dispose();
    }
    
}
