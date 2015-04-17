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
		byte[] buffer = new byte[8192];
		int dataLength = 0;
		try {
			BufferedInputStream in = new BufferedInputStream(this.socket.getInputStream());
			int b = 0;
			StringBuilder header = new StringBuilder();
			int i = 0;
			do {
				b = in.read();
				char c = (char) b;
				header.append(c);
				if (c == ';') {
					i++;
				}
			} while (b != -1 && i < 2);
			
			int index = header.indexOf(";");
			String filename = header.substring(0, index);
			long filesize = Long.parseLong(header.substring(index + 1, header.length()-1));
			System.out.println(String.format("Filename: %s | filesize: %d", filename, filesize));
			
			FileOutputStream out = new FileOutputStream(new File("testner", filename));
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
