import java.util.Observable;
import java.util.Observer;

import javax.swing.JProgressBar;


public class TransferProgressBar extends JProgressBar implements Observer {

	@Override
	public void update(Observable observable, Object object) {
		setValue((int) object);
		
		validate();
		repaint();
	}

}
