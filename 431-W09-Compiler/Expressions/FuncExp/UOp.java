package Expressions.FuncExp;

import Environment.Env;
import Expressions.Expression;
import Values.ReturnContainer;
import Values.VPrimitiveFunction;
import Values.Value;

public class UOp implements Expression {
	String[] posvals = {"string-length", "integer?", "floating-point?", "void?",
						"string?", "closer?", "plain?", "print"};
	
	public String op;
	
	public UOp(String op){
		this.op = op;
	}
	
	public Value interp(Env env){
		return new VPrimitiveFunction(op);
	}
}
