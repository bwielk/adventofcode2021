import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

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
	 * Your puzzle answer was 345035.
	 *
	 * The first half of this puzzle is complete! It provides one gold star: *
	 *
	 * --- Part Two ---
	 * The crabs don't seem interested in your proposed solution. Perhaps you misunderstand crab engineering?
	 *
	 * As it turns out, crab submarine engines don't burn fuel at a constant rate. Instead, each change of 1 step in horizontal position costs 1 more unit of fuel than the last: the first step costs 1, the second step costs 2, the third step costs 3, and so on.
	 *
	 * As each crab moves, moving further becomes more expensive. This changes the best horizontal position to align them all on; in the example above, this becomes 5:
	 *
	 * Move from 16 to 5: 66 fuel
	 * Move from 1 to 5: 10 fuel
	 * Move from 2 to 5: 6 fuel
	 * Move from 0 to 5: 15 fuel
	 * Move from 4 to 5: 1 fuel
	 * Move from 2 to 5: 6 fuel
	 * Move from 7 to 5: 3 fuel
	 * Move from 1 to 5: 10 fuel
	 * Move from 2 to 5: 6 fuel
	 * Move from 14 to 5: 45 fuel
	 * This costs a total of 168 fuel. This is the new cheapest possible outcome; the old alignment position (2) now costs 206 fuel instead.
	 *
	 * Determine the horizontal position that the crabs can align to using the least fuel possible so they can make you an escape route! How much fuel must they spend to align to that position?
	 */

	private static int[] initialState;
	private static HashMap<Integer, Integer> resultsConstantRate;
	private static HashMap<Integer, Integer> resultsIncrementalRate;
	private static int maxPositionRange = 1000;

	public static void main( String[] args ) {
		calculateFuelRatingConstantRate(maxPositionRange);
		int theLeastFuelUsageConstantRate = findTheMostEfficientFuelConsumption(maxPositionRange, resultsConstantRate, FuelUsage.CONSTANT);
		findThePositionWithTheLowestFuelConsumptionConstantRate(theLeastFuelUsageConstantRate, resultsConstantRate, FuelUsage.CONSTANT);
		calculateFuelRatingIncrementalRate(maxPositionRange);
		int theLeastFuelUsageIncrementalRate = findTheMostEfficientFuelConsumption(maxPositionRange, resultsIncrementalRate, FuelUsage.INCREMENTAL);
		findThePositionWithTheLowestFuelConsumptionConstantRate(theLeastFuelUsageIncrementalRate, resultsIncrementalRate, FuelUsage.INCREMENTAL);

	}

	public static int findTheMostEfficientFuelConsumption(int maxPositionRange, HashMap<Integer, Integer> listToAnalyse, FuelUsage fuelUsage){
		Object[] values = listToAnalyse.values().toArray();
		int theLeastFuelUsage = (int) values[0];
		for(int i=0; i<values.length; i++){
			int currentValue = (int) values[i];
			if(theLeastFuelUsage > currentValue){
				theLeastFuelUsage = currentValue;
			}
		}
		System.out.println(String.format("The lowest fuel usage while aligning in line from "
						+ "0 to %s at the %s rate is: %s ",
				maxPositionRange, fuelUsage.getType(), theLeastFuelUsage));
		return theLeastFuelUsage;
	}

	private static void findThePositionWithTheLowestFuelConsumptionConstantRate(int value, HashMap<Integer, Integer> listToAnalyse, FuelUsage fuelUsage){
		int position = 0;
		for(Map.Entry<Integer, Integer> e : listToAnalyse.entrySet()){
			if(e.getValue() == value){
				position = e.getKey();
			}
		}
		System.out.println(String.format("THE RANGE POSITION WITH THE LOWEST FUEL USAGE OF %s "
				+ "AT THE %s RATE is : %s", value, fuelUsage.getType(), position));
	}

	private static void calculateFuelRatingConstantRate(int maxPositionRange){
		resultsConstantRate = new HashMap<>();
		readInitialState();
		for(int n=1; n<=maxPositionRange; n++){
			int result = 0;
			for(int i=0; i<initialState.length; i++){
				result += Math.abs( initialState[i]-n );
			}
			resultsConstantRate.put( n, result );
		}
		System.out.println( "Constant rate:\n" + resultsConstantRate );
	}

	private static void calculateFuelRatingIncrementalRate(int maxPositionRange){
		resultsIncrementalRate = new HashMap<>();
		readInitialState();
		for(int n=1; n<=maxPositionRange; n++){
			int totalFuelUsageUntilPositionReached = 0;
			for(int i=0; i<initialState.length; i++){
				int numberOfMoves = Math.abs( initialState[i] - n);
				for(int move=0; move<numberOfMoves; move++){
					totalFuelUsageUntilPositionReached+= 1+move;
				}
			}
			resultsIncrementalRate.put( n, totalFuelUsageUntilPositionReached );
		}
		System.out.println( "Incremental rate:\n" + resultsIncrementalRate );
	}

	private static void readInitialState(){
		ClassLoader classLoader = Whale.class.getClassLoader();
		InputStream is = classLoader.getResourceAsStream( "whale.txt" );
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
