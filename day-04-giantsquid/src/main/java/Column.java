import java.util.List;

public class Column {

	private List<Number> column;
	private boolean checked = false;

	public Column( final List<Number> column ) {
		this.column = column;
	}

	public void setToChecked() {
		this.checked = true;
	}

	public List<Number> getColumn() {
		return column;
	}

	public boolean isChecked() {
		return checked;
	}
}
