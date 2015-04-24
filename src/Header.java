import java.io.BufferedInputStream;
import java.io.IOException;

public class Header {
	public static final int TYPE_FILE = 0;
	
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
}
