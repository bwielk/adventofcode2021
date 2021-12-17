import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Stream;

public class SmokeBasinSearch {

	/***
	 * --- Day 9: Smoke Basin ---
	 * These caves seem to be lava tubes. Parts are even still volcanically active; small hydrothermal vents release smoke into the caves that slowly settles like rain.
	 *
	 * If you can model how the smoke flows through the caves, you might be able to avoid it and be that much safer. The submarine generates a heightmap of the floor of the nearby caves for you (your puzzle input).
	 *
	 * Smoke flows to the lowest point of the area it's in. For example, consider the following heightmap:
	 *
	 * 2199943210
	 * 3987894921
	 * 9856789892
	 * 8767896789
	 * 9899965678
	 * Each number corresponds to the height of a particular location, where 9 is the highest and 0 is the lowest a location can be.
	 *
	 * Your first goal is to find the low points - the locations that are lower than any of its adjacent locations. Most locations have four adjacent locations (up, down, left, and right); locations on the edge or corner of the map have three or two adjacent locations, respectively. (Diagonal locations do not count as adjacent.)
	 *
	 * In the above example, there are four low points, all highlighted: two are in the first row (a 1 and a 0), one is in the third row (a 5), and one is in the bottom row (also a 5). All other locations on the heightmap have some lower adjacent location, and so are not low points.
	 *
	 * The risk level of a low point is 1 plus its height. In the above example, the risk levels of the low points are 2, 1, 6, and 6. The sum of the risk levels of all low points in the heightmap is therefore 15.
	 *
	 * Find all of the low points on your heightmap. What is the sum of the risk levels of all low points on your heightmap?
	 *
	 * Your puzzle answer was 444.
	 *
	 * The first half of this puzzle is complete! It provides one gold star: *
	 *
	 * --- Part Two ---
	 * Next, you need to find the largest basins so you know what areas are most important to avoid.
	 *
	 * A basin is all locations that eventually flow downward to a single low point. Therefore, every low point has a basin, although some basins are very small. Locations of height 9 do not count as being in any basin, and all other locations will always be part of exactly one basin.
	 *
	 * The size of a basin is the number of locations within the basin, including the low point. The example above has four basins.
	 *
	 * The top-left basin, size 3:
	 *
	 * 2199943210
	 * 3987894921
	 * 9856789892
	 * 8767896789
	 * 9899965678
	 * The top-right basin, size 9:
	 *
	 * 2199943210
	 * 3987894921
	 * 9856789892
	 * 8767896789
	 * 9899965678
	 * The middle basin, size 14:
	 *
	 * 2199943210
	 * 3987894921
	 * 9856789892
	 * 8767896789
	 * 9899965678
	 * The bottom-right basin, size 9:
	 *
	 * 2199943210
	 * 3987894921
	 * 9856789892
	 * 8767896789
	 * 9899965678
	 * Find the three largest basins and multiply their sizes together. In the above example, this is 9 * 14 * 9 = 1134.
	 *
	 * What do you get if you multiply together the sizes of the three largest basins?
	 */

	private static int maximumLengthOfLineInTheFile = 0;
	private static int[][] heightmapMatrix;
	private static List<Coordinates> resultData;
	private static List<Directions> directions = List.of(Directions.UP, Directions.RIGHT, Directions.DOWN, Directions.LEFT);

	public static void main( String[] args ) {
		createHeightmapMatrix();
		checkNeighbouringPoints();
		calculateRiskLevel();
	}

	private static void calculateRiskLevel(){
		int riskLevel = resultData.size() +
				resultData.stream().mapToInt( Coordinates::getValue ).sum();
		System.out.println( "RISK LEVEL FOR ALL LOW POINTS: " +  riskLevel);
	}

	private static void checkNeighbouringPoints(){
		resultData = new ArrayList<>();
		for(int row=0; row<heightmapMatrix.length; row++){
			List<Integer> numbers = new ArrayList<>();
			for(int i=0; i<heightmapMatrix[row].length; i++){
				int currentNumber = heightmapMatrix[row][i];
				HashMap<Directions, Integer> surroundings = new HashMap<>();
				for(Directions d : directions){
					int newX = i+d.getX();
					int newY = row+d.getY();
					if( (newX >= 0 && newX<maximumLengthOfLineInTheFile) &&
							(newY >= 0 && newY<heightmapMatrix.length) ){
						int foundValue = heightmapMatrix[newY][newX];
						surroundings.put(d, foundValue);
						numbers.add(foundValue);
					}
				}
				boolean isLowPoint = true;
				for(Integer number : numbers){
					if(currentNumber >= number){
						isLowPoint = false;
						break;
					}
				}
				numbers.clear();
				if(isLowPoint){
					Coordinates coordinates = new Coordinates( i, row, currentNumber );
					coordinates.setData( surroundings);
					resultData.add( coordinates );
				}
			}
		}
	}

	private static void createHeightmapMatrix(){
		List<String> fileLines = readInitialState();
		for(String line : fileLines){
			if(maximumLengthOfLineInTheFile < line.length()){
				maximumLengthOfLineInTheFile = line.length();
			}
		}
		heightmapMatrix = new int[fileLines.size()][maximumLengthOfLineInTheFile];
		for(int i=0; i<heightmapMatrix.length; i++){
			String currentLine = fileLines.get( i );
			int[] arrayInt = Stream.of(currentLine.split(""))
					.mapToInt(Integer::parseInt)
					.toArray();
			heightmapMatrix[i] = arrayInt;
		}
	}

	private static List<String> readInitialState(){
		List<String> lines = new ArrayList<>();
		InputStream is = SmokeBasinSearch.class.getClassLoader().getResourceAsStream( "heightmap.txt" );
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
