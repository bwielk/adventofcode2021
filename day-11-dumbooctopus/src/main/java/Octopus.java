public class Octopus {

	private int value;
	private final int x;
	private final int y;

	public Octopus( final int value, final int x, final int y ) {
		this.value = value;
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getValue() {
		return value;
	}

	public void setValue( final int value ) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "" + value + "";
	}
}
