import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/*
 * The server as a GUI
 */
public class GUI extends JFrame{

	
    private static final long serialVersionUID = 1L;
    // the stop and start buttons
    //private JButton stopStart;
    // JTextArea for the chat room and the events
    
    private JPanel panelList;
    private JPanel panelBars;
    private JPanel panelDrop;
    private JList<String> list;
    private JButton button;
    private JProgressBar pbar;
    private JLabel fileLabel;
    private JFileChooser fileChooser;
    private JTextArea dropArea; 
    
    // server constructor that receive the port to listen to for connection as parameter
    GUI() {
        super("FileFlush");
        Container content = getContentPane();
        content.setLayout(null);
        setTitle("FileFlush");
        setSize(500,525);
        content.setSize(500,500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent){
               System.exit(0);
            }        
         });    
        
                     
        panelBars = new JPanel();
        panelBars.setBackground(Color.white);
        panelBars.setLocation(250, 0);
        panelBars.setSize(250, 250);
        panelBars.setLayout(null);
        content.add(panelBars);
        
        panelDrop = new JPanel();
        panelDrop.setBackground(Color.green);
        panelDrop.setLocation(250, 250);
        panelDrop.setSize(250, 250);
        panelDrop.setLayout(null);
        content.add(panelDrop);
        
        panelList = new JPanel();
        panelList.setBackground(Color.blue);
        panelList.setLocation(0, 0);
        panelList.setSize(250, 500);
        panelList.setLayout(null);
        content.add(panelList);
        
        button = new JButton("Knapp");
        button.setBounds(0, 475, 90, 25);
        panelList.add(button);
        
        String[] selections = { "user1", "user2", "user3", "user4" };
        list = new JList<String>(selections);
        list.setBounds(50, 50, 150, 200);
        System.out.println(list.getSelectedValue());
        list.setSelectedIndex(1);
        list.setDragEnabled(true);
        panelList.add(list); 
        
        pbar = new JProgressBar();
        pbar.setMinimum(0);
        pbar.setMaximum(100);
        pbar.setBounds(50, 50, 150, 25);
        panelBars.add(pbar);
        
        fileLabel = new JLabel("File1");
        fileLabel.setBounds(10, 50, 50, 25);
        panelBars.add(fileLabel);
        
        dropArea = new JTextArea();
        dropArea.setBounds(50, 50, 100, 100);
        //panelDrop.add(dropArea);
        
        //fileChooser = new JFileChooser();
        //fileChooser.setDialogTitle("Choose a file");
        //fileChooser.setBounds(0, 0, 250, 250);
        //panelDrop.add(fileChooser);
 
           
        setVisible(true);
    }
    
   
   
}


