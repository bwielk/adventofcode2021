import java.util.Arrays;

public class InputLines {

	private final String[] encodedTenDigits;
	private final String[] outputValues;

	public InputLines( final String[] encodedTenDigits, final String[] outputValues ) {
		this.encodedTenDigits = encodedTenDigits;
		this.outputValues = outputValues;
	}

	public String[] getEncodedTenDigits() {
		return encodedTenDigits;
	}

	public String[] getOutputValues() {
		return outputValues;
	}

	@Override
	public String toString() {
		return "InputLines{" + "encodedTenDigits=" + Arrays.toString(
				encodedTenDigits ) + ", outputValues=" + Arrays.toString( outputValues ) + '}';
	}
}
