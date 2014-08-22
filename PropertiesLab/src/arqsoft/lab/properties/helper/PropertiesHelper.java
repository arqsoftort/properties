package arqsoft.lab.properties.helper;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesHelper {
    
    /**
     * Reads the value of a key in a properties file
     * @param filename Name of the file to read (including path if necessary)
     * @param key Name of the key to look for in the file
     * @return Value of the key in the file (or null if not found)
     */
    public static String read(String filename, String key) {
        
        String value = null;
        Properties prop = new Properties();
	InputStream input = null;
 
	try {
            String path = filename + ".properties";
            input = new FileInputStream(path);
            prop.load(input);
            value = prop.getProperty(key);
	} catch (IOException ex) {
            ex.printStackTrace();
	} finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
	}
        
        return value;
    }
    
}