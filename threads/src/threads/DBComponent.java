package threads;
import java.io.*;
import java.sql.*;
import java.util.*;

public class DBComponent
{
	private static DatabasePool Pool = DatabasePool.getAttributes();
    private static String url, username, password, queryFile;
    private static String [] urls, usernames, passwords;
    private static Connection con = null;
    
    
	public static void loadConfig()
    {
    	try
    	{
            Properties cfg = new Properties();
            cfg.load(new FileInputStream ("DB.properties"));
            
            url = cfg.getProperty("db.url");
            urls = url.split(",");
            url = urls[Main.dbIndex];
            
            username = cfg.getProperty("db.user");
            usernames = username.split(",");
            username = usernames[Main.dbIndex];
            
            password = cfg.getProperty("db.password");
            passwords = password.split(",");
            password = passwords[Main.dbIndex];
            

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
            String query = cfg.getProperty(queryKey);
            String[] queryChoose = query.split(",");
            String returnquery = queryChoose[Main.dbIndex];
            
            return returnquery;
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
    	executeQuery(query);
    	releaseConnection();
    }
    
    public static synchronized void closeAllConnections()
    {
    	Pool.closeConnections();
    }
    
    public static synchronized void executeQuery(String query)
    {
    	con = getConnection(con);

		Statement st = null;
		ResultSet rs = null;

        try
        {
			st = con.createStatement();
			rs = st.executeQuery(query);

		    while (rs.next())
		    {
			   String product = rs.getString(2);
			   System.out.println(product);
		    }
		}
		catch (SQLException e) { e.printStackTrace(); }
		finally 
		{
	       try { if (rs != null) rs.close(); } catch (SQLException e) { }
	       try { if (st != null) st.close(); } catch (SQLException e) { }
		}
    }
    
    public static synchronized void releaseConnection()
    {
	   if (con != null)
	   {
	      DBComponent.returnConnection(con);
	      con = null;
	   }
    }
}