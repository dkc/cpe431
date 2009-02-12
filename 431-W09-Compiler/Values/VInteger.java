package Values;

public class VInteger implements Value {
	private Integer integer;
	
	public VInteger(Integer integer){
		this.integer = integer;
	}
	
	public Object storedValue(){
		return this.integer;
	}
	
	public String toString() {
		return integer.toString();
	}
}
