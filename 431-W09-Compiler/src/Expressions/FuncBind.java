package Expressions;

import java.util.ArrayList;
import java.util.Hashtable;
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
	
	public void staticPass(Env env, ArrayList<Integer> funcids){
		local = new Env(this.regnum);
		Env.addScope(local, env);
		
		for(int i = 0; i < funs.size(); i++){
			funs.get(i).staticPass(local, null); //this might be dangerous.
		}
	}
	
	@Override
	public CodeAndReg compile(Env env, ArrayList<String> funcdecs, Hashtable<String, Integer> fieldTable) {

		CodeAndReg val;
		for(int i = 0;i < funs.size();i++){
			val = funs.get(i).compile(local, null, fieldTable);
			RegAndIndex regind = Env.lookup(funs.get(i).name, local);
			//write code?
		}
		
		this.code.addAll(body.compile(local, null, fieldTable).getCode());
		return this;
	}
}
