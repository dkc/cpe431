package Expressions.Const;


import Environment.Env;
import Expressions.Expression;
import Values.*;

public class FVoid implements Expression {
	public Value interp(Env env){
		return new VVoid();
	}
}
