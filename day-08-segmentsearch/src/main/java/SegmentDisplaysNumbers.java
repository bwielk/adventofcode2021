public enum SegmentDisplaysNumbers {

	ZERO(6),
	ONE(2),
	TWO(5),
	THREE(5),
	FOUR(4),
	FIVE(5),
	SIX(6),
	SEVEN(3),
	EIGHT(7),
	NINE(5);

	int occupiedSegments;

	SegmentDisplaysNumbers(int occupiedSegments){
		this.occupiedSegments = occupiedSegments;
	}

	public int getOccupiedSegments() {
		return occupiedSegments;
	}
}
