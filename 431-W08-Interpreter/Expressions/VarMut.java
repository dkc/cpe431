package Expressions;

import Environment.Env;
import Values.*;

public class VarMut implements Expression{
	String id;
	Expression newVal;
	
	public VarMut(String id, Expression newVal){
		this.id = id;
		this.newVal = newVal;
	}
	
	public Value interp(Env env) {
		Env v = Env.lookup(this.id, env);
		Value val;
		try {
			val = newVal.interp(env);
			v.val = val;
			return val;
		} catch (ReturnException e) {
			System.err.println("return in varmut; exiting");
			System.exit(1);
			return null;
		}
	}
}
