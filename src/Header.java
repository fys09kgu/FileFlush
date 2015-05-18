import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.SocketException;

public class Header {
	public static final int TYPE_FILE = 0;
	public static final int TYPE_USER = 1;
	
	private BufferedInputStream inputStream;
	private String[] header;
	private int type;

	public Header(BufferedInputStream in) {
		this.inputStream = in;
	}
	
	public void parseHeader() throws SocketException, IOException {
		int b = 0; 
		char c = 0;
		StringBuilder sb = new StringBuilder();
		try {
			while ((b = inputStream.read()) != -1) {
				c = (char) b;
				if (c == '\n') break;
				sb.append(c);
			}
		} catch (IOException e) {
			throw new SocketException("Connection Error");
		}
		
		if (sb.length() == 0) {
			throw new SocketException("Connection Terminated");
		}
		
		header = sb.toString().split(";");
		System.out.println(sb);
		if (!(header.length > 0)) {
			throw new IOException("Input too short");
		}
		try{
			type = Integer.parseInt(header[0]);
		}
		catch (NumberFormatException e){
			throw new IOException("Input malformed");
		}
	}
	
	public int getType() {
		return type;
	}

	public FileMetadata parseFileMetadata() throws IOException {
		return new FileMetadata(header[1], Long.parseLong(header[2]));
	}
	
	public static byte[] createFileHeader(FileMetadata metadata) {
		return String.format("%d;%s;%s\n", TYPE_FILE, metadata.getFilename(), metadata.getFilesize()).getBytes();
	}
	
	public User parseUser() throws IOException {
		String[] address = header[1].split(":");
		String username = (header.length >= 3) ? header[2] : "";
		return new User(InetAddress.getByName(address[0]), Integer.parseInt(address[1]), username);
	}
	
	public static byte[] createUserHeader(User user) {
		return String.format("%d;%s:%s;%s\n", TYPE_USER, user.getIPAddress(), user.getPort(), user.getUsername()).getBytes();
	}

	public static byte[] createFindUserHeader(User user) {
		return String.format("%s:%s", user.getIPAddress(), user.getPort()).getBytes();
	}
}
