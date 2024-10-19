package threads;

// Imports necesarios para la ejeccución con la base de datos //


public class Clase1 {
	
	public static void main(String[] args) {
				
			int[] cantidad_funcionando = new int[5];
			int[] cantidad_fallados = new int[5];
			long[] times = new long[5];
			
			
			String numero_hilos = "1";
			
			for (int i = 0; i<5; i++) {
				times[i] -= System.currentTimeMillis();
				numero_hilos+= "0";
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

        	System.out.println("FUNCIONAMIENTOl " + "Completados " + " l " + "Fallados" + " l " + "Tiempo " + "(ms)");
        	System.out.println("10 HILOS      l " + cantidad_funcionando[0] + " l " + cantidad_fallados[0] + " l " + times[0] + "ms");
        	System.out.println("100 HILOS     l " + cantidad_funcionando[1] + " l " + cantidad_fallados[1] + " l " + times[1] + "ms");
        	System.out.println("1000 HILOS    l " + cantidad_funcionando[2] + " l " + cantidad_fallados[2] + " l " + times[2] + "ms");
        	System.out.println("10000 HILOS   l " + cantidad_funcionando[3] + " l " + cantidad_fallados[3] + " l " + times[3] + "ms");
        	System.out.println("100000 HILOS  l " + cantidad_funcionando[4] + " l " + cantidad_fallados[4] + " l " + times[4] + "ms");
	}
}

