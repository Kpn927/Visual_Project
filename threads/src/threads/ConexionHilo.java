package threads;

public class ConexionHilo extends Thread {
	private String query;
	
	ConexionHilo(String query){
		
		this.query = query;
		
	}
	
	@Override
	public void run() {
		PoolManager manager = new PoolManager();
		manager.runThread(query);
	}
	
}
