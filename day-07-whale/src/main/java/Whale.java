import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Whale {

	/***
	 * --- Day 7: The Treachery of Whales ---
	 * A giant whale has decided your submarine is its next meal, and it's much faster than you are. There's nowhere to run!
	 *
	 * Suddenly, a swarm of crabs (each in its own tiny submarine - it's too deep for them otherwise) zooms in to rescue you! They seem to be preparing to blast a hole in the ocean floor; sensors indicate a massive underground cave system just beyond where they're aiming!
	 *
	 * The crab submarines all need to be aligned before they'll have enough power to blast a large enough hole for your submarine to get through. However, it doesn't look like they'll be aligned before the whale catches you! Maybe you can help?
	 *
	 * There's one major catch - crab submarines can only move horizontally.
	 *
	 * You quickly make a list of the horizontal position of each crab (your puzzle input). Crab submarines have limited fuel, so you need to find a way to make all of their horizontal positions match while requiring them to spend as little fuel as possible.
	 *
	 * For example, consider the following horizontal positions:
	 *
	 * 16,1,2,0,4,2,7,1,2,14
	 * This means there's a crab with horizontal position 16, a crab with horizontal position 1, and so on.
	 *
	 * Each change of 1 step in horizontal position of a single crab costs 1 fuel. You could choose any horizontal position to align them all on, but the one that costs the least fuel is horizontal position 2:
	 *
	 * Move from 16 to 2: 14 fuel
	 * Move from 1 to 2: 1 fuel
	 * Move from 2 to 2: 0 fuel
	 * Move from 0 to 2: 2 fuel
	 * Move from 4 to 2: 2 fuel
	 * Move from 2 to 2: 0 fuel
	 * Move from 7 to 2: 5 fuel
	 * Move from 1 to 2: 1 fuel
	 * Move from 2 to 2: 0 fuel
	 * Move from 14 to 2: 12 fuel
	 * This costs a total of 37 fuel. This is the cheapest possible outcome; more expensive outcomes include aligning at position 1 (41 fuel), position 3 (39 fuel), or position 10 (71 fuel).
	 *
	 * Determine the horizontal position that the crabs can align to using the least fuel possible. How much fuel must they spend to align to that position?
	 */

	private static int[] initialState;
	private static HashMap<Integer, Integer> results;

	public static void main( String[] args ) {
		int maxPositionRange = 1000;
		calculateFuelRating(maxPositionRange);
		int theLeastFuelUsage = findTheMostEfficientFuelConsumption(maxPositionRange);
		findThePositionWithTheLowestFuelConsumption(theLeastFuelUsage);
	}

	public static int findTheMostEfficientFuelConsumption(int maxPositionRange){
		Object[] values = results.values().toArray();
		int theLeastFuelUsage = (int) values[0];
		for(int i=0; i<values.length; i++){
			int currentValue = (int) values[i];
			if(theLeastFuelUsage > currentValue){
				theLeastFuelUsage = currentValue;
			}
		}
		System.out.println(String.format("The lowest fuel usage while aligning in line from 0 to %s is: %s ",
				maxPositionRange, theLeastFuelUsage));
		return theLeastFuelUsage;
	}

	private static void findThePositionWithTheLowestFuelConsumption(int value){
		int position = 0;
		for(Map.Entry<Integer, Integer> e : results.entrySet()){
			if(e.getValue() == value){
				position = e.getKey();
			}
		}
		System.out.println(String.format("THE RANGE POSITION WITH THE LOWEST FUEL USAGE OF %s is : %s", value, position));
	}

	private static void calculateFuelRating(int maxPositionRange){
		results = new HashMap<>();
		readInitialState();
		for(int n=1; n<=maxPositionRange; n++){
			int[] resultStorage = new int[initialState.length];
			for(int i=0; i<initialState.length; i++){
				resultStorage[i] = Math.abs( initialState[i]-n );
			}
			int sum = IntStream.of(resultStorage).sum();
			results.put( n, sum );
		}
		System.out.println(results);
	}

	private static void readInitialState(){
		ClassLoader classLoader = Whale.class.getClassLoader();
		InputStream is = classLoader.getResourceAsStream( "w.txt" );
		try( InputStreamReader streamReader = new InputStreamReader( is, StandardCharsets.UTF_8 );
				BufferedReader reader = new BufferedReader( streamReader )) {
			String line;
			while((line = reader.readLine()) != null) {
				Object[] listOfObjects = Arrays.asList(line.split( "," )).stream().map( Integer::parseInt ).collect(
						Collectors.toList()).toArray();
				initialState = new int[listOfObjects.length];
				for(int i=0; i<listOfObjects.length; i++) {
					int num = (int) listOfObjects[i];
					initialState[i] = num;
				}
			}
		} catch ( IOException e ) {
			e.printStackTrace();
		}
	}
}
