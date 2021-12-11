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

	public static void main( String[] args ) {

		bingoNumbers = generateBingoNumbers();
		generateBingoTables();

		for(int bingoNumberIndex=0; bingoNumberIndex<bingoNumbers.size(); bingoNumberIndex++) {
			int currentNumberToCheck = bingoNumbers.get( bingoNumberIndex );
			for ( Table t : bingoTables ) {
				checkTableForNumber( currentNumberToCheck, t );
			}
		}

		for ( Table t : bingoTables ) {
			for ( Row r : t.getRows() ) {
				if ( r.getRow().stream().allMatch( Number::isMarked ) ) {
					t.addWinningRows( r );
				}
				//check if the table has already been added
				List<Table> foundTables = winningTables.stream()
						.filter( x -> x.getId().equals( t.getId() ) )
						.collect( Collectors.toList() );
				//if no table like the current one has been found
				// and the current table contains wins, add it to the winning tables
				if ( foundTables.isEmpty() && !t.getWinningRows().isEmpty() ) {
					winningTables.add( t );
				}
			}

			for(Column c : t.getColumns()){
				if(c.getColumn().stream().allMatch( Number::isMarked )) {
					t.addWinningColumns( c );
				}
				List<Table> foundTables = winningTables.stream()
						.filter( x -> x.getId().equals( t.getId() ) )
						.collect( Collectors.toList() );
				//if no table like the current one has been found
				// and the current table contains wins, add it to the winning tables
				if ( foundTables.isEmpty() && !t.getWinningColumns().isEmpty() ) {
					winningTables.add( t );
				}
			}
		}
		System.out.println("Hello");
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
					bingoTables.add( table );
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
				bingoTables.add( table );
			}
		}
		System.out.println("");
	}

	private static List<Integer> generateBingoNumbers(){
		return Arrays.stream( lineReader().get( 0 ).split( "," ) ).map( Integer::parseInt ).collect(
				Collectors.toList());
	}

	private static List<String> lineReader(){
		List<String> lines = new ArrayList<>();
		ClassLoader classLoader = GiantSquid.class.getClassLoader();
		InputStream is = classLoader.getResourceAsStream( "bingom.txt" );
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
}
