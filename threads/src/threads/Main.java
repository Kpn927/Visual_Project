package threads;

public class Main extends Thread
{
	private DBComponent component = new DBComponent();
	private static String query;
	
	@Override
	public void run()
	{
		if (query != null)
		{
			component.runQuery(query);
		}
		else
			System.out.println("ERROR: Could not obtain a statement to run!");
	}

    public static void main(String[] args)
    {
    	int cont = 0;
		Main[] conn = new Main[1000];
		query = DBComponent.loadQuery("db.query");

        try
        {
            for (int i = 0; i < conn.length; i++)
            {
            	conn[i] = new Main();
            	conn[i].start();
            	cont++;
            }
            
            for (Main thread : conn)
            	thread.join();
            
            DBComponent.closeAllConnections();
            System.out.println("Number of Threads: " + cont);
        }
        catch (InterruptedException e) { e.printStackTrace(); }
    }
}