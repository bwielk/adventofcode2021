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
	private static List<Integer> dayRecords = new ArrayList<>();
	private static List<Integer> newFish = new ArrayList<>();

	public static void main( String[] args ) {
		readInitialState();
		calculateFish(80);
		displaySequence();
		System.out.println("TOTAL FISH AT THE END OF THE PERIOD: " + dayRecords.size());
		System.out.println("TOTAL SUM OF DAYS: " + dayRecords.stream().reduce( 0, (a,b) -> a + b) );
	}
	
	private static void calculateFish(int target){
		for(int i=0; i<target; i++){
			ArrayList<Integer> newDayList = new ArrayList<>();
			for(int x=0; x<dayRecords.size(); x++){
				int currentIntervalNumber =  dayRecords.get( x );
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
			dayRecords = newDayList;
			newFish = new ArrayList<>();
		}
		displaySequence();
		System.out.println("TOTAL FISH AT THE END OF THE PERIOD: " + dayRecords.size());
		System.out.println("TOTAL SUM OF DAYS: " + dayRecords.stream().reduce( 0, (a,b) -> a + b) );
	}

	private static void readInitialState(){
		ClassLoader classLoader = Lanternfish.class.getClassLoader();
		InputStream is = classLoader.getResourceAsStream( "fish.txt" );
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
		dayRecords.addAll( initialState );
	}

	private static void displaySequence(){
			System.out.println("\n" + dayRecords.toString());
	}
}
