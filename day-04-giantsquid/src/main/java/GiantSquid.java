import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class GiantSquid {

	private static List<Integer> bingoNumbers;
	private static List<Table> bingoTables = new ArrayList<>();
	private static List<Table> winningTables = new ArrayList<>();

	/***
	 * --- Day 4: Giant Squid ---
	 * You're already almost 1.5km (almost a mile) below the surface of the ocean, already so deep that you can't see any sunlight. What you can see, however, is a giant squid that has attached itself to the outside of your submarine.
	 *
	 * Maybe it wants to play bingo?
	 *
	 * Bingo is played on a set of boards each consisting of a 5x5 grid of numbers. Numbers are chosen at random, and the chosen number is marked on all boards on which it appears. (Numbers may not appear on all boards.) If all numbers in any row or any column of a board are marked, that board wins. (Diagonals don't count.)
	 *
	 * The submarine has a bingo subsystem to help passengers (currently, you and the giant squid) pass the time. It automatically generates a random order in which to draw numbers and a random set of boards (your puzzle input). For example:
	 *
	 * 7,4,9,5,11,17,23,2,0,14,21,24,10,16,13,6,15,25,12,22,18,20,8,19,3,26,1
	 *
	 * 22 13 17 11  0
	 *  8  2 23  4 24
	 * 21  9 14 16  7
	 *  6 10  3 18  5
	 *  1 12 20 15 19
	 *
	 *  3 15  0  2 22
	 *  9 18 13 17  5
	 * 19  8  7 25 23
	 * 20 11 10 24  4
	 * 14 21 16 12  6
	 *
	 * 14 21 17 24  4
	 * 10 16 15  9 19
	 * 18  8 23 26 20
	 * 22 11 13  6  5
	 *  2  0 12  3  7
	 * After the first five numbers are drawn (7, 4, 9, 5, and 11), there are no winners, but the boards are marked as follows (shown here adjacent to each other to save space):
	 *
	 * 22 13 17 11  0         3 15  0  2 22        14 21 17 24  4
	 *  8  2 23  4 24         9 18 13 17  5        10 16 15  9 19
	 * 21  9 14 16  7        19  8  7 25 23        18  8 23 26 20
	 *  6 10  3 18  5        20 11 10 24  4        22 11 13  6  5
	 *  1 12 20 15 19        14 21 16 12  6         2  0 12  3  7
	 * After the next six numbers are drawn (17, 23, 2, 0, 14, and 21), there are still no winners:
	 *
	 * 22 13 17 11  0         3 15  0  2 22        14 21 17 24  4
	 *  8  2 23  4 24         9 18 13 17  5        10 16 15  9 19
	 * 21  9 14 16  7        19  8  7 25 23        18  8 23 26 20
	 *  6 10  3 18  5        20 11 10 24  4        22 11 13  6  5
	 *  1 12 20 15 19        14 21 16 12  6         2  0 12  3  7
	 * Finally, 24 is drawn:
	 *
	 * 22 13 17 11  0         3 15  0  2 22        14 21 17 24  4
	 *  8  2 23  4 24         9 18 13 17  5        10 16 15  9 19
	 * 21  9 14 16  7        19  8  7 25 23        18  8 23 26 20
	 *  6 10  3 18  5        20 11 10 24  4        22 11 13  6  5
	 *  1 12 20 15 19        14 21 16 12  6         2  0 12  3  7
	 * At this point, the third board wins because it has at least one complete row or column of marked numbers (in this case, the entire top row is marked: 14 21 17 24 4).
	 *
	 * The score of the winning board can now be calculated. Start by finding the sum of all unmarked numbers on that board; in this case, the sum is 188. Then, multiply that sum by the number that was just called when the board won, 24, to get the final score, 188 * 24 = 4512.
	 *
	 * To guarantee victory against the giant squid, figure out which board will win first. What will your final score be if you choose that board?
	 */

	public static void main( String[] args ) {

		bingoNumbers = generateBingoNumbers();
		generateBingoTables();

		for(int bingoNumberIndex=0; bingoNumberIndex<bingoNumbers.size(); bingoNumberIndex++) {
			int currentNumberToCheck = bingoNumbers.get( bingoNumberIndex );
			for ( Table t : bingoTables ) {
				checkTableForNumber( currentNumberToCheck, t );
				checkAWinningTableByItsWinningRows( t, currentNumberToCheck );
				checkAWinningTableByItsWinningColumns( t, currentNumberToCheck);
			}
		}
		createAReportOnTheFirstWinningTable();
		createAReportOnTheLastWinningTable();
	}

	private static void createAReportOnTheFirstWinningTable(){
		if(!winningTables.isEmpty()){
			Table winningTable = winningTables.get( 0 );
			int tableScore = winningTable.getTableScore();
			int lastNumberThatMakesTableWin = winningTable.getLastNumberThatMakesTableWin();
			String tableName = winningTable.getId().toString();
			System.out.println(String.format( "\nFIRST WINNING TABLE"
					+ "\nTABLE NAME: %s"
					+ "\nTABLE SCORE: %s"
					+ "\nNUMBER THAT MADE THE TABLE WIN: %s "
					+ "\n%s", tableName, tableScore, lastNumberThatMakesTableWin, winningTable.toString() ));
		}
	}

	private static void createAReportOnTheLastWinningTable(){
		if(!winningTables.isEmpty()){
			Table winningTable = winningTables.get( winningTables.size()-1 );
			int tableScore = winningTable.getTableScore();
			int lastNumberThatMakesTableWin = winningTable.getLastNumberThatMakesTableWin();
			String tableName = winningTable.getId().toString();
			System.out.println(String.format( "\nLAST WINNING TABLE"
					+ "\nTABLE NAME: %s"
					+ "\nTABLE SCORE: %s"
					+ "\nNUMBER THAT MADE THE TABLE WIN: %s "
					+ "\n%s", tableName, tableScore, lastNumberThatMakesTableWin, winningTable.toString() ));
		}
	}

	private static void calculateWinningTableScore(Table t){
		int unmarkedNumbers = 0;
		for(Row row : t.getRows()){
			unmarkedNumbers += row.getRow().stream()
					.filter( x -> !x.isMarked() )
					.map( x -> x.getValue() )
					.reduce(0, (a,b) -> a + b);
		}
		t.setTableScore( unmarkedNumbers*t.getLastNumberThatMakesTableWin());
	}

	private static void checkAWinningTableByItsWinningRows(Table t, int bingoNumber){
		for ( Row r : t.getRows() ) {
			if ( r.getRow().stream().allMatch( Number::isMarked ) ) {
				if(!t.getWinningRows().contains( r )){
					t.addWinningRows( r );
				}
			}
			//check if the table has already been added
			List<Table> foundTables = winningTables.stream()
					.filter( x -> x.getId().equals( t.getId() ) )
					.collect( Collectors.toList() );
			//if no table like the current one has been found
			// and the current table contains wins, add it to the winning tables
			if ( foundTables.isEmpty() && !t.getWinningRows().isEmpty() ) {
				winningTables.add( t );
				if(t.getLastNumberThatMakesTableWin() == null){
					t.setLastNumberThatMakesTableWin( bingoNumber );
					calculateWinningTableScore(t);
				}
			}
		}
	}

	private static void checkAWinningTableByItsWinningColumns(Table t, int bingoNumber){
		for(Column c : t.getColumns()){
			if(c.getColumn().stream().allMatch( Number::isMarked )) {
				if(!t.getWinningColumns().contains( c )) {
					t.addWinningColumns( c );
				}
			}
			List<Table> foundTables = winningTables.stream()
					.filter( x -> x.getId().equals( t.getId() ) )
					.collect( Collectors.toList() );
			//if no table like the current one has been found
			// and the current table contains wins, add it to the winning tables
			if ( foundTables.isEmpty() && !t.getWinningColumns().isEmpty() ) {
				winningTables.add( t );
				if(t.getLastNumberThatMakesTableWin() == null){
					t.setLastNumberThatMakesTableWin( bingoNumber );
					calculateWinningTableScore(t);
				}
			}
		}
	}

	private static void checkTableForNumber(int numberToCheck, Table table){
		for( Row row : table.getRows()){
			row.getRow().stream().forEach( x -> {
				if ( x.getValue().equals( numberToCheck )) {
					x.setAsMarked();
				}
			});
		}
	}

	private static void generateBingoTables(){
		List<String> lines  = lineReader();
		Table table = null;
		for(int i=1; i<lines.size(); i++){
			if( lines.get( i ).equals( "" ) ){
				if(table != null){
					addCreatedBingoTable(table);
					table = new Table();
				}else{
					table = new Table();
				}
			}else{
				List<Integer> rowOfInts = Arrays.stream( lines.get( i ).split( " " ) )
						.filter( x -> !x.isEmpty() )
						.map( Integer::parseInt ).collect( Collectors.toList() );
				table.addRow( rowOfInts.stream().map( Number::new ).collect( Collectors.toList()) );
			}
			if(i==lines.size()-1){
				addCreatedBingoTable(table);
			}
		}
	}

	private static List<Integer> generateBingoNumbers(){
		return Arrays.stream( lineReader().get( 0 ).split( "," ) ).map( Integer::parseInt ).collect(
				Collectors.toList());
	}

	private static List<String> lineReader(){
		List<String> lines = new ArrayList<>();
		ClassLoader classLoader = GiantSquid.class.getClassLoader();
		InputStream is = classLoader.getResourceAsStream( "bingo.txt" );
		try( InputStreamReader streamReader = new InputStreamReader( is, StandardCharsets.UTF_8 );
				BufferedReader reader = new BufferedReader( streamReader )) {
			String line;
			while((line = reader.readLine()) != null) {
				lines.add( line );
			}
		} catch ( IOException e ) {
			e.printStackTrace();
		}
		return lines;
	}

	private static void addCreatedBingoTable(Table table){
		table.createColumns();
		bingoTables.add( table );
	}
}
