package Expressions;

import Environment.*;

public class VarMut extends AbstractCodeAndReg{
	String id;
	CodeAndReg newVal;
	
	public VarMut(String id, CodeAndReg newVal, int regnum){
		super(regnum);
		this.id = id;
		this.newVal = newVal;
	}
	
	public CodeAndReg compile(Env env) {
		RegAndIndex index = Env.lookup(this.id, env);
		
		this.code.addAll(newVal.compile(env).getCode());
		//TODO load val to eframe
		
		
		//store to ret reg
		this.code.add(this.reg + " = add i32 0, " + newVal.getReg() + "\n");
		
		return this;
	}
}
