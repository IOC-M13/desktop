package controller;

import java.awt.event.KeyEvent;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import view.JFrameAdmin;
import view.JFrameIPport;
import view.JFrameLogin;
import view.JFrameWorker;

/**
 * Controller class of JFrameLogin JFrame 
 * Do all the logic of JFrameLogin JFrame
 * @author Oscar Membrilla Estorach
 */
public class Login {

    private JFrameLogin jFrame;
    
    private JTextField tfUserName;
    private JPasswordField pfPass;
    private JButton btnClearAll;
    private JButton btnSignIn;
    
    /**
     * Constructor of JFrameLogin controller
     * @param jFrame JFrame of JFrameLogin
     * @author Oscar Membrilla Estorach
     */
    public Login(JFrameLogin jFrame) {
        this.jFrame = jFrame;
        tfUserName = jFrame.getTfUserName();
        pfPass = jFrame.getPfPass();
        btnClearAll = jFrame.getBtnClearAll();
        btnSignIn = jFrame.getBtnSignIn();
        
        //Obtener los datos del fichero config.properties (la IP y puerto)
        FileProperties fp = new FileProperties();
        fp.loadProperties();
        
    }
    
    /**
     * Delete all text of Swing components in the JFrameLogin
     * @author Oscar Membrilla Estorach
     */
    public void clearAll() {
        tfUserName.setText("");
        pfPass.setText("");
        btnSignIn.transferFocus();
    }
    
    /**
     * Try login to DB
     * @author Oscar Membrilla Estorach
     */
    public void signIn() {
        DBHelper db = new DBHelper();
        
        try {
            
            db.connectDB();
        
            String textUser = tfUserName.getText();
            String textPass = String.valueOf(pfPass.getPassword());

            int stateLogin = db.login(textUser, textPass);
            
            if (stateLogin > 0) {
                JOptionPane.showMessageDialog(null, "Ha entrado correctamente!");
                jFrame.setVisible(false);
                Support.userName = textUser;
                
                if (stateLogin == 1) {
                    JFrameWorker jFrameWorker = new JFrameWorker();
                    jFrameWorker.setVisible(true);
                    jFrameWorker.setLocationRelativeTo(null);
                    jFrameWorker.setLayout(null);
                } else {
                    Support.isAdmin = true;
                    JFrameAdmin jFrameAdmin = new JFrameAdmin();
                    jFrameAdmin.setVisible(true);
                    jFrameAdmin.setLocationRelativeTo(null);
                    jFrameAdmin.setLayout(null);
                }
                
                jFrame.dispose();
                
            } else {
                JOptionPane.showMessageDialog(null, "El usuario y/o contraseña es/son incorrecto/s");
            } 
        
        } catch (NullPointerException e) {
            //System.out.println("Error en: " + e.printStackTrace());
            e.printStackTrace();
        } finally {
            db.closeDB();
        }
    }
    
    /**
     * Transfer to next swing component when user press Enter with JTextField 
     * of user name is focused
     * @param evt java.awt.event.KeyEvent of JTextField of user name
     * @author Oscar Membrilla Estorach
     */
    public void tfUserNameKeyTyped(java.awt.event.KeyEvent evt) {
        if(evt.getKeyChar() == KeyEvent.VK_ENTER) {
            tfUserName.transferFocus();
        }
    }
    
    /**
     * Do click in button of sign in when user press Enter with JPasswordField 
     * of user password is focused
     * @param evt java.awt.event.KeyEvent of JPasswordField of user pass
     * @author Oscar Membrilla Estorach
     */
    public void pfPassKeyTyped(java.awt.event.KeyEvent evt) {
        if(evt.getKeyChar() == KeyEvent.VK_ENTER) {
            btnSignIn.doClick();
        }
    }
    
    /**
     * Try open JFrame to JFrameIPport
     * @author Oscar Membrilla Estorach
     */
    public void jMenuItemAccessDB() {   
        JFrameIPport jframeIPport = JFrameIPport.getInstance();
        jframeIPport.setVisible(true);
        jframeIPport.setLocationRelativeTo(null);
    }
    
    /**
     * Close JFrame of JFrameLogin
     * @author Oscar Membrilla Estorach
     */
    public void exit() {
        System.exit(0);
    }
    
}
