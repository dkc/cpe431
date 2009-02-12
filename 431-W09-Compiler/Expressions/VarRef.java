package Expressions;

import Values.*;
import Environment.Env;

public class VarRef implements Expression{
	String id;
	
	public VarRef(String id){
		this.id = id;
	}
	
	public Value interp(Env env){
		Env v = Env.lookup(id, env);
		if(v != null){
			return v.val;
		}
		return null;
	}
}
