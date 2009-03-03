package Expressions.Const;


import java.util.ArrayList;
import java.util.Hashtable;

import Environment.Env;
import Expressions.AbstractCodeAndReg;
import Expressions.CodeAndReg;
import LLVMObjects.LLVMLine;

public class FVoid extends AbstractCodeAndReg{
	public FVoid(int regnum){
		super(regnum);
	}
	
	public CodeAndReg compile(Env env, ArrayList<String> funcdecs, Hashtable<String, Integer> fieldTable){
		LLVMLine currentLine = new LLVMLine(this.reg + " = add i32 0, 10\n");
		//void is 2 + 10 tag bits
		currentLine.setOperation("add");
		currentLine.setRegisterDefined(this.reg);
		return this;
	}
}
