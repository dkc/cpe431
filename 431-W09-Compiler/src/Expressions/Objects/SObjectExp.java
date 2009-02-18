package Expressions.Objects;

import Values.*;
import Environment.Env;

public class SObjectExp extends PObjectExp{
	String strval;
	
	public SObjectExp(String strval, Env slots){
		super(slots);
		this.strval = strval;
	}
	
	@Override
	public Value interp(Env env){
		return new SObject(strval,slots);
	}
}
