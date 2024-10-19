package threads;

// Imports necesarios para la ejeccución con la base de datos //


public class Clase1 {
	
static class Hilo extends Thread {
		
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
            	e.printStackTrace();
            	synchronized(Hilo.class) {
            		hilos_fallados++;	
            	}                
            }
        }
        
        // OBTENER LOS HILOS QUE ESTAN FALLANDO.
        public static int getHilosFallados() {
        	int hilos_fallados_return = hilos_fallados;
        	hilos_fallados=0;
            return hilos_fallados_return;
        }

        public static int getHilosCompletados() {
        	int hilos_completados_return = hilos_funcionando;
        	hilos_funcionando=0;
            return hilos_completados_return;
        }
    }
	
	public static void main(String[] args) {
				
			int[] cantidad_funcionando = new int[4];
			int[] cantidad_fallados = new int[4];
			long[] times = new long[5];
			
			
			String numero_hilos = "1";
			System.out.println("prueba 1");
			
			for (int i = 0; i<3; i++) {
				times[i] -= System.currentTimeMillis();
				numero_hilos+= "0";
				System.out.println("prueba 2");
				int numero_hilos_int = Integer.valueOf(numero_hilos);
				
				Hilo hilos[] = new Hilo[numero_hilos_int];
				
				for (int j = 0; j<numero_hilos_int;j++) {
					Hilo hilo = new Hilo();
					hilo.start();
					hilos[j]= hilo;
				}
				
				// uso de for each con arreglo de hilos para permitir
				// que terminen los hilos y luego continúe el código
				for (Hilo hilo : hilos) {
					try {
						hilo.join();
		        	}catch(InterruptedException e) {
		        		e.printStackTrace();
		        	}
				}
				
				
                cantidad_funcionando[i] = Hilo.getHilosCompletados();
                cantidad_fallados[i] = Hilo.getHilosFallados();
                times[i] += System.currentTimeMillis();
				
			}
			
			System.out.println("prueba 3");
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			

        	System.out.println("FUNCIONAMIENTOl " + "Completados " + " l " + "Fallados" + " l " + "Tiempo " + "(ms)");
        	System.out.println("10 HILOS      l " + cantidad_funcionando[0] + " l " + cantidad_fallados[0] + " l " + times[0] + "ms");
        	System.out.println("100 HILOS     l " + cantidad_funcionando[1] + " l " + cantidad_fallados[1] + " l " + times[1] + "ms");
        	System.out.println("1000 HILOS    l " + cantidad_funcionando[2] + " l " + cantidad_fallados[2] + " l " + times[2] + "ms");
        	System.out.println("10000 HILOS   l " + cantidad_funcionando[3] + " l " + cantidad_fallados[3] + " l " + times[3] + "ms");
        	System.out.println("100000 HILOS  l " + Hilo.getHilosCompletados() + " l " + Hilo.getHilosFallados() + " l " + times[4] + "ms");
	}
}

