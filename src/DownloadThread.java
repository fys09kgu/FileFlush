import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Observable;

public class DownloadThread extends Transfer implements Runnable {

	private BufferedInputStream inputStream;
	private FileMetadata metadata;
	private double downloaded;

	public DownloadThread(FileMetadata metadata, BufferedInputStream inputStream) {
		this.metadata = metadata;
		this.inputStream = inputStream;
	}
	
	public void run() {
		FileOutputStream out = null;
		byte[] buffer = new byte[8192];
		int dataLength = 0;
		try {
			out = new FileOutputStream(new File(metadata.getDirectory(), metadata.getFilename()));
			while((dataLength = inputStream.read(buffer, 0, buffer.length)) > 0) {
				out.write(buffer, 0, dataLength);
				increaseDownloadSize(dataLength);
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

	private void increaseDownloadSize(int size) {
		downloaded += size;
		setChanged();
		notifyObservers(this);
	}

	@Override
	public String getFilename() {
		return metadata.getFilename();
	}

	public long getFilesize() {
		return metadata.getFilesize();
	}

	@Override
	public int getProgress() {
		return (int) ((double) downloaded/(double) getFilesize() * 100);
	}
}
