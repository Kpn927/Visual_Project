package threads;
import java.util.*;
import java.io.*;
import java.sql.*;

public class DatabasePool
{
	private static int nic, nmc, nc, count;
	private static DatabasePool object;
	private static ArrayList<Connection> poolList;

	private DatabasePool()
	{
		Properties cfg = new Properties();
				
		try { cfg.load(new FileInputStream ("DB.properties")); }
		catch (IOException e) { e.printStackTrace(); }

		nic = Integer.parseInt(cfg.getProperty("pool.nic"));
		nmc = Integer.parseInt(cfg.getProperty("pool.nmc"));
		nc = Integer.parseInt(cfg.getProperty("pool.nc"));
		
		poolList = new ArrayList<Connection>(nic);

		for (int i = 0; i < nic; i++)
		{
			try
			{
				poolList.add(DBComponent.createConnection());
				count++;
			}
			catch (SQLException | ClassNotFoundException e) { e.printStackTrace(); }
		}
	}

	public static synchronized DatabasePool getAttributes()
	{
		if (object == null)
			object = new DatabasePool();

		return object;
	}
    
	public synchronized Connection obtainConnection()
	{
		if (poolList.isEmpty() && count < nmc && count >= nic)
		{
			for (int i = 0; i < nc;)
			{
				try
				{
					poolList.add(DBComponent.createConnection());
					count++;
					i++;
				}
				catch (SQLException | ClassNotFoundException e) { e.printStackTrace(); }
			}
		}
			
		while (poolList.isEmpty())
		{
			try { wait(); }
			catch (InterruptedException e) { e.printStackTrace(); }
		}
			
		return poolList.removeLast();
	}
	
	public synchronized void returnConnection(Connection conn)
	{
		poolList.add(conn);
		notifyAll();
	}

	public synchronized void closeConnections()
	{
		System.out.println("Pool Size: " + poolList.size());

		for (Connection conn : poolList)
		{
			try
			{
				if ((conn != null) && (!conn.isClosed()))
					conn.close();
			}
			catch (SQLException e) { e.printStackTrace(); }
		}

		poolList.clear();
	}
}