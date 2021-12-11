public class Number {

	private Integer value;
	private boolean isMarked;

	public Number( final Integer value ) {
		this.value = value;
		this.isMarked = false;
	}

	public boolean isMarked() {
		return isMarked;
	}

	public Integer getValue() {
		return value;
	}

	public void setAsMarked(){
		this.isMarked = true;
	}
}
