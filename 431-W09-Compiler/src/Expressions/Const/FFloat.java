package Expressions.Const;


import java.util.ArrayList;
import java.util.Hashtable;

import Environment.Env;
import Expressions.AbstractCodeAndReg;
import Expressions.CodeAndReg;
import LLVMObjects.LLVMLine;


public class FFloat extends AbstractCodeAndReg{
	public float number;
	
	public FFloat(float number,int regnum){
		super(regnum);
		this.number = number;
	}
	
	public CodeAndReg compile(Env env, ArrayList<LLVMLine> funcdecs, Hashtable<String, Integer> fieldTable){
		//TODO this is same as int for now
		LLVMLine currentLine;
		
		currentLine = new LLVMLine(this.reg + " = add i32 0, " + number + "\n");
		currentLine.setOperation("add");
		currentLine.setRegisterDefined(this.reg);
		currentLine.addConstantUsed(number);
		
		this.code.add(currentLine);
		
		return this;
	}
}
