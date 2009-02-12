package Expressions.Const;

import Environment.Env;

public class FInteger extends AbstractCodeAndReg{
	public int number;
	
	public FInteger(int number,int regnum){
		super(regnum);
		this.number = number;
	}
	
	//public CodeAndReg compile(Env env,String scope){
	public CodeAndReg compile(){
		this.code = this.reg + " = add i32 0, " + (number << 2) + "\n";
		return this;
	}
}
