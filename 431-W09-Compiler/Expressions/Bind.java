package Expressions;

import Values.*;

import Environment.Env;

public class Bind implements Expression{
	String name;
	Expression val;
	Expression body;
	
	public Bind(String name, Expression val, Expression body){
		this.name = name;
		this.val = val;
		this.body = body;
	}
	
	public Value interp(Env env){
		try {
			Value v = val.interp(env);
			Env newenv = Env.add(new Env(name, v),env);
			return body.interp(newenv);
		} catch (ReturnException e) {
			System.err.println("NOOO YOU CAN'T BIND A RETURN WHY WOULD YOU DO THIS exiting");
			System.exit(1);
			return null;
		}
	}
}
