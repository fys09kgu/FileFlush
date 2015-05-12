import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

import sun.security.ssl.Debug;

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
			socket = new Socket("localhost", 50001);
			
			Debug.println("CT", "Connected to tracker");
			
			// Get streams
			os = new BufferedOutputStream(socket.getOutputStream());
			Header header = new Header(new BufferedInputStream(socket.getInputStream()));
			
			Debug.println("CT", "Streams created");
			
			os.write(Header.createUserHeader(owner));
			os.flush();
			
			Debug.println("CT", "Sent user header");
			
			// Parse incoming users
			while(!socket.isClosed()){
				header.parseHeader();
				Debug.println("CT", "Header recieved");
				if(header.getType() == 1){
					userMonitor.addUser(header.parseUser());
				}
				else{
					socket.close();
					throw new IOException();
				}
			}
		}
		catch (UnknownHostException e) {
			System.out.println("Failed to resolve Tracker Address");
			return;
		}
		catch (SocketException e) {
			System.out.println("No connection to Tracker");
		}
		catch (IOException e){
			System.out.println("Malformed user data from Tracker");
		}
	}
}
