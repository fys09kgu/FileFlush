import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.Socket;

public class ListenFind extends Thread {
	
	private User user;

	public void run() {
		MulticastSocket ms = null;
		try {
			ms = new MulticastSocket(Find.MC_PORT);
			InetAddress ia = InetAddress.getByName(Find.MC_ADDRESS);
			ms.joinGroup(ia);
			while (true) {
				byte[] buf = new byte[65536];
				DatagramPacket dp = new DatagramPacket(buf, buf.length);
				ms.receive(dp);
				String s = new String(dp.getData(), 0, dp.getLength());
				System.out.println("Received UDP packet: " + s);
				
				// TODO: check that the packet we received is what we expected
				
				String[] address = s.split(":");
				Socket socket = new Socket(address[0].trim(), Integer.parseInt(address[1].trim()));

				BufferedOutputStream os = new BufferedOutputStream(socket.getOutputStream());
				os.write(Header.createUserHeader(user));
				os.flush();
				os.close();
				socket.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			ms.close();
		}
	}

	public void setUser(User user) {
		this.user = user;
	}

}
