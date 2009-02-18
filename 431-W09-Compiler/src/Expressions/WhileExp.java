package Expressions;


import Environment.Env;
import Values.*;

public class WhileExp implements Expression{
	Expression test;
	Expression body;
	
	public WhileExp(Expression test, Expression body){
		this.test = test;
		this.body = body;
	}
	
	public Value interp(Env env) throws ReturnException{
		try{
		Value val = test.interp(env);
		if(!(val instanceof VBoolean)){
			System.err.println("Error: test expression in while is not a boolean value");
			System.exit(1);
		}
		if(!(Boolean) val.storedValue()){
			return new VVoid();
		}else{
			body.interp(env);
			return this.interp(env);
		}
		}catch(Exception e){return null;}
	}
}
