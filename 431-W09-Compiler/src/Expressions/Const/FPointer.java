package Expressions.Const;

import java.util.ArrayList;
import java.util.Hashtable;

import Environment.Env;
import Expressions.AbstractCodeAndReg;
import Expressions.CodeAndReg;
import LLVMObjects.LLVMLine;


public class FPointer extends AbstractCodeAndReg{
	public int address;
	
	public FPointer(int address, int regnum){
		super(regnum);
		this.address = address;
	}
	
	public CodeAndReg compile(Env env, ArrayList<LLVMLine> funcdecs, Hashtable<String, Integer> fieldTable){
		LLVMLine currentLine;
		
		currentLine = new LLVMLine(this.reg + " = add i32 0, " + ((address << 2) + 1) + "\n");
		currentLine.setOperation("add");
		currentLine.setRegisterDefined(this.reg);
		currentLine.addConstantUsed((address << 2) + 1);
		this.code.add(currentLine);
		
		return this;
	}
}
