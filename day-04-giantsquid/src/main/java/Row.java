import java.util.List;

public class Row {

	private List<Number> row;
	private boolean checked = false;

	public Row( final List<Number> row ) {
		this.row = row;
	}

	public void setToChecked() {
		this.checked = true;
	}

	public List<Number> getRow() {
		return row;
	}

	public boolean isChecked() {
		return checked;
	}

	@Override
	public String toString() {
		return "\nRow{" + "row=" + row + '}';
	}
}
