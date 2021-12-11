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

	public static void main( String[] args ) {
		List<String> lines  = lineReader();
		bingoNumbers = Arrays.stream( lines.get( 0 ).split( "," ) ).map( Integer::parseInt ).collect(
				Collectors.toList());
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
		}

		for(int bingoNumberIndex=0; bingoNumberIndex<bingoNumbers.size(); bingoNumberIndex++){
			for(Table t : bingoTables){
				for(List<Number> row : t.getRows()){
					final int finalBingoNumberIndex = bingoNumberIndex;
					row.stream().forEach( x -> {
						if ( x.getValue().equals( bingoNumbers.get( finalBingoNumberIndex ) ) ) {
							x.setAsMarked();
						}
					});
				}
			}
			if(bingoNumberIndex > 5){
				System.out.println("Start checks");
			}
		}
		System.out.println("Hello");
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
}
