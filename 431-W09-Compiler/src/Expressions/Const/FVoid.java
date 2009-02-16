package Expressions.Const;


import Environment.Env;
import Expressions.AbstractCodeAndReg;
import Expressions.CodeAndReg;

public class FVoid extends AbstractCodeAndReg{
	public FVoid(int regnum){
		super(regnum);
	}
	
	public CodeAndReg compile(Env env){
		//void is 2 + 10 tag bits
		this.code.add(this.reg + " = add i32 0, " + 10 + "\n");
		return this;
	}
}
