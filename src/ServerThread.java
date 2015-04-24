import java.io.BufferedInputStream;
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
				BufferedInputStream in = new BufferedInputStream(connectionSocket.getInputStream());

				Header header = new Header(in);
				header.parseHeader();
				int type = header.getType();
				switch (type) {
				case Header.TYPE_FILE:
					FileMetadata metadata = header.parseFileMetadata();
					System.out.println(String.format("Filename: %s | filesize: %s",
							metadata.getFilename(), metadata.getFilesize()));
					DownloadThread downloader = new DownloadThread(metadata, in);
					downloader.start();
					break;
				}
			 }
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				serverIn.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
