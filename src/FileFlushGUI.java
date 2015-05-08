import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.border.EmptyBorder;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Color;

import javax.swing.JList;
import javax.swing.JButton;

import java.awt.Dimension;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Observer;

import javax.swing.AbstractListModel;
import javax.swing.ListSelectionModel;


public class FileFlushGUI extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 * @throws UnknownHostException 
	 */
	public static void main(String[] args) throws UnknownHostException {
		User owner = new User(InetAddress.getLocalHost(), ServerThread.SERVER_PORT, "");
		final UserMonitor um = new UserMonitor(owner);
		final TransferMonitor tm = new TransferMonitor();
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FileFlushGUI frame = new FileFlushGUI(um, tm);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public FileFlushGUI(UserMonitor userMonitor, TransferMonitor transferMonitor) {
		setTitle("FileFlush");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[] {350, 200};
		gbl_contentPane.rowHeights = new int[] {200, 0, 50};
		gbl_contentPane.columnWeights = new double[]{0.0, 1.0};
		gbl_contentPane.rowWeights = new double[]{1.0, 1.0, 0.0};
		contentPane.setLayout(gbl_contentPane);
		
		UserJList userList = new UserJList(transferMonitor);
		userList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		userList.setModel(new AbstractListModel() {
			String[] values = new String[] {};
			public int getSize() {
				return values.length;
			}
			public Object getElementAt(int index) {
				return values[index];
			}
		});
		userList.setLayoutOrientation(JList.HORIZONTAL_WRAP);
        userMonitor.addObserver((Observer) userList);
		GridBagConstraints gbc_userList = new GridBagConstraints();
		gbc_userList.gridheight = 2;
		gbc_userList.insets = new Insets(0, 0, 5, 5);
		gbc_userList.fill = GridBagConstraints.BOTH;
		gbc_userList.gridx = 0;
		gbc_userList.gridy = 0;
		contentPane.add(userList, gbc_userList);
		
		TransferPanel transferPanel = new TransferPanel();
		transferMonitor.addObserver((Observer) transferPanel);
		transferPanel.setPreferredSize(new Dimension(10, 300));
		transferPanel.setMinimumSize(new Dimension(10, 300));
		transferPanel.setBackground(Color.WHITE);
		GridBagConstraints gbc_transferPanel = new GridBagConstraints();
		gbc_transferPanel.insets = new Insets(0, 0, 5, 0);
		gbc_transferPanel.fill = GridBagConstraints.BOTH;
		gbc_transferPanel.gridx = 1;
		gbc_transferPanel.gridy = 0;
		contentPane.add(transferPanel, gbc_transferPanel);
		GridBagLayout gbl_transferPanel = new GridBagLayout();
		gbl_transferPanel.columnWidths = new int[] {150, 250, 0};
		gbl_transferPanel.rowHeights = new int[]{0, 0, 0, 0};
		gbl_transferPanel.columnWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		gbl_transferPanel.rowWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		transferPanel.setLayout(gbl_transferPanel);
		
		JPanel uploadPanel = new JPanel();
		GridBagConstraints gbc_uploadPanel = new GridBagConstraints();
		gbc_uploadPanel.gridheight = 2;
		gbc_uploadPanel.insets = new Insets(0, 0, 5, 0);
		gbc_uploadPanel.fill = GridBagConstraints.BOTH;
		gbc_uploadPanel.gridx = 1;
		gbc_uploadPanel.gridy = 1;
		contentPane.add(uploadPanel, gbc_uploadPanel);
		
		JButton button = new JButton("+");
		button.setPreferredSize(new Dimension(200, 100));
		uploadPanel.add(button);
		
		JButton btnFindUsers = new JButton("Find users");
		btnFindUsers.addActionListener(new FindButtonListener(userMonitor));
		GridBagConstraints gbc_btnFindUsers = new GridBagConstraints();
		gbc_btnFindUsers.insets = new Insets(0, 0, 0, 5);
		gbc_btnFindUsers.gridx = 0;
		gbc_btnFindUsers.gridy = 2;
		contentPane.add(btnFindUsers, gbc_btnFindUsers);
	}
}
