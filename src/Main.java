import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class Main {

    public static void main(String[] args) {
        System.out.println("Creating Server Socket");
        ServerThread server = new ServerThread();
        server.start();
        System.out.println("Specify: host port filePath");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String message;
        try {
			while((message = br.readLine()) != null){
				String[] options = message.split(" ");
				String hostName = options[0];
				int port = Integer.parseInt(options[1]);
				String filePath = options[3];
				Socket socket = new Socket(hostName, port);
				ClientThread ct = new ClientThread(socket, new File(filePath));
				ct.start();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
