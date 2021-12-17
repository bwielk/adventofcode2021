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
			if(nextNumber > currentNumber){
				decreasedNumbers.add( currentNumber );
			}
		}
		System.out.println("NUMBER OF DECREASED NUMBERS : " + decreasedNumbers.size());
	}

	private static List<String> lineReader() {
		return FileReaderUtil.readFileAsLinesOfStrings( SonarSweep.class.getClassLoader(),
				"numbers.txt" );
	}
}
