package Expressions.Objects;

import Environment.Env;
import Values.*;

public class CObjectExp extends PObjectExp{
	VClosure clos;
	
	public CObjectExp(VClosure clos,Env slots){
		super(slots);
		this.clos = clos;
	}
	
	@Override
	public Value interp(Env env){
		return new CObject(clos,slots);
	}
}
