import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Objects;

public class User {
	private InetAddress address;
	private int port;
	private String username;
	
	public User(InetAddress address, int port, String username) {
		this.address = address;
		this.port = port;
		this.username = username;
	}
	
	public User(InetAddress address, int port) {
		this(address, port, "");
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getIPAddress() {
		return address.getHostAddress();
	}
	
	public int getPort() {
		return port;
	}
	
	public String getUsername() {
		return username;
	}

	public Socket getSocket() throws IOException {
		return new Socket(address, port);
	}
	
	public String toString() {
		return String.format("%s:%s %s", address, port, username);
	}
	
	public int hashCode() {
		return Objects.hash(getIPAddress(), port);
	}
	
	public boolean equals(Object obj) {
		if (obj instanceof User) {
			User u = (User) obj;
			return (getIPAddress().equals(u.getIPAddress()) && port == u.getPort());
		}
		return false;
	}
}
