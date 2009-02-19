package Expressions.Objects;

import Environment.Env;
import Expressions.CodeAndReg;
import Values.*;

public class CObjectExp extends PObjectExp{
	VClosure clos;
	
	public CObjectExp(VClosure clos,Env slots,int regnum){
		super(slots,regnum);
		this.clos = clos;
	}
	
	@Override
	public CodeAndReg compile(Env env){
		return this;
	}
}
