import java.util.ArrayList;
import java.util.List;

public class Table {

	private final List<List<Number>> rows;

	public Table() {
		this.rows = new ArrayList<>();
	}

	public List<List<Number>> getRows() {
		return rows;
	}

	public void addRow(List<Number> row){
		this.rows.add( row );
	}
}
