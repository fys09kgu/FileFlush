import java.util.Observable;

public abstract class Transfer extends Observable {

	public abstract String getFilename();

	public abstract int getProgress();
}
