package threads;
import java.sql.*;

public class PoolManager {
	
	private DatabasePool object;
	private Connection con = null;
	
	public PoolManager(){
		this.object = DatabasePool.getAttributes();
	}
	
	public synchronized Connection getConnection()
    {
    	if (con == null)
    		this.con = object.obtainConnection();

    	return con;
    }
	
	public synchronized void runThread(String query)
    {
        Statement st = null;
        ResultSet rs = null;

		try 
		{
			con = getConnection();
			st = con.createStatement();
			rs = st.executeQuery(query);

	        while (rs.next())
	        {
		        String product = rs.getString(2);
		        System.out.println(product);
	        }
		}
		catch (SQLException e) { 
			e.printStackTrace(); 
		}
		finally 
		{
            try { 
            	if (rs != null) 
            		rs.close(); 
            	} catch ( SQLException e) { }
            
            try { if (st != null) 
            	st.close(); 
            	} catch (SQLException e) { }
            
            if (con != null)
            {
            	object.returnConnection(con);
            	con = null;
            }
		}
    }
}
