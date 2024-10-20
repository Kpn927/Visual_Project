package threads;

import java.sql.*;

public class DBConnection {
	
	private Connection conn;
	String user;
	String url;
	String password;
	
	public DBConnection(){
		
		getProperties config = new getProperties("DB.properties");
		user = config.obtenerPropiedad("user");
		url = config.obtenerPropiedad("DBurl");
		password = config.obtenerPropiedad("password");
		
	}
	
	public void exeQuery(String query) {
		try {
			
        	conn = DriverManager.getConnection(url, user, password);
        	
            if (conn != null) {
            	
                Statement st = conn.createStatement();
                st.setFetchSize(0);
                
                ResultSet rs = st.executeQuery(query);
                while(rs.next()) {
                	
                	System.out.print(rs.getString(1) + "  ");
                	System.out.println(rs.getString(2));
                
                }
                
                st.close();
                rs.close();
                conn.close();
                
            } else {
            	
                System.out.println("ERROR: La conexión ha fallado.");
                
            }	
        } catch (SQLException e) {
        	
        	throw new Error(e.getMessage());
        	
        }	
	}	
}
