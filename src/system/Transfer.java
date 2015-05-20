package system;
import java.util.Observable;

public abstract class Transfer extends Observable {
	protected long transfered;

	public abstract String getFilename();

	public abstract long getFilesize();
	
	public abstract String getProgressFilename();
	
	public int getProgress() {
		return (int) ((double) transfered/(double) getFilesize() * 100);
	}
}
