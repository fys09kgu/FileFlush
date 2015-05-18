import java.util.Observable;
import java.util.Observer;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;


public class TransferJTable extends JTable implements Observer {
	public TransferJTable() {
		setModel(new TransferTableModel());
		getColumnModel().getColumn(1).setCellRenderer(new ProgressBarRenderer());
	}

	@Override
	public void update(Observable observable, Object object) {
		// TODO: check correct class types before type casting!
		TransferMonitor transferMonitor = (TransferMonitor) observable;
		ClientThread client = (ClientThread) object;
		TransferTableModel model = (TransferTableModel) getModel();
		client.addObserver(model);		
		model.addTransfer(client);
	}
}
