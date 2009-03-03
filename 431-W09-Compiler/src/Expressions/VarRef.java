package Expressions;

import java.util.ArrayList;
import java.util.Hashtable;

import Environment.Env;
import Environment.RegAndIndex;
import LLVMObjects.LLVMLine;

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
	
	
	
	public CodeAndReg compile(Env env, ArrayList<String> funcdecs, Hashtable<String, Integer> fieldTable){
		LLVMLine currentLine;
		RegAndIndex regind = Env.lookup(id, env);
		if(regind != null){
			//this.code.add(this.eframereg + " = add i32 0," + regind.reg + "\n");
			this.code.addAll(regind.code);
			currentLine = new LLVMLine(this.pttreg + " = getelementptr %eframe* " + 
					regind.reg + ", i32 0, i32 2, i32 " + regind.index + "\n");
			currentLine.setOperation("getelementptr");
			currentLine.setRegisterDefined(this.pttreg);
			currentLine.addRegisterUsed(regind.reg);
			this.code.add(currentLine);
			
			currentLine = new LLVMLine(this.reg + " = load i32* " + this.pttreg + "\n");
			currentLine.setOperation("load");
			currentLine.setRegisterDefined(this.reg);
			currentLine.addRegisterUsed(this.pttreg);
			this.code.add(currentLine);
			
			return this;
		}
		//TODO error???
		//System.err.println();
		return null;
	}

	@Override
	public void staticPass(Env env, Integer funcid, ArrayList<String> stringdecs) {
		RegAndIndex regind = Env.lookup(id, env);
		if(regind == null){
			System.err.println("Error in Static Pass: Variable reference before bind");
			System.exit(-1);
		}
	}
}
