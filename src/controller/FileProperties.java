//Creat per Oscar Membrilla Estorach

package controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
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
    
    public FileProperties() {
        prop = new Properties();
        in = null;
    }
    
    public void loadProperties() {
        
        try {
            in = new FileInputStream("src" + File.separator + "resources" + File.separator + "config.properties");
            
            //Cargar el fichero de propiedades
            prop.load(in);
            
            //Obtener las propiedades y almacenarlas en una variable statica y final
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
    
    
    
    
    public void closeProperties() {
        
    }
    

}
