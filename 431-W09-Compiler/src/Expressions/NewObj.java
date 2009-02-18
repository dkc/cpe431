package Expressions;

import java.util.ArrayList;
import Environment.Env;
import Values.*;

public class NewObj implements Expression{
	Expression function;
	ArrayList<Expression> args;
	
	public NewObj(Expression function, ArrayList<Expression> args){
		this.function = function;
		this.args = args;
	}
	
	public Value interp(Env env){
		Value val;
		try {
			val = function.interp(env);
		} catch (ReturnException e) {
			System.err.println("RETURN IS NOT A VALID PART OF A CONSTRUCTOR GO AWAY GO AWAY GO AWAY exiting");
			System.exit(1);
			return null;
		}
		if(!(val instanceof VClosure)){
			System.err.println("Well we can't very well construct a new object with something that's not a closure. Exiting");
			System.exit(1);
			return null;
		}else{
			VClosure vclos = (VClosure) val;
			Env slots = null;
			slots = Env.add(new Env("constructor",val),slots);
			PObject obj = new PObject(slots);
			
			Value argval = null;
			for(int i = 0;i < args.size();i++){
				try {
					argval = args.get(i).interp(env);
				} catch (ReturnException e) {
					System.err.println("Catching a ReturnException in NewObj doot doot doot exiting");
					System.exit(1);
					return null;
				}
				vclos.env = Env.add(new Env(vclos.params.get(i), argval),vclos.env);
			}
			
			vclos.env = Env.add(new Env("this",obj),vclos.env);
			
			try {
				vclos.body.interp(vclos.env);
				
			} catch (ReturnException e) {
				
			}
			return obj;
		}
	}
}
