package system;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class Find extends Thread {

	public static final String MC_ADDRESS = "experiment.mcast.net";
	public static final int MC_PORT = 4095;
	
	private User user;

	public void setUser(User user) {
		this.user = user;
	}

	public void run() {
		MulticastSocket ms = null;
		try {
			ms = new MulticastSocket();
			ms.setTimeToLive(1);
			InetAddress ia = InetAddress.getByName(MC_ADDRESS);
			byte[] buf = Header.createFindUserHeader(user);
			DatagramPacket dp = new DatagramPacket(buf, buf.length, ia, MC_PORT);
			ms.send(dp);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			ms.close();
		}
	}
}
