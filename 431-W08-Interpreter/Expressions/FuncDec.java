package Expressions;

import java.util.ArrayList;
import Values.*;
import Environment.Env;

public class FuncDec implements Expression{
	public String name;
	public ArrayList<String> params;
	public Expression body;
	
	public FuncDec(String name, ArrayList<String> params, Expression body){
		this.name = name;
		this.params = params;
		this.body = body;
	}
	
	public Value interp(Env env){
		return new VClosure(params, body, env);
	}
}
