import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.InetAddress;

public class Header {
	public static final int TYPE_FILE = 0;
	public static final int TYPE_USER = 1;
	
	private BufferedInputStream inputStream;
	private String[] header;
	private int type;

	public Header(BufferedInputStream in) {
		this.inputStream = in;
	}
	
	public void parseHeader() throws IOException {
		int b = 0;
		char c = 0;
		StringBuilder sb = new StringBuilder();
		while ((b = inputStream.read()) != -1) {
			c = (char) b;
			if (c == '\n') break;
			sb.append(c);
		}
		header = sb.toString().split(";");
		type = Integer.parseInt(header[0]);
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
		return new User(InetAddress.getByName(address[0]), Integer.parseInt(address[1]), header[3]);
	}
	
	public static byte[] createUserHeader(User user) {
		return String.format("%d;%s;%s\n", TYPE_USER, user.getAddress(), user.getPort(), user.getUsername()).getBytes();
	}
}
