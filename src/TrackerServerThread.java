import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 *         Maintains the server socket of the Tracker application.
 */

public class TrackerServerThread extends Thread {
	public static final int SERVER_PORT = 50001;
	private UserMonitor userMonitor;
	private ArrayList<TrackerClientConnection> clients;

	public TrackerServerThread(UserMonitor userMonitor) {
		this.userMonitor = userMonitor;
	}

	public void run() {
		ServerSocket serverIn = null;
		try {
			serverIn = new ServerSocket(SERVER_PORT);
			Socket connectionSocket = null;
			while ((connectionSocket = serverIn.accept()) != null) {
				TrackerClientConnection conn = new TrackerClientConnection(connectionSocket, userMonitor, clients);
				clients.add(conn);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				for (TrackerClientConnection conn : clients){
					conn.Terminate();
				}
				serverIn.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Sends a user to all connected clients.
	 * 
	 * @param user User to send
	 */
	public void SendUser(User user) {
		for (TrackerClientConnection conn : clients){
			// Skip sending updates on a user to that user.
			if (conn.getUser().equals(user)) continue;
			
			try {
				conn.SendUser(user);
			} catch (IOException e) {
				System.out.println("A connection error occured.");
				continue;
			}
		}
	}
	
	public void SendRemovedUser(User user) {
		// TODO Auto-generated method stub
	}

	/**
	 *	Represents a connection to a client.
	 *	Is responsible for I/O to a client.
	 */
	private class TrackerClientConnection extends Thread {
		private User user;
		private UserMonitor userMonitor;
		private Socket connectionSocket;
		private BufferedOutputStream os;
		private ArrayList<TrackerClientConnection> clients;

		/**
		 * Initializes a client connection from an existing socket.
		 * Must run Start() on this object to continue execution.
		 * 
		 * @param connectionSocket	The socket used for the connection.
		 * @param userMonitor	The UserMonitor object that keeps a record of users.
		 * @param clients	The List of Clients tracked by the server.
		 */
		public TrackerClientConnection(Socket connectionSocket, UserMonitor userMonitor, ArrayList<TrackerClientConnection> clients) {
			this.connectionSocket = connectionSocket;
			this.userMonitor = userMonitor;
			this.clients = clients;
		}
		
		public void run(){
			try {
				BufferedInputStream in = new BufferedInputStream(connectionSocket.getInputStream());
				os = new BufferedOutputStream(connectionSocket.getOutputStream());

				Header header = new Header(in);
				try {
					header.parseHeader();
				} catch (IOException e) {
					System.out.println(e);
				}
				int type = header.getType();
				switch (type) {
				case Header.TYPE_FILE:
					throw new IOException("File transfer attempted. Not supported.");
				case Header.TYPE_USER:
					this.user = header.parseUser();
					SendAllUsers();
					userMonitor.addUser(user);
					break;
				}
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				clients.remove(this);
				if (!this.user.equals(null)) {
					this.userMonitor.removeUser(this.user);
				}
			}
		}

		/**
		 * Sends all users in UserMonitor to this connection's peer.
		 * 
		 * @throws IOException
		 */
		synchronized public void SendAllUsers() throws IOException {
			String output = "";
			for (User u : userMonitor.getUsers()) {
				output.concat(u.toString() + ";");
			}
			os.write(output.getBytes());
		}

		
		/**
		 * Sends a single user to this connection's peer.
		 * 
		 * @param u	The user to transmit.
		 * @throws IOException
		 */
		synchronized public void SendUser(User u) throws IOException {
			os.write((u.toString() + ";").getBytes());
		}
		
		/**
		 * Terminate the connection to this client and removes it from the client and user lists.
		 * @throws IOException 
		 */
		public void Terminate() throws IOException{
			clients.remove(this);
			this.userMonitor.removeUser(this.user);
			connectionSocket.close();
		}
		
		/**
		 * Gets the user object associated with this connection.
		 * 
		 * @return User object representing this connection's peer.
		 */
		public User getUser(){
			return this.user;
		}
	}
}
