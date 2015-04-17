import javax.swing.*;
import java.awt.*;

/*
 * The server as a GUI
 */
public class GUI extends JFrame{

    private static final long serialVersionUID = 1L;
    // the stop and start buttons
    private JButton stopStart;
    // JTextArea for the chat room and the events
    private JTextArea chat, event;
    // The port number



    // server constructor that receive the port to listen to for connection as parameter
    GUI() {
        super("FileFlush");
        // in the NorthPanel the PortNumber the Start and Stop buttons
        JPanel north = new JPanel();
        north.add(new JLabel("Port number: "));
        // to stop or start the server, we start with "Start"
        stopStart = new JButton("Start");
        north.add(stopStart);
        add(north, BorderLayout.NORTH);

        // the event and chat room
        JPanel center = new JPanel(new GridLayout(2,1));
        chat = new JTextArea(80,80);
        chat.setEditable(false);
        center.add(new JScrollPane(chat));
        event = new JTextArea(80,80);
        event.setEditable(false);
        center.add(new JScrollPane(event));
        add(center);
        // need to be informed when the user click the close button on the frame
        setSize(400, 600);
        setVisible(true);
    }

}


