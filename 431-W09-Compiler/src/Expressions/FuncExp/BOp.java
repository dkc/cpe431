package Expressions.FuncExp;

import Environment.Env;
import Expressions.Expression;
import Values.*;

public class BOp implements Expression{
	String[] posvals = {"+", "-", "*", "/", "<", ">", "<=", ">="
			, "and", "or", "not", "=="};
	
	public String op;
	
	public BOp(String op){
		this.op = op;
	}
	
	public Value interp(Env env){
		return new VPrimitiveFunction(op);
	}
}
