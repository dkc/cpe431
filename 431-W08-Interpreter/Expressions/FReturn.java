package Expressions;


import Environment.Env;
import Values.ReturnContainer;
import Values.Value;

public class FReturn implements Expression {
	Expression target;
	
	
	public FReturn(Expression target){
		this.target = target;
	}
	
	@Override
	public Value interp(Env env) throws ReturnException {
		Value retVal;
	
		try {
			retVal = target.interp(env);
		} catch (ReturnException e) { /* we've caught another ReturnException, which should only be thrown from a return */
			System.err.println("Encountered a return inside of a return--exiting...");
			System.exit(1);
			return null;
		}
		
		throw new ReturnException(retVal);
	}

}
