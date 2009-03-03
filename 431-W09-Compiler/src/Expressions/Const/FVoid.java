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
	
	public CodeAndReg compile(Env env, ArrayList<LLVMLine> funcdecs, Hashtable<String, Integer> fieldTable){
		LLVMLine currentLine;
		
		//void is 2 + 10 tag bits
		currentLine = new LLVMLine(this.reg + " = add i32 0, 10\n");
		currentLine.setOperation("add");
		currentLine.setRegisterDefined(this.reg);
		currentLine.addConstantUsed(10);
		this.code.add(currentLine);
		
		return this;
	}
}
