package Expressions.Const;


import java.util.ArrayList;
import java.util.Hashtable;

import Environment.Env;
import Expressions.AbstractCodeAndReg;
import Expressions.CodeAndReg;
import LLVMObjects.LLVMLine;


public class FBoolean extends AbstractCodeAndReg {
	public boolean bool;
	
	public FBoolean(boolean bool,int regnum){
		super(regnum);
		this.bool = bool;
	}
	

	public CodeAndReg compile(Env env, ArrayList<LLVMLine> funcdecs, Hashtable<String, Integer> fieldTable){
		LLVMLine currentLine;
		if(bool == true){
			//true is 0 with 10 tag
			currentLine = new LLVMLine(this.reg + " = add i32 0, 2\n");
			currentLine.setOperation("add");
			currentLine.setRegisterDefined(this.reg);
			currentLine.addConstantUsed(2);
			this.code.add(currentLine);
		}else{
			//false is 1 with 10 tag
			currentLine = new LLVMLine(this.reg + " = add i32 0, 3\n");
			currentLine.setOperation("add");
			currentLine.setRegisterDefined(this.reg);
			currentLine.addConstantUsed(3);
			this.code.add(currentLine);
		}
		this.code.add(currentLine);
		return this;
	}
}
