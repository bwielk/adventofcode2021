import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class HydrothermalVents {

	/***
	 * --- Day 5: Hydrothermal Venture ---
	 * You come across a field of hydrothermal vents on the ocean floor! These vents constantly produce large, opaque clouds, so it would be best to avoid them if possible.
	 *
	 * They tend to form in lines; the submarine helpfully produces a list of nearby lines of vents (your puzzle input) for you to review. For example:
	 *
	 * 0,9 -> 5,9
	 * 8,0 -> 0,8
	 * 9,4 -> 3,4
	 * 2,2 -> 2,1
	 * 7,0 -> 7,4
	 * 6,4 -> 2,0
	 * 0,9 -> 2,9
	 * 3,4 -> 1,4
	 * 0,0 -> 8,8
	 * 5,5 -> 8,2
	 * Each line of vents is given as a line segment in the format x1,y1 -> x2,y2 where x1,y1 are the coordinates of one end the line segment and x2,y2 are the coordinates of the other end. These line segments include the points at both ends. In other words:
	 *
	 * An entry like 1,1 -> 1,3 covers points 1,1, 1,2, and 1,3.
	 * An entry like 9,7 -> 7,7 covers points 9,7, 8,7, and 7,7.
	 * For now, only consider horizontal and vertical lines: lines where either x1 = x2 or y1 = y2.
	 *
	 * So, the horizontal and vertical lines from the above list would produce the following diagram:
	 *
	 * .......1..
	 * ..1....1..
	 * ..1....1..
	 * .......1..
	 * .112111211
	 * ..........
	 * ..........
	 * ..........
	 * ..........
	 * 222111....
	 * In this diagram, the top left corner is 0,0 and the bottom right corner is 9,9. Each position is shown as the number of lines which cover that point or . if no line covers that point. The top-left pair of 1s, for example, comes from 2,2 -> 2,1; the very bottom row is formed by the overlapping lines 0,9 -> 5,9 and 0,9 -> 2,9.
	 *
	 * To avoid the most dangerous areas, you need to determine the number of points where at least two lines overlap. In the above example, this is anywhere in the diagram with a 2 or larger - a total of 5 points.
	 *
	 * Consider only horizontal and vertical lines. At how many points do at least two lines overlap?
	 */

	private static int[][] board;
	private static List<Coords> coordinates;

	public static void main( String[] args ) {
		createBoard();
		crateCoordsFromFile();
		runAllMoves();
		countOverlapsOfAmount(2);
	}

	private static void runAllMoves(){
		for(Coords coord : coordinates){
			move(coord);
		}
	}

	private static void move(Coords coord){
		StartCoords startCoords = coord.getStartCoords();
		EndCoords endCoords = coord.getEndCoords();
		int startPointY = Math.min( startCoords.getY(), endCoords.getY() );
		int endPointY = Math.max( startCoords.getY(), endCoords.getY() );
		int startPointX = Math.min( startCoords.getX(), endCoords.getX() );
		int endPointX = Math.max( startCoords.getX(), endCoords.getX() );
		if((startCoords.getX() == endCoords.getX()) && (startCoords.getY() != endCoords.getY())){
			for(int y= startPointY; y<=endPointY; y++){
				int currentValue = board[y][coord.getStartCoords().getX()];
				if(currentValue == 0){
					board[y][coord.getStartCoords().getX()] = 1;
				}else{
					board[y][coord.getStartCoords().getX()] = board[y][coord.getStartCoords().getX()]+1;
				}
			}
		}else if((startCoords.getX() != endCoords.getX()) && (startCoords.getY() == endCoords.getY())) {
			for ( int x = startPointX; x <= endPointX; x++ ) {
				int currentValue = board[coord.getStartCoords().getY()][x];
				if ( currentValue == 0 ) {
					board[coord.getStartCoords().getY()][x] = 1;
				} else {
					board[coord.getStartCoords().getY()][x] = board[coord.getStartCoords().getY()][x] + 1;
				}
			}
		}else if((Math.max( startPointX, startPointY ) - Math.min( startPointX, startPointY ))
				== (Math.max(endPointX, endPointY )) - Math.min( endPointX, endPointY )){
			if((coord.getStartCoords().getX() + coord.getStartCoords().getY()) ==
					(coord.getEndCoords().getX() + coord.getEndCoords().getY())){
				// Calculate a rightward diagonal line - /
				System.out.println("DIAGONAL - /");
				//start from the coord the beginning of which is at the uppermost point
				Coordinable diagonalCoordsStart = coord.getStartCoords().getX() < coord.getEndCoords().getX() ? (coord.getEndCoords()) : coord.getStartCoords();
				int directionalInterval = Math.abs( coord.getStartCoords().getX() - coord.getEndCoords().getX() );
				for(int x=0; x<=directionalInterval; x++){
					int currentValue = board[diagonalCoordsStart.getY()+x][diagonalCoordsStart.getX()-x];
					if(currentValue == 0){
						board[diagonalCoordsStart.getY()+x][diagonalCoordsStart.getX()-x] = 1;
					}else{
						board[diagonalCoordsStart.getY()+x][diagonalCoordsStart.getX()-x] = board[diagonalCoordsStart.getY()+x][diagonalCoordsStart.getX()-x] + 1;
					}
				}
			}else{
				// Calculate a leftward diagonal line - /
				System.out.println("DIAGONAL - \\");
				//start from the coord the beginning of which is at the uppermost point
				Coordinable diagonalCoordsStart = coord.getStartCoords().getX() > coord.getEndCoords().getX() ? (coord.getEndCoords()) : coord.getStartCoords();
				int directionalInterval = Math.abs( coord.getStartCoords().getX() - coord.getEndCoords().getX() );
				for(int x=0; x<=directionalInterval; x++){
					int currentValue = board[diagonalCoordsStart.getY()+x][diagonalCoordsStart.getX()+x];
					if(currentValue == 0){
						board[diagonalCoordsStart.getY()+x][diagonalCoordsStart.getX()+x] = 1;
					}else{
						board[diagonalCoordsStart.getY()+x][diagonalCoordsStart.getX()+x] = board[diagonalCoordsStart.getY()+x][diagonalCoordsStart.getX()+x] + 1;
					}
				}
			}
		}else{
			System.out.println("Unsupported coordinates");
		}
	}

	private static void printBoard(){
		for(int r=0; r<board.length; r++){
			int[] row = board[r];
			System.out.println(Arrays.toString(row));
		}
	}

	private static void countOverlapsOfAmount(int numberOfOverlaps){
		int count = 0;
		for(int r=0; r<board.length; r++){
			for(int i=0; i<board[r].length; i++){
				if(board[r][i] >= numberOfOverlaps){
					count++;
				}
			}
		}
		System.out.println(String.format("Overlaps of more than %s points on the board occur %s times", numberOfOverlaps, count));
	}

	private static void createBoard(){
		board = new int[1000][1000];
	}

	private static void crateCoordsFromFile(){
		List<String> lines = readLines();
		List<Coords> coords = new ArrayList<>();
		for(String line : lines){
			List<String> currentCoordLine = Arrays.asList(line.split( " -> " ));
			List<Integer> currentStartCoords = Arrays.asList( currentCoordLine.get( 0 ).split( "," ) ).stream().map(
					Integer::parseInt ).collect(
					Collectors.toList());
			List<Integer> currentEndCoords = Arrays.asList( currentCoordLine.get( 1 ).split( ",") ).stream().map(
					Integer::parseInt ).collect(
					Collectors.toList());
			StartCoords startCoords = new StartCoords( currentStartCoords.get( 0 ), currentStartCoords.get( 1 ) );
			EndCoords endCoords = new EndCoords( currentEndCoords.get( 0 ), currentEndCoords.get( 1 ) );
			Coords coord = new Coords( startCoords, endCoords );
			coords.add( coord );
		}
		coordinates = coords;
	}


	private static List<String> readLines(){
		List<String> lines = new ArrayList<>();
		ClassLoader classLoader = HydrothermalVents.class.getClassLoader();
		InputStream is = classLoader.getResourceAsStream( "vents.txt" );
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
