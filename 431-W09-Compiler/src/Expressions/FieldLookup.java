package Expressions;


import Environment.Env;
import Values.*;

public class FieldLookup implements Expression {
	String name;
	Expression obj;

	
	public FieldLookup(String name, Expression obj){
		this.name = name;
		this.obj = obj;
	}
	
	@Override
	public Value interp(Env env) {
		Value val;
		try {
			val = obj.interp(env);
		} catch (ReturnException e) {
			System.err.println("An error haiku:");
			System.err.println("	Inside field lookup");
			System.err.println("	Return should not have been there");
			System.err.println("	The system exits");
			System.exit(1);
			return null;
		}
		if(!(val instanceof VObject)){
			System.err.println("Looking for fields... on something that's NOT an object? You've gone too far this time. Exiting");
			System.exit(1);
			return null;
		}else{
			Env v = Env.lookup(name,((PObject)val).slots);
			if(v == null){
				System.err.println("Lookup on a nonexistant field--exiting");
				System.exit(1);
				return null;
			}else{
				return v.val;
			}
		}
	}

}
