package Expressions.Objects;

import Environment.Env;
import Expressions.CodeAndReg;

public class SObjectExp extends PObjectExp{
	String strval;
	
	public SObjectExp(String strval, Env slots,int regnum){
		super(slots,regnum);
		this.strval = strval;
	}
	
	@Override
	public CodeAndReg compile(Env env){
		return this;
	}
}
