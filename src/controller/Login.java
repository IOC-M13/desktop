//Creat per Oscar Membrilla Estorach

package controller;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import view.JFrameAdmin;
import view.JFrameLogin;
import view.JFrameWorker;

/**
 *
 * @author Oscar
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
    
}
