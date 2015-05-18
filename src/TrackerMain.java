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

public class TrackerMain {

	public static void main(String[] args) throws InterruptedException {
		UserMonitor userMonitor = new UserMonitor(null);
		new TrackerMaintainerServer(userMonitor);
		
		while(true){
			Thread.sleep(5000);
			System.out.println("Known Users: " + userMonitor.getUsers().size());
		}
	}
}