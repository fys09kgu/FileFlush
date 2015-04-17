import java.io.BufferedInputStream;
import java.io.FileOutputStream;
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
			FileOutputStream out = new FileOutputStream(file);
			byte[] buffer = new byte[8192];
			int dataLength = 0;
			while((dataLength = in.read(buffer, 0, buffer.length)) > 0) {
				out.write(buffer, 0, dataLength);
			}
			out.flush();
			out.close();
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	
	
}
