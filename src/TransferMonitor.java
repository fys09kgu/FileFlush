import java.io.IOException;
import java.util.ArrayList;
import java.util.Observable;

public class TransferMonitor extends Observable {
	ArrayList<ClientThread> uploads;
	ArrayList<DownloadThread> downloads;

	public TransferMonitor() {
		uploads = new ArrayList<ClientThread>();
		downloads = new ArrayList<DownloadThread>();
	}
	
	public void addDownload(DownloadThread dt) {
		downloads.add(dt);
		setChanged();
		notifyObservers(dt);
	}

	public void addUpload(ClientThread ct) throws IOException {
		new Thread(ct).start();
		uploads.add(ct);
		setChanged();
		notifyObservers(ct);
	}

	public int getTotalTransfers() {
		return uploads.size();
	}
}
