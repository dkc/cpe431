package Expressions.Const;

import java.util.ArrayList;
import java.util.Hashtable;

import Environment.Env;
import Expressions.AbstractCodeAndReg;
import Expressions.CodeAndReg;
import LLVMObjects.LLVMLine;

public class FInteger extends AbstractCodeAndReg{
	public int number;
	
	public FInteger(int number,int regnum){
		super(regnum);
		this.number = number;
	}
	
	//public CodeAndReg compile(Env env,String scope){
	public CodeAndReg compile(Env env, ArrayList<LLVMLine> funcdecs, Hashtable<String, Integer> fieldTable){
		LLVMLine currentLine;
		
		currentLine = new LLVMLine(this.reg + " = add i32 0, " + (number << 2) + "\n");
		currentLine.setOperation("add");
		currentLine.setRegisterDefined(this.reg);
		currentLine.addConstantUsed(number<<2);
		this.code.add(currentLine);
		
		return this;
	}
}
