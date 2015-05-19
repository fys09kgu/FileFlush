package gui;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import system.Find;
import system.UserMonitor;

public class FindButtonListener implements ActionListener{
	
	private UserMonitor um;

	public FindButtonListener(UserMonitor um){
		this.um = um;
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		Find find = new Find();
        find.setUser(um.getOwner());
        find.start();
	}
	
}
