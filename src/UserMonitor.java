import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashSet;
import java.util.Observable;

public class UserMonitor extends Observable{
	private HashSet<User> users;
	private User owner;
	private int trackerPort;
	private InetAddress trackerAddress;
	
	public UserMonitor(User owner) {
		users = new HashSet<User>();
		this.owner = owner;
	}

	public HashSet<User> getUsers() {
		return users;
	}
	
	public synchronized void addUser(User u) {
		users.add(u);
		setChanged();
		notifyObservers(u);
	}
	
	public synchronized void removeUser(User u) {
		users.remove(u);
		setChanged();
		notifyObservers(u);
	}
	
	public User getOwner(){
		return owner;
	}
	
	public User[] getUserList() {
		HashSet<User> users = getUsers();
		User[] userList = new User[users.size()];
		int i = 0;
		for (User u : users) {
			userList[i++] = u;
		}
		return userList;
	}

	public InetAddress getTrackerAddress() {
		return trackerAddress;
	}

	public int getTrackerPort() {
		return trackerPort;
	}

	public void setTrackerAddress(String host, int port) {
		try {
			this.trackerAddress = InetAddress.getByName(host);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		this.trackerPort = port;
	}
}
