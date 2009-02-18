package Expressions.Const;


import Environment.Env;
import Expressions.AbstractCodeAndReg;
import Expressions.CodeAndReg;


public class FFloat extends AbstractCodeAndReg{
	public float number;
	
	public FFloat(float number,int regnum){
		super(regnum);
		this.number = number;
	}
	
	public CodeAndReg compile(Env env){
		//TODO this is same as int for now
		this.code.add(this.reg + " = add i32 0, " + number + "\n");
		return this;
	}
}
