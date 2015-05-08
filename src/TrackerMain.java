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

import java.util.Observable;
import java.util.Observer;

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
	}

	@Override
	public void update(Observable userMonitor, Object arg) {
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