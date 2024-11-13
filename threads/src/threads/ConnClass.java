package threads;
import java.sql.*;

public class ConnClass
{
	private static Connection con = null;
	
	public static synchronized void getConnection()
	{
		con = DBComponent.getConnection(con);
	}
	
    public static synchronized void executeQuery(String query)
    {
    	getConnection();

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