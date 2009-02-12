package Expressions;

import java.util.ArrayList;
import Environment.Env;
import Values.*;

public class MethodCall implements Expression {
	Expression obj;
	String mname;
	ArrayList<Expression> args;
	
	public MethodCall(Expression obj, String mname, ArrayList<Expression> args){
		this.obj = obj;
		this.mname = mname;
		this.args = args;
	}
	
	@Override
	public Value interp(Env env) {
		Value val;
		try {
			val = obj.interp(env);
		} catch (ReturnException e) {
			System.err.println("RETURN IS NOT A VALID METHOD CALL OUT OUT");
			System.exit(1);
			return null;
		}
		if(!(val instanceof VObject)){
			System.err.println("Only objects can have methods! Hate you hate you hate you hate you exiting");
			System.exit(1);
			return null;
		}else{
			PObject pobj = (PObject) val;
			
			Env v = Env.lookup(mname, pobj.slots);
			
			if(v == null){
				return null;
			}
			
			if(!(v.val instanceof VClosure)){
				return null;
			}
			
			VClosure vclos = (VClosure) v.val;
			Value argval = null;
			for(int i = 0;i < args.size();i++){
				try {
					argval = args.get(i).interp(env);
				} catch (ReturnException e) {
					System.err.println("Return used as an argument to a method call. I no longer have anything clever to say. Exiting");
					System.exit(1);
					return null;
				}
				vclos.env = Env.add(new Env(vclos.params.get(i), argval), vclos.env);
			}
			
			vclos.env = Env.add(new Env("this",pobj),vclos.env);
			
			try { /* return void if the method returns nothing, return that value otherwise */
				vclos.body.interp(vclos.env);;
				return new VVoid();
			} catch (ReturnException e) {
				return e.storedContainer;
			}
		}
	}

}
