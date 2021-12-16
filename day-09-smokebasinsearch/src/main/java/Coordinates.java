public class Coordinates {

	private final int x;
	private final int y;
	private final int value;

	public Coordinates( final int x, final int y, final int value ) {
		this.x = x;
		this.y = y;
		this.value = value;
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
}
