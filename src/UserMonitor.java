import java.util.HashSet;


public class UserMonitor {
	private HashSet<User> users;
	
	public UserMonitor() {
		users = new HashSet<User>();
	}

	public HashSet<User> getUsers() {
		return users;
	}
	
	public synchronized void addUser(User u) {
		users.add(u);
	}
}
