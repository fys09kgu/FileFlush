import java.util.Observable;
import java.util.Observer;

import javax.swing.JOptionPane;
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
		} else if (object instanceof DownloadThread) {
			int dialogResult = JOptionPane.showConfirmDialog(null,
					"Do you want to download this file?",
					"Warning", JOptionPane.YES_NO_OPTION);
			if (dialogResult == JOptionPane.YES_OPTION){
				System.out.println("yes");
				DownloadThread dt = (DownloadThread) object;
				TransferTableModel model = (TransferTableModel) getModel();
				dt.addObserver(model);
				model.addTransfer(dt);
				new Thread(dt).start();
			} else {
				System.out.println("no");
			}
		}
	}
}
