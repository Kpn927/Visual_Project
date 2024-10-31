package threads;

import java.util.*;

import java.io.*;
import java.sql.*;

public class DatabasePool {
	
	// NIC: NUMERO INICIAL DE CONEXIONES.
	// NMC: NUMERO MAXIMO DE CONEXIONES.
	// NC: NUMERO DE INCREMENTO.
	private static int nic, nmc, nc, contador;
	private static String url, user, password;
	private static DatabasePool object;
	private static ArrayList<Connection> pool;
	
	public static int GetCounter() {
		return contador;
	}
	
	private DatabasePool(){
		Properties config = new Properties();
		
		try {
			
			config.load(new FileInputStream ("DB.properties")); 
			
		}
		catch (IOException e) { 
			
			e.printStackTrace(); 
			
		}
		
		url = config.getProperty("DBurl");
		user = config.getProperty("user");
		password = config.getProperty("password");
		nic = Integer.parseInt(config.getProperty("nic"));
		nmc = Integer.parseInt(config.getProperty("nmc"));
		nc = Integer.parseInt(config.getProperty("nc"));
		
		pool = new ArrayList<Connection>(nic);
		
		for (int i = 0; i < nic; i++)
		{
			try { 
				pool.add(DBConnection());
			}
			catch (SQLException e) 
			{ 
				System.out.println("Error creando instancia"); 
			}
		}
		System.out.println(contador);
		
	}
	
	public Connection DBConnection() throws SQLException {
		contador++;
		return DriverManager.getConnection(url, user, password);
		
	}
	
	public static synchronized DatabasePool getAttributes()
	{
		if (object == null)
			object = new DatabasePool();
		return object;
	}
	
	public synchronized Connection obtainConnection()
	{
		if (pool.isEmpty() && contador < nmc )
		{
			for (int i = 0; i < nc;)
			{
				try
				{
					pool.add(DBConnection());
					i++;
				}
				catch (SQLException e) { 
					System.out.println("Error creando nuevas conexiones"); 
				}
			}
		}
			
		while (pool.isEmpty())
		{
			try { 
				wait(); 
			}
			catch (InterruptedException e) { 
				System.out.println("Error esperando hilos"); 
			}
		} 
			
		return pool.remove(pool.size() - 1);
	}
	
	public synchronized void returnConnection(Connection conn)
	{
		pool.add(conn);
		notifyAll();
	}
	
	public synchronized void closeConnections()
	{
		System.out.println("Pool Size: " + pool.size());

		for (Connection conn : pool)
		{
			try
			{
				if ((conn != null) && (!conn.isClosed()))
					conn.close();
			}
			catch (SQLException e) { 
				System.out.println("Error cerrando hilos"); 
			}
		}

		pool.clear();
	}
	
		
		
	
}
