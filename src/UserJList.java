import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JFileChooser;
import javax.swing.JList;


public class UserJList extends JList implements Observer{
	
	User[] users;
	
	public UserJList() {
		this.setBounds(10, 10, 230, 400);
        this.setSelectedIndex(1);
        this.setDragEnabled(true);
        
        this.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                UserJList list = (UserJList)evt.getSource();
                if (evt.getClickCount() == 1) {
                    // Double-click detected
                    int index = list.locationToIndex(evt.getPoint());
                    //Create a file chooser
                    final JFileChooser fc = new JFileChooser();
                    //In response to a button click:
                    int returnVal = fc.showOpenDialog(null);
                    if (returnVal == JFileChooser.APPROVE_OPTION){
                    	File file = fc.getSelectedFile();
                    	System.out.println("Sending file " + file + " to " + users[index]);
                    	users[index].sendFile(file);
                    }
                    
                }
            }
        });
	}

	@Override
	public void update(Observable observable, Object arg1) {
		UserMonitor um = (UserMonitor) observable;
		this.users = um.getUserList();
		this.setListData(users);
	}
}
