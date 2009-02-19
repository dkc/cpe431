package Expressions;

import java.util.ArrayList;

import Environment.Env;

public class FuncBind extends AbstractCodeAndReg {
	public ArrayList<FuncDec> funs;
	public CodeAndReg body;

	public FuncBind(ArrayList<FuncDec> funs, CodeAndReg body,int regnum){
		super(regnum);
		this.funs = funs;
		this.body = body;
	}
	
	@Override
	public CodeAndReg compile(Env env) {
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
		return body.interp(local);
	}
}
