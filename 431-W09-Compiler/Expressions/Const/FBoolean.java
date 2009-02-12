package Expressions.Const;


import Environment.Env;
import Expressions.Expression;
import Values.*;


public class FBoolean implements Expression {
	public boolean bool;
	
	public FBoolean(boolean bool){
		this.bool = bool;
	}
	
	public Value interp(Env env){
		return new VBoolean(this.bool);
	}
}
