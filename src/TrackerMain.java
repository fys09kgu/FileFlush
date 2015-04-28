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

	public TrackerSystem() {
		this.userMonitor = new UserMonitor();
		userMonitor.addObserver(this);

		TrackerServerThread server = new TrackerServerThread(userMonitor);
		server.start();
	}

	@Override
	public void update(Observable userMonitor, Object arg) {
		User user = (User) arg;

		// TODO: Check if user was added or removed.
		// If user was added, they exist in UserMonitor.
		// If user was removed, then they do not exist in UserMonitor.

		// TODO: Transmit user addition to all clients.

		// TODO: Transmit user deletion to all clients?
	}
}