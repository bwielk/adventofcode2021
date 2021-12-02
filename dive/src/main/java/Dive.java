import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class Dive {

	public static void main( String[] args ) {
		HashMap<Directions, Integer> coords = calculateMovements();
		Integer finalDepth = coords.get( Directions.DOWN) - coords.get( Directions.UP );
		Integer finalHorizontalPosition = coords.get( Directions.FORWARD );
		System.out.println(String.format("\nFINAL DEPTH (FD): %s"
						+ "\nFINAL HORIZONTAL POSTION (FHP): %s"
						+ "\nMULTIPLIED FD AND FHP: %s",
				finalDepth, finalHorizontalPosition, finalDepth*finalHorizontalPosition));
	}

	private static HashMap<Directions, Integer> calculateMovements(){
		HashMap<Directions, Integer> movements = new HashMap<>();
		movements.put( Directions.DOWN, 0 );
		movements.put( Directions.UP, 0 );
		movements.put( Directions.FORWARD, 0 );
		List<String> lines = lineReader();
		for(String line : lines){
			if(line.toLowerCase( Locale.ROOT ).contains(Directions.FORWARD.getDirection().toLowerCase())){
				Integer currentValue = movements.get( Directions.FORWARD );
				Integer valueToAdd = Integer.parseInt(line.replaceAll("\\D+",""));
				movements.put( Directions.FORWARD, currentValue + valueToAdd );
			}
			if(line.toLowerCase().contains(Directions.DOWN.getDirection().toLowerCase())){
				Integer currentValue = movements.get( Directions.DOWN );
				Integer valueToAdd = Integer.parseInt(line.replaceAll("\\D+",""));
				movements.put( Directions.DOWN, currentValue + valueToAdd );
			}
			if(line.toLowerCase().contains(Directions.UP.getDirection().toLowerCase())){
				Integer currentValue = movements.get( Directions.UP );
				Integer valueToAdd = Integer.parseInt(line.replaceAll("\\D+",""));
				movements.put( Directions.UP, currentValue + valueToAdd );
			}
		}
		return movements;
	}

	private static List<String> lineReader(){
		List<String> lines = new ArrayList<>();
		ClassLoader classLoader = Dive.class.getClassLoader();
		InputStream is = classLoader.getResourceAsStream( "directions.txt" );
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
