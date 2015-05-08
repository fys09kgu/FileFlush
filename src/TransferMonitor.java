import java.util.ArrayList;
import java.util.Observable;

public class TransferMonitor extends Observable {
	ArrayList<ClientThread> uploads;

	public TransferMonitor() {
		uploads = new ArrayList<ClientThread>();
	}

	public void addUpload(ClientThread client) {
		uploads.add(client);
		setChanged();
		notifyObservers(client);
	}

	public int getTotalTransfers() {
		return uploads.size();
	}
}
