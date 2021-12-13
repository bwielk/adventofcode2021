import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class SmokeBasin {

	private static int[][] heightmapMatrix;

	public static void main( String[] args ) {
		createHeightmapMatrix();
		System.out.println("");
	}

	private static void createHeightmapMatrix(){
		List<String> fileLines = readInitialState();
		int maximumLengthOfLineInTheFile = 0;
		for(String line : fileLines){
			if(maximumLengthOfLineInTheFile < line.length()){
				maximumLengthOfLineInTheFile = line.length();
			}
		}
		heightmapMatrix = new int[fileLines.size()][maximumLengthOfLineInTheFile];
		for(int i=0; i<heightmapMatrix.length-1; i++){
			String currentLine = fileLines.get( i );
			int[] arrayInt = Stream.of(currentLine.split(""))
					.mapToInt(Integer::parseInt)
					.toArray();
			heightmapMatrix[i] = arrayInt;
		}
	}

	private static List<String> readInitialState(){
		List<String> lines = new ArrayList<>();
		ClassLoader classLoader = SmokeBasin.class.getClassLoader();
		InputStream is = classLoader.getResourceAsStream( "heightmap.txt" );
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
