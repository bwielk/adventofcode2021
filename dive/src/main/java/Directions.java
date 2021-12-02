public enum Directions {

	UP("up"),
	DOWN("down"),
	FORWARD("forward");

	private String direction;

	Directions(String direction){
		this.direction = direction;
	}

	public String getDirection() {
		return direction;
	}
}
