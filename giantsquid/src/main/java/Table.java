import java.util.ArrayList;
import java.util.List;

public class Table {

	private final List<List<Number>> rows;
	private int numOfCols;

	public Table() {
		this.rows = new ArrayList<>();
	}

	public List<List<Number>> getRows() {
		return rows;
	}

	public List<List<Number>> getColumns() {
		List<List<Number>> tableAsColumns = new ArrayList<>();

		// get columns
		for(int i=0; i<numOfCols; i++){
			List<Number> col = new ArrayList<>();
			for(List<Number> r : rows){
				col.add( r.get( i ) );
			}
			tableAsColumns.add( col );
		}
		return tableAsColumns;
	}

	public void addRow(List<Number> row){
		this.rows.add( row );
		//check rows are even column-wise
		int numOfCols = 0;
		boolean firstRowChecked = false;
		for(List<Number> r : rows){
			int currentSize = r.size();
			if(!firstRowChecked){
				numOfCols = r.size();
				firstRowChecked = true;
			}else{
				if(numOfCols != currentSize){
					throw new IllegalStateException("The number of columns is uneven in this table");
				}
			}
		}
	}
}
