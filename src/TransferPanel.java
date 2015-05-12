import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;


public class TransferPanel extends JPanel implements Observer {

	@Override
	public void update(Observable observable, Object object) {
		// TODO: check correct class types before type casting!
		TransferMonitor transferMonitor = (TransferMonitor) observable;
		ClientThread client = (ClientThread) object;
		
		int gridy = transferMonitor.getTotalTransfers() - 1;
		
		JLabel label = new JLabel(client.getFilename());
		GridBagConstraints labelConstraints = new GridBagConstraints();
		labelConstraints.anchor = GridBagConstraints.WEST;
		labelConstraints.insets = new Insets(0, 0, 5, 5);
		labelConstraints.gridx = 0;
		labelConstraints.gridy = gridy;
		add(label, labelConstraints);
		
		TransferProgressBar progressBar = new TransferProgressBar();
		client.addObserver(progressBar);
		progressBar.setMaximum((int) client.getFilesize());
		GridBagConstraints progressBarConstraints = new GridBagConstraints();
		progressBarConstraints.fill = GridBagConstraints.HORIZONTAL;
		progressBarConstraints.insets = new Insets(0, 0, 5, 0);
		progressBarConstraints.gridx = 1;
		progressBarConstraints.gridy = gridy;
		add(progressBar, progressBarConstraints);
		
		validate();
		repaint();
	}
}
