package Expressions.Const;

import Environment.Env;
import Expressions.AbstractCodeAndReg;
import Expressions.CodeAndReg;

public class FInteger extends AbstractCodeAndReg{
	public int number;
	
	public FInteger(int number,int regnum){
		super(regnum);
		this.number = number;
	}
	
	//public CodeAndReg compile(Env env,String scope){
	public CodeAndReg compile(Env env){
		this.code.add(this.reg + " = add i32 0, " + (number << 2) + "\n");
		return this;
	}
}
