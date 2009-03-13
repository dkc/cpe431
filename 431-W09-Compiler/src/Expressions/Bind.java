package Expressions;


import java.util.ArrayList;
import java.util.Hashtable;

import Environment.Env;
import Environment.RegAndIndex;
import LLVMObjects.LLVMLine;
import Environment.FuncIDandParams;

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
	
	@Override
	public void staticPass(Env env, ArrayList<FuncIDandParams> funcids, ArrayList<String> stringdecs){
		//reserved name use check
			for(int i = 0; i < res_len; i++){
				if(name.equals(reserved_names[i])){
					System.err.println("Static Pass Error Variable Definition: illegal use of primitive name");
					System.exit(-1);
				}
			}
		env.add(name);
		this.val.staticPass(env, funcids, stringdecs);
	}
	
	@Override
	public CodeAndReg compile(Env env, ArrayList<LLVMLine> funcdecs, Hashtable<String, Integer> fieldTable){
		this.code.addAll(val.compile(env, funcdecs, fieldTable).getCode());
		
		//llvm load code into eframe
		
		RegAndIndex regind = Env.lookup(name, env, this.regnum);
		this.code.addAll(regind.code);
		
		LLVMLine currentLine;
		
		currentLine = new LLVMLine(this.ptrreg + " = getelementptr %eframe* " + regind.reg + ", i32 0, i32 2, i32 " + regind.index + "\n");
		currentLine.setOperation("getelementptr");
		currentLine.setRegisterDefined(this.ptrreg);
		currentLine.addRegisterUsed(regind.reg);
		currentLine.addConstantUsed(4*2);
		currentLine.addConstantUsed(4*regind.index);
		this.code.add(currentLine);
		
		currentLine = new LLVMLine("store i32 " + val.getReg() + ", i32* " + this.ptrreg + "\n");
		currentLine.setOperation("store");
		currentLine.addRegisterUsed(val.getReg());
		currentLine.addRegisterUsed(this.ptrreg);
		this.code.add(currentLine);		
		
		//return value
		currentLine = new LLVMLine(this.reg + " = add i32 0, 11\n");//return void
		currentLine.setOperation("add");
		currentLine.setRegisterDefined(this.reg);
		currentLine.addConstantUsed(11);
		this.code.add(currentLine);
		
		return this;
	}
}