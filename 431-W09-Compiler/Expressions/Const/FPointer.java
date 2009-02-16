package Expressions.Const;

import Environment.Env;
import Expressions.AbstractCodeAndReg;
import Expressions.CodeAndReg;


public class FPointer extends AbstractCodeAndReg{
	public int address;
	
	public FPointer(int address, int regnum){
		super(regnum);
		this.address = address;
	}
	
	public CodeAndReg compile(Env env){
		this.code.add(this.reg + " = add i32 0, " + ((address << 2) + 1) + "\n");
		return this;
	}
}
