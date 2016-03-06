//Creat per Oscar Membrilla Estorach

package controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Oscar
 */
public class FileProperties {
    
    private Properties prop;
    private InputStream in;
    private OutputStream out;
    
    public FileProperties() {
        prop = new Properties();
    }
    
    public void loadProperties() {
        
        try {
            in = new FileInputStream("src" + File.separator + "resources" + File.separator + "config.properties");
            
            //Cargar el fichero de propiedades
            prop.load(in);
            
            //Obtener las propiedades y almacenarlas en una variable estatica
            Support.IP = prop.getProperty("ip");
            Support.port = prop.getProperty("port");
            
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FileProperties.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(FileProperties.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            closeProperties();
        }
        
    }
    
    public void writeProperties() {
        try {
            out = new FileOutputStream("src" + File.separator + "resources" + File.separator + "config.properties");
            
            // Asignar los valores a las propiedades
            prop.setProperty("ip", Support.IP);
            prop.setProperty("port", Support.port);
            
            // Guardar el archivo de propiedades
            prop.store(out, null);
            
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FileProperties.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(FileProperties.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            closeProperties();
        }
    }
    
    
    public void closeProperties() {
        
    }
    

}
