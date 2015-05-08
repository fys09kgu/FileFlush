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
		System.out.println("hej");
		ProgressList progressList = (ProgressList) observable;
		ClientThread client = (ClientThread) object;
		JProgressBar pbar = new JProgressBar();
		pbar.setMinimum(0);
		pbar.setMaximum(100);
		pbar.setBounds(50, 50, 150, 25);
		add(pbar);

		JLabel fileLabel = new JLabel("File1");
		fileLabel.setBounds(10, 50, 50, 25);
		add(fileLabel);
		
		validate();
		repaint();
	}
}
