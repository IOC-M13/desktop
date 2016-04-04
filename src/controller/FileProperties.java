package controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.security.CodeSource;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 * Class to manage the file config.properties
 * @author Oscar Membrilla Estorach
 */
public class FileProperties {
    
    private Properties prop;
    private InputStream in;
    private OutputStream out;
    /**
     * 
     * For run with .jar
     * 
     * private String jarDir;
     * 
     */
    
    /**
     * Constructor of FileProperties class
     * @author Oscar Membrilla Estorach
     */
    public FileProperties() {
        prop = new Properties();
        
        /**
         * 
         * For run with .jar
         * 
            CodeSource codeSource = FileProperties.class.getProtectionDomain().getCodeSource();
            File jarFile = null;
            try {
                jarFile = new File(codeSource.getLocation().toURI().getPath());
            } catch (URISyntaxException ex) {
                Logger.getLogger(FileProperties.class.getName()).log(Level.SEVERE, null, ex);
            }
            jarDir = jarFile.getParentFile().getAbsolutePath();
         * 
         **/

    }
    
    /**
     * Read the properties of the config.properties file and put in the 
     * Support.IP and Support.port static variables
     * @author Oscar Membrilla Estorach
     */
    public void loadProperties() {
        
        try {
            in = new FileInputStream("src" + File.separator + "resources" + File.separator + "config.properties");
            /**
             * 
             * For run with .jar
             * 
             * in = new FileInputStream(jarDir + File.separator + "config.properties");
             * 
             **/
            
            //Cargar el fichero de propiedades
            prop.load(in);
            
            //Obtener las propiedades y almacenarlas en una variable estatica
            Support.IP = prop.getProperty("ip");
            Support.port = prop.getProperty("port");
            
            
        } catch (FileNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "Error at read: " + ex.getMessage());
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Error at read: " + ex.getMessage());
        } finally {
            closeProperties();
        }
        
    }
    
    /**
     * Write the properties of app of the config.properties file
     * @author Oscar Membrilla Estorach
     */
    public void writeProperties() {
        try {
            out = new FileOutputStream("src" + File.separator + "resources" + File.separator + "config.properties");
            
            /**
             * 
             * For run with .jar
             * 
             * out = new FileOutputStream(jarDir + File.separator + "config.properties");
             * 
             **/
            
            // Asignar los valores a las propiedades
            prop.setProperty("ip", Support.IP);
            prop.setProperty("port", Support.port);
            
            // Guardar el archivo de propiedades
            prop.store(out, null);
            
            
        } catch (FileNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "Error at write: " + ex.getMessage());
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Error at write: " + ex.getMessage());
        } finally {
            closeProperties();
        }
    }
    
    /**
     * Close the InputStream and OutputStream definded as attributes
     * @author Oscar Membrilla Estorach
     */
    public void closeProperties() {
        if (in != null) {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        
        if (out != null) {
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
}
