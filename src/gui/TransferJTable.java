package gui;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JOptionPane;
import javax.swing.JTable;

import system.DownloadThread;
import system.UploadThread;

public class TransferJTable extends JTable implements Observer {
	public TransferJTable() {
		setModel(new TransferTableModel());
		getColumnModel().getColumn(1).setCellRenderer(new ProgressBarRenderer());
	}

	@Override
	public void update(Observable observable, Object object) {
		if (object instanceof UploadThread) {
			UploadThread ut = (UploadThread) object;
			TransferTableModel model = (TransferTableModel) getModel();
			ut.addObserver(model);
			model.addTransfer(ut);
		} else if (object instanceof DownloadThread) {
			int dialogResult = JOptionPane.showConfirmDialog(null,
					"Do you want to download this file?",
					"Warning", JOptionPane.YES_NO_OPTION);
			DownloadThread dt = (DownloadThread) object;
			if (dialogResult == JOptionPane.YES_OPTION){
				dt.setDownload(true);
				TransferTableModel model = (TransferTableModel) getModel();
				dt.addObserver(model);
				model.addTransfer(dt);
			} else {
				dt.setDownload(false);
			}
			new Thread(dt).start();
		}
	}
}
