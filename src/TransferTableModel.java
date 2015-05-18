import java.util.Observable;
import java.util.Observer;
import java.util.Vector;

import javax.swing.table.AbstractTableModel;


public class TransferTableModel extends AbstractTableModel implements Observer {
	private Vector data = new Vector();
	private String[] columnNames = new String[] {"Filename", "Progress"};

	@Override
	public void update(Observable observable, Object object) {
		int index = data.indexOf((ClientThread) object);
		if (index != -1) {
			fireTableCellUpdated(index, 1);
		}
	}
	
	public String getColumnName(int col) {
		return columnNames[col];
	}
	
	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	@Override
	public int getRowCount() {
		return data.size();
	}

	@Override
	public Object getValueAt(int row, int col) {
		ClientThread ct = (ClientThread) data.elementAt(row);
		if (col == 0)
			return ct.getFilename();
		else if (col == 1)
			return new Integer(ct.getProgress());
		else
			return null;
	}

	public void addTransfer(ClientThread ct) {
		data.addElement(ct);
		ct.addObserver(this);
		fireTableRowsInserted(data.size()-1, data.size()-1);
	}
}
