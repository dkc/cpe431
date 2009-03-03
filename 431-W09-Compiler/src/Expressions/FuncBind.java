package Expressions;

import java.util.ArrayList;
import java.util.Hashtable;
import Environment.Env;
import Environment.RegAndIndex;

public class FuncBind extends AbstractCodeAndReg {
	public ArrayList<FuncDec> funs;

	public FuncBind(ArrayList<FuncDec> funs){
		super(-1);
		this.funs = funs;
	}
	
	public void staticPass(Env env, Integer funcid, ArrayList<String> stringdecs){
		for(int i = 1; i < funs.size(); i++){
			env.add(funs.get(i).name);
			//env.add(funs.get(i).name + "met");
		}
		for(FuncDec f: funs){
			f.staticPass(env, funcid, stringdecs);
		}
	}
	
	@Override
	public CodeAndReg compile(Env env, ArrayList<String> funcdecs, Hashtable<String, Integer> fieldTable) {
		for(FuncDec f: funs){
			this.code.addAll(f.compile(env, funcdecs, fieldTable).getCode());
		}
		return this;
	}
}
