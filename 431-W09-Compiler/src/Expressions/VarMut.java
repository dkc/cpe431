package Expressions;

import java.util.ArrayList;
import java.util.Hashtable;

import Environment.*;
import LLVMObjects.LLVMLine;

public class VarMut extends AbstractCodeAndReg{
	String id;
	CodeAndReg newVal;
	String ptrreg;
	
	public VarMut(String id, CodeAndReg newVal, int regnum){
		super(regnum);
		this.id = id;
		this.newVal = newVal;
		this.ptrreg = "%ptrreg" + regnum;
	}
	
	public CodeAndReg compile(Env env, ArrayList<LLVMLine> funcdecs, Hashtable<String, Integer> fieldTable) {
		LLVMLine currentLine;
		this.code.addAll(newVal.compile(env, funcdecs, fieldTable).getCode());
		// load val to eframe
		RegAndIndex regind = Env.lookup(id, env, this.regnum);
		this.code.addAll(regind.code);
		
		currentLine = new LLVMLine(this.ptrreg + " = getelementptr %eframe* " + 
				regind.reg + ", i32 0, i32 2, i32 " + regind.index + "\n");

		currentLine.setOperation("getelementptr");
		currentLine.setRegisterDefined(this.ptrreg);
		currentLine.addRegisterUsed(regind.reg);
		this.code.add(currentLine);
		
		currentLine = new LLVMLine("store i32 " + newVal.getReg() + ", i32* " + this.ptrreg + "\n");
		currentLine.setOperation("store");
		currentLine.addRegisterUsed(newVal.getReg());
		currentLine.addRegisterUsed(this.ptrreg);
		this.code.add(currentLine);
		
		//store to ret reg
		currentLine = new LLVMLine(this.reg + " = add i32 0, 10\n");// return void
		currentLine.setOperation("add");
		currentLine.setRegisterDefined(this.reg);
		currentLine.addConstantUsed(10);
		this.code.add(currentLine);
		
		return this;
	}
	
	@Override
	public void staticPass(Env env, ArrayList<Integer> funcids, ArrayList<String> stringdecs) {
		RegAndIndex regind = Env.lookup(id, env, this.regnum);
		if(regind == null){
			System.err.println("Error in Static Pass: Variable mutation before bind");
			System.exit(-1);
		}
	}
}
