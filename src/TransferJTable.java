import java.util.Observable;
import java.util.Observer;

import javax.swing.JTable;


public class TransferJTable extends JTable implements Observer {
	public TransferJTable() {
		setModel(new TransferTableModel());
		getColumnModel().getColumn(1).setCellRenderer(new ProgressBarRenderer());
	}

	@Override
	public void update(Observable observable, Object object) {
		TransferMonitor transferMonitor = (TransferMonitor) observable;
		if (object instanceof ClientThread) {
			ClientThread client = (ClientThread) object;
			TransferTableModel model = (TransferTableModel) getModel();
			client.addObserver(model);
			model.addTransfer(client);
		}
	}
}
