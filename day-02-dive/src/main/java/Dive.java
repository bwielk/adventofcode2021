import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class Dive {

	/***
	 --- Day 2: Dive! ---
	 Now, you need to figure out how to pilot this thing.

	 It seems like the submarine can take a series of commands like forward 1, down 2, or up 3:

	 forward X increases the horizontal position by X units.
	 down X increases the depth by X units.
	 up X decreases the depth by X units.
	 Note that since you're on a submarine, down and up affect your depth, and so they have the opposite result of what you might expect.

	 The submarine seems to already have a planned course (your puzzle input). You should probably figure out where it's going. For example:

	 forward 5
	 down 5
	 forward 8
	 up 3
	 down 8
	 forward 2
	 Your horizontal position and depth both start at 0. The steps above would then modify them as follows:

	 forward 5 adds 5 to your horizontal position, a total of 5.
	 down 5 adds 5 to your depth, resulting in a value of 5.
	 forward 8 adds 8 to your horizontal position, a total of 13.
	 up 3 decreases your depth by 3, resulting in a value of 2.
	 down 8 adds 8 to your depth, resulting in a value of 10.
	 forward 2 adds 2 to your horizontal position, a total of 15.
	 After following these instructions, you would have a horizontal position of 15 and a depth of 10. (Multiplying these together produces 150.)

	 Calculate the horizontal position and depth you would have after following the planned course. What do you get if you multiply your final horizontal position by your final depth?
	 */

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
		return FileReaderUtil.readFileAsLinesOfStrings( Dive.class.getClassLoader(), "directions.txt" );
	}
}
