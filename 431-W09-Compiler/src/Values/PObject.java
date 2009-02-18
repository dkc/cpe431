package Values;
import Environment.Env;

public class PObject implements VObject {
	public Env slots;
	
	public PObject(Env slots){
		this.slots = slots;
	}
	
	public Object storedValue(){
		return this;
	}
	
	public boolean equals(Object o) {
		if(o == null || !(o instanceof PObject)) {
			return false;
		}
		PObject other = (PObject)o;
		return other.slots.equals(this.slots);
	}
}