import java.util.Observable;
import java.util.Observer;

import javax.swing.JList;


public class UserJList extends JList implements Observer{
	
	public UserJList() {
		this.setBounds(50, 50, 150, 200);
        this.setSelectedIndex(1);
        this.setDragEnabled(true);
	}

	@Override
	public void update(Observable observable, Object arg1) {
		UserMonitor um = (UserMonitor) observable;
		this.setListData(um.getUserList());
	}
}
