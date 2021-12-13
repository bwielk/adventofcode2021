public class EndCoords implements Coordinable{

	private int x;
	private int y;

	public EndCoords( final int x, final int y ) {
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
		return "EndCoords{" + "x=" + x + ", y=" + y + '}';
	}
}
