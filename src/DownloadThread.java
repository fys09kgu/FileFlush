import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.Socket;
import java.io.File;

public class DownloadThread extends Thread {

	private Socket socket;

	public DownloadThread(Socket connectionSocket) {
		this.socket = connectionSocket;
	}
	
	public void run() {
		try {
			BufferedInputStream in = new BufferedInputStream(this.socket.getInputStream());
			File file = new File("test.txt");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	
}
