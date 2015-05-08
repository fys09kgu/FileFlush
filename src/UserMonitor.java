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
	
	public String[] getUserList() {
		HashSet<User> users = getUsers();
		String[] usernames = new String[users.size()];
		int i = 0;
		for (User u : users) {
			usernames[i++] = u.getUsername();
		}
		return usernames;
	}
}
