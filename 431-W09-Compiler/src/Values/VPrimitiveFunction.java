package Values;

public class VPrimitiveFunction implements Value {

	String func;
	
	public VPrimitiveFunction (String f) {
		func = f;
	}
	
	@Override
	public Object storedValue() {
		return func;
	}

}
