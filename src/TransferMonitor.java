import java.io.File;
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

	public void addUpload(User user, File file) throws IOException {
		ClientThread client = new ClientThread(user.getSocket(), file);
		new Thread(client).start();
		uploads.add(client);
		setChanged();
		notifyObservers(client);
	}

	public int getTotalTransfers() {
		return uploads.size();
	}
}
