package threads;
import java.io.*;
import java.util.*;



public class ClasePrincipal {

	public static void main(String[] args) {
		
		int contador = 0;
		String query;
		Properties config = new Properties();
		DatabasePool pool = DatabasePool.getAttributes();
		ConexionHilo[] con = new ConexionHilo[1000];
		
		try {
			config.load(new FileInputStream ("DB.properties")); 
			
			query = config.getProperty("query");
			
			for(int i = 0; i < con.length ; i++) {
				
				con[i] = new ConexionHilo(query);
				con[i].start();
				contador++;
			}
			
			for(ConexionHilo Hilo : con)
				Hilo.join();
				pool.closeConnections();
				System.out.println("Number of Threads: " + contador);
			
		} catch(IOException | InterruptedException e){ System.out.println("Error en main");  }
		
		System.out.println(DatabasePool.GetCounter());
	}

}
