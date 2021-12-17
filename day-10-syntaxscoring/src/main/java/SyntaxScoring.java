import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class SyntaxScoring {

	/***
	 * --- Day 10: Syntax Scoring ---
	 * You ask the submarine to determine the best route out of the deep-sea cave, but it only replies:
	 *
	 * Syntax error in navigation subsystem on line: all of them
	 * All of them?! The damage is worse than you thought. You bring up a copy of the navigation subsystem (your puzzle input).
	 *
	 * The navigation subsystem syntax is made of several lines containing chunks. There are one or more chunks on each line, and chunks contain zero or more other chunks. Adjacent chunks are not separated by any delimiter; if one chunk stops, the next chunk (if any) can immediately start. Every chunk must open and close with one of four legal pairs of matching characters:
	 *
	 * If a chunk opens with (, it must close with ).
	 * If a chunk opens with [, it must close with ].
	 * If a chunk opens with {, it must close with }.
	 * If a chunk opens with <, it must close with >.
	 * So, () is a legal chunk that contains no other chunks, as is []. More complex but valid chunks include ([]), {()()()}, <([{}])>, [<>({}){}[([])<>]], and even (((((((((()))))))))).
	 *
	 * Some lines are incomplete, but others are corrupted. Find and discard the corrupted lines first.
	 *
	 * A corrupted line is one where a chunk closes with the wrong character - that is, where the characters it opens and closes with do not form one of the four legal pairs listed above.
	 *
	 * Examples of corrupted chunks include (], {()()()>, (((()))}, and <([]){()}[{}]). Such a chunk can appear anywhere within a line, and its presence causes the whole line to be considered corrupted.
	 *
	 * For example, consider the following navigation subsystem:
	 *
	 * [({(<(())[]>[[{[]{<()<>>
	 * [(()[<>])]({[<{<<[]>>(
	 * {([(<{}[<>[]}>{[]{[(<()>
	 * (((({<>}<{<{<>}{[]{[]{}
	 * [[<[([]))<([[{}[[()]]]
	 * [{[{({}]{}}([{[{{{}}([]
	 * {<[[]]>}<{[{[{[]{()[[[]
	 * [<(<(<(<{}))><([]([]()
	 * <{([([[(<>()){}]>(<<{{
	 * <{([{{}}[<[[[<>{}]]]>[]]
	 * Some of the lines aren't corrupted, just incomplete; you can ignore these lines for now. The remaining five lines are corrupted:
	 *
	 * {([(<{}[<>[]}>{[]{[(<()> - Expected ], but found } instead.
	 * [[<[([]))<([[{}[[()]]] - Expected ], but found ) instead.
	 * [{[{({}]{}}([{[{{{}}([] - Expected ), but found ] instead.
	 * [<(<(<(<{}))><([]([]() - Expected >, but found ) instead.
	 * <{([([[(<>()){}]>(<<{{ - Expected ], but found > instead.
	 * Stop at the first incorrect closing character on each corrupted line.
	 *
	 * Did you know that syntax checkers actually have contests to see who can get the high score for syntax errors in a file? It's true! To calculate the syntax error score for a line, take the first illegal character on the line and look it up in the following table:
	 *
	 * ): 3 points.
	 * ]: 57 points.
	 * }: 1197 points.
	 * >: 25137 points.
	 * In the above example, an illegal ) was found twice (2*3 = 6 points), an illegal ] was found once (57 points), an illegal } was found once (1197 points), and an illegal > was found once (25137 points). So, the total syntax error score for this file is 6+57+1197+25137 = 26397 points!
	 *
	 * Find the first illegal character in each corrupted line of the navigation subsystem. What is the total syntax error score for those errors?
	 */

	private static List<String> brackets = Arrays.asList("[]", "{}", "<>", "()");

	public static void main( String[] args ) {
		processLines();
	}

	public static void processLines() {
		HashMap<Character, Integer> foundFailures = new HashMap<>();
		foundFailures.put( '}', 0 );
		foundFailures.put( ')', 0 );
		foundFailures.put( '>', 0 );
		foundFailures.put( ']', 0 );
		List<String> lines = readInitialState();
		for(String line : lines){
			String currentLine = line;
				while ( Arrays.stream( new String[] {"[]", "{}", "<>", "()"} ).anyMatch( currentLine::contains ) ) {
					for ( String bracket : brackets ) {
						currentLine = currentLine.replace( bracket, "" );
						System.out.println( currentLine );
					}
				}
			char[] charsOfCurrentLine = currentLine.toCharArray();
			for(int i=0; i<charsOfCurrentLine.length-1; i++){
				char currentChar = charsOfCurrentLine[i];
				char nextChar = charsOfCurrentLine[i+1];
//					if(nextChar matches Any of the opening  - then do nothing )
				if( Arrays.stream( new Character[] {'[', '{', '<', '('} ).anyMatch( x -> currentChar == x)
					&& Arrays.stream( new Character[] {'[', '{', '<', '('} ).anyMatch( x -> nextChar == x)){
					System.out.println("Go next");
				}else{
					if(currentChar != nextChar){
						foundFailures.put( nextChar, foundFailures.get( nextChar )+1 );
						break;
					}
				}
			}
		}
		System.out.println(foundFailures);
	}

	private static List<String> readInitialState(){
		List<String> lines = new ArrayList<>();
		InputStream is = SyntaxScoring.class.getClassLoader().getResourceAsStream( "test.txt" );
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
