package arqsoft.lab.properties;

import arqsoft.lab.properties.helper.PropertiesHelper;

public class Main {

    public static void main(String[] args) {
        
        String filename = "config";
        
        String key = "app.config.skin.name";
        
        String value = PropertiesHelper.read(filename, key);
        
        System.out.println("Value = " + value);
        
    }
    
}
