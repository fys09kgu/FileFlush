
public class FileMetadata {
	
	private String filename;
	private long filesize;

	public FileMetadata(String filename, long filesize) {
		this.filename = filename;
		this.filesize = filesize;
	}

	public String getFilename() {
		return filename;
	}

	public long getFilesize() {
		return filesize;
	}
}
