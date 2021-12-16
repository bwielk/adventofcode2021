import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class SmokeBasinSearch {

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
				for(Directions d : directions){
					int newX = i+d.getX();
					int newY = row+d.getY();
					if( (newX >= 0 && newX<maximumLengthOfLineInTheFile) &&
							(newY >= 0 && newY<heightmapMatrix.length) ){
						numbers.add(heightmapMatrix[newY][newX]);
					}
				}
				boolean isLowPoint = true;
				for(Integer number : numbers){
					if(currentNumber > number){
						isLowPoint = false;
						break;
					}
				}
				numbers.clear();
				if(isLowPoint){
					resultData.add( new Coordinates( i, row, currentNumber ));
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
