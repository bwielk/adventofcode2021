public class StartCoords {

	private int x;
	private int y;

	public StartCoords( final int x, final int y ) {
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	@Override
	public String toString() {
		return "StartCoords{" + "x=" + x + ", y=" + y + '}';
	}
}
