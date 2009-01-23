package Values;

public class VFloat implements Value {
	private Float storedFloat;
	
	public VFloat(float f) {
		storedFloat = new Float(f);
	}

	@Override
	public Object storedValue() {
		return storedFloat;
	}
	
	public String toString() {
		return storedFloat.toString();
	}
}
