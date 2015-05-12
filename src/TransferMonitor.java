import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Observable;

public class TransferMonitor extends Observable {
	ArrayList<ClientThread> uploads;

	public TransferMonitor() {
		uploads = new ArrayList<ClientThread>();
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
