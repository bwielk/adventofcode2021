import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class SonarSweep {

	public static void main( String[] args ) {
		List<String> numbers = lineReader();
		List<Integer> decreasedNumbers = new ArrayList<>();
		for(int i=0; i< numbers.size()-1; i++){
			int currentNumber = Integer.parseInt(numbers.get( i ));
			int nextNumber = Integer.parseInt(numbers.get( i+1 ));
			System.out.println(String.format( "\nPair of numbers %s - %s", numbers.get( i ), numbers.get( i +1 )));
			if(currentNumber > nextNumber){
				decreasedNumbers.add( currentNumber );
			}
		}
		System.out.println("NUMBER OF DECREASED NUMBERS : " + decreasedNumbers.size());
	}

	private static List<String> lineReader(){
		List<String> lines = new ArrayList<>();
		ClassLoader classLoader = SonarSweep.class.getClassLoader();
		InputStream is = classLoader.getResourceAsStream( "numbers.txt" );
		try(InputStreamReader streamReader = new InputStreamReader( is, StandardCharsets.UTF_8 );
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
