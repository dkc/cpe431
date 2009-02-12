package Expressions;

import Values.*;
import Environment.Env;

public class VarRef implements Expression{
	String id;
	
	public VarRef(String id,int regnum){
		super(regnum);
		this.id = id;
		
		
	}
	
	public CodeAndReg compile(){
		Env v = Env.lookup(id, env);
		int arraysize = env.size();
		
		this.code = this.reg + " getelementptr [0 x 0] i32 " + "\n";
		return this;
	}
}
