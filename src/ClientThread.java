import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public class ClientThread extends Thread {
	Socket socket;
	File file;

	public ClientThread(Socket socket, File file) {
		this.socket = socket;
		this.file = file;
	}

	public void run(){
		InputStream is = null;
		BufferedOutputStream os = null;
		byte[] buffer = new byte[8000];
		
		try {
			is = socket.getInputStream();
			os = new BufferedOutputStream(socket.getOutputStream());
			BufferedInputStream fis = new BufferedInputStream(new FileInputStream(file));
			
			int count;
			while ((count = fis.read(buffer)) > 0){
				os.write(buffer, 0, count);
			}
			os.flush();
			fis.close();
			is.close();
			os.close();
			socket.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
