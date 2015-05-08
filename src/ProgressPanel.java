import java.awt.Color;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;


public class ProgressPanel extends JPanel implements Observer {
	
	public ProgressPanel() {
        setBackground(Color.white);
        setLocation(250, 0);
        setSize(250, 250);
        setLayout(null);
	}

	@Override
	public void update(Observable observable, Object object) {
		// TODO: check correct class types before type casting!
		TransferMonitor progressList = (TransferMonitor) observable;
		ClientThread client = (ClientThread) object;
		
		JProgressBar progressBar = new JProgressBar();
		progressBar.setMinimum(0);
		progressBar.setMaximum((int) client.getFilesize());
		progressBar.setBounds(50, 50, 150, 25);
		add(progressBar);

		JLabel fileLabel = new JLabel(client.getFilename());
		fileLabel.setBounds(10, 50, 50, 25);
		add(fileLabel);
		
		validate();
		repaint();
	}
}
