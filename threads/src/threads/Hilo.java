package threads;


public class Hilo extends Thread{
	
	private static int hilos_fallados = 0, hilos_funcionando = 0;
    
    // se utiliza @OVERRIDE RUN Para sobre escribir la función que está en THREAD ya definida.
	
    @Override
    public void run() {
        try {
        	
        	DBConnection conn = new DBConnection();
        	getProperties config = new getProperties("DB.properties");
    		String query = config.obtenerPropiedad("query");
        	conn.exeQuery(query);
                
            synchronized(Hilo.class) {
            	// SE USA SYNCHRONIZED PARA EVITAR CASOS DE MULTIPLES HILOS INTENTANDO ACCEDER AL INCREMENTO DE ESTE.
                hilos_funcionando++;
                
            }
            
        } catch (Error e) {
        	
        	// SE USA SYNCHRONIZED PARA EVITAR CASOS DE MULTIPLES HILOS INTENTANDO ACCEDER AL INCREMENTO DE ESTE.
        	System.out.println("Error de conexion en hilo");
        	synchronized(Hilo.class) {
        		
        		hilos_fallados++;	
        		
        	}                
        }
    }
    
    // OBTENER LOS HILOS QUE ESTAN FALLANDO.
    public static int getHilosFallados() {
    	int hilos_fallados_return = hilos_fallados;
    	
    	hilos_fallados = 0;
    	
        return hilos_fallados_return;
    }

    public static int getHilosCompletados() {
    	int hilos_completados_return = hilos_funcionando;
    	
    	hilos_funcionando = 0;
    	
        return hilos_completados_return;
    }
}
