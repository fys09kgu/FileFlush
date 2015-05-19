import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JFileChooser;
import javax.swing.JList;

public class UserJList extends JList<Object> implements Observer{
	
	User[] users;
	
	public UserJList(final TransferMonitor transferMonitor) {
        this.setSelectedIndex(1);
        this.setDragEnabled(true);
        
		this.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent evt) {
				UserJList list = (UserJList) evt.getSource();
				if (evt.getClickCount() == 1) {
					int index = list.locationToIndex(evt.getPoint());
					if (index >= 0) {
						final JFileChooser fc = new JFileChooser();
						int returnVal = fc.showOpenDialog(null);
						if (returnVal == JFileChooser.APPROVE_OPTION) {
							File file = fc.getSelectedFile();
							System.out.println("Sending file " + file + " to "
									+ users[index]);
							try {
								UploadThread ut = new UploadThread(users[index].getSocket(), file);
								transferMonitor.addUpload(ut);
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
					}
				}
			}
		});
	}

	@Override
	public void update(Observable observable, Object arg1) {
		if (observable instanceof UserMonitor) {
			UserMonitor um = (UserMonitor) observable;
			this.users = um.getUserList();
			this.setListData(users);
		}
	}
}
