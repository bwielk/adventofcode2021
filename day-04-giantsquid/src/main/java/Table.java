import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Table {

	private final List<Row> rows;
	private List<Column> columns;
	private int numOfCols;
	private final List<Row> winningRows = new ArrayList<>();
	private final List<Column> winningColumns = new ArrayList<>();
	private final UUID id;
	private int tableScore = 0;
	private Integer lastNumberThatMakesTableWin = null;

	public Table() {
		this.id = UUID.randomUUID();
		this.rows = new ArrayList<>();
	}

	public List<Row> getRows() {
		return rows;
	}

	public List<Column> getColumns() {
		return columns;
	}

	public void setLastNumberThatMakesTableWin( final int lastNumberThatMakesTableWin ) {
		this.lastNumberThatMakesTableWin = lastNumberThatMakesTableWin;
	}

	public Integer getLastNumberThatMakesTableWin() {
		return lastNumberThatMakesTableWin;
	}

	public void createColumns() {
		List<Column> tableAsColumns = new ArrayList<>();
		for(int i=0; i<numOfCols; i++){
			List<Number> colValues = new ArrayList<>();
			for(Row r : rows){
				colValues.add( r.getRow().get( i ) );
			}
			tableAsColumns.add( new Column( colValues ) );
		}
		this.columns = tableAsColumns;
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
		this.numOfCols = numOfCols;
	}

	public void addWinningColumns( final Column winningColumn ) {
		this.winningColumns.add(winningColumn);
		winningColumn.setToChecked();
	}

	public void addWinningRows( final Row winningRow ) {
		this.winningRows.add(winningRow);
		this.rows.stream().filter( x -> x.equals( winningRow ) ).findFirst().get().setToChecked();
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

	public void setNumOfCols( int numOfCols ) {
		this.numOfCols = numOfCols;
	}

	public int getNumOfCols() {
		return numOfCols;
	}

	public void setTableScore( final int tableScore ) {
		this.tableScore = tableScore;
	}

	public int getTableScore() {
		return tableScore;
	}

	@Override
	public String toString() {
		return "Table{" + "rows=" + rows.toString() + '}';
	}
}
