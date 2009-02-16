package Expressions.Const;


import Environment.Env;
import Expressions.AbstractCodeAndReg;
import Expressions.CodeAndReg;


public class FBoolean extends AbstractCodeAndReg {
	public boolean bool;
	
	public FBoolean(boolean bool,int regnum){
		super(regnum);
		this.bool = bool;
	}
	
	public CodeAndReg compile(Env env){
		if(bool == true){
			//true is 0 with 10 tag
			this.code.add(this.reg + " = add i32 0, 2");
		}else{
			//false is 1 with 10 tag
			this.code.add(this.reg + " = add i32 0, 3");
		}
		return this;
	}
}
