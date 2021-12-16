import java.util.HashMap;
import java.util.Map;

public class Coordinates {

	private final int x;
	private final int y;
	private final int value;
	private HashMap<Directions, Integer> surroundings = new HashMap<>();

	public Coordinates( final int x, final int y, final int value ) {
		this.x = x;
		this.y = y;
		this.value = value;
		this.surroundings.put( Directions.UP, null );
		this.surroundings.put( Directions.RIGHT, null );
		this.surroundings.put( Directions.DOWN, null );
		this.surroundings.put( Directions.LEFT, null );
	}

	public int getY() {
		return y;
	}

	public int getX() {
		return x;
	}

	public int getValue() {
		return value;
	}

	public void setData( final HashMap<Directions, Integer> data ) {
		for ( Map.Entry<Directions, Integer> entry : data.entrySet() ) {
			surroundings.put( entry.getKey(), entry.getValue() );
		}
	}
}
