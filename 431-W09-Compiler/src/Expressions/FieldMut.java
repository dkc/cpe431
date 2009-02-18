package Expressions;


import Environment.Env;
import Values.PObject;
import Values.ReturnContainer;
import Values.VObject;
import Values.Value;

public class FieldMut implements Expression {
	String name;
	Expression newval;
	Expression obj;
	
	public FieldMut(String name, Expression newval, Expression obj){
		this.name = name;
		this.newval = newval;
		this.obj = obj;
	}
	
	@Override
	public Value interp(Env env) {
		Value val, nval;
		try {
			val = obj.interp(env);
		} catch (ReturnException e) {
			System.err.println("An error haiku:");
			System.err.println("	In field mutation");
			System.err.println("	Return should not have been there");
			System.err.println("	The system exits");
			System.exit(1);
			return null;
		}
		if(!(val instanceof VObject)){
			System.err.println("Trying to mutate a field... on something that's NOT an object? You've gone too far this time. Exiting");
			System.exit(1);
			return null;
		}else{
			Env v = Env.lookup(name,((PObject)val).slots);
			try {
				nval = newval.interp(env);
			} catch (ReturnException e) {
				System.err.println("MORE RETURN EXCEPTION ERRORS I HATE THESE THINGS");
				System.exit(1);
				return null;
			}
			if(v == null){
				((PObject)val).slots = Env.add(new Env(name,nval),((PObject)val).slots);
				return nval;
			}else{
				v.val = nval;
				return nval;
			}
		}
	}

}
