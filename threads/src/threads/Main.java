package threads;

import java.util.Scanner;
import java.util.InputMismatchException;

public class Main extends Thread {
    private DBComponent component = new DBComponent();
    private static String query;
    public static int dbIndex;

    @Override
    public void run() {
        if (query != null) {
            component.runQuery(query);
        } else {
            System.out.println("ERROR: El hilo no se ejecutó!");
        }
    }

    public static void main(String[] args) {
    	int opc, count_threads;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Quiere conectarse a ambas bases de datos o solo a una.");
        System.out.println("1. Solo una. ");
        System.out.println("2. Ambas. ");
        opc = scanner.nextInt();
        
        if(opc == 1) {
        	
        	 while (true) {
                 try {
                     System.out.println("Indique a cual quiere conectarse.");
                     dbIndex = scanner.nextInt();
                     scanner.nextLine();
                     break;
                 } catch (InputMismatchException e) {
                     System.out.println("Entrada no válida. Por favor, ingrese un número.");
                     scanner.next();
                 }
             }

             while (true) {
                 try {
                     System.out.println("Indique cuantos hilos quiere hacer.");
                     count_threads = scanner.nextInt();
                     scanner.nextLine();
                     break;
                 } catch (InputMismatchException e) {
                     System.out.println("Entrada no válida. Por favor, ingrese un número.");
                     scanner.next();
                 }
             }
            DBComponent.loadConfig();
            query = DBComponent.loadQuery("db.query");

            int cont = 0;
            Main[] conn = new Main[count_threads];

            try {
                for (int i = 0; i < conn.length; i++) {
                    conn[i] = new Main();
                    conn[i].start();
                    cont++;
                }

                for (Main thread : conn) {
                    thread.join();
                }

                DBComponent.closeAllConnections();
                System.out.println("Number of Threads: " + cont);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        } else if (opc == 2) {
        	
        	while (true) {
                try {
                    System.out.println("Indique cuantos hilos quiere hacer para la primera base de datos");
                    count_threads = scanner.nextInt();
                    scanner.nextLine();
                    break;
                } catch (InputMismatchException e) {
                    System.out.println("Entrada no válida. Por favor, ingrese un número.");
                    scanner.nextLine();
                }
            }
        	
        	dbIndex = 0;
        	
        	DBComponent.loadConfig();
            query = DBComponent.loadQuery("db.query");

            int cont = 0;
            Main[] conn = new Main[count_threads];

            try {
                for (int i = 0; i < conn.length; i++) {
                    conn[i] = new Main();
                    conn[i].start();
                    cont++;
                }

                for (Main thread : conn) {
                    thread.join();
                }

                DBComponent.closeAllConnections();
                System.out.println("Number of Threads: " + cont);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        	
            while (true) {
                try {
                    System.out.println("Indique cuantos hilos quiere hacer para la segunda base de datos");
                    count_threads = scanner.nextInt();
                    scanner.nextLine();
                    break;
                } catch (InputMismatchException e) {
                    System.out.println("Entrada no válida. Por favor, ingrese un número.");
                    scanner.next();
                }
            }
        	
        	dbIndex = 1;
        	
        	DBComponent.loadConfig();
            query = DBComponent.loadQuery("db.query");

            cont = 0;
            Main[] conn1 = new Main[count_threads];

            try {
                for (int i = 0; i < conn1.length; i++) {
                    conn1[i] = new Main();
                    conn1[i].start();
                    cont++;
                }

                for (Main thread : conn1) {
                    thread.join();
                }

                DBComponent.closeAllConnections();
                System.out.println("Number of Threads: " + cont);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        scanner.close();
    }
}
