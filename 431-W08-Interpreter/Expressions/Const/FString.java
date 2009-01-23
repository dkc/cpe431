package Expressions.Const;

import Environment.Env;
import Expressions.Expression;
import Values.*;
public class FString implements Expression{
	String s;
	
	public FString(String s){
		this.s = s;
	}
	
	public Value interp(Env env){
		Env slots = null;
		return new SObject(s,slots);
	}
}
