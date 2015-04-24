import java.net.InetAddress;

public class User {
	private InetAddress address;
	private int port;
	private String username;
	
	public User(InetAddress address, int port, String username) {
		this.address = address;
		this.port = port;
		this.username = username;
	}
	
	public InetAddress getAddress() {
		return address;
	}
	
	public int getPort() {
		return port;
	}
	
	public String getUsername() {
		return username;
	}
	
	public String toString() {
		return String.format("%s:%s %s", address, port, username);
	}
	
	public int hashCode() {
		return (address + Integer.toString(port)).hashCode();
	}
	
	public boolean equals(Object obj) {
		if (obj instanceof User) {
			User u = (User) obj;
			return (address.equals(u.getAddress()) && port == u.getPort());
		}
		return false;
	}
}
