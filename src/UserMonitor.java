import java.util.HashSet;
import java.util.Observable;

public class UserMonitor extends Observable{
	private HashSet<User> users;
	
	public UserMonitor() {
		users = new HashSet<User>();
	}

	public HashSet<User> getUsers() {
		return users;
	}
	
	public synchronized void addUser(User u) {
		users.add(u);
		notifyObservers(u);
	}
	
	public synchronized void removeUser(User u) {
		users.remove(u);
		notifyObservers(u);
	}
}
