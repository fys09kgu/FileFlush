import java.io.BufferedInputStream;
import java.io.IOException;

public class Header {
	public static final int TYPE_FILE = 0;
	
	private BufferedInputStream inputStream;

	public Header(BufferedInputStream in) {
		this.inputStream = in;
	}
	
	public int parseType() throws IOException {
		int b = 0;
		char c = 0;
		StringBuilder sb = new StringBuilder();
		while ((b = inputStream.read()) != -1) {
			c = (char) b;
			if (c == ';') break;
			sb.append(c);
		}
		return Integer.parseInt(sb.toString());
	}

	public FileMetadata parseFileMetadata() throws IOException {
		int b, i = 0;
		char c = 0;
		StringBuilder sb = new StringBuilder();
		do {
			b = inputStream.read();
			c = (char) b;
			sb.append(c);
			if (c == ';') i++;
		} while (b != -1 && i < 2);
		int index = sb.indexOf(";");
		String filename = sb.substring(0, index);
		long filesize = Long.parseLong(sb.substring(index + 1, sb.length() - 1));
		return new FileMetadata(filename, filesize);
	}
	
	public static byte[] createFileHeader(FileMetadata metadata) {
		return String.format("%d;%s;%s;", TYPE_FILE, metadata.getFilename(), metadata.getFilesize()).getBytes();
	}
}
