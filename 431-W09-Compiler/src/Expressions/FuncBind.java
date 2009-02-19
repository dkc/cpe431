package Expressions;

import java.util.ArrayList;

import Environment.Env;
import Environment.RegAndIndex;

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

		CodeAndReg val;
		for(int i = 0;i < funs.size();i++){
			val = funs.get(i).compile(local);
			RegAndIndex regind = Env.lookup(funs.get(i).name, local);
			//write code?
		}
		
		this.code.addAll(body.compile(local).getCode());
		return this;
	}
}
