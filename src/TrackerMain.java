/**
 * @author alexander
 *
 *         A server application that maintains a list of clients that are
 *         running the client application.
 * 
 *         Clients connect to the server in order to be listed, and are then
 *         able to download the list of clients that the server is aware of.
 *
 */

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Observable;
import java.util.Observer;

import sun.security.ssl.Debug;

public class TrackerMain {

	public static void main(String[] args) {
		new TrackerSystem();
	}
}

/**
 * The server's tracking system. Responsible for managing events.
 */
class TrackerSystem implements Observer {
	private UserMonitor userMonitor;
	private TrackerServerThread server;

	public TrackerSystem() {
		this.userMonitor = new UserMonitor(null);
		userMonitor.addObserver(this);

		server = new TrackerServerThread(userMonitor);
		server.start();
		
		/* THIS SECTION ADDS A TEST USER
		try {
			userMonitor.addUser(new User(InetAddress.getByName("192.168.100.1"), 1234, "Test User"));
		} catch (UnknownHostException e) {
		}
		*/
		
		System.out.println("Tracker Online");
	}

	@Override
	public void update(Observable userMonitor, Object arg) {
		Debug.println("User List", "Updated");
		User user = (User) arg;
		
		// If user was added, they exist in UserMonitor.
		// If user was removed, then they do not exist in UserMonitor.
		if (this.userMonitor.getUsers().contains(user)){
			server.SendUser(user);
		}
		else{
			server.SendRemovedUser(user);
		}
	}
}