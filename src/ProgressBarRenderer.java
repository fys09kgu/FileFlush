import java.awt.Component;
import javax.swing.JProgressBar;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;


public class ProgressBarRenderer extends DefaultTableCellRenderer {
	private JProgressBar bar = new JProgressBar();
	
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		bar.setValue((int) value);
		return bar;
	}
}
