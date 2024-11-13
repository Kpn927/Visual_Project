package threads;
import java.io.*;
import java.sql.*;
import java.util.*;

public class DBComponent
{
	private static DatabasePool Pool = DatabasePool.getAttributes();
    private static String url, username, password, queryFile;

    private static void loadConfig()
    {
    	try
    	{
            Properties cfg = new Properties();
            cfg.load(new FileInputStream ("DB.properties"));

            url = cfg.getProperty("db.url");
            username = cfg.getProperty("db.user");
            password = cfg.getProperty("db.password");
            queryFile = cfg.getProperty("db.queryFile");
    	}
    	catch (IOException e) { e.printStackTrace(); }
    }

    public static String loadQuery(String queryKey) 
    {
        try
        {
            Properties cfg = new Properties();
            cfg.load(new FileInputStream (queryFile));

            return cfg.getProperty(queryKey);
        } 
        catch (IOException e)
        {
            e.printStackTrace();
            return null;
        }
    }

    public static Connection createConnection() throws ClassNotFoundException, SQLException 
    {
    	if (url == null)
    		loadConfig();

        Class.forName("org.postgresql.Driver");
        return DriverManager.getConnection(url, username, password);
    }
    
    public static synchronized Connection getConnection(Connection conn)
    {
    	if (conn == null)
    		conn = Pool.obtainConnection();

    	return conn;
    }
    
    public static synchronized void returnConnection(Connection conn)
    {
    	Pool.returnConnection(conn);
    }

    public synchronized void runQuery(String query) 
    {
    	ConnClass.executeQuery(query);
    	ConnClass.releaseConnection();
    }
    
    public static synchronized void closeAllConnections()
    {
    	Pool.closeConnections();
    }
}