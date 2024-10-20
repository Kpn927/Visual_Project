package threads;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class getProperties {
	
    private Properties propiedades;

    public getProperties(String rutaArchivo) {
    	
        propiedades = new Properties();
        
        try (FileInputStream archivo = new FileInputStream(rutaArchivo)) {
        	
            propiedades.load(archivo);
            
        } catch (IOException e) {
        	
            System.out.println("ERROR: Leída del archivo tuvo un problema. ");
            
        }
    }

    public String obtenerPropiedad(String propiedad) {
    	
        return propiedades.getProperty(propiedad);
        
    }
}