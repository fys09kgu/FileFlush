import java.io.File;

public class FileMetadata {
	
	private String filename;
	private long filesize;
	private File directory;

	public FileMetadata(String filename, long filesize) {
		this.filename = filename;
		this.filesize = filesize;
	}

	public FileMetadata(File file) {
		this(file.getName(), file.length());
	}

	public String getFilename() {
		return filename;
	}

	public long getFilesize() {
		return filesize;
	}

	public void setDirectory(File file) {
		this.directory = file;
	}

	public File getDirectory() {
		return directory;
	}
}
