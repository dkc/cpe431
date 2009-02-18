package Values;

import Environment.Env;

public class CObject extends PObject{
	public VClosure clos;
	
	public CObject(VClosure clos,Env slots){
		super(slots);
		this.clos = clos;
	}
}
