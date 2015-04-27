import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class DownloadThread extends Thread {

	private BufferedInputStream inputStream;
	private FileMetadata metadata;

	public DownloadThread(FileMetadata metadata, BufferedInputStream inputStream) {
		this.metadata = metadata;
		this.inputStream = inputStream;
	}
	
	public void run() {
		FileOutputStream out = null;
		byte[] buffer = new byte[8192];
		int dataLength = 0;
		try {
			out = new FileOutputStream(new File("testner", metadata.getFilename()));
			while((dataLength = inputStream.read(buffer, 0, buffer.length)) > 0) {
				out.write(buffer, 0, dataLength);
			}
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				out.close();
				inputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
