package Expressions;

<<<<<<< HEAD:431-W09-Compiler/src/Expressions/VarMut.java
import Environment.Env;
import Environment.RegAndIndex;
=======
import Environment.*;
>>>>>>> c5ed2dc890c6b7898945ef265410535d0f245a94:431-W09-Compiler/src/Expressions/VarMut.java

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
		
		
		//store to ret reg
		this.code.add(this.reg + " = add i32 0, " + newVal.getReg() + "\n");
		
		return this;
	}
}
