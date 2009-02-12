package Expressions.Const;


import Environment.Env;
import Expressions.Expression;
import Values.*;


public class FFloat extends AbstractCodeAndReg{
	public float number;
	
	public FFloat(float number,int regnum){
		super(regnum);
		//this is same as int for now
		this.number = number;
		this.code = this.reg + " = add i32 0, " + number
	}
}
