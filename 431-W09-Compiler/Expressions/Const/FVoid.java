package Expressions.Const;


import Environment.Env;

public class FVoid extends AbstractCodeAndReg{
	public FVoid(int regnum){
		super(regnum);
	}
	
	public CodeAndReg compile(){
		//void is 2 + 10 tag bits
		this.code = this.reg + " = add i32 0, " + 10
		return this;
	}
}
