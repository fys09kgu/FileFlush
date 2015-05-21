package system;
import java.io.File;
import java.util.HashSet;
import java.util.Observable;

public class UserMonitor extends Observable{
	private HashSet<User> users;
	private User owner;
	private int trackerPort = 50001;
	private String trackerHost = "localhost";
	private File directory;
	
	public UserMonitor(User owner) {
		users = new HashSet<User>();
		this.owner = owner;
		directory = new File("FileFlush Downloads");
		directory.mkdir();
	}

	public HashSet<User> getUsers() {
		return users;
	}
	
	public synchronized void addUser(User u) {
		if(u.equals(this.owner)) return;
		else{
			users.add(u);
			setChanged();
			notifyObservers(u);
		}
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

	public String getTrackerHost() {
		return trackerHost;
	}

	public int getTrackerPort() {
		return trackerPort;
	}

	public void setTrackerAddress(String host, int port) {
		this.trackerHost = host;
		this.trackerPort = port;
		
		setChanged();
		notifyObservers("Server");
	}

	public File getDirectory() {
		return directory;
	}
	
	public void setDirectory(File directory) {
		this.directory = directory;
	}
}
