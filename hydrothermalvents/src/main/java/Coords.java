public class Coords {

	private StartCoords startCoords;
	private EndCoords endCoords;

	public Coords( final StartCoords startCoords, final EndCoords endCoords ) {
		this.startCoords = startCoords;
		this.endCoords = endCoords;
	}

	public StartCoords getStartCoords() {
		return startCoords;
	}

	public EndCoords getEndCoords() {
		return endCoords;
	}
}
