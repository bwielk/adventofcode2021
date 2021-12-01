import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class SonarSweep {

	public static void main( String[] args ) {
		ClassLoader classLoader = SonarSweep.class.getClassLoader();
		InputStream is = classLoader.getResourceAsStream( "numbers.txt" );
		try(InputStreamReader streamReader = new InputStreamReader( is, StandardCharsets.UTF_8 );
				BufferedReader reader = new BufferedReader( streamReader )) {
			String line;
			while((line = reader.readLine()) != null) {
				System.out.println(line);
			}
		} catch ( IOException e ) {
			e.printStackTrace();
		}
	}
}
