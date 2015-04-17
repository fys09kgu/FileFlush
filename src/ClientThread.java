import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class ClientThread extends Thread {
	Socket socket;
	File file;

	public ClientThread(Socket socket, File file) {
		this.socket = socket;
		this.file = file;
	}

	public void run(){
		InputStream is;
		BufferedOutputStream os;
		try {
			is = socket.getInputStream();
			os = new BufferedOutputStream(socket.getOutputStream());
			BufferedInputStream fos = new BufferedInputStream(new FileInputStream(file));
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		
	}
}
