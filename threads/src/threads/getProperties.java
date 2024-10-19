package threads;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class getProperties {
    private Properties propiedades;

    public getProperties(String rutaArchivo) {
        propiedades = new Properties();
        try (FileInputStream fis = new FileInputStream(rutaArchivo)) {
            propiedades.load(fis);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String obtenerPropiedad(String clave) {
        return propiedades.getProperty(clave);
    }
}