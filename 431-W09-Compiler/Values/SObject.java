package Values;

import Environment.Env;

public class SObject extends PObject{
	public String strval;
	
	public SObject(String strval,Env slots){
		super(slots);
		this.strval = strval;
	}
	
	public String toString() {
		return strval;
	}
	
	public boolean equals(Object o) {
		if(o instanceof String)
			return ((String)o).equals(strval);
		if(o instanceof SObject)
			return o.equals(strval);
		return false;
	}
}
