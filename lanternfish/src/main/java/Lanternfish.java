import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Lanternfish {

	private static List<Integer> initialState;
	private static List<ArrayList<Integer>> dayRecords = new ArrayList<>();
	private static List<Integer> newFish = new ArrayList<>();

	public static void main( String[] args ) {
		readInitialState();
		for(int i=0; i<80; i++){
			List<Integer> currentDayRecord = dayRecords.get( i );
			ArrayList<Integer> newDayList = new ArrayList<>();
			for(int x=0; x<currentDayRecord.size(); x++){
				int currentIntervalNumber =  currentDayRecord.get( x );
				int newIntervalNumber;
				if(currentIntervalNumber-1<0){
					newIntervalNumber = 6;
					newDayList.add( newIntervalNumber );
					newFish.add( 8 );
				}else{
					newDayList.add( currentIntervalNumber-1);
				}
			}
			newDayList.addAll( newFish );
			dayRecords.add( newDayList );
			newFish = new ArrayList<>();
		}
		displaySequence();
		System.out.println("TOTAL FISH AT THE END OF THE PERIOD: " + dayRecords.get( dayRecords.size()-1 ).size());
		System.out.println("TOTAL SUM: " + dayRecords.get( dayRecords.size()-1 ).stream().reduce( 0, (a,b) -> a + b) );
	}

	private static void readInitialState(){
		ClassLoader classLoader = Lanternfish.class.getClassLoader();
		InputStream is = classLoader.getResourceAsStream( "f.txt" );
		try( InputStreamReader streamReader = new InputStreamReader( is, StandardCharsets.UTF_8 );
				BufferedReader reader = new BufferedReader( streamReader )) {
			String line;
			while((line = reader.readLine()) != null) {
				initialState = Arrays.asList(line.split( "," )).stream().map( Integer::parseInt ).collect(
						Collectors.toList());
			}
		} catch ( IOException e ) {
			e.printStackTrace();
		}
		dayRecords.add( (ArrayList<Integer>) initialState );
	}

	private static void displaySequence(){
		for(int i=0; i<dayRecords.size(); i++){
			System.out.println("\n" + dayRecords.get( i ).toString());
		}
	}
}
