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
 * Clase controller del jframe JFrameLogin. 
 * Se encarga de toda la lógica de la ventana JFrameLogin.
 * 
 */
public class Login {

    private JFrameLogin jFrame;
    
    private JTextField tfUserName;
    private JPasswordField pfPass;
    private JButton btnClearAll;
    private JButton btnSignIn;
    
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
    
    public void clearAll() {
        tfUserName.setText("");
        pfPass.setText("");
        btnSignIn.transferFocus();
    }
    
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
    
    public void tfUserNameKeyTyped(java.awt.event.KeyEvent evt) {
        if(evt.getKeyChar() == KeyEvent.VK_ENTER) {
            tfUserName.transferFocus();
        }
    }
    
    public void pfPassKeyTyped(java.awt.event.KeyEvent evt) {
        if(evt.getKeyChar() == KeyEvent.VK_ENTER) {
            btnSignIn.doClick();
        }
    }
    
    public void jMenuItemAccessDB() {   
        JFrameIPport jframeIPport = JFrameIPport.getInstance();
        jframeIPport.setVisible(true);
        jframeIPport.setLocationRelativeTo(null);
    }
    
    public void exit() {
        System.exit(0);
    }
    
}
