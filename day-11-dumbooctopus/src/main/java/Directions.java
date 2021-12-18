public enum Directions {

	TOP(0, -1),
	TOP_RIGHT(1, -1),
	RIGHT(1, 0),
	BOTTOM_RIGHT(1, 1),
	DOWN(0, 1),
	BOTTOM_LEFT(-1, 1),
	LEFT(-1, 0),
	TOP_LEFT(-1, -1);

	int x;
	int y;

	Directions(int x, int y){
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
}
