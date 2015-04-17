import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerThread extends Thread {
	
	private static final int SERVER_PORT = 50000;

	public void run() {
		ServerSocket serverIn = null;
		try {
			 serverIn = new ServerSocket(SERVER_PORT);
			 Socket connectionSocket = null;
			 while ((connectionSocket = serverIn.accept()) != null) {
				 DownloadThread downloader = new DownloadThread(connectionSocket);
				 downloader.start();
			 }
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				serverIn.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
