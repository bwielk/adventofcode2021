import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

	private static final String[] brackets = new String[] {"[]", "{}", "<>", "()"};
	private static final Character[] bracketOpenings = new Character[] {'[', '{', '<', '('};
	private static final HashMap<Character, Integer> foundSyntaxErrors = new HashMap<>();

	public static void main( String[] args ) {
		defineFoundFailuresDict();
		identifySyntaxErrors();
		calculateSyntaxErrorScore();
	}

	public static void identifySyntaxErrors() {
		List<String> lines = readInitialState();
		for(String line : lines){
			String currentLine = line;
				while ( Arrays.stream( brackets ).anyMatch( currentLine::contains ) ) {
					for ( String bracket : brackets ) {
						currentLine = currentLine.replace( bracket, "" );
					}
				}
			char[] charsOfCurrentLine = currentLine.toCharArray();
			System.out.println("\nPROCESSING POST CLEANUP LINE: " + currentLine);
			for(int i=0; i<charsOfCurrentLine.length-1; i++){
				char currentChar = charsOfCurrentLine[i];
				char nextChar = charsOfCurrentLine[i+1];
				if( Arrays.stream( bracketOpenings ).anyMatch( x -> currentChar == x)
					&& Arrays.stream( bracketOpenings ).anyMatch( x -> nextChar == x)){
					System.out.println(String.format( "Current char - index: %s value :%s\nNext char - value: %s",
							i, currentChar, nextChar ));
				}else{
					if(currentChar != nextChar){
						foundSyntaxErrors.put( nextChar, foundSyntaxErrors.get( nextChar )+1 );
						//only search for the first one in line
						break;
					}
				}
			}
		}
	}

	private static int calculateSyntaxErrorScore(){
		int score = 0;
		for( Map.Entry<Character, Integer> entry : foundSyntaxErrors.entrySet()){
			for(Scores scores : Scores.values()){
				if(entry.getKey().equals(scores.getBracketClosing())){
					score += entry.getValue()*scores.getScore();
				}
			}
		}
		System.out.println("CALCULATED ERROR SCORE: " + score);
		return score;
	}

	private static void defineFoundFailuresDict(){
		foundSyntaxErrors.put( '}', 0 );
		foundSyntaxErrors.put( ')', 0 );
		foundSyntaxErrors.put( '>', 0 );
		foundSyntaxErrors.put( ']', 0 );
	}

	private static List<String> readInitialState(){
		return FileReaderUtil.readFileAsLinesOfStrings( SyntaxScoring.class.getClassLoader(), "input.txt" );
	}
}
