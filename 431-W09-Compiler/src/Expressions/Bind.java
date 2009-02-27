package Expressions;


import java.util.ArrayList;
import java.util.Hashtable;

import Environment.Env;
import Environment.RegAndIndex;
import LLVMObjects.LLVMLine;

public class Bind extends AbstractCodeAndReg{
	public String name;
	public CodeAndReg val;
	private String ptrreg = "%ptrreg";
	
	public Bind(String name, CodeAndReg val,int regnum){
		super(regnum);
		this.name = name;
		this.val = val;
		this.ptrreg += regnum;
	}
	
	public void staticPass(Env env, ArrayList<Integer> funcids){
		env.add(name);
	}
	
	public CodeAndReg compile(Env env, ArrayList<LLVMLine> funcdecs, Hashtable<String, Integer> fieldTable){
		this.code.addAll(val.compile(env, funcdecs, fieldTable).getCode());
		
		//llvm load code into eframe
		
		RegAndIndex regind = Env.lookup(name, env);
		LLVMLine currentLine;
		
		currentLine = new LLVMLine(this.ptrreg + " = getelementptr %eframe* " + regind.reg + ", i32 2, i32 " + regind.index + "\n");
		currentLine.setOperation("getelementptr");
		currentLine.setRegisterDefined(this.ptrreg);
		currentLine.addRegisterUsed(regind.reg);
		currentLine.addRegisterUsed(this.ptrreg);
		this.code.add(currentLine);
		
		currentLine = new LLVMLine("store i32 " + val.getReg() + ", i32* " + this.ptrreg + "\n");
		currentLine.setOperation("store");
		currentLine.addRegisterUsed(this.ptrreg);
		this.code.add(currentLine);		
		//return value
		currentLine = new LLVMLine(this.reg + " = add i32 0, " + val.getReg() + "\n");
		currentLine.setOperation("add");
		currentLine.setRegisterDefined(this.reg);
		currentLine.addRegisterUsed(val.getReg());
		
		return this;
	}
}