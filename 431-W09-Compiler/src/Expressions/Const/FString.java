package Expressions.Const;

import Environment.Env;
import Expressions.AbstractCodeAndReg;

public class FString extends AbstractCodeAndReg{
	String s;
	
	public FString(String s,int regnum){
		super(regnum);
		this.s = s;
	}
	
	//TODO compile!
	/*public Value interp(Env env){
		Env slots = null;
		return new SObject(s,slots);
	}*/
}
