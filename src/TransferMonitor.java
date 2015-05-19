import java.io.IOException;
import java.util.ArrayList;
import java.util.Observable;

public class TransferMonitor extends Observable {
	ArrayList<UploadThread> uploads;
	ArrayList<DownloadThread> downloads;

	public TransferMonitor() {
		uploads = new ArrayList<UploadThread>();
		downloads = new ArrayList<DownloadThread>();
	}
	
	public void addDownload(DownloadThread dt) {
		downloads.add(dt);
		setChanged();
		notifyObservers(dt);
	}

	public void addUpload(UploadThread ut) throws IOException {
		new Thread(ut).start();
		uploads.add(ut);
		setChanged();
		notifyObservers(ut);
	}
}
