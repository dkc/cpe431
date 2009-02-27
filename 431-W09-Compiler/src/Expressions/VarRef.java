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
	
	public CodeAndReg compile(Env env, ArrayList<LLVMLine> funcdecs, Hashtable<String, Integer> fieldTable){
		RegAndIndex regind = Env.lookup(id, env);
		if(regind != null){
			//this.code.add(this.eframereg + " = add i32 0," + regind.reg + "\n");
			this.code.addAll(regind.code);
			this.code.add(this.pttreg + " = getelementptr %eframe* " + 
					regind.reg + ", i32 0, i32 2, i32 " + regind.index + "\n");
			this.code.add(this.reg + " = load i32* " + this.pttreg + "\n");
			
			return this;
		}
		//TODO error???
		//System.err.println();
		return null;
	}
}
