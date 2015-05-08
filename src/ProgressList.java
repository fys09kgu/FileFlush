import java.util.ArrayList;
import java.util.Observable;

public class ProgressList extends Observable {
	ArrayList<ClientThread> clients;

	public ProgressList() {
		clients = new ArrayList<ClientThread>();
	}
	
	public void addClient(ClientThread client) {
		clients.add(client);
		hasChanged();
		notifyObservers(client);
	}

	public void removeClient(ClientThread client) {
		clients.remove(client);
		hasChanged();
		notifyObservers(client);
	}
}
