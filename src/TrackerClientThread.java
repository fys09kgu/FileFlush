import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

/**
 * @author alexander
 *
 *         Manages the client's connection to the Tracking server. Processes
 *         received information to add and remove elements in the UserMonitor.
 *
 */
public class TrackerClientThread extends Thread {

	private UserMonitor userMonitor;
	private Socket socket;

	/**
	 * Initiates a client-to-tracker thread.
	 * 
	 * @param userMonitor The local UserMonitor.
	 */
	public TrackerClientThread(UserMonitor userMonitor) {
		this.userMonitor = userMonitor;
	}

	public void run() {
		BufferedOutputStream os = null;
		
		try {
			// Start the connection
			socket = new Socket("test", 50001);
			
			// Get streams
			os = new BufferedOutputStream(socket.getOutputStream());
			Header header = new Header(new BufferedInputStream(socket.getInputStream()));
			
			// Parse incoming users
			while(!socket.isClosed()){
				header.parseHeader();
				if(header.getType() == 1){
					userMonitor.addUser(header.parseUser());
				}
				else{
					socket.close();
					throw new IOException("Malformed user list from tracker");
				}
			}
			
			socket.close();
		}
		catch (UnknownHostException e) {
			System.out.println("Failed to connect to Tracker");
		}
		catch (SocketException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}

	}
}
