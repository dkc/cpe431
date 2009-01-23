package Expressions;

import java.util.ArrayList;

import Environment.Env;
import Values.*;

public class FuncBind implements Expression {
	public ArrayList<FuncDec> funs;
	public Expression body;

	
	public FuncBind(ArrayList<FuncDec> funs, Expression body){
		this.funs = funs;
		this.body = body;
	}
	
	@Override
	public Value interp(Env env) {
		Env local = env;
		for(int i = 0; i < funs.size(); i++){
			local = Env.add(new Env(funs.get(i).name, null),local); //this might be dangerous.
		}
		Value val = null;
		for(int i = 0;i < funs.size();i++){
			val = funs.get(i).interp(local);
			Env v = Env.lookup(funs.get(i).name, local);
			v.val = val;
		}
		try {
			return body.interp(local);
		} catch (ReturnException e) {
			System.out.println("Attempted to return outside of a function/method (and within a function binding; exiting");
			System.exit(1);
			return null;
		}
	}
}
