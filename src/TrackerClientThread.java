import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

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
		this.socket = new Socket();
	}

	public void run() {
		InputStream is = null;
		BufferedOutputStream os = null;
		
		byte[] buffer = new byte[4096];
		
		try {
			is = socket.getInputStream();
			os = new BufferedOutputStream(socket.getOutputStream());
			
			os.flush();
			is.close();
			os.close();
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
