public enum FuelUsage {

	CONSTANT("CONSTANT"),
	INCREMENTAL("INCREMENTAL");

	String type;

	FuelUsage(String type){
		this.type = type;
	}

	public String getType() {
		return type;
	}
}
