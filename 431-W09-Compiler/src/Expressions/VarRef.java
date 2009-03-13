package Expressions;

import java.util.ArrayList;
import java.util.Hashtable;

import Environment.Env;
import Environment.RegAndIndex;
import LLVMObjects.LLVMLine;
import Environment.FuncIDandParams;

public class VarRef extends AbstractCodeAndReg{
	String id;
	String pttreg = "%pttrreg";
	String eframereg = "%eframereg";
	
	public VarRef(String id,int regnum){
		super(regnum);
		this.id = id;
		this.pttreg += regnum;
		this.eframereg += regnum;
	}
	

	@Override
	public CodeAndReg compile(Env env, ArrayList<LLVMLine> funcdecs, Hashtable<String, Integer> fieldTable){
		LLVMLine currentLine;
		if(this.id.equals("this")){
			currentLine = new LLVMLine(this.reg + " = add i32 0, %this\n");
			currentLine.setOperation("add");
			currentLine.setRegisterDefined(this.reg);
			currentLine.addRegisterUsed("%this");
			currentLine.addConstantUsed(0);
		}else{
		
		RegAndIndex regind = Env.lookup(id, env, this.regnum);
		
			//this.code.add(this.eframereg + " = add i32 0," + regind.reg + "\n");
			this.code.addAll(regind.code);
			
			currentLine = new LLVMLine(this.pttreg + " = getelementptr %eframe* " + 
					regind.reg + ", i32 0, i32 2, i32 " + regind.index + "\n");
			currentLine.setOperation("getelementptr");
			currentLine.setRegisterDefined(this.pttreg);
			currentLine.addRegisterUsed(regind.reg);
			currentLine.addConstantUsed(4 * 2);
			this.code.add(currentLine);
			
			currentLine = new LLVMLine(this.reg + " = load i32* " + this.pttreg + "\n");
			currentLine.setOperation("load");
			currentLine.setRegisterDefined(this.reg);
			currentLine.addRegisterUsed(this.pttreg);
			this.code.add(currentLine);
			
		
		}
		return this;
	}

	@Override
	public void staticPass(Env env, ArrayList<FuncIDandParams> funcids, ArrayList<String> stringdecs) {
		//reserved name use check
			for(int i = 0; i < (res_len - 1); i++){
				if(id.equals(reserved_names[i])){
					System.err.println("Static Pass Error Variable Reference: illegal use of primitive name");
					System.exit(-1);
				}
			}
		RegAndIndex regind = Env.lookup(id, env, this.regnum);
		if(regind == null){
			System.err.println("Error in Static Pass: Variable reference before bind");
			System.exit(-1);
		}
	}
}
