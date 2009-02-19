package Expressions;

import Environment.*;

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
	
	public CodeAndReg compile(Env env) {
		RegAndIndex index = Env.lookup(this.id, env);
		
		this.code.addAll(newVal.compile(env).getCode());
		//TODO load val to eframe
		RegAndIndex regind = Env.lookup(id, env);
		this.code.add(this.ptrreg + " = getelementptr %eframe* " + 
		regind.reg + ", i32 2, i32 " + regind.index + "\n");
		this.code.add("store i32 " + newVal.getReg() + ", i32* " + this.ptrreg + "\n");
		
		//store to ret reg
		this.code.add(this.reg + " = add i32 0, " + newVal.getReg() + "\n");
		
		return this;
	}
}
