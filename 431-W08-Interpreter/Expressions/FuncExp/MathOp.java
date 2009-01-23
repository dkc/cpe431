package Expressions.FuncExp;

import Environment.Env;
import Expressions.Expression;
import Values.*;

public class MathOp implements Expression{
	String[] posvals = {"+", "-", "*", "/", "<", ">", "<=", ">="
			, "and", "or", "not", "=="};
	
	public String op;
	
	public MathOp(String op){
		this.op = op;
	}
	
	public Value interp(Env env){
		return new VPrimitiveFunction(op);
	}
}
