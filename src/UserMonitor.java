import java.util.HashSet;
import java.util.Observable;

public class UserMonitor extends Observable{
	private HashSet<User> users;
	private User owner;
	
	public UserMonitor(User owner) {
		users = new HashSet<User>();
		this.owner = owner;
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
	
	public User getOwner(){
		return owner;
	}
}
