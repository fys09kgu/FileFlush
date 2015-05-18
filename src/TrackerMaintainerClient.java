import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Observable;
import java.util.Observer;

class TrackerMaintainerClient implements Observer{
	TrackerClientThread track;
	UserMonitor userMonitor;
	User owner;
	
	public TrackerMaintainerClient(UserMonitor userMonitor, User owner){
		this.userMonitor = userMonitor;
		this.owner = owner;
		
		track = new TrackerClientThread(userMonitor, owner);
    	track.start();
    	userMonitor.addObserver(this);
	}
	
	@Override
	public void update(Observable o, Object arg) {
		if (arg.getClass() == String.class && (String) arg == "Server"){
			if (track.isAlive()) track.CloseConnection();
			track = new TrackerClientThread(userMonitor, owner);
	    	track.start();
		}
	}
	
	/**
	 * @author alexander
	 *
	 *         Manages the client's connection to the Tracking server. Processes
	 *         received information to add and remove elements in the UserMonitor.
	 *
	 */
	private class TrackerClientThread extends Thread{

		private UserMonitor userMonitor;
		private Socket socket;
		private User owner;

		/**
		 * Initiates a client-to-tracker thread.
		 * 
		 * @param userMonitor The local UserMonitor.
		 * @param owner 
		 */
		public TrackerClientThread(UserMonitor userMonitor, User owner) {
			this.userMonitor = userMonitor;
			this.owner = owner;
		}

		public void run() {
		BufferedOutputStream os = null;
			try {
				// Start the connection
				socket = new Socket(userMonitor.getTrackerHost(), userMonitor.getTrackerPort());
				
				// Get streams
				os = new BufferedOutputStream(socket.getOutputStream());
				Header header = new Header(new BufferedInputStream(socket.getInputStream()));
				
				os.write(Header.createUserHeader(owner));
				os.flush();
				
				// Parse incoming users
				while(!socket.isClosed()){
					header.parseHeader();
					if(header.getType() == 1){
						userMonitor.addUser(header.parseUser());
					}
					else{
						socket.close();
						throw new IOException("Unexpected data");
					}
				}
			}
			catch (UnknownHostException e) {
				System.out.println("Failed to resolve Tracker Address");
			}
			catch (SocketException e) {
				System.out.println("Tracker: " + e.getMessage());
			}
			catch (IOException e){
				System.out.println(e.getMessage());
			}
		}
		
		public synchronized void CloseConnection(){
			if (socket.isClosed()) return;
			else try {
				socket.close();
			} catch (IOException e) {}
		}
	}
}
