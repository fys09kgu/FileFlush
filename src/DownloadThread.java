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
		BufferedInputStream in = null;
		FileOutputStream out = null;
		byte[] buffer = new byte[8192];
		int dataLength = 0;
		try {
			in = new BufferedInputStream(this.socket.getInputStream());
			
			Header header = new Header(in);
			header.parse();
			System.out.println(String.format("Filename: %s | filesize: %s",
					header.getFilename(), header.getFilesize()));
			
			out = new FileOutputStream(new File("testner", header.getFilename()));
			while((dataLength = in.read(buffer, 0, buffer.length)) > 0) {
				out.write(buffer, 0, dataLength);
			}
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				out.close();
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
