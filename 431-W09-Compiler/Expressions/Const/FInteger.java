package Expressions.Const;

import Environment.Env;
import Expressions.Expression;
import Values.*;

public class FInteger implements Expression{
	public int number;
	
	public FInteger(int number){
		this.number = number;
	}
	
	public Value interp(Env env){
		return new VInteger(number);
	}
}
