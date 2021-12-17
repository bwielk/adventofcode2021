import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class FileReaderUtil {

	public static List<String> readFileAsLinesOfStrings( ClassLoader classLoader,
			String filePath ) {
		List<String> lines = new ArrayList<>();
		InputStream is = classLoader.getResourceAsStream( filePath );
		try ( InputStreamReader streamReader = new InputStreamReader( is, StandardCharsets.UTF_8 );
				BufferedReader reader = new BufferedReader( streamReader ) ) {
			String line;
			while ( ( line = reader.readLine() ) != null ) {
				lines.add( line );
			}
		} catch ( IOException e ) {
			e.printStackTrace();
		}
		return lines;
	}
}
