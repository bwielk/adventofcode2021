import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Table {

	private final List<Row> rows;
	private int numOfCols;
	private final List<Row> winningRows = new ArrayList<>();
	private final List<Column> winningColumns = new ArrayList<>();
	private final UUID id;

	public Table() {
		this.id = UUID.randomUUID();
		this.rows = new ArrayList<>();
	}

	public List<Row> getRows() {
		return rows;
	}

	public List<Column> getColumns() {
		List<Column> tableAsColumns = new ArrayList<>();
		for(int i=0; i<numOfCols; i++){
			List<Number> colValues = new ArrayList<>();
			for(Row r : rows){
				colValues.add( r.getRow().get( i ) );
			}
			tableAsColumns.add( new Column( colValues ) );
		}
		return tableAsColumns;
	}

	public void addRow(List<Number> row){
		this.rows.add( new Row( row ));
		//check rows are even column-wise
		int numOfCols = 0;
		boolean firstRowChecked = false;
		for(Row r : rows){
			int currentSize = r.getRow().size();
			if(!firstRowChecked){
				numOfCols = r.getRow().size();
				firstRowChecked = true;
			}else{
				if(numOfCols != currentSize){
					throw new IllegalStateException("The number of columns is uneven in this table");
				}
			}
		}
	}

	public void addWinningColumns( final Column winningColumn ) {
		this.winningColumns.add(winningColumn);
		winningColumn.setToChecked();
	}

	public void addWinningRows( final Row winningRow ) {
		this.winningRows.add(winningRow);
		this.rows.stream().filter( x -> x.equals( winningRow ) ).findFirst().get().setToChecked();
		System.out.println("Hello");
	}

	public List<Column> getWinningColumns() {
		return winningColumns;
	}

	public List<Row> getWinningRows() {
		return winningRows;
	}

	public UUID getId() {
		return id;
	}
}
