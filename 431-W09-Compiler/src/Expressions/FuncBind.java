package Expressions;

import java.util.ArrayList;

import Environment.Env;

public class FuncBind extends AbstractCodeAndReg {
	public ArrayList<FuncDec> funs;
	public CodeAndReg body;
	public Env local;

	public FuncBind(ArrayList<FuncDec> funs, CodeAndReg body,int regnum){
		super(regnum);
		this.funs = funs;
		this.body = body;
	}
	
	public void staticPass(Env env){
		local = new Env();
		Env.addScope(local, env);
		
		for(int i = 0; i < funs.size(); i++){
			local.add(funs.get(i).name); //this might be dangerous.
		}
	}
	
	@Override
	public CodeAndReg compile(Env env) {

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
