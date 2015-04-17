import java.io.BufferedInputStream;
import java.io.IOException;

public class Header {
	private BufferedInputStream in;
	
	private String filename;
	private long filesize;

	public Header(BufferedInputStream in) {
		this.in = in;
	}
	
	public String getFilename() {
		return filename;
	}

	public long getFilesize() {
		return filesize;
	}

	public void parse() throws IOException {
		int b, i = 0;
		char c = 0;
		StringBuilder sb = new StringBuilder();
		do {
			b = in.read();
			c = (char) b;
			sb.append(c);
			if (c == ';') i++;
		} while (b != -1 && i < 2);
		int index = sb.indexOf(";");
		this.filename = sb.substring(0, index);
		this.filesize = Long.parseLong(sb.substring(index + 1, sb.length() - 1));
	}
	
	public static byte[] createHeader(String filename, long filesize) {
		return String.format("%s;%s;", filename, filesize).getBytes();
	}
}
