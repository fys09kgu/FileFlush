import java.io.File;
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
	
	public String getIPAddress() {
		return address.getHostAddress();
	}
	
	public int getPort() {
		return port;
	}
	
	public String getUsername() {
		return username;
	}
	
	public ClientThread sendFile(File file) throws IOException {
		Socket socket = new Socket(address, port);
		ClientThread client = new ClientThread(socket, file);
		client.start();
		return client;
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
