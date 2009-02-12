package Expressions;

import Values.*;

import Environment.Env;
public class IfExp implements Expression{
	Expression test;
	Expression fthen;
	Expression felse;
	
	public IfExp(Expression test, Expression fthen, Expression felse){
		this.test = test;
		this.fthen = fthen;
		this.felse = felse;
	}
	
	public Value interp(Env env) throws ReturnException {
		Value val = test.interp(env);
		if(val instanceof VBoolean){
		if((Boolean) val.storedValue()){
			return fthen.interp(env);
		}else{
			return felse.interp(env);
		}
		}else{
			System.err.println("Test expression in if was not a boolean");
			System.exit(1);
			return null;
		}
	}
}
