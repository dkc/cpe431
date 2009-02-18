package Values;

public class VBoolean implements Value {
	
	Boolean storedBoolean;
	
	public VBoolean(boolean b) {
		storedBoolean = new Boolean(b);
	}

	@Override
	public Object storedValue() {
		return storedBoolean;
	}
	
	public String toString() {
		return storedBoolean.toString();
	}
}
