package Expressions.Const;


import Environment.Env;
import Expressions.Expression;
import Values.*;


public class FFloat implements Expression{
	public float number;
	
	public FFloat(float number){
		this.number = number;
	}
	
	public Value interp(Env env){
		return new VFloat(number);
	}
}
